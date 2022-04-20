import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

import java.util.ArrayList;

class Lienzo extends JPanel
{
	private static final long serialVersionUID = 1L;
	private ThreadC hilo;
	planificador plan = new planificador(1);
	private int band=0;
	ModeloTabla model;
	
	public Lienzo() {
		repaint();
	}
	public Lienzo(ArrayList<Proceso>procesosLista, String tarea, int TE_P, int PID, int tipo,ModeloTabla model) {
		this.model=model;
		if(tipo==1) {
			plan.monoproceso(procesosLista, tarea, TE_P, PID);
			band=1;
			repaint();
			colorear1();
		}
		else {
			plan.multiproceso(procesosLista, tarea, TE_P, PID);
			band=2;
			repaint();
			colorear2();
		}
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	 	Graphics2D g2 = (Graphics2D)g; 
    	Font font = new Font("Sans Serif", Font.BOLD, 12);
    	g2.setFont(font);
	 	setBackground(new Color(30,30,30));
	 	plan.dibujar(g2,this);
	 	if(band==1) {
	 		g2.setPaint(Color.WHITE);
	 		int tiempo=plan.getTiempo();
	 		int x=81,y=60;
	 		Point2D pi=this.getLocation(),pf=this.getLocation();
	 		Line2D linea;
	 		for(int i=1;i<=tiempo;i++) {
	 			g2.drawString(String.valueOf(i),x,y);
	 			pi.setLocation(x-3,y-15);
	 			pf.setLocation(x-3,y+25);
	 			x=x+20;
	 			linea = new Line2D.Double(pi, pf);
	 			g2.draw(linea);
	 		}
	 		x=5;y=45;
	 		for(int i=1;i<=3;i++) {
	 			pi.setLocation(x,y);
	 			pf.setLocation(x+560,y);
	 			y=y+20;
	 			linea = new Line2D.Double(pi, pf);
	 			g2.draw(linea);
	 		}
	 		
	 		g2.drawString("Canal 1:",10,80);
	 		pi.setLocation(5,45);
 			pf.setLocation(5,85);
 			linea = new Line2D.Double(pi, pf);
 			g2.draw(linea);
 			
 			pi.setLocation(560,45);
 			pf.setLocation(560,85);
 			linea = new Line2D.Double(pi, pf);
 			g2.draw(linea);
	 	}
	 	else {
	 		g2.setPaint(Color.WHITE);
	 		int tiempo=plan.getTiempo();
	 		int x=81,y=60;
	 		Point2D pi=this.getLocation(),pf=this.getLocation();
	 		Line2D linea;
	 		for(int i=1;i<=tiempo;i++) {
	 			g2.drawString(String.valueOf(i),x,y);
	 			pi.setLocation(x-3,y-15);
	 			pf.setLocation(x-3,y+45);
	 			x=x+20;
	 			linea = new Line2D.Double(pi, pf);
	 			g2.draw(linea);
	 		}
	 		x=5;y=45;
	 		for(int i=1;i<=4;i++) {
	 			pi.setLocation(x,y);
	 			pf.setLocation(x+560,y);
	 			y=y+20;
	 			linea = new Line2D.Double(pi, pf);
	 			g2.draw(linea);
	 		}
	 		
	 		g2.drawString("Canal 1:",10,80);
	 		g2.drawString("Canal 2:",10,100);
	 		pi.setLocation(5,45);
 			pf.setLocation(5,105);
 			linea = new Line2D.Double(pi, pf);
 			g2.draw(linea);
 			
 			pi.setLocation(560,45);
 			pf.setLocation(560,105);
 			linea = new Line2D.Double(pi, pf);
 			g2.draw(linea);
	 	}
	 	
	}
	
	public void colorear1() {
		hilo=new ThreadC(this,plan.getCanal1(),model);
		hilo.start();
	}
	
	public void colorear2() {
		hilo=new ThreadC(this,plan.getCanal1(),plan.getCanal2(),model);
		hilo.start();
	}
}