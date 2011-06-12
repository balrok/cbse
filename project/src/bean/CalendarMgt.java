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
import java.util.ArrayList;
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

    public Boolean addAppointment(GregorianCalendar start, GregorianCalendar end, String title, String notes, Boolean isPrivate, AppointmentType type, Collection<String> userEmails)
    {
        Appointment app = new Appointment();
        app.start = start;
        app.end = end;
        app.title = title;
        app.notes = notes;
        app.isPrivate = isPrivate;
        app.type = type;

        IUserMgt uMgt = null;
        try {
            InitialContext ctx = new InitialContext();
            uMgt = (IUserMgt) ctx.lookup("UserMgt/remote");
        } catch (Exception e)
        {
            return false;
        }

        for (String email: userEmails)
        {
            Calendar cal = uMgt.getUserCalendar(email);
            if (cal == null || cal.hasConflict(app))
                return false;
        }
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
        return true;
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
        List sortedAppointments = new ArrayList(appointments);
        Collections.sort(sortedAppointments);
        return sortedAppointments;
    }
}
