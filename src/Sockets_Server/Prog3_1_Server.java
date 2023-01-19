package Sockets_Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//programa con un servidor escuchando que cliente se conecta y le devuelve el numero de cliente 
public class Prog3_1_Server {
	
	//declaramos la variable del puerto 
		private static final int PUERTO = 2000;
		private int cliente = 1;
		
		//constructor
		public Prog3_1_Server() {
			
			try {
				
				//publicamos el puerto
				ServerSocket serverSocket = new ServerSocket(PUERTO);
				
				//bucle que va a mantener el programa siempre encendido
				while (true) {
					System.out.println("A la espera de conexion");
					
					//esperamos peticiones (como el wait)
					Socket socketCliente = serverSocket.accept();
					
					//flujos de entrada y salida
					DataInputStream flujoEntrada = new DataInputStream (socketCliente.getInputStream());
					DataOutputStream flujoSalida = new DataOutputStream (socketCliente.getOutputStream());
					
					//informacion recibida
					String IP = flujoEntrada.readUTF();
					
					//enviamos informacion 
					flujoSalida.writeUTF("Eres el cliente nº " + cliente + " y tu IP es " + IP);
					
					//sumamos un cliente
					cliente++;
					
					//cerramos para que se pueda conectar otro cliente
					socketCliente.close();
					
				}
		
			} catch (IOException e) {
				System.out.println(e.getMessage());;
			}
		}
		
		public static void main(String[] args) {
			
			//creamos un objeto que incia el programa gracias al constructor
			Prog3_1_Server servidor = new Prog3_1_Server();

		}

}
