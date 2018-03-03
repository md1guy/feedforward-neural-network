package com.md1guy.feedforward;

import static com.md1guy.feedforward.Matrix.*;

public class NeuralNetwork {
    private int inputLayerNeuronsCount;
    private int outputLayerNeuronsCount;
    private Layer inputLayer;
    private Layer hiddenLayer;
    private Layer outputLayer;
    private double learnRate = 0.1;

    public NeuralNetwork(int inputLayerNeuronsCount, int hiddenLayerNeuronsCount, int outputLayerNeuronsCount) {

        this.inputLayerNeuronsCount = inputLayerNeuronsCount;
        this.outputLayerNeuronsCount = outputLayerNeuronsCount;

        inputLayer = new Layer(inputLayerNeuronsCount);
        hiddenLayer = new Layer(hiddenLayerNeuronsCount, inputLayer);
        outputLayer = new Layer(outputLayerNeuronsCount, hiddenLayer);
    }

    public double[] guess(double[] inputData) {

        double[] guess = new double[outputLayerNeuronsCount];

        if (inputData.length != inputLayerNeuronsCount)
            throw new RuntimeException("Incorrect inputData array size, must equal number of input neurons.");

        inputLayer.setOutputs(transpose(new Matrix(inputData)));

        // feed forward input data
        hiddenLayer.feedForward();
        outputLayer.feedForward();

        // convert output to 1d array
        for (int i = 0; i < guess.length; i++) {
            guess[i] = outputLayer.getOutputs().getValue(i, 0);
        }

        return guess;
    }

    public void train(double[] inputData, double[] expectedOutputData) {
        if (expectedOutputData.length != outputLayerNeuronsCount)
            throw new RuntimeException("Incorrect expectedOutputData array size, must equal number of output neurons.");

        // error calculation
        Matrix guess = transpose(new Matrix(guess(inputData)));
        Matrix expected = transpose(new Matrix(expectedOutputData));

        Matrix outputErrors = sub(expected, guess);
        Matrix hiddenErrors = mul(transpose(outputLayer.getWeights()), outputErrors);

        // backpropagation
        Func dsigm = (x) -> x * (1 - x); // sigmoid derivative function

        Matrix oGradient = map(outputLayer.getOutputs(), dsigm);
        oGradient = hadm(oGradient, outputErrors);
        oGradient = scale(oGradient, learnRate);

        Matrix hGradient = map(hiddenLayer.getOutputs(), dsigm);
        hGradient = hadm(hGradient, hiddenErrors);
        hGradient = scale(hGradient, learnRate);

        Matrix hoDeltaWeights = mul(oGradient, transpose(hiddenLayer.getOutputs()));
        Matrix ihDeltaWeights = mul(hGradient, transpose(inputLayer.getOutputs()));

        outputLayer.getWeights().add(hoDeltaWeights);
        hiddenLayer.getWeights().add(ihDeltaWeights);

        outputLayer.getBiases().add(oGradient);
        hiddenLayer.getBiases().add(hGradient);

        //System.out.println("guess - " + guess.getValue(0, 0) + " | expected: " + expected.getValue(0, 0));
    }
}













