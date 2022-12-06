package Todo.Ludo.JUEGO;
import java.awt.Graphics2D;

public class Jugador {
	//posicion de los jugadores
	jugadores[] pl = new jugadores[4];
	int[][] inicial_X = {
			{1,1,3,3},
			{10,10,12,12},
			{10,10,12,12},
			{1,1,3,3}
	};
	int[][] inicial_Y = {
			{1,3,1,3},
			{1,3,1,3},
			{10,12,10,12},
			{10,12,10,12}
	};
	public Jugador(int al, int an) {
		for(int i=0;i<4;i++) {
			pl[i]=new jugadores(al,an);
		}
	}
	public void Dibuja (Graphics2D g) {
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				pl[i].pa[j].Dibuja(g, inicial_X[i][j], inicial_Y[i][j],i);
			}
		}
	}
	
}
