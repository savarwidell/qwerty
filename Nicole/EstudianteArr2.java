import java.util.Scanner;

public class EstudianteArr2 {
    String nombre;
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        EstudianteArr2[] alumnos = new EstudianteArr2[3];

        for (int i = 0; i < alumnos.length; i++) {
            alumnos[i] = new EstudianteArr2();
            System.out.print("Ingrese el nombre del alumno " + (i + 1) + ": ");
            alumnos[i].nombre = teclado.nextLine();
        }

        System.out.println("Nombres de los alumnos ingresados:");
        for (int i = 0; i < alumnos.length; i++) {
            System.out.println(alumnos[i].nombre);
        }
        teclado.close();
    }
} 
