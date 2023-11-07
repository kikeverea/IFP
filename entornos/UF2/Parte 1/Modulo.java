public class Modulo {
    public static void main(String[] args) {
        Estudiante Juan = new Estudiante(0, 0, 0);
        Estudiante Maria = new Estudiante(0, 0, 0);
        Estudiante Ana = new Estudiante(0, 0, 0);

        Juan.setNotaUF1(6.4f);
        Juan.setNotaUF2(8);

        Maria.setNotaUF1(9f);
        Maria.setNotaUF2(8.6f);
        Maria.setNotaUF3(4f);

        Ana.setNotaUF1(5f);
        Ana.setNotaUF2(6.3f);
        Ana.setNotaUF3(3.5f);

        System.out.println("\nCalificaciones iFP\n__________________");
        System.out.println("JUAN: " + Juan);
        System.out.println("MARIA: " + Maria);
        System.out.println("ANA: " + Ana);
    }
}