package com.company;

import java.util.Scanner;
import java.util.HashMap;

import com.company.colors;

// USE COLORS LIKE THIS:
// colors.red("YOUR STRING");
// REFER TO colors.java TO FIND COLOR FUNCTIONS

// Create a hashmap for each room and return it (you can name it whatever, but I named mine newActions): HashMap<String, Runnable> newActions = new HashMap<>();
/* WRITE ACTIONS/CHOICES LIKE THIS:

HashMapName.put("what the user should input", () -> {
    add stuff here (prints things, add item to inventory, changes HP value, enter new room, etc.)
});

*/

// As of now, items are written just like actions but only function in certain rooms
// (which is why there's a room variable) and are accessed by typing inventory.
// If you find another way to code the inventory, please change this
public class Main {
    private static HashMap<String, Runnable> actions = new HashMap<>();
    private static HashMap<String, Runnable> inventory = new HashMap<>();
    static String room = "";
    static int HP = 3;
    static boolean hasOrange = false;
    static String eggName = "";
    static boolean hasOrangeDoorKey = false;
    static boolean enteredYellow = false;
    static boolean hasBanana = false;
    static boolean eggIsFighting = false;
    static boolean exitIsOpen = false;
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        // COLORS
        System.out.println(colors.red("Hello") + colors.blue("World"));


        room = "roomOne";
        System.out.println(colors.yellow("You feel that 2713 is a relevant number that will prove to be useful in your adventure"));
        System.out.println("You wake up in a dark room. You find three objects in front of you, but you can't quite see what they are.\nItems may be used at any time by typing " + colors.blue("\"inventory\"") + " but will only prove to be useful in certain situations.\n" + colors.blue("\"pickup left\"") + " " + colors.blue("\"pickup middle\"") + " " + colors.blue("\"pickup right\"") + " " + colors.blue("\"continue\"") + " (You can select several objects)");
        //skip to 2nd to last room
        actions.put("skip", () -> {
            hasOrangeDoorKey = true;
            eggName = "bob";
            inventory.put("egg", () -> {
                switch (room) {
                    case "lastroom":
                        System.out.println(eggName + " enters the battlefield.");
                        eggIsFighting = true;
                        break;
                    default:
                        System.out.println(eggName + " stands proudly, offering you moral support");
                        break;
                }
            });
            inventory.put("banana", () -> {
                switch (room) {
                    case "lastroom":
                        if (eggName != "") {
                            System.out.println("The COMICALLY LARGE BANANA levitates out of your inventory and into " + eggName + "'s grasp.");
                        } else {
                            System.out.println("You still can't lift the COMICALLY LARGE BANANA but still decide to carry it with you.");
                        }
                        hasBanana = true;
                        break;
                    default:
                        System.out.println("You find that the COMICALLY LARGE BANANA is too heavy for you to remove from your inventory.\nYou wonder how you got it there.");
                        break;
                }
            });

            actions = orangeDoorRoom();
        });
        actions.put("pickup left", () -> {
            System.out.println("You pick up a half-eaten potato.\nKeyword:" + colors.magenta("\"potato\""));
            // Example of creating an item. Use inventory.put followed by keyword (input needed to access) and what it does
            inventory.put("potato", () -> {
                // using switch case to determine if the item serves a function (please change if you can)
                switch (room) {
                    // item only does something if it matches the room
                    case "room2Left":
                        actions.remove("eat");
                        System.out.println("You throw the POTATO at the CAKE and it explodes. This reveals an ORANGE KEY that was hidden in the CAKE.\nKeyword:" + colors.magenta("\"orangekey\""));
                        inventory.put("orangekey", () -> { //orange goes in orange room/orange fruit player will stay mad
                            if (hasOrange == true) {
                                System.out.println("You impale the ORANGE with your ORANGE KEY and enjoy its contents, revealing an ORANGE DOOR KEY in its center.\nKeyword:" + colors.magenta("orangedoorkey"));
                                inventory.put("orangedoorkey", () -> {
                                    switch(room) {
                                        case "orangeDoorRoom":
                                            hasOrangeDoorKey = true;
                                            System.out.println("You unlock the door, allowing you to continue to the next room.");
                                            break;
                                        default:
                                            System.out.println("You can't use this key here.");
                                            break;
                                    }
                                });
                                hasOrange = false;
                            } else if (room == "orangeDoorRoom") {
                                System.out.println("This ORANGE KEY doesn't work on this ORANGE DOOR.");
                            } else {
                                System.out.println("You can't use this key here.");
                            }
                        });
                        // you can remove the item if it's consumed in the action (potato is thrown, so you lose it)
                        inventory.remove("potato");
                        break;
                    // does nothing if it doesn't match a room
                    default:
                        System.out.println("You can't use this here.");
                        break;
                }
            });
            actions.remove("pickup left");
        });

        actions.put("pickup middle", () -> {
            System.out.println("You cut your hand on a COMICALLY LARGE BANANA and put it in your inventory.\nKeyword:" + colors.magenta("\"banana\""));
            inventory.put("banana", () -> {
                switch (room) {
                    case "lastroom":
                        if (eggName != "") {
                            System.out.println("The COMICALLY LARGE BANANA levitates out of your inventory and into " + eggName + "'s grasp.");
                        } else {
                            System.out.println("You still can't lift the COMICALLY LARGE BANANA but still decide to carry it with you.");
                        }
                        hasBanana = true;
                        break;
                    default:
                        System.out.println("You find that the COMICALLY LARGE BANANA is too heavy for you to remove from your inventory.\nYou wonder how you got it there.");
                        break;
                }
            });
            HP--;
            actions.remove("pickup middle");
        });

        actions.put("pickup right", () -> {
            System.out.println("Picking up the object triggers a PRESSURE PLATE, kicking you out of the room.");
            HP = 0;
        });

        actions.put("continue", () -> actions = r1Part2());

        // takes input as long as player is alive
        while (HP != 0) {
            // choice is initial input, itemChoice is input after entering the inventory
            String choice = scan.nextLine();
            String itemChoice = "";
            switch (choice) {
                //enter inventory
                case "inventory":
                    System.out.println("--Inventory--Enter Keyword--");
                    itemChoice = scan.nextLine();
                    //prevents crash from invalid inputs, then runs whatever item does
                    if (inventory.get(itemChoice) != null) {
                        inventory.get(itemChoice).run();
                    } else {
                        System.out.println("You don't have a(n) " + itemChoice);
                    }
                    System.out.println("--Exit Inventory--");
                    break;
                default:
                    // actions outside of inventory and items such as "pickup left" or "eat"
                    if (actions.get(choice) != null) {
                        actions.get(choice).run();
                    } else {
                        System.out.println("You can't " + choice + " here.");
                    }
            }
        }
        // stuff that happens after death
        if (HP == 0) {
            System.out.println("Tip: You die if your health reaches 0.");
        }
    }

    public static HashMap<String, Runnable> r1Part2() {
        HashMap<String, Runnable> newActions = new HashMap<>();
        room = "r1Part2";
        System.out.println("Tip: You'll need your inventory for the following rooms. Make sure to remember the keywords.");
        System.out.println("You decide it's time to stop messing around and flick the LIGHT SWITCH beside you.\nThe wall to your left contains a PURPLE door, and the wall to your right contains a YELLOW door.\n" + colors.blue("\"left\"") + " " + colors.blue("\"right\""));
        newActions.put("left", () -> {
            System.out.println("You enter the LEFT room.");
            actions = room2Left();
        });
        newActions.put("right", () -> {
            System.out.println("You enter the RIGHT room.");
            actions = yellowRoom();
        });
        return newActions;
    }

    public static HashMap<String, Runnable> room2Left() {
        HashMap<String, Runnable> newActions = new HashMap<>();
        room = "room2Left";
        System.out.println("You find a cake on the floor. There's also another door in front of you.\n" + colors.blue("\"eat\"") + " " + colors.blue("\"back to yellow door\"" + " " + colors.blue("\"continue\"")));
        newActions.put("eat", () -> {
            System.out.println("The cake explodes before you can taste it, demotivating you from continuing your adventure.");
            HP--;
        });
        newActions.put("back to yellow door", () -> {
            actions = yellowRoom();
        });
        newActions.put("continue", () -> {
            actions = room3Left();
        });
        return newActions;
    }

    public static HashMap<String, Runnable> yellowRoom() {
        HashMap<String, Runnable> newActions = new HashMap<>();
        room = "yellowRoom";
        System.out.println("You are now in the yellow room");
        System.out.println("The sun is being contained in the yellow room");

        if (enteredYellow == false) {
            System.out.println("The sun welcomes you");
            HP++;
            enteredYellow = true;
        } else {
            System.out.println("The sun decides it has already welcomed you enough");
        }

        System.out.println(colors.blue("\"continue\""));
        newActions.put("continue", () -> {
            actions = orangeDoorRoom();
        });
        return newActions;
    }

    public static HashMap<String, Runnable> room3Left() {
        HashMap<String, Runnable> newActions = new HashMap<>();
        room = "room3Left";
        System.out.println("You find a large pile of ORANGES. After counting each ORANGE individually, you find that there are exactly 93,756 ORANGES.\nYou get the feeling that only one of these ORANGES will be useful in your adventure.\n" + colors.blue("Enter a number \"go back\""));
        newActions.put("2713", () -> {
            System.out.println("You feel that this is the correct ORANGE and take it with you.\nYou cannot access this item from your inventory");
            hasOrange = true;
            newActions.remove("2713");
        });
        newActions.put("go back", () -> {
            actions = room2Left();
        });
        return newActions;
    }

    public static HashMap<String, Runnable> orangeDoorRoom() {
        HashMap<String, Runnable> newActions = new HashMap<>();
        Scanner scan = new Scanner(System.in);
        room = "orangeDoorRoom";
        System.out.println("Upon entering the room, you find a SUSPICIOUS TREASURE CHEST in the center and an ORANGE DOOR across from you.");
        System.out.println(colors.blue("\"treasure\" \"continue\" \"back to purple room\""));

        newActions.put("treasure", () -> {
            System.out.println("You open the chest and discover an EGG WITH GOOGLY EYES.\nKeyword:");
            System.out.println(colors.magenta("egg"));
            inventory.put("egg", () -> {
                switch (room) {
                    case "lastroom":
                        System.out.println(eggName + " enters the battlefield.");
                        eggIsFighting = true;
                        break;
                    default:
                        System.out.println(eggName + " stands proudly, offering you moral support");
                        break;
                }
            });
            System.out.println(colors.blue("Enter the name of your companion"));
            eggName = scan.nextLine();
            System.out.println("You bring your trusty companion " + eggName + " with you on your adventure");
            newActions.remove("treasure");
        });

        newActions.put("continue", () -> {
            if (hasOrangeDoorKey) {
                System.out.println("You enter the door.");
                actions = lastRoom();
            } else {
                System.out.println("The door is locked. If only you had a matching key...");
            }
        });

        newActions.put("back to purple room", () -> {
            actions = room2Left();
        });
        return newActions;
    }

    public static HashMap<String, Runnable> lastRoom() {
        HashMap<String, Runnable> newActions = new HashMap<>();
        room = "lastroom";
        System.out.println("You can now see the exit, so close yet so far");
        System.out.println("Two unused items will assist you in your escape");
        System.out.println("Choose your items, then type " + colors.blue("continue"));
        newActions.put("continue", () -> {
            System.out.println("An evil egg is blocking the exit");
            if (eggIsFighting && hasBanana) {
                System.out.println("In the blink of an eye, " + eggName + " EGGS-TERMINATES the evil egg, clearing the path to the exit.");
                exitIsOpen = true;
            } else if (eggIsFighting) {
                System.out.println("Sacrificing itself, " + eggName + " throws itself at the evil egg and EGG-SPELLS it from the dungeon. The path to the exit clears, but at what cost?");
                exitIsOpen = true;
            } else if (hasBanana) {
                System.out.println("The COMICALLY LARGE BANANA began a sudden allergic reaction for the evil egg, weakening its EGGS-OSKELETON.");
                System.out.println("You battle the evil egg");
                HP--;
                exitIsOpen = true;
            } else {
                System.out.println("You engage in hand-to-hand combat with the evil egg.");
                exitIsOpen = true;
                HP -= 2;
            }
            if (exitIsOpen) {
                System.out.println(colors.blue("exit"));
                newActions.put("exit", () -> {
                    //last dialogue
                    System.out.println("Your surroundings illuminate as you approach the sunlight at the end of the exit. As you take a step on the grass outside, you find ");
                });
            }

        });
        return newActions;
    }

}
//exiled exoskeleton expelled extravaganza