package neural_network;

import java.util.Random;

public class Util {

    public static double generateRandom(double start, double end) {
        Random random = new Random();
        return start + (end - start) * random.nextDouble();
    }

    public static double normalizeX(double x) {
        return 0.01 + ((0.99 - 0.01) / 1.0) *  x;
    }

}
