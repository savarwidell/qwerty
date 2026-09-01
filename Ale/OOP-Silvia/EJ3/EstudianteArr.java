public class EstudianteArr {
    String nombre;
    public static void main(String[] args) {
        EstudianteArr[] alumnos = new EstudianteArr[3];

        for (int i= 0; i < alumnos.length; i++) {
            alumnos[i] = new EstudianteArr();
            alumnos[i].nombre = "Alumno " + (i + 1);
            System.out.println(alumnos[i].nombre);
        }

    }
}