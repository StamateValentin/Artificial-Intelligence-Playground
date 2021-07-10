package neural_network.initialization;

import neural_network.matrix.Matrix;
import static neural_network.Util.generateRandom;

public class XavierInitialization implements Initialization {
    @Override
    public double[][] create(int n, int m) {
        double[][] matrix = new double[n][m];

        double xavierInterval = Math.pow(m, -0.5);
        Matrix.applyTransformation(matrix, (x) -> generateRandom(-xavierInterval, xavierInterval));

        return matrix;
    }
}
