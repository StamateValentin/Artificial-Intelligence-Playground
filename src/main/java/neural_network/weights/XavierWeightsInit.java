package neural_network.weights;

import neural_network.matrix.Matrix;
import static neural_network.util.Util.generateRandom;

public class XavierWeightsInit implements WeightsInit {
    @Override
    public double[][] create(int n, int m) {
        double[][] matrix = new double[n][m];

        double xavierInterval = Math.pow(m, -0.5);
        matrix = Matrix.map(matrix, (x) -> generateRandom(-xavierInterval, xavierInterval));

        return matrix;
    }
}
