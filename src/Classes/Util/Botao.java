package Classes.Util;

import javax.swing.*;
import java.awt.*;

public class Botao extends ElementoGrafico{

    private boolean apontado, selecionado;
    private Image imgApontado, imgSelecionado;

    public Botao(String url) {
        this.setImage(new ImageIcon(url).getImage());
    }

    public Botao(String url, String urlApontado) {
        this.setImage(new ImageIcon(url).getImage());
        this.imgApontado = new ImageIcon(urlApontado).getImage();
    }

    public void setLocale(int posX, int posY, int largura, int altura){
        this.x = posX;
        this.y = posY;
        this.largura = largura;
        this.altura = altura;
    }

    public void desenha (Graphics g) {

        if(selecionado){
            if(imgSelecionado != null)
                g.drawImage(imgSelecionado, x, y, largura, altura, null);
        }else
        if (apontado) {
            if(imgApontado != null)
            g.drawImage(imgApontado, x, y, largura, altura, null);
        }else
            g.drawImage(this.getImage(), x, y,largura,altura, null);

    }

    public void setApontado(boolean apontado) {
        this.apontado = apontado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    public boolean isApontado() {
        return apontado;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setImgApontado(String url) {
        this.imgApontado = new ImageIcon(url).getImage();
    }

    public void setImgSelecionado(String url) {
        this.imgSelecionado = new ImageIcon(url).getImage();;
    }

    public Image getImgApontado() {
        return imgApontado;
    }

    public void setImgApontado(Image imgApontado) {
        this.imgApontado = imgApontado;
    }

    public Image getImgSelecionado() {
        return imgSelecionado;
    }

    public void setImgSelecionado(Image imgSelecionado) {
        this.imgSelecionado = imgSelecionado;
    }

    @Override
    public String toString() {
        return "Botao{" +
                "ativo=" + this.isAtiva() +
                "apontado=" + apontado +
                ", selecionado=" + selecionado +
                ", x=" + x +
                ", y=" + y +
                ", largura=" + largura +
                ", altura=" + altura +
                '}';
    }
}
