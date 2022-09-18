package Classes.Util;

import Classes.Util.input.Mouse;
import Classes.Util.output.Window;

import javax.swing.*;
import java.awt.*;

public class MyPopUp extends Coordenada2D{

    private Botao[] botoes;
    private ImageIcon mold;

    public MyPopUp(int x, int y, int largura, int altura){
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
    }

    public void addBotoes (Botao... botoes){
        this.botoes = new Botao[botoes.length-1];
        this.botoes = botoes;

        posicionaBotoes();
    }

    private void posicionaBotoes(){

        int l = largura / botoes.length;

        for (int i = 0; i < botoes.length; i++) {
            botoes[i].setLocale((x+i*l), (y + (altura)/2 - l/2), l, l);
        }
    }

    public int mostraPopUp(Graphics g){

        Mouse.setClickd(false);
        while (true){

            if(mold != null) {
                Window.graphics.drawImage(mold.getImage(), x, y, largura, altura, null);
            }

            for (int i = 0; i < botoes.length; i++) {
                if(Mouse.apontaEm(botoes[i].getX(),botoes[i].getY(),botoes[i].getLargura(),botoes[i].getAltura()))
                    botoes[i].setApontado(true);
                else
                    botoes[i].setApontado(false);

                botoes[i].desenha(Window.graphics);

                if(Mouse.clicaEm(botoes[i])){
                    return i;
                }
            }

            Window.update();
        }
    }

    public void setMold(String url) {
        this.mold = new ImageIcon(url);
    }

}
