package Todo.Ludo.JUEGO;
import Todo.Client.Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class Movimientos extends JPanel implements KeyListener, ActionListener,MouseListener {
	
	private static final long serialVersionUID = 1L;
	Layout la;
	Jugador j;
	Timer tiempo;
	int delay = 10;
	int current_player;
	int dado;
	int flag = 0;
	int roll;
	int kill = 0;

	public Movimientos() {
        setFocusTraversalKeysEnabled(false);
        requestFocus();
        current_player=0;
        la = new Layout(80,50);
        j = new Jugador(la.height,la.width);
        dado = 0;
        flag = 0;
        roll = 0;
        kill = 0;
    }
	protected void paintComponent(Graphics g) { //IMAGENES DE PERSONAJES
		super.paintComponent(g);
	}
    @Override
    public void paint(Graphics g) {
    	la.draw((Graphics2D)g);
    	j.Dibuja((Graphics2D)g);
    	if(j.pl[current_player].ficha == 4) {
    		g.setColor(Color.WHITE);
    		g.fillRect(590, 100, 380,130);
    		if(current_player==0) { //si gana el jugador X la felicitacion se pone del color gaandor
				g.setColor(Color.RED);
			}
			else if(current_player== 1) {
				g.setColor(Color.GREEN);
			}
			else if(current_player== 2) {
				g.setColor(Color.YELLOW);
			}
			else if(current_player== 3) {
				g.setColor(Color.BLUE);
			}
			//GANADOR
            g.setFont(new Font("arial", Font.BOLD, 25));
            g.drawString("El jugador "+(current_player+1)+" gana!!!!", 668, 380);
            g.drawString("FELICITACIONES", 600, 430);
            current_player=0;
            la = new Layout(80,50);
            j =new Jugador(la.height,la.width);
            dado =0;
            flag=0;
            roll=0;
            kill=0;
    	}
    	else if(dado !=0) {
    		//cuadro blanco donde se ubican las letras
    		g.setColor(Color.WHITE);
    		g.fillRect(590, 340, 300,120);
			g.setColor(Color.BLACK);
			g.drawRect(590, 340, 300, 120);
    		//cuadro para las imagenes o el borde
			g.setColor(Color.BLACK);
			g.drawRect(600, 30, 280, 300);
			//color de la letra segun el jugador
    		if(current_player==0) {
				g.setColor(Color.RED);
				//cambia la imagen segun el color
				ImageIcon personaje = new ImageIcon("src/Todo/Imagenes/Personaje_1.jpeg");
				g.drawImage(personaje.getImage(), 600, 30, 280, 300, this);
			}
			else if(current_player==1) {
				g.setColor(Color.GREEN);
				ImageIcon personaje = new ImageIcon("src/Todo/Imagenes/Personaje_2.jpeg");
				g.drawImage(personaje.getImage(), 600, 30, 280, 300, this);
			}
			else if(current_player==2) {
				g.setColor(Color.YELLOW);
				ImageIcon personaje = new ImageIcon("src/Todo/Imagenes/Personaje_3.jpeg");
				g.drawImage(personaje.getImage(), 600, 30, 280, 300, this);
			}
			else if(current_player==3) {
				g.setColor(Color.BLUE);
				ImageIcon personaje = new ImageIcon("src/Todo/Imagenes/Personaje_4.jpeg");
				g.drawImage(personaje.getImage(), 600, 30, 280, 300, this);
			}
            g.setFont(new Font("arial", Font.BOLD, 25));
            g.drawString("JUGADOR "+(current_player+1), 668, 380);
            g.drawString("Numero del dado: "+ dado, 630, 430);
    	}
    	if(flag==0&& dado !=0&& dado !=6 && kill==0) {
			current_player=(current_player+1)%4;
		}
    	kill=0;
    }
	@Override
	public void keyPressed(KeyEvent e) {
		//accion cuando el jugador da clic en enter
		if (e.getKeyCode() == KeyEvent.VK_ENTER && flag==0) {
			Client.getInstance().sendMessage("Gira el dado");
			roll=0;
			dado = 1 + (int)(Math.random() * 6); //un numero random del 1 al 6
			repaint();
			for(int i=0;i<4;i++) {
    			if(j.pl[current_player].pa[i].current!=-1&& j.pl[current_player].pa[i].current!=56&&(j.pl[current_player].pa[i].current+ dado)<=56) {
    				flag=1;
    				break;
    			}
    		}
    		if(flag == 0 && dado == 6) {
				Client.getInstance().sendMessage("salio 6, movio ficha");
    			for(int i=0;i<4;i++) {
    				if(j.pl[current_player].pa[i].current==-1) {
    					flag=1;
    					break;
    				}
    			}
    		}
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		//acciones cuando el jugador da clic en la ficha
		if(flag==1) {
			Client.getInstance().sendMessage("mueve una ficha");
			int x=e.getX();
			int y=e.getY();
			x=x-80;
			y=y-50;
			x=x/30;
			y=y/30;
			int value=-1;
			//System.out.println(x+" "+y);
			if(dado ==6) {
				for(int i=0;i<4;i++) {
					if(j.pl[current_player].pa[i].x==x&& j.pl[current_player].pa[i].y==y&&(j.pl[current_player].pa[i].current+ dado)<=56) {
						value=i;
						flag=0;
						break;
					}	
				}
				if(value!=-1) {
					j.pl[current_player].pa[value].current+= dado;
					if(j.pl[current_player].pa[value].current==56) {
						j.pl[current_player].ficha++;
					}
					int k=0;
					int hou= j.pl[current_player].pa[value].current;
					if((hou%13)!=0&&(hou%13)!=8&&hou<51)
					{
					for(int i=0;i<4;i++) {
						if(i!=current_player) {
							for(int j=0;j<4;j++) {
								int tem1= Camino.ax[current_player][this.j.pl[current_player].pa[value].current],tem2= Camino.ay[current_player][this.j.pl[current_player].pa[value].current];
								if(this.j.pl[i].pa[j].x==tem1&& this.j.pl[i].pa[j].y==tem2) {
									this.j.pl[i].pa[j].current=-1;
									kill=1;
									k=1;
									break;
								}
							}
						}
						if(k==1)
							break;
					}
					}
				}
				else {
					for(int i=0;i<4;i++) {
						if(j.pl[current_player].pa[i].current==-1) {
							j.pl[current_player].pa[i].current=0;
							flag=0;
							break;
						}	
					}
				}
			}
			else {
				for(int i=0;i<4;i++) {
					if(j.pl[current_player].pa[i].x==x&& j.pl[current_player].pa[i].y==y&&(j.pl[current_player].pa[i].current+ dado)<=56) {
						value=i;
						flag=0;
						break;
					}	
				}
				if(value!=-1) {
					j.pl[current_player].pa[value].current+= dado;
					if(j.pl[current_player].pa[value].current==56) {
						j.pl[current_player].ficha++;
					}
					int k=0;
					int hou= j.pl[current_player].pa[value].current;
					if((hou%13)!=0&&(hou%13)!=8&&hou<51)
					{
					for(int i=0;i<4;i++) {
						if(i!=current_player) {
							for(int j=0;j<4;j++) {
								int tem1= Camino.ax[current_player][this.j.pl[current_player].pa[value].current],tem2= Camino.ay[current_player][this.j.pl[current_player].pa[value].current];
								if(this.j.pl[i].pa[j].x==tem1&& this.j.pl[i].pa[j].y==tem2) {
									this.j.pl[i].pa[j].current=-1;
									kill=1;
									k=1;
									break;
								}
							}
						}
						if(k==1)
							break;
					}
					}
				}
			}
			repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
