package function;

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

        for (int i = - pApplet.width / 2; i < pApplet.width / 2; i++) {
            pApplet.fill(50, 102, 168);
            pApplet.noStroke();
            pApplet.ellipse(i, function.f(i), 2, 2);
        }

        pApplet.popMatrix();
    }

}
