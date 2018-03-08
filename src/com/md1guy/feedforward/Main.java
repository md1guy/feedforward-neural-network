package com.md1guy.feedforward;

public class Main {

    public static void main(String[] args) {
        int[] hiddenLayers = {4, 5};

        NeuralNetwork nn = new NeuralNetwork(2, hiddenLayers, 1);

        nn.setLearnRate(0.1);

        double[][] trainingSet = {
                {0, 0, 0},
                {0, 1, 1},
                {1, 0, 1},
                {1, 1, 0}
        };

        for (int i = 0; i < 10000; i++) {
            int index = (int)(Math.random() * 4);

            double[] input = {trainingSet[index][0], trainingSet[index][1]};
            double[] expect = {trainingSet[index][2]};

            nn.train(input, expect);
        }

        double[][] testData = {
                {0, 0},         // 0
                {1, 1},         // 0
                {0, 1},         // 1
                {1, 0}          // 1
        };

        System.out.println("guess: " + nn.guess(testData[0])[0] + ", 0 expected");
        System.out.println("guess: " + nn.guess(testData[1])[0] + ", 0 expected");
        System.out.println("guess: " + nn.guess(testData[2])[0] + ", 1 expected");
        System.out.println("guess: " + nn.guess(testData[3])[0] + ", 1 expected");

    }
}
