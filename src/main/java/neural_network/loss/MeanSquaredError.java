package neural_network.loss;

import java.util.List;

public class MeanSquaredError extends LossFunction {
    public MeanSquaredError() {
        super(LossFunctions.MSE);
    }

    @Override
    public double calculate(List<Double> errors) {
        int n = errors.size();

        double sum = 0;

        for (Double x : errors) {
            sum += x;
        }

        return sum / n;
    }


}
