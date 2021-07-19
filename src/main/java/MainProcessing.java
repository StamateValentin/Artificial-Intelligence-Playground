import neural_network.NeuralNetwork;
import neural_network.activation.SigmoidFunction;
import neural_network.bias.RandomBias;
import neural_network.cost.SimpleCost;
import neural_network.training.TrainingData;
import neural_network.weights.RandomWeightsInit;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class MainProcessing extends PApplet {

    private List<Point> points = new ArrayList<>();
    private Function function;

    private final NeuralNetwork neuralNetwork = new NeuralNetwork(
            new int[]{2, 4, 2, 1},
            new SigmoidFunction(),
            new RandomWeightsInit(),
            new RandomBias(),
            new SimpleCost()
    );

    public void settings() {
        size(980, 600);
    }

    public void setup() {
        background(25);
        frameRate(30);
        strokeCap(ROUND);
        shapeMode(CENTER);

        this.function = (x) -> (int) (0.002 * (x - 300) * (x + 400));
        new FunctionDrawer(this, function);

        TrainingData trainingData = new TrainingData();

        for (int i = 0; i < 500; i++) {
            Point point = new Point(this, function);
            double normalizedX = point.getNormalizedX();
            double normalizedY = point.getNormalizedY();

            double[] input = new double[]{normalizedX, normalizedY};
            double[] target = new double[1];
            target[0] = point.getLabel() ? 1 : 0;

            trainingData.add(input, target);
            points.add(point);
        }

        neuralNetwork.setLearningRate(0.007);

        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                neuralNetwork.train(trainingData);
                neuralNetwork.export();

//                System.out.printf("Epoch %5d -> Loss %10.4f | Learning Rate %5.3f \n", i, neuralNetwork.getLoss(), neuralNetwork.getLearningRate());
            }
        }).start();
    }

    public void draw() {
        background(25);
        noStroke();

        int squareSize = 10;

        int cols = width / squareSize;
        int rows = height / squareSize;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double nX = Point.getNormalizedX(j * squareSize - width / 2, width);
                double nY = Point.getNormalizedY((rows - i - 1) * squareSize - height / 2, height);

                double[] input = new double[]{nX, nY};
                double[] output = neuralNetwork.feedForward(input);

                Color col = Color.colorMix(Color.RED, Color.GREEN, output[0]);
                noStroke();

                fill(col.r, col.g, col.b);
                rect(j * squareSize, i * squareSize, squareSize, squareSize);
            }
        }

        refreshPoints();
    }

    private void refreshPoints() {

        for (Point point : points) {
            double[] input = new double[2];
            input[0] = point.getNormalizedX();
            input[1] = point.getNormalizedY();

            double[] output = neuralNetwork.feedForward(input);

            boolean isAbove = output[0] >= 0.5;

            point.setGuessedLabel(isAbove);
        }
    }

    public static void main(String... args){
        PApplet.main("MainProcessing");
    }
}