class Lector {
  // deberia llegar args[0] tamaño del arreglo
  public static void main(String[] args) {
    int tamaño = Integer.parseInt(args[0]);
    String[] logs = generarLogsFijos(tamaño);
 
     
  }


  /**
   * Genera un arreglo de logs aleatorios, donde aproximadamente el 10% de los logs son errores y el 90% son logs de información.
   * @param tamaño El tamaño del arreglo de logs a generar.
   * @return Un arreglo de strings que contiene los logs generados.
   */
  public static String[] generarLogs(int tamaño) {
    String[] logs = new String[tamaño];
    for (int i = 0; i < tamaño; i++) {
      if (Math.random() < 0.1) {
        logs[i] = "ERROR: Log de error " + i;
      } else {
        logs[i] = "INFO: Log de información " + i;
      }
    }
    return logs;
  }

  /**
   * Genera un arreglo de logs con un patrón fijo, donde cada décimo log es un error 
   * y los demás son logs de información.
   * Con un tamaño de array de 100, se generarán 10 logs de error y 90 logs de información.
   * @param tamaño El tamaño del arreglo de logs a generar.
   * @return Un arreglo de strings que contiene los logs generados.
   */
  public static String[] generarLogsFijos(int tamaño) {
    String[] logs = new String[tamaño];
    for (int i = 0; i < tamaño; i++) {
      if (i % 10 == 0) {
        logs[i] = "ERROR: Log de error " + i;
      } else {
        logs[i] = "INFO: Log de información " + i;
      }
    }
    return logs;
  }
}
