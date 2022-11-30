package ifp.kikeverea.gui;

import ifp.kikeverea.app.Calculadora;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

public class CalculadoraGui {

    private static final int ANCHO_CUADRO = 400;
    private static final int TAMANO_BOTON = 60;

    private JFrame frame;
    private JTextField display;

    private final Set<JButton> operaciones = new HashSet<>();
    private final Set<JButton> botones = new HashSet<>();
    private final Calculadora calculadora = new Calculadora();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CalculadoraGui window = new CalculadoraGui();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CalculadoraGui() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, ANCHO_CUADRO + 15, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        display = new JTextField();
        display.setBounds(40, 50, 320, 40);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setColumns(10);
        display.setEditable(false);
        display.setBackground(UIManager.getColor("TextField.background"));
        display.setBorder(BorderFactory.createEtchedBorder());
        frame.getContentPane().add(display);

        JButton c = boton(40, 120, TAMANO_BOTON, TAMANO_BOTON, "C");
        c.addActionListener(e -> {
            display.setText("");
            calculadora.clear();
            desbloquearTodos();
        });
        frame.getContentPane().add(c);

        JButton par = boton(120, 120, TAMANO_BOTON, TAMANO_BOTON, "Par");
        anadirBoton(par, e-> {
            try {
                establecerOperacion("Par");
                ejecutarOperacion();
            }
            catch (Exception ex) {
                error();
            }
        });
        operaciones.add(par);

        anadirBotonNumerico(boton(40, 200, TAMANO_BOTON, TAMANO_BOTON, "1"));

        anadirBotonNumerico(boton(120, 200, TAMANO_BOTON, TAMANO_BOTON, "2"));

        anadirBotonNumerico(boton(200, 200, TAMANO_BOTON, TAMANO_BOTON, "3"));

        anadirBotonOperacion(boton(300, 200, TAMANO_BOTON, TAMANO_BOTON, "+"));

        anadirBotonNumerico(boton(40, 280, TAMANO_BOTON, TAMANO_BOTON, "4"));

        anadirBotonNumerico(boton(120, 280, TAMANO_BOTON, TAMANO_BOTON, "5"));

        anadirBotonNumerico(boton(200, 280, TAMANO_BOTON, TAMANO_BOTON, "6"));

        anadirBotonOperacion(boton(300, 280, TAMANO_BOTON, TAMANO_BOTON, "-"));

        anadirBotonNumerico(boton(40, 360, TAMANO_BOTON, TAMANO_BOTON, "7"));

        anadirBotonNumerico(boton(120, 360, TAMANO_BOTON, TAMANO_BOTON, "8"));

        anadirBotonNumerico(boton(200, 360, TAMANO_BOTON, TAMANO_BOTON, "9"));

        anadirBotonOperacion(boton(300, 360, TAMANO_BOTON, TAMANO_BOTON, "*"));

        anadirBotonNumerico(boton(40, 440, TAMANO_BOTON, TAMANO_BOTON, "0"));

        anadirBotonOperacion(boton(300, 440, TAMANO_BOTON, TAMANO_BOTON, "/"));

        anadirBoton(boton(120, 440, 140, TAMANO_BOTON, "="), e-> ejecutarOperacion());

    }

    private void ejecutarOperacion() {
        try {
            display.setText(calculadora.ejecutarOperacion());
            bloquearTodos();
        }
        catch (Exception e) {
            error();
            e.printStackTrace();
        }
    }

    private void establecerOperacion(String signo) {
        try {
            display.setText("");
            calculadora.establecerOperacion(signo);
            bloquearOperaciones();
        }
        catch (Exception e) {
            error();
            e.printStackTrace();
        }
    }

    private void error() {
        display.setText("error");
        bloquearTodos();
    }

    private JButton boton(int x, int y, int ancho, int alto, String texto) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, ancho, alto);
        return boton; 
    }

    private void anadirBotonNumerico(JButton boton) {
        anadirBoton(boton, e -> display.setText(calculadora.anadirNumero(boton.getText())));
        botones.add(boton);
    }

    private void anadirBotonOperacion(JButton boton) {
        anadirBoton(boton, e -> establecerOperacion(boton.getText()));
        operaciones.add(boton);
    }

    private void anadirBoton(JButton boton, ActionListener accion) {
        boton.addActionListener(accion);
        frame.getContentPane().add(boton);
        botones.add(boton);
    }

    private void bloquearTodos() {
        cambiarHabilitacionBotones(botones, false);
    }

    private void bloquearOperaciones() {
        cambiarHabilitacionBotones(operaciones, false);
    }

    private void desbloquearTodos() {
        cambiarHabilitacionBotones(botones, true);
    }

    private void cambiarHabilitacionBotones(Set<JButton> botones, boolean habilitado) {
        for (JButton boton: botones) {
            boton.setEnabled(habilitado);
        }
    }
}
