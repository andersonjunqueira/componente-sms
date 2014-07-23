package br.com.oakti.sms.servico;

import java.util.ArrayList;
import java.util.List;

public class Exemplo {
  
  public static void main(String[] args) {
    
    // INICIA O SERVIÇO E CONFIGURA O MODEM
    ServicoSMS sms = new ServicoSMS("COM16", 115200, "Motorola", "V3");
    
    // INFORMA SE MOSTRA O TEMPO DE ENVIO DAS MENSAGENS
    sms.setMostraLog(true);
    
    try {
      // INICIA O SERVIÇO
      sms.iniciar();
      
      // ENVIA MENSAGEM 
      sms.enviarMensagem("+556196397999", "TESTE DE MENSAGEM OAK-TI");
      
      // ENVIA MENSAGEM 
      sms.enviarMensagem("+556196397999", "TESTE DE MENSAGEM OAK-TI");
      
      // ENVIA MENSAGEM 
      sms.enviarMensagem("+556196397999", "TESTE DE MENSAGEM OAK-TI");
      
      // ENVIA MENSAGEM 
      sms.enviarMensagem("+556196397999", "TESTE DE MENSAGEM OAK-TI");
      
      // DEFINE LISTA DE DESTINATÁRIOS 
      String[] dests = new String[] {"+556196397999", "+556196397999", "+556196397999", "+556196397999", 
                                     "+556196397999", "+556196397999", "+556196397999", "+556196397999", 
                                     "+556196397999", "+556196397999", "+556196397999", "+556196397999" };
      
      // ENVIA MENSAGEM PARA MULTIPLOS DESTINATARIOS
      sms.enviarMensagem(dests, "TESTE DE MENSAGEM OAK-TI");
      
      // INTERROMPE O SERVIÇO
      sms.parar();
    
    } catch(Exception ex) {
      ex.printStackTrace();
    }
  }
}
