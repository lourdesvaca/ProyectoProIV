package Todo.Server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Logger;

public class Server{

	private static Server instance = null;

	private Lobby currentLobby; //la sala
	private boolean serverIsRunning = true; //pregunta si corre el servidor

	//Si la instancia es nula, cree una nueva instancia y devuélvala. De lo contrario, devolver la instancia existente
	//@return La instancia de la clase Server.

	public static Server getInstance(){
		if(instance == null){
			instance = new Server();
		}
		return instance;
	}
	Logger logger = Logger.getLogger(Server.class.getName());

	//Crea un nuevo socket de servidor y luego llama a la función `threadAcceptClients` con el socket del servidor como parámetro
	//corre el servidor

	public void runServer() {
		ServerSocket server;
		try {
			server = new ServerSocket(12345); //el puerto del servidor (se puede cambiar)
		} catch (IOException e) {
			System.out.println("No se pudo abrir el puerto 12345");
			e.printStackTrace();
			return;
		} 
		System.out.println("Puerto 12345 abierto!");
		currentLobby = new Lobby(); //crea la sala
		threadAcceptClients(server); //crea un hilo que escucha si se unen o no
	}

	//Crea un nuevo hilo que escucha nuevos clientes y cuando se conecta un nuevo cliente, crea un nuevo hilo para escuchar a ese cliente.
	//@param server El socket del servidor que escuchará nuevos clientes.

	private void threadAcceptClients(ServerSocket server) {
		
		Thread acceptClients = new Thread(){
			
			@Override
			public void run() { //define el hilo
				//esto hace el hilo
				while (serverIsRunning){
					
					try {

						Socket newClient = server.accept(); //si se une un jugador, devuelve un socket (obtiene el ip del cliente)
						System.out.println("Nueva conexion con el cliente " + newClient.getInetAddress().getHostAddress());
						threadListenNewClient(newClient); //crea el hilo del jugador (escucha sus movimientos y acciones)
							
					} catch (IOException e) { //si no se une nadie o se cierra sale un error
						System.out.println("El servidor se cerró");
						e.printStackTrace();
					}
				}
			}
		};
		
		acceptClients.run(); //lo ejecuta
	}

	//Crea un nuevo hilo que escucha a un nuevo cliente y lo agrega al lobby actual
	//@param newClient El socket que representa al cliente que acaba de conectarse al servidor.

	private void threadListenNewClient(Socket newClient){
		//hilo que escucha a cada jugador
		Thread listenClient = new Thread(){
			
			@Override
			public void run(){
				
				Scanner scanner;
				//define el lobby
				//si el lobby ya esta lleno crea un nuevo lobby
				if (currentLobby.getIsFull()) {
					System.out.println("Creando nuevo lobby");
					currentLobby = new Lobby(); //crea una nueva sala
				}
				
				Lobby lobby = currentLobby; //actual lobby (si no esta lleno)

				while(serverIsRunning){ //mientras esta escuchando
					String nickname = null;  //declara donde va a guarda el nombre
					
					try {
						scanner = new Scanner(newClient.getInputStream()); //escanea todo lo que el cliente envie

						while (scanner.hasNextLine()) {//mientras recibe un mensaje
							String message = scanner.nextLine(); //guarda el mensaje que se envio
							
							logger.info("Mensaje recibido: " + message); //escribe un log para el registro

							if (message.equals("###")){  //si no lo reconoce no hace nada

							} else if  (message.startsWith("Nickname ")){
								//primero ingrese el nombre del jugador pa iniciar
								nickname = message.substring(9);  //guarda su nombre

								PrintStream output = new PrintStream(newClient.getOutputStream());
								
								if (lobby.addPlayer(nickname, newClient)) { //si el lobby
									output.println("Nickname Valido");
									
									if (lobby.getIsFull()) {  //si el lobby esta lleno ejecuta el juego
										System.out.println("Comenzando la partida");
										lobby.startGame();
									}
								} else { //si no, sigue esperando
									output.println("Nickname Invalido");
								}

							} else {
								lobby.sendToAllPlayers(nickname, message); //el lobby envia a todos los jugadores el nombre y que hizo
							}
						}
					} catch (IOException e) { //caso contrario o sale error (porque si el que creo el server se va, no existe el servidor y se cierra)
						logger.info("El cliente se desconecto");
						e.printStackTrace();
					}
				}
			}
		};
		
		listenClient.start(); //inicia o escucha el hilo
	}
}