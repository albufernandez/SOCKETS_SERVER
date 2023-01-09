package Sockets_Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Ej_Prueba_Server {
	
	//declaramos la variable del puerto y cliente
	private static final int PUERTO = 2000;
	private int cliente = 1;

	//Constructor
	public Ej_Prueba_Server() {

		try {
			
			// 1 Publicamos el puerto
			ServerSocket serverSocket = new ServerSocket(PUERTO);
			
			while (true) {
				
				System.out.println("A la espera de conexion");
				// 2 Esperamos peticiones (se queda a la espera)
				Socket socketCliente = serverSocket.accept();
				
				// 3 Atender cliente: envio + recepcion de datos
				// 3A Establecer flujos de entrada y salida
				DataInputStream flujoEntrada = new DataInputStream (socketCliente.getInputStream());
				DataOutputStream flujoSalida = new DataOutputStream (socketCliente.getOutputStream());
				
				// 3B Recibir informacion
				String nombreCliente = flujoEntrada.readUTF();
				
				// 3C Enviar informacion
				flujoSalida.writeUTF(nombreCliente + cliente + " Te has conectado con el servidor correctamente");
				
				cliente++;
				
				// 4 Cerrar el socket
				socketCliente.close();
				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {		
		
		//Creamos un objeto que ya nos lo inicia todo porque se inicia en el constructor
		Ej_Prueba_Server servidor = new Ej_Prueba_Server();

	}

}
