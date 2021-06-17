import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Util {
    public static int random(int x, int y) {
        return x + (int)(Math.random() * 1000) % (y - x);
    }

    /* TAKEN FROM: https://stackoverflow.com/questions/1519736/random-shuffling-of-an-array */
    public static <T> void randomize(T[] ar) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            T a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
