import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Aerolinea {
    private List<Vuelo> vuelos;
    private Scanner scanner;

    public Aerolinea() {
        vuelos = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void agregarVuelo(Vuelo vuelo) {
        vuelos.add(vuelo);
        System.out.println("Vuelo " + vuelo.getNumeroVuelo() + " registrado exitosamente");
    }

    public Vuelo buscarVuelo(String numeroVuelo) throws Exception {
        for (Vuelo v : vuelos) {
            if (v.getNumeroVuelo().equalsIgnoreCase(numeroVuelo)) {
                return v;
            }
        }
        throw new Exception("Vuelo " + numeroVuelo + " no encontrado");
    }

    public void mostrarTodosLosVuelos() {
        if (vuelos.isEmpty()) {
            System.out.println("No hay vuelos registrados");
            return;
        }
        for (Vuelo v : vuelos) {
            v.mostrarInformacionCompleta();
        }
    }

    public void menu() {
        int opcion;
        do {
            System.out.println("\n=== SISTEMA DE AEROLÍNEA ===");
            System.out.println("1. Registrar vuelo comercial");
            System.out.println("2. Registrar vuelo ejecutivo");
            System.out.println("3. Registrar pasajero");
            System.out.println("4. Registrar tripulante");
            System.out.println("5. Ver todos los vuelos");
            System.out.println("6. Ver información de un vuelo");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido");
                opcion = 0;
                continue;
            }

            try {
                switch (opcion) {
                    case 1:
                        registrarVueloComercial();
                        break;
                    case 2:
                        registrarVueloEjecutivo();
                        break;
                    case 3:
                        registrarPasajero();
                        break;
                    case 4:
                        registrarTripulante();
                        break;
                    case 5:
                        mostrarTodosLosVuelos();
                        break;
                    case 6:
                        mostrarInformacionVuelo();
                        break;
                    case 7:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 7);
    }

    private void registrarVueloComercial() {
        try {
            System.out.print("Número de vuelo: ");
            String num = scanner.nextLine();
            System.out.print("Origen: ");
            String origen = scanner.nextLine();
            System.out.print("Destino: ");
            String destino = scanner.nextLine();
            System.out.print("Fecha (DD/MM/AAAA): ");
            String fecha = scanner.nextLine();
            System.out.print("Hora de salida (HH:MM): ");
            String hora = scanner.nextLine();
            System.out.print("Capacidad máxima: ");
            int capacidad = Integer.parseInt(scanner.nextLine());
            System.out.print("Clase de servicio (Primera/Ejecutiva/Turista): ");
            String clase = scanner.nextLine();

            VueloComercial vuelo = new VueloComercial(num, origen, destino, fecha, hora, capacidad, clase);
            agregarVuelo(vuelo);
        } catch (NumberFormatException e) {
            System.out.println("Error: La capacidad debe ser un número entero");
        }
    }

    private void registrarVueloEjecutivo() {
        try {
            System.out.print("Número de vuelo: ");
            String num = scanner.nextLine();
            System.out.print("Origen: ");
            String origen = scanner.nextLine();
            System.out.print("Destino: ");
            String destino = scanner.nextLine();
            System.out.print("Fecha (DD/MM/AAAA): ");
            String fecha = scanner.nextLine();
            System.out.print("Hora de salida (HH:MM): ");
            String hora = scanner.nextLine();
            System.out.print("Capacidad máxima: ");
            int capacidad = Integer.parseInt(scanner.nextLine());
            System.out.print("Tipo de aeronave: ");
            String aeronave = scanner.nextLine();

            VueloEjecutivo vuelo = new VueloEjecutivo(num, origen, destino, fecha, hora, capacidad, aeronave);
            
            System.out.print("¿Desea agregar servicios especiales? (s/n): ");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                System.out.print("Ingrese servicios separados por coma: ");
                String servicios = scanner.nextLine();
                for (String servicio : servicios.split(",")) {
                    vuelo.agregarServicioEspecial(servicio.trim());
                }
            }
            
            agregarVuelo(vuelo);
        } catch (NumberFormatException e) {
            System.out.println("Error: La capacidad debe ser un número entero");
        }
    }

    private void registrarPasajero() {
        try {
            System.out.print("Número de vuelo: ");
            String numVuelo = scanner.nextLine();
            Vuelo vuelo = buscarVuelo(numVuelo);

            System.out.print("Nombre del pasajero: ");
            String nombre = scanner.nextLine();
            System.out.print("Número de identificación: ");
            String id = scanner.nextLine();
            System.out.print("Asiento asignado: ");
            String asiento = scanner.nextLine();

            Pasajero pasajero = new Pasajero(nombre, id, asiento);
            
            System.out.print("¿Desea servicios adicionales? (s/n): ");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                System.out.print("Ingrese servicios separados por coma: ");
                String servicios = scanner.nextLine();
                for (String servicio : servicios.split(",")) {
                    pasajero.agregarServicioAdicional(servicio.trim());
                }
            }

            vuelo.agregarPasajero(pasajero);
            System.out.println("Pasajero registrado exitosamente en el asiento " + asiento);
        } catch (Exception e) {
            System.out.println("Error al registrar pasajero: " + e.getMessage());
        }
    }

    private void registrarTripulante() {
        try {
            System.out.print("Número de vuelo: ");
            String numVuelo = scanner.nextLine();
            Vuelo vuelo = buscarVuelo(numVuelo);

            System.out.print("Nombre del tripulante: ");
            String nombre = scanner.nextLine();
            System.out.print("Número de identificación: ");
            String id = scanner.nextLine();
            System.out.print("Rol (Piloto/Copiloto/Azafata/Sobrecargo): ");
            String rol = scanner.nextLine();
            System.out.print("Años de experiencia: ");
            int experiencia = Integer.parseInt(scanner.nextLine());

            Tripulante tripulante = new Tripulante(nombre, id, rol, experiencia);
            vuelo.agregarTripulante(tripulante);
            System.out.println("Tripulante registrado exitosamente");
        } catch (NumberFormatException e) {
            System.out.println("Error: Los años de experiencia deben ser un número entero");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void mostrarInformacionVuelo() {
        try {
            System.out.print("Número de vuelo: ");
            String numVuelo = scanner.nextLine();
            Vuelo vuelo = buscarVuelo(numVuelo);
            vuelo.mostrarInformacionCompleta();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Aerolinea sistema = new Aerolinea();
        sistema.menu();
    }
}