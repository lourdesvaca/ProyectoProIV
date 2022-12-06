package Todo.Client;
import Todo.Ludo.VentanaJuego;

public class ClientConnection {
	//inicia la parte grafica
	private static ClientConnection instance = null;
	
	// Creando una nueva instancia de la clase BoardFrame.
	public ClientConnection(){
		new VentanaJuego();
	}

	//Si la instancia es nula, cree una nueva instancia de la clase y devu�lvala. Si la instancia no es nula, devolver la instancia
	//La instancia de la clase ClientConnection.

	public static ClientConnection getInstance(){
		if(instance == null){
			instance = new ClientConnection();
		}
		return instance;
	}

}
