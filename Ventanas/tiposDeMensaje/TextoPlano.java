package tiposDeMensaje;

import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import java.awt.GridBagLayout;

public class TextoPlano extends JPanel {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	
	public static GridBagConstraints gbc() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		return gbc;
	}

	public TextoPlano(String mensaje) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{118, 0};
		gridBagLayout.rowHeights = new int[]{20, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		setBorder(null);
		GridBagConstraints gbc_txtpnAsd = new GridBagConstraints();
		gbc_txtpnAsd.fill = GridBagConstraints.BOTH;
		gbc_txtpnAsd.gridx = 0;
		gbc_txtpnAsd.gridy = 0;
		JTextPane txtpnAsd = new JTextPane();
		txtpnAsd.setText(mensaje);
		txtpnAsd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtpnAsd.setEditable(false);
		add(txtpnAsd, gbc_txtpnAsd);
	}
}