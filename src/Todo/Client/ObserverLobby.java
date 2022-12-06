package Todo.Client;

public interface ObserverLobby {
	//recibe cosas del lobby
	public void receivedNicknameAvaiable(); //recibe el nombre valido
	public void receivedNicknameUnavaiable(); //recibe el nombre invalido
	public void receivedGameStart(); //recibe el comienzo del juego
	
}
