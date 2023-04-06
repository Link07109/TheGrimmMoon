package main.java;

import static main.java.ANSIColorConstants.*;

public class Text {

    public static void typingEffect(String message) {
        for (char ch : message.toCharArray()) {
            System.out.print(ANSI_YELLOW + ANSI_ITALIC + ch + ANSI_RESET);
            if ((ch == '.') || (ch == '?') || (ch == '!'))
                waitVariable(400);
            Game.sound.playSoundEffect("sounds/typing2.wav");

            waitVariable(45);
            Game.sound.stopClip(Game.sound.getClip2());
        }
    }

    public static void waitVariable(int millisToWait) {
        try {
            Thread.sleep(millisToWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
