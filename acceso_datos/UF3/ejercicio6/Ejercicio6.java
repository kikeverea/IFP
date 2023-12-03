package ifp.kikeverea.main;

import ifp.kikeverea.persona.Persona;
import ifp.kikeverea.xml.CreadorXML_DOM;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.time.LocalDate;
import java.time.Month;

public class Ejercicio6 {

    public static void main(String[] args) {
        Persona persona1 =
                Persona.conNombre("Cindy")
                        .appellidos("Nero")
                        .nif("74089868Z")
                        .nacionalidad("España")
                        .fechaNacimiento(LocalDate.of(1992, Month.FEBRUARY, 26))
                        .email("cindynero@gmail.com")
                        .telefono("555 555 555")
                        .direccion("Av. Zumalakarregi 38, 1-4")
                        .provincia("Albacete")
                        .ciudad("Alpera")
                        .codigoPostal("02690")
                        .construir();

        Persona persona2 =
                Persona.conNombre("Elmer")
                        .appellidos("Curio")
                        .nif("24686643F")
                        .nacionalidad("Egipto")
                        .fechaNacimiento(LocalDate.of(1984, Month.OCTOBER, 8))
                        .email("elmercurio@gmail.com")
                        .telefono("888 888 888")
                        .direccion("Ctra. de Siles 56, 2-3")
                        .provincia("Lugo")
                        .ciudad("Quiroga")
                        .codigoPostal("27320")
                        .construir();

        Persona persona3 =
                Persona.conNombre("Esteban")
                        .appellidos("Dido", "Arrimado")
                        .nif("82719642G")
                        .nacionalidad("España")
                        .fechaNacimiento(LocalDate.of(1998, Month.APRIL, 19))
                        .email("elbandido@gmail.com")
                        .telefono("222 222 222")
                        .direccion("C/ Amoladera 80, 4-6")
                        .provincia("Madrid")
                        .ciudad("Somosierra")
                        .codigoPostal("28756")
                        .construir();

        try {
            CreadorXML_DOM adaptadorXML = new CreadorXML_DOM("personas");
            adaptadorXML.escribirContenidoXML("personas.xml", persona1, persona2, persona3);
        }
        catch (ParserConfigurationException e) {
            System.err.println("No se ha podido crear el gestor de archivos XML. Causa: " + e.getMessage());
            e.printStackTrace();
        }
        catch (TransformerException e) {
            System.err.println("No se ha podido escrbir el contenido en el fichero. Causa: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
