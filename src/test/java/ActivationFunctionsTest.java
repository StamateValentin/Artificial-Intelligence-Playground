import neural_network.activation.ActivationFunction;
import neural_network.activation.TanhFunction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActivationFunctionsTest {

    @Test
    public void tanhTest() {
        double x = 0.5;
        double expected = 0.4621171572600097585023;

        ActivationFunction activationFunction = new TanhFunction();

        assertEquals(expected, activationFunction.fun(x));
    }

    @Test
    public void tanhDerivativeTest() {
        double x = 0.392;
        double expected = 0.8608089787752653;

        ActivationFunction activationFunction = new TanhFunction();

        assertEquals(expected, activationFunction.slope(x));
    }

}
