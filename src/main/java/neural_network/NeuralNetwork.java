package neural_network;

import neural_network.activation.ActivationFunction;
import neural_network.activation.TanhFunction;
import neural_network.bias.BiasInit;
import neural_network.bias.RandomBias;
import neural_network.color.Color;
import neural_network.cost.CostFunction;
import neural_network.cost.SimpleCost;
import neural_network.loss.LossFunction;
import neural_network.loss.MeanSquaredError;
import neural_network.math.Matrix;
import neural_network.math.Vector;
import neural_network.training.TrainingData;
import neural_network.weights.RandomWeightsInit;
import neural_network.weights.WeightsInit;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NeuralNetwork {
    private double learningRate = 0.1;
    private static final String EXPORT_FILE = "snapshot.txt";

    private final double[][][] brain;
    private final double[][][] biases;
    private double loss = Integer.MAX_VALUE;

    private final ActivationFunction activationFunction;
    private final WeightsInit weightsInit;
    private final BiasInit biasInit;
    private final CostFunction costFunction;
    private final LossFunction lossFunction;

    /** Get the layer sizes and initializes the rest of the element with their default values */
    public NeuralNetwork(int[] layerDimension) {
        this.activationFunction = new TanhFunction();
        this.weightsInit = new RandomWeightsInit();
        this.biasInit = new RandomBias();
        this.costFunction = new SimpleCost();
        this.lossFunction = new MeanSquaredError();

        int len = layerDimension.length;

        this.brain = new double[len - 1][][];
        this.biases = new double[len - 1][][];

        initBias(layerDimension);
        initBrain(layerDimension);
    }

    public NeuralNetwork(int[] layerDimension, ActivationFunction activationFunction, WeightsInit weightsInit, BiasInit biasInit, CostFunction costFunction) {
        this.activationFunction = activationFunction;
        this.weightsInit = weightsInit;
        this.biasInit = biasInit;
        this.costFunction = costFunction;
        this.lossFunction = new MeanSquaredError();

        int len = layerDimension.length;

        this.brain = new double[len - 1][][];
        this.biases = new double[len][][];

        initBias(layerDimension);
        initBrain(layerDimension);
    }

    /** Initializes the neural network with the given parameters and import the
     * weights and biases from the exported file */
    public NeuralNetwork(ActivationFunction activationFunction, WeightsInit weightsInit, BiasInit biasInit, CostFunction costFunction) throws FileNotFoundException {
        this.activationFunction = activationFunction;
        this.weightsInit = weightsInit;
        this.biasInit = biasInit;
        this.costFunction = costFunction;
        this.lossFunction = new MeanSquaredError();

        Path path = Paths.get(EXPORT_FILE);

        Scanner scanner = new Scanner(path.toFile());

        int layers = scanner.nextInt();
        brain = new double[layers][][];
        biases = new double[layers + 1][][];

        for (int l = 0; l < layers; l++) {
            brain[l] = Matrix.readMatrix(scanner);
        }

        for (int l = 0; l < layers + 1; l++) {
            biases[l] = Matrix.readMatrix(scanner);
        }

        scanner.close();
    }

    /* INITIALIZATION */
    private void initBias(int[] layerDimension) {
        for (int i = 0; i < biases.length; i++) {
            int layer = layerDimension[i];
            biases[i] = biasInit.generate(layer);

            if (i == 0) {
                biases[i] = new double[layer][1];
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
    public double[] feedForward(final double[] input) {
        double[][] currentInput = Matrix.toColumnMatrix(input);

        int loops = brain.length;

        for (int i = 0; i < loops; i++) {
            double[][] weights = brain[i];
            double[][] biases = this.biases[i + 1];

            currentInput = Matrix.dotProduct(weights, currentInput);
            currentInput = Matrix.add(currentInput, biases);

            currentInput = Matrix.map(currentInput, activationFunction::fun);
        }

        return Matrix.lineMatrixToVector(currentInput);
    }

    /** Backpropagation.
     * The formula is as follows: DeltaBias = learningRate * (currentError * derr(output))^ * tr(input)
     *                            DeltaWeights = DeltaBias * tr(input)
     * The ^ means element wise multiplication
     * */
    public void backPropagation(double[] initialInput, double[] target) {
        int layers = brain.length + 1;

        double[][] matrixInput = Matrix.toColumnMatrix(initialInput);
        double[][] matrixTarget = Matrix.toColumnMatrix(target);


        double[][][] rawInputs = getRawInputs(matrixInput);
        double[][][] inputs = getInputs(matrixInput);
        double[][][] errors = getErrors(costFunction.cost(matrixTarget, inputs[layers - 1]));

        for (int i = layers - 1; i >= 1; i--) {
            double[][] rawOutput = rawInputs[i];
            double[][] inputTransposed = Matrix.transpose(inputs[i - 1]);;
            double[][] error = errors[i];


            double[][] gradients = Matrix.map(rawOutput, activationFunction::slope);
            double[][] deltaWeights = Matrix.hadamardProduct(error, gradients);

            deltaWeights = Matrix.map(deltaWeights, (x) -> x * learningRate);

            double[][] deltaBiases = Matrix.copyOf(deltaWeights);

            deltaWeights = Matrix.dotProduct(deltaWeights, inputTransposed);

            brain[i - 1] = Matrix.add(brain[i - 1], deltaWeights);
            biases[i] = Matrix.add(biases[i], deltaBiases);
        }

    }

    public void train(TrainingData trainingData) {
        List<Double> errorData = new ArrayList<>();

        trainingData.randomLoop(((input, target) -> {
            double[] output = feedForward(input);

            double[] errors = Vector.map(Vector.difference(target, output), x -> x * x);
            double error = Vector.mean(errors);

            errorData.add(error);

            backPropagation(input, target);
        }));

        loss = lossFunction.calculate(errorData);
    }

    public void trainWithDelay(TrainingData trainingData, int delay) {
        List<Double> errorData = new ArrayList<>();

        trainingData.randomLoop(((input, target) -> {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            double[] output = feedForward(input);

            double[] errors = Vector.map(Vector.difference(target, output), x -> x * x);
            double error = Vector.mean(errors);

            errorData.add(error);

            backPropagation(input, target);
        }));

        loss = lossFunction.calculate(errorData);
    }

    private double[][][] getInputs(double[][] input) {
        int layers = brain.length + 1;

        double[][][] inputs = new double[layers][][];

        inputs[0] = Matrix.copyOf(input);

        for (int i = 1; i < layers; i++) {
            double[][] weights = brain[i - 1];
            double[][] biases = this.biases[i];

            inputs[i] = Matrix.dotProduct(weights, inputs[i - 1]);

            inputs[i] = Matrix.add(inputs[i], biases);
            inputs[i] = Matrix.map(inputs[i], activationFunction::fun);
        }

        return inputs;
    }

    private double[][][] getRawInputs(double[][] input) {
        int layers = brain.length + 1;

        double[][][] inputs = new double[layers][][];
        double[][][] rawInputs = new double[layers][][];

        inputs[0] = Matrix.copyOf(input);
        rawInputs[0] = Matrix.copyOf(input);

        for (int i = 1; i < layers; i++) {
            double[][] weights = brain[i - 1];
            double[][] biases = this.biases[i];

            inputs[i] = Matrix.dotProduct(weights, inputs[i - 1]);
            inputs[i] = Matrix.add(inputs[i], biases);

            rawInputs[i] = Matrix.copyOf(inputs[i]);
            inputs[i] = Matrix.map(inputs[i], activationFunction::fun);
        }

        return rawInputs;
    }

    private double[][][] getErrors(double[][] error) {
        int layers = brain.length + 1;

        double[][][] errors = new double[layers][][];
        errors[layers - 1] = Matrix.copyOf(error);

        for (int i = layers - 2; i >= 0; i--) {
            double[][] weightsTransposed = Matrix.transpose(brain[i]);

            errors[i] = Matrix.dotProduct(weightsTransposed, errors[i + 1]);
        }

        return errors;
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
            System.out.printf("Layer: %d\n", i);
            Matrix.print(biases[i]);
        }

        System.out.println("");
    }

    /** Exports the neural network data into a file.
     * This is useful after training the neural network. */
    public void export() {
        Path path = Paths.get(EXPORT_FILE);
        Path pathReadable = Paths.get("snapshot_readable.txt");

        try {
            FileWriter fileReadableWriter = new FileWriter(pathReadable.toFile());
            FileWriter fileWriter = new FileWriter(path.toFile());

            int len = brain.length;

            fileReadableWriter.write(String.format("Layers              : %d \n", len));
            fileReadableWriter.write(String.format("Activation Function : %s \n", activationFunction.getName()));
            fileReadableWriter.write(String.format("Weights Init        : %s \n", weightsInit.getName()));
            fileReadableWriter.write(String.format("Bias Init           : %s \n", biasInit.getName()));
            fileReadableWriter.write(String.format("Cost Function       : %s \n", costFunction.getName()));
            fileReadableWriter.write(String.format("Loss Function       : %s \n", lossFunction.getName()));
            fileReadableWriter.write("\n");
            fileReadableWriter.write("---=== Neural Network Weights ===---\n\n");

            fileWriter.write(len + "\n");

            for (int l = 0; l < len; l++) {
                fileReadableWriter.write("Layer " + l + "\n");

                Matrix.printToFile(fileWriter, brain[l]);
                Matrix.printToFile(fileReadableWriter, brain[l]);
            }

            fileReadableWriter.write("---=== Neural Network Biases ===---\n\n");

            for (int l = 0; l < len + 1; l++) {
                fileReadableWriter.write("Layer " + l + "\n");

                Matrix.printToFile(fileWriter, biases[l]);
                Matrix.printToFile(fileReadableWriter, biases[l]);
            }

            fileReadableWriter.flush();
            fileReadableWriter.close();

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Having a training set, it shows the predictions of the neural network */
    public void showPredictions(TrainingData trainingData) {
        trainingData.loop((input, target) -> {
            double[] output = feedForward(input);

            System.out.println("Output:");
            Matrix.print(output);
            System.out.println("Expected:");
            Matrix.print(Matrix.toColumnMatrix(target));
            System.out.println("\n");
        });
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public double getLoss() {
        return loss;
    }
}
