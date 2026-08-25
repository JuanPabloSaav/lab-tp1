/**
 * Intento del patron de diseño Prototype para un sistema de lectura de logs.
 * La clase LecturaLogs implementa la interfaz Cloneable para permitir la clonación de objetos
 * y así evitar la creación de nuevas instancias de la clase cada vez que se necesite un objeto con los mismos datos.
 *
 */
class LecturaLogs implements Cloneable {
  
  private String[] logs;
  private int inicio;
  private int fin;

  /**
   * Constructor de la clase LecturaLogs.
   * @param logs Array de strings que contiene los logs a analizar.
   * @param inicio Indice de inicio del rango de logs a analizar.
   * @param fin Indice de fin del rango de logs a analizar.
   */
  public LecturaLogs(String[] logs, int inicio, int fin) {
    this.logs = logs;
    this.inicio = inicio;
    this.fin = fin;
  }
  
  /**
   * Método que busca errores en los logs dentro del rango especificado.
   * @return El número de errores encontrados en los logs.
   */
  public int buscarErrores() {
    int contador = 0;
    for (int i = inicio; i < fin; i++) {
      if (logs[i].contains("ERROR")) {
        contador++;
      }
    }
    return contador;
  }

  @Override
  public LecturaLogs clone() {
    try { 
      return (LecturaLogs) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
