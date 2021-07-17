package neural_network.activation;

public class TanhFunction extends ActivationFunction {

    public TanhFunction() {
        super(ActivationFunctions.TANH_FUNCTION);
    }

    @Override
    public double fun(double x) {
        return Math.tanh(x);
    }

    @Override
    public double slope(double x) {
        return 1 - fun(x) * fun(x);
    }
}
