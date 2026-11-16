public class PartidoVoleibol extends Evento {
    private final Participante equipoA;
    private final Participante equipoB;

    public PartidoVoleibol(String id, Participante equipoA, Participante equipoB) {
        super(id);
        this.equipoA = equipoA;
        this.equipoB = equipoB;
    }

    @Override
    public void registrarResultado(String datosResultado) throws IllegalArgumentException {
        if (datosResultado == null || datosResultado.trim().isEmpty()) {
            throw new IllegalArgumentException("El resultado no puede estar vacío.");
        }

        String[] partes = datosResultado.trim().split("-");
        if (partes.length != 2) {
            throw new IllegalArgumentException("Formato inválido. Debe ser 'setsA-setsB'.");
        }

        int setsA;
        int setsB;
        try {
            setsA = Integer.parseInt(partes[0].trim());
            setsB = Integer.parseInt(partes[1].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Los sets deben ser numéricos.");
        }

        if (setsA < 0 || setsB < 0) {
            throw new IllegalArgumentException("Los sets no pueden ser negativos.");
        }

        int diferencia = setsA - setsB;
        if (diferencia > 0) {
            equipoA.acumular(3, diferencia, true);
            equipoB.acumular(0, -diferencia, false);
        } else if (diferencia < 0) {
            equipoB.acumular(3, -diferencia, true);
            equipoA.acumular(0, diferencia, false);
        } else {
            equipoA.acumular(1, 0, false);
            equipoB.acumular(1, 0, false);
        }

        setFinalizado(true);
    }
}
