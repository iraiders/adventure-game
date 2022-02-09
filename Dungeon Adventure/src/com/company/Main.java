package com.company;

import java.util.Scanner;
import java.util.HashMap;

public class Main {
    private static HashMap<String, Runnable> actions = new HashMap<>();
    private static HashMap<String, Runnable> inventory = new HashMap<>();
    static String room = "";
    static int HP = 10;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        room = "roomOne";
        System.out.println("You wake up in a dark room. You find three objects in front of you, but you can't quite see what they are.\nItems may be used at any time by typing \"inventory\" but will only prove to be useful in certain situations.\n\"left\" \"middle\" \"right\" \"continue\" (You can select several objects)");

        actions.put("left", () -> {
            System.out.println("You pick up a half-eaten potato.\nKeyword:\"potato\"");
            inventory.put("potato", () -> {
                switch(room) {
                    case "room2Left":
                        System.out.println("You throw the POTATO at the CAKE and it explodes. This reveals an ORANGE KEY that was hidden in the CAKE.\nKeyword:\"orangekey\"");
                        inventory.put("orangekey", () -> {
                            switch(room) {
                                default:
                                    System.out.println("You can't use this key here.");
                                    break;
                            }
                        });
                        break;
                    default:
                        System.out.println("You can't use this here.");
                        break;
                }
            });
            actions.remove("left");
        });

        actions.put("middle", () -> {
            System.out.println("You cut your hand on a COMICALLY LARGE KNIFE and put it in your inventory.\nKeyword:\"knife\"");
            inventory.put("knife", () -> {
                switch(room) {
                    default:
                        System.out.println("You find that the COMICALLY LARGE KNIFE is too heavy for you to remove from your inventory.\nYou wonder how you got it there.");
                        break;
                }
            });
            HP--;
            actions.remove("middle");
        });

        actions.put("right", () -> {
            System.out.println("Picking up the object triggers a pressure plate, blowing up the room.");
            HP = 0;
        });

        actions.put("continue", () -> actions = r1Part2());
        //takes input as long as player is alive
        while (HP != 0) {
            String choice = scan.nextLine();
            switch (choice) {
                case "inventory":
                    inventory.get(scan.nextLine()).run();
                    break;
                default:
                    actions.get(choice).run();
            }
        }
        if (HP == 0) {
            System.out.println("Tip: You die if your health reaches 0.");
        }
    }

    public static HashMap<String, Runnable> r1Part2() {
        HashMap<String, Runnable> newActions = new HashMap<>();
        room = "r1Part2";
        System.out.println("You decide it's time to stop messing around and flick the LIGHT SWITCH beside you.\nThe wall to your left contains a PURPLE door, and the wall to your right contains a YELLOW door.\n\"left\" \"right\"");
        newActions.put("left", () -> {
            System.out.println("You enter the LEFT room.");
            actions = room2Left();
        });
        newActions.put("right", () -> {
            System.out.println("You enter the RIGHT room.");
            //set to new room
        });
        return  newActions;
    }

    public static HashMap<String, Runnable> room2Left() {
        HashMap<String, Runnable> newActions = new HashMap<>();
        room = "room2Left";
        System.out.println("You find a cake on the floor.\n\"eat\" \"leave and enter yellow door\"");
        newActions.put("eat", () -> {
            System.out.println("The cake explodes before you can taste it, causing you to fall into a deep depression and dying.");
        });
        newActions.put("leave and enter yellow door", () -> {
        });
        return newActions;
    }

}
