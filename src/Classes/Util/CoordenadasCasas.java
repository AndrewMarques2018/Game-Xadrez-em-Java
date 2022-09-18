package Classes.Util;

public class CoordenadasCasas {

    private int linha, coluna;

    public CoordenadasCasas(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public CoordenadasCasas() {
    }

    public void setCoordenadas(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public CoordenadasCasas getCoordenadas (){
        return this;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public char getLetraColuna (){
        return (char) (97 + coluna);
    }

    @Override
    public String toString() {
        return "CoordenadasJogo{" +
                "linha=" + linha +
                ", coluna=" + coluna +
                '}';
    }
}
