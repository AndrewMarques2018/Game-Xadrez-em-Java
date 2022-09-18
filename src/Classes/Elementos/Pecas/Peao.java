package Classes.Elementos.Pecas;

import Classes.Elementos.Casa;
import Classes.Elementos.Tabuleiro;
import Classes.Logica_Jogo;
import Classes.Util.CoordenadasCasas;

import java.util.ArrayList;

public class Peao extends Peca{

    public Peao(Tabuleiro tabuleiro, char cor) {
        super(tabuleiro, 'P', cor);
    }

    @Override
    public void atualizarCasasDisponiveis() {

        if(casaAtual != null && this.isAtiva()) {
            int l = coordenadasCasa.getLinha();
            int c = coordenadasCasa.getColuna();

            casasDisponiveis = new ArrayList<>();

            int incremment = (cor == 'b') ? 1 : -1;

            if (examinaFrente(l + incremment, c) && movimentos == 0) {
                examinaFrente(l + incremment * 2, c);
            }

            examinaDiagonal(l + incremment, c - 1);
            examinaDiagonal(l + incremment, c + 1);

            examinaEmPassant();
        }
    }

    private void examinaDiagonal (int l, int c){
        if(0 <=l && l <= 7 && 0 <=c && c <= 7) {
            Casa possivelCasa = tabuleiro.getCasa(new CoordenadasCasas(l, c));
            possivelCasa.incrementaAlvoDePecas(this);
            if (possivelCasa.getPeca() != null)
                if (possivelCasa.getPeca().cor != cor){
                    casasDisponiveis.add(possivelCasa);
                }
        }
    }

    private boolean examinaFrente (int l, int c){
        if(0 <=l && l <= 7 && 0 <=c && c <= 7) {
            Casa possivelCasa = tabuleiro.getCasa(new CoordenadasCasas(l, c));
            if (possivelCasa.getPeca() == null) {
                casasDisponiveis.add(possivelCasa);
                return true;
            }
        }
        return false;
    }

    public void examinaEmPassant (){

        if (Logica_Jogo.getPossivelEmPassant() == null)
            return;

        Peao p = Logica_Jogo.getPossivelEmPassant();

        if (p.cor != this.cor && p.coordenadasCasa.getLinha() == this.coordenadasCasa.getLinha()) {
            if (Math.abs(p.coordenadasCasa.getColuna() - this.coordenadasCasa.getColuna()) == 1) {

                int incremment = p.cor == 'b' ? -1 : 1;
                if(tabuleiro.getCasa(p.coordenadasCasa.getLinha() + incremment, p.coordenadasCasa.getColuna()).getPeca() == null) {
                    casasDisponiveis.add(tabuleiro.getCasa(p.coordenadasCasa.getLinha() + incremment, p.coordenadasCasa.getColuna()));
                }

            }
        }
    }

}
