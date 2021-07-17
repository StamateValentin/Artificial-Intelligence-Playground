package neural_network.bias;

public abstract class BiasInit {

    private final String name;

    protected BiasInit(String name) {
        this.name = name;
    }

    public abstract double[][] generate(int m);

    public String getName() {
        return name;
    }
}
