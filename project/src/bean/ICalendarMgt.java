package bean;

import bean.Appointment;
import bean.IAddAppointment;
import bean.IViewAppointment;
import bean.AppointmentType;
import java.util.GregorianCalendar;
import java.util.Collection;
import java.util.Map;
import java.util.List;

// our interfaces, which are visible for the client getting extended here - nevertheless redefine those methods here too cause they
// can also be used at other servercomponents
public interface ICalendarMgt extends IAddAppointment, IViewAppointment
{
    public Map<String, Appointment> addAppointment(GregorianCalendar start, GregorianCalendar end, String title, String notes, Boolean isPrivate, AppointmentType type, Collection<String> userEmails) throws Exception;
    public List<Appointment> viewAppointments(String email);
}
