package neural_network.training;

import neural_network.matrix.Matrix;
import neural_network.util.Util;
import java.util.ArrayList;
import java.util.List;

public class TrainingData {

    private final List<double[]> trainingData;
    private final List<double[]> trainingTarget;

    public TrainingData() {
        this.trainingData = new ArrayList<>();
        this.trainingTarget = new ArrayList<>();
    }

    public void add(double[] input, double[] target) {
        trainingData.add(Matrix.copyOf(input));
        trainingTarget.add(Matrix.copyOf(target));
    }

    public int size() {
        return trainingData.size();
    }

    public void randomLoop(OutputData outputData) {
        int n = size();

        for (int i = 0; i < n; i++) {
            int rnd = (int) Util.generateRandom(0, n);

            outputData.fun(trainingData.get(rnd), trainingTarget.get(rnd));
        }
    }

    public void loop(OutputData outputData) {
        int n = size();

        for (int i = 0; i < n; i++) {
            outputData.fun(trainingData.get(i), trainingTarget.get(i));
        }
    }


}
