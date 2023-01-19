package Sockets_Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//programa que simula un juego, en el servidor se tiene un numero aleatorio
//y se comunica con el cliente que intenta adivinarlo
public class Prog3_5_Server extends Thread{
	
	//variables 
	private static final int PUERTO = 2000;
	private Socket socketCliente;
	private  int aleatorio;
	
	//constructor del hilo
	public Prog3_5_Server(Socket socketCliente) {
		this.socketCliente = socketCliente;
	}
	
	
	//main crea e inicia el hilo
	public static void main(String[] args) {
		
		try {
			// 1 publicamos el puerto
			ServerSocket serverSocket = new ServerSocket(PUERTO);	
			System.out.println("Esperando conexiones");
			//bucle para que el servidor siempre este activo
			while(true) {
				
				// 2 quedamos a la espera 
				Socket socketCliente = serverSocket.accept();
				
				// 3 creamos el hilo, envio y recepciond de datos
				new Prog3_5_Server(socketCliente).start();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	//run que realiza las tareas del hilo, envio y recepcion de datos
	public void run() {
		
		try {
			aleatorio = (int)(Math.random()*100+1);

			System.out.println("Cliente conectado");
			System.out.println("El numero secreto es: " + aleatorio);
			
			// 3A flujos de entrada y salida
			DataInputStream leer = new DataInputStream(socketCliente.getInputStream());
			DataOutputStream escribir = new DataOutputStream(socketCliente.getOutputStream());
		
			// 3B recibimos el numero
			int numero = leer.readInt();
			
			// 3C Recibimos info (comprobamos si ese numero es el correcto)
			while(numero!=aleatorio) {
				
				//le pasamos al cliente si es mayor o inferior
				if (numero < aleatorio) escribir.writeUTF("+");
				
				else escribir.writeUTF("-");
				
				//volvemos a leer otro numero
				numero = leer.readInt();	
			}
			
			//si el numero es igual que el aleatorio se termina el programa
			escribir.writeUTF("=");
			
			//cerramos el socket 
			socketCliente.close();
		
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	

}
