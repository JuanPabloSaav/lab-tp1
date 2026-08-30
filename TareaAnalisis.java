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
    this.inicio = inicio;
    this.fin = fin;
    // Se crea un prototipo de LecturaLogs con el rango completo de logs
    this.lecturaLogs = lectorPrototipo.clonarConRango(inicio, fin);
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
