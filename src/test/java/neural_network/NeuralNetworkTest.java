package neural_network;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NeuralNetworkTest {

    @Test
    void activationFunction() {
        double x = 0.67;
        double expected = 0.661503159202952425768;

        Assertions.assertEquals(expected, NeuralNetwork.activationFunction(x));
    }

    @Test
    void derivativeActivationFunction() {
        double x = 0.67;
        double expected = 0.2239167295674658031824;

        Assertions.assertEquals(expected, NeuralNetwork.derivativeActivationFunction(x));
    }
}