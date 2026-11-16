import java.util.Objects;

public class Participante implements Comparable<Participante> {
    private String codigo;
    private String nombre;
    private String empresa;
    private int puntosTabla;
    private int diferencia;
    private int victorias;

    public Participante(String codigo, String nombre, String empresa) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.empresa = empresa;
        this.puntosTabla = 0;
        this.diferencia = 0;
        this.victorias = 0;
    }

    public void acumular(int puntos, int diferencia, boolean esVictoria) {
        this.puntosTabla += puntos;
        this.diferencia += diferencia;
        if (esVictoria) {
            this.victorias++;
        }
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getEmpresa() { return empresa; }
    public int getPuntosTabla() { return puntosTabla; }
    public int getDiferencia() { return diferencia; }
    public int getVictorias() { return victorias; }

    @Override
    public int compareTo(Participante otro) {
        if (this.puntosTabla != otro.puntosTabla) {
            return Integer.compare(otro.puntosTabla, this.puntosTabla); // Mayor a menor
        }
        if (this.diferencia != otro.diferencia) {
            return Integer.compare(otro.diferencia, this.diferencia); // Mayor a menor
        }
        if (this.victorias != otro.victorias) {
            return Integer.compare(otro.victorias, this.victorias); // Mayor a menor
        }
        return this.nombre.compareTo(otro.nombre); // Alfabético
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participante that = (Participante) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}