public class Cola {
	private Proceso datos[];
	private int max,frente,fondo,numE;
	
	public Cola() {
		max=10;
		datos=new Proceso[max];
		frente=fondo=0;
		numE=0;
	}
	
	public Cola(int max) {
		if(max>0) {
			this.max=max;
			datos=new Proceso[max];
		}
		else
			System.out.println("Máximo del arreglo inválido");
		frente = fondo=0;
		numE=0;
	}
	
	public boolean encolar(Proceso dato) {
		if(!estaLlena()) {
		datos[fondo]=dato;
		fondo=(fondo+1)%max;
		numE++;
		return true;
		}
		else {
			System.out.println("La cola esta llena");
			return false;
		}
	}
	
	public Proceso desencolar() {
		if(!estaVacia()) {
			Proceso dato=datos[frente];
		frente=(frente +1)%max;
		numE--;
		return dato;
		}
		else
			System.out.println("Cola vacía");
		return null;
	}
	
	public Proceso getDato(int i) {
		Proceso v;
		v=datos[(frente+i)%max];
		return v;
	}

	public int getNumE() {
		return numE;
	}

	public int getFrente() {
		return frente;
	}
	public void setNumE(int numE) {
		this.numE = numE;
	}

	public boolean estaLlena() {
		return (numE==max);
	}
	
	public boolean estaVacia() {
		return (numE==0);
	}
	

	public String toString(){
		String cad="";
		for(int i=0;i<numE;i++) {
			cad=cad+datos[(frente+i)%max]+" ";
		}
		return cad;
		
	}

}