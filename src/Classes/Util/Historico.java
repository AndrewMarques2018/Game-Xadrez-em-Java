package Classes.Util;

import Classes.Elementos.Casa;
import Classes.Elementos.Pecas.Peca;
import Classes.Logica_Jogo;
import Classes.Util.input.Mouse;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Historico {

    private static int numeroJogadas;
    private static ArrayList<String> jogadasEmNotacao = new ArrayList<>();
    private static Image aba = new ImageIcon("src/Recursosos/Imagens/Historico/aba.png").getImage();
    private static Image abaAberta = new ImageIcon("src/Recursos/Imagens/Historico/abaAberta.png").getImage();
    private static Botao cetinha = new Botao("src/Recursos/Imagens/Historico/cetinha.png");
    private static boolean abreAba = false;

    private static int sizeTexto = (int)(Logica_Jogo.getLargura()*0.025);
    private static final int largura = Logica_Jogo.getLargura();
    private static final int altura = Logica_Jogo.getAltura();

    // criar um array tridimencional que representa a situaçao do Jogo:
    // posicionamento das peças
    private static ArrayList<String[][]> situacao = new ArrayList<>();
    private static String[][] tabuleiroInterDimencional = {    // linha - coluna
            {"Tb", "Cb", "Bb", "Db", "Rb", "Bb", "Cb", "Tb"},
            {"Pb", "Pb", "Pb", "Pb", "Pb", "Pb", "Pb", "Pb"},
            {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
            {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
            {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
            {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
            {"Tp", "Cp", "Bp", "Dp", "Rp", "Bp", "Cp", "Tp"},
            {"Pp", "Pp", "Pp", "Pp", "Pp", "Pp", "Pp", "Pp"} };

    public static void voltarJogada (){

    }

    public static void clicked(){
        if(Mouse.clicaEm(cetinha)) {
            abreAba = !abreAba;
            if(abreAba){
                cetinha.setLocale((int) (largura * 0.15),(int)(Logica_Jogo.getAltura()*0.425),
                        (int)(largura*0.025),(int)(largura*0.1));
            }else{
                cetinha.setLocale((int)(largura*0.01),(int)(Logica_Jogo.getAltura()*0.425),
                        (int)(largura*0.025),(int)(largura*0.1));
            }
        }
    }

    private static int whell;
    public static void mostraHistorico (Graphics g){
        // capacidade maxia 30
        if(abreAba){

            g.drawImage(abaAberta, 0, 0, (int) (largura * 0.2), altura, null);

            g.setColor(Color.black);
            g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, (int)(sizeTexto)));

            g.drawString("Historico", (int) (largura * 0.025), (int)(sizeTexto*2.5));
            if(jogadasEmNotacao.size() > 0)
                if(jogadasEmNotacao.size() >= 26){

                    if(Mouse.apontaEm(0,0,(int) (largura * 0.2), altura)){
                        // gira para cima well -1, bara baixo whell 1 falta incrementar apartir disto
                        whell -= Mouse.getWeel();
                    }

                    int n0, nf;
                    n0 = jogadasEmNotacao.size()-1 + whell;
                    if (jogadasEmNotacao.size()-1 < n0)
                        n0 = jogadasEmNotacao.size()-1;
                    if (n0 - 30 < 0)
                        n0 = 30;

                    nf = n0-30;

                    if(whell > jogadasEmNotacao.size()-1 - 30)
                        whell = jogadasEmNotacao.size()-1 - 30;
                    else if (whell < 30- jogadasEmNotacao.size()-1)
                        whell = 30- jogadasEmNotacao.size()-1;


                    for (int i = nf; i < n0; i++) {
                        g.drawString(jogadasEmNotacao.get(i), (int) (largura * 0.025)
                                , sizeTexto * (n0-i+3));
                    }
                }else {
                    for (int i = jogadasEmNotacao.size() - 1; i >= 0; i--) {
                        g.drawString(jogadasEmNotacao.get(i), (int) (largura * 0.025)
                                , sizeTexto * (jogadasEmNotacao.size() + 3 - i));
                    }
                }
            cetinha.desenha(g);
        }else {
            g.drawImage(aba, 0, 0, (int) (largura * 0.05), altura, null);
            cetinha.desenha(g);
        }
    }

    public static void addSituacao (Peca peca, Casa casaDestino){

        int l = peca.getCoordenadasCasa().getLinha();
        int c = peca.getCoordenadasCasa().getColuna();
        tabuleiroInterDimencional[l][c] = "|  |";

        l = casaDestino.getCoordenadaCasa().getLinha();
        c = casaDestino.getCoordenadaCasa().getColuna();
        tabuleiroInterDimencional[l][c] = "|" + peca.getClasse() + peca.getCor() + "|";

        situacao.add(numeroJogadas, tabuleiroInterDimencional);
    }

    private static String slot1;
    public static void anotarJogada(Peca peca, Casa casaDestino, Peca pecaCapturada, String tipoMov) {

        numeroJogadas++;
        addSituacao(peca, casaDestino);
        StringBuilder sb;

        // ex1: 1.g3 e5
        // ex2: 24.Txg3 Rc7
        // ex3: 8.Cxd2 O-O
        // jogada das brancas - slot 1

        if (numeroJogadas % 2 != 0) {

            sb = new StringBuilder();
            // 1 - add numero da rodada: 24.
            sb.append((int) Math.floor(numeroJogadas / 2) + 1).append('.');

            if (tipoMov == "rock") {
                sb.append("O-O ");

            }else if (tipoMov == "grand_rock") {
                sb.append("O-O-O ");

            }else if (tipoMov == "captura" && peca.getClasse() == 'P') {
                sb.append(peca.getCoordenadasCasa().getLetraColuna()).append('x');
                sb.append(casaDestino.getCoordenadaCasa().getLetraColuna())
                        .append(casaDestino.getCoordenadaCasa().getLinha()+1).append(' ');

            }else if (tipoMov == "en_passant") {
                sb.append(peca.getCoordenadasCasa().getLetraColuna()).append('x');
                sb.append(casaDestino.getCoordenadaCasa().getLetraColuna()).append(casaDestino.getCoordenadaCasa().getLinha()+1).append(' ');

            }else if(tipoMov == "promocao"){
                sb.append(casaDestino.getCoordenadaCasa().getLetraColuna()).append(casaDestino.getCoordenadaCasa().getLinha()+1);
                sb.append("=").append(peca.getClasse()).append(' ');

            }else {
                // pegar o tipo da peca: T
                if (peca.getClasse() != 'P')
                    sb.append(peca.getClasse());

                // pegar tipo de movimento: x (captura)
                if (tipoMov == "captura") {
                    sb.append('x');
                }

                // add coluna e linha: g3
                sb.append(casaDestino.getCoordenadaCasa().getLetraColuna()).append(casaDestino.getCoordenadaCasa().getLinha()+1).append(' ');

            }
            // salvar slot1
            slot1 = sb.toString();

        } else {// jogada das pretas - slot 2

            sb = new StringBuilder();
            sb.append(slot1);

            if (tipoMov == "rock") {
                sb.append("O-O ");

            }else if (tipoMov == "grand_rock") {
                sb.append("O-O-O ");

            }else if (tipoMov == "captura" && peca.getClasse() == 'P') {
                sb.append(peca.getCoordenadasCasa().getLetraColuna()).append('x');
                sb.append(casaDestino.getCoordenadaCasa().getLetraColuna())
                        .append(casaDestino.getCoordenadaCasa().getLinha()+1).append(' ');

            }else if (tipoMov == "en_passant") {
                sb.append(peca.getCoordenadasCasa().getLetraColuna()).append('x');
                sb.append(casaDestino.getCoordenadaCasa().getLetraColuna()).append(casaDestino.getCoordenadaCasa().getLinha()+1).append(' ');

            }else if(tipoMov == "promocao"){
                sb.append(casaDestino.getCoordenadaCasa().getLetraColuna()).append(casaDestino.getCoordenadaCasa().getLinha()+1);
                sb.append("=").append(peca.getClasse()).append(' ');

            }else {

                // pegar o tipo da peca: T
                if (peca.getClasse() != 'P')
                    sb.append(peca.getClasse());

                // pegar tipo de movimento: x (captura)
                if (tipoMov == "captura") {
                    sb.append('x');
                }

                // add coluna e linha: g3
                sb.append(casaDestino.getCoordenadaCasa().getLetraColuna()).append(casaDestino.getCoordenadaCasa().getLinha()+1).append(' ');

            }
            // salvar slot1
            jogadasEmNotacao.add(sb.toString());
        }
    }

    public static void setSituaçaoInicial(){

        cetinha.setLocale((int)(largura*0.01),(int)(altura*0.425),
                (int)(largura*0.025),(int)(largura*0.1));

        numeroJogadas = 0;

        tabuleiroInterDimencional = new String[][]{
                {"|Tb|", "|Cb|", "|Bb|", "|Db|", "|Rb|", "|Bb|", "|Cb|", "|Tb|"},
                {"|Pb|", "|Pb|", "|Pb|", "|Pb|", "|Pb|", "|Pb|", "|Pb|", "|Pb|"},
                {"|  |", "|  |", "|  |", "|  |", "|  |", "|  |", "|  |", "|  |"},
                {"|  |", "|  |", "|  |", "|  |", "|  |", "|  |", "|  |", "|  |"},
                {"|  |", "|  |", "|  |", "|  |", "|  |", "|  |", "|  |", "|  |"},
                {"|  |", "|  |", "|  |", "|  |", "|  |", "|  |", "|  |", "|  |"},
                {"|Tp|", "|Cp|", "|Bp|", "|Dp|", "|Rp|", "|Bp|", "|Cp|", "|Tp|"},
                {"|Pp|", "|Pp|", "|Pp|", "|Pp|", "|Pp|", "|Pp|", "|Pp|", "|Pp|"} };

        jogadasEmNotacao = new ArrayList<>();
        situacao = new ArrayList<>();
        situacao.add(tabuleiroInterDimencional);
    }

    public static String getSituacaoAtual(){
        return getSituacao(numeroJogadas);
    }

    public static String getSituacao(int jogada){

        StringBuilder sb = new StringBuilder();
        sb.append("Jogada ").append(jogada).append(":").append('\n');
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                sb.append(situacao.get(jogada)[i][j]);
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    public static ArrayList<String> getJogadasEmNotacao(){
        return jogadasEmNotacao;
    }

    public static void setSizeTexto(int sizeTexto) {
        Historico.sizeTexto = sizeTexto;
    }
}
