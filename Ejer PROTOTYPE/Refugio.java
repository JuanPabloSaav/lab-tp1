import java.util.Scanner;
public class Refugio {
    private Superviviente superviviente;
    private Zombie prototipoZombie;
    // Los enemigos pueden atacar desde 4 lados distintos: 
    // norte 1, sur 2, este 3, oeste 4
    private Zombie[] zombies;
    private int[] tiempoSinZombies;
    private String[] lados = {"Norte", "Sur", "Este", "Oeste"};
    private int nivelZombies = 1;

    public Refugio(Superviviente superviviente) {
        this.superviviente = superviviente;
        this.prototipoZombie = new Zombie(50, 5); // Zombie base
        this.zombies = new Zombie[4];
        this.tiempoSinZombies = new int[4];
        for (int i = 0; i < 4; i++) {
            tiempoSinZombies[i] = delayHastaProxZombie();
        }
    }

    public void empezarRonda(int ronda,Scanner sc) {
        boolean cambiarDecision = true;

        // Turno de zombies
        int danioZombies, danioTotal = 0;
        for (int i=0; i < 4; i++) {
            if (zombies[i] != null) {
                danioZombies = zombies[i].turno(superviviente.getSalud());
                superviviente.recibirAtaque(danioZombies);
                danioTotal += danioZombies;
            } else {
                tiempoSinZombies[i]--;
                if (tiempoSinZombies[i] <= 0) {
                    generarZombie(i);
                }
            }
        }
        System.out.println("Los zombies han atacado. Daño total recibido: " + danioTotal);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (superviviente.getSalud() <= 0) {
            return;
        }

        if (ronda % 5 == 0) {
            prototipoZombie.aumentoDeNivel(); // Sube el nivel de los zombies que se generarán
            nivelZombies += 1;
        }

        // Turno de superviviente
        mostrarEstado();
        System.out.println("Elige que hacer: ");
        System.out.println("0 para Esperar");
        System.out.println("Atacar (1-Norte, 2-Sur, 3-Este, 4-Oeste)");
        System.out.println("5 para usar granada (si tenes alguna)");
        
        while(cambiarDecision) {
            cambiarDecision = false;
            int decision = sc.nextInt();
            while(decision < 0 || decision > 5) {
                System.out.print("Decision inválida: ");
                decision = sc.nextInt();
            }

            if (decision == 0) {
                System.out.println("Decidiste esperar esta ronda.");
            }
            else if (decision > 0 && decision < 5) {
                ataqueZombie(decision);
            }
            else if (decision == 5) {
                if (superviviente.getCantGranadas() > 0) {
                    superviviente.usarGranada();
                    System.out.println("Usaste una granada. Selecciona el lado para lanzar la granada");
                    System.out.print("1-Norte, 2-Sur, 3-Este, 4-Oeste: ");
                    int deciGranada = sc.nextInt();
                    while(deciGranada < 1 || deciGranada > 4) {
                        System.out.print("Decision inválida: ");
                        deciGranada = sc.nextInt();
                    }
                    zombies[deciGranada - 1] = null;
                    tiempoSinZombies[deciGranada - 1] = delayHastaProxZombie();
                    System.out.println("El zombie en el lado " + lados[deciGranada - 1] + " ha sido eliminado con la granada.");
                } else {
                    System.out.print("No tienes granadas para usar. Toma otra decisión: ");
                    cambiarDecision = true;
                }
            }
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void ataqueZombie(int lado) {
        if (zombies[lado - 1] != null) {
            int saludZombie = zombies[lado - 1].recibirAtaque(superviviente.getDanio());
            System.out.println("Atacaste al zombie en el lado " + lados[lado - 1] + ". Salud restante del zombie: " + saludZombie);
            if (saludZombie <= 0) {
                System.out.println("¡Has matado al zombie en el lado " + lados[lado - 1] + "!");
                obtenerDrop();
                zombies[lado - 1] = null;
                tiempoSinZombies[lado - 1] = delayHastaProxZombie();
            }
        } else {
            System.out.println("No hay zombies en el lado " + lados[lado - 1] + ".");
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void obtenerDrop() {
        int i = (int) (Math.random() * 3);
        if (i == 0) {
            System.out.println("¡Has obtenido un drop de vida! Recuperas " + (20 * nivelZombies) + " de salud.");
            superviviente.recibirAtaque(-20 * nivelZombies);
        } else if (i == 1) {
            System.out.println("¡Has obtenido una mejora para tu arma! Aumento de daño en " + (5 * nivelZombies) + ".");
            superviviente.aumentoDanio(5 * nivelZombies);
        } else {
            System.out.println("¡Has obtenido una granada! Puedes usarla para eliminar un zombie");
            System.out.println("Pero cuidado, ya que no obtendras su drop.");
            superviviente.agregarGrandada();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void generarZombie(int lado) {
        zombies[lado] = prototipoZombie.clone();
    } 

    private void mostrarEstado() {
        System.out.println();
        System.out.println("----- Estado del refugio -----");
        System.out.println("Salud del superviviente: " + superviviente.getSalud());
        System.out.println("Cantidad de granadas del superviviente: " + superviviente.getCantGranadas());
        System.out.println();
        for (int i = 0; i < 4; i++) {
            if (zombies[i] != null) {
                System.out.println("Zombie en el lado " + lados[i] + ": Salud: " + zombies[i].getSalud() + ", Daño: " + zombies[i].getDanio());
            } else {
                System.out.println("No hay zombies en el lado " + lados[i] + ".");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    // Genera un número aleatorio que establece el tiempo de reaparición de un nuevo zombie
    private int delayHastaProxZombie() {
        return (int) (Math.random() * 6) + 2;
    }
}