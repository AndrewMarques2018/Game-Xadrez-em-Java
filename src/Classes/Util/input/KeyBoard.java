package Classes.Util.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public final class KeyBoard implements KeyListener {

    private ArrayList<Tecla> teclas;
    private boolean addTeclasAuto;

    public KeyBoard(){
        teclas = new ArrayList<>();

    }

    public boolean keyPressed(int code){
        return teclaIsAtiva(code);
    }

    public boolean keyPressed(String code){
        return teclaIsAtiva(code);
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        setKey(key,true);

        if(addTeclasAuto) {
            addTeclasAuto(e);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();
        setKey(key,false);

    }

    private void setKey(int code, boolean ativa){
        for (Tecla tecla : teclas) {
            if(tecla.code == code) {
                tecla.setAtivo(ativa);
                break;
            }
        }
    }

    private void setKey(String code, boolean ativa){
        for (Tecla tecla : teclas) {
            if(tecla.nome == code) {
                tecla.setAtivo(ativa);
                break;
            }
        }
    }

    public boolean teclaIsAtiva(int code){
        for (Tecla tecla : teclas) {
            if(tecla.code == code) {
                if(tecla.isAtivo())
                    return true;
            }
        }
        return false;
    }

    public boolean teclaIsAtiva(String code){
        for (Tecla tecla : teclas) {
            if(tecla.nome == code) {
                if(tecla.isAtivo())
                    return true;
            }
        }
        return false;
    }

    public void addTeclas(String nome, int code){

        for (Tecla t: teclas) {
            if(t.getCode() == code){
                System.out.println("tecla ja foi add");
                break;
            }
        }
        System.out.println("tecla add nome: "+nome+" code: "+code);
        teclas.add(new Tecla(nome, code));

    }

    private void addTeclasAuto(KeyEvent e){

        int code = e.getKeyCode();
        String nome;

        String param = e.paramString();
        param = param.substring(param.indexOf("keyText=")+8);
        nome = param.substring(0,param.indexOf(','));
        nome += "_KEY";

        addTeclas(nome, code);
        addTeclasAuto = false;
    }

    public void AddTeclasAuto() {
        addTeclasAuto = true;
    }

    private class Tecla {
        private final String nome;
        private final int code;
        private boolean ativo;

        public Tecla(String nome, int code) {
            this.nome = nome;
            this.code = code;
            this.ativo = false;
        }

        public String getNome() {
            return nome;
        }

        public int getCode() {
            return code;
        }

        public boolean isAtivo() {
            return ativo;
        }

        public void setAtivo(boolean ativo) {
            this.ativo = ativo;
        }

    }
}