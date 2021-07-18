package neural_network.cost;

public class SquaredDifference extends CostFunction{
    public SquaredDifference() {
        super(CostFunctions.SQUARED_DIFF_COST);
    }

    @Override
    double cost(double target, double output) {
        double diff = target - output;
        return diff * diff;
    }
}
