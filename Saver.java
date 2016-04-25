import java.io.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author Corradini Celestino, mat.
 * @author Mercadante Giulia, mat.
 * @author Pannitto Ludovica , mat. 491094
 * @author Rambelli Giulia, mat.
 * 
 * */

public class Saver
{
	public void save(ReteSociale rete)
	{
		String file = rete.getSavingFile();
		
		save(rete, file);
	}
	
	public void save(ReteSociale rete, String file_salvataggio)
	{
		try {
			ObjectOutputStream file_output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file_salvataggio)));
			
			file_output.writeObject(rete);
			file_output.close();
			
		} catch (IOException e) {
			System.out.println("ERRORE di I/O.");
			e.printStackTrace();
		}
	}
	
	public void saveXML(ReteSociale rete, String file_xml) throws FileNotFoundException, UnsupportedEncodingException
	{
		PrintWriter writer = new PrintWriter(file_xml, "UTF-8");
		
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
	}
	
}
