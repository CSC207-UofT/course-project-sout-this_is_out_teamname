package TimeTableContainers;

import Helpers.ConflictException;
import Helpers.Constants;
import TimeTableObjects.Events;

// Importing HashMap class
import java.util.*;


/**
 * A manager that manages and holds different timetables.
 * === Private Attributes ===
 * timetables contains different timetables as values and their corresponding names as keys.
 */
public class TimeTableManager {
    private final HashMap<String, TimeTable> timetables;
    /**
     * Creates a new TimeTableManager with two default TimeTables for Fall and Winter.
     */
    public TimeTableManager() {
        this.timetables = new HashMap<>(){{
            put(Constants.FALL, new TimeTable());
            put(Constants.WINTER, new TimeTable());
        }};
    }

    /**
     * Get an event from the user interface and schedule it to the corresponding timetable(s).
     * Precondition: The event to be scheduled will not result in conflicts.
     * @param event an Events passed from user interface
     *
     */
    public void schedule(Events event) {
        switch (event.getTerm()){
            case Constants.FALL:
                timetables.get(Constants.FALL).schedule(event);
                break;
            case Constants.WINTER:
                timetables.get(Constants.WINTER).schedule(event);
                break;
            default:
                timetables.get(Constants.FALL).schedule(event);
                timetables.get(Constants.WINTER).schedule(event);
        }
//        if (event.getTerm().equals(Constants.FALL)){
//           timetables.get(Constants.FALL).schedule(event);
//
//        }
//        else if (event.getTerm().equals(Constants.WINTER)){
//            timetables.get(Constants.WINTER).schedule(event);
//        }
//        else{
//            timetables.get(Constants.FALL).schedule(event);
//            timetables.get(Constants.WINTER).schedule(event);
//        }
    }
        //TODO fix raw parameterized classes
    /**
     * Reformats a given timetable from a hashmap of Events objects to a hashmap of strings
     * The keys are the weekdays. The value is an arraylist of arraylist of strings.
     * The outer-layer of the arraylist contains 25 elements, index 0 to 23 corresponds to hours of the day,
     * each hour contains an arraylist of strings representing the corresponding Event object scheduled in the timetable
     * index 24 contains an arraylist of task objects which are all day events.
     *
     * @param timetable is a timetable object
     * @return hashmap of strings representation of the  given timetable.
     */
    public LinkedHashMap<String, ArrayList<ArrayList<String>>> reformat(TimeTable timetable){
        //creates the hashmap first
        LinkedHashMap calendar = new LinkedHashMap<>() {{
            put(Constants.MONDAY, new ArrayList<ArrayList<String>>(24));
            put(Constants.TUESDAY, new ArrayList<ArrayList<String>>(24));
            put(Constants.WEDNESDAY, new ArrayList<ArrayList<String>>(24));
            put(Constants.THURSDAY, new ArrayList<ArrayList<String>>(24));
            put(Constants.FRIDAY, new ArrayList<ArrayList<String>>(24));
            put(Constants.SATURDAY, new ArrayList<ArrayList<String>>(24));
            put(Constants.SUNDAY, new ArrayList<ArrayList<String>>(24));
        }};
        //iterate through timetable for event objects.
        Set keys = calendar.keySet();
        for (Object list: keys){


        }


        return calendar;
    }

    /**
     * Add a new empty TimeTable with the given term.
     * @param term given term the of timetable
     * @return true if we successfully add a new TimeTable, else false.
     */
    public boolean addTimeTable(String term) {
        if (this.timetables.containsKey(term)) {
            return false;
        }
        else{
            timetables.put(term, new TimeTable());
            return true;
        }
    }

    /**
     * Remove an existing TimeTable with the given term.
     * @param term given term the of timetable
     * @return true if we successfully remove a TimeTable, else false.
     */
    public boolean removeTimeTable(String term) {
        if (this.timetables.containsKey(term)) {
            timetables.remove(term);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Get the terms of the timetables
     * @return the set of terms of the timetables
     */
    public Set<String> getTerms() {
        return this.timetables.keySet();
    }

    /**
     * Returns an array of timetables with all the timetables.
     *
     * @return an array of timetables with all the timetables
     */
    public TimeTable[] getAllTimeTables(){
        TimeTable[] theTimes =
                new TimeTable[this.timetables.keySet().size()];
        int i = 0;
        for (String term : this.timetables.keySet()){
            theTimes[i] = this.timetables.get(term);
            i++;
        }
        return theTimes;
    }

    /**
     * Get the timetable object for the given term.
     *
     * @return the corresponding TimeTable object.
     */
    public TimeTable getTimetable(String term) {
        if (this.timetables.containsKey(term)) {
            return timetables.get(term);
        }
        else{
            //TODO exceptions later
            return null;
        }
    }

    /**
     * Presents the TimeTableManager in string
     * @return a string representation of the TimeTableManager
     */
    public String toString() {
        LinkedHashMap<String, String> times = new LinkedHashMap<>();
        for (String term : this.timetables.keySet()) {
            times.put(term, this.timetables.get(term).toString());
        }
        return times.toString();
    }
}
