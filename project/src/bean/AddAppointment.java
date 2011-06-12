package bean;

import java.util.Collection;
import java.util.Date;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import bean.CalendarMgt;

@Stateful
@Remote(IAddAppointment.class)

public class AddAppointment implements IAddAppointment {

	@Override
	public Boolean addAppointment(Date start, Date end, String title,
			String notes, Boolean isPrivate, AppointmentType type,
			Collection<String> userEmails) {
		// TODO Auto-generated method stub
		CalendarMgt cMgt = new CalendarMgt();
		return cMgt.addAppointment(start, end, title, notes, isPrivate, type, userEmails);
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

}
