package Sockets_Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Prog3_4_Server {
	
	//variables
	private static int PUERTO_ENVIAR = 2000;
	private static int PUERTO_RECIBIR = 2001;
	private static final String HOST = "localhost";
	private boolean salir = false;
	private int numero, aleatorio;
	private String signo;
	
	//constructor 
	public Prog3_4_Server() {
		
		try {
			//manejadores de entrada y salida
			DatagramSocket enviar = new DatagramSocket();
			DatagramSocket recibir = new DatagramSocket(PUERTO_RECIBIR);
			
			while(salir!=true) {
				
				//creamos el paquete para recibir la info
				byte[] mensaje_recibir = new byte[1000];
				DatagramPacket paquete_recibir = new DatagramPacket (mensaje_recibir, mensaje_recibir.length);
				aleatorio = (int)(Math.random()*100+1);
				System.out.println("A la espera de conexion");
				System.out.println("El numero secreto es: " + aleatorio);
				
				//recibimos el numero
				recibir.receive(paquete_recibir);
				//le asiganamos al String datos ese numero que hemos recibido
				String datos = new String(paquete_recibir.getData(),0,paquete_recibir.getLength());
				System.out.println("El numero recibido del cliente es:  " + datos);
				numero = Integer.parseInt(datos);
				
				
				//si el numero es distinto de aleatorio
				while (numero!=aleatorio) {
				
				//le pasamos al cliente si es mayor o inferior
				if (numero < aleatorio) signo = "mayor";
				
				else signo ="menor";;
				
				//Enviamos el signo para que el usuario escoja otro numero
				byte[] mensaje_enviar = signo.getBytes();
				DatagramPacket paquete_enviar = new DatagramPacket(mensaje_enviar, mensaje_enviar.length, InetAddress.getByName(HOST), PUERTO_ENVIAR);
				enviar.send(paquete_enviar);
				
				//volvemos a recibir otro numero
				recibir.receive(paquete_recibir);
				//le asiganamos al String datos ese numero que hemos recibido
				datos = new String(paquete_recibir.getData(),0,paquete_recibir.getLength());
				System.out.println("El numero recibido del cliente es:  " + datos);
				numero = Integer.parseInt(datos);
				}
				
				//si el numero es igual que el aleatorio se envia al cliente y se termina el programa
				signo = "igual";
				byte[] mensaje_enviar = signo.getBytes();
				DatagramPacket paquete_enviar = new DatagramPacket(mensaje_enviar, mensaje_enviar.length, InetAddress.getByName(HOST), PUERTO_ENVIAR);
				enviar.send(paquete_enviar);
				
				System.out.println("Se finaliza el programa porque el usuario ha encontrado el numero");
				salir = true;
				}
				//cerramos manejadores 
				enviar.close();
				recibir.close();
			
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	public static void main(String[] args) {
		Prog3_4_Server server = new Prog3_4_Server();

	}

}
