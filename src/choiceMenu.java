import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class choiceMenu {
	final int EXIT = 0;

	choiceMenu() throws IOException, ClassNotFoundException, SQLException {
		PersonDatabase2 db = new PersonDatabase2();
		grades gr = new grades();
		FileInputOutput fio = new FileInputOutput();
		CRUD crud = new CRUD();

		Scanner input = new Scanner(System.in);
		int choice = 999;

		while (choice != 0) {
			System.out.println("Co chcesz zrobiÃ¦ ?  ");
			System.out.println("1) dodaj(uaktualnij dane) ludzi do bazy lokalnej	");
			System.out.println("2) wyÅ“wietl wszystkie rekordy	");
			System.out.println("3) wyÅ“wietl jeden wybrany rekord	");
			System.out.println("4) usuwa rekord lokalny o podanym nazwisku");
			System.out.println("5) dodaje ocenÄ™");
			System.out.println("6) zapisz liste do pliku");
			System.out.println("7) wgraj listÄ™ z pliku");
			System.out.println("8) usuÅ„ ocenÄ™ ");
			System.out.println("0) exit");
			System.out.println("11) pobierz z  DB");
			System.out.println("12) usun z DB");
			System.out.println("13) dodaj studenta to DB");
			System.out.println("14) zaaktualizuj imie studenta z DB");
			
			System.out.println("15) upload do DB");
			
			
			choice = input.nextInt();
			switch (choice) {
			case 1:
				db.create();
				break;
			case 2:
				db.getAll();
				break;
			case 3:
				db.get1();
				break;
			case 4:
				db.remove();
				break;
			case 5:
				gr.addGrade();
				break;
			case 6:
				fio.saveToFile();
				break;
			case 7:
				fio.loadFromFile();
				break;
			case 8:
				gr.removeGrade();
				break;
			case 11:
				crud.addAllFromDbToLocal();
				break;
			case 12:
				crud.delete();
				break;
			case 13:
				crud.addNewOrUpdate();
				break;
			case 14:
				crud.updateOneRecord();
				break;
			case 15:
				crud.uploadDb();
				break;
			}
		}
	}
}
