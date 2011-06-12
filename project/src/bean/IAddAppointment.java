package bean;

import java.util.Collection;
import java.util.GregorianCalendar;

public interface IAddAppointment {
    public Boolean addAppointment(GregorianCalendar start, GregorianCalendar end, String title, String notes, Boolean isPrivate, AppointmentType type, Collection<String> userEmails);
}
