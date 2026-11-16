public class PartidoBaloncesto extends Evento {
    private final Participante local;
    private final Participante visitante;

    public PartidoBaloncesto(String id, Participante local, Participante visitante) {
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
            throw new IllegalArgumentException("Formato inválido. Debe ser 'puntosLocal-puntosVisitante'.");
        }

        int puntosLocal;
        int puntosVisitante;
        try {
            puntosLocal = Integer.parseInt(partes[0].trim());
            puntosVisitante = Integer.parseInt(partes[1].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Los puntos deben ser numéricos.");
        }

        if (puntosLocal < 0 || puntosVisitante < 0) {
            throw new IllegalArgumentException("Los puntos no pueden ser negativos.");
        }

        int diferencia = puntosLocal - puntosVisitante;
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
}
