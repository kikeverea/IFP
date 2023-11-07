package ifp.kikeverea;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Caballo implements Runnable {

    private final String nombre;
    private int segundos;
    private int segundosTotales;
    private final AvatarCaballo avatar;

    public Caballo(String nombre) {
        this.nombre = nombre;
        this.segundos = ThreadLocalRandom.current().nextInt(1, 11); // aleatorio entre 1 y 10
        this.avatar = new AvatarCaballo(nombre);
    }

    public AvatarCaballo avatar() {
        return avatar;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean masRapidoQue(Caballo caballo) {
        return segundos <= caballo.segundos;
    }

    public boolean haTerminadoLaCarrera(int duracionCarrera) {
        return segundosTotales >= duracionCarrera;
    }

    public int getSegundosTotales() {
        return segundosTotales;
    }

    public void avanzar(int segundos) {
        this.segundosTotales += segundos;
        this.segundos = segundos;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(segundos * 1000L); // suspende este hilo
        }
        catch (InterruptedException e) {
            System.err.println("El programa ha sido interrumpido");
        }
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Caballo caballo = (Caballo) o;
        return nombre.equals(caballo.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}
