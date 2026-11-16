public abstract class Evento implements Comparable<Evento> {
    private String id;
    private String fecha; 
    private int hora; 
    private String sede;
    private boolean finalizado;

    public Evento(String id) {
        this.id = id;
        this.fecha = "";
        this.hora = -1;
        this.sede = "";
        this.finalizado = false;
    }

    public boolean estaProgramado() {
        return !fecha.isEmpty() && hora >= 0 && !sede.isEmpty();
    }

    public void programar(String fecha, int hora, String sede) {
        this.fecha = fecha;
        this.hora = hora;
        this.sede = sede;
    }

    public abstract void registrarResultado(String datosResultado) throws IllegalArgumentException;

    public String getId() { return id; }
    public String getFecha() { return fecha; }
    public int getHora() { return hora; }
    public String getSede() { return sede; }
    public boolean isFinalizado() { return finalizado; }
    protected void setFinalizado(boolean finalizado) { this.finalizado = finalizado; }

    @Override
    public int compareTo(Evento otro) {
        int compFecha = this.fecha.compareTo(otro.fecha);
        if (compFecha != 0) return compFecha;
        
        int compHora = Integer.compare(this.hora, otro.hora);
        if (compHora != 0) return compHora;
        
        return this.sede.compareTo(otro.sede);
    }
}