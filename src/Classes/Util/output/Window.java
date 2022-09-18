package Classes.Util.output;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Window extends JFrame {

    public static Graphics graphics;

    private static BufferStrategy buffer;
    private final GraphicsDevice graphicsDevice;

    public Window(String titulo, int width, int height) {

        super(titulo);
        this.setDefaultCloseOperation(3);
        this.setSize(width, height);
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
        this.createBufferStrategy(3);
        buffer = this.getBufferStrategy();
        graphics = buffer.getDrawGraphics();
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        this.graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();

    }

    public static void update() {
        Window.graphics.dispose();
        Window.buffer.show();
        Toolkit.getDefaultToolkit().sync(); //melhora a perfomace
        Window.graphics = Window.buffer.getDrawGraphics();
    }

    public void exit() {
        this.dispose();
        System.exit(0);
    }

    public void clear(Color color) {
        graphics.setColor(color);
        graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    public void setFullScreen() {
        graphicsDevice.setFullScreenWindow(this);
    }

    public void restoreScreen() {
        graphicsDevice.setFullScreenWindow((java.awt.Window)null);
        super.setLocationRelativeTo((Component)null);
    }

    public void setSize(int width, int height) {
        this.setResizable(false);
        super.setSize(width, height);
        super.setLocationRelativeTo((Component)null);
    }

    public void setSize(Dimension d) {
        this.setSize(d.width, d.height);
    }

    public void display() {
        graphics.dispose();
        buffer.show();
        Toolkit.getDefaultToolkit().sync();
        graphics = buffer.getDrawGraphics();
    }

}
