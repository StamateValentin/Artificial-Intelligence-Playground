import neural_network.math.Matrix;
import processing.core.PApplet;

public class CanvasImage {

    private final PApplet pApplet;
    private final double[][] image = new double[28][28];

    public CanvasImage(PApplet pApplet) {
        this.pApplet = pApplet;

        pApplet.registerMethod("draw", this);
    }

    public void draw() {
        int cellSize = pApplet.width / 28;

        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 28; j++) {
                pApplet.stroke(20);
                pApplet.fill((int)((1.0 - image[i][j]) * 255));
                pApplet.rect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }

    public void mouseDragged() {
        int x = pApplet.mouseX;
        int y = pApplet.mouseY;

        if (x >= pApplet.width || x < 0) {
            return;
        }

        if (y >= pApplet.height || y < 0) {
            return;
        }

        int i = (int) ((1.0 * y / pApplet.height) * 28);
        int j = (int) ((1.0 * x / pApplet.width) * 28);

        image[i][j] = 1.0;
    }

    public double[][] getImage() {
        return Matrix.copyOf(image);
    }

    public double[] getImageAsVector() {
        return Matrix.matrixToVector(image);
    }

    public void clear() {
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                image[i][j] = 0;
            }
        }
    }
}
