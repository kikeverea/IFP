package ifp.kikeverea.io;

import ifp.kikeverea.persona.Persona;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IOFicheroBinarioPersonaTest {

    private static final List<Persona> PERSONAS = List.of(
            new Persona("Aquiles", "Brinco", "Filadelfia", "Estados Unidos", 59),
            new Persona("Elsa", "Pato", "Zapata", "España", 34),
            new Persona("Susana", "Oria", "California", "Mexico", 25),
            new Persona("Elsa", "Pito", "Zapata", "España", 16),
            new Persona("Elmer", "Cado", "Marrakech", "Marruecos", 29)
    );

    private static final String RUTA_BASE = "./src/test/java/ifp/kikeverea/io/";
    private static final String RUTA_FICHERO = RUTA_BASE + "prueba_fichero.txt";

    private IOFichero<Persona> io;
    private File fichero;

    @BeforeEach
    void init() {
        io = new IOFicheroBinarioPersona();
        fichero = new File(RUTA_FICHERO);
    }

    @AfterEach
    void close() {
        if (!fichero.delete())
            throw new IllegalStateException("EL FICHERO DE PRUEBA NO HA SIDO ELIMINADO. " +
                    "Eliminar manualmente; no hacerlo puede alterar resultados de futuras pruebas");
    }

    @Test
    void escribeContenidoEnUnFichero() throws Exception {
        io.escribirEnFichero(fichero, PERSONAS);
        Assertions.assertEquals(PERSONAS, io.leerContenido(fichero));
    }

    @Test
    void escribeContenidoEnUnFicheroEliminaElContenidoAnterior() throws Exception {
        io.escribirEnFichero(fichero, List.of(PERSONAS.get(0)));
        io.escribirEnFichero(fichero, PERSONAS);

        Assertions.assertEquals(PERSONAS, io.leerContenido(fichero));
    }

    @Test
    void leeContenidoRespetandoElFiltroDeAtributos() throws Exception {
        File fichero = new File(RUTA_FICHERO);
        io.escribirEnFichero(fichero, new ArrayList<>(PERSONAS));

        List<Persona> expected = List.of(PERSONAS.get(1), PERSONAS.get(3));

        Assertions.assertEquals(expected, io.leerContenido(fichero, new FiltroNombrePersona(PERSONAS.get(1).getNombre())));
    }
}
