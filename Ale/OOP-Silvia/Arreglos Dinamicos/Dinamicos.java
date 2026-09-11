import java.util.ArrayList;

public class Dinamicos {
    public static void main(String[] args) {
        float suma = 0; float suma_dec = 0;

        ArrayList<Integer> cantidades = new ArrayList<>();
        ArrayList<Float> cantidades_dec = new ArrayList<>();
        ArrayList<String> descripcion = new ArrayList<>();

        System.out.println("Descripcions guardadas:");

        descripcion.add("Mouse");
        descripcion.add("Teclado");
        descripcion.add("Laptop");
        descripcion.add("USB");

        System.out.println("Cantidades enteras guardadas:");    

        cantidades.add(60);
        cantidades.add(30);
        cantidades.add(20);

        System.out.println("Cantidades decimales guardadas:");
        cantidades_dec.add(10.5f);
        cantidades_dec.add(20.5f);
        cantidades_dec.add(30.5f);

        for (int i = 0; i < cantidades.size(); i++) {
            System.out.println("descripcion: " + descripcion.get(i) + " cantidad: " + cantidades.get(i));
            System.out.println("cantidades decimales: " + cantidades_dec.get(i) + " cantidad decimal: " + cantidades_dec.get(i));
            System.out.println("cantidades: " + cantidades.get(i) + " cantidad decimal: " + cantidades_dec.get(i));
            suma
        }

    }
}
