import java.util.concurrent.RecursiveTask;

public class TareaAnalisis extends RecursiveTask<Integer>  {
  private static final int limite = 1000;

  private String[] logs;
  private int inicio;
  private int fin;
  private LecturaLogs lecturaLogs;

  public TareaAnalisis(String[] logs, int inicio, int fin) {
    this.logs = logs;
    this.inicio = inicio;
    this.fin = fin;
    this.lecturaLogs = new LecturaLogs(logs, inicio, fin);
  }

  @Override
  protected Integer compute() {
    if (fin - inicio <= limite) {
      return lecturaLogs.buscarErrores();
    } else {
      int mid = (inicio + fin) / 2;
      TareaAnalisis tareaIzquierda = new TareaAnalisis(logs, inicio, mid);
      TareaAnalisis tareaDerecha = new TareaAnalisis(logs, mid, fin);
      tareaDerecha.fork(); // Ejecuta la tarea derecha en un hilo separado
      // Ejecuta la tarea izquierda en el hilo actual y espera a que la tarea derecha termine
      return tareaIzquierda.compute() + tareaDerecha.join();
    }
  }
}
