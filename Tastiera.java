import java.util.*;

/**
 * @author Corradini Celestino, mat.
 * @author Mercadante Giulia, mat.
 * @author Pannitto Ludovica , mat. 491094
 * @author Rambelli Giulia, mat.
 * 
 * */

public class Tastiera
{
	private Scanner input=new Scanner(System.in);
	
	
	/**
	 * 
	 * 
	 * 
	 * */
	public int nextInt()
	{
		boolean scelto=false;
		int selezione=-1;
		
		while (!scelto)
		{
			try
			{
				selezione=input.nextInt();
				scelto=true;
			}
			catch(InputMismatchException e)
			{
				System.out.println("Non hai inserito un intero, riprova!");
				input.next();
			}

		}
		
		return selezione;
	}
	
	/**
	 * 
	 * 
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
	 * 
	 * 
	 * 
	 * */
	public void aspetta()
	{
		//~ String carattere;
		//~ if (!input.hasNextLine())
		//~ {
			//~ carattere = input.nextLine();
		//~ }
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
	 * 
	 * 
	 * */
	public String nextLine()
	{
		input.useDelimiter("\\n");
		return input.next();
	}
}
