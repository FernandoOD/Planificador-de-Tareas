import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class MiRender extends DefaultTableCellRenderer{
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) { 
		JLabel cell = (JLabel) super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
		if(value instanceof Color) {
			Color color=(Color)value;
			cell.setBackground(color);
			cell.setForeground(color);
		}
		if(value instanceof Integer) {
			cell.setBackground(new Color(30,30,30));
			cell.setForeground(Color.WHITE);
			cell.setHorizontalAlignment(CENTER);
		}
		return cell;
		 
	}

}
