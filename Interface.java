import java.util.*;
import org.jdom2.JDOMException;
import java.io.*;


/**
 * @author Corradini Celestino, mat. 527813
 * @author Mercadante Giulia, mat. 540938
 * @author Pannitto Ludovica , mat. 491094
 * @author Rambelli Giulia, mat. 495544
 * 
 * Gestisce una rete sociale, dando la possibilità di inserire utenti, relazioni di amicizia, visualizzarla...
 * 
 * 
 * */
public class Interface
{
	private static Tastiera input = new Tastiera();
	private static ReteSociale rete = new ReteSociale();
	private static Loader loader = new Loader();
	private static Saver saver = new Saver();
	
	
	/**
	 * 
	 * Controlla un ciclo di esecuzione ad ogni passo del quale viene chiesto gentilmente all'utente cosa vuole fare, e si compie un'azione diversa in base alla risposta.
	 * alla fine del ciclo è possibile salvare la rete su un file.
	 * 
	 * @param il primo argomento (opzionale) della riga di comando è il nome del file dal quale viene eventualmente caricata e sul quale verrà salvata la rete sociale 
	 * 
	 * */
	public static void main(String[] args) 
	{
			
		boolean termina_menu=false;
		
		String file_input="";
	
		if (args.length>0)
		{
			file_input = args[0];
		}
		else
		{
			System.out.println("Non hai indicato nessun file da cui caricare la tua rete sociale.");
			file_input = chiediFile();
		}
		
		
		if (file_input.equals(""))
		{
			rete=new ReteSociale();
		}
		else
		{
			rete = loader.loadFromFile(file_input);
		}
		
		input.aspetta();
		
				
		while (!termina_menu)
		{
			resetConsole();
			
			int scelta=input.nextInt();
			
			termina_menu = gestisciMenu(scelta);
		}
		
		salva();

	}
	
	/**
	 * Chiede all'utente il nome di un file dal quale caricare la rete sociale
	 * 
	 * @return stringa contenente il nome del file
	 * 
	 * */
	private static String chiediFile()
	{	
		System.out.println("Vuoi caricare una rete da file?\n - 1 -> sì\n - 0 -> no");
		int selezione = input.nextInt();
		
		String nome_file="";
		if (selezione==1)
		{
			File cartella_corrente = new File (".");
			File[] fs = cartella_corrente.listFiles();
			List<String> fs_xml = new ArrayList<String>();
			for (File i : fs) {	
				if (i.isFile() && !i.isHidden()){
					fs_xml.add(i.getName());
					System.out.println(i.getName());
				}
				
			}
			if (fs_xml.size() >0)
			{
				System.out.println("Inserisci il nome del file da cui caricare la rete tra quelli presenti nella corrente directory (vedi lista sovrastante):");
			}
			else
			{
				System.out.println("Nessun file presente nella directory corrente. Inserisci il path relativo:");
			}
			
			nome_file = input.nextLine();
			
		}
		
		return nome_file;
	}
	
	/**
	 * Da implementare: stampa la rete sociale limitatamente agli utenti che rispettano i criteri della ricerca
	 * 
	 * 
	 * */
	private static void cercaUtenti()
	{
		System.out.println("Inserisci la stringa da cercare (può contenere spazi!)");
		String da_cercare = input.nextAlphabetic();
		
		String s = rete.stampa_sottorete(da_cercare);
		
		System.out.println (s);
	}
	
	/**
	 * Stampa la probabilità di avere un nodo di grado <k>
	 * 
	 * 
	 * */
	private static void distribuzione()
	{
		System.out.println("Inserisci il grado di cui vuoi conoscere la distribuzione:");
		int k=input.nextInt();
		
		System.out.println("Distribuzione del grado "+k+": "+rete.getDegreeDistribution(k));
		
	}
	
	/**
	 * Aggiunge un utente alla rete sociale.
	 * 
	 * 
	 * */
	private static void aggiungiUtente()
	{
		String nome;
		String cognome;
		
		System.out.println("Inserisci il nome del nuovo utente:");
		nome=input.nextAlphabetic();
		
		System.out.println("Inserisci il cognome del nuovo utente:");
		cognome=input.nextAlphabetic();
		
		Utente u=new Utente(nome, cognome);
		
		try
		{
			rete.addUser(u);
		}
		catch (UserException e)
		{
			System.out.println ("Utente già presente, "+e.getMessage());
		}
	}
	
	/**
	 * Rimuove un utente dalla rete sociale.
	 * 
	 * 
	 * */
	public static void rimuoviUtente()
	{
		stampaRete();
		System.out.println("Inserisci l'id dell'utente da rimuovere");
		
		int id_utente=input.nextInt();
		
		try
		{
			Utente utente_da_rimuovere=rete.getUser(id_utente);
			
			System.out.println(utente_da_rimuovere);
			
			rete.removeUser(utente_da_rimuovere);
			
		}
		catch (UserException e)
		{
			System.out.println("Errore durante la rimozione dell'utente dalla rete, "+e.getMessage());
			//~ e.printStackTrace();
		}
		
	}
	
	/**
	 * Aggiunge o rimuove la relazione di amicizia tra due utenti della rete sociale.
	 * 
	 * 
	 * */
	public static void modificaAmicizia()
	{
		stampaRete();
		System.out.println("Seleziona il primo utente");
		int id_utenteUno=input.nextInt ();
		System.out.println("Seleziona il secondo utente");
		int id_utenteDue=input.nextInt ();
		Utente utenteUno;
		Utente utenteDue;
		
		try
		{
			utenteUno=rete.getUser(id_utenteUno);
			utenteDue=rete.getUser(id_utenteDue);
		
			rete.changeRelation(utenteUno, utenteDue);
				
		}
		catch (UserException e)
		{
			System.out.println("Errore durante la selezione dell'utente, "+e.getMessage());
			//~ e.printStackTrace();
		}
		catch (RelationException e)
		{
			System.out.println("Errore durante la modifica della relazione, "+e.getMessage());
			//~ e.printStackTrace();
		}
		
	}
	
	/**
	 * Salva la rete sociale su file.
	 * 
	 * 
	 * */
	public static void salva()
	{
		System.out.println("Vuoi salvare la tua rete? 0=no, 1=salva..., 2=salva come...");
		int scelta= input.nextInt();
		String file_rete = "";
		boolean salva = false;
		
		if (scelta == 1)
		{	
			file_rete = rete.getSavingFile();
			System.out.println("Salvo su: "+file_rete);
			salva=true;
		}
		else if (scelta == 2)
		{
			System.out.println ("Inserisci nome del file su cui salvare:");
			file_rete = input.nextLine();
			salva=true;
		}
		
		if (salva)
		{
			boolean isXML = file_rete.endsWith(".xml");
			
			if (isXML)
			{
				esportaXML(file_rete);
			}
			else
			{
				saver.save(rete, file_rete);
			}
		}
		
	}
	
	/**
	 * visualizza la rete sociale
	 * 
	 * */
	private static void stampaRete()
	{
		
		System.out.println("RETE:\n");
		String s=rete.toString();
		if (s.equals(""))
		{
			System.out.println("la rete è vuota");
		}
		else
		{
			System.out.println(s);
		}
	}
	
	/**
	 * 
	 * Esegue l'operazione SuperRemove, che rimuove dagli amici di un utente un altro utente e tutti i suoi amici  
	 * 
	 * */
	private static void superRemove ()
	{
		stampaRete();
		
		System.out.println("Inserisci id dell'utente da cui rimuovere le relazioni:");
		int id_utente_uno=input.nextInt();
		System.out.println("Inserisci id dell'utente di cui non si vuole più sentir parlare:");
		int id_utente_due=input.nextInt();
		
		try{
			Utente utente_uno = rete.getUser(id_utente_uno);
			Utente utente_due = rete.getUser(id_utente_due);
			rete.SuperRemove(utente_uno, utente_due);
		}
		catch(UserException e)
		{
			System.out.println("Errore dutante la ricerca dell'utente, "+e.getMessage());
			//~ e.printStackTrace();
		}
		catch(RelationException e)
		{
			System.out.println("Errore dutante la modifica della relazione, "+e.getMessage());
			//~ e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * Visualizza le relazioni a una certa distanza di un utente
	 * 
	 * */
	private static void mostraRelazioni()
	{
		stampaRete();
		
		Set<Utente> ret=null;
		
		System.out.println("Inserisci l'id dell'utente di cui vuoi visualizzare le relazioni:");
		int i=input.nextInt();
		
		System.out.println("Inserisci distanza:");
		int d=input.nextInt();
		
		try
		{
			Utente u = rete.getUser(i);
			ret=rete.getRelations(u, d);
		}
		catch(UserException e)
		{
			System.out.println("Errore durante la selezione dell'utente, "+e.getMessage());
			System.out.println("Utente con id "+i+"Non presente");
		}
		
		System.out.println(ret);
	}
	
	private static void mostraStatistiche()
	{
		System.out.println("Nodi: " + rete.getNodi());
		System.out.println("Archi: " + rete.getArchi());
		System.out.println("Average degree: " + rete.avg_degree());
		System.out.println("Cammino più lungo possibile: "+rete.Lmax());
		System.out.println("Network density: " + rete.density());
	}
	
	/**
	 * Resetta la console tenendo in alto il menu di scelta.
	 * 
	 * */
	private static void resetConsole()
	{
		//Per pulire la console su Linux
		System.out.println("\033[H\033[2J");
		System.out.flush();
			
		System.out.println("Cosa vuoi fare? Rispondi:\n"+
							"- 1 per inserire un nuovo utente\n"+
							"- 2 per rimuovere un utente\n"+
							"- 3 aggiungere o rimuovere una relazione di amicizia\n"+
							"- 4 per stampare la rete sociale\n"+
							"- 5 per effetturare l'operazione SuperRemove\n"+
							"- 6 per vedere le relazioni a distanza d da un utente\n"+
							"- 7 statistiche\n"+
							"- 8 getDegreeDistribution\n"+
							"- 9 per cercare utenti per nome / cognome\n"+
							"- 10 per salvare la rete in xml\n"+
							"- 11 per uscire\n");
	}
	

	/**
	 * 
	 * Salva la rete su un file xml
	 * 
	 * @param file_xml il nome del file
	 * 
	 * */
	public static void esportaXML (String file_xml)
	{
		try
		{
			saver.saveXML(rete, file_xml);	
		}
		catch(Exception e)
		{
			System.err.println("errore esportazione xml.");
			e.printStackTrace();
			System.exit (1);
		}
	}

	/**
	 * 
	 * Salva la rete su un file xml.
	 * Chiede il nome del file all'utente 
	 * 
	 * */
	public static void esportaXML()
	{
		System.out.println("Inserisci nome file su cui esportare:");
		String file_xml=input.nextLine();
		boolean isXML = file_xml.endsWith(".xml");
		
		if (!isXML)
		{
			file_xml+=".xml";
			
		}
		
		esportaXML(file_xml);

	}
	
	/**
	 * 
	 * gestisce la scelta dell'utente, eseguendo la funzione appropriata
	 * @param scelta la scelta dell'utente
	 * 
	 * @return true se l'utente ha deciso di uscire
	 * @return false altrimenti
	 * 
	 * */
	private static boolean gestisciMenu (int scelta)
	{
		String not_selectable = "La rete è vuota, opzione non selezionabile";
		boolean ret=false;
		
		boolean rete_vuota = rete.isEmpty();
		
		switch (scelta)
		{
			case 1: System.out.println("hai selezionato: inserire un nuovo utente"); 
					aggiungiUtente();
					break;
			case 2: System.out.println("hai selezionato: rimuovere un utente"); 
					if (!rete_vuota) rimuoviUtente();
					else System.out.println(not_selectable);
					break;
			case 3: System.out.println("hai selezionato: aggiungere o rimuovere una relazione di amicizia");
					if (!rete_vuota) modificaAmicizia();
					else System.out.println(not_selectable);
					break;
			case 4: System.out.println("hai selezionato: stampare rete sociale\n");
					stampaRete();
					break;
			case 5: System.out.println("hai selezionato: SuperRemove\n");
					if (!rete_vuota) superRemove();
					else System.out.println(not_selectable);	
					break;
			case 6: System.out.println("hai selezionato: visualizzare relazioni\n");
					if (!rete_vuota) mostraRelazioni();
					else System.out.println(not_selectable);
					break;
			case 7: System.out.println("hai selezionato: visualizzare statistiche sulla rete\n");
					if (!rete_vuota) mostraStatistiche();
					else System.out.println(not_selectable);
					break;
			case 8: System.out.println("hai selezionato: visualizzare Degree Distribution della rete\n");
					if (!rete_vuota) distribuzione();
					else System.out.println(not_selectable);
					break;
			case 9: System.out.println("hai selezionato: ricerca utenti per stringa\n");
					if (!rete_vuota) cercaUtenti();
					else System.out.println(not_selectable);
					break;
			case 10: System.out.println("hai selezionato: esporta in xml\n");
					esportaXML();
					break;
			case 11: System.out.println("Ciao ciao!"); 
					ret=true; 
					break;
			default: System.out.println("il numero selezionato non esiste");
		}
		
		input.aspetta();
		
		return ret;
	}	
}
