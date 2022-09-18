package Classes.Elementos.Pecas;

import Classes.Elementos.Casa;
import Classes.Elementos.Tabuleiro;
import Classes.Util.CoordenadasCasas;

import java.util.ArrayList;

public class Cavalo extends Peca{
    public Cavalo(Tabuleiro tabuleiro, char cor) {
        super(tabuleiro, 'C', cor);
    }

    @Override
    public void atualizarCasasDisponiveis() {

        if(casaAtual != null && this.isAtiva()){
            int l = coordenadasCasa.getLinha();
            int c = coordenadasCasa.getColuna();

            casasDisponiveis = new ArrayList<>();

            examina((l + 2), (c + 1));
            examina((l + 2), (c - 1));
            examina((l - 2), (c + 1));
            examina((l - 2), (c - 1));
            examina((l + 1), (c + 2));
            examina((l + 1), (c - 2));
            examina((l - 1), (c + 2));
            examina((l - 1), (c - 2));
        }
    }

    public void examina (int l, int c){
        if(0 <=l && l <= 7 && 0 <=c && c <= 7) {
            Casa possivelCasa = tabuleiro.getCasa(new CoordenadasCasas(l, c));
            possivelCasa.incrementaAlvoDePecas(this);
            if (possivelCasa.getPeca() == null)
                casasDisponiveis.add(possivelCasa);
            else if (possivelCasa.getPeca().cor != cor)
                casasDisponiveis.add(possivelCasa);
        }
    }
}
