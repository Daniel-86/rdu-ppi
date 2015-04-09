/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.tester;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import javax.imageio.ImageIO;

/**
 *
 * @author oracle
 */
public class test {

    public static void main(String args[]) {
        try {
            File img = new File("/home/oracle/Imágenes/index.jpg");

            BufferedImage bufferedImage = ImageIO.read(img);
            BufferedImage newImage = rotate90ToRight(bufferedImage);


            ImageIO.write(newImage, "jpg", new File("/home/oracle/Imágenes/index1.jpg"));
            //        persona = new Persona();

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static BufferedImage rotate90ToRight(BufferedImage inputImage) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        BufferedImage returnImage = new BufferedImage(height, width, inputImage.getType());

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                returnImage.setRGB(height - y - 1, x, inputImage.getRGB(x, y));


//Again check the Picture for better understanding
            }
        }
        return returnImage;
    }
}
