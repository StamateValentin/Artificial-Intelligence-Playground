import exceptions.InvalidInputException;
import function.Function;
import function.FunctionDrawer;
import neural_network.NeuralNetwork;
import processing.core.PApplet;
import java.util.ArrayList;
import java.util.List;

public class Main extends PApplet {

    private final List<Point> points = new ArrayList<>();
    private NeuralNetwork neuralNetwork;
    private Function function;

    public void settings() {
        size(980, 600);
    }

    public void setup() {
        background(25);
        frameRate(30);
        strokeCap(ROUND);
        shapeMode(CENTER);

//        this.function = (x) -> (int)(0.1 * x * x + 6.7 * x);
        this.function = (x) -> (2 * x + 5);

        new FunctionDrawer(this, function);
        neuralNetwork = new NeuralNetwork(new int[]{2, 1});

        for (int i = 0; i < 200; i++) {
            points.add(new Point(this, function));
        }

        Thread thread = new Thread(this::train);
        thread.start();

    }

    private void train() {
        while (true) {

            int n = points.size();

            double[][][] inputs = new double[n][][];
            double[][][] expectedOutputs = new double[n][][];

            for (int i = 0; i < n; i++) {
                Point point = points.get(i);

                double[][] input = new double[2][1];
                input[0][0] = normalizeX(point.getX());
                input[1][0] = normalizeY(point.getY());

                double[][] expectedOutput = new double[1][1];
                expectedOutput[0][0] = point.isRealAbove() ? 1.0 : 0.0;

                inputs[i] = input;
                expectedOutputs[i] = expectedOutput;
            }

            try {
                neuralNetwork.trainIteration(inputs, expectedOutputs);
            } catch (InvalidInputException e) {
                e.printStackTrace();
            }

            System.out.printf("%.4f\n", neuralNetwork.getCost());

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void refreshPoints() {

        for (Point point : points) {
            double[][] input = new double[2][1];
            input[0][0] = normalizeX(point.getX());
            input[1][0] = normalizeY(point.getY());

            try {
                double[][] output = neuralNetwork.getOutput(input);

                point.setAbove(output[0][0] > 0.5);

            } catch (InvalidInputException e) {
                e.printStackTrace();
            }
        }
    }

    private double normalizeX(int x) {
        x += width / 2;
        return (1.0 * x) / width;
    }

    private double normalizeY(int y) {
        y += height / 2;
        return (1.0 * y) / height;
    }

    public void draw() {
        background(25);

        pushMatrix();
        translate(width / 2, height / 2);

        refreshPoints();

        stroke(255);
        line(- width / 2, 0, width / 2, 0);
        line(0, height / 2, 0, - height / 2);

        popMatrix();
    }

    public static void main(String... args){
        PApplet.main("Main");
    }
}