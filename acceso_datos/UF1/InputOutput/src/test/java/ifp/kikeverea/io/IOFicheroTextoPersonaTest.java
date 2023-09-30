package ifp.kikeverea.io;

import ifp.kikeverea.persona.Persona;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IOFicheroTextoPersonaTest {

    private static final List<Persona> PERSONAS = List.of(
        new Persona("Aquiles", "Brinco", "Filadelfia", "Estados Unidos", 59),
        new Persona("Elsa", "Pato", "Zapata", "España", 34),
        new Persona("Susana", "Oria", "California", "Mexico", 25),
        new Persona("Elmer", "Cado", "Marrakech", "Marruecos", 29)
    );

    private static final String RUTA_BASE = "./src/test/java/ifp/kikeverea/io/";
    private static final String RUTA_FICHERO = RUTA_BASE + "prueba_fichero.txt";
    private static final String RUTA_FICHERO_DINAMICO = RUTA_BASE + "prueba_fichero_dinamica.txt";

    private IOFichero<Persona> io;

    @BeforeEach
    void init() {
        io = new IOFicheroTextoPersona();
    }

    @Test
    void escribeContenidoEnUnFichero() throws Exception {
        File fichero = new File(RUTA_FICHERO_DINAMICO);
        fichero.deleteOnExit();

        io.escribirEnFichero(fichero, PERSONAS, false);
        Assertions.assertEquals(PERSONAS, io.leerContenido(fichero));
    }

    @Test
    void escribirContenidoEnUnFicheroEliminaElContenidoAnterior() throws Exception {
        File fichero = new File(RUTA_FICHERO_DINAMICO);
        fichero.deleteOnExit();

        io.escribirEnFichero(fichero, List.of(PERSONAS.get(0)), false);
        io.escribirEnFichero(fichero, PERSONAS, false);

        Assertions.assertEquals(PERSONAS, io.leerContenido(fichero));
    }

    @Test
    void anadirContenidoEnUnFicheroNoAlteraElContenidoExistente() throws Exception {
        Persona personaAnadida = PERSONAS.get(0);
        File fichero = new File(RUTA_FICHERO_DINAMICO);

        io.escribirEnFichero(fichero, PERSONAS);
        io.escribirEnFichero(fichero, List.of(personaAnadida), true);

        List<Persona> expected = new ArrayList<>(PERSONAS);
        expected.add(personaAnadida);

        Assertions.assertEquals(expected, io.leerContenido(fichero));
    }
}