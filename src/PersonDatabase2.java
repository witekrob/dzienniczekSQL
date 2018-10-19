import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class PersonDatabase2 implements Readable {
	public static int a = 0;

	public static List<Person2> nowa;
	public static Scanner input = new Scanner(System.in);

	public PersonDatabase2() {
		nowa = new LinkedList<Person2>();
	}

	public static Person2 findByPesel(Person2 check) {
		Person2 found = null;

		for (Person2 p : nowa) {
			if (p.getPesel().equals(check.getPesel())) {

				System.out.println("porównuję : " + check.toString() + " z : " + p.toString());
				found = p;
				break;
			} else {
				found = null;
			}
		}
		return found;
	}

	public static void addToLocal(Person2 newPerson) {

		if (nowa.isEmpty()) {
			nowa.add(newPerson);
		} else {
			Person2 check = findByPesel(newPerson);
			if (check == null) {
				System.out.println("Dodaję nowy do bazy lokalnej");
				nowa.add(newPerson);
			} else {
				String newName = newPerson.getName();
				String newSurname = newPerson.getSurname();
				List<Integer> newOcenki = newPerson.getOcenki();
				check.setName(newName);
				check.setSurname(newSurname);
				check.setOcenki(newOcenki);
			}
		}
	}

	public void create() {

		Person2 p = new Person2();
		List<Integer> grades = new LinkedList<Integer>();

		System.out.println("podaj imiê : ");
		String name = input.next();
		p.setName(name);

		System.out.println("podaj nazwisko : ");
		String surname = input.next();
		p.setSurname(surname);

		System.out.println("podaj pesel : ");
		String pesel = input.next();
		p.setPesel(pesel);
		p.setOcenki(grades);

		addToLocal(p);
	}

	public void getAll() {
		Iterator<Person2> iter = nowa.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
	}

	public Person2 get1() {

		int choice = 99;
		int byNameAndSurname = 1;
		int byPesel = 2;
		int exit = 0;
		Person2 looked = null;
		Iterator<Person2> iter = nowa.iterator();

		while (choice != 0) {
			System.out.println("W jaki sposób szukać?");
			System.out.println("1) wpisz imię i nazwisko");
			System.out.println("2) wpisz pesel studenta");
			System.out.println("0) przejdz poziom wyżej");
			choice = input.nextInt();
			switch (choice) {
			case 1:

				System.out.println("Podaj imiê osoby do wyœwietlenia");
				String name = input.next();
				System.out.println("Podaj nazwisko osoby do wyœwietlenia");
				String surname = input.next();

				int count = 0;

				for (Person2 look : nowa) {
					if (look.getName().equals(name) && look.getSurname().equals(surname)) {
						looked = look;
						count++;
						break;
					}
				}

				if (count == 1) {
					System.out.println("znalazłem");
					System.out.println(looked.toString());
					return looked;

				} else if (count == 0) {
					System.out.println("nie znaleziono studenta o takich danych");
					break;
				} else {

					System.out.println("znaleziono więcej wyników o takich danych");
					break;
				}

			case 2:
				System.out.println("Podaj pesel osoby do wyœwietlenia");
				String pesel = input.next();
				Person2 found = new Person2();

				for (Person2 look : nowa) {
					if (look.getPesel().equals(pesel)) {
						found = look;
						System.out.println("znalazłem : " + found.toString());
						return found;

					} else {
						System.out.println("nie znalazlem");
						break;
					}

				}
				if (found.getOcenki() != null) {
					System.out.println("a jego oceny to: ");
					for (int g : found.getOcenki()) {
						System.out.println(g);
						looked = found;
					}

				}
			}
			break;
		}

		return looked;
	}

	public void remove() {
		System.out.println("Podaj nazwisko osoby do usuniêcia z listy");
		String surname = input.next();
		System.out.println("Podaj imię osoby do usuniêcia z listy");
		String name = input.next();
		int counter = 0;

		Person2 szukana = null;

		for (int i = 0; i < nowa.size(); i++) {
			Person2 lValue = nowa.get(i);
			if (lValue.getName().equals(name)) {
				szukana = lValue;
				counter++;
			}
		}

		if (counter == 1) {
			nowa.remove(szukana);
			System.out.println("udano usunięcie");
		}

		else if (counter > 1) {
			System.out.println("znalaziono za dużo wyników");
		}
	}

	@Override
	public int read(CharBuffer cb) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}
}