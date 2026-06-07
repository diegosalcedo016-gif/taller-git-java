package modelo;

/**
 * Representa un podcast dentro de la biblioteca multimedia.
 * Hereda de ArchivoMultimedia y redefine el comportamiento reproducir().
 */
public class Podcast extends ArchivoMultimedia {
    private String presentador;

    public Podcast(String titulo, String presentador, double duracion) {
        super(titulo, duracion);
        setPresentador(presentador);
    }

    public String getPresentador() {
        return presentador;
    }

    public void setPresentador(String presentador) {
        if (presentador == null || presentador.trim().isEmpty()) {
            throw new IllegalArgumentException("El presentador no puede estar vacio.");
        }
        this.presentador = presentador.trim();
    }

    @Override
    public String reproducir() {
        return "Reproduciendo podcast: " + getTitulo() + " - " + presentador;
    }

    @Override
    public String getTipo() {
        return "Podcast";
    }

    @Override
    public String getResponsable() {
        return presentador;
    }
}
