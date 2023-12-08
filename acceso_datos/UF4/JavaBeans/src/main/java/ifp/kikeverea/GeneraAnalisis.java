package ifp.kikeverea;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Date;

public class GeneraAnalisis implements Serializable, PropertyChangeListener {

    static final long serialVersionUID = 1L;

    private int numAnalisis;
    private Paciente paciente;
    private Date fecha;
    private int analisisPendiente;

    // constructor para cumplir con especificaiones JavaBean
    public GeneraAnalisis() {}

    public GeneraAnalisis(int numAnalisis, Date fecha) {
        this.numAnalisis = numAnalisis;
        this.fecha = fecha;
    }

    public GeneraAnalisis generarNuevoAnalisis(GeneraAnalisis antiguo, Date fecha) {
        GeneraAnalisis nuevoAnalisis = new GeneraAnalisis(
                antiguo.getNumAnalisis() + 1,
                fecha);

        nuevoAnalisis.setPaciente(antiguo.getPaciente());
        return nuevoAnalisis;
    }

    // Getters
    public Date getFecha() {
        return fecha;
    }
    public int getNumAnalisis() {
        return numAnalisis;
    }
    public Paciente getPaciente() {
        return paciente;
    }
    public int getAnalisisPendiente() {
        return analisisPendiente;
    }

    //Setters
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
        paciente.addPropertyChangeListener(this);
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public void setNumAnalisis(int numAnalisis) {
        this.numAnalisis = numAnalisis;
    }
    public void setAnalisisPendiente(int analisisPendiente) {
        this.analisisPendiente = analisisPendiente;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equalsIgnoreCase("ultimoHierro")) {

            int variacionHierro = diferenciaEntreValores(evt.getOldValue(), evt.getNewValue());

            if (variacionHierro > 10)
                analisisPendiente++;
        }
    }

    private int diferenciaEntreValores(Object valorAntiguo, Object nuevoValor) {
        Integer antiguo = (Integer) valorAntiguo;
        Integer nuevo = (Integer) nuevoValor;
        return Math.abs(antiguo - nuevo);
    }
}
