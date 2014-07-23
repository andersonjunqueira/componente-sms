package br.com.oakti.sms.servico;

import br.com.oakti.sms.cronometro.Cronometro;

import java.io.IOException;

import java.util.Collection;

import org.smslib.GatewayException;
import org.smslib.OutboundMessage;
import org.smslib.SMSLibException;
import org.smslib.Service;
import org.smslib.TimeoutException;
import org.smslib.modem.SerialModemGateway;

public class ServicoSMS {
  
  private String porta;
  private int baud;
  private String fabricante;
  private String modelo;
  private boolean mostraLog;
  
  private Service srv;

  public ServicoSMS() {
  }

  public ServicoSMS(String porta, int baud, String fabricante, String modelo) {
    setPorta(porta);
    setBaud(baud);
    setFabricante(fabricante);
    setModelo(modelo);
  }
  
  public long enviarMensagem(Collection<String> destinatarios, String mensagem) throws TimeoutException, 
    GatewayException, IOException, InterruptedException {
    String[] dests = new String[destinatarios.size()];
    destinatarios.toArray(dests);
    return enviarMensagem(dests, mensagem);
  }
  
  public long enviarMensagem(String[] destinatarios, String mensagem) throws TimeoutException, 
    GatewayException, IOException, InterruptedException {
  
    if(srv == null) throw new TimeoutException("É necessário iniciar o serviço");
    
    String grupo = "envio";
    srv.createGroup(grupo);
    for(String destinatario : destinatarios)
      srv.addToGroup(grupo, destinatario);

    if(mostraLog)    
      Cronometro.iniciar("Enviando mensagens ...");
    else
      Cronometro.iniciar();
    
    OutboundMessage msg = new OutboundMessage(grupo, mensagem);
    srv.sendMessage(msg);
    
    srv.removeGroup(grupo);    
    
    return Cronometro.parar(mostraLog);  
  }
  
  public long enviarMensagem(String destinatario, String mensagem) throws TimeoutException, GatewayException, 
    IOException, InterruptedException {
  
    if(srv == null) throw new TimeoutException("É necessário iniciar o serviço");
    
    if(mostraLog)    
      Cronometro.iniciar("Enviando mensagem ...");
    else
      Cronometro.iniciar();
    
    OutboundMessage msg = new OutboundMessage(destinatario, mensagem);
    srv.sendMessage(msg);

    return Cronometro.parar(mostraLog);  
  }
  
  public long parar() throws SMSLibException, TimeoutException, GatewayException, IOException, InterruptedException {
    
    if(srv == null) return 0;
    
    if(mostraLog)    
      Cronometro.iniciar("Interrompendo o serviço SMS ...");
    else
      Cronometro.iniciar();

    srv.stopService();
    
    return Cronometro.parar(mostraLog);    
  }
  
  public long iniciar() throws GatewayException, SMSLibException, TimeoutException, IOException, InterruptedException {
    
    if(srv != null) {
      parar();
    }
    
    SerialModemGateway gateway = new SerialModemGateway("modem.com1", 
                                                        getPorta(), getBaud(), getFabricante(), getModelo());
    gateway.setInbound(false);
    gateway.setOutbound(true);
    gateway.setSimPin("0000");

    if(mostraLog)    
      Cronometro.iniciar("Iniciando o serviço SMS ...");
    else
      Cronometro.iniciar();
    
    srv = new Service();
    srv.addGateway(gateway);
    srv.startService();
    
    return Cronometro.parar(mostraLog);    
  }
  
  public void setPorta(String porta) {
    this.porta = porta;
  }

  public String getPorta() {
    return porta;
  }

  public void setBaud(int baud) {
    this.baud = baud;
  }

  public int getBaud() {
    return baud;
  }

  public void setFabricante(String fabricante) {
    this.fabricante = fabricante;
  }

  public String getFabricante() {
    return fabricante;
  }

  public void setModelo(String modelo) {
    this.modelo = modelo;
  }

  public String getModelo() {
    return modelo;
  }

  public void setMostraLog(boolean mostraLog) {
    this.mostraLog = mostraLog;
  }

  public boolean isMostraLog() {
    return mostraLog;
  }
}
