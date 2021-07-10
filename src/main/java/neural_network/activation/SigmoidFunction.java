package neural_network.activation;

public class SigmoidFunction implements ActivationFunction {
    @Override
    public double fun(double x) {
        return 1.0D / (1 + Math.pow(Math.E, -x));
    }

    @Override
    public double slope(double x) {
        double y = fun(x);
        return y * (1 - y);
    }
}
