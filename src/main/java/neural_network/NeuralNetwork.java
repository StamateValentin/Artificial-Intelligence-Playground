package neural_network;

import exceptions.InvalidInputException;
import matrix.Matrix;

import java.util.Arrays;

/* TODO: BIAS */
public class NeuralNetwork {
    private final double learningRate = 0.05;

    private final double[][][] brain;

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
            normalizeMatrix(currentInput);
        }

        return currentInput;
    }

    public void backPropagation(double[][] output, double[][] expected) {
        double[][] currentOutput = output;
        double[][] currentError = Matrix.subtract(expected, output);

        for (int i = brain.length - 1; i >= 0; i--) {

            final double[][] layer = brain[i];
            final double[][] layerTranspose = Matrix.transpose(layer);

            final double[][] previousError = Matrix.multiply(layerTranspose, currentError);
            final double[][] previousOutput = Matrix.multiply(layerTranspose, currentOutput);

            /* FIST BIT */
            double[][] errorSigmoid = Arrays.copyOf(currentError, currentError.length);

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

    private void normalizeMatrix(double[][] A) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                A[i][j] = activationFunction(A[i][j]);
            }
        }
    }

    /* SIGMOID ACTIVATION FUNCTION */
    public static double activationFunction(double x) {
        x = NeuralNetworkUtil.normalizeX(x);
        return 1.0D / (1 + Math.pow(Math.E, -x));
    }

    public static double derivativeActivationFunction(double x) {
        double y = activationFunction(x);
        return y * (1 - y);
    }

}
