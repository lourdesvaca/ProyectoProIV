package Todo.Client;

public class main {
	//inicia el cliente, ventana donde pone el nombre
	public static void main(String[] args) {
		
		ClientFrame screen = ClientFrame.getInstance();
		screen.setSize(300,400);
		screen.setLocationRelativeTo(null);
		screen.setVisible(true);

		Client client = Client.getInstance();
		client.startClient(); //lo inicia
	
	}

}
