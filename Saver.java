import java.io.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author Corradini Celestino, mat.
 * @author Mercadante Giulia, mat.
 * @author Pannitto Ludovica , mat. 491094
 * @author Rambelli Giulia, mat.
 * 
 * contiene i metodi per memorizzare la rete su un file XML o serializzato
 * 
 * */
public class Saver
{
	/**
	 * 
	 * salva la rete serializzandola  sul file specificato dalla rete stessa
	 * 
	 * @param rete rete da salvare
	 * 
	 * */
	public void save(ReteSociale rete)
	{
		String file = rete.getSavingFile();
		
		save(rete, file);
	}
	
	/**
	 * 
	 * salva la rete serializzandola sul file specificato come parametro
	 * 
	 * @param rete rete da salvare
	 * @param file_salvataggio nome del file su cui salvare
	 * 
	 * */
	public void save(ReteSociale rete, String file_salvataggio)
	{
		try {
			ObjectOutputStream file_output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file_salvataggio)));
			
			file_output.writeObject(rete);
			file_output.close();
			
		} catch (IOException e) {
			System.err.println("ERRORE di I/O.");
			e.printStackTrace();
			System.exit (1);
		}
		
		System.out.println ("Rete salvata su "+file_salvataggio);
	}
	
	/**
	 * 
	 * Salva la rete su un file XML
	 * 
	 * @param rete rete da salvare
	 * @param file_xml nome del file su cui salvare 
	 * 
	 * @throws FileNotFoundException se il file non esiste ma non può essere creato, oppure se si verifica qualche altro errore
	 *
	 *  */
	public void saveXML(ReteSociale rete, String file_xml) throws FileNotFoundException
	{
		
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(file_xml, "UTF-8");
		} catch (UnsupportedEncodingException e )
		{
			System.err.println (e);
			e.printStackTrace();
			System.exit (1);
		}
		
		Map<Integer, Vector<Integer>> grafo = rete.getRete();
		Map<Integer, Utente> persone = rete.getUtenti();
		
		
		Iterator<Entry<Integer, Vector<Integer>>> it = grafo.entrySet().iterator();
		writer.println("<?xml version=\"1.0\"?>");
		writer.println("<ReteSociale>");
		
		while (it.hasNext()) {
		
		    Map.Entry<Integer, Vector<Integer>> entry = it.next();
		    
			writer.println("\t<Utente id='"+entry.getKey()+"'>");
			
			Utente u=persone.get(entry.getKey());
			
			writer.println("\t\t<nome>"+u.getNome()+"</nome>");
			writer.println("\t\t<cognome>"+u.getCognome()+"</cognome>");
		
			writer.println("\t\t<amici>");
			Vector<Integer> amici = entry.getValue();	
			for (int i : amici) {
				writer.println("\t\t\t<id>"+i+"</id>");
		    }
			writer.println("\t\t</amici>");
			writer.println("\t</Utente>");
		}
		writer.println("</ReteSociale>");
		writer.close();
		
		System.out.println ("Rete salvata su "+file_xml);

	}
	
}
