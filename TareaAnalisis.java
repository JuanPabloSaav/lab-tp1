import java.util.concurrent.RecursiveTask;
import java.nio.file.Path;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;

public class TareaAnalisis extends RecursiveTask<Integer> {
  private List<Path> logs;
  private int inicio;
  private int fin;

  public TareaAnalisis(List<Path> archivosLogs, int inicio, int fin) {
    this.logs = archivosLogs;
    this.inicio = inicio;
    this.fin = fin;
  }

  private int buscarErrores(Path log) {
    int contadorErrores = 0;
    try (BufferedReader br = Files.newBufferedReader(log)) {
      String linea;
      while ((linea = br.readLine()) != null) {
        if (linea.contains("ERROR")) {
          contadorErrores++;
        }
      }
    } catch (Exception e) {
      System.out.println("Error al leer el archivo de log: " + log.toString() + " - " + e.getMessage());
    }
    System.out.println(
        Thread.currentThread().getName() + " encontró " + contadorErrores + " errores en el log: " + log.toString());
    return contadorErrores;
  }

  @Override
  protected Integer compute() {
    System.out.println(Thread.currentThread().getName() + " procesando los logs desde " + inicio + " hasta " + fin);
    int tamaño = fin - inicio;
    if (tamaño <= 1) {
      System.out.println("Procesando log individual: " + logs.get(inicio).toString());
      Path log = logs.get(inicio);
      return buscarErrores(log);
    } else {
      System.out.println(Thread.currentThread().getName() + " dividiendo logs desde " + inicio + " hasta " + fin);
      // Dividir la tarea en dos sub-tareas
      int mitad = (inicio + fin) / 2;
      TareaAnalisis subTarea1 = new TareaAnalisis(logs, inicio, mitad);
      TareaAnalisis subTarea2 = new TareaAnalisis(logs, mitad, fin);

      // Ejecutar las sub-tareas en paralelo
      subTarea1.fork();
      int resultadoSubTarea2 = subTarea2.compute();
      int resultadoSubTarea1 = subTarea1.join();

      // Combinar los resultados de las sub-tareas
      return resultadoSubTarea1 + resultadoSubTarea2;
    }
  }
}
