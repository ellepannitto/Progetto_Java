import java.io.*;

public class Loader
{
	public ReteSociale loadFromFile(String file_rete){
		
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
		System.out.println ("[DEBUG] prossimo id: "+rete.next_user_id);
		return rete;
	}
}
