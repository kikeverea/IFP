package ifp.kikeverea;

import java.util.*;
import java.util.stream.Collectors;

public class MainMejorada {

    private static final int DURACION_CARRERA = 20;
    private static final int LONGITUD_BLOQUE = 3;
    private static final int LONGITUD_BANNER = 30;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Caballo> caballos = solicitarCaballos(scanner);
        Carrera carrera = new Carrera(caballos, DURACION_CARRERA);

        try {
            conteoInicioCarrera();
            do {
                // inicia la ejecución del mensaje dinámico
                Thread mensajeAvance = new Thread(mensajeAvance());
                mensajeAvance.start();

                carrera.avanzarCaballos();

                // interrumpe la ejecución del mensaje dinámico
                mensajeAvance.interrupt();

                imprimirResultadoDeLaVuelta(carrera.caballos(), carrera.podio());
            }
            while (carrera.caballosEnPodio() < carrera.caballosEnCarrera());
        }
        catch (InterruptedException e) {
            System.err.println("La carrera ha sido interrumpida. No se ha podido determinar un ganador.");
        }

        imprimirPodio(carrera.podio());
    }

    private static List<Caballo> solicitarCaballos(Scanner scanner) {
        List<Caballo> caballos = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            System.out.print("Nombre del caballo " + (i+1) + ": ");
            caballos.add(new Caballo(scanner.nextLine()));
        }

        return caballos;
    }

    private static void conteoInicioCarrera() throws InterruptedException {
        System.out.print("3..");
        Thread.sleep(1000);
        System.out.print("2..");
        Thread.sleep(1000);
        System.out.println("1!");
        Thread.sleep(1000);
        imprimirBanner("Comienza!!");
    }

    private static void imprimirResultadoDeLaVuelta(Caballos caballos, List<Caballo> podio) {
        System.out.println();

        List<Caballo> enCarrera = caballos.lista();
        int longitudMaximaCaballos = caballos.longitudMaximaAvatares();

        imprimirLimiteCarril(longitudMaximaCaballos);

        for (Caballo caballo : enCarrera) {
            if (podio.contains(caballo))
                imprimirCarrilVacio(caballo, podio, longitudMaximaCaballos);

            else
                imprimirCaballoEnCarril(caballo, longitudMaximaCaballos);

            imprimirLimiteCarril(longitudMaximaCaballos);
        }
    }

    private static void imprimirLimiteCarril(int longitudMaximaCaballos) {
        System.out.print("___".repeat(DURACION_CARRERA));
        System.out.println("_".repeat(longitudMaximaCaballos));
    }

    private static void imprimirCarrilVacio(Caballo caballo, List<Caballo> podio, int longitudMaximaCaballos) {
        AvatarCaballo avatar = caballo.avatar();
        int totalRecorrido = DURACION_CARRERA + longitudMaximaCaballos / LONGITUD_BLOQUE;
        int padding = longitudMaximaCaballos - LONGITUD_BLOQUE * (longitudMaximaCaballos / LONGITUD_BLOQUE);
        int paddingTrofeo = longitudMaximaCaballos - avatar.longitud();
        int posicion = podio.indexOf(caballo) + 1;

        System.out.println(pista(totalRecorrido) + padding(padding) + "|   " + avatar.crin(0) + padding(paddingTrofeo) + Trofeo.superior(posicion));
        System.out.println(track(totalRecorrido) + padding(padding) + "|   " + avatar.morro(0) + padding(paddingTrofeo) + Trofeo.medio(posicion));
        System.out.println(pista(totalRecorrido) + padding(padding) + "|   " + avatar.nombre(0) + padding(paddingTrofeo) + Trofeo.bajo(posicion));
    }

    private static void imprimirCaballoEnCarril(Caballo caballo, int longitudMaximaCaballos) {
        AvatarCaballo avatar = caballo.avatar();
        int totalRecorrido = caballo.getSegundosTotales();
        int paddingAvatar = (longitudMaximaCaballos - avatar.longitud()) / 2;
        int totalPorRecorrer = DURACION_CARRERA - caballo.getSegundosTotales();

        System.out.println(pista(totalRecorrido) + avatar.crin(paddingAvatar)   + pista(totalPorRecorrer) + "|");
        System.out.println(track(totalRecorrido) + avatar.morro(paddingAvatar)  + pista(totalPorRecorrer) + "|");
        System.out.println(pista(totalRecorrido) + avatar.nombre(paddingAvatar) + pista(totalPorRecorrer) + "|");
    }

    private static String pista(int longitud) {
        return "   ".repeat(longitud);
    }

    private static String track(int longitud) {
        return " - ".repeat(longitud);
    }

    private static String padding(int cantidad) {
        return " ".repeat(cantidad);
    }

    private static void imprimirPodio(List<Caballo> podio) {
        String representacionPodio = podio
                .stream()
                .map(MainMejorada::nombreEnPodio)
                .collect(Collectors.joining("\n"));

        System.out.println("\nLa carrera ha terminado!!\n");
        imprimirBanner("Podio");
        System.out.println(representacionPodio);
        System.out.println("-----------------------------");
    }

    private static String nombreEnPodio(Caballo caballo) {
        String nombre = caballo.getNombre();
        if (nombre.length() % 2 != 0)
            nombre+=" ";

        int padding = (28 - nombre.length()) / 2;
        return "|" + " ".repeat(padding) + nombre + " ".repeat(padding) + "|";
    }

    @SuppressWarnings({"InfiniteLoopStatement", "BusyWait"})
    private static Runnable mensajeAvance() {
        return ()-> {
            try {
                System.out.print("Avanzan los caballos");
                while (true) {
                    System.out.print(".");
                    Thread.sleep(1000);
                }
            }
            catch (InterruptedException ignored) {}
        };
    }

    private static void imprimirBanner(String mensaje) {
        int padding = (LONGITUD_BANNER - mensaje.length()) / 2;
        System.out.println("-".repeat(LONGITUD_BANNER));
        System.out.println(" ".repeat(padding) + mensaje + " ".repeat(padding));
        System.out.println("-".repeat(LONGITUD_BANNER));
    }
}
