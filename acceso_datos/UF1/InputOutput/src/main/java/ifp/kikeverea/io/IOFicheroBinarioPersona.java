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
    public Collection<Persona> leerContenido(File fichero, FiltroPersona filtro) throws IOException {
        return leerContenido(fichero)
                .stream()
                .filter(persona -> extraerAtributo(persona, filtro.atributo()).equals(filtro.valor()))
                .collect(Collectors.toList());
    }

    private Object extraerAtributo(Persona persona, String atributo) {
        switch (atributo) {
            case "Nombre" :
                return persona.getNombre();
            case "Apellido" :
                return persona.getApellido();
            case "Ciudad" :
                return persona.getCiudad();
            case "Nacionalidad" :
                return persona.getNacionalidad();
            case "Edad" :
                return persona.getEdad();
            default:
                throw new IllegalArgumentException("El filtro no contiene nungún atributo de la clase Persona");
        }
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
