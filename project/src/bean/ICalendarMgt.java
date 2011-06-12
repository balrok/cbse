package bean;

import bean.Appointment;
import bean.IAddAppointment;
import bean.IViewAppointment;
import bean.AppointmentType;
import java.util.Date;
import java.util.Collection;

// our interfaces, which are visible for the client getting extended here - nevertheless redefine those methods here too cause they
// can also be used at other servercomponents
public interface ICalendarMgt extends IAddAppointment, IViewAppointment
{
    public Boolean addAppointment(Date start, Date end, String title, String notes, Boolean isPrivate, AppointmentType type, Collection<String> userEmails);
    public Collection<Appointment> viewAppointments(String email);
}
