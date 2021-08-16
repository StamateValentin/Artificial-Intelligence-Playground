package neural_network.bias;

public class NullBias extends BiasInit {
    public NullBias() {
        super(BiasInitFunctions.NULL_BIAS);
    }

    @Override
    public double[][] generate(int m) {
        double[][] bias = new double[m][1];

        for (int i = 0; i < m; i++) {
            bias[i][0] = 0;
        }

        return bias;
    }
}
