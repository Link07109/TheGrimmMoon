package main.java;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.BufferedInputStream;
import java.net.URL;
import java.util.Objects;

public class Sound {

    private Clip clip1;
    private Clip clip2;

    public Clip getClip1() {
        return clip1;
    }

    public Clip getClip2() {
        return clip2;
    }

    public Sound() {
        try {
            clip1 = AudioSystem.getClip();
            clip2 = AudioSystem.getClip();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playMusic(String url) {
        try {
            if (clip1.isActive()) stopClip(clip1);
            AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(
                    Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(url))));
            clip1.open(ais);
            clip1.loop(Clip.LOOP_CONTINUOUSLY);
            clip1.start();
            FloatControl gainControl = (FloatControl) clip1.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-15.0f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSoundEffect(String url) {
        try {
            if (clip2.isActive()) stopClip(clip2);
            AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(
                    Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(url))));
            clip2.open(ais);
            clip2.loop(0);
            clip2.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopClip(Clip clip) {
        clip.stop();
        clip.flush();
        clip.close();
    }
}
