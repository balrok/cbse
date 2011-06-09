package bean;

import bean.Appointment;
import bean.AppointmentType;
import javax.ejb.Remove;
import java.util.Date;
import java.util.Collection;

public interface ICalendarMgt
{
    public void addAppointment(Date start, Date end, String title, String notes, Boolean isPrivate, AppointmentType type, Collection<String> userEmails);
    public Collection<Appointment> viewAppointments(String email);
    @Remove void remove();
}
