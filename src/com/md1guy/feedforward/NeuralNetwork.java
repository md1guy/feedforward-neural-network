package com.md1guy.feedforward;

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
        hiddenLayer = new Layer(inputLayerNeuronsCount, hiddenLayerNeuronsCount, inputLayer);
        outputLayer = new Layer(hiddenLayerNeuronsCount, outputLayerNeuronsCount, hiddenLayer);
    }

    public double[] guess(double[] inputData) {

        double[] guess = new double[outputLayerNeuronsCount];

        if (inputData.length != inputLayerNeuronsCount)
            throw new RuntimeException("Incorrect inputData array size, must equal number of input neurons.");

        inputLayer.setOutputs(Matrix.transpose(new Matrix(inputData)));

        // feed forward input data
        hiddenLayer.setInputs(inputLayer.getOutputs()); // it's fine
        hiddenLayer.feedForward();

        outputLayer.setInputs(inputLayer.getOutputs());
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

        // error calculation part
        Matrix guess = Matrix.transpose(new Matrix(guess(inputData)));
        Matrix expected = Matrix.transpose(new Matrix(expectedOutputData));

        Matrix outputErrors = Matrix.sub(expected, guess);
        Matrix hiddenErrors = Matrix.mul(Matrix.transpose(outputLayer.getWeights()), outputErrors);

        // backpropagation part
        Func dsigm = (x) -> x * (1 - x); // sigmoid derivative function

        Matrix outputGradient = Matrix.hadm(outputErrors, Matrix.map(outputLayer.getOutputs(), dsigm));
        Matrix hiddenGradient = Matrix.hadm(hiddenErrors, Matrix.map(hiddenLayer.getOutputs(), dsigm));

        outputGradient = Matrix.scale(outputGradient, learnRate);
        hiddenGradient = Matrix.scale(hiddenGradient, learnRate);

        Matrix hoDeltaWeights = Matrix.mul(outputGradient, Matrix.transpose(hiddenLayer.getOutputs()));
        Matrix ihDeltaWeights = Matrix.mul(hiddenGradient, Matrix.transpose(inputLayer.getOutputs()));

        outputLayer.setWeights(Matrix.add(outputLayer.getWeights(), hoDeltaWeights));
        outputLayer.setBiases(Matrix.add(outputLayer.getBiases(), outputGradient));
        hiddenLayer.setWeights(Matrix.add(hiddenLayer.getWeights(), ihDeltaWeights));
        hiddenLayer.setBiases(Matrix.add(hiddenLayer.getBiases(), hiddenGradient));

        //System.out.println("guess - " + guess.getValue(0, 0) + " | expected: " + expected.getValue(0, 0));
    }
}













