import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int width = 600;
        int height = width;

        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width,height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }
}