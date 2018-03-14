package com.md1guy.feedforward;

import static com.md1guy.feedforward.Matrix.*;

public class Layer {
    private Matrix inputs;
    private Matrix weights;
    private Matrix outputs;
    private Matrix biases;

    private Layer prevLayer;

    private Matrix errors;

    Layer(int curLayerNeurons, Layer prevLayer) {
        //this.inputs = new Matrix(prevLayer.getOutputs().getValues().length, 1);
        this.weights = new Matrix(curLayerNeurons, prevLayer.getOutputs().getValues().length);
        this.outputs = new Matrix(curLayerNeurons, 1);
        this.biases = new Matrix(curLayerNeurons, 1);
        this.prevLayer = prevLayer;

        this.weights.randomize();
        this.biases.randomize();
    }

    Layer(int curLayerNeurons) {
        this.outputs = new Matrix(curLayerNeurons, 1);
    }

    public Matrix getInputs() {
        if(inputs == null)
            throw new NullPointerException();
        return inputs;
    }

    public void setInputs(Matrix inputs) {
        this.inputs = inputs;
    }

    public Matrix getWeights() {
        if(weights == null)
            throw new NullPointerException();
        return weights;
    }

    public void setWeights(Matrix weights) {
        this.weights = weights;
    }

    public Matrix getOutputs() {
        if(outputs == null)
            throw new NullPointerException();
        return outputs;
    }

    public void setOutputs(Matrix outputs) {
        this.outputs = outputs;
    }

    public Matrix getBiases() {
        if(biases == null)
            throw new NullPointerException();
        return biases;
    }

    public void setBiases(Matrix biases) {
        this.biases = biases;
    }

    void feedForward() {
        inputs = prevLayer.getOutputs();
        outputs = mul(weights, inputs);
        outputs.add(biases);
        outputs.map(Activation.getInstance().getActivation());
    }

    void backpropagate(Matrix errors, double learnRate) {
        if(weights == null) return;

        this.errors = errors;

        Matrix gradient = map(outputs, Activation.getInstance().getDeactivation());
        gradient = hadm(gradient, errors);
        gradient = scale(gradient, learnRate);

        Matrix deltaWeights = mul(gradient, transpose(prevLayer.outputs));

        weights.add(deltaWeights);
        biases.add(gradient);

        Matrix prevLayerErrors = mul(transpose(weights), errors);

        prevLayer.backpropagate(prevLayerErrors, learnRate);
    }

    Func sigm = (x) -> (1 / (1 + Math.pow(Math.E, (-1 * x))));  // sigmoid function
    Func dsigm = (x) -> x * (1 - x);                            // derivative sigmoid function
}
