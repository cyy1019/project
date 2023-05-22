package model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.io.SequenceInputStream;
import javax.sound.sampled.AudioFileFormat;


public class Music {
    static Clip clip;
    public static void playMusic(File file){
        try{
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-20.0f);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }



}
