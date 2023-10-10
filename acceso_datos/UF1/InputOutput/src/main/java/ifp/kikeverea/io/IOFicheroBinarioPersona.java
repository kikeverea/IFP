package ifp.kikeverea.io;

import ifp.kikeverea.persona.Persona;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class IOFicheroBinarioPersona implements IOFichero<Persona> {

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Persona> leerContenido(File fichero) throws IOException {
        try (FileInputStream fis = new FileInputStream(fichero);
             BufferedInputStream bs = new BufferedInputStream(fis);
             ObjectInputStream reader = new ObjectInputStream(bs))
        {
            return (ArrayList<Persona>) reader.readObject();
        }
        catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("El fichero no contiene objetos tipo ArrayList<Persona>");
        }
    }

    @Override
    public Collection<Persona> leerContenido(File fichero, FiltroLectura filtro) throws IOException {
        return leerContenido(fichero)
                .stream()
                .filter(filtro::pasaFiltro)
                .collect(Collectors.toList());
    }

    @Override
    public void escribirEnFichero(File fichero, Collection<Persona> personas) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fichero);
             BufferedOutputStream bs = new BufferedOutputStream(fos);
             ObjectOutputStream writer = new ObjectOutputStream(bs))
        {
            writer.writeObject(personas);
        }
    }

    @Override
    public void escribirEnFichero(File fichero, Collection<Persona> personas, boolean anadir) throws IOException {
        // esta clase no da soporte a 'append'. Convoca la versión de este método sin append
        escribirEnFichero(fichero, personas);
    }
}
