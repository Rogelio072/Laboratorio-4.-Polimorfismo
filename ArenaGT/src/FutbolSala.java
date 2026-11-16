import java.util.ArrayList;
import java.util.List;

public class FutbolSala extends Disciplina {

    public FutbolSala() {
        super("Fútbol Sala", 4, 8, 600.0);
    }

    @Override
    public List<Evento> generarEventos(List<Participante> participantes, String torneoId) {
        List<Evento> eventos = new ArrayList<>();
        int contador = 1;
        for (int i = 0; i < participantes.size(); i++) {
            for (int j = i + 1; j < participantes.size(); j++) {
                eventos.add(new EncuentroFutbol(
                    torneoId + "-E" + (contador++),
                    participantes.get(i),
                    participantes.get(j)
                ));
            }
        }
        return eventos;
    }
}