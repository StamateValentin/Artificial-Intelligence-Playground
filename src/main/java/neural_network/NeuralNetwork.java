package neural_network;

import neural_network.exceptions.InvalidInputException;
import neural_network.activation.TanhFunction;
import neural_network.weights.WeightsInit;
import neural_network.weights.RandomWeightsInit;
import neural_network.matrix.Matrix;
import neural_network.activation.ActivationFunction;
import neural_network.activation.SigmoidFunction;

/* TODO: BIAS */
public class NeuralNetwork {
    private final double learningRate = 0.07;

    private ActivationFunction activationFunction;
    private WeightsInit weightsInit;

    private final double[][][] brain;
    private double cost = 0;

    public NeuralNetwork(int[] layerDimension) {
        this.brain = new double[layerDimension.length - 1][][];
        this.activationFunction = new TanhFunction();
        this.weightsInit = new RandomWeightsInit();

        for (int i = 0; i < brain.length; i++) {
            int n = layerDimension[i + 1];
            int m = layerDimension[i];
            brain[i] = weightsInit.create(n, m);
        }
    }

    public NeuralNetwork(int[] layerDimension, ActivationFunction activationFunction) {
        this(layerDimension);

        this.activationFunction = activationFunction;
    }

    /* FOR DEBUG MODE */
    public NeuralNetwork(double[][][] brain) {
        this.brain = brain;
        this.activationFunction = new SigmoidFunction();
        this.weightsInit = new RandomWeightsInit();
    }

    public double[][] feedForward(final double[][] input) throws InvalidInputException {
        double[][] currentInput = input;

        for (int i = 0; i < brain.length; i++) {
            double[][] weights = brain[i];

//            Matrix.print(weights);

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
                    layer[k][l] = layer[k][l] + (- learningRate * (- slopeMatrix[k][l]));
                }
            }

            currentOutput = previousOutput;
            currentError = previousError;
        }
    }

    public void backPropagationDebug(double[][] input, double[][] expected) {
        System.out.println("Inside:");
        double[][][] outputs = getOutputs(input);

        double[][] currentOutput = outputs[outputs.length - 1];
        double[][] currentError = Matrix.subtract(expected, currentOutput);

        System.out.println("Inside Current Output");
        Matrix.print(currentOutput);
        System.out.println("Inside Current Error");
        Matrix.print(currentError);

        for (int i = brain.length - 1; i >= 0; i--) {
            System.out.println("Iteration " + i);
            final double[][] layer = brain[i];
            System.out.println("Layer");
            Matrix.print(layer);
            final double[][] previousOutput = outputs[i];
            System.out.println("Previous Output");
            Matrix.print(previousOutput);

            final double[][] layerTranspose = Matrix.transpose(layer);

            final double[][] previousError = Matrix.multiply(layerTranspose, currentError);
            System.out.println("Previous error");
            Matrix.print(previousError);

            /* FIST BIT */
            double[][] errorSigmoid = Matrix.copyOf(currentError);

            for (int k = 0; k < errorSigmoid.length; k++) {
                errorSigmoid[k][0] *= - activationFunction.slope(currentOutput[k][0]);
            }

            System.out.println("Error sigmoid");
            Matrix.print(errorSigmoid);

            /* SECOND BIT */
            final double[][] slopeMatrix = Matrix.multiply(errorSigmoid, Matrix.transpose(previousOutput));

            System.out.println("Slope neural_network.matrix");
            Matrix.print(slopeMatrix);

            /* UPDATE THE WEIGHTS */
            for (int k = 0; k < layer.length; k++) {
                for (int l = 0; l < layer[0].length; l++) {
                    System.out.println("Initial:" + layer[k][l]);
                    System.out.println("Value: " + learningRate * slopeMatrix[k][l]);
                    layer[k][l] = layer[k][l] - learningRate * slopeMatrix[k][l];
                    System.out.println("After: " + layer[k][l]);
                }
            }

            System.out.println("After update");
            Matrix.print(layer);

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
            double[][] input = Matrix.copyOf(inputs[i]);
            double[][] expectedOutput = expectedOutputs[i];

            if (input[0][0] > 0.7 && expectedOutput[0][0] == 1.0) {
                continue;
            }

            if (input[0][0] <= 0.7 && expectedOutput[0][0] == 0.0) {
                continue;
            }

            backPropagation(input, expectedOutput);

            double[][] output = feedForward(input);
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
        Matrix.applyTransformation(A, activationFunction::fun);
    }

    /* GETTERS AND SETTERS */
    public double getCost() {
        return cost;
    }

    public double[][][] getBrainCopy() {
        int n = brain.length;

        double[][][] brainCopy = new double[n][][];

        for (int i = 0; i < n; i++) {
            brainCopy[i] = Matrix.copyOf(brain[i]);
        }

        return brainCopy;
    }
}
