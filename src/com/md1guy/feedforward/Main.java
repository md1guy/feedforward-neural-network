package com.md1guy.feedforward;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        NeuralNetwork nn = new NeuralNetwork(2,4, 1);

        double[][] trainingSet = {
                {0, 0, 0},
                {0, 1, 1},
                {1, 0, 1},
                {1, 1, 0}
        };

        Random random = new Random();

        for (int i = 0; i < 5000; i++) {
            int index = random.nextInt(4);

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
