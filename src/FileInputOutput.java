import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedList;

public class FileInputOutput {

	LinkedList<Person2> lista = (LinkedList) PersonDatabase2.nowa;
	
	int a = PersonDatabase2.a;

	public void saveToFile() throws IOException {
		File file = new File("c:\\java\\lista.txt");

		try (FileOutputStream fos = new FileOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(fos);) {

			for (Person2 pp : PersonDatabase2.nowa) {
		
				oos.writeObject(pp);
			

			}
		}
	}

	public void loadFromFile() throws FileNotFoundException, IOException, ClassNotFoundException, EOFException {

		try {
			File file = new File("c:\\java\\lista.txt");

			Person2 pLoad = null;
			Person2 comparable = null;
			try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis)) {

				while (ois.read() != 0) {
					pLoad = (Person2) ois.readObject();
					if (!compare(pLoad, comparable)) {
						PersonDatabase2.nowa.add(a, pLoad);
						a++;
						continue;
					}
				}
				System.out.println("załadowano");
			} catch (EOFException eof) {
				System.out.println("koniec pliku - wszystko wczytane");
			} catch (FileNotFoundException fnf) {
				System.out.println("Nie znaleziono podanego pliku : " + file.getAbsolutePath());
			}
		} finally {
		}
	}

	public boolean compare(Person2 p1, Person2 p2) {
		for (Person2 p3 : lista) {
			p2 = p3;

			if (p1.getPesel().equals(p2.getPesel())) {
				System.out.println("już taki jest");
				return true;
			}
		}
		return false;
	}
}
