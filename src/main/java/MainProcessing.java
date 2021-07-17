import neural_network.NeuralNetwork;
import neural_network.activation.SigmoidFunction;
import neural_network.bias.RandomBias;
import neural_network.training.TrainingData;
import neural_network.weights.RandomWeightsInit;
import processing.core.PApplet;

public class MainProcessing extends PApplet {

    private final NeuralNetwork neuralNetwork = new NeuralNetwork(
            new int[]{2, 4, 4, 1},
            new SigmoidFunction(),
            new RandomWeightsInit(),
            new RandomBias()
    );

    public void settings() {
        size(980, 600);
    }

    public void setup() {
        background(25);
        frameRate(30);
        strokeCap(ROUND);
        shapeMode(CENTER);

        TrainingData trainingData = new TrainingData();
        trainingData.add(new double[]{0, 0}, new double[]{0});
        trainingData.add(new double[]{1, 0}, new double[]{1});
        trainingData.add(new double[]{0, 1}, new double[]{1});
        trainingData.add(new double[]{1, 1}, new double[]{0});

        neuralNetwork.setLearningRate(0.8);

        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                neuralNetwork.train(trainingData);
                neuralNetwork.export();
            }
        }).start();
    }

    public void draw() {
        background(25);
        noStroke();

        int cellSize = 10;

        int cols = width / cellSize;
        int rows = height / cellSize;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double x1 = (1.0 * i) / rows;
                double x2 = (1.0 * j) / cols;

                double output = neuralNetwork.feedForward(new double[]{x1, x2})[0];

                fill((int) (output * 255));
                rect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }

    }

    public static void main(String... args){
        PApplet.main("MainProcessing");
    }
}