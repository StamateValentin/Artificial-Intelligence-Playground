import neural_network.exceptions.InvalidInputException;
import neural_network.NeuralNetwork;
import neural_network.matrix.Matrix;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NeuralNetworkTest {

    @Test
    public void neuralNetworkTest() throws InvalidInputException {
        NeuralNetwork neuralNetwork = new NeuralNetwork(new double[][][] {{{0.8, 0.3}}});
        double[][] input = new double[][]{{0.2}, {0.75}};
        double[][] expectedOutput = new double[][]{{0.5950784738661340245533}};

        double[][] output = neuralNetwork.feedForward(input);

        Matrix.print(output);

        assertTrue(Matrix.equalWith(expectedOutput, output));
    }

    @Test
    public void neuralNetworkBackPropagationTest() {
        NeuralNetwork neuralNetwork = new NeuralNetwork(new double[][][] {{{0.8, 0.3}}});
        double[][] input = new double[][]{{0.2}, {0.75}};
        double[][] expectedOutput = new double[][]{{0.820120951, 0.3799834319}};

        neuralNetwork.backPropagation(input, new double[][]{{0.0}});



//        assertTrue(Matrix.equalWith(expectedOutput, brainSnapshot[0]));
    }

}
