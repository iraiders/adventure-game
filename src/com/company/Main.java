package com.company;

import java.util.Scanner;
import java.util.HashMap;
import com.company.colors;

// USE COLORS LIKE THIS:
// colors.red("YOUR STRING");
// REFER TO colors.java TO FIND COLOR FUNCTIONS

public class Main {
    private static HashMap<String, Runnable> actions = new HashMap<>();
    private static HashMap<String, Runnable> inventory = new HashMap<>();
    static String room = "";
    static int HP = 4;

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        // COLORS
        System.out.println(colors.red("Hello") + colors.blue("World"));


        room = "roomOne";
        System.out.println("You wake up in a dark room. You find three objects in front of you, but you can't quite see what they are.\nItems may be used at any time by typing \"inventory\" but will only prove to be useful in certain situations.\n\"pickup left\" \"pickup middle\" \"pickup right\" \"continue\" (You can select several objects)");

        actions.put("pickup left", () -> {
            System.out.println("You pick up a half-eaten potato.\nKeyword:\"potato\"");
            inventory.put("potato", () -> {
                switch(room) {
                    case "room2Left":
                        actions.remove("eat");
                        System.out.println("You throw the POTATO at the CAKE and it explodes. This reveals an ORANGE KEY that was hidden in the CAKE.\nKeyword:\"orangekey\"");
                        inventory.put("orangekey", () -> { //orange goes in orange room/orange fruit player will stay mad
                            switch(room) {
                                default:
                                    System.out.println("You can't use this key here.");
                                    break;
                            }
                        });
                        inventory.remove("potato");
                        break;
                    default:
                        System.out.println("You can't use this here.");
                        break;
                }
            });
            actions.remove("pickup left");
        });

        actions.put("pickup middle", () -> {
            System.out.println("You cut your hand on a COMICALLY LARGE KNIFE and put it in your inventory.\nKeyword:\"knife\"");
            inventory.put("knife", () -> {
                switch(room) {
                    default:
                        System.out.println("You find that the COMICALLY LARGE KNIFE is too heavy for you to remove from your inventory.\nYou wonder how you got it there.");
                        break;
                }
            });
            HP--;
            actions.remove("pickup middle");
        });

        actions.put("pickup right", () -> {
            System.out.println("Picking up the object triggers a pressure plate, blowing up the room.");
            HP = 0;
        });

        actions.put("continue", () -> actions = r1Part2());
        //takes input as long as player is alive
        while (HP != 0) {
            String choice = scan.nextLine();
            String itemChoice = "";
            switch (choice) {
                case "inventory":
                    System.out.println("--Inventory--Enter Keyword--");
                    itemChoice = scan.nextLine();
                    if (inventory.get(itemChoice) != null && itemChoice != "") {
                        inventory.get(itemChoice).run();
                    } else {
                        System.out.println("You don't have a(n) " + itemChoice);
                    }

                    break;
                default:
                    if(actions.get(choice) != null && choice != "") {
                        actions.get(choice).run();
                    } else {
                        System.out.println("You can't " + choice + " here.");
                    }
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
            //yellow room just kills you, so new rooms should be added to find an item to survive
            System.out.println("You enter the RIGHT room.");
            yellowRoom();
        });
        return  newActions;
    }

    public static HashMap<String, Runnable> room2Left() {
        HashMap<String, Runnable> newActions = new HashMap<>();
        room = "room2Left";
        System.out.println("You find a cake on the floor.\n\"eat\" \"leave and enter yellow door\"");
        newActions.put("eat", () -> {
            System.out.println("The cake explodes before you can taste it, causing you to fall into a deep depression and dying.");
            HP = 0;
        });
        newActions.put("leave and enter yellow door", () -> {
        });
        return newActions;
    }
    public static HashMap<String,Runnable> yellowRoom(){
        room = "yellowRoom";
        System.out.println("You are now in the yellow");
        System.out.println("The sun is being contained in the yellow room");
        System.out.println("The sun burns you :");
        HP = 0;
       return null;
    }

}
