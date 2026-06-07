package modelo;

/**
 * Clase base abstracta para todos los archivos multimedia de la biblioteca.
 * Aplica encapsulamiento porque sus atributos son privados y se accede a ellos
 * mediante getters y setters con validacion.
 */
public abstract class ArchivoMultimedia {
    private String titulo;
    private double duracion;

    public ArchivoMultimedia(String titulo, double duracion) {
        setTitulo(titulo);
        setDuracion(duracion);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El titulo no puede estar vacio.");
        }
        this.titulo = titulo.trim();
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        if (duracion <= 0) {
            throw new IllegalArgumentException("La duracion debe ser mayor que cero.");
        }
        this.duracion = duracion;
    }

    public abstract String reproducir();

    public abstract String getTipo();

    public abstract String getResponsable();
}
