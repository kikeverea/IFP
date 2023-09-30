package ifp.kikeverea.persona;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PersonaTest {

    @Test
    void convierteUnaStringConFormatoToStringEnPersona() {
        Persona persona = new Persona("Aquiles", "Brinco", "Filadelfia", "Estados Unidos", 59);
        String personaString = persona.toString();

        Assertions.assertEquals(persona, Persona.fromString(personaString));
    }
}
