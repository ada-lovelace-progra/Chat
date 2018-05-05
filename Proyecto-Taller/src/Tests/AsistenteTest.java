package Tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ada.Asistente;

public class AsistenteTest {

	public final static String USUARIO = "delucas";

	Asistente ada;

	@Before
	public void setup() {
		ada = new Asistente();
		ada.escuchar("hola ada");
	}

	@Test
	public void agradecimiento() {
		String[] mensajes = { "¡Muchas gracias, @ada!", "@ada gracias", "gracias @ada" };
		for (String mensaje : mensajes) {
			Assert.assertEquals("no es nada", ada.escuchar(mensaje));
		}
	}

	@Test
	public void calculos() {
		Assert.assertEquals("la funcion da: 3", ada.escuchar("@ada resolver 1+2"));

		Assert.assertEquals("la funcion da: 1", ada.escuchar("@ada resolver 5-2*2"));

		Assert.assertEquals("la funcion da: 42", ada.escuchar("@ada resolver 17+5^2"));

	}

	@Test
	public void calcularConNegativos() {
		Assert.assertEquals("la funcion da: 10", ada.escuchar("@ada resolver -1*(((1+1)^3*10)/80-6)*2"));// da un
																									// numero																											// negativo
	}

	@Test
	public void calculosCompuestos() {
		Assert.assertEquals("la funcion da: -112", ada.escuchar("@ada resolver (((1+1)^3*10)/80-6)*2-100-5+3"));
	}

	@Test
	public void porcentaje() {
		Assert.assertEquals("la funcion da: 100", ada.escuchar("@ada resolver 25%400"));
	}

	@Test
	public void mixSupremo() {
		Assert.assertEquals("la funcion da: 15", ada.escuchar("@ada resolver 10%((30+20)+((135-30)-5)^1)"));
	}

	@Test
	public void fecha() {
		Assert.assertEquals("hoy es s�bado, 05 de mayo de 2018", ada.escuchar("@ada cual es la fecha de hoy?"));
		Assert.assertEquals("hoy es s�bado, 05 de mayo de 2018", ada.escuchar("@ada dame la fecha"));
	}

	@Test
	public void hora() {
		String[] mensajes = { "¿qué hora es, @ada?", "@ada, la hora por favor", "me decís la hora @ada?" };
		for (String mensaje : mensajes) {
			Assert.assertEquals("son las 10:53", ada.escuchar(mensaje));
		}
	}

	@Test
	public void dia() {
		String[] mensajes = { "@ada que dia es hoy?", "@ada dia hoy", "@ada decime el dia" };
		for (String mensaje : mensajes) {
			Assert.assertEquals("hoy es Sabado", ada.escuchar("@ada que dia es hoy?"));
		}
	}
}
