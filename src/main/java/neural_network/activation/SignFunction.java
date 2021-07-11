package neural_network.activation;

public class SignFunction implements ActivationFunction {
    @Override
    public double fun(double x) {
        return x < 0 ? 0 : 1;
    }

    @Override
    public double slope(double x) {
        return 0;
    }
}
