package Classes.Elementos.Pecas;

import Classes.Elementos.Casa;
import Classes.Elementos.Tabuleiro;
import Classes.Util.CoordenadasCasas;

import java.util.ArrayList;

public class Bispo extends Peca{

    public Bispo(Tabuleiro tabuleiro, char cor) {
        super(tabuleiro, 'B', cor);
    }

    @Override
    public void atualizarCasasDisponiveis() {

        if(casaAtual != null && this.isAtiva()){
            int l = coordenadasCasa.getLinha();
            int c = coordenadasCasa.getColuna();

            casasDisponiveis = new ArrayList<>();

            for (int i = 1; i <= 7; i++) {
                if (!examina(l + i, c + i))
                    break;

            }
            for (int i = 1; i <= 7; i++) {
                if (!examina(l + i, c - i))
                    break;
            }
            for (int i = 1; i <= 7; i++) {
                if (!examina(l - i, c + i))
                    break;
            }
            for (int i = 1; i <= 7; i++) {
                if (!examina(l - i, c - i))
                    break;
            }
        }
    }

    public boolean examina (int l, int c){

        try {
            Casa possivelCasa = tabuleiro.getCasa(new CoordenadasCasas(l, c));
            if(possivelCasa.getPeca() == null){
                casasDisponiveis.add(possivelCasa);
                possivelCasa.incrementaAlvoDePecas(this);
                return true; // continua
            }else {
                if (possivelCasa.getPeca().cor != cor) {
                    casasDisponiveis.add(possivelCasa);
                    possivelCasa.incrementaAlvoDePecas(this);
                    return false; // para
                }
                possivelCasa.incrementaAlvoDePecas(this);
                return false; // para
            }
        }catch (ArrayIndexOutOfBoundsException e){
            return false; // para
        }
    }
}
