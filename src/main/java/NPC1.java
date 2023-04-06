package main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class NPC1 {
    private String name;
    private ArrayList<String> dialogueLines = new ArrayList<>();
    List<List<String>> chunks = new ArrayList<>();
    private int chunkIndex = 0;

    public NPC1(String name) {
        this.name = name;
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("npcs/" + name + ".txt")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(stream)));
            String i;
            while ((i = br.readLine()) != null) dialogueLines.add(i + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        createChunks();
    }

    static <T> List<Integer> indexOfAll(T obj, List<T> list) {
        final List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (obj.equals(list.get(i))) {
                indexList.add(i);
            }
        }
        return indexList;
    }

    public final void createChunks() {
        List<Integer> indices = indexOfAll("\n", dialogueLines);

        int oldI = 0;
        for (int i : indices) {
            chunks.add(dialogueLines.subList(oldI, i));
            oldI = i;
        }
        chunks.add(dialogueLines.subList(oldI, dialogueLines.size()));
    }

    public final void sayDialogueChunk() {
        for (String line : chunks.get(chunkIndex)) {
            if (!line.isEmpty()) {
                if (!name.isEmpty())
                    System.out.print(ANSIColorConstants.ANSI_GREEN + "[*]<" + name + ">: " + ANSIColorConstants.ANSI_RESET);
                else
                    Text.waitVariable(1000);
                Text.typingEffect(line.trim().replace("{name}", Game.player.getPlayerName()) + "\n");
            }
        }
        if (chunkIndex < chunks.size() - 1) chunkIndex++;
    }

}
