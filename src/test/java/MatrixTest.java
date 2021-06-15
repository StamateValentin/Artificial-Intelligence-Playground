import matrix.Matrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MatrixTest {

    @Test
    public void multiplication() {
        double[][] A = new double[][] {
                {1, 2, 1},
                {0, 1, 0},
                {2, 3, 4}
        };

        double[][] B = new double[][] {
                {2, 5},
                {6, 7},
                {1, 8}
        };

        double[][] expected = new double[][] {
                {15, 27},
                {6, 7},
                {26, 63}
        };

        double[][] result = Matrix.multiply(A, B);

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

        double[][] result = Matrix.difference(A, B);

        Assertions.assertArrayEquals(expected, result);
    }

}
