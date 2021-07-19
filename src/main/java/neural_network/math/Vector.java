package neural_network.math;

public class Vector {

    public static double[] difference(double[] a, double[] b) {
        if (!verifyVectors(a, b)) {
            return null;
        }

        int n = a.length;

        double[] vector = new double[n];

        for (int i = 0; i < n; i++) {
            vector[i] = a[i] - b[i];
        }

        return vector;
    }

    public static double mean(double[] v) {
        int n = v.length;
        double sum = 0;

        for (double value : v) {
            sum += value;
        }

        return sum / n;
    }

    public static double[] map(double[] v, Lambda lambda) {
        int n = v.length;

        double[] vector = new double[n];

        for (int i = 0; i < n; i++) {
            vector[i] = lambda.fun(v[i]);
        }

        return vector;
    }

    private static boolean verifyVectors(double[] a, double[] b) {
        return a.length == b.length;
    }
}
