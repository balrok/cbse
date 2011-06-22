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
        //testDoubleAppointment();
        InitialContext ctx = new InitialContext();
        ILoginUser loginUser = (ILoginUser) ctx.lookup("UserMgt/remote");
        IAddAppointment addApp = (IAddAppointment) ctx.lookup("CalendarMgt/remote");
        IViewAppointment viewApp = (IViewAppointment) ctx.lookup("CalendarMgt/remote");

        System.out.println("Enter your name: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String name = in.readLine();
        System.out.println("Enter your email: ");
        String email = in.readLine();

        System.out.println((loginUser.login(name, email))?"Created a new account":"Welcome back "+name);

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
                SimpleDateFormat dateFormater = new SimpleDateFormat("dd.MM.yy HH:mm");
                while (true)
                {
                    System.out.println("Enter the starting date with format dd.MM.yy HH:mm");
                    String input = in.readLine();
                    Date inpTime;
                    try
                    {
                        inpTime = dateFormater.parse(input);
                    }
                    catch (Exception e)
                    {
                        System.out.println("wrong format - try again");
                        continue;
                    }
                    Calendar cal=Calendar.getInstance();
                    cal.setTime(inpTime);
                    start= (GregorianCalendar) cal;
                    break;
                }
                while (true)
                {
                    System.out.println("Enter the ending date with format dd.MM.yy HH:mm");
                    String input = in.readLine();
                    Date inpTime;
                    try
                    {
                        inpTime = dateFormater.parse(input);
                    }
                    catch (Exception e)
                    {
                        System.out.println("wrong format - try again");
                        continue;
                    }
                    Calendar cal=Calendar.getInstance();
                    cal.setTime(inpTime);
                    end = (GregorianCalendar) cal;
                    if (end.before(start))
                    {
                        System.out.println("End should be after start");
                        continue;
                    }
                    break;
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
                while (true)
                {
                    System.out.println("Should this appointment be private? (Y/N):");
                    String input = in.readLine().toLowerCase();
                    if (input.equals("y"))
                        isPrivate = true;
                    else if (input.equals("n"))
                        isPrivate = false;
                    else
                        continue;
                    break;
                }
                while (true)
                {
                    System.out.println("What type is this Appointment? (free, blocked, pot.blocked, away)");
                    String input = in.readLine().toLowerCase();
                    if (input.equals("free"))
                        type = AppointmentType.FREE;
                    else if (input.equals("blocked"))
                        type = AppointmentType.BLOCKED;
                    else if (input.equals("pot.blocked"))
                        type = AppointmentType.POTENTIALLYBLOCKED;
                    else if (input.equals("away"))
                        type = AppointmentType.AWAY;
                    else if (input.equals("free"))
                        type = AppointmentType.FREE;
                    else
                        continue;
                    break;
                }
                while (true)
                {
                    System.out.println("Enter other users emails which should also get this appointment or an empty line to continue:");
                    String input = in.readLine();
                    if (input.equals(""))
                        break;
                    userEmails.add(input);
                }

                // also add own
                userEmails.add(email);
                Map<String, Appointment> errorAppointments = addApp.addAppointment(
                    start,
                    end,
                    title,
                    notes,
                    isPrivate,
                    type,
                    userEmails
                );
                if (errorAppointments != null)
                {
                    System.out.println("Couldn't add appointment");
                    if (!errorAppointments.isEmpty())
                    {
                        System.out.println("List of email-addresses which are conflicting:");
                        for(Map.Entry<String, Appointment> e: errorAppointments.entrySet())
                        {
                            System.out.println(e.getKey()); //+" - "+(e.getValue().isPrivate?"*private Appointment*":e.getValue().title));
                        }
                    }
                    else
                        System.out.println("Something is gone terribly wrong, you can't do anything :(");
                }
            }
            else if (nextStep.equals("2"))
            {
                viewAppointments(viewApp.viewAppointments(email));
            }
        }
    }

    public static void viewAppointments(Collection<Appointment> appointments)
    {
        if (appointments == null || appointments.isEmpty())
        {
            System.out.println("No appointments");
            return;
        }
        System.out.printf("start\t\tend\t\tprivate\ttype\ttitle\tnotes\n");
        for(Appointment a: appointments)
        {
            printAppointment(a);
        }
    }

    public static void printAppointment(Appointment a)
    {
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd.MM.yy HH:mm");
        System.out.printf("%s\t%s\t%s\t%s\t%s\t%s\n",
            dateFormater.format(a.start.getTime()),
            dateFormater.format(a.end.getTime()),
            a.isPrivate,
            a.type,
            a.title,
            a.notes);
    }

    // to use this just call it in the main function - the expected output is, that the appointment couldn't be added for the first one -
    // but cold be added for the 2nd one
    public static void testDoubleAppointment() throws Exception
    {
        InitialContext ctx = new InitialContext();
        ILoginUser loginUser = (ILoginUser) ctx.lookup("UserMgt/remote");
        IAddAppointment addApp = (IAddAppointment) ctx.lookup("CalendarMgt/remote");
        IViewAppointment viewApp = (IViewAppointment) ctx.lookup("CalendarMgt/remote");

        {
            System.out.println("enter 'a:a'");
            String name = "a";
            String email = "a";
            System.out.println((loginUser.login(name, email))?"Created a new account":"Welcome back "+name);
            System.out.println("viewapp");
            viewAppointments(viewApp.viewAppointments(email));

            GregorianCalendar start = new GregorianCalendar();
            GregorianCalendar end = new GregorianCalendar();
            String title;
            String notes;
            Boolean isPrivate;
            AppointmentType type;
            HashSet <String> userEmails = new HashSet <String>();
            SimpleDateFormat dateFormater = new SimpleDateFormat("dd.MM.yy HH:mm");
            Date inpTime = dateFormater.parse("01.07.11 14:00");
            Calendar cal=Calendar.getInstance();
            cal.setTime(dateFormater.parse("01.07.11 14:00"));
            start = (GregorianCalendar) cal;
            Calendar cal2=Calendar.getInstance();
            cal2.setTime(dateFormater.parse("01.07.11 15:00"));
            end = (GregorianCalendar) cal2;
            title = "1";
            notes = "1";
            isPrivate = false;
            type = AppointmentType.BLOCKED;
            userEmails.add(email);
            for (int i = 0; i<=2; i++)
            {
                title = title+i;
                Map<String, Appointment> errorAppointments = addApp.addAppointment(
                    start,
                    end,
                    title,
                    notes,
                    isPrivate,
                    type,
                    userEmails
                );
                if (errorAppointments != null)
                {
                    System.out.println("Couldn't add appointment");
                    if (!errorAppointments.isEmpty())
                    {
                        System.out.println("List of email-addresses which are conflicting:");
                        for(Map.Entry<String, Appointment> e: errorAppointments.entrySet())
                        {
                            System.out.println(e.getKey()); //+" - "+(e.getValue().isPrivate?"*private Appointment*":e.getValue().title));
                        }
                    }
                    else
                        System.out.println("Something is gone terribly wrong, you can't do anything :(");
                }
            }

            System.out.println("viewapp");
            viewAppointments(viewApp.viewAppointments(email));
        }

        {
            System.out.println("enter 'b:b'");
            String name = "b";
            String email = "b";
            System.out.println((loginUser.login(name, email))?"Created a new account":"Welcome back "+name);
            System.out.println("viewapp");
            viewAppointments(viewApp.viewAppointments(email));

            GregorianCalendar start = new GregorianCalendar();
            GregorianCalendar end = new GregorianCalendar();
            String title;
            String notes;
            Boolean isPrivate;
            AppointmentType type;
            HashSet <String> userEmails = new HashSet <String>();
            SimpleDateFormat dateFormater = new SimpleDateFormat("dd.MM.yy HH:mm");
            Date inpTime = dateFormater.parse("01.07.11 14:00");
            Calendar cal=Calendar.getInstance();
            cal.setTime(dateFormater.parse("01.07.11 14:00"));
            start = (GregorianCalendar) cal;
            Calendar cal2=Calendar.getInstance();
            cal2.setTime(dateFormater.parse("01.07.11 15:00"));
            end = (GregorianCalendar) cal2;
            title = "1";
            notes = "1";
            isPrivate = false;
            type = AppointmentType.FREE;
            userEmails.add(email);
            userEmails.add("a");
            for (int i = 0; i<=2; i++)
            {
                title = title+i;
                Map<String, Appointment> errorAppointments = addApp.addAppointment(
                    start,
                    end,
                    title,
                    notes,
                    isPrivate,
                    type,
                    userEmails
                );
                if (errorAppointments != null)
                {
                    System.out.println("Couldn't add appointment");
                    if (!errorAppointments.isEmpty())
                    {
                        System.out.println("List of email-addresses which are conflicting:");
                        for(Map.Entry<String, Appointment> e: errorAppointments.entrySet())
                        {
                            System.out.println(e.getKey()); //+" - "+(e.getValue().isPrivate?"*private Appointment*":e.getValue().title));
                        }
                    }
                    else
                        System.out.println("Something is gone terribly wrong, you can't do anything :(");
                }
            }

            System.out.println("viewapp");
            viewAppointments(viewApp.viewAppointments(email));
        }
    }
}
