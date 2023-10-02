package ifp.kikeverea.io;

import ifp.kikeverea.persona.Persona;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOFicheroTextoPersonaTest {

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
        io = new IOFicheroTextoPersona();
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
        io.escribirEnFichero(fichero, PERSONAS, false);
        Assertions.assertEquals(PERSONAS, io.leerContenido(fichero));
    }

    @Test
    void escribeContenidoLuegoLeeElMismoContenido() throws IOException {
        File fichero = new File(RUTA_FICHERO);
        io.escribirEnFichero(fichero, PERSONAS, true);

        Assertions.assertEquals(PERSONAS, io.leerContenido(fichero));
    }

    @Test
    void leeContenidoRespetandoElFiltroDeAtributos() throws Exception {
        File fichero = new File(RUTA_FICHERO);
        io.escribirEnFichero(fichero, PERSONAS);

        List<Persona> expected = List.of(PERSONAS.get(1), PERSONAS.get(3));

        Assertions.assertEquals(expected, io.leerContenido(fichero, new FiltroLectura("Nombre", "Elsa")));
    }

    @Test
    void escribirContenidoEnUnFicheroEliminaElContenidoAnterior() throws Exception {
        File fichero = new File(RUTA_FICHERO);

        io.escribirEnFichero(fichero, List.of(PERSONAS.get(0)), false);
        io.escribirEnFichero(fichero, PERSONAS, false);

        Assertions.assertEquals(PERSONAS, io.leerContenido(fichero));
    }

    @Test
    void anadirContenidoEnUnFicheroNoAlteraElContenidoExistente() throws Exception {
        Persona personaAnadida = PERSONAS.get(0);
        File fichero = new File(RUTA_FICHERO);

        io.escribirEnFichero(fichero, PERSONAS);
        io.escribirEnFichero(fichero, List.of(personaAnadida), true);

        List<Persona> expected = new ArrayList<>(PERSONAS);
        expected.add(personaAnadida);

        Assertions.assertEquals(expected, io.leerContenido(fichero));
    }

    @Test
    void anadirContenidoEnUnFicheroVacionNoAlteraElContenidoQueSeAnade() throws Exception {
        File fichero = new File(RUTA_FICHERO);
        io.escribirEnFichero(fichero, PERSONAS, true);

        Assertions.assertEquals(PERSONAS, io.leerContenido(fichero));
    }
}