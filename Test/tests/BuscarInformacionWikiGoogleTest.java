package tests;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usuariosYAsistente.Asistente;

public class BuscarInformacionWikiGoogleTest {

	public final static String USUARIO = "delucas";

	static Asistente ada;

	@BeforeClass
	public static void setup() {
		ada = new Asistente();
		escucha("Hola @Ada");
	}

	private static String escucha(String mensaje) {
		return ada.escuchar(USUARIO + ": " + mensaje).substring(4);
	}

	@Test
	public void wikipedia() {
		String respuesta = "Ada: Segun Wikipedia La programaci�n es un proceso que se utiliza para idear y ordenar las <b>acciones</b> que se realizar�n en el marco de un proyecto;al anuncio de las partes que componen un acto o espect�culo; a la preparaci�n de m�quinas para que cumplan con una cierta tarea en un momento determinado; a la elaboraci�n de programas para la resoluci�n de problemas mediante ordenadores; y a la preparaci�n de los datos necesarios para obtener una soluci�n de un problema. @delucas";
		String[] mensajes = { "@ada me buscas informacion sobre programacion" };
		for (String mensaje : mensajes) {
			Assert.assertEquals(respuesta, escucha(mensaje));
		}
	}

}
