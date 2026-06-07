package main;

import controlador.ControladorBiblioteca;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import vista.VentanaPrincipal;

/**
 * Punto de entrada de MusicPlayer Pro.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                System.out.println("No se pudo aplicar el estilo del sistema: " + ex.getMessage());
            }

            ControladorBiblioteca controlador = new ControladorBiblioteca();
            VentanaPrincipal ventana = new VentanaPrincipal(controlador);
            ventana.setVisible(true);
        });
    }
}
