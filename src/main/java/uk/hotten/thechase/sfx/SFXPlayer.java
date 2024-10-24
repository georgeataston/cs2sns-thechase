package uk.hotten.thechase.sfx;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class SFXPlayer {

    public static void init() {

    }

    public static Clip gameTimer() {
        return play("game-timer.wav");
    }

    public static Clip playerLockIn() {
        return play("player-lockin.wav");
    }

    public static Clip chaserLockIn() {
        return play("chaser-lockin.wav");
    }

    public static Clip gameOutOfTime() {
        return play("game-outoftime.wav");
    }

    public static Clip gameCorrectAnswer() {
        return play("game-correctanswer.wav");
    }

    public static Clip playerAnswer() {
        return play("player-answer.wav");
    }

    public static Clip chaserAnswer() {
        return play("chaser-answer.wav");
    }

    public static Clip playerWin() {
        return play("player-win.wav");
    }


    public static Clip playerLoose() {
        return play("player-loose.wav");
    }



    private static Clip play(String file) {
        try {
            InputStream is = SFXPlayer.class.getClassLoader().getResourceAsStream(file);
            InputStream bufferedIn = new BufferedInputStream(is);
            AudioInputStream stream = AudioSystem.getAudioInputStream(bufferedIn);
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
            return clip;
        } catch (Exception e) {

        }

        return null;
    }

}
