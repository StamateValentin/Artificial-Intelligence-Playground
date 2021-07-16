import neural_network.NeuralNetwork;
import neural_network.activation.SigmoidFunction;
import neural_network.bias.RandomBias;
import neural_network.matrix.Matrix;
import neural_network.weights.RandomWeightsInit;

public class MainClass {

    public static void main(String... args) {

        NeuralNetwork neuralNetwork = new NeuralNetwork(
                new int[]{4, 2, 1},
                new SigmoidFunction(),
                new RandomWeightsInit(),
                new RandomBias()
        );

        neuralNetwork.printBrain();
        neuralNetwork.printBiases();

        double[][] input = Matrix.toColumnMatrix(new double[]{0.4, 0.6, 0.1, 0.7});
        double[][] output = neuralNetwork.feedForward(input);
        double[][] expectedOutput = Matrix.toColumnMatrix(new double[]{1.0});

        neuralNetwork.backPropagation(input, expectedOutput);

        System.out.println("Output");
        Matrix.print(output);
    }

}
