import java.util.ArrayList;

public class Dinamicos {
    public static void main(String[] args) {
        float suma = 0; float suma_dec = 0;

        ArrayList<Integer> cantidades = new ArrayList<>();
        ArrayList<Float> cantidades_dec = new ArrayList<>();
        ArrayList<String> descripcion = new ArrayList<>();

        descripcion.add("Mouse");
        descripcion.add("Teclado");
        descripcion.add("Laptop");
        descripcion.add("USB");

        cantidades.add(60);
        cantidades.add(30);
        cantidades.add(20);

        cantidades_dec.add(10.5f);
        cantidades_dec.add(20.5f);
        cantidades_dec.add(30.5f);

        System.out.println("Producto\tCantidad\tPrecio");

        for (int i = 0; i < cantidades.size(); i++) {
            System.out.println(descripcion.get(i) + "\t\t" + cantidades.get(i) + "\t\t" + cantidades_dec.get(i));
            suma_dec = suma_dec + cantidades_dec.get(i);
        }

        System.out.println("El monto total de precios es de $" + suma_dec);
    }
}
