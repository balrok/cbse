package bean;

import java.util.Collection;
import java.util.GregorianCalendar;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import bean.CalendarMgt;
import bean.Appointment;
import java.util.Map;

@Stateful
@Remote(IAddAppointment.class)

public class AddAppointment implements IAddAppointment {
    public Map<String, Appointment> addAppointment(GregorianCalendar start, GregorianCalendar end, String title,
            String notes, Boolean isPrivate, AppointmentType type,
            Collection<String> userEmails) throws Exception{
        // FIXME the following code doesn't work @see(LoginUser)
        CalendarMgt cMgt = new CalendarMgt();
        return cMgt.addAppointment(start, end, title, notes, isPrivate, type, userEmails);
    }
}
