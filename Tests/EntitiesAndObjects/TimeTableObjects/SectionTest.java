package EntitiesAndObjects.TimeTableObjects;

import GlobalHelpers.Constants;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;

import static org.junit.Assert.*;

public class SectionTest {

    @Before
    public void setUp() {
    }

    @Test
    public void testToString() {
    }

    @Test
    public void getCode() {
    }

    @Test
    public void getProfessor() {
    }

    @Test
    public void getFaculty() {
    }

    @Test
    public void getDeliveryMethod() {
    }

    @Test
    public void compareTo() {
        Time Time8 = new Time(8,0,0);
        Time Time9 = new Time(9,0,0);
        Time Time10 = new Time(10, 0,0);

        Time Time1030 = new Time (10,30,0);
        Time Time11 = new Time(11,0,0);

        CourseSection lecture0 = new CourseSection(Time9,Time10,"Professor: Gauss, Faculty: Arts and Science" +
                "Delivery Method: In Person, Location: SS100", Constants.MONDAY, Constants.YEAR, "MAT257");
        CourseSection lecture1 = new CourseSection(Time9,Time10,"Professor: Gauss, Faculty: Arts and Science" +
                "Delivery Method: In Person, Location: SS100", Constants.MONDAY,Constants.YEAR, "MAT257");
        CourseSection lecture2 = new CourseSection(Time9,Time11,"Professor: Descartes, Faculty: Arts and Science" +
                "Delivery Method: Online, Location: SS101",Constants.MONDAY,Constants.FALL, "MAT157");
        CourseSection lecture3 = new CourseSection(Time10,Time11,"Professor: Alphonso, Faculty: Arts and Science" +
                "Delivery Method: Online, Location: SS100",Constants.MONDAY,Constants.FALL, "MAT137");
        CourseSection lecture4 = new CourseSection(Time1030,Time11,"Professor: Alphonso, Faculty: Arts and Science" +
                "Delivery Method: Online, Location: SS100",Constants.MONDAY,Constants.FALL, "MAT137");
        CourseSection lecture5 = new CourseSection(Time8,Time10,"Professor: Alphonso, Faculty: Arts and Science" +
                "Delivery Method: Online, Location: SS100",Constants.MONDAY,Constants.FALL, "MAT137");
        CourseSection lecture6 = new CourseSection(Time8,Time11,"Professor: Alphonso, Faculty: Arts and Science" +
                "Delivery Method: Online, Location: SS100",Constants.MONDAY,Constants.FALL, "MAT137");

        assertEquals(lecture1.compareTo(lecture0), -1);
        assertEquals(lecture1.compareTo(lecture2), -1);
        assertEquals(lecture1.compareTo(lecture3), 1);
        assertEquals(lecture1.compareTo(lecture4), 1);
        assertEquals(lecture1.compareTo(lecture5), -1);
        assertEquals(lecture2.compareTo(lecture1), -1);
        assertEquals(lecture2.compareTo(lecture3), -1);
        assertEquals(lecture6.compareTo(lecture2), -1);
    }
}
