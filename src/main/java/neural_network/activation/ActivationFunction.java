package neural_network.activation;

public interface ActivationFunction {
    double fun(double x);

    /* DERIVATIVE OF THE FUNCTION */
    double slope(double x);
}
