public class Superviviente {
    private int salud;
    private int danio;
    private int cantGranadas;

    public Superviviente(int salud, int danio) {
        this.salud = salud;
        this.danio = danio;
        cantGranadas = 1; // Inicialmente tenes 1 granada
    }

    public int getSalud() {
        return salud;
    }

    public int getDanio() {
        return danio;
    }

    public int getCantGranadas() {
        return cantGranadas;
    }

    public void agregarGrandada() {
        cantGranadas++;
    }

    public void usarGranada() {
        if (cantGranadas > 0) {
            cantGranadas--;
        }
    }

    public void aumentoDanio(int incremento) {
        danio += incremento;
    }

    public void recibirAtaque(int danio) {
        salud -= danio;
        if (salud < 0) {
            salud = 0;
        }
    }
}