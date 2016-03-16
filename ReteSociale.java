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
			throw new UserException ("elemento "+u+" già presente");
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
		
		if (!amici_di_a.contains(posizione_b))
		{
			throw new RelationException ("");
		}
		
		if (!amici_di_b.contains(posizione_a))
		{
			throw new RelationException ("");
		}
		
		
		amici_di_a.remove((Integer)posizione_b);
		amici_di_b.remove((Integer)posizione_a);
		
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
		
	/*	if (!amici_di_a.contains(posizione_b))
		{
			throw new RelationException ("");
		}
		
		if (!amici_di_b.contains(posizione_a))
		{
			throw new RelationException ("");
		}*/
		
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
		
		
		id_amici=this.getRelations_bfs (posizione_a, d);
		
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
			Utente u= this.lista_utenti.get(i);
			ret.add(u);
		}
		
		return ret;
	}
	
	public String toString()
	{
		String s="";
		int i=0;
		for (Utente u : this.lista_utenti)
		{
			s+= i + " " + u+"\n";
			i+=1;
		}
		
		s+=this.contatti;
		return s;
		
	}
}
