package neural_network;

import neural_network.bias.BiasInit;
import neural_network.bias.RandomBias;
import neural_network.color.Color;
import neural_network.activation.TanhFunction;
import neural_network.weights.WeightsInit;
import neural_network.weights.RandomWeightsInit;
import neural_network.matrix.Matrix;
import neural_network.activation.ActivationFunction;

public class NeuralNetwork {
    private static final double LEARNING_RATE = 0.07;

    private final double[][][] brain;
    private final double[][][] biases;

    private final ActivationFunction activationFunction;
    private final WeightsInit weightsInit;
    private final BiasInit biasInit;

    public NeuralNetwork(int[] layerDimension) {
        this.activationFunction = new TanhFunction();
        this.weightsInit = new RandomWeightsInit();
        this.biasInit = new RandomBias();

        int len = layerDimension.length;

        this.brain = new double[len - 1][][];
        this.biases = new double[len - 1][][];

        initBias(layerDimension);
        initBrain(layerDimension);
    }

    public NeuralNetwork(int[] layerDimension, ActivationFunction activationFunction, WeightsInit weightsInit, BiasInit biasInit) {
        this.activationFunction = activationFunction;
        this.weightsInit = weightsInit;
        this.biasInit = biasInit;

        int len = layerDimension.length;

        this.brain = new double[len - 1][][];
        this.biases = new double[len - 1][][];

        initBias(layerDimension);
        initBrain(layerDimension);
    }

    /* INITIALIZATION */
    private void initBias(int[] layerDimension) {
        for (int i = 0; i < biases.length; i++) {
            int laterSize = layerDimension[i + 1];
            biases[i] = biasInit.generate(laterSize);
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
    public double[][] feedForward(final double[][] input) {
        double[][] currentInput = input;

        int loops = brain.length;

        for (int i = 0; i < loops; i++) {
            double[][] weights = brain[i];

            currentInput = Matrix.multiply(weights, currentInput);

            if (i != 0) {
                currentInput = Matrix.add(currentInput, biases[i]);
            }

            applyActivationFunction(currentInput);
        }

        return currentInput;
    }

    public void backPropagation(double[][] input, double[][] target, double[][] expected) {
        double[][] currentError = Matrix.subtract(target, expected);

    }

    private void applyActivationFunction(double[][] A) {
        Matrix.applyTransformation(A, activationFunction::fun);
    }

    /* GETTERS AND SETTERS */
    public void printBrain() {
        int n = brain.length;

        System.out.println(Color.GREEN_BOLD + "---=== Neural Network Brain ===---" + Color.RESET);

        for (int i = 0; i < n; i++) {
            System.out.printf("Layer: %d - %d\n", i, i + 1);
            Matrix.print(brain[i]);
        }

        System.out.println("");
    }

    public void printBiases() {
        int n = biases.length;

        System.out.println(Color.GREEN_BOLD + "---=== Neural Network Biases ===---" + Color.RESET);

        for (int i = 0; i < n; i++) {
            System.out.printf("Layer: %d\n", i + 1);
            Matrix.print(biases[i]);
        }

        System.out.println("");
    }
}
