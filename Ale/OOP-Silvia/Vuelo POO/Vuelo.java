import java.util.ArrayList;
import java.util.List;

public abstract class Vuelo {
    protected String numeroVuelo;
    protected String origen;
    protected String destino;
    protected String fecha;
    protected String horaSalida;
    protected int capacidadMaxima;
    protected List<Pasajero> pasajeros;
    protected List<Tripulante> tripulantes;

    public Vuelo(String numeroVuelo, String origen, String destino, String fecha, 
                 String horaSalida, int capacidadMaxima) {
        this.numeroVuelo = numeroVuelo;
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        this.horaSalida = horaSalida;
        this.capacidadMaxima = capacidadMaxima;
        this.pasajeros = new ArrayList<>();
        this.tripulantes = new ArrayList<>();
    }

    public String getNumeroVuelo() {
        return numeroVuelo;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public int getPasajerosRegistrados() {
        return pasajeros.size();
    }

    public int getAsientosDisponibles() {
        return capacidadMaxima - pasajeros.size();
    }

    public List<Pasajero> getPasajeros() {
        return new ArrayList<>(pasajeros);
    }

    public void agregarPasajero(Pasajero pasajero) throws Exception {
        if (pasajeros.size() >= capacidadMaxima) {
            throw new Exception("No hay asientos disponibles en el vuelo " + numeroVuelo);
        }
        
        for (Pasajero p : pasajeros) {
            if (p.getAsiento().equals(pasajero.getAsiento())) {
                throw new Exception("El asiento " + pasajero.getAsiento() + " ya está ocupado");
            }
        }
        
        pasajeros.add(pasajero);
    }

    public void agregarTripulante(Tripulante tripulante) {
        tripulantes.add(tripulante);
    }

    public abstract void mostrarInformacionAdicional();

    public void mostrarInformacionCompleta() {
        System.out.println("=== VUELO " + numeroVuelo + " ===");
        System.out.println("Origen: " + origen);
        System.out.println("Destino: " + destino);
        System.out.println("Fecha: " + fecha);
        System.out.println("Hora de salida: " + horaSalida);
        System.out.println("Capacidad máxima: " + capacidadMaxima);
        System.out.println("Pasajeros registrados: " + getPasajerosRegistrados());
        System.out.println("Asientos disponibles: " + getAsientosDisponibles());
        mostrarInformacionAdicional();
        
        System.out.println("\n--- TRIPULACIÓN ---");
        if (tripulantes.isEmpty()) {
            System.out.println("No hay tripulación registrada");
        } else {
            for (Tripulante t : tripulantes) {
                System.out.println(t.getNombre() + " - " + t.getRol());
            }
        }
        
        System.out.println("\n--- PASAJEROS ---");
        if (pasajeros.isEmpty()) {
            System.out.println("No hay pasajeros registrados");
        } else {
            for (Pasajero p : pasajeros) {
                System.out.println(p.getNombre() + " - Asiento: " + p.getAsiento());
                if (!p.getServiciosAdicionales().isEmpty()) {
                    System.out.println("  Servicios: " + p.getServiciosAdicionales());
                }
            }
        }
        System.out.println();
    }
}