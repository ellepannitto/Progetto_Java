import java.util.*;

public class Test
{
	public static void main (String[] args) throws UserException, RelationException
	{
		ReteSociale rete=new ReteSociale();
		
		Vector<Utente> lista_utenti = new Vector<Utente>();
		Utente r;
			
		lista_utenti.add(new Utente("zero", "zero"));
		lista_utenti.add(new Utente("uno", "uno"));
		lista_utenti.add(new Utente("due", "due"));
		lista_utenti.add(new Utente("tre", "tre"));
		lista_utenti.add(new Utente("quattro", "quattro"));
		lista_utenti.add(new Utente("cinque", "cinque"));
		lista_utenti.add(new Utente("sei", "sei"));
		lista_utenti.add(new Utente("sette", "sette"));
		lista_utenti.add(new Utente("otto", "otto"));
		lista_utenti.add(new Utente("nove", "nove"));
		lista_utenti.add(r=new Utente("dieci", "dieci"));
		lista_utenti.add(new Utente("undici", "undici"));
		lista_utenti.add(new Utente("dodici", "dodici"));
		
		for (Utente u: lista_utenti)
		{
			rete.addUser(u);
		}	
		
		Vector<Integer> lista_left = new Vector<Integer>();
		Vector<Integer> lista_right = new Vector<Integer>();
		
		lista_left.add(0); lista_right.add(1);
		lista_left.add(0); lista_right.add(2);
		lista_left.add(1); lista_right.add(3);
		lista_left.add(2); lista_right.add(3);
		lista_left.add(3); lista_right.add(4);
		lista_left.add(3); lista_right.add(5);
		lista_left.add(6); lista_right.add(5);
		lista_left.add(6); lista_right.add(7);
		lista_left.add(11); lista_right.add(7);
		lista_left.add(11); lista_right.add(12);
		lista_left.add(6); lista_right.add(8);
		lista_left.add(9); lista_right.add(8);
		lista_left.add(10); lista_right.add(8);
		lista_left.add(10); lista_right.add(12);
		
		for (int i=0; i<lista_right.size(); i++)
		{
			Utente u1=lista_utenti.get(lista_left.get(i));
			Utente u2=lista_utenti.get(lista_right.get(i));
			rete.changeRelation(u1, u2);
		}
		
		//~ System.out.println(rete);
		
		//~ rete.changeRelation(lista_utenti.get(6), lista_utenti.get(5), false);
		
		//~ System.out.println(rete);
		
		//~ rete.SuperRemove(lista_utenti.get(3), lista_utenti.get(0));
		
		//~ System.out.println(rete);
		
		System.out.println(rete.getRelations(lista_utenti.get(3), 3));
		
		
		
		
	}
}
