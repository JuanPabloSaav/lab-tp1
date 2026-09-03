public class Zombie {
    private int salud;
    private int danio;

    public Zombie(int salud, int danio) {
        this.salud = salud;
        this.danio = danio;
    }

    public Zombie(Zombie oriZombie) {
        this.salud = oriZombie.salud;
        this.danio = oriZombie.danio;
    }

    public int turno(int saludJugador) {
        if (saludJugador > 0) {
            return danio;
        } else {
            return 0;
        }
    }

    public void aumentoDeNivel() {
        this.salud += 10;
        this.danio += 5;
    }

    public int recibirAtaque(int danio) {
        salud -= danio;
        if (salud < 0) {
            salud = 0;
        }
        return salud;
    }

    public int getDanio() {
        return danio;
    }

    public int getSalud() {
        return salud;
    }

    public Zombie clone() {
        return new Zombie(this);
    }
}
