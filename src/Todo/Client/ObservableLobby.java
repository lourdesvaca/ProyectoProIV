package Todo.Client;
public interface ObservableLobby {

	public void addObserver(ObserverLobby o); //añade
	public void removeObserver(ObserverLobby o); //remueve
	public void notifyObserver(ObserverLobby o); //notifica

}
