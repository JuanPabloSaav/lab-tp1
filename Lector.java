import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;



class Lector {
  // args[0] deberia ser el limite de lectura del lector de logs
  // args[1] define si crear logs aleatorios(-r), fijos(-s) o ninguno con 
  public static void main(String[] args) { 
    int limiteLectura = args.indexOf("-l") != -1 ? Integer.parseInt(args[args.indexOf("-l") + 1]) : 1000;
    int logsOption = args.indexOf("-r") != -1 ? 1 : args.indexOf("-s") != -1 ? 2 : 0; 
    
    if (logsOption == 1) {
      generarLogs(tamaño);
    } else if (logsOption == 2) {
      generarLogsFijos(tamaño);
    } else {
      System.out.println("No se generaron logs, se usará un archivo de logs existente.");
    }

    TareaAnalisis tarea = new TareaAnalisis(rutaArchivo, limiteLectura);
    tarea.fork(); 
  }

  private static void generarLogs(){
    Path logPath = Paths.get("./");
  }
}
