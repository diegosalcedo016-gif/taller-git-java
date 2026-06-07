package controlador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import modelo.ArchivoMultimedia;
import modelo.Cancion;
import modelo.Podcast;

/**
 * Controlador principal de la aplicacion.
 * Administra la biblioteca y expone operaciones para la vista.
 */
public class ControladorBiblioteca {
    private final ArrayList<ArchivoMultimedia> biblioteca;

    public ControladorBiblioteca() {
        biblioteca = new ArrayList<>();
        cargarDatosDeEjemplo();
    }

    public void agregarArchivo(ArchivoMultimedia archivo) {
        if (archivo == null) {
            throw new IllegalArgumentException("El archivo multimedia no puede ser nulo.");
        }
        biblioteca.add(archivo);
    }

    public ArchivoMultimedia obtenerArchivo(int indice) {
        validarIndice(indice);
        return biblioteca.get(indice);
    }

    public ArchivoMultimedia eliminarArchivo(int indice) {
        validarIndice(indice);
        return biblioteca.remove(indice);
    }

    public List<ArchivoMultimedia> obtenerBiblioteca() {
        return Collections.unmodifiableList(biblioteca);
    }

    public List<ArchivoMultimedia> buscarPorTitulo(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return obtenerBiblioteca();
        }

        String consulta = texto.trim().toLowerCase();
        ArrayList<ArchivoMultimedia> resultados = new ArrayList<>();

        for (ArchivoMultimedia archivo : biblioteca) {
            if (archivo.getTitulo().toLowerCase().contains(consulta)) {
                resultados.add(archivo);
            }
        }

        return resultados;
    }

    public int contarCanciones() {
        int cantidad = 0;
        for (ArchivoMultimedia archivo : biblioteca) {
            if (archivo instanceof Cancion) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public int contarPodcasts() {
        int cantidad = 0;
        for (ArchivoMultimedia archivo : biblioteca) {
            if (archivo instanceof Podcast) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public double calcularDuracionTotal() {
        double total = 0;
        for (ArchivoMultimedia archivo : biblioteca) {
            total += archivo.getDuracion();
        }
        return total;
    }

    public ArchivoMultimedia obtenerArchivoMayorDuracion() {
        if (biblioteca.isEmpty()) {
            return null;
        }

        ArchivoMultimedia mayor = biblioteca.get(0);
        for (ArchivoMultimedia archivo : biblioteca) {
            if (archivo.getDuracion() > mayor.getDuracion()) {
                mayor = archivo;
            }
        }
        return mayor;
    }

    private void validarIndice(int indice) {
        if (indice < 0 || indice >= biblioteca.size()) {
            throw new IndexOutOfBoundsException("No existe un archivo multimedia en esa posicion.");
        }
    }

    private void cargarDatosDeEjemplo() {
        biblioteca.add(new Cancion("Blinding Lights", "The Weeknd", 3.20));
        biblioteca.add(new Cancion("Viva La Vida", "Coldplay", 4.10));
        biblioteca.add(new Podcast("Tecnologia al Dia", "Laura Gomez", 28.50));
    }
}
