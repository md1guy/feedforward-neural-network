package com.md1guy.feedforward;

import static com.md1guy.feedforward.Matrix.*;

public class Layer {
    private Matrix weights;
    private Matrix biases;

    Layer(int prevLayerNeuronsCount, int curLayerNeuronsCount) {
        this.weights = new Matrix(curLayerNeuronsCount, prevLayerNeuronsCount);
        this.biases = new Matrix(curLayerNeuronsCount, 1);

        weights.randomize();
        biases.randomize();
    }

    public Matrix getWeights() {
        return weights;
    }

    public Matrix getBiases() {
        return biases;
    }

    public Matrix feedForward(Matrix neuronValues) {
        neuronValues = mul(weights, neuronValues);
        neuronValues.add(biases);
        Func sigm = (x) -> (1 / (1 + Math.exp(-1 * x)));
        neuronValues.map(sigm);
        return neuronValues;
    }

//    Func softplus = (x) -> Math.log(1 + Math.exp(x));
//    Func sigm = (x) -> (1 / (1 + Math.exp(-1 * x)));
//    Func tanh = Math::tanh;
//    Func relu = (x) -> Math.max(0, x);
}
