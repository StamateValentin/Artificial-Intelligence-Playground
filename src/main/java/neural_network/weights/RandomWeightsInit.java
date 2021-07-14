package neural_network.weights;

import neural_network.matrix.Matrix;
import static neural_network.util.Util.generateRandom;

public class RandomWeightsInit implements WeightsInit {
    @Override
    public double[][] create(int n, int m) {
        return Matrix.map(Matrix.create(n, m), (x) -> generateRandom(-1.0, 1.0));
    }
}
