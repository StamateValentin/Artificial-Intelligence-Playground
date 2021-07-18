package neural_network.cost;

public abstract class CostFunction {
    private final String name;

    public CostFunction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double[][] cost(double[][] output, double[][] target) {
        int nO = output.length;
        int mO = output[0].length;

        int nT = target.length;
        int mT = target.length;

        if (nO != nT || mO != mT) {
            System.out.println("Cost: Invalid Matrix Size");
            return new double[1][1];
        }

        double[][] matrix = new double[nO][mO];

        for (int i = 0; i < nO; i++) {
            for (int j = 0; j < mO; j++) {
                matrix[i][j] = cost(output[i][j], target[i][j]);
            }
        }

        return matrix;
    }

    abstract double cost(double target, double output);
}
