package com.md1guy.feedforward;

public class Main {

    public static void main(String[] args) {
        NeuralNetwork nn = new NeuralNetwork(2, 10, 2);

        Point[] trainingSet = new Point[100000];

        for (int i = 0; i < trainingSet.length; i++) {
            trainingSet[i] = new Point();

            nn.train(trainingSet[i].getInput(), trainingSet[i].getExpectedOutput());

            double[] guess = nn.guess(trainingSet[i].getInput());

            System.out.println("{" + trainingSet[i].getInput()[0] + ", " + trainingSet[i].getInput()[1]
                    + "} ---> {"
                    + guess[0] + ", " + guess[1] + "}");
        }
    }
}
