package InterfaceAdaptors;
import Commands.Command;
import Commands.FunctionCommands.ExitProgramCommand;
import Commands.ManagerChanged;
import Helpers.InvalidInputException;
import TimeTableContainers.Caretaker;
import TimeTableContainers.Originator;
import TimeTableContainers.TimeTableManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Stack;

/**
 * A database controller class that serves as the Invoker in the command
 * pattern. It sets the command and executes them.
 *
 * === Private Attributes ===
 * CommandHistory: The history of all the commands that has ever been made
 *  since the program was run (For Phase 2, maybe serialize it so that it can
 *  have a history of all commands ever made)
 * possibleCommands: The factory that generates the commands to be executed
 * currentManagerIndex: the current index of the TimeTableManager in the list of stored managers
 * Originator: Sets and gets TimeTableManager from Memento
 * Caretaker: Stores all Memento of TimeTableManager
 */
public class DatabaseController {
    private final Stack<Command> CommandHistory;
    private CommandFactory Factory;
    private int currentManagerIndex;
    private final Originator originator;
    private final Caretaker caretaker;

    /**
     * A constructor. Sets the commandHistory and Factory
     */
    public DatabaseController(){
        this.CommandHistory = new Stack<>();
        this.Factory = null;
        this.currentManagerIndex = 0;
        this.caretaker = new Caretaker();
        this.originator = new Originator();
    }

    /**
     * Sets the command into the Command History.
     *
     * The command history will act as a history of all the commands in order
     * they were added. The setCommands will also execute the command.
     *
     * @param requestedCommand the command that has been requested
     * @return true iff the command has been run properly. False to indicate
     * that the program should exit
     * @exception InvalidInputException Throws invalid Input exception if the
     * input command is invalid
     */
    public boolean runCommand(String requestedCommand) throws InvalidInputException {
        assert this.Factory != null;
        Command theCommand = this.Factory.getCommand(requestedCommand);

        // If the command is to exit the program, it will return false to let
        // UserInterface know to exit the program
        if (theCommand instanceof ExitProgramCommand) {
            return false;
        }

        // Execute the command. Throws InvalidInputException if the command is
        // invalid
        executeCommand(theCommand);

        //Add to memento if TimeTableManager is changed
        if (theCommand instanceof ManagerChanged && ((ManagerChanged) theCommand).managerChanged()) {
            this.originator.setManager(((ManagerChanged) theCommand).getManager());
            this.caretaker.addMemento(currentManagerIndex, originator.storeInMemento());
            addManagerIndex();
        }

        return true;
    }

    /**
     * Add 1 to currentManagerIndex
     */
    public void addManagerIndex() { currentManagerIndex++;}

    /**
     * Remove 1 from currentManagerIndex
     */
    public void removeManagerIndex() {
        currentManagerIndex--;
    }

    // ===================== Command Pattern Infrastructure ====================
    /**
     * Sends the command into the commandHistory and executes the command.
     *
     * The execute method is an abstract method so all command objects will
     * have it.
     *
     * @param theCommand the command
     */
    private void executeCommand(Command theCommand){
        this.CommandHistory.push(theCommand);
        theCommand.execute();
    }

    // ============================ Setters and Getters ========================
    /**
     * Set the factory attached to this controller
     *
     * @param theFactory the factory to be set
     */
    public void setFactory(CommandFactory theFactory){
        this.Factory = theFactory;
    }

    /**
     * Set the manager to the factory
     *
     */
    public void setManager(){
        TimeTableManager newManager = new TimeTableManager();
        this.Factory.setManager(newManager);
        this.originator.setManager(newManager);
        this.caretaker.addMemento(currentManagerIndex, this.originator.storeInMemento());
        this.currentManagerIndex++;
    }

    /**
     * Get a Stack of the commands that has been executed in the history
     * @return The Stack of commands.
     */
    public Stack<Command> getCommandHistory() {
        return CommandHistory;
    }

    /**
     * Returns a hashmap of the entries in the string array commandList with
     * corresponding integer values from least to greatest.
     *
     * @return a hashmap of items from strings as values and ascending
     * integers as keys
     */
    public LinkedHashMap<String, String> getAllowedFunctions(){
        String[] commandList = this.Factory.getAllowedFunctions();
        LinkedHashMap<String, String> enumerateToCommandMap = new LinkedHashMap<>();
        for (int i = 0; i < commandList.length; i++){
            enumerateToCommandMap.put(String.valueOf(i), commandList[i]);
        }
        return enumerateToCommandMap;
    }

    /**
     * Gets the currentManagerIndex
     * @return the currentManagerIndex
     */
    public int getCurrentManagerIndex() {return currentManagerIndex; }

    /**
     * Gets the originator
     * @return the originator
     */
    public Originator getOriginator() {return originator; }

    /**
     * Gets the caretaker
     * @return the caretaker
     */
    public Caretaker getCaretaker() {return caretaker; }
}
