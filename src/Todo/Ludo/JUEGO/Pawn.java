package Todo.Ludo.JUEGO;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Pawn { //fichas
	int x;
	int y;
	int current;
	int altura;
	int anchura;

	public Pawn(int alto,int ancho){
		current = -1;
		x = -1;
		y = -1;
		altura = alto;
		anchura = ancho;
	}
	public void Dibuja (Graphics2D g, int i, int j, int juega) {

		if(current == -1) {
			int temp1=80+(altura /2);
			int temp2=50+(anchura /2);
			x=i;
			y=j;
			if(juega==0) {
				//pone el color de las fichas segun el jugador
				//juagador 1 = rojo
				g.setColor(Color.RED);
			}
			else if(juega==1) {
				//juagador 2 = verde
				g.setColor(Color.GREEN);
			}
			else if(juega==2) {
				//juagador 3 = amarillo

				g.setColor(Color.YELLOW);
			}
			else if(juega==3) {
				//juagador 4 = azul
				g.setColor(Color.BLUE);
			}
			g.fillOval(temp1+5+(i* anchura),temp2+5+(j* altura), anchura -10, altura -10);
			g.setStroke(new BasicStroke(2));
			g.setColor(Color.BLACK);
	    	g.drawOval(temp1+5+(i* anchura),temp2+5+(j* altura), anchura -10, altura -10);
		}
		else
		{
			int temp1 = 80;
			int temp2 = 50;
			x= Camino.ax[juega][current];
			y= Camino.ay[juega][current];
			if(juega==0) {
				g.setColor(Color.RED);
			}
			else if(juega==1) {
				g.setColor(Color.GREEN);
			}
			else if(juega==2) {
				g.setColor(Color.YELLOW);
			}
			else if(juega==3) {
				g.setColor(Color.BLUE);
			}
			g.fillOval(temp1+5+(x* anchura),temp2+5+(y* altura), anchura -10, altura -10);
			g.setStroke(new BasicStroke(2));
			g.setColor(Color.BLACK);
	    	g.drawOval(temp1+5+(x* anchura),temp2+5+(y* altura), anchura -10, altura -10);
		}
	}
}
