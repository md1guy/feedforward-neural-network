package com.md1guy.feedforward;

public class Layer {
    private Matrix inputs;
    private Matrix weights;
    private Matrix outputs;
    private Matrix biases;
    Layer prevLayer;

    Layer(int prevLayerNeurons, int curLayerNeurons, Layer prevLayer) {
        this.inputs = new Matrix(curLayerNeurons, 1);
        this.weights = new Matrix(curLayerNeurons, prevLayerNeurons);
        this.outputs = new Matrix(curLayerNeurons, 1);
        this.biases = new Matrix(curLayerNeurons, 1);
        this.prevLayer = prevLayer;

        this.weights.randomize();
        this.biases.randomize();
    }

    Layer(int curLayerNeurons) {
        this.inputs = null;
        this.weights = null;
        this.outputs = new Matrix(curLayerNeurons, 1);
        this.biases = null;
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
        outputs = Matrix.mul(weights, prevLayer.getOutputs());
        outputs = Matrix.add(outputs, biases);
        outputs.map(sigm);
    }

    Func sigm = (x) -> (1 / (1 + Math.pow(Math.E, (-1 * x))));
}
