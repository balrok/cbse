package bean;

import java.util.Collection;
import java.util.Map;
import java.util.GregorianCalendar;

public interface IAddAppointment {
    public Map<String, Appointment> addAppointment(GregorianCalendar start, GregorianCalendar end, String title, String notes, Boolean isPrivate, AppointmentType type, Collection<String> userEmails) throws Exception;
}
