public class Principal {
    public static void main(String[] args) {
        // Polimorfismo: variables de tipo Personal referenciando a subclases
        Personal doctor = new Doctor();
        Personal enfermera = new Enfermera();
        Personal administrativo = new Personal();

        System.out.println("=== PAGOS DE PERSONAL ===");
        System.out.println("Doctor (sueldo base 15000): S/." + doctor.calcularPago(15000));
        System.out.println("Enfermera (sueldo base 10000): S/." + enfermera.calcularPago(10000));
        System.out.println("Administrativo (sueldo 10000 + bono 1000): S/." + administrativo.calcularPago(10000, 1000));

        // Uso de métodos específicos (requiere casteo)
        System.out.println("\n=== CON BONOS ADICIONALES ===");
        Doctor doc = (Doctor) doctor;
        System.out.println("Doctor (sueldo 15000 + 5 horas extra): S/." + doc.calcularPago(15000, 5));

        Enfermera enf = (Enfermera) enfermera;
        System.out.println("Enfermera (sueldo 10000 + 3 turnos noche): S/." + enf.calcularPago(10000, 3));
    }
}