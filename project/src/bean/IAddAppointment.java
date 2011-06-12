package bean;

import java.util.Collection;
import java.util.Date;

public interface IAddAppointment {
    public Boolean addAppointment(Date start, Date end, String title, String notes, Boolean isPrivate, AppointmentType type, Collection<String> userEmails);
}
