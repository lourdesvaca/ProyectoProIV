package Todo.Client;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ClientFrame extends JFrame implements ActionListener, ObserverLobby {
	//ventana del cliente y observa la sala
	private static ClientFrame instance = null;
	private JLabel l1;
	private JButton b1;
	private JTextField t1;
	private Container screen;
	private String currentNickname;
	private boolean ValidNickname = true;
	
	// El constructor de la clase ClientFrame. esta creado la ventana del cliente.
	public ClientFrame(String s){
		super(s);
		screen = getContentPane();
		screen.setLayout(null);

		l1 = new JLabel("Nickname");
		l1.setBounds(40,70,100,30);
		t1 = new JTextField();
		t1.setBounds(130,70,100,30);
		b1 = new JButton("Unirse");
		b1.setBounds(100,230,80,70);
		
		screen.add(l1);
		screen.add(t1);
		screen.add(b1);

		t1.addActionListener(this);
		b1.addActionListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Client.getInstance().addObserver(this); //obtiene ese cliente y le añade un observador
	}

	//Si la instancia es nula, cree una nueva instancia de ClientFrame; de lo contrario, devuelva la instancia existente
	//La instancia de la clase ClientFrame.

	public static ClientFrame getInstance(){
		if(instance == null){
			instance = new ClientFrame("Ludo");
		}
		
		return instance;
	}

	@Override
	// Este metodo se llama cuando el usuario hace clic en el bot�n. Obtiene el apodo del campo de texto y lo envia al
	// servidor.
	public void actionPerformed(ActionEvent e) { //al apretar
		if(e.getSource() == b1){
			//obtiene el nombre y envia mensaje al servidor
			currentNickname = t1.getText();
			Client.getInstance().sendMessage("Nickname " + currentNickname);
			b1.setEnabled(false);
		}
		else{
			System.exit(1); //si no cierra todo
		}
		
	}

	@Override
	//si el nombre es valido,Ocultar el campo de texto y cambiar la etiqueta para dar la bienvenida al usuario.
	public void receivedNicknameAvaiable() {
		t1.hide(); //oculta el boton
		l1.setSize(100, 30);
		l1.setText("¡Bienvenido " + currentNickname + "!");
	}


	@Override
	// Habilitando el boton nuevamente si el nombre no es valido
	public void receivedNicknameUnavaiable() {
		b1.setEnabled(true);
	}

	@Override
	//Crear una nueva instancia de ClientConnection y luego desechar el marco actual.
	public void receivedGameStart() {
		ClientConnection.getInstance();
		dispose();
	}

}
