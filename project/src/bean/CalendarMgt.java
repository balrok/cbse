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

@Stateful
@Remote(ICalendarMgt.class)
public class CalendarMgt implements ICalendarMgt, java.io.Serializable
{
    @PersistenceContext
    private EntityManager manager;

    public void addAppointment(Date start, Date end, String title, String notes, Boolean isPrivate, AppointmentType type, Collection<String> userEmails)
    {
        // TODO
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
