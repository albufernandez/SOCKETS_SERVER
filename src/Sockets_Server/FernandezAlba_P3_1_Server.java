package Sockets_Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//programa que comprueba el nombre del fichero que el cliente da, en caso de coincidir
//con el nombre del fichero que el servidor tiene, le envia el contenido del fichero 
public class FernandezAlba_P3_1_Server extends Thread{

	//variables 
	private static final int PUERTO = 2000;
	private Socket socketCliente;
	private boolean existe = false;
	
	//constructor del hilo
	public FernandezAlba_P3_1_Server(Socket socketCliente) {
	this.socketCliente = socketCliente;
	}
	
	//main que creae inicia el hilo
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
				new FernandezAlba_P3_1_Server(socketCliente).start();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	
	}
	
	//run que realiza las tareas del hilo, envio y recepcion de datos
	public void run() {	
		System.out.println("Cliente conectado");
		
		try {
			
			//flujos de entrada y salida
			DataInputStream leer = new DataInputStream(socketCliente.getInputStream());
			DataOutputStream escribir = new DataOutputStream(socketCliente.getOutputStream());
			
			//guardamos el nombre del fichero y creamos un objeto de tipo fichero
			String fichero = leer.readUTF();
			File fi = new File(fichero);
			
			//si el fichero existe
			if (fi.exists()) { 
				existe = true;
				escribir.writeUTF("SI");
			}
			else escribir.writeUTF("NO");
			
			//si existe leemos el contenido y lo vamos enviando
			if (existe) {
				BufferedReader leerFi = new BufferedReader ( new FileReader (fi));
				String linea = leerFi.readLine();
				
				//mientras que la linea no este vacia enviamos el contenido
				while(linea!=null) {
					escribir.writeUTF(linea);
					linea = leerFi.readLine();
				}
				
				//cuando la linea este vacia enviamos el mensaje de aviso
				escribir.writeUTF("fin_archivo_fichero");
				System.out.println("Fichero enviado");
				
				
				//cerramos buffered y socket
				leerFi.close();
				socketCliente.close();
			}
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	
		
	}

}
