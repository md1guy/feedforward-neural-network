package com.md1guy.feedforward;

public class Main {

    public static void main(String[] args) {
        NeuralNetwork nn = new NeuralNetwork(2, 5, 2);

        double[] testInputData = {1, 0};

        double[] testOutputData = nn.guess(testInputData);

        for (int i = 0; i < testOutputData.length; i++) {
            System.out.println(testOutputData[i]);
        }
    }
}
