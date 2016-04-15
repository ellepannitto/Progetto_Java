import java.util.*;

public class Interface
{
	private static void aggiungiUtente(Tastiera input, ReteSociale rete)
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
			System.out.println ("Errore durante l'aggiunta: "+e);
		}
				
	}
	
	public static void rimuoviUtente(Tastiera input, ReteSociale rete)
	{
		//~ System.out.println(rete);
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
			System.out.println(e);
		}
		
	}
	
	public static void modificaAmicizia(Tastiera input, ReteSociale rete)
	{
		//~ System.out.println(rete);
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
  
	public static void salva(Saver saver, Tastiera input, ReteSociale rete)
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
	
	private static void stampaRete(ReteSociale rete)
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
	
	private static void superRemove (Tastiera input, ReteSociale rete)
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
	
	public static void main(String[] args) 
	{
		Tastiera input=new Tastiera();
		
		ReteSociale mia_rete;
		
		Loader loader=new Loader();
		Saver saver=new Saver();
		
		System.out.println("vuoi caricare una rete da file (1) o crearne una nuova (0)?");
	
		int selezione = input.nextInt();
	
		if (selezione == 1) 
		{
			System.out.println("inserisci il nome del file da cui caricare la rete sociale");
			String file_rete = input.nextLine();

			mia_rete = loader.loadFromFile(file_rete);
		}
		else if (selezione==0)
		{
			mia_rete=new ReteSociale();
		}
		else
		{
			mia_rete=new ReteSociale();
			System.out.println("La scelta non era valida, ho creato una nuova rete");
		}
		
		
		boolean condizione=true;
		
		while (condizione)
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
								"- 10 per cercare utenti per nome / cognome"+
								"- 11 per uscire\n");
			
			int scelta=input.nextInt();
			
			switch (scelta)
			{
				case 1: System.out.println("hai selezionato: inserire un nuovo utente"); 
						aggiungiUtente(input, mia_rete);
						input.aspetta();
						break;
				case 2: System.out.println("hai selezionato: rimuovere un utente"); 
						stampaRete(mia_rete);
						rimuoviUtente(input, mia_rete);
						input.aspetta();
						break;
				case 3: System.out.println("hai selezionato: aggiungere o rimuovere una relazione di amicizia"); 
						stampaRete(mia_rete);
						modificaAmicizia(input, mia_rete);
						input.aspetta();
						break;
				case 4: System.out.println("hai selezionato: stampare rete sociale\n");
						stampaRete(mia_rete);
						input.aspetta();
						break;
				case 5: System.out.println("hai selezionato: SuperRemove\n");
						superRemove(input, mia_rete);	
						break;
				case 6: System.out.println("hai selezionato: visualizzare relazioni\n");
						//~ getRelations();
						break;
				case 7: System.out.println("hai selezionato: visualizzare nodi\n");
						//~ getNodi();
						break;
				case 8: System.out.println("hai selezionato: visualizzare Degree Distribution della rete\n");
						//~ getDegreeDistribution();
						break;
				case 9: System.out.println("hai selezionato: visualizzare il cammino più lungo nella rete\n");
						//~ rete.Lmax();
						break;
				case 10: System.out.println("hai selezionato: ricerca utenti per nome / cognome\n");
						
						break;
				case 11: salva(saver, input, mia_rete);
						System.out.println("Ciao ciao!"); 
						condizione=false;
						input.aspetta(); 
						break;
				default: System.out.println("il numero da te selezionato non esiste");
			}
		}

	}
	
	
}
