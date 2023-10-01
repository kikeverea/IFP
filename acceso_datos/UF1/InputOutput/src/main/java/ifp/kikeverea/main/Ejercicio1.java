package ifp.kikeverea.main;

import ifp.kikeverea.persona.Persona;

public class Ejercicio1 {
    public static void main(String[] args) {
        Persona persona1 = new Persona("Elsa", "Pato", "Zapata", "España", 34);
        Persona persona2 = new Persona("Susana", "Oria", "California", "Mexico", 25);
        Persona persona3 = new Persona("Elmer", "Cado", "Marrakech", "Marruecos", 29);

        System.out.println("Informe:");
        System.out.println("Persona 1: " + persona1);
        System.out.println("Persona 2: " + persona2);
        System.out.println("Persona 3: " + persona3);
    }
}