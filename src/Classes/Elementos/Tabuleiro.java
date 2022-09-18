package Classes.Elementos;

import Classes.Util.CoordenadasCasas;
import Classes.Util.ElementoGrafico;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Tabuleiro extends ElementoGrafico {

    private final Casa[][] casas;
    private Casa casaApontada, casaSelecionada;
    private final ElementoGrafico borda;

    public Tabuleiro(int x, int y, int largura, int altura) {

        setImage(new ImageIcon("src\\Recursos\\Imagens\\Tabuleiros\\padrao.png").getImage());

        borda = new ElementoGrafico();
        borda.setImage(new ImageIcon("src\\Recursos\\Imagens\\Tabuleiros\\moldura.png").getImage());

        this.casas = new Casa[8][8];

        redimenciona(x, y, largura, altura);
    }

    public void redimenciona (int x, int y, int largura, int altura){

        setLocal(x, y);
        setDimensao(largura, altura);

        borda.setLocal((int) (x - largura*0.05) ,(int) (y - altura*0.05));
        borda.setDimensao((int) (largura + largura*0.1),(int) (altura + altura*0.1));

        posicionarCasas();
    }

    public void posicionarCasas(){

        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                casas[linha][coluna] = new Casa(linha, coluna);
                casas[linha][coluna].redimenciona(
                        this.getX() + this.getLargura() * coluna / 8,
                        this.getY() + this.getAltura() * (7 - linha) / 8,
                        this.getLargura() / 8, this.getAltura() / 8);
            }
        }
    }

    public void desenha (Graphics g){

        borda.desenha(g);
        super.desenha(g);

        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                casas[linha][coluna].desenha(g);
            }
        }

    }

    public ArrayList<Casa> getCasas() {
        ArrayList<Casa> c = new ArrayList<>();

        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                c.add(casas[linha][coluna]);
            }
        }

        return c;
    }

    public Casa getCasa (CoordenadasCasas coordenada) {
        return getCasa(coordenada.getLinha(),coordenada.getColuna());
    }

    public Casa getCasa (int l, int c) {
        return casas[l][c];
    }

    public Casa getCasaApontada() {
        return casaApontada;
    }

    public void setCasaApontada(Casa casaApontada) {

        try {
            if (this.casaApontada != null)
                this.casaApontada.setApontado(false);

            this.casaApontada = casaApontada;
            casaApontada.setApontado(true);

        }catch (NullPointerException e){
            // nenhuma casa apontada
        }
    }

    public Casa getCasaSelecionada() {
        return casaSelecionada;
    }

    public void setCasaSelecionada(Casa casaSelecionada) {

        if(this.casaSelecionada != null)
            this.casaSelecionada.setSelecionado(false);

        this.casaSelecionada = casaSelecionada;

        if(this.casaSelecionada != null)
            casaSelecionada.setSelecionado(true);

        this.casaSelecionada = casaSelecionada;
    }

    public void zeraCasasAmeacadas (){
        for (Casa c : getCasas()) {
            c.zeraAlvoDePecas();
        }
    }

    public void reset() {
        casaApontada = null;
        casaSelecionada = null;

        for (Casa[] cc : casas) {
            for (Casa c : cc) {
                c.setPeca(null);
            }
        }
    }
}
