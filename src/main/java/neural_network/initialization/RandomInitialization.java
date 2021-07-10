package neural_network.initialization;

import neural_network.matrix.Matrix;
import static neural_network.Util.generateRandom;

public class RandomInitialization implements Initialization {
    @Override
    public double[][] create(int n, int m) {
        double[][] matrix = new double[n][m];
        Matrix.applyTransformation(matrix, (x) -> generateRandom(0.01, 0.99));
        return matrix;
    }
}
