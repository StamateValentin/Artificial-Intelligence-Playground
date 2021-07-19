import processing.core.PApplet;
import processing.core.PConstants;

public class FunctionDrawer {
    private final PApplet pApplet;
    private final Function function;

    public FunctionDrawer(PApplet pApplet, Function function) {
        this.pApplet = pApplet;
        this.function = function;

        pApplet.registerMethod("draw", this);
    }

    public void draw() {
        pApplet.pushMatrix();
        pApplet.translate(pApplet.width / 2, pApplet.height / 2);
        pApplet.rotate(PConstants.PI);
        pApplet.scale(-1,1);

        int width = pApplet.width;
        int height = pApplet.height;

        pApplet.stroke(255);
        pApplet.line(- width / 2, 0, width / 2, 0);
        pApplet.line(0, height / 2, 0, - height / 2);

        for (int i = - pApplet.width / 2 + 1; i < pApplet.width / 2; i++) {
            int y1 = function.fun(i - 1);
            int y2 = function.fun(i);
            if (isOutsideCanvas(y1) || isOutsideCanvas(y2)) {
                continue;
            }

            pApplet.stroke(50, 102, 168);
            pApplet.line(i - 1, y1, i, y2);
        }

        pApplet.popMatrix();
    }

    private boolean isOutsideCanvas(int y) {
        return y < - pApplet.height / 2 || y > pApplet.height / 2;
    }

}
