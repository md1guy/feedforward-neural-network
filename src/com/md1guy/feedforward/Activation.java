package com.md1guy.feedforward;

public class Activation {
    private Activation() {
    }

    private Func activation;
    private Func deactivation;

    private static volatile Activation instance;

    public static Activation getInstance() {
        Activation localInstance = instance;

        if (localInstance == null) {
            synchronized (Activation.class) {
                localInstance = instance;

                if (localInstance == null) {
                    instance = localInstance = new Activation();
                }
            }
        }

        return localInstance;
    }

    public void setActivationFunction(String activation) {
        switch (activation) {
            case ("sigmoid"):
                this.activation = x -> (1 / (1 + Math.pow(Math.E, (-1 * x))));
                this.deactivation = y -> y * (1 - y);
                break;

            case ("tanh"):
                this.activation = Math::tanh;
                this.deactivation = y -> 1 - Math.pow(y, 2);
                break;

            case ("linear"):
                this.activation = x -> x;
                this.deactivation = x -> 1;
                break;

            default:
                throw new RuntimeException("Function " + activation + " do not exists.");
        }
    }

    public Func getActivation() {
        return activation;
    }

    public Func getDeactivation() {
        return deactivation;
    }
}
