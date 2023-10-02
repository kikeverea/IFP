package ifp.kikeverea.main;

import ifp.kikeverea.io.FicheroPersonas;
import ifp.kikeverea.io.IOFichero;
import ifp.kikeverea.persona.Persona;
import ifp.kikeverea.util.InputUsuario;

public class ProgramaProveerdorFicheros {

    public static FicheroPersonas solicitarFichero(IOFichero<Persona> io, InputUsuario input) {
        FicheroPersonas fichero = new FicheroPersonas(io);
        establecerRutaDelFichero(input, fichero);

        return fichero;
    }

    public static FicheroPersonas solicitarFicheroExistente(IOFichero<Persona> io, InputUsuario input) {
        do {
            FicheroPersonas fichero = solicitarFichero(io, input);

            if (fichero.existe())
                return fichero;

            else System.out.println("El fichero no existe");
        }
        while (true);
    }

    private static void establecerRutaDelFichero(InputUsuario input, FicheroPersonas fichero) {
        String ruta;
        do {
            ruta = input.solicitarTexto("Ruta y/o nombre del fichero: ");
            ruta = ruta.strip();  // elimina los espacios en blanco al principio y final del String
        }
        while (!fichero.establecerRuta(ruta));
    }
}
