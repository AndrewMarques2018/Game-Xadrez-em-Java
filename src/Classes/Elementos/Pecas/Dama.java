package Classes.Elementos.Pecas;

import Classes.Elementos.Casa;
import Classes.Elementos.Tabuleiro;
import Classes.Util.CoordenadasCasas;

import java.util.ArrayList;

public class Dama extends Peca{

    public Dama(Tabuleiro tabuleiro, char cor) {
        super(tabuleiro, 'D', cor);
    }

    @Override
    public void atualizarCasasDisponiveis() {

        if(casaAtual != null && this.isAtiva()){

            int l = coordenadasCasa.getLinha();
            int c = coordenadasCasa.getColuna();

            casasDisponiveis = new ArrayList<>();

            // horizontal e vertical
            for (int i = l+1; i <= 7; i++) {
                if(!examina(i,c))
                    break;
            }
            for (int i = l-1; i >= 0; i--) {
                if(!examina(i,c))
                    break;
            }
            for (int i = c+1; i <= 7; i++) {
                if(!examina(l,i))
                    break;
            }
            for (int i = c-1; i >= 0; i--) {
                if(!examina(l,i))
                    break;
            }


            // diagonal
            for (int i = 1; i <= 7; i++) {
                if(!examina(l + i, c + i))
                    break;

            }

            for (int i = 1; i <= 7; i++) {
                if(!examina(l + i, c - i))
                    break;
            }
            for (int i = 1; i <= 7; i++) {
                if(!examina(l - i, c + i))
                    break;
            }
            for (int i = 1; i <= 7; i++) {
                if(!examina(l - i, c - i))
                    break;
            }

        }else{
            casasDisponiveis = null;
        }
    }

    public boolean examina (int l, int c){

        try {
            Casa possivelCasa = tabuleiro.getCasa(new CoordenadasCasas(l,c));
            if(possivelCasa.getPeca() == null){
                casasDisponiveis.add(possivelCasa);
                possivelCasa.incrementaAlvoDePecas(this);
                return true;
            }else {
                if (possivelCasa.getPeca().cor != this.cor) {
                    casasDisponiveis.add(possivelCasa);
                    possivelCasa.incrementaAlvoDePecas(this);
                    return false; // para
                }else{
                    possivelCasa.incrementaAlvoDePecas(this);
                }
                return false; // para
            }
        }catch (ArrayIndexOutOfBoundsException e){
            return false; // para
        }
    }

}
