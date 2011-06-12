package client;


import bean.ILoginUser;
import bean.IViewAppointment;
import bean.IAddAppointment;
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
        ILoginUser loginUser = (ILoginUser) ctx.lookup("UserMgt/remote");
        IAddAppointment addApp = (IAddAppointment) ctx.lookup("CalendarMgt/remote");
        IViewAppointment viewApp = (IViewAppointment) ctx.lookup("CalendarMgt/remote");
        System.out.println("starting login with alice");
        loginUser.login("Alice", "alice@a.com");

        System.out.println("starting addAppointment");
        HashSet <String> userEmails = new HashSet <String>();
        userEmails.add("alice@a.com");
        addApp.addAppointment(
            new Date(2011,6,19), // start
            new Date(2011,6,20), // end
            "title",
            "notes",
            false, // isPrivate
            AppointmentType.FREE,
            userEmails);

        System.out.println("starting viewAppointments:");
        viewAppointments(viewApp.viewAppointments("alice@a.com"));
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
