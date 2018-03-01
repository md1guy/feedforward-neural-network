package com.md1guy.feedforward;

public class Layer {
    Matrix input;
    Matrix weights;
    Matrix output;

    Layer(Matrix input, Matrix weights, Matrix biases) {
        this.input = input;
        this.weights = weights;

        output = Matrix.mul(Matrix.transpose(weights), input);
        output = Matrix.add(output, biases);
        output.map(sigm);
    }

    Func sigm = (x) -> (1 / ( 1 + Math.pow(Math.E, (-1 * x))));
}
