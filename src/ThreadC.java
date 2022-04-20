import java.util.ArrayList;

import java.awt.geom.*;

public class ThreadC extends Thread{
	private Lienzo lienzo;
	private ArrayList<Proceso> canal1 = new ArrayList<Proceso>();
	private ArrayList<Proceso> canal2 = new ArrayList<Proceso>();
	int band;
	ModeloTabla model;
	
	public ThreadC(Lienzo lienzo, ArrayList<Proceso> canal1,ModeloTabla model) {
		this.model=model;
		this.lienzo=lienzo;
		this.canal1.addAll(canal1);
		band=1;
	}
	
	public ThreadC(Lienzo lienzo, ArrayList<Proceso> canal1,ArrayList<Proceso> canal2,ModeloTabla model) {
		this.model=model;
		this.lienzo=lienzo;
		this.canal1.addAll(canal1);
		this.canal2.addAll(canal2);
		band=2;
	}
	public void run() {
		Point2D x=lienzo.getLocation();
		Proceso aux=canal1.get(0);
		double a=65,b=75;
		if(band==1) {
			x.setLocation(a,b);
			aux.creaCirculo(x);
			for(int i=0;i<canal1.size();i++) {
				if(canal1.get(i).equals(aux)) {
					a=a+20.5;
					x.setLocation(a,b);
					canal1.get(i).creaCirculo(x);
				}
				else {
					aux=canal1.get(i);
					
					a=a+20;
					x.setLocation(a,b);
					canal1.get(i).creaCirculo(x);
				}
				
				try {
					lienzo.repaint();
					sleep(1000);
				}catch(InterruptedException ex) {}
			}
		}
		else {
			x.setLocation(a,b);
			aux.creaCirculo(x);
			for(int i=0;i<canal1.size();i++) {
				if(canal1.get(i).equals(aux)) {
					a=a+20.5;
					x.setLocation(a,b);
					canal1.get(i).creaCirculo(x);
				}
				else {
					aux=canal1.get(i);
					a=a+20;
					x.setLocation(a,b);
					canal1.get(i).creaCirculo(x);
				}
				try {
					lienzo.repaint();
					sleep(1000);
				}catch(InterruptedException ex) {}
				
				if(i<canal2.size()) {
					if(canal2.get(i)!=null) {
						if(canal2.get(i).equals(aux)) {
							x.setLocation(a,b+20);
							canal2.get(i).creaCirculo(x);
						}
						else {
							aux=canal2.get(i);
							x.setLocation(a,b+20);
							canal2.get(i).creaCirculo(x);
						}
						try {
							lienzo.repaint();
							sleep(1000);
						}catch(InterruptedException ex) {}
					}
				}
			}
			
		}
		
	}

}
