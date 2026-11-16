import java.util.List;

public abstract class Disciplina {
    protected String nombre;
    protected int minParticipantes;
    protected int maxParticipantes;
    protected double costoInscripcion;

    public Disciplina(String nombre, int minParticipantes, int maxParticipantes, double costoInscripcion) {
        this.nombre = nombre;
        this.minParticipantes = minParticipantes;
        this.maxParticipantes = maxParticipantes;
        this.costoInscripcion = costoInscripcion;
    }

    public abstract List<Evento> generarEventos(List<Participante> participantes, String torneoId);

    public double calcularCotizacion(int cantidad) {
        return cantidad * costoInscripcion;
    }

    // Getters
    public String getNombre() { return nombre; }
    public int getMinParticipantes() { return minParticipantes; }
    public int getMaxParticipantes() { return maxParticipantes; }
    public double getCostoInscripcion() { return costoInscripcion; }
}