import java.util.ArrayList;
import java.util.List;

public class VueloEjecutivo extends Vuelo {
    private String tipoAeronave;
    private List<String> serviciosEspeciales;

    public VueloEjecutivo(String numeroVuelo, String origen, String destino, 
                         String fecha, String horaSalida, int capacidadMaxima, 
                         String tipoAeronave) {
        super(numeroVuelo, origen, destino, fecha, horaSalida, capacidadMaxima);
        this.tipoAeronave = tipoAeronave;
        this.serviciosEspeciales = new ArrayList<>();
    }

    public String getTipoAeronave() {
        return tipoAeronave;
    }

    public void setTipoAeronave(String tipoAeronave) {
        this.tipoAeronave = tipoAeronave;
    }

    public void agregarServicioEspecial(String servicio) {
        serviciosEspeciales.add(servicio);
    }

    public List<String> getServiciosEspeciales() {
        return new ArrayList<>(serviciosEspeciales);
    }

    @Override
    public void mostrarInformacionAdicional() {
        System.out.println("Tipo de aeronave: " + tipoAeronave);
        System.out.print("Servicios especiales: ");
        if (serviciosEspeciales.isEmpty()) {
            System.out.println("Ninguno");
        } else {
            System.out.println(String.join(", ", serviciosEspeciales));
        }
    }
}