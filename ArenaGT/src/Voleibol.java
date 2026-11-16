import java.util.ArrayList;
import java.util.List;

public class Voleibol extends Disciplina {

    public Voleibol() {
        super("Voleibol", 4, 6, 550.0);
    }

    @Override
    public List<Evento> generarEventos(List<Participante> participantes, String torneoId) {
        List<Evento> eventos = new ArrayList<>();
        int contador = 1;
        for (int i = 0; i < participantes.size(); i++) {
            for (int j = i + 1; j < participantes.size(); j++) {
                eventos.add(new PartidoVoleibol(
                    torneoId + "-E" + contador++,
                    participantes.get(i),
                    participantes.get(j)
                ));
            }
        }
        return eventos;
    }
}
