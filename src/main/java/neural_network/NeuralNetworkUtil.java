package neural_network;

import java.util.Random;

public class NeuralNetworkUtil {

    public static double generateRandom(double start, double end) {
        Random random = new Random();
        return start + (end - start) * random.nextDouble();
    }

    public static double[][] createRandomWeightedMatrix(int n, int m) {
        double[][] matrix = new double[n][m];

        double xavierInterval = Math.pow(m, -0.5);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                /* Xavier Weight Initialization */
                matrix[i][j] = generateRandom(-xavierInterval, xavierInterval);
            }
        }

        return matrix;
    }

    public static double normalizeX(double x) {
        return 0.01 + ((0.99 - 0.01) / 1.0) *  x;
    }

}
