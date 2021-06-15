public class Util {
    public static int random(int x, int y) {
        return x + (int)(Math.random() * 1000) % (y - x);
    }
}
