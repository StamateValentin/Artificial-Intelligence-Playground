package neural_network.cost;

public class SimpleCost extends CostFunction {
    public SimpleCost() {
        super(CostFunctions.SIMPLE_COST);
    }

    @Override
    double cost(double target, double output) {
        return target - output;
    }
}
