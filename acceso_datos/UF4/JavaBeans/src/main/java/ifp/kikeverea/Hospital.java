package ifp.kikeverea;

import ifp.kikeverea.util.InputUsuario;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Scanner;

public class Hospital {

    private static final DateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");

    public static void main(String[] args) {
        InputUsuario input = new InputUsuario(new Scanner(System.in));

        Paciente paciente = new Paciente("Joe", "Pris", 30, "630009", 10, 44444);

        GeneraAnalisis analisis = new GeneraAnalisis(1, Date.from(Instant.now()));
        analisis.setPaciente(paciente);

        while (true) {
            System.out.println(
                    "Análisis " + analisis.getNumAnalisis() +
                    " del paciente " + paciente.getNombre() + " " + paciente.getApellido() + " (" +
                    formatoFecha.format(analisis.getFecha()) + ")");

            System.out.println("Valores actuales");
            System.out.println("Hierro: " + paciente.getUltimoHierro());
            System.out.println("Úrea: " + paciente.getUltimaUrea());

            int valorHierro = input.solicitarEntero("Introducir valor de hierro: ");
            paciente.setUltimoHierro(valorHierro);

            int valorUrea = input.solicitarEntero("Introducir valor de úrea': ");
            paciente.setUltimaUrea(valorUrea);

            if (analisis.getAnalisisPendiente() == 0) {
                // no hay necesidad de un nuevo analisis, terminar programa
                System.out.println("*******************");
                System.out.println("Valores correctos");
                System.out.println("*******************");
                break;
            }
            else {
                // un nuevo análisis es necesario
                System.out.println("*****************************************************");
                System.out.println("Valores de hierro inadecuados.");
                System.out.println("Diferencia mayor a 10. Generando nuevo análisis de seguimiento");
                System.out.println("*****************************************************");

                analisis = analisis.generarNuevoAnalisis(
                        analisis,
                        Date.from(Instant.now().plus(7, ChronoUnit.DAYS)));
            }
        }
    }
}