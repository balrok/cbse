package bean;

import bean.AppointmentType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import java.util.Date;

@Entity
public class Appointment implements java.io.Serializable
{
    // this id must be added for the databasemanager - just use an autoincrement here
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    public Date start;
    // end is a sql keyword so rename it here
    @Column(name="end_time")
    public Date end;
    public String title;
    public String notes;
    public Boolean isPrivate;
    public AppointmentType type;

    public Boolean conflictsWith(Appointment a)
    {
        return false;
    }
}
