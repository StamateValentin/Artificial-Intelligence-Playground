package neural_network;

import neural_network.color.Color;
import neural_network.exceptions.InvalidInputException;
import neural_network.activation.TanhFunction;
import neural_network.util.Util;
import neural_network.weights.WeightsInit;
import neural_network.weights.RandomWeightsInit;
import neural_network.matrix.Matrix;
import neural_network.activation.ActivationFunction;
import neural_network.activation.SigmoidFunction;

public class NeuralNetwork {
    private static final double LEARNING_RATE = 0.07;

    private double[][][] brain;
    private double[][] biases;

    private ActivationFunction activationFunction;
    private WeightsInit weightsInit;

    public NeuralNetwork(int[] layerDimension) {
        this.activationFunction = new TanhFunction();
        this.weightsInit = new RandomWeightsInit();

        int len = layerDimension.length;

        this.brain = new double[len - 1][][];
        this.biases = len == 2 ? null : new double[len - 2][];

        if (biases != null) {
            initBias(layerDimension);
        }

        initBrain(layerDimension);
    }

    public NeuralNetwork(int[] layerDimension, ActivationFunction activationFunction) {
        this(layerDimension);

        this.activationFunction = activationFunction;
    }

    /* FOR DEBUG MODE */
    public NeuralNetwork(double[][][] brain) {
        this(new int[]{1, 2});
        this.brain = brain;
        this.activationFunction = new SigmoidFunction();
        this.weightsInit = new RandomWeightsInit();
    }

    /* INITIALIZATION */
    private void initBias(int[] layerDimension) {
        for (int i = 0; i < biases.length; i++) {
            int laterSize = layerDimension[i];
            biases[i] = new double[laterSize];

            for (int l = 0; l < laterSize; l++) {
                biases[i][l] = Util.generateRandom(0, 1);
            }
        }
    }

    private void initBrain(int[] layerDimension) {
        for (int i = 0; i < brain.length; i++) {
            int n = layerDimension[i + 1];
            int m = layerDimension[i];
            brain[i] = weightsInit.create(n, m);
        }
    }

    /**/
    public double[][] feedForward(final double[][] input) throws InvalidInputException {
        double[][] currentInput = input;

        for (int i = 0; i < brain.length; i++) {
            double[][] weights = brain[i];

            currentInput = Matrix.multiply(weights, currentInput);
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
                errorSigmoid[k][0] *= activationFunction.slope(currentOutput[k][0]);
            }

            /* SECOND BIT */
            final double[][] slopeMatrix = Matrix.multiply(errorSigmoid, Matrix.transpose(previousOutput));

            /* UPDATE THE WEIGHTS */
            for (int k = 0; k < layer.length; k++) {
                for (int l = 0; l < layer[0].length; l++) {
                    layer[k][l] = layer[k][l] + (-LEARNING_RATE * (- slopeMatrix[k][l]));
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

        for (int i = 0; i < n; i++) {
            double[][] input = Matrix.copyOf(inputs[i]);
            double[][] expectedOutput = expectedOutputs[i];

            if (input[0][0] > 0.7 && expectedOutput[0][0] == 1.0) {
                continue;
            }

            if (input[0][0] <= 0.7 && expectedOutput[0][0] == 0.0) {
                continue;
            }

            backPropagation(input, expectedOutput);
        }
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
        Matrix.applyTransformation(A, activationFunction::fun);
    }

    /* GETTERS AND SETTERS */
    public void printBrain() {
        int n = brain.length;

        System.out.println(Color.GREEN_BOLD + "---=== Neural Network Brain ===---" + Color.RESET);

        for (int i = 0; i < n; i++) {
            System.out.printf("Layer: %d\n", i);
            Matrix.println(brain[i]);
        }

        System.out.println("");
    }

    public void printBiases() {
        int n = biases.length;

        System.out.println(Color.GREEN_BOLD + "---=== Neural Network Biases ===---" + Color.RESET);

        System.out.print(Color.BLUE_BOLD);
        for (int i = 0; i < n; i++) {
            System.out.printf(Color.RESET + "Layer: %d\n" + Color.BLUE_BOLD, i + 1);

            int m = biases[i].length;

            for (int j = 0; j < m; j++) {
                System.out.printf("| %7.3f |\n", biases[i][j]);
            }
        }

        System.out.println("");
        System.out.println(Color.RESET);
    }
}
