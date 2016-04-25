import java.util.*;

/**
 * 
 * Eccezione che viene lanciata se si verifica un errore nel gestire le relazioni
 * 
 * */
public class RelationException extends Exception
{
	/**
	 * 
	 * Costruisce un'eccezione a partire dal messaggio di errore
	 * 
	 * */
	public RelationException (String m)
	{
		super(m);
	}
}
