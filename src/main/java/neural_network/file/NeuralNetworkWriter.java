package neural_network.file;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NeuralNetworkWriter {
    private final FileWriter fileWriter;

    public NeuralNetworkWriter() {
        Path path = Paths.get("snapshot.txt");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(path.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.fileWriter = fileWriter;
    }

    public void write(String string) {
        try {
            fileWriter.write(string);
            fileWriter.flush();
        } catch (IOException | NullPointerException e) {
            System.out.println("Error writing string");
        }
    }

}
