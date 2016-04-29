import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.Collections;

/**
 * 
 * La classe ReteSociale memorizza informazioni riguardanti una rete sociale: gli utenti iscritti e le loro relazioni di amicizia
 * 
 * 
 * @author Corradini Celestino, mat. 527813
 * @author Mercadante Giulia, mat. 540938
 * @author Pannitto Ludovica , mat. 491094
 * @author Rambelli Giulia, mat. 495544
 * 
 * */
public class ReteSociale implements Serializable
{
	private int next_user_id = 0;
	
	private Map<Integer, Utente> persone;
	private Map<Integer, Vector<Integer>> rete;
	
	private String FileSalvataggio = "";
	
	/**
	 * 
	 * costruisce una rete sociale vuota (ovvero senza utenti iscritti)
	 * 
	 * */
	public ReteSociale() 
	{
		rete = new HashMap<Integer, Vector<Integer>>(); 
		persone = new HashMap<Integer, Utente>();
	}
	
	/**
	 * 
	 * @return il grafo delle relazioni di amicizia
	 * 
	 * */
	public Map<Integer, Vector<Integer>> getRete()
	{
		return this.rete;
	}
	
	/**
	 * 
	 * @return restituisce una mappa <id, Utente> contenente gli utenti iscritti e il relativo id
	 * 
	 * */
	public Map<Integer, Utente> getUtenti()
	{
		return this.persone;
	}
	
	
	/**
	 * Setta il file su cui serializzare la rete
	 * 
	 * @param nome_file	file da assegnare
	 * 
	 * */
	public void setFile(String nome_file)
	{
		this.FileSalvataggio=nome_file;
	}
	
	
	/**
	 * @return il nome del file di salvataggio
	 * 
	 * */
	public String getSavingFile()
	{
		return this.FileSalvataggio;
	}
	
	/**
	 * Stampa l'hashmap della rete sociale
	 * 
	 * @return String	output della rete nel formato: (id) nome cognome	Amici= (id) nome cognome [...] (id) nome cognome
	 */
	public String toString()
	{
		String output = "";
		
		Iterator<Entry<Integer, Vector<Integer>>> it = rete.entrySet().iterator();
		 
		
		while (it.hasNext()) {
		
		    Map.Entry<Integer, Vector<Integer>> entry = it.next();
		    
		
		    output = output +"("+entry.getKey()+") "+ persone.get(entry.getKey()) + "\t";
		    output = output + "Amici =";
		    Vector<Integer> amici = entry.getValue();
		    for (int i : amici) {
				Utente u=persone.get(i);
		    	output = output +"  ("+i+") "+u;
		    }
		    output = output + "\n";
		}
		
		return output;	
	}
	
	public String toString (Map<Integer, Vector<Integer>> sottorete)
	{
		String output = "";
		
		Iterator<Entry<Integer, Vector<Integer>>> it = sottorete.entrySet().iterator();
		 
		
		while (it.hasNext()) {
		
		    Map.Entry<Integer, Vector<Integer>> entry = it.next();
		    
		
		    output = output +"("+entry.getKey()+") "+ persone.get(entry.getKey()) + "\t";
		    output = output + "Amici =";
		    Vector<Integer> amici = entry.getValue();
		    for (int i : amici) {
				Utente u=persone.get(i);
		    	output = output +"  ("+i+") "+u;
		    }
		    output = output + "\n";
		}
		
		return output;
		
	}
	
	
	/**
	 * Restituisce un utente a partire dal suo id all'interno della rete
	 * 
	 * @param id	id dell'utente cercato
	 * 
	 * @return l'utente desiderato (oggetto di classe Utente)
	 * 
	 * @throws UserException	se l'id cercato non è presente nella rete
	 * 
	 * */
	public Utente getUser (int id) throws UserException
	{
		Utente u=null;
		boolean already_in = this.persone.containsKey(id);
		if (already_in)
		{
			u=this.persone.get(id);

		}else{
			throw new UserException("id "+id+" non presente nella rete sociale");
		}
		
		return u;
	}
	
	/**
	 * Aggiunge un utente alla rete sociale
	 * 
	 * @param u	Utente da aggiungere alla rete
	 * 
	 * @return id (intero) dell'utente aggiunto alla rete
	 * 
	 * @throws UserException nel caso in cui l'utente sia già presente nella rete
	 * 
	 * */
	
	public int addUser(Utente u) throws UserException
	{
		boolean already_in = this.persone.containsValue(u);
		int id=-1;

		if (already_in)
		{
			throw new UserException ("elemento "+u+" già presente");
		}
		else
		{
			
			id=next_user_id;
			next_user_id++;
			this.persone.put(id, u);
			this.rete.put(id, new Vector<Integer>());
			
			System.out.println ("Aggiunto "+id+" "+u);
			
		}
		
		return id;
	}
	
	/**
	 * 
	 * Restituisce l'id di un utente
	 * @param u utente da cercare
	 * 
	 * @return il suo id
	 * 
	 * 
	 * */
	private int getId(Utente u)
	{
		int id=-1;
		
		for (Map.Entry<Integer, Utente> entry : this.persone.entrySet()) 
		{ 
			if (entry.getValue() == u)
			{
				id = entry.getKey();
			}

		}
		
		return id;
	}
	
	
	/**
	 * 
	 * Rimuove un utente dalla rete
	 * @param u utente da rimuovere
	 * 
	 * @throws UserException se l'utente non è presente
	 * 
	 * 
	 * */
	public void removeUser (Utente u) throws UserException
	{
		
		boolean already_in = this.persone.containsValue(u);
	
		if (!already_in)
		{
			throw new UserException ("elemento "+u+" non presente");
		}
		else
		{
			Vector<Integer> lista_amici=this.rete.get(getId(u));
			
			System.out.println ("rimuovo tutti "+lista_amici);
			
			Vector<Integer> copia_lista_amici = new Vector<Integer> (lista_amici);
			
			for (Integer i: copia_lista_amici)
			{
				try
				{
					this.removeRelation(this.persone.get(i), u);
				}
				catch (RelationException e)
				{
					System.err.println("Rete inconsistente: gli utenti "+i+" e "+u+" non sono amici");
					e.printStackTrace();
					System.exit (1);
				}
			}
			
			
		}
		
		this.rete.remove(getId(u));
		this.persone.remove(getId(u));
		
		
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
	public void addRelation (Utente a, Utente b) throws UserException, RelationException
	{
		if (a.equals(b))
		{
			throw new UserException ("Impossibile aggiungere una relazione tra due utenti uguali.");
		}
		
		boolean a_already_in = this.persone.containsValue(a);
		boolean b_already_in = this.persone.containsValue(b);
			
		if (!a_already_in) 
		{
			throw new UserException ("Utente "+a+" non trovato");
		}
		
		if (!b_already_in )
		{
			throw new UserException ("Utente "+b+" non trovato");
		}
		
		Vector<Integer> amici_di_a = this.rete.get(getId(a));
		Vector<Integer> amici_di_b = this.rete.get(getId(b));
		
		if (amici_di_a.contains(getId(b)))
		{
			throw new RelationException ("");
		}
		
		if (amici_di_b.contains(getId(a)))
		{
			throw new RelationException ("");
		}
		
		amici_di_a.addElement(getId(b));
		amici_di_b.addElement(getId(a));
		
	}
	
	/**
	 * Modifica la relazione di amicizia tra due utenti: 
	 * 	- se i due utenti non sono amici, aggiunge la relazione di amicizia
	 *  - se i due utenti sono già amici, rimuove la relazione
	 * 
	 * 
	 * @throws UserException
	 * @throws RelationException
	 * 
	 * */
	public void changeRelation (Utente a, Utente b) throws UserException, RelationException
	{
		if (a.equals(b))
		{
			throw new UserException ("Impossibile gestire una relazione tra due utenti uguali.");
		}
		
		boolean add = !checkRelation (a, b);
		
		if (add)
		{
			this.addRelation(a, b);
			System.out.println ("Aggiunta relazione tra '"+a+"' e '"+b+"'.");
		}
		else
		{
			this.removeRelation(a, b);
			System.out.println ("Rimossa relazione tra '"+a+"' e '"+b+"'.");
			
		}	
	}
	
	/**
	 * Controlla se i due utenti sono amici.
	 * 
	 * @param a Utente
	 * @param b Utente
	 * 
	 * @return false se i due utenti non sono amici
	 * 			true altrimenti
	 * 
	 * @throws UserException se uno dei due utenti non appartiene alla rete
	 * 
	 * */	
	private boolean checkRelation (Utente a, Utente b) throws UserException
	{
		boolean ret = true;
		
		boolean a_already_in = this.persone.containsValue(a);
		boolean b_already_in = this.persone.containsValue(b);
			
		if (!a_already_in) 
		{
			throw new UserException ("Utente "+a+" non trovato");
		}
		
		if (!b_already_in )
		{
			throw new UserException ("Utente "+b+" non trovato");
		}
		
		Vector<Integer> amici_di_a = this.rete.get(getId(a));
		Vector<Integer> amici_di_b = this.rete.get(getId(b));
		
		if (!amici_di_a.contains(getId(b)) && !amici_di_b.contains(getId(a)))
		{
			ret = false;
		}
		
		return ret;


	}
	
	/**
	 * 
	 * Rimuove la relazione di amicizia fra due utenti
	 * 
	 * @param a il primo utente
	 * @param b il secondo utente
	 * 
	 * @throws UserException se uno dei due utenti non è presente nella rete
	 * @throws RelationException se non esiste una relazione di amicizia fra i due utenti
	 * 
	 * */
	public void removeRelation (Utente a, Utente b) throws UserException, RelationException
	{
		if (a.equals(b))
		{
			throw new UserException ("Impossibile rimuovere una relazione tra due utenti uguali.");
		}
		
		boolean a_already_in = this.persone.containsValue(a);
		boolean b_already_in = this.persone.containsValue(b);
			
		if (!a_already_in) 
		{
			throw new UserException ("Utente "+a+" non trovato");
		}
		
		if (!b_already_in )
		{
			throw new UserException ("Utente "+b+" non trovato");
		}
			
		Vector<Integer> amici_di_a = this.rete.get(getId(a));
		Vector<Integer> amici_di_b = this.rete.get(getId(b));
		
		if (!amici_di_a.contains(getId(b)))
		{
			throw new RelationException ("");
		}
		
		if (!amici_di_b.contains(getId(a)))
		{
			throw new RelationException ("");
		}
		
		amici_di_a.remove((Integer)getId(b));
		amici_di_b.remove((Integer)getId(a));
	
	}
	
	
	/**
	 * 
	 * Dati due utenti a, b rimuove dagli amici di a sia b che tutti gli amici di b
	 * 
	 * @param a utente da cui rimuovere gli amici
	 * @param b utente di cui rimuovere gli amici
	 *  
	 * @throws UserException se uno dei due utenti non è presente nella rete
	 * @throws RelationException se non esiste una relazione di amicizia fra i due utenti
	 * 
	 * */
	 
	public void SuperRemove (Utente a, Utente b) throws UserException, RelationException
	{
		if (a.equals(b))
		{
			throw new UserException ("Impossibile considerare una relazione tra due utenti uguali.");
		}
		
		boolean a_already_in = this.persone.containsValue(a);
		boolean b_already_in = this.persone.containsValue(b);
			
		if (!a_already_in) 
		{
			throw new UserException ("Utente "+a+" non trovato");
		}
		
		if (!b_already_in )
		{
			throw new UserException ("Utente "+b+" non trovato");
		}
		
		Vector<Integer> amici_di_a = this.rete.get(getId(a));
		Vector<Integer> amici_di_b = this.rete.get(getId(b));
				
		try
		{
			this.removeRelation(a, b);
		}
		catch(RelationException e)
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
	
	/**
	 *
	 * cerca tutti gli utenti ad una specifica distanza da un utente specifico nel grafo delle relazioni  
	 * @param a utente dal quale far cominciare la ricerca
	 * @param d distanza dall'utente
	 * 
	 * @return l'insieme di utenti richiesto
	 * 
	 * @throws UserException se l'utente non esiste
	 * 
	 * */
	public Set<Utente> getRelations (Utente a, int d) throws UserException
	{
		boolean a_already_in = this.persone.containsValue(a);
		Set<Integer> id_amici;
				
		if (!a_already_in) 
		{
			throw new UserException ("Utente "+a+" non trovato");
		}
		
		
		id_amici=this.getRelations_bfs (getId(a), d);
		
		return this.converti(id_amici);
		
	}
	
	/**
	 * 
	 * Esegue una ricerca sul grafo delle relazioni, per trovare tutti gli utenti a distanza d da uno specifico utente con id a
	 * 
	 * @param a id dell'utente di partenza
	 * @param d distanza a cui cercare gli amici
	 * 
	 * @return l'insieme degli id degli utenti richiesti 
	 * 
	 * */
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


			Vector<Integer> vicini = this.rete.get(u);
			for (Integer v: vicini)
			{
				if (!nodi_visitati.contains(v))
				{
					lista_amici.add(v);
					lista_distanze.add(k+1);
				}
			}
			
			if (!lista_amici.isEmpty())
			{
				k=lista_distanze.get(0);
			}
			
		}
		
		ret.addAll(lista_amici);
		
		return ret;

	}
	
	/**
	 * 
	 * converte un insieme di id nell'insieme di utenti corrispondente
	 * @param lista insieme di id
	 * 
	 * @return insieme di utenti con id corrispondenti a quelli passati come parametro
	 * 
	 * */
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
	
	public boolean isEmpty()
	{
		if (rete.size()>0)
			return false;
		else
			return true;
	}
	
	/**
	 * 
	 * @return il numero di utenti nella rete
	 * 
	 * */
	public int getNodi () 
	{
		return rete.size();
	}
	
	public int getArchi ()
	{
		int L = 0;
		Iterator<Integer> keySetIterator = rete.keySet().iterator();
		while (keySetIterator.hasNext())
		{
		    Integer key1 = keySetIterator.next();
		    L += rete.get(key1).size();
		}
		return L/2;
	}
		
	/**
	 * Restituisce la distribuzione di probabilità per il grado k di un nodo nella rete
	 * 
	 * @param k	intero...
	 * 
	 * @return ...
	 * */
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
			return Nk / (double) getNodi();
	}

	public double avg_degree()
	{
		double N = this.getNodi();
		double L = this.getArchi();
		
		return (2*L)/N;
	}
	/**
	 * Lmax indica il numero massimo di link che una rete di N nodi può avere 
	 * 
	 * @return double con il valore di Lmax
	 */
	public double Lmax () {
		int N = getNodi();
		return (N*(N-1))/2;
	}
	
	public double density()
	{
		double neighbors = 0;
		Iterator<Integer> keySetIterator = rete.keySet().iterator();
		while (keySetIterator.hasNext()) {
		    Integer key1 = keySetIterator.next();
		    neighbors += rete.get(key1).size(); 
		}
			return neighbors / Lmax();
	}
	
	/**
	 * 
	 * Carica la rete dalla sua rappresentazione a grafo
	 * 
	 * @param mapping_persone lista di nodi dell grafo delle relazioni, ovvero lista di utenti 
	 * @param mapping_rete lista di archi dell grafo delle relazioni, ovvero lista di relazioni di amicizia fra utenti 
	 * 
	 * */
	public void caricaDaGrafo (Map<Integer, Utente> mapping_persone, Map<Integer, Vector<Integer>> mapping_rete) 
	{
		
		int max=0;
		
		if (!mapping_persone.isEmpty() && !mapping_rete.isEmpty())
		{
			Set<Integer> chiavi = mapping_persone.keySet();
			max = Collections.max(chiavi);
		
			next_user_id=max;
		}
		
		persone = mapping_persone;
		rete = mapping_rete;
		
		
		System.out.println("Attenzione: la rete è stata creata a partire da una fonte esterna.");
		System.out.println("Per questo alcuni dati potrebbero essere inconsistenti");
	}
	
	public String stampa_sottorete (String ricerca)
	{
		String output = "";
		
		Vector <String> parts = new Vector<String> (Arrays.asList(ricerca.split(" ")));
		
		Set <Integer> find = new HashSet <Integer> ();
		
		Iterator<Integer> keySetIterator = rete.keySet().iterator();
		while (keySetIterator.hasNext()) 
		{
		    Integer id = keySetIterator.next();
		    
		    Utente u = this.persone.get(id);
		    
		    Vector <String> nome = new Vector<String> (Arrays.asList(u.getNome().split(" ")));
		    Vector <String> cognome = new Vector<String> (Arrays.asList(u.getCognome().split(" ")));
		    
		    for (String s: parts)
		    {
				if (nome.contains(s) || cognome.contains(s))
				{
					find.add(id);
				}
			}
		}
		    
		if (!find.isEmpty())
		{
			Map <Integer, Vector<Integer>> sottorete = new HashMap<Integer, Vector<Integer>> ();
			
			for (Integer i: find)
			{
				sottorete.put(i, this.rete.get(i));	
			}
			output = toString(sottorete);
			
		}
		else
		{
			output = "Nessun risultato per la tua ricerca";
		}
		
		return output;
	}
	
}
