import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * Created by alex on 3/5/17.
 */
public class ImagePanel extends JPanel
{
    private BufferedImage _image;

    public ImagePanel()
    {
        setBackground(Color.black);
        setPreferredSize(new Dimension(500, 500));
        addMouseListener(new ClickListener(this));
    }

    public void setImage(BufferedImage i)
    {
        _image = i;
    }

    private boolean closeEnough(int baseColor, int testColor, int percentDifference)
    {
        Color base = new Color(baseColor);
        Color test = new Color(testColor);
        double diff = percentDifference * .01;

        base.getBlue();
        base.getGreen();

        double test_red_variation = test.getRed() * diff;
        double test_blue_variation = test.getBlue() * diff;
        double test_green_variation = test.getGreen() * diff;

        int test_red_bottom = (int) (test.getRed() - test_red_variation);
        int test_blue_bottom = (int) (test.getBlue() - test_blue_variation);
        int test_green_bottom = (int) (test.getGreen() - test_green_variation);

        int test_red_top = (int) (test.getRed() + test_red_variation);
        int test_blue_top = (int) (test.getBlue() + test_blue_variation);
        int test_green_top = (int) (test.getGreen() + test_green_variation);

        // Red Variation
        if (!(test_red_bottom <= base.getRed() && base.getRed() <= test_red_top))
        {
            return false;
        }

        // blue Variation
        if (!(test_blue_bottom <= base.getBlue() && base.getBlue() <= test_blue_top))
        {
            return false;
        }

        // green Variation
        if (!(test_green_bottom <= base.getGreen() && base.getGreen() <= test_green_top))
        {
            return false;
        }

        return true;
    }

    public void highlightObject(Point p)
    {
        int x = p.x;
        int y = p.y;
        int baseColor = _image.getRGB(x, y);
        int highlightAmount = 100;
        int colorDiff = 30;
        Point imageMax = new Point(_image.getWidth(), _image.getHeight());

        Color initColor = new Color(baseColor);
        int red = initColor.getRed();
        int blue = initColor.getBlue();
        int green = initColor.getGreen();
        if (red >= (255 - highlightAmount))
        {
            red = 255;
        }
        else
        {
            red += highlightAmount;
        }
        int highlightColor = new Color(red, blue, green).getRGB();

        for (int directionModifier = 1; directionModifier <= 4; directionModifier++)
        {
            Point direction_modifier = new Point();
            Point start_pos_modifier = new Point();
            switch (directionModifier)
            {
                case 1:
                    direction_modifier.x = 1;
                    direction_modifier.y = 1;
                    start_pos_modifier.x = 0;
                    start_pos_modifier.y = 0;
                    break;
                case 2:
                    direction_modifier.x = -1;
                    direction_modifier.y = -1;
                    start_pos_modifier.x = -1;
                    start_pos_modifier.y = 0;
                    break;
                case 3:
                    direction_modifier.x = -1;
                    direction_modifier.y = 1;
                    start_pos_modifier.x = -1;
                    start_pos_modifier.y = 1;
                    break;
                case 4:
                    direction_modifier.x = 1;
                    direction_modifier.y = -1;
                    start_pos_modifier.x = 0;
                    start_pos_modifier.y = -1;
                    break;
            }

            x = p.x + start_pos_modifier.x;
            y = p.y + start_pos_modifier.y;

            // First Pass
            int pixelColor = _image.getRGB(x, y);
            while (closeEnough(baseColor, pixelColor, colorDiff))
            {
                while (closeEnough(baseColor, pixelColor, colorDiff))
                {
                    _image.setRGB(x, y, highlightColor);
                    x += direction_modifier.x;
                    if (0 >= x || x >= imageMax.x)
                    {
                        break;
                    }
                    pixelColor = _image.getRGB(x, y);
                    updateUI();
                }
                x = p.x + start_pos_modifier.x;
                y += direction_modifier.y;
                if (0 >= y || y >= imageMax.y)
                {
                    break;
                }
                pixelColor = _image.getRGB(x, y);
            }
        }
    }

    @Override
    public void paint(Graphics graphics)
    {
        super.paint(graphics);

        graphics.drawImage(_image, 0, 0, this);
    }


    private class ClickListener implements MouseListener
    {
        private ImagePanel _parent;

        public ClickListener(ImagePanel imagePanel)
        {
            _parent = imagePanel;
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent)
        {
            System.out.println("Click at (" + mouseEvent.getX() + "," + mouseEvent.getY() + ")");
            _parent.highlightObject(new Point(mouseEvent.getX(), mouseEvent.getY()));
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent)
        {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent)
        {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent)
        {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent)
        {

        }
    }
}
