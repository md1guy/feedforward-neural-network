package com.md1guy.feedforward;

public class Point {
    private double[] input;
    private double[] expectedOutput;

    Point() {
        this.input = new double[2];
        this.expectedOutput = new double[2];

        for (int i = 0; i < input.length; i++) {
            input[i] = (int)(Math.random() * 2);
        }

        if(input[0] != input[1]) {
            expectedOutput[0] = 1;
            expectedOutput[1] = 0;
        }
        else {
            expectedOutput[0] = 0;
            expectedOutput[1] = 1;
        }
    }

    public double[] getExpectedOutput() {
        return expectedOutput;
    }

    public double[] getInput() {
        return input;
    }
}
