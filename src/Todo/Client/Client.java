package Todo.Client;
import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;

public class Client implements ObservableLobby, ObservableGame {
	// el cliente se conecta al servidor
	// Creación de un singleton.
	private static Client instance = null;
	private static ArrayList<ObserverLobby> lst1 = new ArrayList<ObserverLobby>();
	private static ArrayList<ObserverGame> lst2 = new ArrayList<ObserverGame>();
	

	private Socket socket;

	private PrintStream output;
	private Scanner input;
	
	private static boolean Nick = false;

	//Si la instancia es nula, cree una nueva instancia y devuélvala. Si la instancia no es nula, devolver la instancia existente
	//@return La instancia de la clase Cliente.

	public static Client getInstance(){ //constructor del cliente
		if(instance == null){
			instance = new Client();
		}
		return instance;
	}

	//Crea un socket, crea un hilo para escuchar los mensajes del servidor y crea un PrintStream para enviar mensajes al servidor.

	public void startClient() { //iniciodelcliente
		//crea un hilo pa escuchar sus acciones
		try {//intenta conectarse al servidor
			socket = new Socket("localhost", 12345);//intenta conectarse por el socket (puerto mismo)
			//veo la ip y se la paso a la otra persona7/persona: coloca el ip en el localhost
		} catch (IOException e) {
			System.out.println("No a sido posible conectar con el servidor");
			return;
		}
		input = new Scanner(System.in);//input: el que recibe

		if (socket.isConnected()) {//si se conecto inicia el hilo

			threadMessages(socket);  //atento a los mensajes del servidor

			try {
				output = new PrintStream(socket.getOutputStream()); //output: envio mensajes al servidor
			} catch (IOException e) {
				System.out.println("No se ha podido establecer la conexión");
				return;
			}
		}
	}

	//Crea un hilo que escucha los mensajes del servidor y notifica a los observadores.
	// @param server El servidor al que conectarse.

	private static void threadMessages(Socket server) { //el hilo que escucha todo el servidor
		Scanner scanner;
		try {
			scanner = new Scanner(server.getInputStream()); //guarda todo lo que el servidor envia
		} catch (IOException e2) {
			System.out.println("Imposible conectarse con el servidor");
			e2.printStackTrace();
			return;
		}
		//aqui hace todo lo que tiene que hacer
		Thread messageThread = new Thread() {
					
			@Override
			public void run() {
				
				try{
					while (scanner.hasNextLine()) {
						String msg = scanner.nextLine(); //recibe el mensaje del server
						
						ListIterator<ObserverLobby> li = lst1.listIterator();
						if (msg.compareTo("Valid Nickname") == 0) {
							Nick = true;
							System.out.println("Nickname valido");
							while(li.hasNext()) {
								
								li.next().receivedNicknameAvaiable();
							}
						} else if (msg.compareTo("Invalid Nickname") == 0) {
							System.out.println("Nickname invalido");
							
							while(li.hasNext()){
								
								li.next().receivedNicknameUnavaiable();
							}
							
						
						} else if (msg.contains("Game Start")) {
							System.out.println("Game Start");
							
							while(li.hasNext()){
								
								li.next().receivedGameStart();
							}

						} else {
							ListIterator<ObserverGame> listIt2 = lst2.listIterator();
							
							while (listIt2.hasNext()) {
								listIt2.next().receivedPlay(msg);
							}
						}
					}
				} catch(Exception e) {
					System.out.println("Error en el thread");
					e.printStackTrace();
					try { //cierra la conexion si hay un error
						server.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}	
			}					
		};
		
		messageThread.start(); //inicia el hilo
	}

	//Crea un nuevo objeto PrintStream, que es una clase de Java que nos permite enviar datos a través de un socket, y luego
	// envía el mensaje a través del socket.
	// param message El mensaje que se enviará al servidor.

	public void sendMessage(String message) { //enviarmensaje al server

		try {
			output = new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("No se ha podido establecer la conexión");
			return;
		}
		
		output.println(message);
	}

	//Cierra el socket, los flujos de entrada y salida e imprime un mensaje en la consola

	public void closeClient() {  //cerra el cliente
		//TODO se cierra
		System.out.println("Cerrando cliente");
		
		output.close();
		input.close();
		try {
			socket.close();
		} catch (IOException e) {
			System.out.println("No se ha podido cerrar el socket");
		}
	}

	@Override
	public void addObserver(ObserverLobby o) {
		
		lst1.add(o); //añade un observador
	}

	@Override
	public void removeObserver(ObserverLobby o) { }

	@Override
	public void notifyObserver(ObserverLobby o) { }

	@Override
	// Adición de un observador a la lista de observadores.
	public void addObserver(ObserverGame o) {
		
		lst2.add(o);
	}

	@Override
	public void removeObserver(ObserverGame o) { }

	@Override
	public void notifyObserver(ObserverGame o) { }
}









