package bean;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Appointment implements java.io.Serializable
{
    // this id must be added for the databasemanager - just use an autoincrement here
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
}
