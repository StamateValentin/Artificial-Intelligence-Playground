package neural_network;

import neural_network.activation.ActivationFunction;
import neural_network.activation.SigmoidFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NeuralNetworkTest {

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