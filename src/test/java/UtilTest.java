import neural_network.weights.WeightsInit;
import neural_network.weights.RandomWeightsInit;
import neural_network.matrix.Matrix;
import neural_network.util.Util;
import org.junit.jupiter.api.Test;

public class UtilTest {

    @Test
    public void randomTest() {
        for (int i = 0; i < 50; i++) {
            System.out.println(Util.generateRandom(-1.0, 1.0));
        }
    }

    @Test
    public void randomWeightedMatrixGeneratorTest() {
        WeightsInit weightsInit = new RandomWeightsInit();
        double[][] result = weightsInit.create(5, 8);

        Matrix.println(result);
    }

}
