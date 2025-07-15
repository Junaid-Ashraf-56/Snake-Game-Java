import javax.swing.*;
import java.awt.*;

public class SnakeGame extends JPanel {
    int width;
    int height;
    int tileSize = 25;

    public SnakeGame(int width,int height) {
        this.width=width;
        this.height= height;

        setPreferredSize(new Dimension(width,height));
        setBackground(Color.black);
        setFocusable(true);

    }

    @Override
    public void paintComponents(Graphics graphics){
        super.paintComponents(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics){

        //Lines
        graphics.setColor(Color.DARK_GRAY);
        for (int i = 0; i <= height/tileSize ; i++) {
            graphics.drawLine(i*tileSize,0,i*tileSize,height);
        }
    }
}