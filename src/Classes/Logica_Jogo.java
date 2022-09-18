package Classes;

import Classes.Elementos.Casa;
import Classes.Elementos.Pecas.*;
import Classes.Elementos.Tabuleiro;
import Classes.Util.*;
import Classes.Util.input.KeyBoard;
import Classes.Util.input.Mouse;
import Classes.Util.output.Window;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Logica_Jogo {

    private final int fps = 25;
    private static final int largura = 950;
    private static final int altura = 700;

    private static Window window;
    private final KeyBoard keyboard;
    private final Mouse mouse;
    private final Sound musicaGame, musicaMenu, mov, movEspecial, captura, promocao;

    private Botao sair, reiniciar, voltar, sons, musica;
    private ArrayList<Botao> botoes;
    private final MyPopUp popUpConfirma, popUpPromocao;


    private static Tabuleiro tabuleiro;
    private final ElementoGrafico mesa;

    private static Peca[] pecasBrancas, pecasPretas;
    private ArrayList<Peca> pretasCapturadas, brancasCapturadas;
    private static boolean brancas_Xeque, pretas_Xeque;
    private static char vezDeQuem = 'b', vencedor = ' ';
    private static Peao possivelEmPassant;

    public Logica_Jogo() {

        window = new Window("Xadrez", largura, altura);
        keyboard = new KeyBoard();
        mouse = new Mouse();

        window.addKeyListener(keyboard);
        window.addMouseListener(mouse);
        window.addMouseMotionListener(mouse);
        window.addMouseWheelListener(mouse);

        keyboard.addTeclas("Escape_KEY", 27);
        keyboard.addTeclas("Enter_KEY", 10);

        musicaMenu = new Sound("src\\Recursos\\Sons\\Bach.wav", true);
        musicaGame = new Sound("src\\Recursos\\Sons\\Canon.wav", true);
        mov = new Sound("src\\Recursos\\Sons\\mov.wav", false);
        movEspecial = new Sound("src\\Recursos\\Sons\\mov.wav", false);
        captura = new Sound("src\\Recursos\\Sons\\captura.wav", false);
        promocao = new Sound("src\\Recursos\\Sons\\promocao.wav", false);


        popUpConfirma = new MyPopUp((int)(window.getWidth()*0.175),(int)(window.getWidth()*0.1), (int)(window.getWidth()*0.65), (int)(window.getHeight()*0.6));
        popUpConfirma.addBotoes(
                new Botao("src/Recursos/Imagens/PopUp/confirma.png","src/Recursos/Imagens/PopUp/confirmaSelect.png"),
                new Botao("src/Recursos/Imagens/PopUp/naoConfirma.png","src/Recursos/Imagens/PopUp/naoConfirmaSelect.png")
        );

        popUpPromocao = new MyPopUp((int)(window.getWidth()*0.175),(int)(window.getWidth()*0.1), (int)(window.getWidth()*0.65), (int)(window.getHeight()*0.6));
        popUpPromocao.addBotoes(
                new Botao("src/Recursos/Imagens/PopUp/cavalo.png","src/Recursos/Imagens/PopUp/cavaloSelect.png"),
                new Botao("src/Recursos/Imagens/PopUp/bispo.png", "src/Recursos/Imagens/PopUp/bispoSelect.png"),
                new Botao("src/Recursos/Imagens/PopUp/torre.png","src/Recursos/Imagens/PopUp/torreSelect.png"),
                new Botao("src/Recursos/Imagens/PopUp/rainha.png","src/Recursos/Imagens/PopUp/rainhaSelect.png")
        );

        criarBotoes();

        tabuleiro = new Tabuleiro(window.getWidth()/2 - 275,window.getHeight()/2 - 250,550,550);

        mesa = new ElementoGrafico();
        mesa.setImage(new ImageIcon("src\\Recursos\\Imagens\\Mesas\\Madeira.png").getImage());
        mesa.setLocal(0,0);
        mesa.setDimensao(window.getWidth(), window.getHeight());

        pretasCapturadas = new ArrayList<>();
        brancasCapturadas = new ArrayList<>();

        criarPecas();

        Historico.setSituaçaoInicial();
    }

    private void criarBotoes (){

        int tam = (int)(window.getWidth()*0.045);
        int x = (int)(window.getWidth()*0.92);
        int y = (int)(window.getHeight()*0.13);

        sair = new Botao("src/Recursos/Imagens/Botoes/sair.png");
        sair.setLocale(x, (int)(y - tam*0.2), tam, tam);
        sair.setImgApontado("src/Recursos/Imagens/Botoes/sairApontado.png");

        reiniciar = new Botao("src/Recursos/Imagens/Botoes/reiniciar.png");
        reiniciar.setLocale(x, (int)(y + tam*1.2), tam, tam);
        reiniciar.setImgApontado("src/Recursos/Imagens/Botoes/reiniciarApontado.png");

        voltar = new Botao("src/Recursos/Imagens/Botoes/voltar.png");
        voltar.setLocale(x, (int)(y + tam*2.4), tam, tam);
        voltar.setImgApontado("src/Recursos/Imagens/Botoes/voltarApontado.png");

        sons = new Botao("src/Recursos/Imagens/Botoes/sons.png");
        sons.setLocale(x, (int)(y + tam*3.6), tam, tam);
        sons.setImgApontado("src/Recursos/Imagens/Botoes/sonsSelecionado.png");
        sons.setImgSelecionado("src/Recursos/Imagens/Botoes/sonsSelecionado.png");

        musica = new Botao("src/Recursos/Imagens/Botoes/musica.png");
        musica.setLocale(x, (int)(y + tam*4.8), tam, tam);
        musica.setImgApontado("src/Recursos/Imagens/Botoes/musicaSelecionado.png");
        musica.setImgSelecionado("src/Recursos/Imagens/Botoes/musicaSelecionado.png");

        botoes = new ArrayList<>();
        botoes.add(reiniciar);
        botoes.add(voltar);
        botoes.add(sair);
        botoes.add(sons);
        botoes.add(musica);

    }

    public void criarPecas (){

        pecasBrancas = new Peca[16];
        pecasPretas = new Peca[16];

        pecasBrancas[0] = new Peao(tabuleiro, 'b');
        pecasBrancas[1] = new Peao(tabuleiro, 'b');
        pecasBrancas[2] = new Peao(tabuleiro, 'b');
        pecasBrancas[3] = new Peao(tabuleiro, 'b');
        pecasBrancas[4] = new Peao(tabuleiro, 'b');
        pecasBrancas[5] = new Peao(tabuleiro, 'b');
        pecasBrancas[6] = new Peao(tabuleiro, 'b');
        pecasBrancas[7] = new Peao(tabuleiro, 'b');

        pecasBrancas[8] = new Torre(tabuleiro, 'b');
        pecasBrancas[9] = new Torre(tabuleiro, 'b');

        pecasBrancas[10] = new Cavalo(tabuleiro, 'b');
        pecasBrancas[11] = new Cavalo(tabuleiro, 'b');

        pecasBrancas[12] = new Bispo(tabuleiro, 'b');
        pecasBrancas[13] = new Bispo(tabuleiro, 'b');

        pecasBrancas[14] = new Dama(tabuleiro, 'b');

        pecasBrancas[15] = new Rei(tabuleiro, 'b');

        pecasBrancas[0].move(tabuleiro.getCasa(new CoordenadasCasas(1, 0)));
        pecasBrancas[1].move(tabuleiro.getCasa(new CoordenadasCasas(1, 1)));
        pecasBrancas[2].move(tabuleiro.getCasa(new CoordenadasCasas(1, 2)));
        pecasBrancas[3].move(tabuleiro.getCasa(new CoordenadasCasas(1, 3)));
        pecasBrancas[4].move(tabuleiro.getCasa(new CoordenadasCasas(1, 4)));
        pecasBrancas[5].move(tabuleiro.getCasa(new CoordenadasCasas(1, 5)));
        pecasBrancas[6].move(tabuleiro.getCasa(new CoordenadasCasas(1, 6)));
        pecasBrancas[7].move(tabuleiro.getCasa(new CoordenadasCasas(1, 7)));

        pecasBrancas[8].move(tabuleiro.getCasa(new CoordenadasCasas(0, 0)));
        pecasBrancas[9].move(tabuleiro.getCasa(new CoordenadasCasas(0, 7)));

        pecasBrancas[10].move(tabuleiro.getCasa(new CoordenadasCasas(0, 1)));
        pecasBrancas[11].move(tabuleiro.getCasa(new CoordenadasCasas(0, 6)));

        pecasBrancas[12].move(tabuleiro.getCasa(new CoordenadasCasas(0, 2)));
        pecasBrancas[13].move(tabuleiro.getCasa(new CoordenadasCasas(0, 5)));

        pecasBrancas[14].move(tabuleiro.getCasa(new CoordenadasCasas(0, 3)));
        pecasBrancas[15].move(tabuleiro.getCasa(new CoordenadasCasas(0, 4)));

        pecasPretas[0] = new Peao(tabuleiro, 'p');
        pecasPretas[1] = new Peao(tabuleiro, 'p');
        pecasPretas[2] = new Peao(tabuleiro, 'p');
        pecasPretas[3] = new Peao(tabuleiro, 'p');
        pecasPretas[4] = new Peao(tabuleiro, 'p');
        pecasPretas[5] = new Peao(tabuleiro, 'p');
        pecasPretas[6] = new Peao(tabuleiro, 'p');
        pecasPretas[7] = new Peao(tabuleiro, 'p');

        pecasPretas[8] = new Torre(tabuleiro, 'p');
        pecasPretas[9] = new Torre(tabuleiro, 'p');

        pecasPretas[10] = new Cavalo(tabuleiro, 'p');
        pecasPretas[11] = new Cavalo(tabuleiro, 'p');

        pecasPretas[12] = new Bispo(tabuleiro, 'p');
        pecasPretas[13] = new Bispo(tabuleiro, 'p');

        pecasPretas[14] = new Dama(tabuleiro, 'p');

        pecasPretas[15] = new Rei(tabuleiro, 'p');

        pecasPretas[0].move(tabuleiro.getCasa(new CoordenadasCasas(6, 0)));
        pecasPretas[1].move(tabuleiro.getCasa(new CoordenadasCasas(6, 1)));
        pecasPretas[2].move(tabuleiro.getCasa(new CoordenadasCasas(6, 2)));
        pecasPretas[3].move(tabuleiro.getCasa(new CoordenadasCasas(6, 3)));
        pecasPretas[4].move(tabuleiro.getCasa(new CoordenadasCasas(6, 4)));
        pecasPretas[5].move(tabuleiro.getCasa(new CoordenadasCasas(6, 5)));
        pecasPretas[6].move(tabuleiro.getCasa(new CoordenadasCasas(6, 6)));
        pecasPretas[7].move(tabuleiro.getCasa(new CoordenadasCasas(6, 7)));

        pecasPretas[8].move(tabuleiro.getCasa(new CoordenadasCasas(7, 0)));
        pecasPretas[9].move(tabuleiro.getCasa(new CoordenadasCasas(7, 7)));

        pecasPretas[10].move(tabuleiro.getCasa(new CoordenadasCasas(7, 1)));
        pecasPretas[11].move(tabuleiro.getCasa(new CoordenadasCasas(7, 6)));

        pecasPretas[12].move(tabuleiro.getCasa(new CoordenadasCasas(7, 2)));
        pecasPretas[13].move(tabuleiro.getCasa(new CoordenadasCasas(7, 5)));

        pecasPretas[14].move(tabuleiro.getCasa(new CoordenadasCasas(7, 3)));
        pecasPretas[15].move(tabuleiro.getCasa(new CoordenadasCasas(7, 4)));

        atualizarPecas();
    }

    public void run() {

        long proxAtualizacao = 0;

        musicaGame.play();
        while (true) {

            // runtime fps millisegundos
            if (System.currentTimeMillis() >= proxAtualizacao) {

                if (keyboard.teclaIsAtiva("Escape_KEY")) {
                    window.exit();
                }

                mouseMoved();
                mouseClicked();

                // Area de deseno
                mesa.desenha(Window.graphics);
                tabuleiro.desenha(Window.graphics);

                if(tabuleiro.getCasaSelecionada() != null && tabuleiro.getCasaSelecionada().getPeca() != null){
                    mostrarCasasDisponiveis(Window.graphics, tabuleiro.getCasaSelecionada().getPeca());
                }

                desenharPecas(Window.graphics);
                mostraVezDeQuem(Window.graphics);

                for (Botao b: botoes) {
                    b.desenha(Window.graphics);
                }

                Historico.mostraHistorico(Window.graphics);

                window.update();

                proxAtualizacao = System.currentTimeMillis() + fps;
            }
        }
    }

    private void reiniciar (){

        pretasCapturadas = new ArrayList<>();
        brancasCapturadas = new ArrayList<>();

        tabuleiro.reset();
        criarPecas();
        vezDeQuem = 'b';
        vencedor = ' ';
        examina_Xeque();

        Historico.setSituaçaoInicial();
    }

    private void mouseMoved() {

        if(Mouse.isMoved()){
            tabuleiro.setCasaApontada(null);

            if (Mouse.apontaEm(tabuleiro)){
                for (Casa c : tabuleiro.getCasas() ) {
                    if(Mouse.apontaEm(c)){
                        tabuleiro.setCasaApontada(c);
                        break;
                    }
                }
            }

            for (Botao b: botoes) {
                b.setApontado(Mouse.apontaEm(b));
            }

        }
        Mouse.setMoved(false);
    }

    private void mouseClicked() {

        if(Mouse.isClickd()) {

            // se a casa selecionada esta ocupada
            if(tabuleiro.getCasaSelecionada() != null){
                if(tabuleiro.getCasaSelecionada().getPeca() != null) {
                    Peca p = tabuleiro.getCasaSelecionada().getPeca();

                    // se clickei em uma casa disponivel
                    if (p.getCasasDisponiveis() != null)
                    for (Casa c : p.getCasasDisponiveis()) {
                        if (Mouse.clicaEm(c)) {
                            moverPeca(p, c);
                            tabuleiro.setCasaApontada(null);
                            tabuleiro.setCasaSelecionada(null);
                            break;
                        }
                    }
                }
            }

            tabuleiro.setCasaSelecionada(tabuleiro.getCasaApontada());

        }

        if (Mouse.clicaEm(sair)) {
            System.out.println("sair");
            if(popUpConfirma.mostraPopUp(Window.graphics) == 0){
                window.exit();
            }

        }else
        if (Mouse.clicaEm(reiniciar)) {
            if(popUpConfirma.mostraPopUp(Window.graphics) == 0)
                reiniciar();
        }else
        if(Mouse.clicaEm(voltar)){

        }else
        if(Mouse.clicaEm(sons)) {
            sons.setSelecionado(!sons.isSelecionado());
            if(sons.isSelecionado()){
                sons.setImgApontado(sons.getImage());
            }else{
                sons.setImgApontado(sons.getImgSelecionado());
            }
        }else
        if (Mouse.clicaEm(musica)){
            musica.setSelecionado(!musica.isSelecionado());
            if(musica.isSelecionado()){
                musica.setImgApontado(musica.getImage());
                musicaGame.stop();
            }else{
                musica.setImgApontado(musica.getImgSelecionado());
                musicaGame.play();
            }
        }

        Historico.clicked();

        Mouse.setClickd(false);
    }

    public void moverPeca(Peca p, Casa c){

        if(vezDeQuem == p.getCor()) {
            String tipoMov = "mov";

            CoordenadasCasas origem = p.getCoordenadasCasa();
            CoordenadasCasas destino = c.getCoordenadaCasa();

            Peca pCapturada = (c.getPeca() == null) ? null : c.getPeca();

            if (pCapturada != null) {
                pCapturada.capturada();
                setPosPecaCapturda(pCapturada);
                if (pCapturada.getCor() == 'b') {
                    brancasCapturadas.add(pCapturada);
                } else {
                    pretasCapturadas.add(pCapturada);
                }
                c.setPeca(null);
                if (!sons.isSelecionado()){
                    captura.play();
                }
                tipoMov = "captura";
            }

            // examina movimentos especiais
            if(p instanceof Rei){
                if(p.getCoordenadasCasa().getColuna() - c.getCoordenadaCasa().getColuna() == -2){
                    tabuleiro.getCasa(p.getCoordenadasCasa().getLinha(), 7).getPeca()
                            .move(tabuleiro.getCasa(p.getCoordenadasCasa().getLinha(), 5));
                    tipoMov = "rock";
                }else
                if(p.getCoordenadasCasa().getColuna() - c.getCoordenadaCasa().getColuna() == 2){
                    tabuleiro.getCasa(p.getCoordenadasCasa().getLinha(), 0).getPeca()
                            .move(tabuleiro.getCasa(p.getCoordenadasCasa().getLinha(), 3));
                    tipoMov = "grand_rock";
                }
            }else
            if (p instanceof Peao){
                try {
                    if (possivelEmPassant != null){
                        if(c.getPeca() == null && Math.abs(p.getCoordenadasCasa().getColuna() - c.getCoordenadaCasa().getColuna()) == 1){
                            possivelEmPassant.getCasaAtual().setPeca(null);
                            possivelEmPassant.capturada();
                            setPosPecaCapturda(possivelEmPassant);
                            if (possivelEmPassant.getCor() == 'b') {
                                brancasCapturadas.add(possivelEmPassant);
                            } else {
                                pretasCapturadas.add(possivelEmPassant);
                            }
                            possivelEmPassant = null;
                            if (!sons.isSelecionado()){
                                captura.play();
                            }
                            tipoMov = "en_passant";
                        }
                    }
                }catch (Exception e){
                    // continue
                }

                // add possivel em passant
                if(Math.abs(p.getCoordenadasCasa().getLinha() - c.getCoordenadaCasa().getLinha()) == 2){
                    possivelEmPassant = (Peao) p;
                }else{
                    possivelEmPassant = null;
                }

                // examinar promocao
                if(c.getCoordenadaCasa().getLinha() == 0 || c.getCoordenadaCasa().getLinha() == 7){
                    p.getCasaAtual().setPeca(null);

                    Peca pecaPromovida;

                    switch (popUpPromocao.mostraPopUp(Window.graphics)){
                        case 0:
                            pecaPromovida= new Cavalo(tabuleiro, p.getCor());
                            break;
                        case 1:
                            pecaPromovida= new Bispo(tabuleiro, p.getCor());
                            break;
                        case 2:
                            pecaPromovida= new Torre(tabuleiro, p.getCor());
                            break;
                        case 3:
                            pecaPromovida= new Dama(tabuleiro, p.getCor());
                            break;

                        default:
                            pecaPromovida = new Dama(tabuleiro, p.getCor());
                    }

                    for (int i = 0; i < 8; i++) {
                        if (pecasBrancas[i].equals(p)){
                            pecasBrancas[i] = pecaPromovida;
                            p = pecasBrancas[i];
                        }else
                        if (pecasPretas[i].equals(p)){
                            pecasPretas[i] = pecaPromovida;
                            p = pecasPretas[i];
                        }
                    }
                    tipoMov = "promocao";
                }
            }

            Historico.anotarJogada(p, c, pCapturada, tipoMov);
            p.move(tabuleiro.getCasa(destino));
            p.incrementaMovimentos();

            if (!sons.isSelecionado()){
                mov.play();
            }

            trocaVez();

            atualizarPecas();

            examina_Xeque();

            if (vezDeQuem == 'b' && brancas_Xeque){
                System.out.println("xeque brancas");
                if(atualizarSaidaDeXeque(getRei('b')) == 0){
                    vencedor = 'p';
                    System.out.println("xeque Math");
                }
            }else
            if (vezDeQuem == 'p' && pretas_Xeque){
                System.out.println("xeque pretas");
                if(atualizarSaidaDeXeque(getRei('p')) == 0){
                    vencedor = 'b';
                    System.out.println("xeque Math");
                }
            }
        }
    }

    public void trocaVez(){
        vezDeQuem = vezDeQuem == 'b' ? 'p' : 'b';
    }

    public void desfazerMovimento(Peca p, CoordenadasCasas origem, Peca pCapturada, CoordenadasCasas destino){
        p.move(tabuleiro.getCasa(origem));
        if(pCapturada != null)
            pCapturada.move(tabuleiro.getCasa(destino));
    }

    public void mostrarCasasDisponiveis(Graphics g, Peca p){

        if (vezDeQuem == p.getCor()) {
            if (p.getCasaAtual() != null && p.getCasasDisponiveis() != null) {
                for (Casa c : p.getCasasDisponiveis()) {
                    if (c.getPeca() != null) {
                        c.mostraAreaDesenho(g, Color.red);
                    } else {
                        g.setColor(new Color(255, 255, 0, 100));
                        g.fillOval(c.getX() + (c.getLargura() / 2) - 10, c.getY() + (c.getAltura() / 2) - 10, 20, 20);
                    }
                }
            }
        }
    }

    public void desenharPecas(Graphics g){

        for (int i = 0; i < 16; i++) {
            try {
                pecasBrancas[i].desenha(g);
            }catch (NullPointerException e){
                // essa peça é null
            }
            try {
                pecasPretas[i].desenha(g);
            }catch (NullPointerException e){
                // essa peça é null
            }

            for (Peca p : brancasCapturadas) {
                p.desenha(g);
            }
            for (Peca p : pretasCapturadas) {
                p.desenha(g);
            }
        }
    }

    public void setPosPecaCapturda(Peca p){
        p.setAtiva(true);
        int largura = tabuleiro.getLargura()/16;
        int altura = tabuleiro.getAltura()/16;

        p.setDimensao(largura , altura);
        if (p.getCor() == 'p') {
            p.setLocal((int) (tabuleiro.getX() + tabuleiro.getLargura() * 1.05),
                    tabuleiro.getY() + altura * pretasCapturadas.size());
        }else{
            p.setLocal((int) (tabuleiro.getX() - tabuleiro.getLargura() * 0.1),
                    tabuleiro.getY() + altura * brancasCapturadas.size());
        }
    }

    public static void atualizarPecas(){

        tabuleiro.zeraCasasAmeacadas();

        for (int i = 0; i < 16; i++) {
            try {
                pecasBrancas[i].atualizarCasasDisponiveis();
                pecasPretas[i].atualizarCasasDisponiveis();
                pecasBrancas[i].atualizarCasasDisponiveis();
            }catch (NullPointerException e){
                // peca é null
            }
        }

        if (vezDeQuem == 'b') {
            for (int i = 0; i < 16; i++) {
                try {
                    pecasBrancas[i].filtraCasasDisponiveis();
                } catch (NullPointerException e) {
                    // peca é null
                }
            }
        }else{
            for (int i = 0; i < 16; i++) {
                try {
                    pecasPretas[i].filtraCasasDisponiveis();
                } catch (NullPointerException e) {
                    // peca é null
                }
            }
        }
    }

    public static void atualizarPecas(char cor){

        tabuleiro.zeraCasasAmeacadas();

        for (int i = 0; i < 16; i++) {
            try {
                getPecas(cor)[i].atualizarCasasDisponiveis();
            }catch (NullPointerException e){
                // peca é null
            }
        }

        for (int i = 0; i < 16; i++) {
            try {
                getPecas(cor)[i].filtraCasasDisponiveis();
            } catch (NullPointerException e) {
                // peca é null
            }
        }

    }

    public int atualizarSaidaDeXeque(Peca rei){
        int nSaida = 0;
        for (Peca p : getPecas(rei.getCor())) {
            if (p != null && p.isAtiva())
                nSaida += p.saidaDeXeque(rei);
        }

        return nSaida;
    }

    public static void examina_Xeque (){

        brancas_Xeque = false;
        pretas_Xeque = false;

        if(getRei('b').getCasaAtual().getAlvoDePecasPretas().size() > 0){
            brancas_Xeque = true;
        }else if(getRei('p').getCasaAtual().getAlvoDePecasBrancas().size() > 0){
            pretas_Xeque = true;
        }

    }

    public static Peca getRei(char cor){

        if (cor == 'b'){
            for (Peca p : pecasBrancas) {
                if(p instanceof Rei){
                    return p;
                }
            }
        }else{
            for (Peca p : pecasPretas) {
                if(p instanceof Rei){
                    return p;
                }
            }
        }
        throw new RuntimeException("Jogo esta sem um rei");
    }

    private void mostraVezDeQuem (Graphics g) {

        g.setFont( new Font("Serif", Font.BOLD + Font.ITALIC, (int)(window.getWidth()*0.05)));

        Color cor;
        String j;

        if(vencedor != ' '){
            cor = vencedor == 'b' ? Color.WHITE : Color.lightGray;
            j = vencedor == 'b' ? " Brancas Venceram" : " Pretas Venceram";
        }else
        if(pretas_Xeque && vezDeQuem == 'p'){
            cor = Color.lightGray;
            j = " Pretas Em Xeque";
        }else
        if(brancas_Xeque && vezDeQuem == 'b'){
            cor = Color.WHITE;
            j = " Brancas Em Xeque";
        }else{
            cor = vezDeQuem == 'b' ? Color.WHITE : Color.lightGray;
            j = vezDeQuem == 'b' ? "Turno das Brancas" : " Turno das Pretas";
        }

        // sombra:
        g.setColor(new Color(255,255,0,90));
        g.drawString(j,(int)(window.getWidth()*0.3)-3,(int)(window.getHeight()*0.1)-4);

        g.setColor(cor);
        g.drawString(j,(int)(window.getWidth()*0.3),(int)(window.getHeight()*0.1)-2);
    }

    public static boolean isBrancas_Xeque() {
        return brancas_Xeque;
    }

    public static boolean isPretas_Xeque() {
        return pretas_Xeque;
    }

    public static Peca[] getPecas(char cor) {
        return cor == 'b' ? pecasBrancas : pecasPretas;
    }

    public static Peao getPossivelEmPassant() {
        return possivelEmPassant;
    }

    public static int getLargura() {
        return largura;
    }

    public static int getAltura() {
        return altura;
    }

    public void irMenu (){

        musicaMenu.play();

        window.clear(Color.WHITE);
        Window.graphics.setColor(Color.black);
        Window.graphics.drawImage(new ImageIcon("src/Recursos/Imagens/Menu/menu.png").getImage(), 0, 0, window.getWidth(), window.getHeight(), null);
        Window.graphics.setFont(new Font("TimesRoman", Font.PLAIN, (int)(window.getWidth()*0.05)));
        Window.graphics.drawString("Apernte <Enter>", (int)(window.getWidth()*0.1),(int)(window.getWidth()*0.11));


        while (true){

            window.update();

            if (keyboard.teclaIsAtiva("Enter_KEY")) {
                musicaMenu.stop();
                break;
            }

            if (keyboard.teclaIsAtiva("Escape_KEY")) {
                window.exit();
            }

        }

        run();

    }

}
