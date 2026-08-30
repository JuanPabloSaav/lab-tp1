import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;



class Lector {
  // args[0] deberia ser el limite de lectura del lector de logs
  // args[1] define si crear logs aleatorios(-r), fijos(-s) o ninguno con 
  public static void main(String[] args) { 
    int limiteLectura = args.indexOf("-l") != -1 ? Integer.parseInt(args[args.indexOf("-l") + 1]) : 1000;
    int logsOption = args.indexOf("-r") != -1 ? 1 : args.indexOf("-f") != -1 ? 2 : 0;
    int tamaño = args.indexOf("-t") != -1 ? Integer.parseInt(args[args.indexOf("-t") + 1]) : 10000;
    String rutaArchivo = "./logs.txt"; // deberiamos dejar que se cambie por argumento?
    if (logsOption == 1) {
      generarLogsAleatorios(rutaArchivo, tamaño);
    } else if (logsOption == 2) {
      generarLogsFijos(rutaArchivo, tamaño);
    } else {
      System.out.println("No se generaron logs, se usará un archivo de logs existente.");
    }
    // Se crea una tarea de análisis de logs con el límite de lectura especificado
    TareaAnalisis tarea = new TareaAnalisis(rutaArchivo, limiteLectura);
    tarea.fork(); 
  } 

  /**
   * Método que genera logs aleatorios y los escribe en un archivo.
   * @param rutaArchivo Ruta del archivo donde se escribirán los logs.
   * @param tamaño Número de logs a generar.
   */
  private static void generarLogsAleatorios(rutaArchivo, int tamaño) {
    BufferedWriter writer = getBufferedWriter(rutaArchivo);
    for (int i = 0; i < tamaño; i++) {
      // Genera un log aleatorio, con un 10% de probabilidad de ser un ERROR y un 90% de ser INFO
      String log = (Math.random() < 0.1) ? "ERROR: Log aleatorio " + i : "INFO: Log aleatorio " + i;
      writer.write(log);
      writer.newLine();
    }
    writer.close();
  }


  /**
   * Método que genera logs fijos y los escribe en un archivo.
   * @param rutaArchivo Ruta del archivo donde se escribirán los logs.
   * @param tamaño Número de logs a generar.
   */
  private static void generarLogsFijos(rutaArchivo, int tamaño) {
    BufferedWriter writer = getBufferedWriter(rutaArchivo);
    for (int i = 0; i < tamaño; i++) {
      // Genera un log fijo, cada 10 logs es un ERROR y los demas son INFO
      String log = (i % 10 == 0) ? "ERROR: Log fijo " + i : "INFO: Log fijo " + i;
      writer.write(log);
      writer.newLine();
    }
    writer.close();
  }

  /**
   * Método que obtiene un BufferedWriter para escribir en un archivo.
   * Si el archivo no existe, lo crea.
   * @param rutaArchivo Ruta del archivo donde se escribirá.
   * @return Un BufferedWriter para escribir en el archivo.
   * @throws IOException Si ocurre un error al crear o abrir el archivo.
   */
  private static BufferedWriter getBufferedWriter(String rutaArchivo) throws IOException {
    Path logPath = Paths.get(rutaArchivo);
    if(!Files.exists(logPath)){
      Files.createFile(logPath);
    }
    return Files.newBufferedWriter(logPath);
  }

}
