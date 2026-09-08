public class Tripulante extends Persona {
    private String rol;
    private int anosExperiencia;

    public Tripulante(String nombre, String identificacion, String rol, int anosExperiencia) {
        super(nombre, identificacion);
        this.rol = rol;
        this.anosExperiencia = anosExperiencia;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getAnosExperiencia() {
        return anosExperiencia;
    }

    public void setAnosExperiencia(int anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("Tripulante: " + nombre + " (ID: " + identificacion + ")");
        System.out.println("  Rol: " + rol);
        System.out.println("  Años de experiencia: " + anosExperiencia);
    }
}