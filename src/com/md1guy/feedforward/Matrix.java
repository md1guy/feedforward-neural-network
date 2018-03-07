package com.md1guy.feedforward;

import java.util.Random;

public class Matrix {
    private double[][] values;

    // get particular value from matrix values array
    public double getValue(int i, int j) {
        return values[i][j];
    }

    // values getter
    public double[][] getValues() {
        return values;
    }

    // set particular value to matrix values array
    public void setValue(int i, int j, double value) {
        values[i][j] = value;
    }

    // values setter
    public void setValues(double[][] values) {
        this.values = values;
    }

    public void setValues(Matrix matrix) {
        this.values = matrix.getValues();
    }

    // new values filled with 0's
    public Matrix(int rows, int cols) {
        this.values = new double[rows][cols];
    }

    // new values based on 2d array
    public Matrix(double[][] values) {
        this.values = new double[values.length][values[0].length];

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                this.values[i][j] = values[i][j];
            }
        }
    }

    // new matrix(vector) based on 1d array
    public Matrix(double[] vector) {

        this.values = new double[1][vector.length];

        for (int j = 0; j < vector.length; j++) {
            this.values[0][j] = vector[j];
        }
    }


    static Matrix fromArray(double[] arr) {
        Matrix m = new Matrix(arr.length, 1);
        for (int i = 0; i < arr.length; i++) {
            m.values[i][0] = arr[i];
        }
        return m;
    }

    // randomize values
    public void randomize() {
        Random random = new Random();

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                this.values[i][j] = random.nextFloat() * 2 - 1; //Math.random() * 2 - 1;
            }
        }
    }

    // fill matrix values with particular double value
    public void fill(double value) {
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                values[i][j] = value;
            }
        }
    }

    // returns matrix with random values in range [0, 1]
    public static Matrix random(int rows, int cols) {

        Matrix matrix = new Matrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix.values[i][j] = Math.random();
            }
        }

        return matrix;
    }

    // returns transposed matrix
    public static Matrix transpose(Matrix matrix) {
        Matrix transposed = new Matrix(matrix.values[0].length, matrix.values.length);

        for (int i = 0; i < transposed.values.length; i++) {
            for (int j = 0; j < transposed.values[0].length; j++) {
                transposed.values[i][j] = matrix.values[j][i];
            }
        }

        return transposed;
    }

    // A + B
    public static Matrix add(Matrix A, Matrix B) {
        if (B.values.length != A.values.length || B.values[0].length != A.values[0].length) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }

        Matrix C = new Matrix(A.values.length, A.values[0].length);

        for (int i = 0; i < A.values.length; i++) {
            for (int j = 0; j < A.values[0].length; j++) {
                C.values[i][j] = A.values[i][j] + B.values[i][j];
            }
        }

        return C;
    }

    // Adding Matrix B to current Matrix values
    public void add(Matrix B) {
        if (B.values.length != values.length || B.values[0].length != values[0].length) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                values[i][j] += B.values[i][j];
            }
        }
    }

    // A + n
    public static Matrix add(Matrix matrix, double n) {
        Matrix C = new Matrix(matrix.values.length, matrix.values[0].length);

        for (int i = 0; i < matrix.values.length; i++) {
            for (int j = 0; j < matrix.values[0].length; j++) {
                C.values[i][j] = matrix.values[i][j] + n;
            }
        }

        return C;
    }

    // A - B
    public static Matrix sub(Matrix A, Matrix B) {
        if (B.values.length != A.values.length || B.values[0].length != A.values[0].length) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }

        Matrix C = new Matrix(A.values.length, A.values[0].length);

        for (int i = 0; i < A.values.length; i++) {
            for (int j = 0; j < A.values[0].length; j++) {
                C.values[i][j] = A.values[i][j] - B.values[i][j];
            }
        }

        return C;
    }

    // A - n
    public static Matrix sub(Matrix matrix, double n) {
        Matrix C = new Matrix(matrix.values.length, matrix.values[0].length);

        for (int i = 0; i < matrix.values.length; i++) {
            for (int j = 0; j < matrix.values[0].length; j++) {
                C.values[i][j] = matrix.values[i][j] - n;
            }
        }

        return C;
    }

    // A * n
    public static Matrix scale(Matrix matrix, double scalar) {
        Matrix C = new Matrix(matrix.values.length, matrix.values[0].length);

        for (int i = 0; i < matrix.values.length; i++) {
            for (int j = 0; j < matrix.values[0].length; j++) {
                C.values[i][j] = matrix.values[i][j] * scalar;
            }
        }

        return C;
    }

    // A .* B (Hadamard product)
    public static Matrix hadm(Matrix A, Matrix B) {
        if (B.values.length != A.values.length || B.values[0].length != A.values[0].length) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }

        Matrix C = new Matrix(A.values.length, A.values[0].length);

        for (int i = 0; i < A.values.length; i++) {
            for (int j = 0; j < A.values[0].length; j++) {
                C.values[i][j] = A.values[i][j] * B.values[i][j];
            }
        }

        return C;
    }

    // A * B
    public static Matrix mul(Matrix A, Matrix B) {

        if (A.values[0].length != B.values.length) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }

        Matrix C = new Matrix(A.values.length, B.values[0].length);

        for (int i = 0; i < C.values.length; i++) {
            for (int j = 0; j < C.values[0].length; j++) {
                for (int k = 0; k < A.values[0].length; k++) {
                    C.values[i][j] += (A.values[i][k] * B.values[k][j]);
                }
            }
        }

        return C;
    }

    // maps every element of the matrix with function, passed as parameter
    public void map(Func function) {
        for (int i = 0; i < this.values.length; i++) {
            for (int j = 0; j < this.values[0].length; j++) {
                this.values[i][j] = function.fn(this.values[i][j]);
            }
        }
    }

    public static Matrix map(Matrix matrix, Func function) {
        for (int i = 0; i < matrix.values.length; i++) {
            for (int j = 0; j < matrix.values[0].length; j++) {
                matrix.values[i][j] = function.fn(matrix.values[i][j]);
            }
        }

        return matrix;
    }

    // prints matrix
    public void print() {
        for (int i = 0; i < this.values.length; i++) {
            for (int j = 0; j < this.values[0].length; j++) {
                System.out.print(this.values[i][j] + " ");
            }
            System.out.println();
        }
    }
}

@FunctionalInterface
interface Func {
    double fn(double x);
}