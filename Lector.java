class Lector {
  public static void main(String[] args) {

  }

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
}
