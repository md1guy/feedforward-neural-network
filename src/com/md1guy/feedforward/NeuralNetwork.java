package com.md1guy.feedforward;

public class NeuralNetwork {
    private int inputLayerNeurons;
    //private int hiddenLayerNeurons;
    private int outputLayerNeurons;
    private Matrix ihWeights;
    private Matrix hoWeights;

    NeuralNetwork(int inputLayerNeurons, int hiddenLayerNeurons, int outputLayerNeurons) {
        this.inputLayerNeurons = inputLayerNeurons;
        //this.hiddenLayerNeurons = hiddenLayerNeurons;
        this.outputLayerNeurons = outputLayerNeurons;
        this.ihWeights = new Matrix(inputLayerNeurons + 1, hiddenLayerNeurons);
        this.hoWeights = new Matrix(hiddenLayerNeurons + 1, outputLayerNeurons);

        this.ihWeights.randomize();
        this.hoWeights.randomize();
    }

    double[] guess(double[] inputData) {
        Matrix input = Matrix.transpose(new Matrix(inputData));
        if(input.getValues().length != inputLayerNeurons) throw new RuntimeException("Incorrect inputData array size, must equal number of input neurons.");

        Layer hiddenLayer = new Layer(expandWithBias(input), ihWeights);
        Layer outputLayer = new Layer(expandWithBias(hiddenLayer.output), hoWeights);

        double[] outputArray = new double[outputLayerNeurons];
        for (int i = 0; i < outputArray.length; i++) {
            outputArray[i] = outputLayer.output.getValue(i, 0);
        }

        return outputArray;
    }

    void train(double[] inputData, double[] expectedOutputData) {
        double[] guess = guess(inputData);
    }

    private Matrix expandWithBias(Matrix inputs) {
        double[][] inputsArray = new double[inputs.getValues().length + 1][1];

        for (int i = 0; i < inputsArray.length - 1; i++) {
            inputsArray[i][0] = inputs.getValue(i, 0);
        }

        inputsArray[inputsArray.length - 1][0] = 1;

        return  new Matrix(inputsArray);
    }
}
