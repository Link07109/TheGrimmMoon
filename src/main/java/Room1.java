package main.java;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Room1 {
    private String roomName;
    private String roomMap;
    private String storyTextUrl;
    private String musicUrl;
    private boolean firstTimeInRoom = true;

    private NPC1[] npcs;
    private Choice[] choices;
    private int selectedChoiceIndex = 0;
    private int choiceIndex = 0;

    private Consumer<Void> onExitBehavior;

    public Room1() {
        this.roomName = "empty";
        roomMap = "rooms/" + roomName + "/map.txt";
        storyTextUrl = "rooms/" + roomName + "/story.txt";
        musicUrl = "sounds/" + roomName + ".wav";
        this.npcs = new NPC1[] {};
        this.onExitBehavior = (input) -> {};
        this.choices = new Choice[] {};
    }

    public Room1(String roomName, NPC1[] npcs, Choice[] choices, Consumer<Void> onExitBehavior) {
        this.roomName = roomName;
        roomMap = "rooms/" + roomName + "/map.txt";
        storyTextUrl = "rooms/" + roomName + "/story.txt";
        musicUrl = "sounds/" + roomName + ".wav";
        this.npcs = npcs;
        this.onExitBehavior = onExitBehavior;
        this.choices = choices;
    }

    public void onEntered() {
        Game.sound.stopClip(Game.sound.getClip1());
        Game.sound.playMusic(musicUrl);

        try {
            InputStreamReader fr = new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader()
                    .getResourceAsStream(roomMap)));
            int i;
            while ((i = fr.read()) != -1)  System.out.print((char) i);
            System.out.println();

            StringBuilder contentBuilder = new StringBuilder();
            try (InputStream stream = getClass().getClassLoader().getResourceAsStream(storyTextUrl)) {
                BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(stream)));
                String j;
                while ((j = br.readLine()) != null) contentBuilder.append(j).append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (firstTimeInRoom) {
                Text.typingEffect(contentBuilder.toString());
                firstTimeInRoom = false;
            } else
                System.out.println(contentBuilder);


            if (choices.length > 0) {
                Game.changeState(GameState.CHOICE);
                loadNextChoice();
            } else {
                System.out.println("type 'continue' to move on.");
            }
            Game.player.getPlayerInput();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void onExited() {
        onExitBehavior.accept(null);
    }

    public void loadNextChoice() {
        Choice choice = choices[choiceIndex];
        Text.typingEffect(choice.getPrompt());

        for (int i = 0; i < choice.getOptions().length; i++) {
            System.out.println((i + 1) + ": " + choice.getOptions()[i].getOptionName());
        }
    }

    public void processDecision() {
        Choice choice = choices[choiceIndex];
        choice.pickOption(choice.getOptions()[selectedChoiceIndex]);
        choiceIndex++;
    }

    public String getRoomName() {
        return roomName;
    }

    public NPC1[] getNPCS() {
        return npcs;
    }

    public void setSelectedChoiceIndex(int selectedChoiceIndex) {
        this.selectedChoiceIndex = Math.min(selectedChoiceIndex, choices[choiceIndex].getOptions().length - 1);
    }
}
