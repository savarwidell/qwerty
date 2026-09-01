public class Estudiante {
    String nombre;
    public static void main(String[] args) {
        Estudiante[] alumnos = new Estudiante[3];
        alumnos[0] = new Estudiante();
        alumnos[1] = new Estudiante();
        alumnos[2] = new Estudiante();

        alumnos[0].nombre = "Juan";
        alumnos[1].nombre = "Maria";
        alumnos[2].nombre = "Pedro";

        System.out.println(alumnos[0].nombre);
        System.out.println(alumnos[1].nombre);
        System.out.println(alumnos[2].nombre);

    }
}
