package neural_network.weights;

import neural_network.matrix.Matrix;
import static neural_network.Util.generateRandom;

public class RandomWeightsInit implements WeightsInit {
    @Override
    public double[][] create(int n, int m) {
        double[][] matrix = new double[n][m];
        Matrix.applyTransformation(matrix, (x) -> generateRandom(-1.0, 1.0));
        return matrix;
    }
}
