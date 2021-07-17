package neural_network.weights;

public abstract class WeightsInit {
    private final String name;

    public WeightsInit(String name) {
        this.name = name;
    }

    public abstract double[][] create(int n, int m);

    public String getName() {
        return name;
    }
}
