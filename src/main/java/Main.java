import exceptions.InvalidInputException;
import function.Function;
import function.FunctionDrawer;
import neural_network.NeuralNetwork;
import neural_network.matrix.Matrix;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.*;

public class Main extends PApplet {

    private final List<Point> points = new ArrayList<>();
    private final NeuralNetwork neuralNetwork = new NeuralNetwork(new int[]{2, 1});
    private Function function;

    public void settings() {
        size(980, 600);
    }

    public void setup() {
        background(25);
        frameRate(30);
        strokeCap(ROUND);
        shapeMode(CENTER);

//        this.function = (x) -> (x * x) - 28000;
//        this.function = (x) -> (2 * x + -400);
        this.function = (x) -> (-x);

        new FunctionDrawer(this, function);

        for (int i = 0; i < 255; i++) {
            points.add(new Point(this, function));
        }

        Thread thread = new Thread(() -> {
            try {
                train();
            } catch (InvalidInputException e) {
                e.printStackTrace();
            }
        });
        thread.start();

    }

    private void train() throws InvalidInputException {
        while (true) {

            int n = points.size();

            double[][][] inputs = new double[n][][];
            double[][][] expectedOutputs = new double[n][][];

            Collections.shuffle(points);

            for (int i = 0; i < n; i++) {
                Point point = points.get(i);

                double[][] input = new double[2][1];
                input[0][0] = normalizeX(point.getX());
                input[1][0] = normalizeY(point.getY());

                double[][] expectedOutput = new double[1][1];
                expectedOutput[0][0] = point.isRealAbove() ? 1.0 : -1.0;

//                if (!point.isRealAbove() && point.isAbove() && point.getY() > 0) {
//                    double[][] output = neuralNetwork.getOutput(input);
//
//                    System.out.println(point);
//                    System.out.println("Output before:");
//                    Matrix.print(output);
//                    if (output[0][0] > 0.7) {
//                        System.out.print("Prediction: above\n");
//                    }
//                    System.out.println("Input");
//                    Matrix.print(input);
//                    System.out.println("Output");
//                    Matrix.print(output);
//                    System.out.println("Expected Output");
//                    Matrix.print(expectedOutput);
//                    System.out.println("Error");
//                    Matrix.print(Matrix.subtract(expectedOutput, output));
//
//                    neuralNetwork.backPropagationDebug(input, expectedOutput);
//                    output = neuralNetwork.getOutput(input);
//                    System.out.println("After backpropagation");
//                    Matrix.print(output);
//
//                    System.out.println("");
//
//                }

                inputs[i] = input;
                expectedOutputs[i] = expectedOutput;
            }

            neuralNetwork.trainIteration(inputs, expectedOutputs);

//            System.out.printf("%.4f\n", neuralNetwork.getCost());

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void refreshPoints() {

        for (Point point : points) {
            double[][] input = new double[2][1];
            input[0][0] = point.getX();
            input[1][0] = point.getY();

            try {
//                System.out.println("Point");
//                Matrix.print(input);
                double[][] output = neuralNetwork.feedForward(input);
//                Matrix.print(output);
//                System.out.println("");
                point.setAbove(output[0][0] > 0);

            } catch (InvalidInputException e) {
                e.printStackTrace();
            }
        }
    }

    private double normalizeX(int x) {
        x += width / 2;
        return (1.0 * x) / width;
    }

    private double repositionY(double y) {
        if (y < - height / 2) {
            return - height / 2 + 10;
        }

        if (y > height / 2) {
            return height / 2 - 10;
        }

        return y;
    }

    private double normalizeY(int y) {
        y += height / 2;
        return (1.0 * y) / height;
    }

    public void draw() {
        pushMatrix();
        translate(width / 2, height / 2);
        rotate(PConstants.PI);
        scale(-1,1);

        background(25);

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