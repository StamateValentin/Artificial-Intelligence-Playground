package neural_network.training;

@FunctionalInterface
public interface OutputData {
    void fun(double[] input, double[] target);
}
