package Classes.Elementos.Pecas;

import Classes.Elementos.Casa;
import Classes.Elementos.Tabuleiro;
import Classes.Logica_Jogo;
import Classes.Util.CoordenadasCasas;
import Classes.Util.ElementoGrafico;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Peca extends ElementoGrafico {

    protected Casa casaAtual;
    protected ArrayList<Casa> casasDisponiveis;
    protected CoordenadasCasas coordenadasCasa;
    protected final Tabuleiro tabuleiro;
    protected final char cor, classe;
    protected BufferedImage bufferedImage;
    protected int movimentos;

    public Peca(Tabuleiro tabuleiro, char classe, char cor) {
        this.tabuleiro = tabuleiro;
        this.cor = cor;
        this.classe = classe;
        this.setAtiva(false);

        try {
            bufferedImage = ImageIO.read(new File("src/Recursos/Imagens/Pecas/padrao.png"));
        }
        catch (IOException e){
            System.out.println("Class Pecas: Erro ao ler bufferedImg" + e);
        }

        addImg(classe, cor);
    }

    public void filtraCasasDisponiveis(){

        Set<Peca> pecasInimigas = new HashSet<>(this.casaAtual.getAlvoDePecasInimigas(this.cor));

        if (pecasInimigas.isEmpty()){
            return;
        }

        for (Peca p : pecasInimigas ) {
            for (Casa c : p.casasDisponiveis) {

                if(c.getPeca() instanceof Rei && c.getPeca().cor == this.cor){
                    return;
                }
            }
        }

        // se minha peça estiver entre ela e o rei
        casaAtual.setPeca(null);
        for (Peca p : pecasInimigas ){
            p.atualizarCasasDisponiveis();
            for (Casa c : p.casasDisponiveis) {

                if(c.getPeca() instanceof Rei && c.getPeca().cor == this.cor){
                    casaAtual.setPeca(this);
                    p.atualizarCasasDisponiveis();
                    filtra(p, c.getPeca());
                    return;
                }

            }
        }
        casaAtual.setPeca(this);

    }

    public void filtra(Peca p, Peca rei){

        Casa minhaCasa = this.casaAtual;

        Set<Casa> cDisponiveis = new HashSet<>(this.casasDisponiveis);

        for (Casa casaDisponivel : cDisponiveis) {

            if (casaDisponivel == p.casaAtual){
                break;
            }

            if(casaDisponivel.getPeca() != null && casaDisponivel.getPeca() != p){
                casasDisponiveis.remove(casaDisponivel);
            }

            this.move(casaDisponivel);
            p.atualizarCasasDisponiveis();

            for (Casa c : p.casasDisponiveis) {

                if(c.getPeca() == rei){
                    casasDisponiveis.remove(casaDisponivel);
                    break;
                }

            }

            this.move(minhaCasa);
        }

        // resolvendo um bug estranho
        if(casasDisponiveis.contains(p.getCasaAtual())){
            casasDisponiveis.add(p.getCasaAtual());
        }

    }

    public int saidaDeXeque (Peca rei){

        Set<Peca> pecasAmeaca = new HashSet<>(rei.getCasaAtual().getAlvoDePecasInimigas(rei.getCor()));

        int nSaidas = 0;
        /*
        if (this.equals(rei)){
            for (Casa c : rei.casasDisponiveis) {
                nSaidas++;
            }
            return nSaidas;
        }
        */

        Casa minhaCasa = this.casaAtual;
        Peca pecaInimiga = null;
        Casa casaInimiga = null;

        Set<Casa> cDisponiveis = new HashSet<>();

        if (this.casasDisponiveis != null)
            cDisponiveis = new HashSet<>(this.casasDisponiveis);

        for (Casa casaDisponivel : cDisponiveis) {

            if(casaDisponivel.getPeca() != null){
                if(pecasAmeaca.size() == 1 && pecasAmeaca.contains(casaDisponivel.getPeca())){
                    nSaidas++;
                    continue;
                }

                pecaInimiga = casaDisponivel.getPeca();
                casaInimiga = casaDisponivel;

                casaInimiga.setPeca(null);
            }

            this.move(casaDisponivel);

            Logica_Jogo.atualizarPecas(rei.getCor() == 'b' ? 'p' : 'b');
            Logica_Jogo.examina_Xeque();

            if (rei.getCor() == 'b' && Logica_Jogo.isBrancas_Xeque()) {
                casasDisponiveis.remove(casaDisponivel);
            }else
            if (rei.getCor() == 'p' && Logica_Jogo.isPretas_Xeque()) {
                casasDisponiveis.remove(casaDisponivel);
            }else {
                nSaidas++;
            }

            this.move(minhaCasa);
            if(pecaInimiga != null) {
                pecaInimiga.move(casaInimiga);
            }

            Logica_Jogo.atualizarPecas(rei.getCor() == 'b' ? 'p' : 'b');
            Logica_Jogo.examina_Xeque();
        }
        return nSaidas;
    }

    public void move( Casa c ){

        if(c.getPeca() == null) {

            if (casaAtual != null)
                casaAtual.setPeca(null);

            setAtiva(true);
            setCoordenada2D(c.getCoordenada2D());
            casaAtual = c;
            coordenadasCasa = c.getCoordenadaCasa();
            setCoordenada2D(c.getCoordenada2D());

            c.setPeca(this);
        }

    }

    public void capturada (){

        setAtiva(false);
        casaAtual = null;
        coordenadasCasa = null;
        casasDisponiveis = null;
    }

    protected void addImg (char classe, char cor) {
        int x = 0;
        int y = 0;

        if(cor == 'b'){
            y = 150;
        }else if(cor == 'p'){
            y = 0;
        }else{
            try {
                throw new Exception("Class Peca: erro ao add img");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        switch (classe) {
            case 'P':
                x = 750;
                break;
            case 'T':
                x = 300;
                break;
            case 'C':
                x = 600;
                break;
            case 'B':
                x = 450;
                break;
            case 'D':
                x = 150;
                break;
            case 'R':
                x = 0;
                break;
        }

        setImage(bufferedImage.getSubimage(x,y,150,150));
    }

    public void incrementaMovimentos (){
        movimentos++;
    }

    public void decrementaMovimentos (){
        movimentos--;
    }

    public int getMovimentos() {
        return movimentos;
    }

    public abstract void atualizarCasasDisponiveis();

    public Casa getCasaAtual() {
        return casaAtual;
    }

    public ArrayList<Casa> getCasasDisponiveis() {
        return casasDisponiveis;
    }

    public CoordenadasCasas getCoordenadasCasa() {
        return coordenadasCasa;
    }

    public char getCor() {
        return cor;
    }

    public char getClasse() {
        return classe;
    }

    @Override
    public String toString() {
        return getClass() + "{" +
                "coordenada=" + getCoordenadasCasa() +
                "x=" + getX() +
                "y=" + getY() +
                "largura=" + getLargura() +
                "Altura=" + getAltura() +
                ", cor=" + cor +
                '}';
    }
}
