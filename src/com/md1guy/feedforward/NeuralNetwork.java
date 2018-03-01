package com.md1guy.feedforward;

public class NeuralNetwork {
    private Matrix inputLayerNeurons;
    private Matrix hiddenLayerNeurons;
    private Matrix outputLayerNeurons;
    private Matrix ihWeights;
    private Matrix hoWeights;
    private double[] biases = new double[2];

    NeuralNetwork(int inputLayerNeurons, int hiddenLayerNeurons, int outputLayerNeurons) {
        this.inputLayerNeurons = new Matrix(inputLayerNeurons, 1);
        this.hiddenLayerNeurons = new Matrix(hiddenLayerNeurons, 1);
        this.outputLayerNeurons = new Matrix(outputLayerNeurons, 1);
        this.ihWeights = new Matrix(inputLayerNeurons, hiddenLayerNeurons);
        this.hoWeights = new Matrix(hiddenLayerNeurons, outputLayerNeurons);

        this.ihWeights.randomize();
        this.hoWeights.randomize();

        for (int i = 0; i < biases.length; i++) {
            biases[i] = Math.random();
        }
    }

    double[] guess(double[] inputData) {
        Matrix input = Matrix.transpose(new Matrix(inputData));
        if(input.getValues().length != inputLayerNeurons.getValues().length) throw new RuntimeException("Incorrect inputData array size, must equal number of input neurons.");

        Layer hiddenLayer = new Layer(input, ihWeights, biases[0]);
        Layer outputLayer = new Layer(hiddenLayer.output, hoWeights, biases[1]);

        double[] outputArray = new double[outputLayerNeurons.getValues().length];
        for (int i = 0; i < outputArray.length; i++) {
            outputArray[i] = outputLayer.output.getValue(i, 0);
        }

        return outputArray;
    }

    void train(double[] inputData, double[] expectedOutputData) {
        double[] guess = guess(inputData);
    }
}
