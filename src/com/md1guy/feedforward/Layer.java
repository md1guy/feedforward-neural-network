package com.md1guy.feedforward;

public class Layer {
    Matrix input;
    Matrix weights;
    Matrix output;

    Layer(Matrix input, Matrix weights, double bias) {
        this.input = input;
        this.weights = weights;

        output = Matrix.add(Matrix.mul(Matrix.transpose(weights), input), bias);
        //output = Matrix.mul(Matrix.transpose(weights), input);
        output.map(sigm);
    }

    Func sigm = (x) -> (1 / ( 1 + Math.pow(Math.E, (-1 * x))));
}
