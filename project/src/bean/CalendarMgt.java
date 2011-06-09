package bean;

import bean.Calendar;
import bean.Appointment;
import bean.AppointmentType;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import bean.IUserMgt;
import bean.Calendar;
import javax.naming.InitialContext;

@Stateful
@Remote(ICalendarMgt.class)
public class CalendarMgt implements ICalendarMgt, java.io.Serializable
{
    @PersistenceContext
    private EntityManager manager;

    public Boolean addAppointment(Date start, Date end, String title, String notes, Boolean isPrivate, AppointmentType type, Collection<String> userEmails)
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
        }
        return true;
    }

    public Collection<Appointment> viewAppointments(String email)
    {
        return null;
    }

    @Remove
    public void remove()
    {
    }
}
