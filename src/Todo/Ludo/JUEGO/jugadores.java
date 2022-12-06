package Todo.Ludo.JUEGO;

import java.awt.Graphics2D;

public class jugadores {
	int h; //altura
	int w; //ancho
	int status;
	int ficha;

	Pawn[] pa = new Pawn[4];

	public jugadores(int height, int width) {
		status = -1;
		ficha = 0;
		for(int i=0;i<4;i++) {
			pa[i] = new Pawn(height,width);
		}
	}
	public void draw(Graphics2D g) { }
}
