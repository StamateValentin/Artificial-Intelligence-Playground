import mnist.Image;
import mnist.MnistDecompressedReader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;

public class MainClass {

    public static void main(String... args) throws IOException {

        MnistDecompressedReader mnistReader = new MnistDecompressedReader();
        mnistReader.readDecompressedTraining(Paths.get("./data"), mnistEntry -> {
            int label = mnistEntry.getLabel();
            BufferedImage image = mnistEntry.createImage();

            Image img = new Image(image, label);
        });

    }

}
