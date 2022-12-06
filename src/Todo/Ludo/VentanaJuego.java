package Todo.Ludo;
import Todo.Client.Client;
import Todo.Ludo.JUEGO.Movimientos;
import javax.swing.*;
import Todo.Client.ObserverGame;

import java.awt.*;

public class VentanaJuego extends JFrame implements ObserverGame {
    private Movimientos movimiento;

    public VentanaJuego() {
        //VENTANA
        super("LUDO GAME");
        setBounds(100, 100, 1000, 640);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        movimiento = new Movimientos();
        movimiento.setFocusable(true);
        movimiento.setVisible(true);
        movimiento.setBounds(0,0,1000,1000);
        movimiento.addKeyListener(movimiento);
        movimiento.addMouseListener(movimiento);
        add(movimiento);
        setVisible(true);
        Client.getInstance().addObserver(this);
    }
    public void paint(Graphics g){ //fondo
        ImageIcon imagen = new ImageIcon("src/Todo/Imagenes/LUDO_LOGO.png");
        g.drawImage(imagen.getImage(),300,80,416,532,this);
    }
    //llama al
    @Override
    public void receivedPlay(String play) {
        System.out.println(play);
    }

}

