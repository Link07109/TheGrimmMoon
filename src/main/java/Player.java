package main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Player {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private boolean hasLearnedMaintainMoon = false;
    private boolean death;
    private boolean end;
    private int money = 100;

    private String playerName;
    private String input;
    private String nextRoom = "inbetween";

    private String[] playerInputChoices = {"1", "2", "3", "4"};
    private ArrayList<String> inventory = new ArrayList<>();

    public void doChoice(int choiceIndex) {
        Game.roomHandler.getCurrentRoom().setSelectedChoiceIndex(choiceIndex);
        Game.roomHandler.getCurrentRoom().processDecision();
    }

    public void addToInventory(String item) {
        if (!inventory.contains(item)) {
            inventory.add(item);

            for (NPC1 npc : Game.roomHandler.getCurrentRoom().getNPCS()) {
                npc.sayDialogueChunk();
            }
        }
        else System.out.println("you already have that in your inventory");
    }

    public void showStatus() {
        System.out.println("*====== STATUS SCREEN ===========*");
        System.out.println("| == INVENTORY ==  |  == COIN == |");
        System.out.println("|                  |      " + money + "    |");
        System.out.print("| "); inventory.forEach(System.out::println);
        System.out.println("*================================*");
        getPlayerInput();
    }

    public void talkToNPC() {
        for (NPC1 npc : Game.roomHandler.getCurrentRoom().getNPCS()) {
            npc.sayDialogueChunk();
        }
    }

    String helpMsg =
            "*================= HELP MENU ====================* \n" +
            "|   continue: brings you to the next room        | \n" +
            "|       help: shows you a list of valid commands | \n" +
            "|     status: displays your character status     | \n" +
            "|        end: ends the game                      | \n" +
            "*================================================*";

    public void getPlayerInput() {
        System.out.print("> ");

        try {
            input = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String inputErrorMsg =
                "=================== ERROR =====================* \n" +
                " '" + input + "' " + "is not a valid command.  \n" +
                " To see a list of valid commands, type 'help'  \n" +
                "*==============================================*";

        switch (Game.gameState) {
            case PREGAME:
                if (input.equals("start")) {
                    System.out.println("Starting Game...");
                    Game.beginGame();
                }
                if (input.equals("end"))
                    Game.endGame();
                getPlayerInput();
                break;
            case BEGIN:
                setPlayerName(input);
                Game.startGame();
                break;
            case GAME:
                if (input.equals("continue"))
                    Game.roomHandler.moveToRoom(nextRoom);
                else if (input.equals("status") | input.equals("inventory"))
                    showStatus();
                else if (input.equals("help"))
                    System.out.println(helpMsg);
                else if (input.equals("end")) {
                    end = true;
                    Game.endGame();
                } else if (input.equals("death")) {
                    death = true;
                    Game.death();
                } else
                    System.out.println(inputErrorMsg);

                getPlayerInput();
                break;
            case CHOICE:
                if (Arrays.asList(playerInputChoices).contains(input)) {
                    doChoice(Integer.parseInt(input) - 1);
                } else System.out.println("please input a valid choice (1-4)");
                break;
            case DEATH:
            case END:
                System.out.println("Ending Game...");
                System.exit(0);
                break;
        }
    }

    public void setNextRoom(String nextRoom) {
        this.nextRoom = nextRoom;
    }

    public ArrayList<String> getInventory() {
        return inventory;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean isDeath() {
        return death;
    }

    public boolean isEnd() {
        return end;
    }
}
