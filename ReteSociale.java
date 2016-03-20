import java.util.*;

public class ReteSociale
{


	private Map<Integer, Utente> mappa_utenti = new HashMap<Integer, Utente>();
	private Map<Integer, Vector<Integer>> contatti = new HashMap<Integer, Vector<Integer>>(); 
	
	
	public int addUser(Utente u) throws UserException
	{
		boolean already_in = this.mappa_utenti.containsKey(u.getId());

		if (already_in)
		{
			throw new UserException ("elemento "+u+" già presente");
		}
		else
		{
			this.mappa_utenti.put(u.getId(), u);
			this.contatti.put(u.getId(), new Vector<Integer>());
			
		}
		
		return u.getId();
	}
	
	public void removeUser (Utente u) throws UserException
	{
		boolean already_in = this.mappa_utenti.containsKey(u.getId());

		if (!already_in)
		{
			throw new UserException ("elemento "+u+" già presente");
		}
		else
		{
			Vector<Integer> lista_amici=this.contatti.get(u.getId());
			
			Vector<Integer> copia_lista_amici = new Vector<Integer> (lista_amici);
			
			
			for (Integer i: copia_lista_amici)
			{
				try
				{
					this.removeRelation(this.mappa_utenti.get(i), u);
				}
				catch (RelationException e)
				{
					;
				}
			}
			
		}
		
		this.contatti.remove(u.getId());
		this.mappa_utenti.remove(u.getId());
		
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
		boolean a_already_in = this.mappa_utenti.containsKey(a.getId());
		boolean b_already_in = this.mappa_utenti.containsKey(b.getId());
			
		if (!a_already_in) 
		{
			throw new UserException ("Utente "+a+" non trovato");
		}
		
		if (!b_already_in )
		{
			throw new UserException ("Utente "+b+" non trovato");
		}
		
		Vector<Integer> amici_di_a = this.contatti.get(a.getId());
		Vector<Integer> amici_di_b = this.contatti.get(b.getId());
		
		if (amici_di_a.contains(b.getId()))
		{
			throw new RelationException ("");
		}
		
		if (amici_di_b.contains(a.getId()))
		{
			throw new RelationException ("");
		}
		
		amici_di_a.addElement(b.getId());
		amici_di_b.addElement(a.getId());
		
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
		boolean a_already_in = this.mappa_utenti.containsKey(a.getId());
		boolean b_already_in = this.mappa_utenti.containsKey(b.getId());
			
		if (!a_already_in) 
		{
			throw new UserException ("Utente "+a+" non trovato");
		}
		
		if (!b_already_in )
		{
			throw new UserException ("Utente "+b+" non trovato");
		}
		
		
		Vector<Integer> amici_di_a = this.contatti.get(a.getId());
		Vector<Integer> amici_di_b = this.contatti.get(b.getId());
		
		if (!amici_di_a.contains(b.getId()))
		{
			throw new RelationException ("");
		}
		
		if (!amici_di_b.contains(a.getId()))
		{
			throw new RelationException ("");
		}
		
		
		amici_di_a.remove((Integer)b.getId());
		amici_di_b.remove((Integer)a.getId());
		
	}
	
	public void SuperRemove (Utente a, Utente b) throws UserException, RelationException
	{
		boolean a_already_in = this.mappa_utenti.containsKey(a.getId());
		boolean b_already_in = this.mappa_utenti.containsKey(b.getId());
			
		if (!a_already_in) 
		{
			throw new UserException ("Utente "+a+" non trovato");
		}
		
		if (!b_already_in )
		{
			throw new UserException ("Utente "+b+" non trovato");
		}
		
		Vector<Integer> amici_di_a = this.contatti.get(a.getId());
		Vector<Integer> amici_di_b = this.contatti.get(b.getId());
				
		try
		{
			this.removeRelation(a, b);
		}
		catch (RelationException e)
		{
			;
		}
	
	
		for (Integer i : amici_di_b)
		{
			try
			{
				Utente u=this.mappa_utenti.get(i);
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
		boolean a_already_in = this.mappa_utenti.containsKey(a.getId());
		Set<Integer> id_amici;
				
		if (!a_already_in) 
		{
			throw new UserException ("Utente "+a+" non trovato");
		}
		
		
		id_amici=this.getRelations_bfs (a.getId(), d);
		
		return this.converti(id_amici);
		
		
	}
	
	private Set<Integer> getRelations_bfs (int a, int d)
	{
		
		Set<Integer> ret = new HashSet<Integer>();
		
		LinkedList<Integer> lista_amici = new LinkedList<Integer>();
		LinkedList<Integer> lista_distanze = new LinkedList<Integer>();
		
		LinkedList<Integer> nodi_visitati = new LinkedList<Integer>();
		
		lista_amici.add(a);
		lista_distanze.add(0);
		
		int u;
		int k = 0;		
		
		while (!lista_amici.isEmpty() && k<d)
		{
			
			u = lista_amici.removeFirst();
			nodi_visitati.add(u);
			k = lista_distanze.removeFirst();
			//~ System.out.println("Utente: "+u+" Distanza: "+k);

			Vector<Integer> vicini = this.contatti.get(u);
			for (Integer v: vicini)
			{
				if (!nodi_visitati.contains(v))
				{
					lista_amici.add(v);
					lista_distanze.add(k+1);
				}
			}
			
			//~ System.out.println(lista_amici);
			if (!lista_amici.isEmpty())
			{
				k=lista_distanze.get(0);
			}
			
		}
		
		ret.addAll(lista_amici);
		
		return ret;

	}
	
	
	private Set<Utente> converti (Set<Integer> lista)
	{
		Set<Utente> ret=new HashSet<Utente>();
		
		for (Integer i: lista)
		{
			Utente u= this.mappa_utenti.get(i);
			ret.add(u);
		}
		
		return ret;
	}
	
	public String toString()
	{
		String s="";
		for (Map.Entry<Integer, Utente> pair : this.mappa_utenti.entrySet())
		{
			s+= pair.getKey() + " " + pair.getValue() + "\n";
		}
		
		s+=this.contatti;
		return s;
		
	}
}
