package bean;

import java.util.Collection;
import java.util.GregorianCalendar;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import bean.CalendarMgt;

@Stateful
@Remote(IAddAppointment.class)

public class AddAppointment implements IAddAppointment {
    public Boolean addAppointment(GregorianCalendar start, GregorianCalendar end, String title,
            String notes, Boolean isPrivate, AppointmentType type,
            Collection<String> userEmails) {
        // FIXME the following code doesn't work @see(LoginUser)
        CalendarMgt cMgt = new CalendarMgt();
        return cMgt.addAppointment(start, end, title, notes, isPrivate, type, userEmails);
    }
}
