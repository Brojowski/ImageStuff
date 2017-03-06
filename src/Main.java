import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by alex on 3/5/17.
 */
public class Main
{
    public static void main(String args[])
    {
        JFrame gui = new JFrame("Test");

        ImagePanel image = new ImagePanel();
        BufferedImage img = null;
        try
        {
            File imageFile = new File("futurama.jpg");
            img = ImageIO.read(imageFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        image.setImage(img);

        gui.setContentPane(image);
        gui.setMinimumSize(new Dimension(1000,600));
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);
    }
}
