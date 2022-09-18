package Classes.Elementos.Pecas;

import Classes.Elementos.Casa;
import Classes.Elementos.Tabuleiro;
import Classes.Util.CoordenadasCasas;

import java.util.ArrayList;

public class Rei extends Peca{

    public Rei(Tabuleiro tabuleiro, char cor) {
        super(tabuleiro, 'R', cor);
    }

    @Override
    public void atualizarCasasDisponiveis() {

        if(casaAtual != null && this.isAtiva()) {
            int l = coordenadasCasa.getLinha();
            int c = coordenadasCasa.getColuna();

            casasDisponiveis = new ArrayList<>();

            examina(l, c + 1);
            examina(l, c - 1);
            examina(l + 1, c);
            examina(l - 1, c);
            examina(l + 1, c + 1);
            examina(l + 1, c - 1);
            examina(l - 1, c + 1);
            examina(l - 1, c - 1);

            if (movimentos == 0) {
                examinaRock();
                examinaGrand_rock();
            }
        }
    }

    public void examinaRock(){
        int l = this.getCoordenadasCasa().getLinha();
        int c = this.getCoordenadasCasa().getColuna();

        if (this.tabuleiro.getCasa(l, c + 1).getPeca() != null)
            return;

        if (this.tabuleiro.getCasa(l, c + 2).getPeca() != null)
            return;

        if (this.tabuleiro.getCasa(l, c + 3).getPeca() == null ||
                this.tabuleiro.getCasa(l, c + 3).getPeca().getMovimentos() != 0)
            return;

        casasDisponiveis.add(tabuleiro.getCasa(l,c+2));

    }

    public void examinaGrand_rock(){

        int l = this.getCoordenadasCasa().getLinha();
        int c = this.getCoordenadasCasa().getColuna();

        if (this.tabuleiro.getCasa(l, c - 1).getPeca() != null)
            return;

        if (this.tabuleiro.getCasa(l, c - 2).getPeca() != null)
            return;

        if (this.tabuleiro.getCasa(l, c - 3).getPeca() != null)
            return;

        if (this.tabuleiro.getCasa(l, c - 4).getPeca() == null ||
                this.tabuleiro.getCasa(l, c - 4).getPeca().getMovimentos() != 0)
            return;

        casasDisponiveis.add(tabuleiro.getCasa(l,c-2));

    }

    public void examina (int l, int c){
        if(0 <=l && l <= 7 && 0 <=c && c <= 7) {
            Casa possivelCasa = tabuleiro.getCasa(new CoordenadasCasas(l, c));

            possivelCasa.incrementaAlvoDePecas(this);

            if(possivelCasa.getAlvoDePecasInimigas(this.cor).size() > 0){
                return;
            }

            if (possivelCasa.getPeca() == null) {
                casasDisponiveis.add(possivelCasa);
            }else if (possivelCasa.getPeca().cor != cor ) {
                casasDisponiveis.add(possivelCasa);
            }

        }
    }
}
