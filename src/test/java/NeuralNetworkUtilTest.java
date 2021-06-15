import matrix.Matrix;
import neural_network.NeuralNetworkUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class NeuralNetworkUtilTest {

    @Test
    public void randomTest() {
        for (int i = 0; i < 50; i++) {
            System.out.println(NeuralNetworkUtil.generateRandom(0.0, 1.0));
        }
    }

    @Test
    public void randomWeightedMatrixGeneratorTest() {
        double[][] result = NeuralNetworkUtil.createRandomWeightedMatrix(5, 8);

        Matrix.print(result);
    }

}
