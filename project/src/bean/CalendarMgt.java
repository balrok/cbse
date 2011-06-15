package bean;

import bean.Calendar;
import bean.Appointment;
import bean.AppointmentType;
import bean.IAddAppointment;
import bean.IViewAppointment;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.Stateful;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.GregorianCalendar;

import bean.IUserMgt;
import bean.Calendar;
import javax.naming.InitialContext;

@Stateful
@Remote(ICalendarMgt.class)
public class CalendarMgt implements ICalendarMgt
{
    @PersistenceContext
    private EntityManager manager;

    // returns a list of appointments which are in conflict or null
    // throws exception if something strange happened (couldn't find UserMgt/remote)
    public Map<String, Appointment> addAppointment(GregorianCalendar start, GregorianCalendar end, String title, String notes, Boolean isPrivate, AppointmentType type, Collection<String> userEmails) throws Exception
    {
        Map<String, Appointment> errorAppointments = new HashMap<String, Appointment>();
        Appointment app = new Appointment();
        app.start = start;
        app.end = end;
        app.title = title;
        app.notes = notes;
        app.isPrivate = isPrivate;
        app.type = type;

        IUserMgt uMgt = null;
        InitialContext ctx = new InitialContext();
        uMgt = (IUserMgt) ctx.lookup("UserMgt/remote");

        for (String email: userEmails)
        {
            Calendar cal = uMgt.getUserCalendar(email);
            if (cal == null || cal.hasConflict(app))
            {
                if (app.isPrivate)
                {
                    // cause we shouldn't send the user full information about a private appointment
                    app = new Appointment();
                }
                errorAppointments.put(email, app);
            }
        }
        if (!errorAppointments.isEmpty())
            return errorAppointments;
        for (String email: userEmails)
        {
            Calendar cal = uMgt.getUserCalendar(email);
            cal.addAppointment(app);

            /*
             * persist can only be used when an object is new - merge is something like update/add if not exist
             * persist(app) was not possible cause the manytomany relation wasn't saved then and an error occured
             * perhaps someone wants to validate later the usage of merge in this context
             */
            manager.merge(cal);
        }
        return null;
    }

    public List<Appointment> viewAppointments(String email)
    {
        IUserMgt uMgt = null;
        try {
            InitialContext ctx = new InitialContext();
            uMgt = (IUserMgt) ctx.lookup("UserMgt/remote");
        } catch (Exception e)
        {
            return null;
        }

        Calendar cal = uMgt.getUserCalendar(email);
        if (cal == null)
            return null;
        Collection<Appointment> appointments = cal.viewAppointments();
        List sortedAppointments = new LinkedList(appointments);
        Collections.sort(sortedAppointments);
        return sortedAppointments;
    }
}
