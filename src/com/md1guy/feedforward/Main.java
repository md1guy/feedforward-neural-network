package com.md1guy.feedforward;

public class Main {

    public static void main(String[] args) {
        NeuralNetwork nn = new NeuralNetwork(2, 3, 2);

        Point[] trainingSet = new Point[1];

        for (int i = 0; i < trainingSet.length; i++) {
            trainingSet[i] = new Point();

            nn.train(trainingSet[i].getInput(), trainingSet[i].getExpectedOutput());
        }
    }
}
