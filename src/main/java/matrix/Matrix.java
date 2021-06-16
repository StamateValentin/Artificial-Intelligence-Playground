package matrix;

/* TODO: exceptions */
public class Matrix {

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

    public static void print(double[][] A) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                System.out.printf("%7.3f ", A[i][j]);
            }
            System.out.println("");
        }
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

    public static void applyTransformation(double[][] A, Transform transform) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                A[i][j] = transform.applyFunction(A[i][j]);
            }
        }
    }

}
