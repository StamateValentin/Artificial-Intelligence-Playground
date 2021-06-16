package neural_network;

import exceptions.InvalidInputException;
import matrix.Matrix;

/* TODO: BIAS */
public class NeuralNetwork {
    private final double learningRate = 0.01;

    private final double[][][] brain;
    private double cost = 0;

    public NeuralNetwork(int[] layerDimension) {
        this.brain = new double[layerDimension.length - 1][][];

        for (int i = 0; i < brain.length; i++) {
            int n = layerDimension[i + 1];
            int m = layerDimension[i];
            brain[i] = NeuralNetworkUtil.createRandomWeightedMatrix(n, m);
        }
    }

    public double[][] getOutput(final double[][] input) throws InvalidInputException {
        double[][] currentInput = input;

        for (int i = 0; i < brain.length; i++) {
            currentInput = Matrix.multiply(brain[i], currentInput);
            applyActivationFunction(currentInput);
        }

        return currentInput;
    }

    public void backPropagation(double[][] input, double[][] expected) {
        double[][][] outputs = getOutputs(input);

        double[][] currentOutput = outputs[outputs.length - 1];
        double[][] currentError = Matrix.subtract(expected, currentOutput);

        for (int i = brain.length - 1; i >= 0; i--) {
            final double[][] layer = brain[i];
            final double[][] previousOutput = outputs[i];

            final double[][] layerTranspose = Matrix.transpose(layer);

            final double[][] previousError = Matrix.multiply(layerTranspose, currentError);

            /* FIST BIT */
            double[][] errorSigmoid = Matrix.copyOf(currentError);

            for (int k = 0; k < errorSigmoid.length; k++) {
                errorSigmoid[k][0] *= - derivativeActivationFunction(currentOutput[k][0]);
            }

            /* SECOND BIT */
            final double[][] slopeMatrix = Matrix.multiply(errorSigmoid, Matrix.transpose(previousOutput));

            /* UPDATE THE WEIGHTS */
            for (int k = 0; k < layer.length; k++) {
                for (int l = 0; l < layer[0].length; l++) {
                    layer[k][l] = layer[k][l] - learningRate * slopeMatrix[k][l];
                }
            }

            currentOutput = previousOutput;
            currentError = previousError;
        }
    }

    private double[][][] getOutputs(final double[][] input) {
        int n = brain.length;
        double[][][] outputs = new double[n + 1][][];

        outputs[0] = Matrix.copyOf(input);
        for (int i = 1; i <= n; i++) {
            double[][] layer = brain[i - 1];
            double[][] currentInput = outputs[i - 1];

            outputs[i] = Matrix.multiply(layer, currentInput);

            applyActivationFunction(outputs[i]);
        }

        return outputs;
    }

    public void trainIteration(double[][][] inputs, double[][][] expectedOutputs) throws InvalidInputException {
        int n = inputs.length;
        double cost = 0;

        for (int i = 0; i < n; i++) {
            double[][] input = inputs[i];
            double[][] expectedOutput = expectedOutputs[i];

            backPropagation(input, expectedOutput);

            double[][] output = getOutput(input);
            cost += getOutputCost(output, expectedOutput);
        }

        this.cost = cost;
    }

    private double getOutputCost(double[][] output, double[][] expectedOutput) {
        double sum = 0;
        int n = output.length;
        for (int i = 0; i < n; i++) {
            sum += Math.pow(expectedOutput[i][0] - output[i][0], 2);
        }

        return sum / n;
    }

    private void applyActivationFunction(double[][] A) {
        Matrix.applyTransformation(A, NeuralNetwork::activationFunction);
    }

    /* SIGMOID ACTIVATION FUNCTION */
    public static double activationFunction(double x) {
        return 1.0D / (1 + Math.pow(Math.E, -x));
    }

    public static double derivativeActivationFunction(double x) {
        double y = activationFunction(x);
        return y * (1 - y);
    }

    /* GETTERS AND SETTERS */
    public double getCost() {
        return cost;
    }

}
