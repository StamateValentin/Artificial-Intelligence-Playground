package neural_network.loss;

import java.util.List;

public abstract class LossFunction {
    private final String name;

    public LossFunction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract double calculate(List<Double> errors);

}
