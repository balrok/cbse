package bean;

import bean.AppointmentType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import java.util.GregorianCalendar;

@Entity
public class Appointment implements java.io.Serializable, Comparable<Appointment>
{
    // this id must be added for the databasemanager - just use an autoincrement here
    // generally not of public interest - but i like to see the incrementing ids in the outside world :)
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    public int id;

    public GregorianCalendar start;
    // end is a sql keyword and causes an error when ejb trys to create a table --> so rename it here
    @Column(name="end_time")
    public GregorianCalendar end;
    public String title;
    public String notes;
    // private -> had be named isPrivate cause it is a java keyword
    public Boolean isPrivate;
    public AppointmentType type;

    /*
     * Two appointments are in conflict, if they
     * overlap in time, and none of the two appointments is of type Free
     */
    public Boolean conflictsWith(Appointment a)
    {
        assert(a != null);
        // free is a special type and returns always true
        if (a.type == AppointmentType.FREE || isPrivate)
            return true;
        return overlapsInTime(a);
    }

    /*
     * Will return wether another Appointment overlaps in time with this one
     *
     * to illustrate the following on a timeline (s=start, e=end)
     * first one - not that the end of the other can be at any point after start
     * this :     s-----e
     * other:  s----e
     * second one - when the start of the other is after start of that one and before the end of this one
     * this :  s-----e
     * other:     s----e
    */
    protected Boolean overlapsInTime(Appointment a)
    {
        if (a.start.before(start) && a.end.after(start)
            || a.start.after(start) && a.start.before(end))
        {
            return true;
        }
        return false;
    }

    // implementing Comparable interface for easier sorting
    public int compareTo(Appointment a)
    {
        int result = start.compareTo(a.start);
        return result;
    }
}
