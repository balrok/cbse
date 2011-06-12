package bean;

import java.util.Collection;

public interface IViewAppointment {
    public Collection<Appointment> viewAppointments(String email);
}
