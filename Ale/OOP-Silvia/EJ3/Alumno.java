public class Alumno {
    private String nombre;

    public static void main(String[] args) {
        Alumno alumno1 = new Alumno();
        Alumno alumno2 = new Alumno();
        Alumno alumno3 = new Alumno();
        alumno1.nombre = "Juan";
        alumno2.nombre = "Maria";
        alumno3.nombre = "Pedro";
        System.out.println(alumno1.nombre);
        System.out.println(alumno2.nombre);
        System.out.println(alumno3.nombre);
    }
}