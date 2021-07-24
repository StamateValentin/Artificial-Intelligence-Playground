package mnist;

import neural_network.math.Matrix;

import java.awt.image.BufferedImage;

public class Image {
    private final double[][] pixels;
    private final int label;

    public Image(BufferedImage bufferedImage, int label) {
        this.label = label;

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        if (width != 28 || height != 28) {
            System.out.println("Wrong image");
        }

        pixels = new double[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int clr = bufferedImage.getRGB(x, y);
                double  red   = (clr & 0x00ff0000) >> 16;
                double  green = (clr & 0x0000ff00) >> 8;
                double  blue  =  clr & 0x000000ff;

                pixels[y][x] = ((red + green + blue) / 3) / 255;
            }
        }

    }

    public double[] getImageAsInput() {
        int n = pixels.length;
        int m = pixels[0].length;

        int l = 0;

        double[] input = new double[n * m];

        for (double[] line : pixels) {
            for (int j = 0; j < m; j++) {
                input[l++] = line[j];
            }
        }

        return input;
    }

    public double[] getLabelAsOutput() {
        double[] output = new double[10];

        for (int i = 0; i < 10; i++) {
            if (i == label) {
                output[i] = 1.0;
                break;
            }
        }

        return output;
    }

    public int getLabel() {
        return label;
    }

    public void show() {
        System.out.printf("Label: %d \nSize: %dx%d \n", label, pixels.length, pixels[0].length);
        Matrix.println(pixels);
    }

}
