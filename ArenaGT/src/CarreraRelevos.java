public class CarreraRelevos extends Evento {
    private final Participante participanteA;
    private final Participante participanteB;

    public CarreraRelevos(String id, Participante participanteA, Participante participanteB) {
        super(id);
        this.participanteA = participanteA;
        this.participanteB = participanteB;
    }

    @Override
    public void registrarResultado(String datosResultado) throws IllegalArgumentException {
        if (datosResultado == null || datosResultado.trim().isEmpty()) {
            throw new IllegalArgumentException("El resultado no puede estar vacío.");
        }

        String[] partes = datosResultado.trim().split("-");
        if (partes.length != 2) {
            throw new IllegalArgumentException("Formato inválido. Debe ser 'tiempoA-tiempoB' en segundos.");
        }

        double tiempoA;
        double tiempoB;
        try {
            tiempoA = Double.parseDouble(partes[0].trim());
            tiempoB = Double.parseDouble(partes[1].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Los tiempos deben ser numéricos.");
        }

        if (tiempoA < 0 || tiempoB < 0) {
            throw new IllegalArgumentException("Los tiempos no pueden ser negativos.");
        }

        if (tiempoA < tiempoB) {
            participanteA.acumular(3, 1, true);
            participanteB.acumular(0, -1, false);
        } else if (tiempoB < tiempoA) {
            participanteB.acumular(3, 1, true);
            participanteA.acumular(0, -1, false);
        } else {
            participanteA.acumular(1, 0, false);
            participanteB.acumular(1, 0, false);
        }

        setFinalizado(true);
    }
}
