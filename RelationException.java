import java.util.*;

/**
 * @author Corradini Celestino, mat. 527813
 * @author Mercadante Giulia, mat. 540938
 * @author Pannitto Ludovica , mat. 491094
 * @author Rambelli Giulia, mat. 495544
 * 
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
