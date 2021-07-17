package neural_network.bias;

import neural_network.util.Util;

public class RandomBias extends BiasInit {

    public RandomBias() {
        super(BiasInitFunctions.RANDOM_BIAS_INIT);
    }

    @Override
    public double[][] generate(int m) {
        double[][] bias = new double[m][1];

        for (int i = 0; i < m; i++) {
            bias[i][0] = Util.generateRandom(0, 1);
        }

        return bias;
    }
}
