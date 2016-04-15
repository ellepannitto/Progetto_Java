import java.io.*;

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
			System.out.println("ERRORE di I/O: "+e);
		}
	}
	
}
