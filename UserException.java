import java.util.*;

/**
 * 
 * @author Corradini Celestino, mat. 527813
 * @author Mercadante Giulia, mat. 540938
 * @author Pannitto Ludovica , mat. 491094
 * @author Rambelli Giulia, mat. 495544
 * 
 * Eccezione che viene lanciata se si verificano errori nel gestire utenti
 * 
 * */
public class UserException extends Exception
{
	/**
	 * 
	 * costruisce una nuova eccezione a partire dal messaggio di errore
	 * 
	 * */
	public UserException (String m)
	{
		super(m);
	}
}
