import neural_network.activation.ActivationFunction;
import neural_network.activation.SigmoidFunction;
import neural_network.matrix.Matrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MatrixTest {

    @Test
    public void hadamardProduct() {
        double[][] A = new double[][] {
                {1, 2},
                {0, 1},
                {2, 3}
        };

        double[][] B = new double[][] {
                {2, 5},
                {6, 7},
                {1, 8}
        };

        double[][] expected = new double[][]{
                {2, 10},
                {0, 7},
                {2, 24}
        };

        double[][] result = Matrix.hadamardProduct(A, B);

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void dotProduct() {
        double[][] A = new double[][] {
                {1},
                {4},
                {2}
        };

        double[][] B = new double[][] {
                {2, 5, 7, 4, 3}
        };

        double[][] expected = new double[][] {
                {2, 5, 7, 4, 3},
                {8, 20, 28, 16, 12},
                {4, 10, 14, 8, 6}
        };

        double[][] result = Matrix.dotProduct(A, B);

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void transpose() {
        double[][] A = new double[][] {
                {2, 5},
                {6, 7},
                {1, 8}
        };

        double[][] expected = new double[][] {
                {2, 6, 1},
                {5, 7, 8}
        };

        double[][] result = Matrix.transpose(A);

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void difference() {
        double[][] A = new double[][] {
                {15, 27},
                {6, 7},
                {26, 63}
        };

        double[][] B = new double[][] {
                {15, 27},
                {6, 7},
                {26, 63}
        };

        double[][] expected = new double[][] {
                {0, 0},
                {0, 0},
                {0, 0}
        };

        double[][] result = Matrix.subtract(A, B);

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void sum() {
        double[][] A = new double[][] {
                {15, 27},
                {6, 7},
                {26, 63}
        };

        double[][] B = new double[][] {
                {15, 27},
                {6, 7},
                {26, 63}
        };

        double[][] expected = new double[][] {
                {30, 54},
                {12, 14},
                {52, 126}
        };

        double[][] result = Matrix.add(A, B);

        Assertions.assertArrayEquals(expected, result);
    }

    static class NeuralNetworkTest {

        @Test
        void activationFunction() {
            double x = 0.67;
            double expected = 0.661503159202952425768;

            ActivationFunction activationFunction = new SigmoidFunction();

            Assertions.assertEquals(expected, activationFunction.fun(x));
        }

        @Test
        void derivativeActivationFunction() {
            double x = 0.67;
            double expected = 0.2239167295674658031824;

            ActivationFunction activationFunction = new SigmoidFunction();

            Assertions.assertEquals(expected, activationFunction.slope(x));
        }
    }
}
