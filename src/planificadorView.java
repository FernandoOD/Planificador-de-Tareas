import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class planificadorView extends JFrame implements ActionListener{
	private JPanel Titulo,cargaArchivo,sistema,algoritmo,algoritmo2,ejecucion,tblresults,sistema2;
	private Lienzo lienzo;
	private JLabel titulo,Larchivo,Lsistema,Lalgoritmo,Lejecucion;
	private JFileChooser archivoSelect;
	private JTextField nameArch;
	private JButton Examinar,Ejecutar;
	private Scanner entrada;
	private JRadioButton MonoTarea, MonoProceso, MultiTarea, MultiProceso, TP, PT, PA, PD;
	private ButtonGroup bg1,bg2,bg3,bg4;
	private String ruta;
	
	ModeloTabla model = new ModeloTabla();
    final JTable table = new JTable(model);
    JScrollPane scrollPane = new JScrollPane(table);
	
	Color Azul=new Color(42, 149, 255);
	Color Morado=new Color(149, 42, 255);
	Color Verde=new Color(170, 255, 0);
	private Color color[]= {Color.BLUE,Color.RED,Color.GREEN,Color.ORANGE,Color.WHITE,Azul,Color.PINK,Color.YELLOW,Color.MAGENTA,Color.CYAN,Morado,Verde};
	
	private ArrayList<Proceso> procesosLista=new ArrayList<Proceso>();
	
	public planificadorView() {
		super("Planificador de Tareas");
        setLocation(100, 100);
        setLayout(null);
        setPreferredSize(new Dimension(800,600));
        
        initComponents();
        pack();
	}
	
	public void initComponents() {
		
		bg1 = new ButtonGroup();
		bg2 = new ButtonGroup();
		bg3 = new ButtonGroup();
		bg4 = new ButtonGroup();
		
		//Declaración de los JPanel
		Titulo = new JPanel();
		Titulo.setBackground(new Color(0,0,0));
		Titulo.setBounds(0,0,800,50);
		
		cargaArchivo = new JPanel();
		cargaArchivo.setBackground(new Color(30,30,30));
		cargaArchivo.setBounds(0,53,800,50);
		cargaArchivo.setLayout(null);
		
		sistema = new JPanel();
		sistema.setBackground(new Color(30,30,30));
		sistema.setLayout(new BoxLayout(sistema,BoxLayout.Y_AXIS));
		sistema.setBounds(0,106,398,150);
		
		sistema2 = new JPanel();
		sistema2.setBackground(new Color(30,30,30));
		sistema2.setLayout(new GridLayout(2,2));
		
		algoritmo = new JPanel();
		algoritmo.setBackground(new Color(30,30,30));
		algoritmo.setLayout(new BoxLayout(algoritmo,BoxLayout.Y_AXIS));
		algoritmo.setBounds(402,106,395,150);
		
		algoritmo2 = new JPanel();
		algoritmo2.setBackground(new Color(30,30,30));
		algoritmo2.setLayout(new GridLayout(2,2));
		
		ejecucion = new JPanel();
		ejecucion.setBackground(new Color(30,30,30));
		ejecucion.setLayout(null);
		ejecucion.setBounds(0,260,570,335);

		tblresults = new JPanel();
		tblresults.setBackground(new Color(30,30,30));
		tblresults.setLayout(null);
		tblresults.setBounds(575,260,225,335);
		
		//Declaración de los JLabel
		titulo = new JLabel("Planificador");
		titulo.setFont(new Font("Sans Serif", Font.PLAIN, 20));
		titulo.setForeground(Color.WHITE);
		
		Lsistema = new JLabel("Sistema");
		Lsistema.setFont(new Font("Sans Serif", Font.BOLD, 15));
		Lsistema.setForeground(Color.WHITE);
		Lsistema.setAlignmentX(CENTER_ALIGNMENT);
		
		Larchivo = new JLabel("Lista de Procesos");
		Larchivo.setFont(new Font("Sans Serif", Font.PLAIN, 15));
		Larchivo.setForeground(Color.WHITE);
		Larchivo.setBounds(20,10,150,30);
		
		Lalgoritmo = new JLabel("Algoritmo");
		Lalgoritmo.setFont(new Font("Sans Serif", Font.BOLD, 15));
		Lalgoritmo.setForeground(Color.WHITE);
		Lalgoritmo.setAlignmentX(CENTER_ALIGNMENT);
		
		Lejecucion = new JLabel("Ejecución");
		Lejecucion.setFont(new Font("Sans Serif", Font.BOLD, 15));
		Lejecucion.setForeground(Color.WHITE);
		Lejecucion.setBounds(200,10,80,20);
		
		//Declaración de los JTextField
		nameArch = new JTextField();
		nameArch.setText("  Selecciona un archivo.....");
		nameArch.setBackground(new Color(30,30,30));
		nameArch.setForeground(Color.GREEN);
		nameArch.setBounds(180,10,450,30);
		
		//Declaraci�n de los Botones
		Examinar = new JButton("Examinar");
		Examinar.setBounds(650,10,100,30);
		Examinar.setBackground(Color.GREEN);
		Examinar.addActionListener(this);
		
		Ejecutar = new JButton("Ejecutar");
		Ejecutar.setBounds(0,270,225,30);
		Ejecutar.setBackground(Color.GREEN);
		Ejecutar.addActionListener(this);

		//Declaraci�n de los JRadioButton
		MonoTarea = new JRadioButton("Monotarea");
		MonoTarea.setBackground(null);
		MonoTarea.setForeground(Color.GREEN);
		
		MonoProceso = new JRadioButton("Monoproceso");
		MonoProceso.setBackground(null);
		MonoProceso.setForeground(Color.GREEN);
		
		MultiTarea = new JRadioButton("Multitarea");
		MultiTarea.setBackground(null);
		MultiTarea.setForeground(Color.GREEN);
		
		MultiProceso = new JRadioButton("Multiproceso");
		MultiProceso.setBackground(null);
		MultiProceso.setForeground(Color.GREEN);
		
		TP = new JRadioButton("T. Ejecución----- Prioridad");
		TP.setBackground(null);
		TP.setForeground(Color.GREEN);
		
		PT = new JRadioButton("Prioridad ----- T. Ejecución");
		PT.setBackground(null);
		PT.setForeground(Color.GREEN);
		
		PA = new JRadioButton("PID Ascendente");
		PA.setBackground(null);
		PA.setForeground(Color.GREEN);
		
		PD = new JRadioButton("PID Descendente");
		PD.setBackground(null);
		PD.setForeground(Color.GREEN);
		
		//Grupos de Botones
		bg1.add(MonoTarea);
		bg1.add(MultiTarea);
		bg2.add(MonoProceso);
		bg2.add(MultiProceso);
		bg3.add(TP);
		bg3.add(PT);
		bg4.add(PA);
		bg4.add(PD);
		
		//Agregando componentes a cada contenedor
		Titulo.add(titulo);
		
		cargaArchivo.add(Larchivo);
		cargaArchivo.add(nameArch);
		cargaArchivo.add(Examinar);
		
		sistema.add(Lsistema);
		sistema.add(sistema2);
		
		sistema2.add(MonoTarea);
		sistema2.add(MultiTarea);
		sistema2.add(MonoProceso);
		sistema2.add(MultiProceso);
		
		
		algoritmo.add(Lalgoritmo);
		algoritmo.add(algoritmo2);
		
		algoritmo2.add(TP);
		algoritmo2.add(PA);
		algoritmo2.add(PT);
		algoritmo2.add(PD);
		
		ejecucion.add(Lejecucion);
		ejecucion.add(Ejecutar);

		tblresults.add(Ejecutar);
		
		//Agregando todos los contenedores al principal
		add(Titulo);
		add(cargaArchivo);
		add(sistema);
		add(algoritmo);
		add(ejecucion);
		add(tblresults);
	}
	
	public void actionPerformed(ActionEvent e) {
		String [] textoSeparado = null;
		File archivo = null;
	    FileReader fr = null;
	    BufferedReader br = null;
	    
	    String tarea;
	    int TE_P;
	    int PID;
	    int num=0;
	    
		if(e.getSource()==Examinar) {
			archivoSelect = new JFileChooser();
			archivoSelect.showOpenDialog(archivoSelect);
		    ruta = archivoSelect.getSelectedFile().getAbsolutePath();
			nameArch.setText(ruta);
		}
		if(e.getSource()== Ejecutar) {
			try {                                       
	            archivo = new File(ruta);
	            fr = new FileReader (archivo);
		        br = new BufferedReader(fr);
		        String linea;
		        
		        while((linea=br.readLine())!=null) {
		            textoSeparado=linea.split(" ");
		        	Proceso p = new Proceso(Integer.parseInt(textoSeparado[0]),Integer.parseInt(textoSeparado[1]),Integer.parseInt(textoSeparado[2]),Integer.parseInt(textoSeparado[3]),color[num]);
		        	num=(num+1)%12;
		        	procesosLista.add(p);
		        	}
	        } catch (FileNotFoundException e1) {
	            System.out.println(e1.getMessage());
	        } catch (NullPointerException e1) {
	            System.out.println("No se ha seleccionado ning�n fichero");
	        } catch (Exception e1) {
	            System.out.println(e1.getMessage());
	        } finally {
	            if (entrada != null) {
	                entrada.close();
	            }
	        }
			
			
			if(model.getRowCount()==0) {
				for(Proceso p:procesosLista) {
					model.AgregaProceso(p);
				}
				
				 table.setDefaultRenderer(model.getColumnClass(0), new MiRender());
				 table.setDefaultRenderer(model.getColumnClass(1), new MiRender());
				 table.setDefaultRenderer(model.getColumnClass(1), new MiRender());
				 
			        scrollPane.setViewportView(table);
			        scrollPane.setColumnHeaderView (table.getTableHeader());
			        scrollPane.setBounds(0,0,225,270);
					scrollPane.setOpaque(false);
					scrollPane.getViewport().setOpaque(false);
			        
			        table.setGridColor(new Color(30,30,30));
			        table.setSelectionForeground( Color.white );
					table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			        
					tblresults.add(scrollPane);
			}
			
			if(TP.isSelected())
				TE_P=1;
			else
				TE_P=2;
				
			if(MonoTarea.isSelected())
				tarea="Monotarea";
			else
				tarea="Multitarea";
			
			if(PA.isSelected())
				PID=1;
			else
				PID=2;
			
			if(MonoProceso.isSelected()) {
				lienzo=new Lienzo(procesosLista, tarea, TE_P, PID,1,model);
				lienzo.setBounds(5,35,561,200);
				ejecucion.add(lienzo);
			}
			else {
				lienzo=new Lienzo(procesosLista, tarea, TE_P, PID,2,model);
				lienzo.setBounds(5,35,561,200);
				ejecucion.add(lienzo);
			}
				
		}
	}
}
