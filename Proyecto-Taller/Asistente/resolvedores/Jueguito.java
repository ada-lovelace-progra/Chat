package resolvedores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * Resolvedor, juega mayor-menor con el usuario.
 * (o al menos eso intenta)
 * 
 */
public class Jueguito extends RespuestaGenerico {
	private boolean EstoyJugando = false, EstoyAdivinando = false;
	private int yoDije = 0, max = 0, min = 0, pensado = 0;

	public String intentarResponder(String mensaje) {
		if (consulta(mensaje))
			if (EstoyJugando) {
				if (EstoyAdivinando) {
					return adivinar(mensaje);
				} else {
					return pensando(mensaje);
				}
			} else {
				EstoyJugando = true;
				EstoyAdivinando = true;
				return "Ok. Empeza vos, ";
			}
		return null;
	}

	private String pensando(String mensaje) {
		if (pensado == 0)
			pensado = (int) (Math.random() * 99999);
		Matcher numeroP = Pattern.compile("([0-9]+)").matcher(mensaje);
		if (numeroP.find()) {
			int numero = Integer.parseInt(numeroP.group(1));
			if (numero > pensado)
				return "nono... mas chico";
			if (numero < pensado)
				return "nono... mas grande";
			EstoyAdivinando = true;
			pensado = 0;
			return "correcto... ganaste... ahora pensa vos...";

		}
		return "que me mandaste papu??? cualquiera...";
	}

	private String adivinar(String mensaje) {
		if (yoDije == 0) {
			yoDije = (int) (Math.random() * 999);
			return "es " + yoDije + "?";
		}
		if (mensaje.contains("chico")) {
			max = yoDije;
		} else if (mensaje.contains("grande")) {
			min = yoDije;
		} else {
			EstoyAdivinando = false;
			yoDije = max = min = 0;
			return "GANEE!!!!\r\nIgual lo sabia... siempre gano... ahora yo pienso";
		}
		yoDije = (int) ((Math.random() * (max - min)) + min);
		return "es " + yoDije + "?";
	}

}
