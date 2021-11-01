package Interfaces;


import GlobalHelpers.Constants;
import DatabaseController.DatabaseController;
import DatabaseController.CommandFactory;
import GlobalHelpers.InvalidInputException;

import java.util.HashMap;
import java.util.Scanner;

public class UserInterface {
    private final HashMap<String, String[]> usableClasses;
    private final DatabaseController control;

    /**
     * Constructor.
     *
     * === Private Attributes ===
     * usableClasses: This is a hashmap of all the usable classes in the
     * program. TODO Please attach Operator interface to make this more
     * TODO compact, and obsolete
     */
    public UserInterface(){
        this.control = new DatabaseController();
        this.control.addFactory(new CommandFactory(control));

        // Will be replaced with something by OperatorInterface in later Phases.
        usableClasses = new HashMap<>();
        usableClasses.put(Constants.COURSE, new String[]{Constants.COURSE});
        usableClasses.put(Constants.NON_COURSE_OBJECT,
                new String[]{Constants.ACTIVITY,
                        Constants.TASK});
    }

    /**
     * A Quick Binary Search helper method to find queries in an array.
     *
     * @param query The item that you are trying to see if it is in the array
     * @param array The array to search
     * @return true iff the item is in the array
     */
    private boolean BinarySearch(String query, String[] array){
        for (String item : array){
            if (query.equals(item)){
                return true;
            }
        }
        return false;
    }

    /**
     * A helper method to help get the correct value key for the given
     * function. For instance, if the user wants to schedule an event, it
     * will correct the input so that it will return the 'Non Course Object'
     * Constant.
     *
     * @param input The input that the uper gave
     * @return The corresponding constant based on the input of the user.
     * Returns null if the input in invalid.
     */
    private String checkInputValue(String input){
        for (String key : usableClasses.keySet()){
            if (BinarySearch(input, usableClasses.get(key))){
                return key;
            }
        }
        return null;
    }

    /**
     * Gets a printable string representation of the usable classes
     *
     * @return A string representation of all the usable classes.
     */
    private String getUsableClasses(){
        StringBuilder usableClassesString = new StringBuilder();
        for (String[] item : usableClasses.values()){
            for (String usableItem : item){
                usableClassesString.append(usableItem).append(", ");
            }
        }
        return usableClassesString.toString();
    }

    /**
     * Runs the UserInterface. TODO Make method more User Friendly!
     *
     */
    public void run(){
        // As long as the program is running
        boolean running = true;

        while(running) {
            System.out.println("\nUsable Objects: " + this.getUsableClasses());
            Scanner objectScanner = new Scanner(System.in);
            System.out.println("Please enter what type of object: ");
            String schedulingObject = objectScanner.nextLine();

            String dataCategory = checkInputValue(schedulingObject);

            try {
                control.addToCommandHistory(dataCategory);
            } catch (InvalidInputException e){
                ; // TODO FIXME
            }
            // User types in the section they want to search
            Scanner continueQuestion = new Scanner(System.in);
            System.out.println("Do you want to add another object? " +
                    "(true/false):");
            String continueResponse = continueQuestion.nextLine();

            // Checks if the user wants to add any more courses.
            if (continueResponse.equals("false")){
                running = false;
            }
        }

        // Gets all the timetables.
        try {
            control.addToCommandHistory("Get All TimeTables");
        } catch (InvalidInputException e){
            System.out.println("Oh No! I can't get the TimeTables");
        }
    }

    /**
     * A UserInterface. The main method of the program and the one that the
     * user interacts with.
     *
     * @param args The arguments
     */
    public static void main(String[] args) {
        UserInterface user = new UserInterface();

        user.run();

        System.out.println("Here are your TimeTable!");
    }
}
