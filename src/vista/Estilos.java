package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Utilidad interna para mantener una apariencia visual consistente.
 */
final class Estilos {
    static final Color COLOR_FONDO = new Color(12, 14, 18);
    static final Color COLOR_PANEL = new Color(25, 29, 36);
    static final Color COLOR_PANEL_CLARO = new Color(33, 38, 47);
    static final Color COLOR_PRIMARIO = new Color(36, 211, 111);
    static final Color COLOR_PRIMARIO_OSCURO = new Color(18, 143, 78);
    static final Color COLOR_TITULO_TABLA = new Color(191, 255, 217);
    static final Color COLOR_SECUNDARIO = new Color(63, 87, 113);
    static final Color COLOR_TEXTO = new Color(244, 247, 250);
    static final Color COLOR_TEXTO_SECUNDARIO = new Color(169, 179, 191);
    static final Color COLOR_BORDE = new Color(58, 67, 80);
    static final Font FUENTE_BASE = new Font("Segoe UI", Font.PLAIN, 15);
    static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 32);

    private Estilos() {
    }

    static JButton crearBotonPrimario(String texto) {
        JButton boton = crearBotonBase(texto);
        boton.setBackground(COLOR_PRIMARIO);
        boton.setForeground(new Color(7, 18, 12));
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(91, 236, 151)),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        return boton;
    }

    static JButton crearBotonSecundario(String texto) {
        JButton boton = crearBotonBase(texto);
        boton.setBackground(COLOR_SECUNDARIO);
        boton.setForeground(COLOR_TEXTO);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(86, 112, 139)),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        return boton;
    }

    static JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(FUENTE_BASE);
        etiqueta.setForeground(COLOR_TEXTO);
        return etiqueta;
    }

    static Border crearBordeInterno() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                BorderFactory.createEmptyBorder(16, 16, 16, 16));
    }

    static JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setFont(FUENTE_BASE);
        campo.setBackground(COLOR_PANEL_CLARO);
        campo.setForeground(COLOR_TEXTO);
        campo.setCaretColor(COLOR_PRIMARIO);
        campo.setSelectionColor(COLOR_PRIMARIO_OSCURO);
        campo.setSelectedTextColor(COLOR_TEXTO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                BorderFactory.createEmptyBorder(9, 12, 9, 12)));
        campo.setPreferredSize(new Dimension(220, 40));
        campo.setMinimumSize(new Dimension(180, 40));
        return campo;
    }

    static void aplicarFiltroDecimal(JTextField campo) {
        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                reemplazarSiEsValido(fb, offset, 0, string, attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                reemplazarSiEsValido(fb, offset, length, text, attrs);
            }

            private void reemplazarSiEsValido(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String textoActual = fb.getDocument().getText(0, fb.getDocument().getLength());
                String nuevoTexto = textoActual.substring(0, offset) + (text == null ? "" : text)
                        + textoActual.substring(offset + length);

                if (nuevoTexto.matches("\\d{0,4}([\\.,]\\d{0,2})?")) {
                    fb.replace(offset, length, text, attrs);
                }
            }
        });
    }

    private static JButton crearBotonBase(String texto) {
        JButton boton = new JButton(texto);
        boton.setUI(new BasicButtonUI());
        boton.setFont(FUENTE_BASE.deriveFont(Font.BOLD));
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(true);
        boton.setOpaque(true);
        boton.setMargin(new Insets(8, 12, 8, 12));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return boton;
    }
}
