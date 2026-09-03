import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Superviviente superviviente = new Superviviente(100, 30);
        Refugio refugio = new Refugio(superviviente);
        int ronda = 1;
        Scanner sc = new Scanner(System.in);

        while (superviviente.getSalud() > 0) {
            System.out.println();
            System.out.println();
            System.out.println("----- Ronda " + ronda + " -----");
            refugio.empezarRonda(ronda, sc);
            ronda++;
        }

        System.out.println("¡El superviviente ha muerto! Fin del juego.");
        sc.close();
    }
}
