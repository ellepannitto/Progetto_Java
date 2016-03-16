import java.util.*;

public class ReteSociale
{


	private Vector<Utente> lista_utenti = new Vector<Utente>();
	private Map<Integer, Vector<Integer>> contatti = new HashMap<Integer, Vector<Integer>>(); 
	
	
	public int addUser(Utente u) throws UserException
	{
		int posizione;

		boolean already_in = this.lista_utenti.contains(u);

		if (already_in)
		{
			throw new UserException ("elemento già presente");
		}
		else
		{
			this.lista_utenti.addElement(u);
			posizione = this.lista_utenti.size()-1;
			this.contatti.put(posizione, new Vector<Integer>());
			
		}
		return posizione;
	}
	
	/**
	 * 
	 * Aggiunge un'amicizia tra due utenti a e b alla rete sociale.
	 * 
	 * @param a è il primo utente
	 * @param b è il secondo utente
	 * 
	 * @throws UserException se l'utente a o l'utente b non fanno parte della rete sociale
	 * @throws RelationException se i due utenti sono già amici
	 * 
	 * */
	public void changeRelation (Utente a, Utente b) throws UserException, RelationException
	{
		int posizione_a = this.lista_utenti.indexOf(a);
		int posizione_b = this.lista_utenti.indexOf(b);
		
		if (posizione_a < 0) 
		{
			throw new UserException ("Utente "+a+" non trovato");
		}
		
		if (posizione_b < 0 )
		{
			throw new UserException ("Utente "+b+" non trovato");
		}
		
		Vector<Integer> amici_di_a = this.contatti.get(posizione_a);
		Vector<Integer> amici_di_b = this.contatti.get(posizione_b);
		
		if (amici_di_a.contains(posizione_b))
		{
			throw new RelationException ("");
		}
		
		if (amici_di_b.contains(posizione_a))
		{
			throw new RelationException ("");
		}
		
		amici_di_a.addElement(posizione_b);
		amici_di_b.addElement(posizione_a);
		
	}
	
	
	public void changeRelation (Utente a, Utente b, boolean add) throws UserException, RelationException
	{
		if (add)
		{
			this.changeRelation(a, b);
		}
		else
		{
			this.removeRelation(a, b);
			
		}	
	}
	
	
	public void removeRelation (Utente a, Utente b) throws UserException, RelationException
	{
		int posizione_a = this.lista_utenti.indexOf(a);
		int posizione_b = this.lista_utenti.indexOf(b);
		
		if (posizione_a < 0) 
		{
			throw new UserException ("Utente "+a+" non trovato");
		}
		
		if (posizione_b < 0 )
		{
			throw new UserException ("Utente "+b+" non trovato");
		}
		
		Vector<Integer> amici_di_a = this.contatti.get(posizione_a);
		Vector<Integer> amici_di_b = this.contatti.get(posizione_b);
		
		if (amici_di_a.contains(posizione_b))
		{
			throw new RelationException ("");
		}
		
		if (amici_di_b.contains(posizione_a))
		{
			throw new RelationException ("");
		}
		
		amici_di_a.remove(posizione_b);
		amici_di_b.remove(posizione_a);
		
	}
	
	public void SuperRemove (Utente a, Utente b) throws UserException, RelationException
	{
		int posizione_a = this.lista_utenti.indexOf(a);
		int posizione_b = this.lista_utenti.indexOf(b);
		
		if (posizione_a < 0) 
		{
			throw new UserException ("Utente "+a+" non trovato");
		}
		
		if (posizione_b < 0 )
		{
			throw new UserException ("Utente "+b+" non trovato");
		}
		
		Vector<Integer> amici_di_a = this.contatti.get(posizione_a);
		Vector<Integer> amici_di_b = this.contatti.get(posizione_b);
		
		if (amici_di_a.contains(posizione_b))
		{
			throw new RelationException ("");
		}
		
		if (amici_di_b.contains(posizione_a))
		{
			throw new RelationException ("");
		}
		
		for (Integer i : amici_di_b)
		{
			try
			{
				Utente u=this.lista_utenti.get(i);
				this.removeRelation(a, u);
			}
			catch (RelationException e)
			{
				;
			}
			
		}	
	}
	
	public Set<Utente> getRelations (Utente a, int d) throws UserException
	{
		int posizione_a = this.lista_utenti.indexOf(a);
		Set<Integer> id_amici;
		
		
		if (posizione_a < 0) 
		{
			throw new UserException ("Utente "+a+" non trovato");
		}
		
		id_amici=this.getRelations_recursive (posizione_a, d, new HashSet<Integer>());
		
		return this.converti(id_amici);
		
		
	}
	
	private Set<Integer> getRelations_recursive (int a, int d, Set<Integer> nodi_visitati)
	{
		
		
		Set<Integer> ret = new HashSet<Integer>();
		if (d<=0)
		{
			ret.add(a);
		}
		else
		{
			Vector<Integer> amici_di_a = this.contatti.get(a);
			nodi_visitati.add(a);
			for (Integer i: amici_di_a)
			{
				if (!nodi_visitati.contains(i))
				{
					ret.addAll(getRelations_recursive(a, d-1, nodi_visitati));
				}
			}
			nodi_visitati.remove(a);
			
			
		}
		return ret;
	}
	
	
	private Set<Utente> converti (Set<Integer> lista)
	{
		Set<Utente> ret=new HashSet<Utente>();
		
		for (Integer i: lista)
		{
			Utente u= this.lista_utenti.get(i);
			ret.add(u);
		}
		
		return ret;
	}
}
