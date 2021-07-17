import neural_network.NeuralNetwork;
import neural_network.activation.SigmoidFunction;
import neural_network.bias.RandomBias;
import neural_network.training.TrainingData;
import neural_network.weights.RandomWeightsInit;

public class MainClass {

    public static void main(String... args) {

        NeuralNetwork neuralNetwork;

        TrainingData trainingData = new TrainingData();
        trainingData.add(new double[]{0, 0}, new double[]{0});
        trainingData.add(new double[]{1, 0}, new double[]{1});
        trainingData.add(new double[]{0, 1}, new double[]{1});
        trainingData.add(new double[]{1, 1}, new double[]{0});

        neuralNetwork = new NeuralNetwork(
                new int[]{2, 8, 1},
                new SigmoidFunction(),
                new RandomWeightsInit(),
                new RandomBias()
        );

        for (int i = 0; i < 2000; i++) {
            neuralNetwork.train(trainingData);
        }

        neuralNetwork.export();

        neuralNetwork.showPredictions(trainingData);

    }

}
