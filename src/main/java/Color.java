public class Color {
    public static final Color GREEN = new Color(65, 209, 103);
    public static final Color RED = new Color(255, 82, 82);
    public static final Color BLACK = new Color(0, 0, 0);

    public final int r, g, b;

    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /* https://stackoverflow.com/q/22607043/10805602 & https://stackoverflow.com/a/39924008/10805602*/
    public static Color colorMix(Color c1, Color c2, double mix) {
        //Mix [0..1]
        //  0   --> all c1
        //  0.5 --> equal mix of c1 and c2
        //  1   --> all c2

        //Invert sRGB gamma compression
        c1 = inverseSrgbCompanding(c1);
        c2 = inverseSrgbCompanding(c2);

        Color result = new Color(
                (int) (1.0 * c1.r * (1 - mix) + 1.0 * c2.r * mix),
                (int) (1.0 * c1.g * (1 - mix) + 1.0 * c2.g * mix),
                (int) (1.0 * c1.b * (1 - mix) + 1.0 * c2.b * mix)
        );

        //Reapply sRGB gamma compression
        result = SrgbCompanding(result);
        return result;
    }

    private static Color inverseSrgbCompanding(Color c) {
        //Convert color from 0..255 to 0..1
        double r = 1.0 * c.r / 255;
        double g = 1.0 * c.g / 255;
        double b = 1.0 * c.b / 255;

        //Inverse Red, Green, and Blue
        if (r > 0.04045) r = Math.pow((r + 0.055) / 1.055, 2.4); else r = r / 12.92;
        if (g > 0.04045) g = Math.pow((g + 0.055) / 1.055, 2.4); else g = g / 12.92;
        if (b > 0.04045) b = Math.pow((b + 0.055) / 1.055, 2.4); else b = b / 12.92;

        return new Color((int) (r * 255), (int) (g * 255), (int) (b * 255));
    }

    private static Color SrgbCompanding(Color c) {
        //Convert color from 0..255 to 0..1
        double r = 1.0 * c.r / 255;
        double g = 1.0 * c.g / 255;
        double b = 1.0 * c.b / 255;

        //Apply companding to Red, Green, and Blue
        if (r > 0.0031308) r = 1.055 * Math.pow(r, 1 / 2.4) - 0.055; else r = r * 12.92;
        if (g > 0.0031308) g = 1.055 * Math.pow(g, 1 / 2.4) - 0.055; else g = g * 12.92;
        if (b > 0.0031308) b = 1.055 * Math.pow(b, 1 / 2.4) - 0.055; else b = b * 12.92;

        //return new color. Convert 0..1 back into 0..255
        return new Color((int) (r * 255), (int) (g * 255), (int) (b * 255));
    }

    @Override
    public String toString() {
        return String.format("Color: %d %d %d", r, g, b);
    }
}
