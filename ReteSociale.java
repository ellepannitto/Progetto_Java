import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class ReteSociale
{


	private Map<Integer, Utente> persone = new HashMap<Integer, Utente>();
	private Map<Integer, Vector<Integer>> rete = new HashMap<Integer, Vector<Integer>>(); 
	
	private boolean modifiche = false;
	private String nomeFileRete = "";
	private String nomeFileUtenti = "";
	
	
	public ReteSociale() {
		rete = new HashMap<Integer, Vector<Integer>>(); 
		persone = new HashMap<Integer, Utente>();
	}
	
	public ReteSociale (String rete_file, String utenti_file){

		try {
			this.nomeFileRete = rete_file;
			ObjectInputStream file_input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(rete_file)));
			rete = (HashMap<Integer, Vector<Integer>>) file_input.readObject();
			file_input.close();
			
			this.nomeFileUtenti = utenti_file;
			file_input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(utenti_file)));
			persone = (HashMap<Integer, Utente>) file_input.readObject();
			file_input.close();
		} catch (FileNotFoundException e) {
			// gestisce il caso in cui il file non sia presente (sarà creato poi...)
			System.out.println("ATTENZIONE: Il file non esiste");
			System.out.println("Sara' creato al primo salvataggio");
			System.out.println();
			rete = new HashMap<Integer, Vector<Integer>>(); 
			persone = new HashMap<Integer, Utente>();
		} catch (ClassNotFoundException e) {
			// gestisce il caso in cui il file non contenga un oggetto
			System.out.println("ERRORE di lettura");
			System.out.println(e);
		} catch (IOException e) {
			// gestisce altri errori di input/output
			System.out.println("ERRORE di I/O");
			System.out.println(e);
		}
	}
	
	/**
	 * Stampa l'hashmap della rete sociale
	 * 
	 * @return String
	 */
	public String toString()
	{
		String output = "";
		// Costruisce l'iteratore con il metodo dedicato
		Iterator<Entry<Integer, Vector<Integer>>> it = rete.entrySet().iterator();
		 
		// Verifica con il metodo hasNext() che nella hashmap
		// ci siano altri elementi su cui ciclare
		while (it.hasNext()) {
		    // Utilizza il nuovo elemento (coppia chiave-valore)
		    // dell'hashmap
		    Map.Entry entry = (Map.Entry)it.next();
		    
		    // Stampa a schermo la coppia chiave-valore;
		    output = output + persone.get(entry.getKey()) + "   ";
		    output = output + "Amici =";
		    Vector<Integer> amici = (Vector) entry.getValue();
		    for (int i : amici) {
		    	output = output + " " +persone.get(i);
		    }
		    output = output + "\n";
		}
		//~ System.out.println(output);
		return output;	
	}
	
	
	public Utente getUser (int id) throws UserException
	{
		boolean already_in = this.persone.containsKey(id);
		if (already_in)
		{
			Utente u=this.persone.get(id);
			return u;
		}else{
			throw new UserException("id "+id+" non presente nella rete sociale");
		}
	}
	
	public int addUser(Utente u) throws UserException
	{
		boolean already_in = this.persone.containsKey(u.getId());

		if (already_in)
		{
			throw new UserException ("elemento "+u+" già presente");
		}
		else
		{
			this.persone.put(u.getId(), u);
			this.rete.put(u.getId(), new Vector<Integer>());
			
		}
		
		return u.getId();
	}
	
	public void removeUser (Utente u) throws UserException
	{
		boolean already_in = this.persone.containsKey(u.getId());

		if (!already_in)
		{
			throw new UserException ("elemento "+u+" già presente");
		}
		else
		{
			Vector<Integer> lista_amici=this.rete.get(u.getId());
			
			Vector<Integer> copia_lista_amici = new Vector<Integer> (lista_amici);
			
			
			for (Integer i: copia_lista_amici)
			{
				try
				{
					this.removeRelation(this.persone.get(i), u);
				}
				catch (RelationException e)
				{
					;
				}
			}
			
		}
		
		this.rete.remove(u.getId());
		this.persone.remove(u.getId());
		
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
		boolean a_already_in = this.persone.containsKey(a.getId());
		boolean b_already_in = this.persone.containsKey(b.getId());
			
		if (!a_already_in) 
		{
			throw new UserException ("Utente "+a+" non trovato");
		}
		
		if (!b_already_in )
		{
			throw new UserException ("Utente "+b+" non trovato");
		}
		
		Vector<Integer> amici_di_a = this.rete.get(a.getId());
		Vector<Integer> amici_di_b = this.rete.get(b.getId());
		
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
		boolean a_already_in = this.persone.containsKey(a.getId());
		boolean b_already_in = this.persone.containsKey(b.getId());
			
		if (!a_already_in) 
		{
			throw new UserException ("Utente "+a+" non trovato");
		}
		
		if (!b_already_in )
		{
			throw new UserException ("Utente "+b+" non trovato");
		}
		
		
		Vector<Integer> amici_di_a = this.rete.get(a.getId());
		Vector<Integer> amici_di_b = this.rete.get(b.getId());
		
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
		boolean a_already_in = this.persone.containsKey(a.getId());
		boolean b_already_in = this.persone.containsKey(b.getId());
			
		if (!a_already_in) 
		{
			throw new UserException ("Utente "+a+" non trovato");
		}
		
		if (!b_already_in )
		{
			throw new UserException ("Utente "+b+" non trovato");
		}
		
		Vector<Integer> amici_di_a = this.rete.get(a.getId());
		Vector<Integer> amici_di_b = this.rete.get(b.getId());
				
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
				Utente u=this.persone.get(i);
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
		boolean a_already_in = this.persone.containsKey(a.getId());
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

			Vector<Integer> vicini = this.rete.get(u);
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
			Utente u= this.persone.get(i);
			ret.add(u);
		}
		
		return ret;
	}
	
	public int getNodi () 
	{
			return rete.size();
	}
		
		// Restituisce la distribuzione di probabilità per il grado k di un nodo nella rete
	public double getDegreeDistribution (int k) 
	{
		int Nk = 0;
		Iterator<Integer> keySetIterator = rete.keySet().iterator();
		while (keySetIterator.hasNext()) {
		    Integer key1 = keySetIterator.next();
		    //System.out.println("key: " + key1 + " value: " + map.get(key1));
		    if (rete.get(key1).size() == k)
		       	Nk++;			            
		}
			return Nk / getNodi();
	}

	// Average degree = 2L/N (L=num archi, N = num nodi)
	
		/**
		 * Lmax indica il numero massimo di link che una rete di N nodi può avere 
		 * @return double con il valore di Lmax
		 */
	public double Lmax () {
		int N = getNodi();
		return (N*(N-1))/2;
	}
	// Diametro: il cammino più lungo possibile
	// Average distance = (1/Lmax)* S_d<i,j>
	// Clustering coefficient = 
	
	/** Salva il registro nel file restituisce true se il salvataggio è andato a buon fine
	 * 
	 * @return
	 */
	public boolean salva() {
		System.out.println(nomeFileRete);
		//if (modifiche) { // salva solo se necessario (se ci sono modifiche)				
			try {
				ObjectOutputStream file_output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(nomeFileRete)));
				// salva l'intero oggetto nel file
				file_output.writeObject(rete);
				file_output.close();
				file_output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(nomeFileUtenti)));
				// salva l'intero oggetto nel file
				file_output.writeObject(persone);
				file_output.close();
				modifiche = false; // le modifiche sono state salvate
				return true;
			} catch (IOException e) {
				System.out.println("ERRORE di I/O");
				System.out.println(e);
				return false;
			}	
		//} else return true;
	}
		
	public boolean salva(String file_rete, String file_utenti)
	{
		this.nomeFileRete = file_rete;
		this.nomeFileUtenti = file_utenti;
			
		return this.salva();
	}
	
	/*public String toString()
	{
		String s="";
		for (Map.Entry<Integer, Utente> pair : this.persone.entrySet())
		{
			s+= pair.getKey() + " " + pair.getValue() + "\n";
		}
		
		s+=this.rete;
		return s;
		
	}*/
}
