import java.util.ArrayList;
import java.util.List;

public class Pasajero extends Persona {
    private String asiento;
    private List<String> serviciosAdicionales;

    public Pasajero(String nombre, String identificacion, String asiento) {
        super(nombre, identificacion);
        this.asiento = asiento;
        this.serviciosAdicionales = new ArrayList<>();
    }

    public Pasajero(String nombre, String identificacion, String asiento, 
                    List<String> serviciosAdicionales) {
        super(nombre, identificacion);
        this.asiento = asiento;
        this.serviciosAdicionales = new ArrayList<>(serviciosAdicionales);
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public void agregarServicioAdicional(String servicio) {
        serviciosAdicionales.add(servicio);
    }

    public List<String> getServiciosAdicionales() {
        return new ArrayList<>(serviciosAdicionales);
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("Pasajero: " + nombre + " (ID: " + identificacion + ")");
        System.out.println("  Asiento: " + asiento);
        if (!serviciosAdicionales.isEmpty()) {
            System.out.println("  Servicios adicionales: " + String.join(", ", serviciosAdicionales));
        }
    }
}