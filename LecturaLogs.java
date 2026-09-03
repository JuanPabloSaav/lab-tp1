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
  //TODO: cambiar logs[] al tipo de dato que se use para leer los logs desde un archivo
  public LecturaLogs(String[] logs, int inicio, int fin) {
    /*TODO: deberia validar que inicio y fin sean indices validos del arreglo logs
     * tambien deberia abrirse aca el archivo de logs y despues dejar que el metodo buscarErrores() haga la lectura de los logs desde el archivo
    */
    this.logs = logs;
    this.inicio = inicio;
    this.fin = fin;
  }
  
  /**
   * Método que busca errores en los logs dentro del rango especificado.
   * @return El número de errores encontrados en los logs.
   */
  //TODO: hacer que lea archivos y no un array
  public int buscarErrores() {
    int contador = 0;
    for (int i = inicio; i < fin; i++) {
      if (logs[i].contains("ERROR")) {
        contador++;
      }
    }
    return contador;
  }

  /**
   * Método que permite clonar un objeto de la clase LecturaLogs.
   * El chiste de esto es que no tenga que abrir el archivo de logs cada vez que se quiera hacer una lectura
   * @return Un nuevo objeto de la clase LecturaLogs con los mismos datos que el objeto original.
   */
  @Override
  public LecturaLogs clone() {
    try { 
      return (LecturaLogs) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }

  //TODO: hacer que clone() pueda recibir un rango de indices para clonar solo una parte del arreglo de logs
}
