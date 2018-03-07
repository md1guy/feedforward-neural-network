package com.md1guy.feedforward;

import static com.md1guy.feedforward.Matrix.*;

public class NeuralNetwork {
    private int inputLayerNeuronsCount;
    private int hiddenLayerNeuronsCount;
    private int outputLayerNeuronsCount;

    private Matrix ihWeights;
    private Matrix hoWeights;
    private Matrix ihBiases;
    private Matrix hoBiases;

    private double learnRate;

    public NeuralNetwork(int inputLayerNeuronsCount, int hiddenLayerNeuronsCount, int outputLayerNeuronsCount) {
        this.inputLayerNeuronsCount = inputLayerNeuronsCount;
        this.hiddenLayerNeuronsCount = hiddenLayerNeuronsCount;
        this.outputLayerNeuronsCount = outputLayerNeuronsCount;

        ihWeights = new Matrix(hiddenLayerNeuronsCount, inputLayerNeuronsCount);
        hoWeights = new Matrix(outputLayerNeuronsCount, hiddenLayerNeuronsCount);
        ihBiases = new Matrix(hiddenLayerNeuronsCount, 1);
        hoBiases = new Matrix(outputLayerNeuronsCount, 1);

        ihWeights.randomize();
        hoWeights.randomize();
        ihBiases.randomize();
        hoBiases.randomize();

        learnRate = 0.2;
    }

    public double[] guess(double[] inputData) {

        double[] guess = new double[hoWeights.getValues().length];

        if (inputData.length != ihWeights.getValues()[0].length)
            throw new RuntimeException("Incorrect inputData array size, must equal number of input neurons.");

        // feed forward input data

        Matrix inputNeurons = fromArray(inputData);

        Matrix hiddenNeurons = mul(ihWeights, inputNeurons);
        hiddenNeurons.add(ihBiases);
        hiddenNeurons.map(sigm);

        Matrix outputNeurons = mul(hoWeights, hiddenNeurons);
        outputNeurons.add(hoBiases);
        outputNeurons.map(sigm);

        // convert output to 1d array
        for (int i = 0; i < guess.length; i++) {
            guess[i] = outputNeurons.getValue(i, 0);
        }

        return guess;
    }

    public void train(double[] inputData, double[] expectedOutputData) {
        if (expectedOutputData.length != outputLayerNeuronsCount)
            throw new RuntimeException("Incorrect expectedOutputData array size, must equal number of output neurons.");

        if (inputData.length != inputLayerNeuronsCount)
            throw new RuntimeException("Incorrect inputData array size, must equal number of input neurons.");

        // feed forward input data
        Matrix inputNeurons = fromArray(inputData);

        Matrix hiddenNeurons = mul(ihWeights, inputNeurons);
        hiddenNeurons.add(ihBiases);
        hiddenNeurons.map(sigm);

        Matrix outputNeurons = mul(hoWeights, hiddenNeurons);
        outputNeurons.add(hoBiases);
        outputNeurons.map(sigm);

        Matrix guess = outputNeurons;
        Matrix expected = fromArray(expectedOutputData);

        Matrix outputErrors = sub(expected, guess);

        // backpropagation
        Matrix oGradient = map(outputNeurons, dsigm);
        oGradient = hadm(oGradient, outputErrors);
        oGradient = scale(oGradient, learnRate);

        Matrix hoDeltaWeights = mul(oGradient, transpose(hiddenNeurons));
        hoWeights.add(hoDeltaWeights);
        hoBiases.add(oGradient);

        Matrix hiddenErrors = mul(transpose(hoWeights), outputErrors);

        Matrix hGradient = map(hiddenNeurons, dsigm);
        hGradient = hadm(hGradient, hiddenErrors);
        hGradient = scale(hGradient, learnRate);

        Matrix ihDeltaWeights = mul(hGradient, transpose(inputNeurons));
        ihWeights.add(ihDeltaWeights);
        ihBiases.add(hGradient);


        //System.out.println("guess - " + guess.getValue(0, 0) + " | expected: " + expected.getValue(0, 0));
    }

    Func sigm = (x) -> (1 / (1 + Math.exp(-1 * x)));    // sigmoid function
    Func dsigm = (x) -> x * (1 - x);                    // sigmoid derivative function
}













