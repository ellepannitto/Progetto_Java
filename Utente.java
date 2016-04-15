import java.io.Serializable;
import java.util.*;

public class Utente implements Serializable
{
	private static int next_id = 0;
	
	private final int id;
	private String nome;
	private String cognome;
	private Date data_di_nascita;
	
	
	
	public Utente (String nome, String cognome, Date data_di_nascita)
	{
		this.nome=nome;
		this.cognome=cognome;
		this.data_di_nascita=data_di_nascita;
		this.id = next_id;
		next_id++;
	}
	
	public Utente (String nome, String cognome)
	{
		this.nome=nome;
		this.cognome=cognome;

		this.id = next_id;
		next_id++;
	}
	
	public String toString ()
	{
		return this.nome+" "+this.cognome;
	}
	
	public int hashCode ()
	{
		return this.id;
	}
	
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
	
	public int getId()
	{
		return this.id;
	}
	
}
