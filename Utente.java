import java.io.Serializable;
import java.util.*;

/**
 * @author Corradini Celestino, mat.
 * @author Mercadante Giulia, mat.
 * @author Pannitto Ludovica , mat. 491094
 * @author Rambelli Giulia, mat.
 * 
 * */

/**
 * Memorizza informazioni relative all'utente.
 * 
 * */
public class Utente implements Serializable
{
	//~ private static int next_id = 0;
	
	//~ private final int id;
	private String nome;
	private String cognome;
	private Date data_di_nascita;
	
	
	/**
	 * Inizializza un oggetto di tipo utente.
	 * 
	 * @param nome	nome dell'utente
	 * @param cognome	cognome dell'utente
	 * @param data_di_nascita	data di nascita dell'utente
	 * 
	 * */
	public Utente (String nome, String cognome, Date data_di_nascita)
	{
		this.nome=nome;
		this.cognome=cognome;
		this.data_di_nascita=data_di_nascita;
		//~ this.id = next_id;
		//~ next_id++;
	}
	/**
	 * Inizializza un oggetto di tipo utente.
	 * 
	 * @param nome	nome dell'utente
	 * @param cognome	cognome dell'utente
	 * 
	 * */
	public Utente (String nome, String cognome)
	{
		this.nome=nome;
		this.cognome=cognome;

		//~ this.id = next_id;
		//~ next_id++;
	}
	
	/**
	 * 
	 * @return la stringa che rappresenta l'utente.
	 * 
	 * */
	public String toString ()
	{
		return this.nome+" "+this.cognome;
	}
	
	/**
	 * @return l'hashcode dell'utente
	 * */
	public int hashCode ()
	{
		return (this.nome+" "+this.cognome).hashCode();
	}
	
	
	/**
	 * 
	 * @return 	true se l'utente coincide con il parametro
	 * 			false altrimenti
	 * */
	public boolean equals (Object o)
	{
		boolean ret=false;
	
		if (o != null && o instanceof Utente)
		{			
			if (this == ((Utente)o))
			{
				ret=true;
			}
		}
		return ret;
	}
	
	//~ public int getId()
	//~ {
		//~ return this.id;
	//~ }
	
	/**
	 * 
	 * @return il nome dell'utente
	 * */
	public String getNome()
	{
		return this.nome;
	}

	/**
	 * @return il cognome dell'utente
	 * 
	 * */
	public String getCognome()
	{
		return this.cognome;
	}
	
}
