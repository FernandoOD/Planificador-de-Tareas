import java.util.ArrayList;
import java.awt.*;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

public class planificador {
	private int tiempo;
	private ArrayList<Proceso> canal1 = new ArrayList<Proceso>();
	private ArrayList<Proceso> canal2 = new ArrayList<Proceso>();
	private ArrayList<Proceso> llegadas = new ArrayList<Proceso>();
	private ArrayList<Proceso> porEjecucion = new ArrayList<Proceso>();
	private ArrayList<Proceso> Prioridad = new ArrayList<Proceso>();
	private ArrayList<Proceso> porPID = new ArrayList<Proceso>();
	private ArrayList<Proceso> Mayores =new ArrayList<Proceso>();
	private Cola c= new Cola();
	
	public planificador(int procesoTipo) {
		tiempo=0;
		if(procesoTipo==1) {
			canal1=new ArrayList<Proceso>();
		}
		else {
			canal1=new ArrayList<Proceso>();
			canal2=new ArrayList<Proceso>();
		}
		
	}
	
	public void incrementaTiempo() {
		tiempo++;
	}
	
	public void monoproceso(ArrayList<Proceso> lista,String tarea, int TE_P, int PID) {
		limpiaListas();
		if(tarea.equalsIgnoreCase("Monotarea")) {
			Monotarea(lista,TE_P,PID);
			System.out.println(canal1);
		}
		else {
			Multitarea(lista,TE_P,PID);
			System.out.println(canal1);
		}
	}
	
	public void multiproceso(ArrayList<Proceso> lista,String tarea, int TE_P, int PID) {
		limpiaListas();
		if(tarea.equalsIgnoreCase("Monotarea")) {
			Monotarea2(lista,TE_P,PID);
			System.out.println(canal1);
			System.out.println(canal2);
		}
		else {
			Multitarea2(lista,TE_P,PID);
			System.out.println(canal1);
			System.out.println(canal2);
		}
	}
	
	public void Monotarea(ArrayList<Proceso> lista, int TE_P, int PID) {
		int i;
		int band=1,index;
		while(band==1) {
			band=0;
			for(i=0;i<lista.size();i++) {
				if(!lista.get(i).estaTerminado() && lista.get(i).gettLlegada()<=tiempo) {
					index=llegadas.indexOf(lista.get(i));
					if(index<0)
						llegadas.add(lista.get(i));
				}
			}
			if(esUno(llegadas)&&tiempo==0) {
				Proceso p=llegadas.get(0);
				procesalo(p);
				llegadas.remove(0);
			}
			else {
				if(TE_P==1) {
					porEjecucion.clear();
					porEjecucion.addAll(llegadas);
					Mayores.clear();
					filtrar(1);
					buscar(1);
				}
				else {
					Prioridad.clear();
					Prioridad.addAll(llegadas);
					Mayores.clear();
					filtrar(2);
					buscar(2);
				}
				if(esUno(Mayores)) {
					Proceso p=Mayores.get(0);
					procesalo(p);
					Mayores.clear();
					if(TE_P==1) {
						index=porEjecucion.indexOf(p);
						porEjecucion.remove(index);
					}
					else {
						index=Prioridad.indexOf(p);
						Prioridad.remove(index);
					}
					index=llegadas.indexOf(p);
					llegadas.remove(index);
				}
				else {
					if(TE_P==1) {
						Prioridad.clear();
						Prioridad.addAll(Mayores);
						Mayores.clear();
						filtrar(2);
						buscar(2);
					}
					else {
						porEjecucion.clear();
						porEjecucion.addAll(Mayores);
						Mayores.clear();
						filtrar(1);
						buscar(1);
					}
					if(esUno(Mayores)) {
						Proceso p=Mayores.get(0);
						procesalo(p);
						Mayores.clear();
						index=porEjecucion.indexOf(p);
						porEjecucion.remove(index);
						index=llegadas.indexOf(p);
						llegadas.remove(index);
						index=Prioridad.indexOf(p);
						Prioridad.remove(index);
					}
					else {
						porPID.clear();
						porPID.addAll(Mayores);
						Mayores.clear();
						if(PID==1) {
							filtrar(3);
						}
						else {
							filtraPIDD();
						}
						buscar(3);
						Proceso p=Mayores.get(0);
						procesalo(p);
						index=porEjecucion.indexOf(p);
						porEjecucion.remove(index);
						index=llegadas.indexOf(p);
						llegadas.remove(index);
						index=Prioridad.indexOf(p);
						Prioridad.remove(index);
						index=porPID.indexOf(p);
						porPID.remove(index);
					}
				}
				
			}
			for(Proceso p:lista) {
				if(!p.estaTerminado())
					band=1;
			}
		}
	}
	
	public void Multitarea(ArrayList<Proceso> lista, int TE_P, int PID) {
		int i;
		int band=1,index;
		while(band==1) {
			band=0;
			if(llegadas.isEmpty()&& !c.estaVacia()) {
				for(i=0;i<c.getNumE();i++) {
					llegadas.add(c.desencolar());
				}
			}
			for(i=0;i<lista.size();i++) {
				if(!lista.get(i).estaTerminado() && lista.get(i).gettLlegada()<=tiempo) {
					index=llegadas.indexOf(lista.get(i));
					if(index<0) {
						Proceso p=buscaEnCola(lista.get(i));
						if(p==null)
							llegadas.add(lista.get(i));
					}	
				}
			}
			if(esUno(llegadas)&&tiempo==0) {
				Proceso p=llegadas.get(0);
				procesalo2(p);
				llegadas.remove(0);
			}
			else {
				if(TE_P==1) {
					porEjecucion.clear();
					porEjecucion.addAll(llegadas);
					Mayores.clear();
					filtrar(1);
					buscar(1);
				}
				else {
					Prioridad.clear();
					Prioridad.addAll(llegadas);
					Mayores.clear();
					filtrar(2);
					buscar(2);
				}
				if(esUno(Mayores)) {
					Proceso p=Mayores.get(0);
					procesalo2(p);
					Mayores.clear();
					if(TE_P==1) {
						index=porEjecucion.indexOf(p);
						porEjecucion.remove(index);
					}
					else {
						index=Prioridad.indexOf(p);
						Prioridad.remove(index);
					}
					index=llegadas.indexOf(p);
					llegadas.remove(index);
				}
				else {
					if(TE_P==1) {
						Prioridad.clear();
						Prioridad.addAll(Mayores);
						Mayores.clear();
						filtrar(2);
						buscar(2);
					}
					else {
						porEjecucion.clear();
						porEjecucion.addAll(Mayores);
						Mayores.clear();
						filtrar(1);
						buscar(1);
					}
					if(esUno(Mayores)) {
						Proceso p=Mayores.get(0);
						procesalo2(p);
						Mayores.clear();
						index=porEjecucion.indexOf(p);
						porEjecucion.remove(index);
						index=llegadas.indexOf(p);
						llegadas.remove(index);
						index=Prioridad.indexOf(p);
						Prioridad.remove(index);
					}
					else {
						porPID.clear();
						porPID.addAll(Mayores);
						Mayores.clear();
						if(PID==1) {
							filtrar(3);
						}
						else {
							filtraPIDD();
						}
						buscar(3);
						Proceso p=Mayores.get(0);
						procesalo2(p);
						index=porEjecucion.indexOf(p);
						porEjecucion.remove(index);
						index=llegadas.indexOf(p);
						llegadas.remove(index);
						index=Prioridad.indexOf(p);
						Prioridad.remove(index);
						index=porPID.indexOf(p);
						porPID.remove(index);
					}
				}
				
			}
			for(Proceso p:lista) {
				if(!p.estaTerminado())
					band=1;
			}
		}
	}
	
	public void Monotarea2(ArrayList<Proceso> lista, int TE_P, int PID) {
		int i;
		int band=1,index;
		while(band==1) {
			band=0;
			
			for(i=0;i<lista.size();i++) {
				if(!lista.get(i).estaTerminado() && lista.get(i).gettLlegada()<=tiempo) {
					index=llegadas.indexOf(lista.get(i));
					if(index<0)
						llegadas.add(lista.get(i));
				}
			}
			if(esUno(llegadas)&&tiempo==0) {
				Proceso p=llegadas.get(0);
				procesalo3(p);
				llegadas.remove(0);
			}
			else {
				if(TE_P==1) {
					porEjecucion.clear();
					porEjecucion.addAll(llegadas);
					Mayores.clear();
					filtrar(1);
					buscar(1);
				}
				else {
					Prioridad.clear();
					Prioridad.addAll(llegadas);
					Mayores.clear();
					filtrar(2);
					buscar(2);
				}
				if(esUno(Mayores)) {
					Proceso p=Mayores.get(0);
					procesalo3(p);
					Mayores.clear();
					if(TE_P==1) {
						index=porEjecucion.indexOf(p);
						porEjecucion.remove(index);
					}
					else {
						index=Prioridad.indexOf(p);
						Prioridad.remove(index);
					}
					index=llegadas.indexOf(p);
					llegadas.remove(index);
				}
				else {
					if(TE_P==1) {
						Prioridad.clear();
						Prioridad.addAll(Mayores);
						Mayores.clear();
						filtrar(2);
						buscar(2);
					}
					else {
						porEjecucion.clear();
						porEjecucion.addAll(Mayores);
						Mayores.clear();
						filtrar(1);
						buscar(1);
					}
					if(esUno(Mayores)) {
						Proceso p=Mayores.get(0);
						procesalo3(p);
						Mayores.clear();
						index=porEjecucion.indexOf(p);
						porEjecucion.remove(index);
						index=llegadas.indexOf(p);
						llegadas.remove(index);
						index=Prioridad.indexOf(p);
						Prioridad.remove(index);
					}
					else {
						porPID.clear();
						porPID.addAll(Mayores);
						Mayores.clear();
						if(PID==1) {
							filtrar(3);
						}
						else {
							filtraPIDD();
						}
						buscar(3);
						Proceso p=Mayores.get(0);
						procesalo3(p);
						index=porEjecucion.indexOf(p);
						porEjecucion.remove(index);
						index=llegadas.indexOf(p);
						llegadas.remove(index);
						index=Prioridad.indexOf(p);
						Prioridad.remove(index);
						index=porPID.indexOf(p);
						porPID.remove(index);
					}
				}
				
			}
			for(Proceso p:lista) {
				if(!p.estaTerminado())
					band=1;
			}
		}
	}
	
	public void Multitarea2(ArrayList<Proceso> lista, int TE_P, int PID) {
		int i;
		int band=1,index;
		while(band==1) {
			band=0;
			if(llegadas.isEmpty()&& !c.estaVacia()) {
				while(!c.estaVacia()) {
					llegadas.add(c.desencolar());
				}
			}
			for(i=0;i<lista.size();i++) {
				if(!lista.get(i).estaTerminado() && lista.get(i).gettLlegada()<=tiempo) {
					index=llegadas.indexOf(lista.get(i));
					if(index<0) {
						Proceso p=buscaEnCola(lista.get(i));
						if(p==null)
							llegadas.add(lista.get(i));
					}	
				}
			}
			Cerebro(TE_P,PID,1);
			if(!llegadas.isEmpty()&&canal2.size()<canal1.size()) {
				Cerebro(TE_P,PID,2);
			}
			else {
				if(llegadas.isEmpty()) {
					Proceso p=null;
					procesalo5(p);
				}
			}
			for(Proceso p:lista) {
				if(!p.estaTerminado())
					band=1;
			}
		}
	}
	
	public void Cerebro(int TE_P, int PID,int vez) {
		int index;
		if(esUno(llegadas)&&tiempo==0) {
			Proceso p=llegadas.get(0);
			if(vez==1)
				procesalo4(p);
			else
				procesalo5(p);
			llegadas.remove(0);
		}
		else {
			if(TE_P==1)
				PorEjecucion(llegadas);
			else 
				PorPrioridad(llegadas);
			
			if(esUno(Mayores)) {
				Proceso p=Mayores.get(0);
				Mayores.clear();
				if(vez==1)
					procesalo4(p);
				else
					procesalo5(p);
				if(TE_P==1) {
					index=porEjecucion.indexOf(p);
					porEjecucion.remove(index);
				}
				else {
					index=Prioridad.indexOf(p);
					Prioridad.remove(index);
				}
				index=llegadas.indexOf(p);
				llegadas.remove(index);
			}
			else {
				if(TE_P==1) {
					PorPrioridad(Mayores);
				}
				else {
					PorEjecucion(Mayores);
				}
				if(esUno(Mayores)) {
					Proceso p=Mayores.get(0);
					if(vez==1)
						procesalo4(p);
					else
						procesalo5(p);
					Mayores.clear();
					index=porEjecucion.indexOf(p);
					porEjecucion.remove(index);
					index=llegadas.indexOf(p);
					llegadas.remove(index);
					index=Prioridad.indexOf(p);
					Prioridad.remove(index);
				}
				else {
					PorPID(Mayores,PID);
					Proceso p=Mayores.get(0);
					if(vez==1)
						procesalo4(p);
					else
						procesalo5(p);
					index=porEjecucion.indexOf(p);
					porEjecucion.remove(index);
					index=llegadas.indexOf(p);
					llegadas.remove(index);
					index=Prioridad.indexOf(p);
					Prioridad.remove(index);
					index=porPID.indexOf(p);
					porPID.remove(index);
				}
			}
			
		}
	}
	public void PorEjecucion(ArrayList<Proceso> fuente) {
		porEjecucion.clear();
		porEjecucion.addAll(fuente);
		Mayores.clear();
		filtrar(1);
		buscar(1);
	}
	
	public void PorPrioridad(ArrayList<Proceso> fuente) {
		Prioridad.clear();
		Prioridad.addAll(fuente);
		Mayores.clear();
		filtrar(2);
		buscar(2);
	}
	
	public void PorPID(ArrayList<Proceso> fuente,int PID) {
		porPID.clear();
		porPID.addAll(fuente);
		Mayores.clear();
		if(PID==1) {
			filtrar(3);
		}
		else {
			filtraPIDD();
		}
		buscar(3);
	}
	
	public void buscar(int tipo) {
		if(tipo==1) {
			Mayores.add(porEjecucion.get(0));
			for(int i=1;i<porEjecucion.size();i++) {
				if(porEjecucion.get(i).gettEjecucion()==porEjecucion.get(0).gettEjecucion()) {
					Mayores.add(porEjecucion.get(i));
				}
			}
		}
		else {
			if(tipo==2) {
				Mayores.add(Prioridad.get(0));
				for(int i=1;i<Prioridad.size();i++) {
					if(Prioridad.get(i).getPrioridad()==Prioridad.get(0).getPrioridad()) {
						Mayores.add(Prioridad.get(i));
					}
				}
			}
			else {
				Mayores.add(porPID.get(0));
			}
		}
	}
	
	public void buscar2(int tipo) {
		if(tipo==1) {
			Mayores.add(porEjecucion.get(0));
			for(int i=1;i<porEjecucion.size();i++) {
				if(porEjecucion.get(i).gettEjecucion()==porEjecucion.get(0).gettEjecucion()) {
					Mayores.add(porEjecucion.get(i));
				}
			}
			if(!esMayorIgual2(Mayores)) {
				Mayores.add(porEjecucion.get(1));
				for(int i=1;i<porEjecucion.size();i++) {
					if(porEjecucion.get(i).gettEjecucion()==porEjecucion.get(0).gettEjecucion()) {
						Mayores.add(porEjecucion.get(i));
					}
				}
			}
		}
		else {
			if(tipo==2) {
				Mayores.add(Prioridad.get(0));
				for(int i=1;i<Prioridad.size();i++) {
					if(Prioridad.get(i).getPrioridad()==Prioridad.get(0).getPrioridad()) {
						Mayores.add(Prioridad.get(i));
					}
				}
				if(!esMayorIgual2(Mayores)) {
					Mayores.add(porEjecucion.get(1));
					for(int i=1;i<porEjecucion.size();i++) {
						if(porEjecucion.get(i).gettEjecucion()==porEjecucion.get(0).gettEjecucion()) {
							Mayores.add(porEjecucion.get(i));
						}
					}
				}
			}
			else {
				Mayores.add(porPID.get(0));
				Mayores.add(porPID.get(1));
			}
		}
	}
	
	public void filtrar(int tipo) {
		if(tipo==1) {
			Collections.sort(porEjecucion, new Comparator<Proceso>() {
				@Override
				public int compare(Proceso p1, Proceso p2) {
					return p1.gettEjecucion().compareTo(p2.gettEjecucion());
				}
			});
		}
		else {
			if(tipo==2) {
				Collections.sort(Prioridad, new Comparator<Proceso>() {
					@Override
					public int compare(Proceso p1, Proceso p2) {
						return p2.getPrioridad().compareTo(p1.getPrioridad());
					}
				});
			}
			else {
				Collections.sort(porPID, new Comparator<Proceso>() {
					@Override
					public int compare(Proceso p1, Proceso p2) {
						return p1.getPid().compareTo(p2.getPid());
					}
				});
			}
		}
	}
	
	public void filtraPIDD() {
		Collections.sort(porPID, new Comparator<Proceso>() {
			@Override
			public int compare(Proceso p1, Proceso p2) {
				return p2.getPid().compareTo(p1.getPid());
			}
		});
	}
	public boolean esUno(ArrayList<Proceso> filtro) {
		if(filtro.size()==1 || filtro.isEmpty())
			return true;
		else
			return false;
	}
	
	public boolean esDos(ArrayList<Proceso> filtro) {
		if(filtro.size()<=2)
			return true;
		else
			return false;
	}
	
	public boolean esMayorIgual2(ArrayList<Proceso> filtro) {
		if(filtro.size()>=2)
			return true;
		else
			return false;
	}
	
	public void procesalo(Proceso p) {
		for(int i=0;i<p.gettEjecucion();i++) {
			canal1.add(p);
			incrementaTiempo();
		}
		p.settEspera(tiempo-(p.gettLlegada()+p.gettEspera()));
		p.terminalo();
		
	}
	
	public void procesalo2(Proceso p) {
		canal1.add(p);
		incrementaTiempo();
		p.settEjecucion(p.gettEjecucion()-1);
		if(p.gettEjecucion()>0) {
			c.encolar(p);
		}
		else {
			p.settEspera(tiempo-(p.gettLlegada()+p.gettEspera()));
			p.terminalo();
		}	
	}
	
	public void procesalo3(Proceso p) {
		for(int i=0;i<p.gettEjecucion();i++) {
			if((i%2==0)) {
				if(p.gettEjecucion()==1) {
					canal1.add(p);
					canal2.add(null);
					incrementaTiempo();
				}
				else
					canal1.add(p);
			}
			else {
				canal2.add(p);
				incrementaTiempo();
			}
			
		}
		p.settEspera(tiempo-(p.gettLlegada()+p.gettEspera()));
		p.terminalo();
	}
	
	public void procesalo4(Proceso p) {
		canal1.add(p);
		p.settEjecucion(p.gettEjecucion()-1);
		if(p.gettEjecucion()>0) {
			c.encolar(p);
		}
		else {
			p.terminalo();
		}
	}
	
	public void procesalo5(Proceso p) {
		canal2.add(p);
		if(p!=null) {
			p.settEjecucion(p.gettEjecucion()-1);
			if(p.gettEjecucion()>0) {
				c.encolar(p);
			}
			else {
				p.settEspera(tiempo-(p.gettLlegada()+p.gettEspera()));
				p.terminalo();
			}
		}
		incrementaTiempo();
	}
	
	public void limpiaListas() {
		canal1.clear();
		canal2.clear();
		llegadas.clear();
		porEjecucion.clear();
		Prioridad.clear();
		porPID.clear();
		Mayores.clear();
		tiempo=0;
	}
	
	public Proceso buscaEnCola(Proceso p) {
		
		for(int i=0;i<c.getNumE();i++) {
			Proceso aux=c.getDato(i);
			if(aux==p)
				return p;
		}
		return null;
	}
	
	public void dibujar(Graphics2D g2, JPanel Lienzo){
		for(Proceso p: canal1) {
	 		p.dibujar(g2);
	 	}
		for(Proceso p: canal2) {
			if(p!=null)
				p.dibujar(g2);
	 	}

	}
	
	public ArrayList<Proceso> getCanal1(){
		return canal1;
	}
	
	public ArrayList<Proceso> getCanal2(){
		return canal2;
	}
	
	public int getTiempo() {
		return tiempo;
	}
}
