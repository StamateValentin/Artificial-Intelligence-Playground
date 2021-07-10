package neural_network.activation;

public class TanhFunction implements ActivationFunction {
    @Override
    public double fun(double x) {
        return Math.tanh(x);
    }

    @Override
    public double slope(double x) {
        return 1 - fun(x) * fun(x);
    }
}
