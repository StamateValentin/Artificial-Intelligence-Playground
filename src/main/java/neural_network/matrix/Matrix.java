package neural_network.matrix;

import neural_network.color.Color;

public class Matrix {

    public static double[][] toRowMatrix(double[] V) {
        int m = V.length;
        double[][] matrix = new double[1][m];

        System.arraycopy(V, 0, matrix[0], 0, m);

        return matrix;
    }

    public static double[][] toColumnMatrix(double[] V) {
        int n = V.length;
        double[][] matrix = new double[n][1];

        for (int i = 0; i < n; i++) {
            matrix[i][0] = V[i];
        }

        return matrix;
    }

    public static double[][] transpose(double[][] A) {
        int n = A.length;
        int m = A[0].length;

        double[][] newA = new double[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                newA[i][j] = A[j][i];
            }
        }

        return newA;
    }

    public static double[][] multiply(double[][] A, double[][] B) {
        int nA = A.length;
        int mA = A[0].length;

        int nB = B.length;
        int mB = B[0].length;

        if (nA != mB || mA != nB) {
            System.out.println("Multiply: Invalid matrix size.");
            return create(1, 1);
        }

        double[][] output = new double[nA][mB];

        for (int i = 0; i < nA; i++) {
            for (int j = 0; j < mB; j++) {
                for (int k = 0; k < mA; k++) {
                    output[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return output;
    }

    public static double[][] add(double[][] A, double[][] B) {
        int nA = A.length;
        int mA = A[0].length;

        int nB = B.length;
        int mB = B[0].length;

        if (nA != nB || mA != mB) {
            System.out.println("Add: Invalid matrix size");
            return create(1, 1);
        }

        double[][] sum = new double[nA][mA];

        for (int i = 0; i < nA; i++) {
            for (int j = 0; j < mA; j++) {
                sum[i][j] = A[i][j] + B[i][j];
            }
        }

        return sum;
    }

    public static double[][] subtract(double[][] A, double[][] B) {
        int nA = A.length;
        int mA = A[0].length;

        int nB = B.length;
        int mB = B[0].length;

        if (nA != nB || mA != mB) {
            System.out.println("Subtract: Invalid matrix size");
            return create(1, 1);
        }

        double[][] difference = new double[nA][mA];

        for (int i = 0; i < nA; i++) {
            for (int j = 0; j < mA; j++) {
                difference[i][j] = A[i][j] - B[i][j];
            }
        }

        return difference;
    }

    public static void print(double[][] A) {
        System.out.print(Color.BLUE_BOLD);
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                if (j == 0) {
                    System.out.print("|");
                }
                System.out.printf("%7.3f ", A[i][j]);
                if (j == A[0].length - 1) {
                    System.out.print("|");
                }
            }

            if (i != A.length - 1) {
                System.out.println("");
            }
        }
        System.out.print(Color.RESET);
        System.out.println("");
    }

    public static void println(double[][] A) {
        print(A);
        System.out.println("");
    }

    public static double[][] copyOf(double[][] A) {
        double[][] copy = new double[A.length][A[0].length];

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                copy[i][j] = A[i][j];
            }
        }

        return copy;
    }

    public static boolean equalWith(double[][] A, double[][] B) {
        if (A.length != B.length || A[0].length != B[0].length) {
            System.out.println("Equals With: Invalid matrix size.");
            return false;
        }

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                if (A[i][j] != B[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public static double[][] map(double[][] A, Transform transform) {
        double[][] matrix = Matrix.copyOf(A);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = transform.applyFunction(matrix[i][j]);
            }
        }

        return matrix;
    }

    public static double[][] create(int n, int m) {
        return new double[n][m];
    }
}
