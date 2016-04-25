import java.util.*;

/**
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
