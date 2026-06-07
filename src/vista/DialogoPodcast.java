package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import modelo.Podcast;

/**
 * Dialogo modal para capturar la informacion de un podcast.
 */
public class DialogoPodcast extends JDialog {
    private final JTextField campoTitulo;
    private final JTextField campoPresentador;
    private final JTextField campoDuracion;
    private Podcast podcastCreado;

    public DialogoPodcast(JFrame padre) {
        super(padre, "Agregar Podcast", true);
        campoTitulo = Estilos.crearCampoTexto();
        campoPresentador = Estilos.crearCampoTexto();
        campoDuracion = Estilos.crearCampoTexto();

        configurarVentana();
        construirFormulario();
    }

    public Podcast mostrarDialogo() {
        setVisible(true);
        return podcastCreado;
    }

    private void configurarVentana() {
        setSize(390, 230);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout(12, 12));
    }

    private void construirFormulario() {
        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCampos.setBorder(Estilos.crearBordeInterno());
        panelCampos.setBackground(Estilos.COLOR_FONDO);

        panelCampos.add(Estilos.crearEtiqueta("Titulo"));
        panelCampos.add(campoTitulo);
        panelCampos.add(Estilos.crearEtiqueta("Presentador"));
        panelCampos.add(campoPresentador);
        panelCampos.add(Estilos.crearEtiqueta("Duracion (minutos)"));
        panelCampos.add(campoDuracion);

        JButton botonGuardar = Estilos.crearBotonPrimario("Guardar");
        JButton botonCancelar = Estilos.crearBotonSecundario("Cancelar");

        botonGuardar.addActionListener(evento -> guardarPodcast());
        botonCancelar.addActionListener(evento -> dispose());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(Estilos.COLOR_FONDO);
        panelBotones.add(botonCancelar);
        panelBotones.add(botonGuardar);

        add(panelCampos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        getContentPane().setBackground(Estilos.COLOR_FONDO);
    }

    private void guardarPodcast() {
        try {
            double duracion = Double.parseDouble(campoDuracion.getText().trim());
            podcastCreado = new Podcast(campoTitulo.getText(), campoPresentador.getText(), duracion);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La duracion debe ser un numero valido.", "Dato invalido",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dato invalido", JOptionPane.ERROR_MESSAGE);
        }
    }
}
