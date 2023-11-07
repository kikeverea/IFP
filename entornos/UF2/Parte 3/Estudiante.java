public class Estudiante {
    private float[] notas;
    private boolean presentaUF1;
    private boolean presentaUF2;
    private boolean presentaUF3;

    // Constructor
    public Estudiante(float notaUF1, float notaUF2, float notaUF3) {
        this.notas = new float[]{notaUF1, notaUF2, notaUF3};
        this.presentaUF1 = false;
        this.presentaUF2 = false;
        this.presentaUF3 = false;
    }

    // Métodos get y set para notaUF1
    public float getNotaUF1() {
        return notas[0];
    }

    public void setNotaUF1(float notaUF1) {
        this.notas[0] = notaUF1;
        this.setPresentaUF1(true);
    }

    // Métodos get y set para notaUF2
    public float getNotaUF2() {
        return notas[1];
    }

    public void setNotaUF2(float notaUF2) {
        this.notas[1] = notaUF2;
        this.setPresentaUF2(true);
    }

    // Métodos get y set para notaUF3
    public float getNotaUF3() {
        return notas[2];
    }

    public void setNotaUF3(float notaUF3) {
        this.notas[2] = notaUF3;
        this.setPresentaUF3(true);
    }

    // Métodos get y set para presentaUF1
    public boolean getPresentadaUF1() {
        return presentaUF1;
    }

    public void setPresentaUF1(boolean presentaUF1) {
        this.presentaUF1 = presentaUF1;
    }

    // Métodos get y set para presentaUF2
    public boolean getPresentadaUF2() {
        return presentaUF2;
    }

    public void setPresentaUF2(boolean presentaUF2) {
        this.presentaUF2 = presentaUF2;
    }

    // Métodos get y set para presentaUF3
    public boolean getPresentadaUF3() {
        return presentaUF3;
    }

    public void setPresentaUF3(boolean presentaUF3) {
        this.presentaUF3 = presentaUF3;
    }

    public float getNotaFinal() {
        return (getNotaUF1() + getNotaUF2() + getNotaUF3()) / 3;
    }

    public boolean todoEntregado() {
        if (presentaUF1 && presentaUF2 && presentaUF3) {
            return true;
        } else {
            return false;
        }
    }

    public boolean estaAprobado() {
        return (todoEntregado() && (getNotaFinal() >= 5));
    }

    public String toString() {
        String mensaje = new String("El/La estudiante ");

        if (todoEntregado()) {

            mensaje += "ha presentado todos los ejercicios, ha ";

            if (estaAprobado())
                mensaje += "aprobado ";
            else
                mensaje += "suspendido ";

            mensaje += "con una nota final de " + getNotaFinal() + " puntos.";
        }
        else
            mensaje += "no ha presentado todos los ejercicios, ha suspendido";

        return mensaje;
    }
}
