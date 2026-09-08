public class VueloComercial extends Vuelo {
    private String claseServicio;

    public VueloComercial(String numeroVuelo, String origen, String destino, 
                         String fecha, String horaSalida, int capacidadMaxima, 
                         String claseServicio) {
        super(numeroVuelo, origen, destino, fecha, horaSalida, capacidadMaxima);
        this.claseServicio = claseServicio;
    }

    public String getClaseServicio() {
        return claseServicio;
    }

    public void setClaseServicio(String claseServicio) {
        this.claseServicio = claseServicio;
    }

    @Override
    public void mostrarInformacionAdicional() {
        System.out.println("Clase de servicio: " + claseServicio);
    }
}