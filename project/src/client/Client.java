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
import java.util.Date;
import java.util.Calendar;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.text.SimpleDateFormat;

public class Client
{
    public static void main(String[] args) throws Exception
    {
        InitialContext ctx = new InitialContext();
        ILoginUser loginUser = (ILoginUser) ctx.lookup("UserMgt/remote");
        IAddAppointment addApp = (IAddAppointment) ctx.lookup("CalendarMgt/remote");
        IViewAppointment viewApp = (IViewAppointment) ctx.lookup("CalendarMgt/remote");

        System.out.println("Enter your name: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String name = in.readLine();
        System.out.println("Enter your email: ");
        String email = in.readLine();

        Boolean newUser = loginUser.login(name, email);
        if (newUser)
            System.out.println("Created a new account");
        else
            System.out.println("Welcome back "+name);

        String nextStep = "42";
        while (!nextStep.toLowerCase().equals("q"))
        {
            System.out.println("You can now either enter a new appointment[1] or view your appointments[2]. Enter the number to switch to the menu or [q] to exit.");
            nextStep = in.readLine();

            if (nextStep.equals("1"))
            {
                GregorianCalendar start = new GregorianCalendar();
                GregorianCalendar end = new GregorianCalendar();
                String title;
                String notes;
                Boolean isPrivate;
                AppointmentType type;
                HashSet <String> userEmails = new HashSet <String>();
                {
                    while (true)
                    {
                        SimpleDateFormat dateFormater = new SimpleDateFormat("dd.MM.yy hh:mm");
                        System.out.println("Enter the starting date with format dd.MM.yy hh:mm");
                        String input = in.readLine();
                        Date inpTime;
                        try
                        {
                            inpTime = dateFormater.parse(input);
                        }
                        catch (Exception e)
                        {
                            System.out.println("wrong format");
                            continue;
                        }
                        Calendar cal=Calendar.getInstance();
                        cal.setTime(inpTime);
                        start= (GregorianCalendar) cal;
                        break;
                    }
                }
                {
                    while (true)
                    {
                        SimpleDateFormat dateFormater = new SimpleDateFormat("dd.MM.yy hh:mm");
                        System.out.println("Enter the ending date with format dd.MM.yy hh:mm");
                        String input = in.readLine();
                        Date inpTime;
                        try
                        {
                            inpTime = dateFormater.parse(input);
                        }
                        catch (Exception e)
                        {
                            System.out.println("wrong format");
                            continue;
                        }
                        Calendar cal=Calendar.getInstance();
                        cal.setTime(inpTime);
                        end = (GregorianCalendar) cal;
                        break;
                    }
                }
                {
                    System.out.println("Enter the title:");
                    String input = in.readLine();
                    title = input;
                }
                {
                    System.out.println("Enter notes:");
                    String input = in.readLine();
                    notes = input;
                }
                {
                    System.out.println("Should this appointment be private? (Y/N):");
                    String input = in.readLine();
                    isPrivate = (input.toLowerCase().equals("y"));
                }
                {
                    System.out.println("What type is this Appointment? (free, blocked, pot.blocked, away)");
                    String input = in.readLine();
                    if (input.toLowerCase().equals("free"))
                        type = AppointmentType.FREE;
                    else if (input.toLowerCase().equals("blocked"))
                        type = AppointmentType.BLOCKED;
                    else if (input.toLowerCase().equals("pot.blocked"))
                        type = AppointmentType.POTENTIALLYBLOCKED;
                    else if (input.toLowerCase().equals("away"))
                        type = AppointmentType.AWAY;
                    else
                        type = AppointmentType.FREE;
                }
                {
                    while (true)
                    {
                        System.out.println("Enter other users emails which should also get this appointment or an empty line to continue:");
                        String input = in.readLine();
                        if (input.equals(""))
                            break;
                        userEmails.add(input);
                    }
                }

                userEmails.add(email);
                Map<String, Appointment> errorAppointments = addApp.addAppointment(
                    start,
                    end,
                    title,
                    notes,
                    isPrivate,
                    type,
                    userEmails);
                if (errorAppointments != null)
                    System.out.println("Couldn't add appointment");
            }
            else if (nextStep.equals("2"))
            {
                viewAppointments(viewApp.viewAppointments(email));
            }
        }
    }

    public static void viewAppointments(Collection<Appointment> appointments)
    {
        if (appointments == null)
            return;
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
