import java.util.*;
import org.jdom2.JDOMException;


/**
 * @author Corradini Celestino, mat. 527813
 * @author Mercadante Giulia, mat.
 * @author Pannitto Ludovica , mat. 491094
 * @author Rambelli Giulia, mat.
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
	 * 
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
			nome_file=input.next();
		}
		
		return nome_file;
	}
	
	/**
	 * 
	 * 
	 * 
	 * */
	private static void distribuzione()
	{
		int k=input.nextInt();
		
		System.out.println("...: "+rete.getDegreeDistribution(k));
		
	}
	
	/**
	 * 
	 * 
	 * 
	 * */
	private static void aggiungiUtente()
	{
		String nome;
		String cognome;
		
		System.out.println("Inserisci il nome del nuovo utente:");
		nome=input.nextLine();
		
		System.out.println("Inserisci il cognome del nuovo utente:");
		cognome=input.nextLine();
		
		Utente u=new Utente(nome, cognome);
		
		try
		{
			rete.addUser(u);
		}
		catch (UserException e)
		{
			System.err.println ("Errore durante l'aggiunta dell'utente alla rete");
			System.err.println (e.printStackTrace());
		}
				
	}
	
	/**
	 * 
	 * 
	 * 
	 * */
	public static void rimuoviUtente()
	{
		stampaRete(rete);
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
			System.err.println("Errore durante la rimozione dell'utente dalla rete: "+e)
		}
		
	}
	
	/**
	 * 
	 * 
	 * */
	public static void modificaAmicizia()
	{
		stampaRete(rete);
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
		
		
			System.out.println("Seleziona 0 per aggiungere la relazione\n 1 per rimuovere una relazione");
			int scelta=input.nextInt ();
			if (scelta==0)
			{
				rete.changeRelation(utenteUno, utenteDue);
				
			} 
			else {
				
				rete.changeRelation(utenteUno, utenteDue, false);
				
			}
		}
		catch (UserException e)
		{
			;
		}
		catch (RelationException e)
		{
			;
		}
	}
	
	/**
	 * 
	 * 
	 * 
	 * */
	public static void salva()
	{
		System.out.println("Vuoi salvare la tua rete? 1=sì, 0=no");
		int scelta= input.nextInt();
		
		if (scelta==1)
		{
			System.out.println("inserisci il nome del file su cui salvare la rete sociale");
			String file_rete = input.nextLine();

			saver.save(rete, file_rete);
		}
	}
	
	/**
	 * 
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
	 * 
	 * */
	private static void superRemove ()
	{
		stampaRete(rete);
		
		System.out.println("Inserisci id del primo utente:");
		int id_utente_uno=input.nextInt();
		System.out.println("Inserisci id del secondo utente:");
		int id_utente_due=input.nextInt();
		
		try{
			Utente utente_uno = rete.getUser(id_utente_uno);
			Utente utente_due = rete.getUser(id_utente_due);
			rete.SuperRemove(utente_uno, utente_due);
		}
		catch(UserException e)
		{
			;
		}
		catch(RelationException e)
		{
			;
		}
		
	}
	
	/**
	 * 
	 * 
	 * 
	 * */
	private static void mostraRelazioni()
	{
		stampaRete(rete);
		
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
			;
		}
		
		System.out.println(ret);
	}
	
	/**
	 * 
	 * 
	 * */
	private static void resetConsole()
	{
		System.out.println("\033[H\033[2J");
		System.out.flush();
			
		System.out.println("Cosa vuoi fare? Rispondi:\n"+
							"- 1 per inserire un nuovo utente\n"+
							"- 2 per rimuovere un utente\n"+
							"- 3 aggiungere o rimuovere una relazione di amicizia\n"+
							"- 4 per stampare la rete sociale\n"+
							"- 5 per effetturare l'operazione SuperRemove\n"+
							"- 6 per vedere le relazioni a distanza d da un utente\n"+
							"- 7 getNodi\n"+
							"- 8 getDegreeDistribution\n"+
							"- 9 LMax\n"+
							"- 10 per cercare utenti per nome / cognome\n"+
							"- 11 per salvare la rete in xml\n"+
							"- 12 per uscire\n");
	}
	

	
	public static void esportaXML()
	{
		System.out.println("Inserisci nome file su cui esportare:");
		String file_xml=input.nextLine();
		file_xml+=".xml";
		try
		{
			saver.saveXML(rete, file_xml);	
		}
		catch(Exception e)
		{
			System.err.println("errore esportazione xml "+e);
		}

	}
	
	private static boolean gestisciMenu (int scelta)
	{
		boolean ret=false;
		
		switch (scelta)
		{
			case 1: System.out.println("hai selezionato: inserire un nuovo utente"); 
					aggiungiUtente();
					break;
			case 2: System.out.println("hai selezionato: rimuovere un utente"); 
					rimuoviUtente();
					break;
			case 3: System.out.println("hai selezionato: aggiungere o rimuovere una relazione di amicizia"); 
					modificaAmicizia();
					break;
			case 4: System.out.println("hai selezionato: stampare rete sociale\n");
					stampaRete();
					break;
			case 5: System.out.println("hai selezionato: SuperRemove\n");
					superRemove();	
					break;
			case 6: System.out.println("hai selezionato: visualizzare relazioni\n");
					mostraRelazioni();
					break;
			case 7: System.out.println("hai selezionato: visualizzare nodi\n");
					System.out.println("Dimensione rete: "+rete.getNodi());
					break;
			case 8: System.out.println("hai selezionato: visualizzare Degree Distribution della rete\n");
					distribuzione();
					break;
			case 9: System.out.println("hai selezionato: visualizzare il cammino più lungo nella rete\n");
					System.out.println("...: "+rete.Lmax());
					break;
			case 10: System.out.println("hai selezionato: ricerca utenti per nome / cognome\n");
					
					break;
			case 11: System.out.println("hai selezionato: esporta in xml\n");
					esportaXML();
					break;
			case 12: System.out.println("Ciao ciao!"); 
					ret=true; 
					break;
			default: System.out.println("il numero da te selezionato non esiste");
		}
		
		input.aspetta();
		
		return ret;
		
	}
	
}
