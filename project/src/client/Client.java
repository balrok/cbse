package client;


import bean.IUserMgt;
import bean.ILoginUser;
import bean.ICalendarMgt;
import bean.Appointment;
import bean.AppointmentType;

import javax.naming.InitialContext;
import java.util.HashSet;
import java.util.Collection;
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
        ILoginUser loginUser = (ILoginUser) ctx.lookup("LoginUser/remote");
        ICalendarMgt cMgt = (ICalendarMgt) ctx.lookup("CalendarMgt/remote");
        System.out.println("starting login");
        loginUser.login("Alice", "alice@a.com");
        assert(uMgt.getUserCalendar("email") != null);

        System.out.println("starting addAppointment");
        HashSet <String> userEmails = new HashSet <String>();
        userEmails.add("alice@a.com");
        cMgt.addAppointment(
            new Date(2011,6,19), // start
            new Date(2011,6,20), // end
            "title",
            "notes",
            false, // isPrivate
            AppointmentType.FREE,
            userEmails);

        System.out.println("starting viewAppointments:");
        viewAppointments(cMgt.viewAppointments("alice@a.com"));
    }

    public static void viewAppointments(Collection<Appointment> appointments)
    {
        for(Appointment a: appointments)
        {
            printAppointment(a);
        }
    }

    public static void printAppointment(Appointment a)
    {
        System.out.printf("%d: %s", a.id, a.title);
        System.out.println("");
    }
}
