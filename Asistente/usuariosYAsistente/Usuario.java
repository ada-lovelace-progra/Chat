package usuariosYAsistente;

import java.util.Hashtable;
import cs.Cliente;

public class Usuario extends UsuarioGenerico {
	private Hashtable<Integer, Cliente> cliente = new Hashtable<>();
	private String codTemp;
	public static String usuariosConectados = "";

	public Usuario(String NombreUsuario) {
		nombre = NombreUsuario;
		usuariosConectados += nombre + "?";
	}

	public int pedirNuevoChat(String userAConectar) {
		int codChat = 0;
		try {
			System.out.println("Solicitando nuevo Chat");
			codTemp = "0000";
			cliente.get(0).enviar("0000nuevoChat" + "|" + userAConectar + "|" + nombre);
			while (codTemp == "0000") {
				System.out.print(".");
				Thread.sleep(300);
			}
			if (codTemp != "----") {
				codChat = Integer.parseInt(codTemp);
				Cliente clienteTemp = new Cliente(5050);
				clienteTemp.enviar(codTemp + nombre);
				cliente.put(codChat, clienteTemp);
				cliente.get(codChat).enviar(codTemp + nombre);
				return codChat;
			}
		} catch (Exception e) {
		}
		return -1;
	}

	public void nuevoChat(int codChat) {
		try {
			System.out.println("intentando levantar conexion... CodChat: " + codChat);
			cliente.put(codChat, new Cliente(5050));
			cliente.get(codChat).enviar(String.format("%04d", codChat) + nombre);
		} catch (Exception e) {
		}
	}

	public void enviar(int codChat, String mensaje) throws Exception {
		cliente.get(codChat).enviar(String.format("%04d", codChat) + nombre + ": " + mensaje);
	}

	public String recibir(int codChat) throws Exception {
		String recibir = cliente.get(codChat).recibir();
		if (recibir.endsWith("-99-00")) {
			cliente.get(codChat).cerrar();
			cliente.remove(codChat);
			return "";
		}
		if (codChat == 0) {
			if (recibir.matches("[0-9]+")) {
				codTemp = recibir;
				System.out.println(codTemp);
				return "";
			} else if (recibir.contains("levantarConexion")) {
				System.out.println("levantando");
				String codChatNuevo = recibir.substring(16, 20);
				nuevoChat(Integer.parseInt(codChatNuevo));
				return recibir;
			} else if (recibir.contains("?")) {
				return recibir.substring(4);
			}
		}
		return recibir.substring(4);
	}
}