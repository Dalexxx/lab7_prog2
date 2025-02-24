package atividade1;

public class Distancia implements Comparable<Distancia> {
    private String origem;
    private String destino;
    private double dist;

    public Distancia(String origem, String destino, double dist) {
        this.origem = origem;
        this.destino = destino;
        this.dist = dist;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public double getDist() {
        return dist;
    }

    @Override
    public int compareTo(Distancia outra) {
        return Double.compare(this.dist, outra.dist);
    }

    @Override
    public String toString() {
        return origem + " - " + destino + ": " + dist + " km";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Distancia distancia = (Distancia) obj;
        return Double.compare(distancia.dist, dist) == 0 &&
                origem.equals(distancia.origem) &&
                destino.equals(distancia.destino);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = origem.hashCode();
        result = 31 * result + destino.hashCode();
        temp = Double.doubleToLongBits(dist);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}

