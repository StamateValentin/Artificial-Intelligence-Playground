import neural_network.NeuralNetworkWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FileWriter {
    @Test
    public void writeTest() throws IOException {
        NeuralNetworkWriter neuralNetworkWriter = new NeuralNetworkWriter();
        neuralNetworkWriter.write("Ana are mere");
    }
}
