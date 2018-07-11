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
		Assert.assertEquals("Jenkins: @delucas, @juan te debe $50 @delucas", jenkins.escuchar(USUARIO + ": cu�nto me debe @juan? @jenkins").substring(4));
		Assert.assertEquals("Jenkins: Deuda agendada @delucas", jenkins.escuchar(USUARIO + ": le debo $60 a @maria @jenkins").substring(4));

		Assert.assertEquals("@delucas le deb�s $60 a @maria. @juan te debe $50",
				jenkins.escuchar(USUARIO + ": cual es mi estado de deudas? @jenkins").substring(4));

		Assert.assertEquals("@delucas bueno.",
				jenkins.escuchar(USUARIO + ": simplificar deudas con @juan y @maria @jenkins").substring(4));

		Assert.assertEquals("@delucas le deb�s $10 a @maria",
				jenkins.escuchar(USUARIO + ": cual es mi estado de deudas? @jenkins").substring(4));
		// por detr�s, ahora @juan le debe $50 a @maria. Podr�a probarse,
		// cambiando el interlocutor del asistente
	}

	@Test
	public void deudasGrupalesCasoUno() {

		Assert.assertEquals("@delucas anotado.",
				jenkins.escuchar(USUARIO + ": con @juan y @maria gastamos $300 y pag� @juan @jenkins").substring(4));

		Assert.assertEquals("@delucas le deb�s $100 a @juan",
				jenkins.escuchar(USUARIO + ": cual es mi estado de deudas? @jenkins").substring(4));
		// @maria le debe otros $100 a @juan

	}

	@Test
	public void deudasGrupalesCasoDos() {
		Assert.assertEquals("@delucas anotado.",
				jenkins.escuchar(USUARIO + ": con @juan y @maria gastamos $300 y pagu� yo @jenkins").substring(4));

		Assert.assertEquals("@delucas @juan te debe $100. @maria te debe $100",
				jenkins.escuchar(USUARIO + ": cual es mi estado de deudas? @jenkins").substring(4));
	}

	@Test
	public void deudasSimples() {
		Assert.assertEquals("@delucas anotado.", jenkins.escuchar(USUARIO + ": @juan me debe $500 @jenkins").substring(4));
		Assert.assertEquals("@delucas @juan te debe $500",
				jenkins.escuchar(USUARIO + ": cu�nto me debe @juan? @jenkins").substring(4));

		Assert.assertEquals("@delucas anotado.", jenkins.escuchar(USUARIO + ": @juan me pag� $501 @jenkins").substring(4));
		Assert.assertEquals("@delucas @juan no te debe nada. Vos le deb�s $1",
				jenkins.escuchar(USUARIO + ": cu�nto me debe @juan? @jenkins").substring(4));
		Assert.assertEquals("@delucas deb�s $1 a @juan",
				jenkins.escuchar(USUARIO + ": cu�nto le debo a @juan? @jenkins").substring(4));

		Assert.assertEquals("@delucas anotado.", jenkins.escuchar(USUARIO + ": le pagu� a @juan $10 @jenkins").substring(4));
		Assert.assertEquals("@delucas @juan te debe $9", jenkins.escuchar(USUARIO + ": cu�nto me debe @juan? @jenkins").substring(4));
		Assert.assertEquals("@delucas no le deb�s nada. @juan te debe $9",
				jenkins.escuchar(USUARIO + ": cu�nto le debo a @juan? @jenkins").substring(4));
	}

}