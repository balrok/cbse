package client;


import bean.IUserMgt;
import bean.ICalendarMgt;
import bean.Calendar;
import bean.Appointment;
import bean.AppointmentType;

import javax.naming.InitialContext;
import java.util.HashSet;
import java.util.Date;

public class Client
{
    /**
     * the use of my Date function is deprecated so suppress this warning with following comment
     *  @deprecated
    */
    public static void main(String[] args) throws Exception
    {
        InitialContext ctx = new InitialContext();
        IUserMgt uMgt = (IUserMgt) ctx.lookup("UserMgt/remote");
        ICalendarMgt cMgt = (ICalendarMgt) ctx.lookup("CalendarMgt/remote");
        System.out.println("starting login");
        uMgt.login("name", "email");
        assert(uMgt.getUserCalendar("email") != null);

        System.out.println("starting addAppointment");
        HashSet <String> userEmails = new HashSet <String>();
        userEmails.add("email");
        cMgt.addAppointment(
            new Date(2011,6,19), // start
            new Date(2011,6,20), // end
            "title",
            "notes",
            false, // isPrivate
            AppointmentType.FREE,
            userEmails);
        System.out.println("starting viewAppointments");
        for(Appointment a: cMgt.viewAppointments("email"))
        {
            System.out.println(a.title);
        }
    }
}
