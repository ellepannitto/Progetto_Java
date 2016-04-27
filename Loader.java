
import java.io.*;
import java.util.*;


import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


/**
 * @author Corradini Celestino, mat. 527813
 * @author Mercadante Giulia, mat.
 * @author Pannitto Ludovica , mat. 491094
 * @author Rambelli Giulia, mat.
 *
 *  
 * */

/**
 * Contiene i metodi per caricare una rete sociale da file.
 * 
 * 
 * */
public class Loader
{
	
	/**
	 * Carica un oggetto di tipo ReteSociale da file.
	 * In caso di errore restituisce null.
	 * 
	 * @param file_rete nome del file
	 * 
	 * @return la rete caricata
	 * 
	 * */
	private ReteSociale loadObject(String file_rete)
	{
		ReteSociale rete = new ReteSociale();
		
		try {
			
			ObjectInputStream file_input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file_rete)));
			rete = (ReteSociale) file_input.readObject();
			rete.setFile(file_rete);
			file_input.close();
			
		} catch (FileNotFoundException e) {
			
			System.out.println("ATTENZIONE: Il file non esiste.");
			System.out.println("Sara' creato al primo salvataggio.");
			
			rete= new ReteSociale();
			rete.setFile(file_rete);
			
		} catch (ClassNotFoundException e) {

			System.err.println("ERRORE: classe non trovata.");
			e.printStackTrace();
			System.exit (1);

		} catch (IOException e) {
			
			System.err.println("ERRORE di I/O.");
			e.printStackTrace();
			System.exit (1);			
		}
		
		return rete;
	}
	
	/**
	 * Carica una rete sociale da un file XML.
	 * In caso di errore restituisce null.
	 * 
	 * @param file_rete	nome del file
	 * 
	 * @return la rete caricata
	 * 
	 * */
	private ReteSociale loadFromXML(String file_rete)
	{
		ReteSociale rete = new ReteSociale();
		
		Map<Integer, Utente>  mapping_utenti = new HashMap <Integer, Utente>();
		Map<Integer, Vector<Integer>>  mapping_rete = new HashMap <Integer, Vector<Integer>>();
		
		try
		{
			File fileinput = new File(file_rete);
			SAXBuilder saxBuilder = new SAXBuilder();
			
			Document document = saxBuilder.build(fileinput);
			
			Element radice = document.getRootElement();
			
			List<Element> utenti = radice.getChildren();
				
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
			
		}
		catch(JDOMException e)
		{
			System.err.println ("Errore durante il parsing XML.");
			System.err.println (e);
			System.exit (1);
		}
		catch(IOException ioe)
		{
			System.err.println ("Errore durante l'apertura del file.");
			System.err.println (ioe);
			System.exit (1);
		}
		
		rete.caricaDaGrafo ( mapping_utenti, mapping_rete );
		return rete;
		
	}
	
	/**
	 * Carica una rete da un file XML o serializzato.
	 * In caso di errore stampa un messaggio e termina l'esecuzione.
	 * 
	 * 
	 * @param file_rete nome del file
	 * 
	 * @return la rete
	 * 
	 * */
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
		
		if (rete == null)
		{
			System.err.println ("Errore durante il caricamento della rete da file.");
			System.exit(1);
		}
		return rete;
	}
	
}
