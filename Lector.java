import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.BufferedWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.io.IOException;
import java.util.Arrays;

class Lector {

  /*
   * -r: Genera logs aleatorios
   * -f: Genera logs fijos
   * -t: Tamaño de los logs a generar (default 10000)
   * -c: Cantidad de logs a generar (default 5)
   * -p: Ruta de la carpeta donde se generarán los logs (default ./logs/)
   * Si no se especifica ninguna opcion, se asume que los logs ya existen y se
   * procede a leerlos.
   */
  public static void main(String[] args) {

    List<String> argList = Arrays.asList(args);

    // argumentos para testear
    int logsOption = argList.indexOf("-r") != -1 ? 1 : argList.indexOf("-f") != -1 ? 2 : 0;
    int tamaño = (argList.indexOf("-t") != -1 && argList.indexOf("-t") + 1 < args.length)
        ? Integer.parseInt(args[argList.indexOf("-t") + 1])
        : 10000;
    int cantidad = (argList.indexOf("-c") != -1 && argList.indexOf("-c") + 1 < args.length)
        ? Integer.parseInt(args[argList.indexOf("-c") + 1])
        : 5;
    String rutaCarpetaLogs = (argList.indexOf("-p") != -1 && argList.indexOf("-p") + 1 < args.length)
        ? args[argList.indexOf("-p") + 1]
        : "./logs/";

    if (!verificarCarpetaLogs(rutaCarpetaLogs)) {
      System.out.println("La carpeta de logs no existe, creando carpeta...");
      try {
        Files.createDirectories(Paths.get(rutaCarpetaLogs));
        System.out.println("Carpeta recien creada. forzando generacion de logs...");
        logsOption = 2; // Forzamos la generación de logs fijos si la carpeta no existía
      } catch (Exception e) {
        System.out.println("Error al crear la carpeta de logs: " + e.getMessage());
        return;
      }
    }

    if (logsOption != 0) {
      System.out.println("Generando logs...");
      generarLogs(rutaCarpetaLogs, logsOption, tamaño, cantidad);
    }

    System.out.println("Cargando logs desde la carpeta: " + rutaCarpetaLogs);
    List<Path> archivosLogs = cargarLogs(rutaCarpetaLogs, cantidad);
    // Se crea una tarea de análisis de logs con el límite de lectura especificado
    TareaAnalisis tarea = new TareaAnalisis(archivosLogs, 0, archivosLogs.size());

    try (ForkJoinPool pool = new ForkJoinPool();) {
      long inicio = System.currentTimeMillis();
      int totalErrores = pool.invoke(tarea);
      long fin = System.currentTimeMillis();
      System.out.println("Total de errores encontrados: " + totalErrores);
      System.out.println("Tiempo transcurrido: " + (fin - inicio) + " ms");
    } catch (Exception e) {
      System.out.println("Error al procesar los logs: " + e.getMessage());
      return;
    }
  }

  private static boolean verificarCarpetaLogs(String rutaCarpeta) {
    Path path = Paths.get(rutaCarpeta);
    return (Files.exists(path) && Files.isDirectory(path)) ? true : false;
  }

  private static List<Path> cargarLogs(String rutaCarpetaLogs, int cantidad) {
    List<Path> archivosLogs = new ArrayList<>();
    for (int i = 0; i < cantidad; i++) {
      String rutaArchivo = rutaCarpetaLogs + "log_" + i + ".txt";
      Path path = Paths.get(rutaArchivo);
      try {
        // si por alguna razon no existe el archivo de log, se crea uno default
        if (!Files.exists(path)) {
          System.out.println("El archivo de log " + rutaArchivo + "no existe. Creando archivo de log default");
          Files.createFile(path);
          escribirLogFijo(Files.newBufferedWriter(path), 10000); // Escribe un log de 10000 lineas
        }
        archivosLogs.add(path);
      } catch (Exception e) {
        System.out.println("Error al leer el archivo de logs: " + e.getMessage());
        return null;
      }
    }
    return archivosLogs;
  }

  private static void generarLogs(String rutaCarpetaLogs, int logsOption, int tamaño, int cantidad) {
    for (int i = 0; i < cantidad; i++) {
      String rutaArchivo = rutaCarpetaLogs + "log_" + i + ".txt";
      try (BufferedWriter archivo = Files.newBufferedWriter(Paths.get(rutaArchivo))) {

        if (logsOption == 1) {
          escribirLogAleatorios(archivo, tamaño);
        } else if (logsOption == 2) {
          escribirLogFijo(archivo, tamaño);
        }

      } catch (Exception e) {
        System.out.println("Error al escribir el archivo de logs: " + e.getMessage());
      }
    }
  }

  
  private static void escribirLogAleatorios(BufferedWriter archivo, int tamaño) throws IOException {
    for (int i = 0; i < tamaño; i++) {
      // Genera un log aleatorio, con un 10% de probabilidad de ser un ERROR y un 90%
      // de ser INFO
      String log = (Math.random() < 0.1) ? "ERROR: Log aleatorio " + i : "INFO: Log aleatorio " + i;
      archivo.write(log);
      archivo.newLine();
    }
  }

  private static void escribirLogFijo(BufferedWriter archivo, int tamaño) throws IOException {
    for (int i = 0; i < tamaño; i++) {
      // Genera un log fijo, cada 10 logs es un ERROR y los demas son INFO
      String log = (i % 10 == 0) ? "ERROR: Log fijo " + i : "INFO: Log fijo " + i;
      archivo.write(log);
      archivo.newLine();
    }
  }

}
