import mnist.Image;
import mnist.MnistDecompressedReader;
import neural_network.NeuralNetwork;
import neural_network.activation.SigmoidFunction;
import neural_network.bias.RandomBias;
import neural_network.cost.SimpleCost;
import neural_network.training.TrainingData;
import neural_network.weights.RandomWeightsInit;
import processing.core.PApplet;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;

public class MainProcessing extends PApplet {

    private final NeuralNetwork neuralNetwork = new NeuralNetwork(
            new int[]{28 * 28, 200, 10},
            new SigmoidFunction(),
            new RandomWeightsInit(),
            new RandomBias(),
            new SimpleCost()
    );

    private CanvasImage canvasImage;

    public void settings() {
        size(392, 392);
    }

    public void setup() {
        background(25);
        frameRate(30);
        strokeCap(ROUND);
        shapeMode(CENTER);

        canvasImage = new CanvasImage(this);

        TrainingData trainingData = new TrainingData();

        /* Get training data */
        MnistDecompressedReader mnistReader = new MnistDecompressedReader();
        try {
            mnistReader.readDecompressedTraining(Paths.get("./data"), mnistEntry -> {
                int label = mnistEntry.getLabel();
                BufferedImage image = mnistEntry.createImage();

                Image img = new Image(image, label);
                double[] input = img.getImageAsInput();
                double[] output = img.getLabelAsOutput();

                trainingData.add(input, output);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        neuralNetwork.setLearningRate(0.1);

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                neuralNetwork.train(trainingData);
                neuralNetwork.export();

                System.out.printf("Epoch %5d -> Loss %10.4f | Learning Rate %5.3f \n", i, neuralNetwork.getLoss(), neuralNetwork.getLearningRate());
            }
        }).start();
    }

    @Override
    public void draw() {
        background(25);
        noStroke();

    }

    @Override
    public void mouseDragged() {
        canvasImage.mouseDragged();
    }

    @Override
    public void mouseReleased() {
        double[] input = canvasImage.getImageAsVector();

        double[] output = neuralNetwork.feedForward(input);

        int iMax = 10;
        double iValue = 0;
        for (int i = 0; i < 10; i++) {
            if (iValue < output[i]) {
                iValue = output[i];
                iMax = i;
            }
        }

        System.out.println(iMax);

        canvasImage.clear();
    }

    public static void main(String... args){
        PApplet.main("MainProcessing");
    }
}