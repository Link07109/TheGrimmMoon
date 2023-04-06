package main.java;

import java.util.ArrayList;
import java.util.Arrays;

public class RoomHandler1 {
    private static final NPC1 rusl = new NPC1("Rusl");
    private static final NPC1 blank = new NPC1("");
    private static final NPC1 countryman = new NPC1("Countryman");
    private static final NPC1 first = new NPC1("TheFirst");
    private static final NPC1 second = new NPC1("TheSecond");
    private static final NPC1 third = new NPC1("TheThird");

    private static final Room1 empty = new Room1();
    private static Room1 currentRoom = empty;

    private static final Choice room1Choice = new Choice("", new ChoiceOption[] {
            new ChoiceOption("Talk to him", (input) -> Game.player.talkToNPC()),
            new ChoiceOption("Fight him", (input) -> Game.death()),
    });

    private static final Choice inbetweenChoice = new Choice("You find an oil canister on the ground outside your door.\n", new ChoiceOption[] {
            new ChoiceOption("Pick it up.", (input) -> {
                System.out.println(
                        "\nYou picked up the oil canister!\n" +
                                "It has been added to your inventory.\n" +
                                "Type 'status' or 'inventory' to access your inventory.\n"
                );
                Game.player.addToInventory("Oil Canister.");
                Game.player.talkToNPC();
            }),
            new ChoiceOption("Leave it be.", (input) -> {
                System.out.println("You left the oil canister where you found it.");
                Game.player.talkToNPC();
            }),
    });

    private static final Room1 entrance = new Room1("entrance", new NPC1[] { rusl }, new Choice[] { room1Choice },
            (input) -> System.out.println("You head back to the castle, making sure to get a good night's rest."));

    private static final Room1 inbetween = new Room1("inbetween", new NPC1[] { rusl }, new Choice[] { inbetweenChoice },
            (input) -> Text.typingEffect(
                    "You head out on your quest to Lothric in Rusl's stead.\n" +
                    "Three guards accompany you, leading the way and serving as bodyguards."
            )
    );

    private static final Room1 kingdom2 = new Room1("kingdom2", new NPC1[] {}, new Choice[] {}, (input) ->
        Text.typingEffect(
            "\nIn the evening when the sun had disappeared behind the mountains,\n" +
            "a shining globe was placed on an oak-tree, which shed a soft light far and wide.\n" +
            "By means of this, everything could very well be seen and distinguished,\n" +
            "even though it was not so brilliant as the sun."
        )
    );

    private static final Room1 moonRoom = new Room1("moonRoom", new NPC1[] { countryman, blank, first, second, third, blank }, new Choice[] {
        new Choice("You hear someone approaching from behind.\n", new ChoiceOption[] {
            new ChoiceOption("Talk", (input) -> Game.player.talkToNPC()),
            new ChoiceOption("Fight", (input) -> Game.death()),
            new ChoiceOption("Hide", (input) -> Game.death()),
        }),
        new Choice("The guards are attempting to steal the moon", new ChoiceOption[] {
            new ChoiceOption("Help them", (input) -> Game.player.talkToNPC()),
            new ChoiceOption("Stop them", (input) -> Game.death()),
        }) // TODO fix by presentation yes no maybe
    }, (input) -> {});

    private static final Room1 kingdom1 = new Room1("kingdom1", new NPC1[] { rusl }, new Choice[] {}, (input) -> {});
    private static final Room1 cave = new Room1("cave", new NPC1[] {}, new Choice[] {}, (input) -> {});
    private static final Room1 night = new Room1("night", new NPC1[] {}, new Choice[] {}, (input) -> {});
    private static final Room1 heaven = new Room1("heaven", new NPC1[] {}, new Choice[] {}, (input) -> {});
    private static final Room1 extra = new Room1("extra", new NPC1[] {}, new Choice[] {}, (input) -> {});

    private Room1[] rooms = {entrance, inbetween, kingdom2, moonRoom, kingdom1, cave, night, heaven, extra};
    private ArrayList<String> roomNames = new ArrayList<>();

    public RoomHandler1() {
        currentRoom = entrance;
        Arrays.asList(rooms).forEach(room -> roomNames.add(room.getRoomName()));
    }

    public void moveToRoom(String roomName) {
        currentRoom.onExited();
        Arrays.asList(rooms).forEach(room -> {
            if (room.getRoomName().equals(roomName)) {
                currentRoom = room;
            }
        });

        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        loadCurrentRoom();
    }

    public void loadCurrentRoom() {
        if (roomNames.indexOf(currentRoom.getRoomName()) + 1 < roomNames.size())
            Game.player.setNextRoom(roomNames.get(roomNames.indexOf(currentRoom.getRoomName()) + 1));
        else
            Game.endGame();

        currentRoom.onEntered();
    }

    public Room1 getCurrentRoom() {
        return currentRoom;
    }

}
