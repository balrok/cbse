package bean;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.FetchType;
import java.util.Collection;

@Entity
public class Calendar implements java.io.Serializable
{
    // this id must be added for the databasemanager - just use an autoincrement here
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Appointment> appointments;

    public void addAppointment(Appointment a)
    {
        appointments.add(a);
    }

    public Collection<Appointment> viewAppointments()
    {
        return appointments;
    }

    public Boolean hasConflict(Appointment a)
    {
        return false;
    }
}
