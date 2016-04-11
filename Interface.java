import java.util.*;

public class Interface
{
	private static void aggiungiUtente(Scanner input, ReteSociale rete)
	{
		String nome;
		String cognome;
		
		System.out.println("Inserisci il nome del nuovo utente:");
		nome=input.next();
		
		System.out.println("Inserisci il cognome del nuovo utente:");
		cognome=input.next();
		
		Utente u=new Utente(nome, cognome);
		
		try
		{
			rete.addUser(u);
		}
		catch (UserException e)
		{
				;
		}
				
	}
	
	public static void rimuoviUtente(Scanner input, ReteSociale rete)
	{
		System.out.println(rete);
		System.out.println("Inserisci l'id dell'utente da rimuovere");
		int id_utente=input.nextInt ();
		
		Utente utente_da_rimuovere=rete.getUser(id_utente);
		
		System.out.println(utente_da_rimuovere);
		
		try
		{
			rete.removeUser(utente_da_rimuovere);
		}
		catch (UserException e)
		{ 
			//System.out.println("Utente inesistente");//
			
		}
		
	}
	
	public static void modificaAmicizia(Scanner input, ReteSociale rete)
	{
		System.out.println(rete);
		System.out.println("Seleziona il primo utente");
		int id_utenteUno=input.nextInt ();
		System.out.println("Seleziona il secondo utente");
		int id_utenteDue=input.nextInt ();
		
		Utente utenteUno=rete.getUser(id_utenteUno);
		Utente utenteDue=rete.getUser(id_utenteDue);
		
		System.out.println("Seleziona 0 per aggiungere la relazione\n 1 per rimuovere una relazione");
		int scelta=input.nextInt ();
		if (scelta==0)
		{
			try{
				 rete.changeRelation(utenteUno, utenteDue);
			}
			catch (UserException e)
			{
				;
			}
			catch ( RelationException e)
			{
				;
			}
		} 
		else {
			try{
				 rete.changeRelation(utenteUno, utenteDue, false);
			}
			catch (UserException e)
			{
				;
			}
			catch ( RelationException e)
			{
				;
			}
	}
  }
	
	public static void main(String[] args) 
	{
		
		ReteSociale mia_rete=new ReteSociale();
		Scanner input=new Scanner(System.in);
		boolean condizione=true;
		
		System.out.println("Cosa vuoi fare? Rispondi:\n - 1 per inserire un nuovo utente\n - 2 per rimuovere un utente\n - 3 aggiungere o rimuovere una relazione di amicizia\n - 4 per uscire");
	
		while (condizione)
		{		
			
		    int scelta=input.nextInt();
			
			String risposta;

			switch (scelta)
			{
				case 1: risposta="hai selezionato: inserire un nuovo utente"; 
						aggiungiUtente(input, mia_rete);
						System.out.println(mia_rete);
						break;
				case 2: risposta="hai selezionato: rimuovere un utente"; 
						rimuoviUtente(input, mia_rete);
						break;
				case 3: risposta="hai selezionato: aggiungere o rimuovere una relazione di amicizia"; 
						modificaAmicizia(input, mia_rete);
						break;
				case 4: risposta= "Ciao ciao!"; condizione=false; break;
				default: risposta="il numero da te selezionato non esiste"; 
			}

			System.out.println(risposta);
		}

	}
	
	
}
