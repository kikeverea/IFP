package ifp.kikeverea;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Carrera {

    private static final int AVANCE_MAXIMO = 7;
    private static final int AVANCE_MINIMO = 1;

    private final Caballos caballos;
    private final List<Caballo> podio;
    private final ThreadLocalRandom random;
    private final int duracionCarrera;

    public Carrera(List<Caballo> caballos, int duracion) {
        this.caballos = new Caballos(caballos);
        this.podio = new ArrayList<>();
        this.random = ThreadLocalRandom.current();
        this.duracionCarrera = duracion;
    }

    public Caballos caballos() {
        return caballos;
    }

    public List<Caballo> podio() {
        return podio;
    }

    public int caballosEnCarrera() {
        return caballos.count();
    }

    public int caballosEnPodio() {
        return podio.size();
    }

    public void avanzarCaballos() throws InterruptedException {
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

        actualizarPodio();
    }

    public void actualizarPodio() {
        // determina los caballos que han cruzado la meta en esta vuelta, en el orden en que han cruzado
        List<Caballo> hanCruzadoLaMeta = caballos
                .lista()
                .stream()
                .filter(caballo -> caballo.haTerminadoLaCarrera(duracionCarrera))
                .sorted(Comparator.comparingInt(caballo -> (duracionCarrera - caballo.getSegundosTotales())))
                .toList();

        for (Caballo caballo : hanCruzadoLaMeta)
            if (!podio.contains(caballo))
                podio.add(caballo);
    }
}
