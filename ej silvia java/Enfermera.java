class Enfermera extends Personal {
    @Override
    double calcularPago(double sueldo) {
        // Las enfermeras reciben un bono adicional de 2000
        return sueldo + 2000;
    }

    // Sobrecarga adicional específica para enfermeras
    double calcularPago(double sueldo, int turnosNoche) {
        return sueldo + 2000 + (turnosNoche * 300);
    }
}