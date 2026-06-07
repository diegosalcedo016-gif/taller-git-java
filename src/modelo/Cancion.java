package modelo;

/**
 * Representa una cancion dentro de la biblioteca multimedia.
 * Hereda de ArchivoMultimedia y redefine el comportamiento reproducir().
 */
public class Cancion extends ArchivoMultimedia {
    private String artista;

    public Cancion(String titulo, String artista, double duracion) {
        super(titulo, duracion);
        setArtista(artista);
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        if (artista == null || artista.trim().isEmpty()) {
            throw new IllegalArgumentException("El artista no puede estar vacio.");
        }
        this.artista = artista.trim();
    }

    @Override
    public String reproducir() {
        return "Reproduciendo cancion: " + getTitulo() + " - " + artista;
    }

    @Override
    public String getTipo() {
        return "Cancion";
    }

    @Override
    public String getResponsable() {
        return artista;
    }
}
