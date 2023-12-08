package ifp.kikeverea;

import ifp.kikeverea.util.InputUsuario;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Scanner;

public class Hospital {

    public static void main(String[] args) {
        InputUsuario input = new InputUsuario(new Scanner(System.in));

        Paciente paciente = new Paciente("Joe", "Pris", 30, "630009", 10, 44444);

        GeneraAnalisis analisis = new GeneraAnalisis(1, Date.from(Instant.now()));
        analisis.setPaciente(paciente);

        while (true) {
            System.out.println("Análisis " + analisis.getNumAnalisis() +
                    " del paciente " + paciente.getNombre() + " " + paciente.getApellido());

            int valorHierro = input.solicitarEntero("Introducir valor de hierro: ");
            paciente.setUltimoHierro(valorHierro);

            int valorUrea = input.solicitarEntero("Introducir valor de úrea': ");
            paciente.setUltimaUrea(valorUrea);

            // no hay necesidad de un nuevo analisis, terminar programa
            if (analisis.getAnalisisPendiente() == 0) {
                System.out.println("Valores correctos");
                break;
            }

            System.out.println("Valores de hierro inadecuados. Diferencia mayor a 10. " +
                                "Generando nuevo análisis de seguimmiento");

            analisis = analisis.generarNuevoAnalisis(
                    analisis,
                    Date.from(Instant.now().plus(7, ChronoUnit.DAYS)));
        }
    }
}