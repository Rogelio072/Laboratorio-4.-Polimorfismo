import java.util.ArrayList;
import java.util.List;

public class Torneo {
    public enum Estado { EN_INSCRIPCION, EN_JUEGO, FINALIZADO }

    private String codigo;
    private String nombre;
    private Disciplina disciplina;
    private Estado estado;
    private List<Participante> inscritos;
    private List<Evento> eventos;

    public Torneo(String codigo, String nombre, Disciplina disciplina) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.disciplina = disciplina;
        this.estado = Estado.EN_INSCRIPCION;
        this.inscritos = new ArrayList<>();
        this.eventos = new ArrayList<>();
    }

    public boolean inscribirParticipante(Participante p) {
        if (estado != Estado.EN_INSCRIPCION) return false;
        if (inscritos.size() >= disciplina.getMaxParticipantes()) return false;
        if (inscritos.contains(p)) return false;
        
        long countEmpresa = inscritos.stream().filter(ins -> ins.getEmpresa().equalsIgnoreCase(p.getEmpresa())).count();
        if (countEmpresa >= 2) return false;

        inscritos.add(p);
        return true;
    }

    public void cerrarInscripciones() {
        if (inscritos.size() >= disciplina.getMinParticipantes()) {
            this.estado = Estado.EN_JUEGO;
            this.eventos = disciplina.generarEventos(inscritos, codigo);
        }
    }

    public void verificarFinTorneo() {
        if (estado == Estado.EN_JUEGO) {
            boolean todosTerminados = eventos.stream().allMatch(Evento::isFinalizado);
            if (todosTerminados) {
                this.estado = Estado.FINALIZADO;
            }
        }
    }

    public Estado getEstado() { return estado; }
    public List<Evento> getEventos() { return eventos; }
    public List<Participante> getInscritos() { return inscritos; }
    public Disciplina getDisciplina() { return disciplina; }
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getNome() { return nombre; }
}