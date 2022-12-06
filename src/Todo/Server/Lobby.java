package Todo.Server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Logger;

public class Lobby {
	//LA SALA
	private Player players[]; //debe tener jugadores
	private int numberOfPlayers; //ve cuantos jugadores
	private boolean isFull; //si esta lleno
	private int turn = 0; //el turno
	Logger logger = Logger.getLogger(Lobby.class.getName()); //logs que se guardan

	// El constructor de la clase Lobby.
	public Lobby(){
		players = new Player[4]; //defino cuantos jugadores deben estar en el servidor
		numberOfPlayers = 0; //inicio de jugadores
		isFull = false; //esta lleno? no
	}
	
	//Esta funcion devuelve el valor de la variable isFull
	//@return El valor de la variable isFull.

	public boolean getIsFull() {
		return isFull;
	} //si esta lleno

	//Esta funcion devuelve el n�mero de jugadores en el juego.
	//@return El numero de jugadores en el juego.

	public int getNumberOfPlayers(){
		return numberOfPlayers;
	} //cuantos jugadores existen

	//Esta funcion agrega un jugador al juego.
	//@param nickname El apodo del jugador que se agregara.

	public boolean addPlayer(String nickname, Socket socket) { //agrega al jugador (necesita su nombre y su socket)
		
		if (numberOfPlayers == 3) { //pregunta
			isFull = true;
		}
		
		if (numberOfPlayers != 0) { //si no, aun se puede añadir

			for (int i = 0; i < numberOfPlayers; i++) {
				
				if (players[i].getNickname().compareTo(nickname) == 0) {
					//recorre y compara si el jugador tiene el mismo nombre
					isFull = false;
					return false;
				}
			}
		}
		//si no hay problema, crea un objeto de la clase jugador y pasa sus datos
		players[numberOfPlayers] = new Player(nickname, socket);
		numberOfPlayers += 1; //aumenta el numero de jugadores
		return true;
	}

	//Env�a un mensaje a todos los jugadores en el juego para iniciar el juego

	public void startGame() {
		//Envia un mensaje a todos los jugadores en el juego para iniciar el juego
		for (int i = 0; i<numberOfPlayers; i++){ //hace recorrido y le envia a todos el mensaje de que se inicie

			try {
				//envia un mensaje al jugador
				PrintStream output = new PrintStream(players[i].getSocket().getOutputStream());
				String message = "Game Start";
				String fullMessage;
				//si el turno es del jugador, le envia el mensaje pa indicarle que le toca jugar
				if (turn == i) {
					fullMessage = "YourTurn " + message;
				} else {
					fullMessage = message;
				}

				output.println(fullMessage); //imprime el mensaje
			} catch (IOException e) {
				logger.severe("Error en enviar mensaje al cliente");
				e.printStackTrace();
			}
		}
		
		turn += 1;
	}

	//Envia un mensaje a todos los jugadores excepto al que envia el mensaje.
	//@param nickname El apodo del jugador que envia el mensaje.
	//@param message El mensaje que se enviara a todos los jugadores.

	public void sendToAllPlayers(String nickname, String message) { //un jugador hizo algo, y todo eso le envia a los demas

		for(int i = 0; i < numberOfPlayers; i++) { //le envia un mensaje al resto menos al que le toca
			//if(players[i].getNickname().compareTo(nickname) != 0) { //lo quito si quiero que me mande los mensajes
				try {

					PrintStream output = new PrintStream(players[i].getSocket().getOutputStream());
					String fullMessage;
					
					if (turn == i) {
						fullMessage = "YourTurn " + message;
					} else {
						fullMessage = message;
					}

					output.println(fullMessage);
				} catch (IOException e) {
					logger.severe("Error en enviar mensaje al cliente");
					e.printStackTrace();
				}
			}
		//}
		
		turn += 1; //si ya envia, le toca al otro
		
		if (turn == 4) {
			turn = 0;
		}
	}
}
