package ifp.kikeverea.main.programas;

import java.util.Arrays;

public enum AccionEscritura {
    SOBREESCIBIR_FICHERO(1, "Sobreescribir fichero", "Datos escritos al fichero"),
    ANADIR_A_FICHERO(2, "Añadir contenido al final", "Datos añadidos al final del fichero"),
    CREAR_FICHERO(3, "Crear fichero", "Fichero creado correctamente"),
    CANCELAR(0, "Cancelar", "Programa finalizado");

    private final int numero;
    private final String nombre;
    private final String mensajeFinal;

    AccionEscritura(int numero, String nombre, String mensajeFinal) {
        this.numero = numero;
        this.nombre = nombre;
        this.mensajeFinal = mensajeFinal;
    }


    public int numero() {
        return numero;
    }

    public String nombre() {
        return nombre;
    }

    public String mensajeFinal() {
        return mensajeFinal;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    static AccionEscritura determinarAccion(int numero) {
        return Arrays.stream(AccionEscritura.values())
                .filter(accionEscritura -> accionEscritura.numero == numero)
                .findFirst()
                .get();
    }
}
