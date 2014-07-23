package br.com.oakti.sms.util;

import java.io.InputStream;
import java.io.OutputStream;

import java.util.Enumeration;
import java.util.Formatter;

import org.smslib.helper.CommPortIdentifier;
import org.smslib.helper.SerialPort;

public class TestarPortas {
  
  private static final String ERR_DISPOSITIVO_NAO_ENCONTRADO = "  nenhum modem encontrado";
  private final static Formatter formatador = new Formatter(System.out);
  static int bauds[] = { 9600, 14400, 19200, 28800, 33600, 38400, 56000, 57600, 115200 };

  private static Enumeration<CommPortIdentifier> getCleanPortIdentifiers() {
    return CommPortIdentifier.getPortIdentifiers();
  }

  public static void main(String[] args) {
    
    System.out.println("\nPesquisando...");
    
    Enumeration<CommPortIdentifier> portList = getCleanPortIdentifiers();
    while(portList.hasMoreElements()) {
      CommPortIdentifier portId = portList.nextElement();      
      if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {

        formatador.format("%nPORTA: %-5s%n", portId.getName());
        for (int baud : bauds) {
          formatador.format("       Testando %6d...", baud);
          SerialPort serialPort = null;
          try {
            InputStream inStream;
            OutputStream outStream;

            int c;
            String response;
            serialPort = portId.open("SMSLibCommTester", 1971);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);
            serialPort.setSerialPortParams(baud, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            
            inStream = serialPort.getInputStream();
            outStream = serialPort.getOutputStream();
            serialPort.enableReceiveTimeout(1000);
            c = inStream.read();
            
            while (c != -1) c = inStream.read();
            
            outStream.write('A');
            outStream.write('T');
            outStream.write('\r');
            Thread.sleep(1000);
            
            response = "";
            c = inStream.read();
            while (c != -1) {
              response += (char)c;
              c = inStream.read();
            }
            
            if (response.indexOf("OK") >= 0) {
              try {
                outStream.write('A');
                outStream.write('T');
                outStream.write('+');
                outStream.write('C');
                outStream.write('G');
                outStream.write('M');
                outStream.write('M');
                outStream.write('\r');
                response = "";
                c = inStream.read();
                while (c != -1) {
                  response += (char)c;
                  c = inStream.read();
                }
                
                System.out.println("  Encontrada: " + 
                                   response.replaceAll("\\s+OK\\s+", "").replaceAll("\n", "").replaceAll("\r", ""));
                
              } catch (Exception e) {
                System.out.println(ERR_DISPOSITIVO_NAO_ENCONTRADO);
              }
            } else {
              System.out.println(ERR_DISPOSITIVO_NAO_ENCONTRADO);
            }
          } catch (Exception e) {
            System.out.print(ERR_DISPOSITIVO_NAO_ENCONTRADO);
            Throwable cause = e;
            while (cause.getCause() != null) {
              cause = cause.getCause();
            }
            System.out.println(" (" + cause.getMessage() + ")");
          } finally {
            if (serialPort != null) {
              serialPort.close();
            }
          }
        }
      }
    }
  }
}
