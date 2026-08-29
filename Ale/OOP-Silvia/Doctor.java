class Doctor extends Personal {
    @Override
    double calcularPago(double sueldo) {
        // Los doctores reciben un bono adicional de 5000
        return sueldo + 5000;
    }

    // Sobrecarga adicional específica para doctores
    double calcularPago(double sueldo, double horasExtra) {
        return sueldo + 5000 + (horasExtra * 200);
    }
}