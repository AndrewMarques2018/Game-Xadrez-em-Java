package Classes.Util;

public abstract class Coordenada2D {

    int x, y;
    int largura, altura;

    public Coordenada2D() {

    }

    public Coordenada2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordenada2D(int x, int y, int largura, int altura) {
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
    }

    public void setCoordenada2D(Coordenada2D c){
        this.x = c.x;
        this.y = c.y;
        this.largura = c.largura;
        this.altura = c.altura;
    }

    public void setLocal(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setDimensao(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
    }

    public Coordenada2D getCoordenada2D(){
        return this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }
}
