package resolvedores;


/** 
 * Resolvedor, son las leyes de la robotica.
 */
public class LeyesRobotica extends RespuestaGenerico {

	String resp = "1- Un robot no debe da�ar a un ser humano o, por su inacci�n, dejar que un ser humano sufra da�o.\r\n"
			+ "2- Un robot debe obedecer las �rdenes que le son dadas por un ser humano, excepto si estas �rdenes entran en conflicto con la Primera Ley.\r\n"
			+ "3- Un robot debe proteger su propia existencia, hasta donde esta protecci�n no entre en conflicto con la Primera o la Segunda Ley.";

	public String intentarResponder(String mensaje) {
		if (consulta(mensaje))
			return resp;
		return null;
	}

}
