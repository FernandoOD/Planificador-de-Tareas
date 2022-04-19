/**
 * Javier Abell�n, 26 Oct 2003
 *
 * ModeloTabla.java
 *
 * Modelo de tabla para el ejmplo de uso del JTable
 */
import javax.swing.table.*;
import javax.swing.event.*;

import java.awt.Color;
import java.util.LinkedList;

/** 
 * Modelo de personas. Cada fila es una persona y las columnas son los datos
 * de la persona.
 * Implementa TableModel y dos m�todos para a�adir y eliminar Personas del
 * modelo 
 */
public class ModeloTabla implements TableModel
{
    
    /** Returns the number of columns in the model. A
     * <code>JTable</code> uses this method to determine how many columns it
     * should create and display by default.
     *
     * @return the number of columns in the model
     * @see #getRowCount
     *
     */
    public int getColumnCount() {
        // Devuelve el n�mero de columnas del modelo, que coincide con el
        // n�mero de datos que tenemos de cada persona.
        return 6;
    }
    
    /** Returns the number of rows in the model. A
     * <code>JTable</code> uses this method to determine how many rows it
     * should display.  This method should be quick, as it
     * is called frequently during rendering.
     *
     * @return the number of rows in the model
     * @see #getColumnCount
     *
     */
    public int getRowCount() {
        // Devuelve el n�mero de personas en el modelo, es decir, el n�mero
        // de filas en la tabla.
        return datos.size();
    }
    
    /** Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param	rowIndex	the row whose value is to be queried
     * @param	columnIndex 	the column whose value is to be queried
     * @return	the value Object at the specified cell
     *
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        Proceso aux;
        
        // Se obtiene la persona de la fila indicada
        aux = (Proceso)(datos.get(rowIndex));
        
        // Se obtiene el campo apropiado seg�n el valor de columnIndex
        switch (columnIndex)
        {
            case 0:
                return aux.getPid();
            case 1:
                return aux.getColor();
            case 2:
                return aux.gettLlegada();
            case 3:
                return aux.gettEjecucion();
            case 4:
                return aux.getPrioridad();
            case 5:
                return aux.gettEspera();
            default:
                return null;
        }
    }
    
    /**
     * Borra del modelo la persona en la fila indicada 
     */
    public void borraProceso (int fila)
    {
        // Se borra la fila 
        datos.remove(fila);
        
        // Y se avisa a los suscriptores, creando un TableModelEvent...
        TableModelEvent evento = new TableModelEvent (this, fila, fila, 
            TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
        
        // ... y pas�ndoselo a los suscriptores
        avisaSuscriptores (evento);
    }
    
    /**
     * A�ade una persona al final de la tabla
     */
    public void AgregaProceso (Proceso nuevoProceso)
    {
        // A�ade la persona al modelo 
        datos.add (nuevoProceso);
        
        // Avisa a los suscriptores creando un TableModelEvent...
        TableModelEvent evento;
        evento = new TableModelEvent (this, this.getRowCount()-1,
            this.getRowCount()-1, TableModelEvent.ALL_COLUMNS,
            TableModelEvent.INSERT);

        // ... y avisando a los suscriptores
        avisaSuscriptores (evento);
    }
    
    /** Adds a listener to the list that is notified each time a change
     * to the data model occurs.
     *
     * @param	l		the TableModelListener
     *
     */
    public void addTableModelListener(TableModelListener l) {
        // A�ade el suscriptor a la lista de suscriptores
        listeners.add (l);
    }
    
    /** Returns the most specific superclass for all the cell values
     * in the column.  This is used by the <code>JTable</code> to set up a
     * default renderer and editor for the column.
     *
     * @param columnIndex  the index of the column
     * @return the common ancestor class of the object values in the model.
     *
     */
    public Class getColumnClass(int columnIndex) {
        // Devuelve la clase que hay en cada columna.
        switch (columnIndex)
        {
            case 0:
                // La columna cero contiene el nombre de la persona, que es
                // un String
                return Integer.class;
            case 1:
                // La columna uno contiene el apellido de la persona, que es
                // un String
                return Color.class;
            case 2:
                // La columna dos contine la edad de la persona, que es un
                // Integer (no vale int, debe ser una clase)
                return Integer.class;
            case 3:
                // La columna dos contine la edad de la persona, que es un
                // Integer (no vale int, debe ser una clase)
                return Integer.class;
            case 4:
                // La columna dos contine la edad de la persona, que es un
                // Integer (no vale int, debe ser una clase)
                return Integer.class;
            case 5:
                // La columna dos contine la edad de la persona, que es un
                // Integer (no vale int, debe ser una clase)
                return Integer.class;
            default:
                // Devuelve una clase Object por defecto.
                return Object.class;
        }
    }
    
    /** Returns the name of the column at <code>columnIndex</code>.  This is used
     * to initialize the table's column header name.  Note: this name does
     * not need to be unique; two columns in a table can have the same name.
     *
     * @param	columnIndex	the index of the column
     * @return  the name of the column
     *
     */
    public String getColumnName(int columnIndex) 
    {
        // Devuelve el nombre de cada columna. Este texto aparecer� en la
        // cabecera de la tabla.
        switch (columnIndex)
        {
            case 0:
                return "PID";
            case 1:
                return "Color";
            case 2:
                return "T.Arrive";
            case 3:
                return "T.Exe";
            case 4:
                return "Prioridad";
            case 5:
                return "T.Wait";
            default:
                return null;
        }
    }
    
    /** Returns true if the cell at <code>rowIndex</code> and
     * <code>columnIndex</code>
     * is editable.  Otherwise, <code>setValueAt</code> on the cell will not
     * change the value of that cell.
     *
     * @param	rowIndex	the row whose value to be queried
     * @param	columnIndex	the column whose value to be queried
     * @return	true if the cell is editable
     * @see #setValueAt
     *
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Permite que la celda sea editable.
        return true;
    }
    
    /** Removes a listener from the list that is notified each time a
     * change to the data model occurs.
     *
     * @param	l		the TableModelListener
     *
     */
    public void removeTableModelListener(TableModelListener l) {
        // Elimina los suscriptores.
        listeners.remove(l);
    }
    
    /** Sets the value in the cell at <code>columnIndex</code> and
     * <code>rowIndex</code> to <code>aValue</code>.
     *
     * @param	aValue		 the new value
     * @param	rowIndex	 the row whose value is to be changed
     * @param	columnIndex 	 the column whose value is to be changed
     * @see #getValueAt
     * @see #isCellEditable
     *
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) 
    {
        // Obtiene la persona de la fila indicada
        Proceso aux;
        aux = (Proceso)(datos.get(rowIndex));
        
        // Cambia el campo de Persona que indica columnIndex, poniendole el 
        // aValue que se nos pasa.
        switch (columnIndex)
        {
            case 0:
                aux.setPid ((Integer)aValue);
                break;
            case 1:
            	
                aux.setColor ((Color)aValue);
                break;
            case 2:
                aux.settLlegada ((Integer)aValue);
                break;
            case 3:
                aux.settEjecucion ((Integer)aValue);
                break;
            case 4:
                aux.setPrioridad ((Integer)aValue);
                break;
            case 5:
                aux.settEspera ((Integer)aValue);
                break;
            default:
                break;
        }
        
        // Avisa a los suscriptores del cambio, creando un TableModelEvent ...
        TableModelEvent evento = new TableModelEvent (this, rowIndex, rowIndex, 
            columnIndex);

        // ... y pas�ndoselo a los suscriptores.
        avisaSuscriptores (evento);
    }
    
    /**
     * Pasa a los suscriptores el evento.
     */
    private void avisaSuscriptores (TableModelEvent evento)
    {
        int i;
        
        // Bucle para todos los suscriptores en la lista, se llama al metodo
        // tableChanged() de los mismos, pas�ndole el evento.
        for (i=0; i<listeners.size(); i++)
            ((TableModelListener)listeners.get(i)).tableChanged(evento);
    }
    
    /** Lista con los datos. Cada elemento de la lista es una instancia de
     * Persona */
    private LinkedList datos = new LinkedList();
    
    /** Lista de suscriptores. El JTable ser� un suscriptor de este modelo de
     * datos */
    private LinkedList listeners = new LinkedList();
}
