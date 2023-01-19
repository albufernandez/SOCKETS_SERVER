package Sockets_Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

//programa con un servidor que esta esperando que se conecten clientes y les envia el numero de cliente
public class Prog3_3_Server {

	//variables
	private static int PUERTO_ENVIAR = 2000;
	private static int PUERTO_RECIBIR = 2001;
	private static final String HOST = "localhost";
	private int cliente = 1;
	private boolean salir = false;
	
	//constructor
	public Prog3_3_Server() {
		
		try {
			
			//manejadores de envio y recepcion de info
			DatagramSocket enviar = new DatagramSocket();
			DatagramSocket recibir = new DatagramSocket(PUERTO_RECIBIR);
			
			while(salir!=true) {
		
			//creamos el paquete para recibir la info
			byte[] mensaje_recibir = new byte[1000];
			DatagramPacket paquete_recibir = new DatagramPacket (mensaje_recibir, mensaje_recibir.length);
			System.out.println("Esperando conexion");
			
			//recibimos la IP
			recibir.receive(paquete_recibir);
			//le asiganamos al String datos esa IP que hemos recibido
			String datos = new String(paquete_recibir.getData(),0,paquete_recibir.getLength());
			System.out.println("IP recibida del cliente: " + cliente + " y es la IP " + datos);
			
			//creamos el paquete para enviar la info
			String textoEnviar = "Eres el cliente nº: " + cliente + " y tu IP es: " + datos;
			byte[] mensaje_enviar = textoEnviar.getBytes();
			DatagramPacket paquete_enviar = new DatagramPacket(mensaje_enviar, mensaje_enviar.length, InetAddress.getByName(HOST), PUERTO_ENVIAR);
			
			//enviamos el paquete 
			enviar.send(paquete_enviar);
			
			//sumamos cliente
			cliente++;
			
			}
			//cerramos manejadores 
			enviar.close();
			recibir.close();
			
		} catch (Exception e) {		
			System.out.println(e.getMessage());
		}	
		
	}
	
	
	public static void main(String[] args) {	
		Prog3_3_Server server = new Prog3_3_Server();
	}

}
