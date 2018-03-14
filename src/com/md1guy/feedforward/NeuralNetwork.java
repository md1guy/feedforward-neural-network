package com.md1guy.feedforward;

import static com.md1guy.feedforward.Matrix.*;

public class NeuralNetwork {
    private int inputLayerNeuronsCount;
    private int outputLayerNeuronsCount;
    private Layer inputLayer;
    private Layer[] hiddenLayers;
    private Layer outputLayer;
    private double learnRate;
    Activation activation;

    public NeuralNetwork(int inputLayerNeuronsCount, int[] hiddenLayersNeuronsCounts, int outputLayerNeuronsCount) {

        this.inputLayerNeuronsCount = inputLayerNeuronsCount;
        this.outputLayerNeuronsCount = outputLayerNeuronsCount;

        inputLayer = new Layer(inputLayerNeuronsCount);
        hiddenLayers = new Layer[hiddenLayersNeuronsCounts.length];
        hiddenLayers[0] = new Layer(hiddenLayersNeuronsCounts[0], inputLayer);

        if(hiddenLayers.length > 1) {
            for (int i = 1; i < hiddenLayers.length; i++) {
                hiddenLayers[i] = new Layer(hiddenLayersNeuronsCounts[i], hiddenLayers[i - 1]);
            }
        }

        outputLayer = new Layer(outputLayerNeuronsCount, hiddenLayers[hiddenLayersNeuronsCounts.length - 1]);

        learnRate = 0.1;

        setActivation("sigmoid");
    }

    public NeuralNetwork(int inputLayerNeuronsCount, int[] hiddenLayersNeuronsCounts, int outputLayerNeuronsCount, String activation) {

        this.inputLayerNeuronsCount = inputLayerNeuronsCount;
        this.outputLayerNeuronsCount = outputLayerNeuronsCount;

        inputLayer = new Layer(inputLayerNeuronsCount);
        hiddenLayers = new Layer[hiddenLayersNeuronsCounts.length];
        hiddenLayers[0] = new Layer(hiddenLayersNeuronsCounts[0], inputLayer);

        if(hiddenLayers.length > 1) {
            for (int i = 1; i < hiddenLayers.length; i++) {
                hiddenLayers[i] = new Layer(hiddenLayersNeuronsCounts[i], hiddenLayers[i - 1]);
            }
        }

        outputLayer = new Layer(outputLayerNeuronsCount, hiddenLayers[hiddenLayersNeuronsCounts.length - 1]);

        learnRate = 0.1;

        setActivation(activation);
    }

    public NeuralNetwork(int inputLayerNeuronsCount, int hiddenLayersNeuronsCounts, int outputLayerNeuronsCount) {

        this.inputLayerNeuronsCount = inputLayerNeuronsCount;
        this.outputLayerNeuronsCount = outputLayerNeuronsCount;

        inputLayer = new Layer(inputLayerNeuronsCount);
        hiddenLayers = new Layer[1];
        hiddenLayers[0] = new Layer(hiddenLayersNeuronsCounts, inputLayer);

        outputLayer = new Layer(outputLayerNeuronsCount, hiddenLayers[0]);

        learnRate = 0.1;

        setActivation("sigmoid");
    }

    public NeuralNetwork(int inputLayerNeuronsCount, int hiddenLayersNeuronsCounts, int outputLayerNeuronsCount, String activation) {

        this.inputLayerNeuronsCount = inputLayerNeuronsCount;
        this.outputLayerNeuronsCount = outputLayerNeuronsCount;

        inputLayer = new Layer(inputLayerNeuronsCount);
        hiddenLayers = new Layer[1];
        hiddenLayers[0] = new Layer(hiddenLayersNeuronsCounts, inputLayer);

        outputLayer = new Layer(outputLayerNeuronsCount, hiddenLayers[0]);

        learnRate = 0.1;

        setActivation(activation);
    }

    public void setActivation(String activation) {
        this.activation = Activation.getInstance();
        this.activation.setActivationFunction(activation);
    }

    public void setLearnRate(double learnRate) {
        this.learnRate = learnRate;
    }

    public double[] guess(double[] inputData) {

        double[] guess = new double[outputLayerNeuronsCount];

        if (inputData.length != inputLayerNeuronsCount)
            throw new RuntimeException("Incorrect inputData array size, must equal number of input neurons.");

        inputLayer.setOutputs(transpose(new Matrix(inputData)));

        // feed forward input data
        for (int i = 0; i < hiddenLayers.length; i++) {
            hiddenLayers[i].feedForward();
        }
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

        Matrix guess = fromArray(guess(inputData));
        Matrix expected = fromArray(expectedOutputData);

        // backpropagation
        Matrix outputErrors = sub(expected, guess);

        outputLayer.backpropagate(outputErrors, learnRate);

        //System.out.println("guess - " + guess.getValue(0, 0) + " | expected: " + expected.getValue(0, 0));
    }
}

































