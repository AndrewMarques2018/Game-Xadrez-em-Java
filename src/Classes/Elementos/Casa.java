package Classes.Elementos;

import Classes.Elementos.Pecas.Peca;
import Classes.Util.CoordenadasCasas;
import Classes.Util.ElementoGrafico;

import java.awt.*;
import java.util.ArrayList;

public class Casa extends ElementoGrafico {

    private final CoordenadasCasas coordenada;
    private ArrayList<Peca> alvoDePecasBrancas, alvoDePecasPretas;
    private boolean selecionado, apontado;
    private Peca peca;

    public Casa (int linha, int coluna) {

        coordenada = new CoordenadasCasas();
        coordenada.setCoordenadas(linha, coluna);
        alvoDePecasBrancas = new ArrayList<>();
        alvoDePecasPretas = new ArrayList<>();
    }

    public void redimenciona (int x, int y, int largura, int altura){

        setLocal(x, y);
        setDimensao(largura, altura);
    }

    @Override
    public void desenha(Graphics g){
        if(selecionado){
            super.mostraAreaDesenho(g, new Color(255,255,0,95));
        }else
        if(apontado){
            super.mostraAreaDesenho(g, new Color(255,255,0,70));
        }
    }
    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    public void setApontado(boolean apontado) {
        this.apontado = apontado;
    }

    public CoordenadasCasas getCoordenadaCasa() {
        return coordenada;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public void incrementaAlvoDePecas(Peca p){
        if(p.getCor() == 'b') {
            alvoDePecasBrancas.add(p);
        }else{
            alvoDePecasPretas.add(p);
        }
    }

    public void zeraAlvoDePecas(){
        alvoDePecasPretas = new ArrayList<>();
        alvoDePecasBrancas = new ArrayList<>();
    }


    public ArrayList<Peca> getAlvoDePecasInimigas(char cor ) {
        return cor == 'b' ? alvoDePecasPretas : alvoDePecasBrancas;
    }

    public ArrayList<Peca> getAlvoDePecasBrancas() {
        return alvoDePecasBrancas;
    }

    public ArrayList<Peca> getAlvoDePecasPretas() {
        return alvoDePecasPretas;
    }

    @Override
    public String toString() {
        return "Casa{" +
                "coordenada=" + coordenada +
                "x=" + getX() +
                "y=" + getY() +
                "largura=" + getLargura() +
                "Altura=" + getAltura() +
                '}';
    }


}
