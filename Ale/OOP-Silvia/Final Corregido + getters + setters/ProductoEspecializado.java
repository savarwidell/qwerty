public class ProductoEspecializado {

    private String descripcion;
    private String paisDeOrigen;
    private boolean organico;

    public ProductoEspecializado(String descripcion, String paisDeOrigen, boolean organico) {
        this.descripcion = descripcion;
        this.paisDeOrigen = paisDeOrigen;
        this.organico = organico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPaisDeOrigen() {
        return paisDeOrigen;
    }

    public void setPaisDeOrigen(String paisDeOrigen) {
        this.paisDeOrigen = paisDeOrigen;
    }

    public boolean isOrganico() {
        return organico;
    }

    public void setOrganico(boolean organico) {
        this.organico = organico;
    }
}