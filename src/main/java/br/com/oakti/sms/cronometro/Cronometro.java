package br.com.oakti.sms.cronometro;

public class Cronometro {
  
  private static long inicial;
  
  public static void iniciar() {
    inicial = System.currentTimeMillis();
  }
  
  public static void iniciar(String texto) {
    System.out.println(texto);
    iniciar();
  }
  
  public static long parar(boolean mostrar) {
    long decorrido = System.currentTimeMillis() - inicial;
    
    if(mostrar)
      System.out.println("Tempo Decorrido: " + decorrido + "\n");
    
    return decorrido;
  }
  
  private Cronometro() {
  }
}
