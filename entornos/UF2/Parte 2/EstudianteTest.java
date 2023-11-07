import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Prueba Unitaria de Caja Blanca de la clase EstudianteTest.
 *
 * @author  (Juan Pedro)
 * @version (1.0)
 */
public class EstudianteTest
{
    private Estudiante Juan;
    private Estudiante Ana;

    /**
     * Default constructor for test class EstudianteTest
     */
    public EstudianteTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        Juan = new Estudiante(0, 0, 0);
        Ana = new Estudiante(0, 0, 0);
        Juan.estaAprobado();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }


    @Test
    public void presentarSolo2UF()
    {
        Ana.setNotaUF1(6);
        Ana.setNotaUF2(4);
        assertEquals(false, Ana.getPresentadaUF3());
        assertEquals(false, Ana.todoEntregado());
    }

    @Test
    public void registrarCalificacionesJuan()
    {
        Juan.setNotaUF1(7);
        Juan.setNotaUF2(8);
        Juan.setNotaUF3(5);
        assertEquals(true, Juan.todoEntregado());
        assertEquals(true, Juan.estaAprobado());
        assertEquals(6.6, Juan.getNotaFinal(), 0.05);
    }

    @Test
    public void copiarCalificaciones()
    {
        Juan.setNotaUF1(7);
        Juan.setNotaUF2(8);
        Juan.setNotaUF3(5);
        Ana.setNotaUF1(Juan.getNotaUF1());
        Ana.setNotaUF2(Juan.getNotaUF2());
        Ana.setNotaUF3(Juan.getNotaUF3());
        assertEquals(true, Ana.equals(Juan));
        assertEquals(Juan.getNotaFinal(), Ana.getNotaFinal(), 0.1);
    }
}






