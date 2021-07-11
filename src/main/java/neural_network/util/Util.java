package neural_network.util;

import java.util.Random;

public class Util {

    public static double generateRandom(double start, double end) {
        Random random = new Random();
        return start + (end - start) * random.nextDouble();
    }

}
