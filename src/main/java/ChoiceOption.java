package main.java;

import java.util.function.Consumer;

public class ChoiceOption {
    private String optionName;
    private Consumer<Void> consequenceBehavior;

    public ChoiceOption(String optionName, Consumer<Void> consequenceBehavior) {
        this.optionName = optionName;
        this.consequenceBehavior = consequenceBehavior;
    }

    public void consequence() {
        consequenceBehavior.accept(null);

        if (Game.gameState.equals(GameState.DEATH) || Game.gameState.equals(GameState.END)) return;

        Game.changeState(GameState.GAME);
        System.out.println("type 'continue' to move on.");
        Game.player.getPlayerInput();
    }

    public String getOptionName() {
        return optionName;
    }

}
