package vistas;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URI;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import usuariosYAsistente.Usuario;

public class Pestana {

	private Font fuente = new Font("Tahoma", Font.PLAIN, 11);
	private Usuario usuario;

	public Pestana(Usuario usuario) {
		this.usuario = usuario;
	}

	public JPanel nuevo(int codChat) {
		JPanel panel = new JPanel();
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 394, 0 };
		gbl_panel.rowHeights = new int[] { 200, 28, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel.add(scrollPane, gbc_scrollPane);

		JEditorPane mensajes = new JEditorPane();
		mensajes.setContentType("text/html");
		mensajes.setEditable(false);
		mensajes.setFont(fuente);
		scrollPane.setViewportView(mensajes);

		JEditorPane textEnviar = new JEditorPane();
		textEnviar.setContentType("text/plain");
		textEnviar.setFont(fuente);
		GridBagConstraints gbc_textEnviar = new GridBagConstraints();
		gbc_textEnviar.fill = GridBagConstraints.BOTH;
		gbc_textEnviar.gridx = 0;
		gbc_textEnviar.gridy = 1;
		panel.add(textEnviar, gbc_textEnviar);

		setearEventos(codChat, textEnviar, mensajes);
		cargaMensajesNuevosHilo(codChat, mensajes);

		return panel;
	}

	private void setearEventos(int codChat, JEditorPane textEnviar, JEditorPane mensajes) {
		mensajes.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent hLinkEv) {
				String url = null;
				URL uURL = hLinkEv.getURL();
				if (uURL != null)
					url = uURL.toString();
				else
					url = hLinkEv.getDescription();

				if (hLinkEv.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					System.out.println("url: " + url);
					if (Desktop.isDesktopSupported())
						try {
							Desktop.getDesktop().browse(new URI(url));
							;
						} catch (Exception e1) {
							System.out.println("fallo Desktop");
						}
					else
						try {
							new ProcessBuilder(url).start();
						} catch (Exception e2) {
							System.out.println("fallto tmabien forma fea...");
							try {
								String comando = url;
								Runtime.getRuntime().exec("start ");
								Runtime.getRuntime().exec(comando);
							} catch (Exception e3) {
								System.out.println("se acabo todo... todillo");
							}
						}
				} // fin if activated
			}
		});
		mensajes.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				mensajes.setCaretPosition(mensajes.getDocument().getLength());
			}

		});

		textEnviar.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyChar() == '\n') {
					enviarMensaje(textEnviar, mensajes, codChat);
					textEnviar.setText("");
				} else if (textEnviar.getText().length() > 2) {
					char ch = textEnviar.getText().charAt(0);
					if (ch == '\n' || ch == '\r')
						textEnviar.setText(textEnviar.getText().substring(1));
				}
			}
		});
	}

	private void cargaMensajesNuevosHilo(int codChat, JEditorPane mensajes) {
		new Thread() {
			public void run() {
				cargaContenidoDeChat();
				try {
					while (true) {
						String recibido = usuario.recibir(codChat);
						if (recibido.contains(":zumbido:"))
							new Zumbido().start();
						HTMLDocument doc = (HTMLDocument) mensajes.getDocument();
						HTMLEditorKit editorKit = (HTMLEditorKit) mensajes.getEditorKit();
						editorKit.insertHTML(doc, doc.getLength(), recibido, 0, 0, null);
						mensajes.setCaretPosition(doc.getLength());
					}
				} catch (Exception e) {
					System.out.println("error recibiendo el mensaje");
				}
			}

			private void cargaContenidoDeChat() {
				try {
					mensajes.setContentType("text/html");
					HTMLDocument doc = (HTMLDocument) mensajes.getDocument();
					HTMLEditorKit editorKit = (HTMLEditorKit) mensajes.getEditorKit();
					editorKit.insertHTML(doc, doc.getLength(),
							"<HTML>\r\n" + "<HEAD>\r\n" + "</HEAD>\r\n" + "<BODY>\r\n" + "</BODY>\r\n" + "</HTML>", 0,
							0, null);
					// JScrollBar vertical = scrollPane.getVerticalScrollBar();
					mensajes.setCaretPosition(mensajes.getDocument().getLength());
				} catch (Exception e) {
				}
			}
		}.start();
	}

	private void enviarMensaje(JEditorPane textEnviar, JEditorPane mensajes, int codChat) {
		String mensaje = textEnviar.getText();
		if (mensaje.length() > 0 && !mensaje.equals("\n")) {
			textEnviar.setText("");
			mensaje = Codificaciones.codificar(mensaje);
			HTMLDocument doc = (HTMLDocument) mensajes.getDocument();
			try {
				HTMLEditorKit editorKit = (HTMLEditorKit) mensajes.getEditorKit();
				editorKit.insertHTML(doc, doc.getLength(), usuario.nombre + ": " + mensaje, 0, 0, null);
				usuario.enviar(codChat, mensaje);
				mensajes.setCaretPosition(mensajes.getDocument().getLength());
			} catch (Exception e) {
			}
		}
	}

}