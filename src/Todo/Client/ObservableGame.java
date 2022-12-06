package Todo.Client;

public interface ObservableGame {

	public void addObserver(ObserverGame o); //añade
	public void removeObserver(ObserverGame o); //remueve
	public void notifyObserver(ObserverGame o); //notifica
	
}
