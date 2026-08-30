import java.util.concurrent.RecursiveTask;

public class TareaAnalisis extends RecursiveTask<Integer>  {
  private int limiteLectura;
  private int inicio;
  private int fin;
  private LecturaLogs lecturaLogs;

  /**
   * Constructor de la clase TareaAnalisis.
   * @param logs Array de strings que contiene los logs a analizar.
   * @param inicio Indice de inicio del rango de logs a analizar.
   * @param fin Indice de fin del rango de logs a analizar.
   */
  public TareaAnalisis(String rutaArchivo, int limiteLectura) {
    Path path = Paths.get(rutaArchivo);
    /* INFO: horrible problema: si se intenta paralelizar la lectura de un solo archivo de logs,
     * se tendria que saber cuanta lineas tiene el archivo para poder dividirlo por rangos pero
     * no se puede saber cuantas lineas tiene un archivo sin leerlo completo. Se puede
     * resolver leyendo el tamaño en bytes y diviendolo, pero hay que resolver el problema de que
     * un hilo obtenga justo un byte que esta a la mitad de una linea y no pueda leerla completa.
     * Podemos solucionar eso pero para algo tan simple como este tp no creo que haga falta.
     * Otra solucion es cambiar el enfoque y que se creen muchos archivos de logs y dividirlos por los hilos,
     * asi cada hilo lee un archivo diferente y se puede usar el BufferedReader para leerlo completo sin problemas.
     */
    this.limiteLectura = limiteLectura;  
    this.inicio = inicio;
    this.fin = fin;
    // Se crea un prototipo de LecturaLogs con el rango completo de logs
    this.lecturaLogs = lectorPrototipo.clonarConRango();
  }

  /**
   * Método que realiza el análisis de los logs en paralelo utilizando el framework Fork/Join.
   * Si el tamaño del rango de logs es menor o igual al límite, 
   * se realiza la búsqueda de errores directamente.
   * Si el tamaño del rango de logs es mayor al límite, 
   * se divide el rango en dos y se crean dos tareas para analizarlas en paralelo.
   * @return El número total de errores encontrados en los logs.
   */
  @Override
  protected Integer compute() {
    int tamaño = fin - inicio;
    String threadName = Thread.currentThread().getName();
    System.out.println("["+ threadName +"]Entrando con tamaño de logs: " + tamaño);
    if (tamaño <= limite) {
      System.out.println("["+ threadName + "]Tamaño de logs dentro del límite, buscando errores...");
      return lecturaLogs.buscarErrores();
    } else {
      System.out.println("["+ threadName + "]Excedido el límite, dividiendo la tarea en dos sub-tareas...");
      int mid = (inicio + fin) / 2;
      TareaAnalisis tareaIzquierda = new TareaAnalisis(logs, inicio, mid);
      TareaAnalisis tareaDerecha = new TareaAnalisis(logs, mid, fin);
      tareaDerecha.fork(); // Ejecuta la tarea derecha en un hilo separado
      // Ejecuta la tarea izquierda en el hilo actual y espera a que la tarea derecha termine
      return tareaIzquierda.compute() + tareaDerecha.join();
    }
  }
}
