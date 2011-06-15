package client;


import bean.ILoginUser;
import bean.IViewAppointment;
import bean.IAddAppointment;
import bean.Appointment;
import bean.AppointmentType;

import javax.naming.InitialContext;
import java.util.HashSet;
import java.util.Collection;
import java.util.Map;
import java.util.GregorianCalendar;

public class Client
{
    public static void main(String[] args) throws Exception
    {
        InitialContext ctx = new InitialContext();
        ILoginUser loginUser = (ILoginUser) ctx.lookup("UserMgt/remote");
        IAddAppointment addApp = (IAddAppointment) ctx.lookup("CalendarMgt/remote");
        IViewAppointment viewApp = (IViewAppointment) ctx.lookup("CalendarMgt/remote");
        System.out.println("starting login with alice");
        Boolean newUser = loginUser.login("Alice", "alice@a.com");
        if (newUser)
            System.out.println("created new account");

        System.out.println("starting addAppointment");
        HashSet <String> userEmails = new HashSet <String>();
        userEmails.add("alice@a.com");
        Map<String, Appointment> errorAppointments = addApp.addAppointment(
            new GregorianCalendar(2011, 6, 19, 12, 30),
            new GregorianCalendar(2011, 6, 19, 13, 30),
            "title",
            "notes",
            false, // isPrivate
            AppointmentType.FREE,
            userEmails);
        if (errorAppointments != null)
            System.out.println("Couldn't add appointment");

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
