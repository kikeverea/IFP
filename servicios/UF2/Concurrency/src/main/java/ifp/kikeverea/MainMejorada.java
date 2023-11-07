package ifp.kikeverea;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class CarreraMejorada {

    private static final int DURACION_CARRERA = 20;
    private static final int LONGITUD_BLOQUE = 3;
    private static final int AVANCE_MAXIMO = 7;
    private static final int AVANCE_MINIMO = 1;

    private static final String TROFEO_SUPERIOR = " _______ ";
    private static final String TROFEO_MEDIO = "(\\__1__/)";
    private static final String TROFEO_BAJO = "  _|_|_  ";

    private final Caballos caballos;
    private final List<Caballo> podio;

    private final ThreadLocalRandom random;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nombre del caballo 1: ");
        String nombre1 = scanner.nextLine();

        System.out.print("Nombre del caballo 2: ");
        String nombre2 = scanner.nextLine();

        System.out.print("Nombre del caballo 3: ");
        String nombre3 = scanner.nextLine();

        CarreraMejorada carrera = new CarreraMejorada(
                new Caballo(nombre1), new Caballo(nombre2), new Caballo(nombre3));

        try {
            inicioCarrera();
            do {
                carrera.avanzarCaballos();
                System.out.println();
                imprimirCarrera(carrera.caballos, carrera.podio);
            }
            while (carrera.podio.size() < carrera.caballos.count());
        }
        catch (InterruptedException e) {
            System.err.println("La carrera ha sido interrumpida. No se ha podido determinar un ganador.");
        }

        imprimirPodio(carrera.podio);
    }

    public CarreraMejorada(Caballo... caballos) {
        this.caballos = new Caballos(caballos);
        this.podio = new ArrayList<>();
        this.random = ThreadLocalRandom.current();
    }

    private static void inicioCarrera() throws InterruptedException {
        System.out.print("3..");
        Thread.sleep(1000);
        System.out.print("2..");
        Thread.sleep(1000);
        System.out.println("1!");
        Thread.sleep(1000);
        imprimirBanner("Comienza!!");
    }

    private static void imprimirBanner(String mensaje) {
        int padding = (30 - mensaje.length()) / 2;
        System.out.println("-".repeat(30));
        System.out.println(" ".repeat(padding) + mensaje + " ".repeat(padding));
        System.out.println("-".repeat(30));
    }

    private static void imprimirCarrera(Caballos caballos, List<Caballo> podio) {
        List<Caballo> enCarrera = caballos.lista();
        int longitudMaximaCaballos = caballos.longitudMaximaCaballos();

        imprimirLimiteCarril(longitudMaximaCaballos);

        for (Caballo caballo : enCarrera) {
            AvatarCaballo avatar = caballo.avatar();

            if (podio.contains(caballo)) {
                int totalRecorrido = DURACION_CARRERA + longitudMaximaCaballos / LONGITUD_BLOQUE;
                int padding = LONGITUD_BLOQUE - longitudMaximaCaballos / LONGITUD_BLOQUE;
                int posicion = podio.indexOf(caballo) + 1;

                System.out.println(pista(totalRecorrido) + padding(padding) + "|   " + avatar.crin(0) + posicionSuperior(posicion));
                System.out.println(track(totalRecorrido) + padding(padding) + "|   " + avatar.morro(0) + posicionMedio(posicion));
                System.out.println(pista(totalRecorrido) + padding(padding) + "|   " + avatar.nombre(0) + posicionBajo(posicion));
            }
            else {
                int totalRecorrido = caballo.getSegundosTotales();
                int paddingAvatar = (longitudMaximaCaballos - avatar.longitud()) / 2;
                int totalPorRecorrer = DURACION_CARRERA - caballo.getSegundosTotales();

                System.out.println(pista(totalRecorrido) + avatar.crin(paddingAvatar)   + pista(totalPorRecorrer) + "|");
                System.out.println(track(totalRecorrido) + avatar.morro(paddingAvatar)  + pista(totalPorRecorrer) + "|");
                System.out.println(pista(totalRecorrido) + avatar.nombre(paddingAvatar) + pista(totalPorRecorrer) + "|");
            }
            imprimirLimiteCarril(longitudMaximaCaballos);
        }
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

    private static String posicionSuperior(int posicion) {
        String padding = "   ";
        return posicion == 1 ? padding + TROFEO_SUPERIOR : "";
    }

    private static String posicionMedio(int posicion) {
        String padding = "   ";
        return posicion == 1 ? padding + TROFEO_MEDIO : padding + "(  "+posicion+"  )";
    }

    private static String posicionBajo(int posicion) {
        String padding = "   ";
        return posicion == 1 ? padding + TROFEO_BAJO : "";
    }

    private static void imprimirLimiteCarril(int longitudMaximaCaballos) {
        System.out.print("___".repeat(DURACION_CARRERA));
        System.out.println("_".repeat(longitudMaximaCaballos));
    }

    private static void imprimirPodio(List<Caballo> podio) {
        String representacionPodio = podio
                .stream()
                .map(CarreraMejorada::nombreEnPodio)
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

    private void avanzarCaballos() throws InterruptedException {
        Thread mensajeAvance = new Thread(mensajeAvance());
        mensajeAvance.start();

        List<Thread> hilos = new ArrayList<>();

        // crea y ejecuta todos los hilos
        for (Caballo caballo : caballos.lista()) {

            if (podio.contains(caballo))
                continue;

            caballo.avanzar(random.nextInt(AVANCE_MINIMO, AVANCE_MAXIMO + 1));
            Thread hilo = new Thread(caballo);
            hilos.add(hilo);
            hilo.start();
        }

        // suspende este hilo hasta que los todos los hilos
        // terminan su ejecución y mueren
        for (Thread hilo : hilos)
            hilo.join();

        mensajeAvance.interrupt();
        actualizarPodio();
    }

    private Runnable mensajeAvance() {
        return ()-> {
            try {
                System.out.print("Avanzan los caballos");
                while (true) {
                    System.out.print(".");
                    Thread.sleep(1000);
                }
            }
            catch (InterruptedException e) {}
        };
    }

    private void actualizarPodio() {
        // determina los caballos que han cruzado la meta en esta vuelta, en el orden en que han cruzado
        List<Caballo> hanCruzadoLaMeta = caballos
                .lista()
                .stream()
                .filter(caballo -> caballo.haTerminadoLaCarrera(DURACION_CARRERA))
                .sorted(Comparator.comparingInt(caballo -> (DURACION_CARRERA - caballo.getSegundosTotales())))
                .toList();

        for (Caballo caballo : hanCruzadoLaMeta)
            if (!podio.contains(caballo))
                podio.add(caballo);
    }
}
