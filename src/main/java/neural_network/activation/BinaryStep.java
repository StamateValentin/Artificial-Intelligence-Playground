package neural_network.activation;

public class BinaryStep extends ActivationFunction {

    public BinaryStep() {
        super(ActivationFunctions.BINARY_STEP);
    }

    @Override
    public double fun(double x) {
        return x < 0 ? 0 : 1;
    }

    @Override
    public double slope(double x) {
        return x != 0 ? 0 : 100;
    }
}
