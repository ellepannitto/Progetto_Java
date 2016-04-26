import java.util.*;

/**
 * @author Corradini Celestino, mat.
 * @author Mercadante Giulia, mat.
 * @author Pannitto Ludovica , mat. 491094
 * @author Rambelli Giulia, mat.
 * 
 * Gestisce le operazioni di input
 * */
public class Tastiera
{
	private Scanner input=new Scanner(System.in);
	
	
	/**
	 * @return un intero preso in input
	 * 
	 * 
	 * */
	public int nextInt()
	{
		boolean scelto=false;
		int selezione=-1;
		String str;
		
		while (!scelto)
		{
			try
			{
				str = input.next ();
				selezione = Integer.parseInt(str);
				scelto=true;
			}
			catch(NumberFormatException e)
			{
				System.out.println("Non hai inserito un intero, riprova!");
				//~ input.next();
			}

		}
		
		return selezione;
	}
	
	/**
	 * Gestisce l'input di una stringa eventualmente intervallata da spazi
	 * 
	 * @return stringa s
	 * 
	 * */
	public String next()
	{
		boolean condizione=false;
		String s="";
		while (!condizione)
		{
			String nuovo= input.next();
			
			if (!nuovo.equals(""))
			{
				//~ System.out.println(s);
				s+=input.next();
			}
			else
			{
				condizione=true;
			}
			
		}
		return s;
	}
	
	/**
	 * Aspetta un input
	 * 
	 * 
	 * */
	public void aspetta()
	{
		System.out.println("\npremi ENTER per continuare");
		try
		{
			System.in.read();
		}catch(Exception e){
			;
		}
		
		
	}
	
	/**
	 * 
	 * @return un'intera riga presa in input, eventualmente contenente spazi 
	 * 
	 * */
	public String nextLine()
	{
		input.useDelimiter("\\n");
		return input.next();
	}
	
	public String nextAlphabetic()
	{
		String stringa = "";
		
		boolean check = false;
	
		while (!check)
		{
			stringa = nextLine();
			stringa = stringa.trim();
			if(!(stringa.matches("[a-zA-Z ]+"))) 
				System.out.println("ERRORE: Inserire un nome valido ");
			else
				check=true;
		}
		
		return stringa;

	}
}
