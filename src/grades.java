import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class grades {

	
	public grades() {
	}

	public void addGrade() {
		int index = 0;
		Scanner input = new Scanner(System.in);
		
		Person2 p = PersonDatabase2.get1();

		if (p != null) {

			System.out.println("Wpisz ocenę studenta : " + p.getName() + "  " + p.getSurname());
			int grade = input.nextInt();
			p.getOcenki().add(grade);
			System.out.println("Dodano ocenę");
		}
	}

	public void removeGrade() {
		Scanner input = new Scanner(System.in);

		Person2 p = PersonDatabase2.get1();

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
