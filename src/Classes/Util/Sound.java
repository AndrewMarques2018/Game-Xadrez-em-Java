package Classes.Util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound {

    private final boolean continuos;
    private final String url;
    private Clip clip;

    public Sound(String url, boolean continuos) {
        this.continuos = continuos;
        this.url = url;
    }

    public void play () {
        try {

            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(new File(url).getAbsoluteFile());

            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.loop(0);

            if(continuos)
                clip.loop(Clip.LOOP_CONTINUOUSLY); //Para repetir o som.

        } catch (Exception ex) {
            System.out.println("Erro ao executar SOM!");
            ex.printStackTrace();
        }
    }

    public void stop (){
        clip.stop();
    }
}
