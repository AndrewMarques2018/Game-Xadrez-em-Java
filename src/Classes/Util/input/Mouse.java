package Classes.Util.input;

import Classes.Util.ElementoGrafico;

import java.awt.event.*;

public class Mouse implements MouseWheelListener, MouseListener, MouseMotionListener {

    private static boolean moved;
    private static boolean clickd;
    private static int x, y, weel;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        weel = e.getWheelRotation();
        try {
            Thread.sleep(50);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        weel = 0;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        clickd = true;
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        moved = true;
        x = e.getX();
        y = e.getY();
    }

    public static boolean apontaEm(int x0, int y0, int largura, int altura) {
        if (x > x0 && y > y0 && x < x0 + largura && y < y0 + altura) {
            return true;
        }
        return false;
    }

    public static boolean apontaEm(ElementoGrafico e) {

        return apontaEm(e.getX(), e.getY(), e.getLargura(), e.getAltura());
    }

    public static boolean clicaEm(int x0, int y0, int largura, int altura) {
        if (clickd) {
            if (x > x0 && y > y0 && x < x0 + largura && y < y0 + altura) {
                return true;
            }
        }
        return false;
    }

    public static boolean clicaEm(ElementoGrafico e) {
        return clicaEm(e.getX(), e.getY(), e.getLargura(), e.getAltura());
    }

    public static boolean isMoved() {
        return moved;
    }

    public static void setMoved(boolean moved) {
        Mouse.moved = moved;
    }

    public static boolean isClickd() {
        return clickd;
    }

    public static void setClickd(boolean clickd) {
        Mouse.clickd = clickd;
    }

    public static int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static int getWeel() {
        return weel;
    }
}

