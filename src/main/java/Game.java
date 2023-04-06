package main.java;

public class Game {
    public static GameState gameState = GameState.PREGAME;
    public static final Player player = new Player();
    public static final RoomHandler1 roomHandler = new RoomHandler1();
    public static final Sound sound = new Sound();

    public static GameState getGameState() {
        return gameState;
    }

    static final String gameOver =
            ANSIColorConstants.ANSI_RED +
            "  /$$$$$$   /$$$$$$  /$$      /$$ /$$$$$$$$        /$$$$$$  /$$    /$$ /$$$$$$$$ /$$$$$$$ \n" +
            " /$$__  $$ /$$__  $$| $$$    /$$$| $$_____/       /$$__  $$| $$   | $$| $$_____/| $$__  $$\n" +
            "| $$  \\__/| $$  \\ $$| $$$$  /$$$$| $$            | $$  \\ $$| $$   | $$| $$      | $$  \\ $$\n" +
            "| $$ /$$$$| $$$$$$$$| $$ $$/$$ $$| $$$$$         | $$  | $$|  $$ / $$/| $$$$$   | $$$$$$$/\n" +
            "| $$|_  $$| $$__  $$| $$  $$$| $$| $$__/         | $$  | $$ \\  $$ $$/ | $$__/   | $$__  $$\n" +
            "| $$  \\ $$| $$  | $$| $$\\  $ | $$| $$            | $$  | $$  \\  $$$/  | $$      | $$  \\ $$\n" +
            "|  $$$$$$/| $$  | $$| $$ \\/  | $$| $$$$$$$$      |  $$$$$$/   \\  $/   | $$$$$$$$| $$  | $$\n" +
            " \\______/ |__/  |__/|__/     |__/|________/       \\______/     \\_/    |________/|__/  |__/\n" +
            ANSIColorConstants.ANSI_RESET;

    public static void beginGame() {
        changeState(GameState.BEGIN);
        Text.typingEffect("What is your name?");
    }

    public static void startGame() {
        changeState(GameState.GAME);
        System.out.println();
        Text.typingEffect(
            "In days gone by there was a land where the nights were always dark,\n" +
            "and the sky spread over it like a black cloth.\n" +
            "At the creation of the world, the light at night had been sufficient.\n\n"
        );
        roomHandler.loadCurrentRoom();
    }

    public static void death() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        System.out.println(gameOver);
        Game.sound.stopClip(Game.sound.getClip1());
        Game.sound.playSoundEffect("sounds/death.wav");
        System.out.println("You died!");

        System.out.println("Enter any key to end the game.");
        changeState(GameState.DEATH);
        Game.player.getPlayerInput();
    }

    public static void changeState(GameState newState) {
        gameState = newState;
    }

    public static void endGame() {
        Game.sound.stopClip(Game.sound.getClip1());
        Game.sound.playMusic("sounds/end.wav");

        System.out.println("Enter any key to end the game.");
        changeState(GameState.END);
        Game.player.getPlayerInput();
    }

    public void gameLoop() {
        sound.playMusic("sounds/twilight.wav");

        System.out.println(
                ANSIColorConstants.ANSI_BLUE +
                " /$$$$$$$$ /$$   /$$ /$$$$$$$$        /$$$$$$  /$$$$$$$  /$$$$$$ /$$      /$$ /$$      /$$       /$$      /$$  /$$$$$$   /$$$$$$  /$$   /$$\n" +
                "|__  $$__/| $$  | $$| $$_____/       /$$__  $$| $$__  $$|_  $$_/| $$$    /$$$| $$$    /$$$      | $$$    /$$$ /$$__  $$ /$$__  $$| $$$ | $$\n" +
                "   | $$   | $$  | $$| $$            | $$  \\__/| $$  \\ $$  | $$  | $$$$  /$$$$| $$$$  /$$$$      | $$$$  /$$$$| $$  \\ $$| $$  \\ $$| $$$$| $$\n" +
                "   | $$   | $$$$$$$$| $$$$$         | $$ /$$$$| $$$$$$$/  | $$  | $$ $$/$$ $$| $$ $$/$$ $$      | $$ $$/$$ $$| $$  | $$| $$  | $$| $$ $$ $$\n" +
                "   | $$   | $$__  $$| $$__/         | $$|_  $$| $$__  $$  | $$  | $$  $$$| $$| $$  $$$| $$      | $$  $$$| $$| $$  | $$| $$  | $$| $$  $$$$\n" +
                "   | $$   | $$  | $$| $$            | $$  \\ $$| $$  \\ $$  | $$  | $$\\  $ | $$| $$\\  $ | $$      | $$\\  $ | $$| $$  | $$| $$  | $$| $$\\  $$$\n" +
                "   | $$   | $$  | $$| $$$$$$$$      |  $$$$$$/| $$  | $$ /$$$$$$| $$ \\/  | $$| $$ \\/  | $$      | $$ \\/  | $$|  $$$$$$/|  $$$$$$/| $$ \\  $$\n" +
                "   |__/   |__/  |__/|________/       \\______/ |__/  |__/|______/|__/     |__/|__/     |__/      |__/     |__/ \\______/  \\______/ |__/  \\__/\n" +
                ANSIColorConstants.ANSI_RESET
        );

        System.out.println(
                ANSIColorConstants.ANSI_CYAN +
                "*============ MAIN MENU ============* \n" +
                "| Type 'start' to start the game.   | \n" +
                "| Type 'end' to end the game.       | \n" +
                "*===================================*" +
                ANSIColorConstants.ANSI_RESET
        );

        player.getPlayerInput();
    }

}
