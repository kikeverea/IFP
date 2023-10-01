package ifp.kikeverea.main;

import ifp.kikeverea.io.FicheroPersonas;
import ifp.kikeverea.io.IOFicheroTextoPersona;
import ifp.kikeverea.util.InputUsuario;

public class ProgramaProveerdorFicheros {

    public static FicheroPersonas ejecutar(InputUsuario input) {
        FicheroPersonas fichero = new FicheroPersonas(new IOFicheroTextoPersona());
        establecerRutaDelFichero(input, fichero);

        return fichero;
    }

    private static void establecerRutaDelFichero(InputUsuario input, FicheroPersonas fichero) {
        String ruta;
        do {
            ruta = input.solicitarTexto("Ruta y/o nombre del fichero: ");
        }
        while (!fichero.establecerRuta(ruta));
    }
}
