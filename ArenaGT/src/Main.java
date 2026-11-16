import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Participante> participantes = new ArrayList<>();
    private static List<Torneo> torneos = new ArrayList<>();
    private static List<Evento> calendarioGlobal = new ArrayList<>();

    public static void main(String[] args) {
        // 1. Cargar datos iniciales obligatorios
        cargarDatosIniciales();

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("\n       ARENAGT - SISTEMA DE TORNEOS     ");
            System.out.println("1. Cotizar e inscribir participantes");
            System.out.println("2. Registrar participantes y crear torneos");
            System.out.println("3. Cerrar inscripciones de un torneo");
            System.out.println("4. Programar / Reprogramar eventos");
            System.out.println("5. Registrar resultados de eventos");
            System.out.println("6. Consultar torneos, participantes y calendarios");
            System.out.println("7. Generar reportes (Ingresos, Premios, Ranking)");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                if (scanner.hasNextInt()) {
                    opcion = scanner.nextInt();
                    scanner.nextLine(); // Limpiar buffer

                    switch (opcion) {
                        case 1:
                            menuCotizarEInscribir(scanner);
                            break;
                        case 2:
                            menuRegistrarYCrear(scanner);
                            break;
                        case 3:
                            menuCerrarInscripciones(scanner);
                            break;
                        case 4:
                            menuProgramarEvento(scanner);
                            break;
                        case 5:
                            menuRegistrarResultado(scanner);
                            break;
                        case 6:
                            menuConsultas();
                            break;
                        case 7:
                            menuReportes();
                            break;
                        case 8:
                            System.out.println("Saliendo del programa. de nada por todo :)");
                            break;
                        default:
                            System.out.println("Opción inválida. Intente de nuevo.");
                    }
                } else {
                    System.out.println("Error: Debe ingresar un valor numérico válido.");
                    scanner.nextLine();
                }
            } catch (Exception e) {
                System.out.println("Error inesperado en la operación: " + e.getMessage());
            }

        } while (opcion != 8);

        scanner.close();
    }

    private static void cargarDatosIniciales() {
        // Empresas: TecnoSistemas, InnovaGT, DataSoft
        Participante p1 = new Participante("P01", "Ana Gómez", "TecnoSistemas");
        Participante p2 = new Participante("P02", "Carlos Ruiz", "TecnoSistemas");
        Participante p3 = new Participante("P03", "Elena Morales", "InnovaGT");
        Participante p4 = new Participante("P04", "David Pérez", "InnovaGT");
        Participante p5 = new Participante("P05", "Sofía Castro", "DataSoft");
        Participante p6 = new Participante("P06", "Javier López", "DataSoft");

        participantes.add(p1);
        participantes.add(p2);
        participantes.add(p3);
        participantes.add(p4);
        participantes.add(p5);
        participantes.add(p6);

        Torneo t1 = new Torneo("T-FS1", "Copa Fútbol Sala", new FutbolSala());
        t1.inscribirParticipante(p1);
        t1.inscribirParticipante(p2);
        t1.inscribirParticipante(p3);
        t1.inscribirParticipante(p4);
        t1.cerrarInscripciones();
        torneos.add(t1);

        for (Evento ev : t1.getEventos()) {
            calendarioGlobal.add(ev);
        }

        if (!t1.getEventos().isEmpty()) {
            Evento evDemo = t1.getEventos().get(0);
            evDemo.programar("2026-11-20", 10, "Cancha Central");
            // Registrar un resultado para mostrar avance
            try {
                evDemo.registrarResultado("3-1");
            } catch (Exception e) {
            }
        }
    }

    private static void menuCotizarEInscribir(Scanner scanner) {
        System.out.println("\n   COTIZAR E INSCRIBIR " );
        System.out.println("1. Cotizar inscripción");
        System.out.println("2. Inscribir participante en torneo");
        System.out.print("Elija una opción: ");
        int subOp = scanner.nextInt();
        scanner.nextLine();

        if (subOp == 1) {
            System.out.print("Ingrese disciplina (1: Fútbol Sala, 2: Baloncesto, 3: Voleibol, 4: Relevos): ");
            int discOp = scanner.nextInt();
            System.out.print("Ingrese cantidad de participantes: ");
            int cant = scanner.nextInt();
            
            Disciplina disc = obtenerDisciplinaPorOpcion(discOp);
            if (disc != null) {
                double total = disc.calcularCotizacion(cant);
                System.out.printf("Cotización total para %d participantes en %s: Q%.2f\n", cant, disc.getNombre(), total);
            } else {
                System.out.println("Disciplina no válida.");
            }
        } else if (subOp == 2) {
            System.out.print("Ingrese código del torneo: ");
            String tCod = scanner.nextLine();
            Torneo torneo = buscarTorneo(tCod);
            if (torneo == null) {
                System.out.println("Torneo no encontrado.");
                return;
            }

            System.out.print("Ingrese código del participante: ");
            String pCod = scanner.nextLine();
            Participante part = buscarParticipante(pCod);
            if (part == null) {
                System.out.println("Participante no encontrado. Debe registrarlo primero.");
                return;
            }

            boolean inscrito = torneo.inscribirParticipante(part);
            if (inscrito) {
                System.out.println("¡Participante inscrito exitosamente!");
            } else {
                System.out.println("Operación rechazada: No cumple con las condiciones de inscripción o cupo lleno.");
            }
        }
    }

    private static void menuRegistrarYCrear(Scanner scanner) {
        System.out.println("\n    REGISTRAR Y CREAR   ");
        System.out.println("1. Registrar nuevo participante");
        System.out.println("2. Crear nuevo torneo");
        System.out.print("Elija una opción: ");
        int subOp = scanner.nextInt();
        scanner.nextLine();

        if (subOp == 1) {
            System.out.print("Código único: ");
            String cod = scanner.nextLine();
            if (buscarParticipante(cod) != null) {
                System.out.println("Error: Ya existe un participante con ese código.");
                return;
            }
            System.out.print("Nombre: ");
            String nom = scanner.nextLine();
            System.out.print("Empresa: ");
            String emp = scanner.nextLine();

            participantes.add(new Participante(cod, nom, emp));
            System.out.println("Participante registrado correctamente.");
        } else if (subOp == 2) {
            System.out.print("Código del torneo: ");
            String cod = scanner.nextLine();
            System.out.print("Nombre del torneo: ");
            String nom = scanner.nextLine();
            System.out.print("Disciplina (1: Fútbol Sala, 2: Baloncesto, 3: Voleibol, 4: Relevos): ");
            int discOp = scanner.nextInt();

            Disciplina disc = obtenerDisciplinaPorOpcion(discOp);
            if (disc != null) {
                torneos.add(new Torneo(cod, nom, disc));
                System.out.println("Torneo creado exitosamente en estado de inscripción.");
            } else {
                System.out.println("Disciplina inválida.");
            }
        }
    }

    private static void menuCerrarInscripciones(Scanner scanner) {
        System.out.print("Ingrese el código del torneo a cerrar inscripciones: ");
        String cod = scanner.nextLine();
        Torneo torneo = buscarTorneo(cod);
        if (torneo != null) {
            if (torneo.getInscritos().size() >= torneo.getDisciplina().getMinParticipantes()) {
                torneo.cerrarInscripciones();
                for (Evento ev : torneo.getEventos()) {
                    if (!calendarioGlobal.contains(ev)) {
                        calendarioGlobal.add(ev);
                    }
                }
                System.out.println("Inscripciones cerradas. El torneo ha pasado a estado EN JUEGO y se generaron los eventos.");
            } else {
                System.out.println("Operación rechazada: El torneo no cuenta con el mínimo de participantes requeridos.");
            }
        } else {
            System.out.println("Torneo no encontrado.");
        }
    }

    private static void menuProgramarEvento(Scanner scanner) {
        System.out.print("Ingrese el ID del evento a programar: ");
        String idEv = scanner.nextLine();
        Evento evento = buscarEventoGlobal(idEv);

        if (evento == null) {
            System.out.println("Evento no encontrado.");
            return;
        }

        System.out.print("Ingrese fecha (YYYY-MM-DD): ");
        String fecha = scanner.nextLine();
        System.out.print("Ingrese hora (0 a 23): ");
        int hora = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese sede: ");
        String sede = scanner.nextLine();

        for (Evento e : calendarioGlobal) {
            if (e.estaProgramado() && !e.getId().equals(evento.getId())) {
                if (e.getFecha().equals(fecha) && e.getHora() == hora) {
                    if (e.getSede().equalsIgnoreCase(sede)) {
                        System.out.println("Operación rechazada: La sede ya tiene otro evento programado en esa fecha y hora.");
                        return;
                    }
                }
            }
        }

        evento.programar(fecha, hora, sede);
        System.out.println("¡Evento programado exitosamente!");
    }

    private static void menuRegistrarResultado(Scanner scanner) {
        System.out.print("Ingrese el ID del evento: ");
        String idEv = scanner.nextLine();
        Evento evento = buscarEventoGlobal(idEv);

        if (evento == null) {
            System.out.println("Evento no encontrado.");
            return;
        }

        if (!evento.estaProgramado()) {
            System.out.println("Operación rechazada: El evento no está programado.");
            return;
        }

        if (evento.isFinalizado()) {
            System.out.println("Operación rechazada: El evento ya se encuentra finalizado.");
            return;
        }

        System.out.print("Ingrese el resultado según el formato del deporte (ej. 3-1): ");
        String resultadoStr = scanner.nextLine();

        try {
            evento.registrarResultado(resultadoStr);
            System.out.println("Resultado registrado exitosamente.");

            for (Torneo t : torneos) {
                t.verificarFinTorneo();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Resultado inválido rechazado: " + e.getMessage());
        }
    }

    private static void menuConsultas() {
        System.out.println("\n   CALENDARIO GENERAL (ORDEN CRONOLÓGICO)  ");
        Collections.sort(calendarioGlobal);
        for (Evento ev : calendarioGlobal) {
            String estadoEv = ev.isFinalizado() ? "[Finalizado]" : (ev.estaProgramado() ? "[Programado]" : "[Sin programar]");
            System.out.printf("ID: %s | Fecha: %s | Hora: %02d:00 | Sede: %s %s\n",
                    ev.getId(), ev.getFecha().isEmpty() ? "N/D" : ev.getFecha(),
                    ev.getHora(), ev.getSede().isEmpty() ? "N/D" : ev.getSede(), estadoEv);
        }
    }

    private static void menuReportes() {
        System.out.println("\n    REPORTES FINANCIEROS Y PREMIOS ");
        double ingresosTotales = 0;
        double premiosTotales = 0;

        for (Torneo t : torneos) {
            double ingresoTorneo = t.getInscritos().size() * t.getDisciplina().getCostoInscripcion();
            ingresosTotales += ingresoTorneo;
            System.out.printf("Torneo: %s (%s) - Inscritos: %d - Ingresos: Q%.2f\n",
                    t.getNombre(), t.getDisciplina().getNombre(), t.getInscritos().size(), ingresoTorneo);

            if (t.getEstado() == Torneo.Estado.FINALIZADO && !t.getInscritos().isEmpty()) {
                double premio = 0.50 * t.getDisciplina().getCostoInscripcion() * t.getInscritos().size();
                premiosTotales += premio;
                System.out.printf(" -> ¡Torneo Finalizado! Premio entregado al 1er lugar: Q%.2f\n", premio);
            }
        }

        System.out.printf("\nIngresos Totales por Inscripciones: Q%.2f\n", ingresosTotales);
        System.out.printf("Premios Totales Entregados: Q%.2f\n", premiosTotales);
    }

    private static Torneo buscarTorneo(String codigo) {
        return torneos.stream().filter(t -> t.getCodigo().equalsIgnoreCase(codigo)).findFirst().orElse(null);
    }

    private static Participante buscarParticipante(String codigo) {
        return participantes.stream().filter(p -> p.getCodigo().equalsIgnoreCase(codigo)).findFirst().orElse(null);
    }

    private static Evento buscarEventoGlobal(String id) {
        return calendarioGlobal.stream().filter(e -> e.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    private static Disciplina obtenerDisciplinaPorOpcion(int opcion) {
        switch (opcion) {
            case 1: return new FutbolSala();
            case 2: return new Baloncesto(); 
            case 3: return new Voleibol();  
            case 4: return new Relevos();   
            default: return null;
        }
    }
}