package vista;

import controlador.ControladorBiblioteca;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.JTableHeader;
import modelo.ArchivoMultimedia;
import modelo.Cancion;
import modelo.Podcast;

/**
 * Vista principal de MusicPlayer Pro.
 * Construye la interfaz Swing y delega la logica al controlador.
 */
public class VentanaPrincipal extends JFrame {
    private final ControladorBiblioteca controlador;
    private final JTable tablaBiblioteca;
    private final DefaultTableModel modeloTabla;
    private final JLabel etiquetaEstado;
    private final JTextField campoBusqueda;
    private List<ArchivoMultimedia> elementosMostrados;

    public VentanaPrincipal(ControladorBiblioteca controlador) {
        this.controlador = controlador;
        this.elementosMostrados = new ArrayList<>();
        this.modeloTabla = crearModeloTabla();
        this.tablaBiblioteca = new JTable(modeloTabla);
        this.etiquetaEstado = new JLabel("Biblioteca lista.");
        this.campoBusqueda = Estilos.crearCampoTexto();
        this.campoBusqueda.setColumns(22);

        configurarVentana();
        construirInterfaz();
        actualizarTabla(controlador.obtenerBiblioteca());
    }

    private void configurarVentana() {
        setTitle("MusicPlayer Pro");
        setSize(980, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new java.awt.Dimension(860, 520));
        getContentPane().setBackground(Estilos.COLOR_FONDO);
        setLayout(new BorderLayout(14, 14));
    }

    private void construirInterfaz() {
        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);
        add(crearPanelEstado(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Estilos.COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(18, 22, 4, 22));

        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panelTitulo.setBackground(Estilos.COLOR_FONDO);

        JLabel tituloMusic = new JLabel("MUSIC");
        tituloMusic.setFont(Estilos.FUENTE_TITULO);
        tituloMusic.setForeground(Estilos.COLOR_PRIMARIO);

        JLabel tituloPlayer = new JLabel("PLAYER PRO");
        tituloPlayer.setFont(Estilos.FUENTE_TITULO);
        tituloPlayer.setForeground(Estilos.COLOR_TEXTO);

        panelTitulo.add(tituloMusic);
        panelTitulo.add(tituloPlayer);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        panelBusqueda.setBackground(Estilos.COLOR_FONDO);
        JButton botonBuscar = Estilos.crearBotonSecundario("Buscar");
        botonBuscar.addActionListener(evento -> buscar());
        campoBusqueda.addActionListener(evento -> buscar());
        panelBusqueda.add(campoBusqueda);
        panelBusqueda.add(botonBuscar);

        panel.add(panelTitulo, BorderLayout.WEST);
        panel.add(panelBusqueda, BorderLayout.EAST);
        return panel;
    }

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBackground(Estilos.COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(8, 22, 8, 22));

        configurarTabla();
        JScrollPane scrollTabla = new JScrollPane(tablaBiblioteca);
        scrollTabla.setBorder(BorderFactory.createLineBorder(Estilos.COLOR_BORDE));
        scrollTabla.getViewport().setBackground(Estilos.COLOR_PANEL);

        panel.add(scrollTabla, BorderLayout.CENTER);
        panel.add(crearPanelBotones(), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new GridLayout(1, 5, 10, 0));
        panel.setBackground(Estilos.COLOR_FONDO);

        JButton botonAgregarCancion = Estilos.crearBotonPrimario("Agregar Cancion");
        JButton botonAgregarPodcast = Estilos.crearBotonPrimario("Agregar Podcast");
        JButton botonReproducir = Estilos.crearBotonSecundario("Reproducir");
        JButton botonEliminar = Estilos.crearBotonSecundario("Eliminar");
        JButton botonEstadisticas = Estilos.crearBotonSecundario("Mostrar Estadisticas");

        botonAgregarCancion.addActionListener(evento -> agregarCancion());
        botonAgregarPodcast.addActionListener(evento -> agregarPodcast());
        botonReproducir.addActionListener(evento -> reproducirSeleccionado());
        botonEliminar.addActionListener(evento -> eliminarSeleccionado());
        botonEstadisticas.addActionListener(evento -> mostrarEstadisticas());

        panel.add(botonAgregarCancion);
        panel.add(botonAgregarPodcast);
        panel.add(botonReproducir);
        panel.add(botonEliminar);
        panel.add(botonEstadisticas);
        return panel;
    }

    private JPanel crearPanelEstado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Estilos.COLOR_PANEL);
        panel.setBorder(BorderFactory.createEmptyBorder(12, 22, 12, 22));

        etiquetaEstado.setFont(Estilos.FUENTE_BASE);
        etiquetaEstado.setForeground(Estilos.COLOR_TEXTO_SECUNDARIO);
        panel.add(etiquetaEstado, BorderLayout.WEST);
        return panel;
    }

    private DefaultTableModel crearModeloTabla() {
        return new DefaultTableModel(new Object[] { "Tipo", "Titulo", "Artista/Presentador", "Duracion" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void configurarTabla() {
        tablaBiblioteca.setFont(Estilos.FUENTE_BASE);
        tablaBiblioteca.setRowHeight(34);
        tablaBiblioteca.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaBiblioteca.setBackground(Estilos.COLOR_PANEL);
        tablaBiblioteca.setForeground(Estilos.COLOR_TEXTO);
        tablaBiblioteca.setGridColor(Estilos.COLOR_BORDE);
        tablaBiblioteca.setSelectionBackground(Estilos.COLOR_PRIMARIO_OSCURO);
        tablaBiblioteca.setSelectionForeground(Estilos.COLOR_TEXTO);
        tablaBiblioteca.setShowVerticalLines(false);
        tablaBiblioteca.setIntercellSpacing(new java.awt.Dimension(0, 1));
        tablaBiblioteca.setFillsViewportHeight(true);

        JTableHeader encabezado = tablaBiblioteca.getTableHeader();
        encabezado.setFont(Estilos.FUENTE_BASE.deriveFont(Font.BOLD, 15f));
        encabezado.setBackground(Estilos.COLOR_PANEL_CLARO);
        encabezado.setForeground(Estilos.COLOR_TITULO_TABLA);
        encabezado.setOpaque(true);
        encabezado.setBorder(BorderFactory.createLineBorder(Estilos.COLOR_BORDE));
        encabezado.setPreferredSize(new java.awt.Dimension(encabezado.getPreferredSize().width, 44));

        DefaultTableCellRenderer renderEncabezado = new DefaultTableCellRenderer();
        renderEncabezado.setBackground(Estilos.COLOR_PANEL_CLARO);
        renderEncabezado.setForeground(Estilos.COLOR_TITULO_TABLA);
        renderEncabezado.setFont(Estilos.FUENTE_BASE.deriveFont(Font.BOLD, 15f));
        renderEncabezado.setOpaque(true);
        renderEncabezado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, Estilos.COLOR_PRIMARIO_OSCURO),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        renderEncabezado.setHorizontalAlignment(SwingConstants.LEFT);
        encabezado.setDefaultRenderer(renderEncabezado);

        DefaultTableCellRenderer renderCeldas = new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                java.awt.Component componente = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                        row, column);
                if (isSelected) {
                    componente.setBackground(Estilos.COLOR_PRIMARIO_OSCURO);
                    componente.setForeground(Estilos.COLOR_TEXTO);
                } else {
                    componente.setBackground(row % 2 == 0 ? Estilos.COLOR_PANEL : new Color(21, 25, 31));
                    componente.setForeground(Estilos.COLOR_TEXTO);
                }
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return componente;
            }
        };
        tablaBiblioteca.setDefaultRenderer(Object.class, renderCeldas);
        configurarColumnas();
        tablaBiblioteca.setAutoCreateRowSorter(true);
    }

    private void configurarColumnas() {
        TableColumnModel columnas = tablaBiblioteca.getColumnModel();
        columnas.getColumn(0).setPreferredWidth(120);
        columnas.getColumn(1).setPreferredWidth(300);
        columnas.getColumn(2).setPreferredWidth(260);
        columnas.getColumn(3).setPreferredWidth(140);
    }

    private void agregarCancion() {
        DialogoCancion dialogo = new DialogoCancion(this);
        Cancion cancion = dialogo.mostrarDialogo();
        if (cancion != null) {
            controlador.agregarArchivo(cancion);
            actualizarTabla(controlador.obtenerBiblioteca());
            actualizarEstado("Cancion agregada correctamente: " + cancion.getTitulo());
        }
    }

    private void agregarPodcast() {
        DialogoPodcast dialogo = new DialogoPodcast(this);
        Podcast podcast = dialogo.mostrarDialogo();
        if (podcast != null) {
            controlador.agregarArchivo(podcast);
            actualizarTabla(controlador.obtenerBiblioteca());
            actualizarEstado("Podcast agregado correctamente: " + podcast.getTitulo());
        }
    }

    private void reproducirSeleccionado() {
        ArchivoMultimedia archivo = obtenerSeleccionado();
        if (archivo == null) {
            mostrarMensajeSeleccion("Seleccione un archivo para reproducir.");
            return;
        }

        actualizarEstado(archivo.reproducir());
    }

    private void eliminarSeleccionado() {
        int fila = tablaBiblioteca.getSelectedRow();
        ArchivoMultimedia archivo = obtenerSeleccionado();
        if (archivo == null) {
            mostrarMensajeSeleccion("Seleccione un archivo para eliminar.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(this,
                "Desea eliminar \"" + archivo.getTitulo() + "\"?",
                "Confirmar eliminacion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) {
            controlador.eliminarArchivo(controlador.obtenerBiblioteca().indexOf(archivo));
            actualizarTabla(controlador.obtenerBiblioteca());
            actualizarEstado(archivo.getTipo() + " eliminado correctamente: " + archivo.getTitulo());
        } else {
            tablaBiblioteca.setRowSelectionInterval(fila, fila);
        }
    }

    private void buscar() {
        String texto = campoBusqueda.getText();
        List<ArchivoMultimedia> resultados = controlador.buscarPorTitulo(texto);
        actualizarTabla(resultados);

        if (texto == null || texto.trim().isEmpty()) {
            actualizarEstado("Mostrando biblioteca completa.");
        } else {
            actualizarEstado("Resultados encontrados: " + resultados.size());
        }
    }

    private void mostrarEstadisticas() {
        ArchivoMultimedia mayor = controlador.obtenerArchivoMayorDuracion();
        String archivoMayor = mayor == null ? "No hay archivos registrados"
                : mayor.getTipo() + " - " + mayor.getTitulo() + " (" + formatearDuracion(mayor.getDuracion()) + ")";

        String mensaje = "Cantidad de canciones: " + controlador.contarCanciones()
                + "\nCantidad de podcasts: " + controlador.contarPodcasts()
                + "\nDuracion total: " + formatearDuracion(controlador.calcularDuracionTotal())
                + "\nMayor duracion: " + archivoMayor;

        JOptionPane.showMessageDialog(this, mensaje, "Estadisticas de la biblioteca",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private ArchivoMultimedia obtenerSeleccionado() {
        int filaVista = tablaBiblioteca.getSelectedRow();
        if (filaVista < 0) {
            return null;
        }

        int filaModelo = tablaBiblioteca.convertRowIndexToModel(filaVista);
        if (filaModelo < 0 || filaModelo >= elementosMostrados.size()) {
            return null;
        }

        return elementosMostrados.get(filaModelo);
    }

    private void actualizarTabla(List<ArchivoMultimedia> archivos) {
        elementosMostrados = new ArrayList<>(archivos);
        modeloTabla.setRowCount(0);

        for (ArchivoMultimedia archivo : elementosMostrados) {
            modeloTabla.addRow(new Object[] {
                    archivo.getTipo(),
                    archivo.getTitulo(),
                    archivo.getResponsable(),
                    formatearDuracion(archivo.getDuracion())
            });
        }
    }

    private String formatearDuracion(double duracion) {
        return String.format("%.2f min", duracion);
    }

    private void mostrarMensajeSeleccion(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Seleccion requerida", JOptionPane.WARNING_MESSAGE);
        actualizarEstado(mensaje);
    }

    private void actualizarEstado(String mensaje) {
        etiquetaEstado.setHorizontalAlignment(SwingConstants.LEFT);
        etiquetaEstado.setText(mensaje);
    }
}
