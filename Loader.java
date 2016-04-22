
import java.io.*;
import java.util.*;


import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


/**
 * @author Corradini Celestino, mat.
 * @author Mercadante Giulia, mat.
 * @author Pannitto Ludovica , mat. 491094
 * @author Rambelli Giulia, mat.
 * 
 * */

public class Loader
{
	
	public Loader(){
		System.out.println("Creo Loader");
	}
	
	
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
			SAXBuilder saxBuilder = new SAXBuilder();
			
			Document document = saxBuilder.build(fileinput);
			
			Element radice = document.getRootElement();
			
			List<Element> utenti = radice.getChildren();
			
			Map<Integer, Utente>  mapping_utenti = new HashMap <Integer, Utente>();
			Map<Integer, Vector<Integer>>  mapping_rete = new HashMap <Integer, Vector<Integer>>();
			
			for(int i=0; i<utenti.size(); i++)
			{
				Element nodo_utente = utenti.get(i);
				int id = Integer.parseInt( nodo_utente.getAttribute("id").getValue() );	
				
				
				String nome = nodo_utente.getChild("nome").getText();
				String cognome = nodo_utente.getChild("cognome").getText();
				
				Utente u=new Utente(nome, cognome);
				
				Element nodo_amici = nodo_utente.getChild("amici");
				
				List<Element> id_amici = nodo_amici.getChildren();
				
				Vector<Integer> lista_amici = new Vector<Integer>();
				
				for (int j=0; j<id_amici.size(); j++)
				{
					Element nodo_amico = id_amici.get(j);
					
					int id_amico = Integer.parseInt( nodo_amico.getText() );
					
					lista_amici.addElement( id_amico );
				}	
				
				mapping_utenti.put(id, u);
				mapping_rete.put(id, lista_amici);
			}
			
			rete = new ReteSociale();
			
			rete.caricaDaXML ( mapping_utenti, mapping_rete );
			
		}
		catch(JDOMException e)
		{
			e.printStackTrace();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		
		return rete;
		
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
