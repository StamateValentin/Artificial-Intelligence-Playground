package neural_network.activation;

public abstract class ActivationFunction {
    private final String name;

    public ActivationFunction(String name) {
        this.name = name;
    }

    public abstract double fun(double x);

    /** Derivative of the activation function */
    public abstract double slope(double x);

    public String getName() {
        return name;
    }
}
