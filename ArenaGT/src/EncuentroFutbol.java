public class EncuentroFutbol extends Evento {
    private final Participante local;
    private final Participante visitante;

    public EncuentroFutbol(String id, Participante local, Participante visitante) {
        super(id);
        this.local = local;
        this.visitante = visitante;
    }

    @Override
    public void registrarResultado(String datosResultado) throws IllegalArgumentException {
        if (datosResultado == null || datosResultado.trim().isEmpty()) {
            throw new IllegalArgumentException("El resultado no puede estar vacío.");
        }

        String[] partes = datosResultado.trim().split("-");
        if (partes.length != 2) {
            throw new IllegalArgumentException("Formato inválido. Debe ser 'golesLocal-golesVisitante'.");
        }

        int golesLocal;
        int golesVisitante;
        try {
            golesLocal = Integer.parseInt(partes[0].trim());
            golesVisitante = Integer.parseInt(partes[1].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Los goles deben ser valores numéricos.");
        }

        if (golesLocal < 0 || golesVisitante < 0) {
            throw new IllegalArgumentException("Los goles no pueden ser negativos.");
        }

        int diferencia = golesLocal - golesVisitante;
        if (diferencia > 0) {
            local.acumular(3, diferencia, true);
            visitante.acumular(0, -diferencia, false);
        } else if (diferencia < 0) {
            visitante.acumular(3, -diferencia, true);
            local.acumular(0, diferencia, false);
        } else {
            local.acumular(1, 0, false);
            visitante.acumular(1, 0, false);
        }

        setFinalizado(true);
    }

    public Participante getLocal() {
        return local;
    }

    public Participante getVisitante() {
        return visitante;
    }
}
