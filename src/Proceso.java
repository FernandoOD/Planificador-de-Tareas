import java.awt.*;
import java.awt.geom.*;


public class Proceso{
	private Integer pid;
	private int tLlegada;
	private Integer tEjecucion;
	private Integer prioridad;
	private Integer tEspera;
	private boolean terminado;
	
	//Gr�fico
	 private Point2D origen; //punto origen
     private Ellipse2D circulo;  //nodo
     private Color color;
     private final static int diametro = 15;
	
	public Proceso(int pid, int tLlegada, int tEjecucion,int prioridad,Color color) {
		this.pid=pid;
		this.tLlegada=tLlegada;
		this.tEjecucion=tEjecucion;
		this.prioridad=prioridad;
		this.tEspera=tEjecucion;
		terminado=false;
		circulo = new Ellipse2D.Double(0-diametro/2,0-diametro/2,diametro,diametro);
		this.color = color;
	}
	
	public void  settEspera(int tEspera) {
		this.tEspera=tEspera;
	}
	
	public void settEjecucion(Integer tEjecucion) {
		this.tEjecucion=tEjecucion;
	}
	
	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Integer prioridad) {
		this.prioridad = prioridad;
	}

	@Override
	public String toString() {
		return pid +" ";
	}

	public boolean estaTerminado() {
		return terminado;
	}
	
	public void terminalo() {
		terminado=true;
	}
	
	public int gettLlegada() {
		return tLlegada;
	}

	public Integer gettEjecucion() {
		return tEjecucion;
	}
	
	public void creaCirculo(Point2D origen) {
		this.origen=origen;
		double x = this.origen.getX();
        double y = this.origen.getY();
		 circulo = new Ellipse2D.Double(x-diametro/2,y-diametro/2,diametro,diametro);
	}
	
	public void dibujar(Graphics2D g2){
   	 	g2.setPaint(color);
   	 	g2.fill(circulo);
   }
	
	public void setColor(Color color) {
		this.color=color;
	}

	public Integer gettEspera() {
		return tEspera;
	}

	public Color getColor() {
		return color;
	}

	public void settLlegada(Integer tLlegada){
		this.tLlegada=tLlegada;
	}
	
}
