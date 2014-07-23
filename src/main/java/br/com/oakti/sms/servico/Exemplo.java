package br.com.oakti.sms.servico;


public class Exemplo {
  
  public static void main(String[] args) {
    
    // INICIA O SERVI�O E CONFIGURA O MODEM
    ServicoSMS sms = new ServicoSMS("COM8", 115200, "Motorola", "V3");
    
    // INFORMA SE MOSTRA O TEMPO DE ENVIO DAS MENSAGENS
    sms.setMostraLog(true);
    
    try {
      // INICIA O SERVI�O
      sms.iniciar();
      /*
      // ENVIA MENSAGEM 
      sms.enviarMensagem("+556184211429", "TESTE DE MENSAGEM OAK-TI");
      sms.enviarMensagem("+556184211429", "TESTE DE MENSAGEM OAK-TI");
      sms.enviarMensagem("+556184211429", "TESTE DE MENSAGEM OAK-TI");
      sms.enviarMensagem("+556184211429", "TESTE DE MENSAGEM OAK-TI");
      sms.enviarMensagem("+556184211429", "TESTE DE MENSAGEM OAK-TI");
      sms.enviarMensagem("+556184211429", "TESTE DE MENSAGEM OAK-TI");
      */
      

      // DEFINE LISTA DE DESTINAT�RIOS 
      String[] dests = new String[] {"+556184211429", "+556184211429", "+556184211429", "+556184211429", 
                                     "+556184211429", "+556184211429", "+556184211429", "+556184211429", 
                                     "+556184211429", "+556184211429", "+556184211429", "+556184211429" };
      
      // ENVIA MENSAGEM PARA MULTIPLOS DESTINATARIOS
      sms.enviarMensagem(dests, "TESTE DE MENSAGEM OAK-TI");

      // INTERROMPE O SERVI�O
      sms.parar();
    
    } catch(Exception ex) {
      ex.printStackTrace();
    }
  }
}
