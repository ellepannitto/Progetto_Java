
import java.io.*;
import java.util.*;
import org.jdom2.*;

/**
 * @author Corradini Celestino, mat.
 * @author Mercadante Giulia, mat.
 * @author Pannitto Ludovica , mat. 491094
 * @author Rambelli Giulia, mat.
 * 
 * */

public class Loader
{
	private ReteSociale loadObject(String file_rete)
	{
		ReteSociale rete=null;
		
		try {
			
			ObjectInputStream file_input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file_rete)));
			rete = (ReteSociale) file_input.readObject();
			file_input.close();
			
		} catch (FileNotFoundException e) {
			
			System.out.println("ATTENZIONE: Il file non esiste");
			System.out.println("Sara' creato al primo salvataggio\n");
			rete= new ReteSociale();
			
		} catch (ClassNotFoundException e) {

			System.out.println("ERRORE di lettura: "+e);
			System.exit(1);

		} catch (IOException e) {

			System.out.println("ERRORE di I/O: "+e);
			System.exit(1);
		}
		
		
		rete.setFile(file_rete);
	
		return rete;
	}
	
	private ReteSociale loadFromXML(String file_rete)
	{
		ReteSociale rete = null;
		
		try
		{
			File fileinput = new File(file_rete);
			SAXBuilder saxBuiler = new SAXBuilder();
			
			Document document = saxBuilder.build(fileinput);
			
			System.out.println("Root element :" + document.getRootElement().getName());
			
		}
		catch(Exception e)
		{
			;
		}
		
	}
	
	
	public ReteSociale loadFromFile(String file_rete){

		ReteSociale rete=null;
	
		boolean isXML = file_rete.endsWith(".xml");
		
	
		if (isXML)
		{
			rete = loadFromXML(file_rete);
		}
		else
		{
			rete = loadObject(file_rete);
		}
		
		return rete;
		
	}
	
}
