package videoclub.client.utiles;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class Resaltador implements TableCellRenderer {
	private boolean resaltado;
	private boolean columnaModificable;
	private int[] columna;
	public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

	/**
	 * Creamos el resaltador indicando que columna se coloreara por defecto
	 * 
	 * @param columna
	 */
	public Resaltador(boolean resaltado, boolean columnaModificable, int[] columna) {
		this.resaltado = resaltado;
		this.columnaModificable = columnaModificable;
		this.columna = columna;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// Obtenemos la celda que se esta renderizando
		Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		DEFAULT_RENDERER.setHorizontalAlignment(SwingConstants.CENTER);

		// Solo marcaremos las columnas que quieran ser reslatadas:
		if (resaltado == true) {
			if (isSelected == true) {
				c.setBackground(Color.GREEN);
				c.setForeground(Color.BLACK);
			} else {
				c.setBackground(Color.darkGray);
				c.setForeground(Color.GREEN);
			}

			for (int i = 0; i < columna.length; i++) {
				if (columnaModificable == true && columna[i] == column) {
					c.setBackground(Color.GREEN);
					c.setForeground(Color.RED);
				}
			}

		}
		return c;
	}
}