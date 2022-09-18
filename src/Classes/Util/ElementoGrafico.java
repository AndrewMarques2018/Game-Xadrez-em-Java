package Classes.Util;

import java.awt.*;

public class ElementoGrafico extends Coordenada2D{

    private Image image;
    private boolean ativa = true;

    public void desenha (Graphics g) {
        if(ativa) {
            if (image != null) {
                if (largura != 0 && altura != 0)
                    g.drawImage(image, x, y, largura, altura, null);
                else
                    g.drawImage(image, x, y, null);
            }
        }
    }

    public void mostraAreaDesenho(Graphics g, Color c){
        g.setColor(c);
        g.fillRect(x, y, largura, altura);
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public Image getImage() {
        return image;
    }
}
