import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.lang.module.FindException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CRUD {
	dbConnector connect = new dbConnector();
	Scanner input = new Scanner(System.in);

	int a = PersonDatabase2.a;

	public CRUD() {
	}

	public LinkedList<Person2> getAllFromDb() throws SQLException {
		Connection con = connect.getConnection();
		String sqlQuery = "SELECT * from uczniowie";
		PreparedStatement prepStat = con.prepareStatement(sqlQuery);
		ResultSet reSet = prepStat.executeQuery();
		PersonDatabase2 database = new PersonDatabase2();
		LinkedList<Person2> dbList = new LinkedList<Person2>();

		String name = null;
		String surname = null;
		String pesel = null;

		while (reSet.next()) {
			name = reSet.getString("name");
			surname = reSet.getString("surname");
			pesel = reSet.getString("pesel");
			Person2 pers = new Person2(name, surname, pesel, null);
			dbList.add(pers);
		}
		return dbList;
	}

	public void addAllFromDbToLocal() throws SQLException {
		LinkedList<Person2> fromDb = getAllFromDb();
		PersonDatabase2 database = new PersonDatabase2();

		for (Person2 p : fromDb) {
			database.addToLocal(p);
		}
	}

	public void delete() throws SQLException {

		Connection con = connect.getConnection();

		System.out.println("podaj pesel osoby do usunięcia");
		int pes = input.nextInt();
		String deleteQuery = "DELETE FROM uczniowie WHERE pesel=?;";
		PreparedStatement prepStmt = con.prepareStatement(deleteQuery);
		prepStmt.setInt(1, pes);
		int result = prepStmt.executeUpdate();
		con.close();
	}

	public void addNewOrUpdate() throws SQLException {
		System.out.println("podaj imię studenta do dodania");
		String name = input.next();
		System.out.println("podaj nazwisko studenta do dodania");
		String surname = input.next();
		System.out.println("podaj pesel " + name + " " + surname);
		String pesel = input.next();

		Person2 newPerson = new Person2(name, surname, pesel, null);

		if (!checkByPeselInDB(newPerson)) {
			addToDb(newPerson);
		} else {
			updateInDb(newPerson);
		}
	}

	public void addToDb(Person2 pers) throws SQLException {
		Connection con = connect.getConnection();

		String name = pers.getName();
		String surname = pers.getSurname();
		String pesel = pers.getPesel();

		String createQuery = "INSERT into uczniowie(name,surname,pesel) VALUES (?,?,?);";
		PreparedStatement prepStat = con.prepareStatement(createQuery);
		prepStat.setString(1, name);
		prepStat.setString(2, surname);
		prepStat.setString(3, pesel);
		prepStat.executeUpdate();
		con.close();
	}

	public void updateInDb(Person2 p) throws SQLException {
		Connection conn = connect.getConnection();
		String query = "UPDATE uczniowie SET name=?,surname=? WHERE pesel = ?;";

		String name = p.getName();
		String surname = p.getSurname();
		String pesel = p.getPesel();

		PreparedStatement prep = conn.prepareStatement(query);
		prep.setString(1, name);
		prep.setString(2, surname);
		prep.setString(3, pesel);
		prep.executeUpdate();
		conn.close();
	}

	public void updateOneRecord() throws SQLException {

		System.out.println("Podaj pesel osoby do zaktualizowania jego imienia i nazwiska w bazie ");
		String pesel = input.next();
		System.out.println("Wpisz poprawione imie osoby o numerze pesel  " + pesel);
		String name = input.next();
		System.out.println("Wpisz poprawione nazwisko osoby o numerze pesel  " + pesel);
		String surname = input.next();

		Person2 newPerson = new Person2(name, surname, pesel);

		if (checkByPeselInDB(newPerson)) {
			updateInDb(newPerson);
		} else {
			System.out.println("Nie ma takiej osoby - dodaję nową");
			addToDb(newPerson);
		}

	}

	public void uploadDb() throws SQLException {
		Iterator<Person2> iter = PersonDatabase2.nowa.iterator();
		LinkedList<Person2> fromDb = getAllFromDb();
		Iterator<Person2> iterDb = fromDb.iterator();

		while (iter.hasNext()) {
			while (iterDb.hasNext()) {

				try {
					Person2 personLocal = iter.next();
					if (checkByPeselInDB(personLocal)) {
						updateInDb(personLocal);
					} else {
						System.out.println("Nie ma takiej osoby - dodaję nową");
						addToDb(personLocal);
					}
				} catch (NoSuchElementException e) {
					System.out.println("brak zmian");
					break;
				}
			}
		}
	}

	/*
	 * if (peselDb.equals(peselLocal)) {
	 * System.out.println("zmiany w rekordzie o numerze pesel : " + peselDb);
	 * updateInDb(personLocal); break; } else {
	 * System.out.println("nie ma w bazie więc dodaje"); addToDb(personLocal);
	 * break; } } }
	 */

	public boolean checkByPeselInDB(Person2 checkedPerson) throws SQLException {

		boolean result = false;
		String checkedPersonPesel = checkedPerson.getPesel();

		LinkedList<Person2> fromDb = getAllFromDb();
		Iterator<Person2> iterDb = fromDb.iterator();

		while (iterDb.hasNext()) {

			Person2 personDb = iterDb.next();
			String peselDb = personDb.getPesel();

			if (peselDb.equals(checkedPersonPesel)) {
				result = true;
				break;
			} else {
				result = false;
			}
		}
		return result;
	}
}