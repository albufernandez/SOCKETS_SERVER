package Sockets_Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Prog3_2_Server {
	
	//variables
	private static final int PUERTO=2000;
	private int numero;
	
	//constructor
	public Prog3_2_Server() {
		
		try {
			
			//publicamos el puerto
			ServerSocket serverSocket = new ServerSocket(PUERTO);
			System.out.println("A la espera de conexion");
			
			//bucle para que el servidor siempre este activo
			while(true) {
				int aleatorio = (int)(Math.random()*100+1);
				
				System.out.println("Cliente conectado");
				System.out.println("El numero secreto es: " + aleatorio);
				//quedamos a la espera 
				Socket socketCliente = serverSocket.accept();
				
				//flujos de entrada y salida
				DataInputStream flujoEntrada = new DataInputStream(socketCliente.getInputStream());
				DataOutputStream flujoSalida = new DataOutputStream(socketCliente.getOutputStream());
			
				//cogemos el numero que escribe el cliente
				numero = flujoEntrada.readInt();
				
				//si el numero es distinto de aleatorio
				while (numero != aleatorio) {
				
				//le pasamos al cliente si es mayor o inferior
				if (numero < aleatorio) flujoSalida.writeUTF("+");
				
				else flujoSalida.writeUTF("-");
				
				//volvemos a leer otro numero
				numero = flujoEntrada.readInt();
				}
				
				//si el numero es igual que el aleatorio se termina el programa
				flujoSalida.writeUTF("=");
				
				//cerramos el socket 
				socketCliente.close();
			}
			
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
	

	public static void main(String[] args) {
		
		Prog3_2_Server server = new Prog3_2_Server();

	}

}
