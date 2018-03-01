package com.md1guy.feedforward;

public class NeuralNetwork {
    private int inputLayerNeurons;
    private int outputLayerNeurons;
    private Matrix ihWeights;
    private Matrix hoWeights;
    private Matrix ihBiases;
    private Matrix hoBiases;

    public NeuralNetwork(int inputLayerNeurons, int hiddenLayerNeurons, int outputLayerNeurons) {
        this.inputLayerNeurons = inputLayerNeurons;
        this.outputLayerNeurons = outputLayerNeurons;

        this.ihWeights = new Matrix(inputLayerNeurons, hiddenLayerNeurons);
        this.hoWeights = new Matrix(hiddenLayerNeurons, outputLayerNeurons);
        this.ihBiases = new Matrix(hiddenLayerNeurons, 1);
        this.hoBiases = new Matrix(outputLayerNeurons, 1);

        this.ihWeights.randomize();
        this.hoWeights.randomize();
        this.ihBiases.randomize();
        this.hoBiases.randomize();
    }

    public double[] guess(double[] inputData) {
        Matrix input = Matrix.transpose(new Matrix(inputData));
        if(input.getValues().length != inputLayerNeurons)
            throw new RuntimeException("Incorrect inputData array size, must equal number of input neurons.");

        Layer hiddenLayer = new Layer(input, ihWeights, ihBiases);
        Layer outputLayer = new Layer(hiddenLayer.output, hoWeights, hoBiases);

        double[] outputArray = new double[outputLayerNeurons];
        for (int i = 0; i < outputArray.length; i++) {
            outputArray[i] = outputLayer.output.getValue(i, 0);
        }

        return outputArray;
    }

    public void train(double[] inputData, double[] expectedOutputData) {
        if(expectedOutputData.length != outputLayerNeurons)
            throw new RuntimeException("Incorrect expectedOutputData array size, must equal number of output neurons.");

        Matrix hiddenErrors = calculateErrors(inputData, expectedOutputData);
    }

    Matrix calculateErrors(double[] inputData, double[] expectedOutputData) {
        Matrix guess = new Matrix(guess(inputData));
        guess = Matrix.transpose(guess);
        Matrix expected = new Matrix(expectedOutputData);
        expected = Matrix.transpose(expected);

        Matrix outputErrors = Matrix.sub(expected, guess);

        outputErrors.print();

        Matrix hiddenErrors = Matrix.mul(hoWeights, outputErrors);

        return hiddenErrors;
    }
}