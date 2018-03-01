package com.md1guy.feedforward;

public class Matrix {

    private int rows;
    private int cols;
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

    // new values filled with 0's
    public Matrix(int rows, int cols) {

        this.rows = rows;
        this.cols = cols;
        this.values = new double[rows][cols];
    }

    // new values based on 2d array
    public Matrix(double[][] values) {

        this.rows = values.length;
        this.cols = values[0].length;
        this.values = new double[this.rows][this.cols];

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.values[i][j] = values[i][j];
            }
        }
    }

    // new matrix(vector) based on 1d array
    public Matrix(double[] vector) {

        this.rows = 1;
        this.cols = vector.length;
        this.values = new double[this.rows][this.cols];

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.values[i][j] = vector[j];
            }
        }
    }

    // randomize values
    public void randomize() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.values[i][j] = Math.random() * 2 - 1;
            }
        }
    }

    // fill matrix values with particular double value
    public void fill(double value) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
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
        Matrix transposed = new Matrix(matrix.cols, matrix.rows);

        for (int i = 0; i < transposed.rows; i++) {
            for (int j = 0; j < transposed.cols; j++) {
                transposed.values[i][j] = matrix.values[j][i];
            }
        }

        return transposed;
    }

    // A + B
    public static Matrix add(Matrix A, Matrix B) {

        if (B.rows != A.rows || B.cols != A.cols) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }

        Matrix C = new Matrix(A.rows, A.cols);

        for (int i = 0; i < A.rows; i++) {
            for (int j = 0; j < A.cols; j++) {
                C.values[i][j] = A.values[i][j] + B.values[i][j];
            }
        }

        return C;
    }

    // A + n
    public static Matrix add(Matrix matrix, double n) {
        Matrix C = new Matrix(matrix.rows, matrix.cols);

        for (int i = 0; i < matrix.rows; i++) {
            for (int j = 0; j < matrix.cols; j++) {
                C.values[i][j] = matrix.values[i][j] + n;
            }
        }

        return C;
    }

    // A - B
    public static Matrix sub(Matrix A, Matrix B) {

        if (B.rows != A.rows || B.cols != A.cols) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }

        Matrix C = new Matrix(A.rows, A.cols);

        for (int i = 0; i < A.rows; i++) {
            for (int j = 0; j < A.cols; j++) {
                C.values[i][j] = A.values[i][j] - B.values[i][j];
            }
        }

        return C;
    }

    // A - n
    public static Matrix sub(Matrix matrix, double n) {
        Matrix C = new Matrix(matrix.rows, matrix.cols);

        for (int i = 0; i < matrix.rows; i++) {
            for (int j = 0; j < matrix.cols; j++) {
                C.values[i][j] = matrix.values[i][j] - n;
            }
        }

        return C;
    }

    // A * n
    public static Matrix scale(Matrix matrix, double scalar) {

        Matrix C = new Matrix(matrix.rows, matrix.cols);

        for (int i = 0; i < matrix.rows; i++) {
            for (int j = 0; j < matrix.cols; j++) {
                C.values[i][j] = matrix.values[i][j] * scalar;
            }
        }

        return C;
    }

    // A .* B (Hadamard product)
    public static Matrix hadm(Matrix A, Matrix B) {

        if (B.rows != A.rows || B.cols != A.cols) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }

        Matrix C = new Matrix(A.rows, A.cols);

        for (int i = 0; i < A.rows; i++) {
            for (int j = 0; j < A.cols; j++) {
                C.values[i][j] = A.values[i][j] * B.values[i][j];
            }
        }

        return C;
    }

    // A * B
    public static Matrix mul(Matrix A, Matrix B) {

        if (A.cols != B.rows) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }

        Matrix C = new Matrix(A.rows, B.cols);

        for (int i = 0; i < C.rows; i++) {
            for (int j = 0; j < C.cols; j++) {
                for (int k = 0; k < A.cols; k++) {
                    C.values[i][j] += (A.values[i][k] * B.values[k][j]);
                }
            }
        }

        return C;
    }

    // maps every element of the matrix with function, passed as parameter
    public void map(Func function) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.values[i][j] = function.fn(this.values[i][j]);
            }
        }
    }

    // prints matrix
    public void print() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
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