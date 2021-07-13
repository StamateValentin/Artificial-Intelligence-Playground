package neural_network.matrix;

import neural_network.color.Color;

public class Matrix {

    public static double[][] toRowMatrix(final double[] V) {
        int m = V.length;
        double[][] matrix = new double[1][m];

        System.arraycopy(V, 0, matrix[0], 0, m);

        return matrix;
    }

    public static double[][] toColumnMatrix(final double[] V) {
        int n = V.length;
        double[][] matrix = new double[n][1];

        for (int i = 0; i < n; i++) {
            matrix[i][0] = V[i];
        }

        return matrix;
    }

    public static double[][] transpose(final double[][] A) {
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

    public static double[][] multiply(final double[][] A, final double[][] B) {
        int n = A.length;
        int m = A[0].length;

        double[][] output = new double[n][B[0].length];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < B[0].length; j++) {
                for (int k = 0; k < m; k++) {
                    output[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return output;
    }

    public static double[][] add(final double[][] A, final double[][] B) {
        int n = A.length;
        int m = A[0].length;

        double[][] sum = new double[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                sum[i][j] = A[i][j] + B[i][j];
            }
        }

        return sum;
    }

    public static double[][] subtract(final double[][] A, final double[][] B) {
        int n = A.length;
        int m = A[0].length;

        double[][] difference = new double[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                difference[i][j] = A[i][j] - B[i][j];
            }
        }

        return difference;
    }

    public static void print(final double[][] A) {
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

    public static double[][] copyOf(final double[][] A) {
        double[][] copy = new double[A.length][A[0].length];

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                copy[i][j] = A[i][j];
            }
        }

        return copy;
    }

    public static boolean equalWith(final double[][] A, final double[][] B) {
        if (A.length != B.length || A[0].length != B[0].length) {
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

    public static void applyTransformation(final double[][] A, Transform transform) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                A[i][j] = transform.applyFunction(A[i][j]);
            }
        }
    }

}
