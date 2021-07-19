import neural_network.util.Util;
import processing.core.PApplet;
import processing.core.PConstants;

public class Point {
    private final PApplet pApplet;

    private final int x;
    private final int y;

    private boolean guessedLabel = false;
    private boolean label = false;

    public Point(PApplet pApplet, Function function) {
        this.pApplet = pApplet;
        this.pApplet.registerMethod("draw", this);

        this.x = (int) Util.generateRandom(- pApplet.width / 2 + 5, pApplet.width / 2 - 5);
        this.y = (int) Util.generateRandom(- pApplet.height / 2 + 5, pApplet.height / 2 - 5);

        int x = this.x;
        int y = function.fun(x);

        label = this.y >= y;
    }

    public Point(PApplet pApplet, Function function, int x, int y) {
        this.pApplet = pApplet;
        this.pApplet.registerMethod("draw", this);

        this.x = x;
        this.y = y;

        int functionValue = function.fun(x);

        if (this.y >= functionValue) {
            label = true;
        }
    }

    public void draw() {
        pApplet.pushMatrix();
        pApplet.translate(pApplet.width / 2, pApplet.height / 2);
        pApplet.rotate(PConstants.PI);
        pApplet.scale(-1,1);

        Color fillColor = guessedLabel ? Color.GREEN : Color.RED;
        Color strokeColor = label ? Color.GREEN : Color.RED;

        pApplet.fill(fillColor.r, fillColor.g, fillColor.b);
        pApplet.strokeWeight(1);
        pApplet.stroke(strokeColor.r, strokeColor.g, strokeColor.b);
        pApplet.ellipse(x, y, 8, 8);

        pApplet.popMatrix();
    }

    public boolean getLabel() {
        return label;
    }

    public void setGuessedLabel(boolean isAbove) {
        this.guessedLabel = isAbove;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getGuessedLabel() {
        return guessedLabel;
    }

    @Override
    public String toString() {
        return String.format("Point: %3d %3d", x, y);
    }

    /* range [-1 1] */
    public double getNormalizedX() {
       return getNormalizedX(x, pApplet.width);
    }

    public double getNormalizedY() {
        return getNormalizedY(y, pApplet.height);
    }

    public static double getNormalizedX(int x, int width) {
        return ((1.0 * width / 2 + x) / width) * 2 - 1;
    }

    public static double getNormalizedY(int y, int height) {
        return ((1.0 * height / 2 + y) / height) * 2 - 1;
    }
}
