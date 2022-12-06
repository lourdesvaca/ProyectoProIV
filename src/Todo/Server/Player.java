package Todo.Server;

import java.net.Socket;

public class Player {
	//nombre del jugador y su socket
	private String nickname;
	private Socket socket;
	
	public Player(String nickname, Socket socket) {
		this.nickname = nickname;
		this.socket = socket;
	}
	
	public String getNickname(){
		return nickname;
	}
	
	public Socket getSocket() {
		return socket;
	}
}
