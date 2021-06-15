import function.Function;
import processing.core.PApplet;
import processing.core.PConstants;

public class Point {
    private final PApplet pApplet;

    private int x;
    private int y;

    private final int colorA;
    private final int colorB;

    private boolean isAbove = false;
    private boolean realAbove = false;

    public Point(PApplet pApplet, Function function) {
        this.pApplet = pApplet;
        this.pApplet.registerMethod("draw", this);
        this.colorA = pApplet.color(65, 209, 103);
        this.colorB = pApplet.color(255, 82, 82);

        this.x = Util.random(- pApplet.width / 2 + 5, pApplet.width / 2 - 5);
        this.y = Util.random(- pApplet.height / 2 + 5, pApplet.height / 2 - 5);

        int x = this.x;
        int y = function.f(x);

        if (this.y >= y) {
            realAbove = true;
        }
    }

    public void draw() {
        pApplet.pushMatrix();
        pApplet.translate(pApplet.width / 2, pApplet.height / 2);
        pApplet.rotate(PConstants.PI);

        int color = isAbove ? colorA : colorB;
        int strokeColor = realAbove ? colorA : colorB;

        pApplet.fill(color);
        pApplet.strokeWeight(1);
        pApplet.stroke(strokeColor);
        pApplet.ellipse(x, y, 8, 8);

        pApplet.popMatrix();
    }

    public boolean isRealAbove() {
        return realAbove;
    }

    public void setAbove(boolean isAbove) {
        this.isAbove = isAbove;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
