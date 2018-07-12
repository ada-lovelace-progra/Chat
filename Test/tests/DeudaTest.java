package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import usuariosYAsistente.Asistente;

public class DeudaTest {

	public final static String USUARIO = "delucas";

	Asistente jenkins;

	@Before
	public void setup() {
		jenkins = new Asistente();
		jenkins.escuchar(USUARIO + ": hola @jenkins");
	}

	@Test
	public void transferenciaDeDeudas() {
		Assert.assertEquals("Jenkins: Deuda agendada @delucas", jenkins.escuchar(USUARIO + ": @juan me debe $50 @jenkins").substring(4));
		Assert.assertEquals("Jenkins: @juan te debe $50 @delucas", jenkins.escuchar(USUARIO + ": cu�nto me debe @juan? @jenkins").substring(4));
		Assert.assertEquals("Jenkins: Deuda agendada @delucas", jenkins.escuchar(USUARIO + ": le debo $60 a @maria @jenkins").substring(4));

		Assert.assertEquals("Jenkins: @juan te debe $50, le deb�s $60 a @maria @delucas",
				jenkins.escuchar(USUARIO + ": cual es mi estado de deudas? @jenkins").substring(4));

		Assert.assertEquals("Jenkins: listo. @delucas",
				jenkins.escuchar(USUARIO + ": simplificar deudas con @juan y @maria @jenkins").substring(4));

		Assert.assertEquals("Jenkins: le deb�s $10 a @maria @delucas",
				jenkins.escuchar(USUARIO + ": cual es mi estado de deudas? @jenkins").substring(4));
		// por detr�s, ahora @juan le debe $50 a @maria. Podr�a probarse,
		// cambiando el interlocutor del asistente
	}

	@Test
	public void deudasGrupalesCasoUno() {

		Assert.assertEquals("Jenkins: gasto grupal anotado @delucas",
				jenkins.escuchar(USUARIO + ": con @juan y @maria gastamos $300 y pag� @juan @jenkins").substring(4));

		Assert.assertEquals("Jenkins: @maria te debe $90 @delucas",
				jenkins.escuchar(USUARIO + ": cual es mi estado de deudas? @jenkins").substring(4));
		// @maria le debe otros $100 a @juan

	}

	@Test
	public void deudasGrupalesCasoDos() {
		Assert.assertEquals("Jenkins: gasto grupal anotado @delucas",
				jenkins.escuchar(USUARIO + ": con @juan y @maria gastamos $300 y pagu� yo @jenkins").substring(4));

		Assert.assertEquals("Jenkins: @juan te debe $100, @maria te debe $90 @delucas",
				jenkins.escuchar(USUARIO + ": cual es mi estado de deudas? @jenkins").substring(4));
	}

	@Test
	public void deudasSimples() {
		Assert.assertEquals("Jenkins: Deuda agendada @delucas", jenkins.escuchar(USUARIO + ": @juan me debe $500 @jenkins").substring(4));
		Assert.assertEquals("Jenkins: @juan te debe $500 @delucas",
				jenkins.escuchar(USUARIO + ": cu�nto me debe @juan? @jenkins").substring(4));

		Assert.assertEquals("Jenkins: Pago agendado @delucas", jenkins.escuchar(USUARIO + ": @juan me pag� $501 @jenkins").substring(4));
		Assert.assertEquals("Jenkins: @juan no te debe nada, le deb�s $1 a @juan @delucas",
				jenkins.escuchar(USUARIO + ": cu�nto me debe @juan? @jenkins").substring(4));
		Assert.assertEquals("Jenkins: le deb�s $1 a @juan @delucas",
				jenkins.escuchar(USUARIO + ": cu�nto le debo a @juan? @jenkins").substring(4));

		Assert.assertEquals("Jenkins: Pago agendado @delucas", jenkins.escuchar(USUARIO + ": le pagu� a @juan $10 @jenkins").substring(4));
		Assert.assertEquals("Jenkins: @juan te debe $9 @delucas", jenkins.escuchar(USUARIO + ": cu�nto me debe @juan? @jenkins").substring(4));
		Assert.assertEquals("Jenkins: No le debes nada a @juan, @juan te debe $9 @delucas",
				jenkins.escuchar(USUARIO + ": cu�nto le debo a @juan? @jenkins").substring(4));
	}

}