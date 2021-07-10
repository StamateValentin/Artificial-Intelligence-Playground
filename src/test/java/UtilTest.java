import neural_network.initialization.Initialization;
import neural_network.initialization.RandomInitialization;
import neural_network.initialization.XavierInitialization;
import neural_network.matrix.Matrix;
import neural_network.Util;
import org.junit.jupiter.api.Test;

public class UtilTest {

    @Test
    public void randomTest() {
        for (int i = 0; i < 50; i++) {
            System.out.println(Util.generateRandom(0.0, 1.0));
        }
    }

    @Test
    public void randomWeightedMatrixGeneratorTest() {
        Initialization initialization = new RandomInitialization();
        double[][] result = initialization.create(5, 8);

        Matrix.print(result);
    }

    @Test
    public void normalizeX() {
        System.out.println(Util.normalizeX(0.6));
    }


}
