package Todo.Server;

public class MainServer {
	//inicia el servidor
	public static void main(String[] args) {
		Server server = Server.getInstance();
		server.runServer();
		return;
	}

}