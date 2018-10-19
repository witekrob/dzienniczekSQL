import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class grades {
	PersonDatabase2 db2 = new PersonDatabase2();
	LinkedList<Person2> nn = (LinkedList<Person2>) db2.nowa;

	public grades() {

	}
	public void addGrade() {

		Scanner input = new Scanner(System.in);
		Person2 p = db2.get1();

		if (p != null) {

			System.out.println("Wpisz ocenę studenta : " + p.getName() + "  " + p.getSurname());
			int grade = input.nextInt();

			p.getOcenki().add(grade);

			System.out.println("Dodano ocenę");

		}
	}

	public void removeGrade() {
		Scanner input = new Scanner(System.in);

		Person2 p = db2.get1();

		List<Integer> grady = new LinkedList<Integer>();
		grady = p.getOcenki();
		getAll(p);

		System.out.println("wpisz ocenę do usunięcia");
		int delGrade = input.nextInt();

		for (int zz = 0; zz < grady.size(); zz++) {
			int foundIndex = grady.get(zz);

			if (foundIndex == delGrade) {
				grady.remove(zz);
				System.out.println("usunięto " + delGrade);
				break;
			}

		}
	}

	public void getAll(Person2 p) {
		System.out.println("Oto oceny ucznia : " + p.getName());
		for (int g : p.getOcenki()) {
			System.out.println(g);
		}
	}
}
