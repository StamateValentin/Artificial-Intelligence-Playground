import neural_network.NeuralNetwork;
import processing.core.PApplet;
import processing.core.PConstants;

public class Main extends PApplet {

    private final NeuralNetwork neuralNetwork = new NeuralNetwork(new int[]{2, 1});

    public void settings() {
        size(980, 600);
    }

    public void setup() {
        background(25);
        frameRate(30);
        strokeCap(ROUND);
        shapeMode(CENTER);
    }

    public void draw() {
        pushMatrix();
        translate(width / 2, height / 2);
        rotate(PConstants.PI);
        scale(-1,1);

        background(25);

        stroke(255);
        line(- width / 2, 0, width / 2, 0);
        line(0, height / 2, 0, - height / 2);

        popMatrix();
    }

    public static void main(String... args){
        PApplet.main("Main");
    }
}