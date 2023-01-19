package Sockets_Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Ej_Prueba_UDP_Server {
	
	//Solo declaramos este puerto porque en este prog solo se envia info en una direccion
	private static final int PUERTO = 1500;
	
	//constructor
	//el servidor espera a recibir 3 paquetes con mensajes y finaliza
	public Ej_Prueba_UDP_Server() {
		
		int contador = 0;
	
		try {
			//Iniciamos el manejador publicando el puerto (recibe porque le pasamos el puerto)
			DatagramSocket recibir = new DatagramSocket(PUERTO);
			
			//creamos el paquete para recibir la info
			byte[] mensaje = new byte[1000];
			DatagramPacket paquete = new DatagramPacket (mensaje, mensaje.length);
			System.out.println("Esperando mensajes...");
			
			//recibimos los paquetes con paquetes y los mostramos
			while (contador < 3) {
				recibir.receive(paquete);
				String datos = new String(paquete.getData(),0,paquete.getLength());
				System.out.println("Mensaje recibido: " + datos);
				contador++;
			}
			
			//cerramos el manejador
			recibir.close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	public static void main(String[] args) {
		Ej_Prueba_UDP_Server server = new Ej_Prueba_UDP_Server();

	}

}
