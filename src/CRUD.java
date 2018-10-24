import java.sql.SQLException;
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
		LinkedList<Person2> dbList = new LinkedList<Person2>();

		String name = null;
		String surname = null;
		String pesel = null;
	
		while (reSet.next()) {
			List<Integer> grades = new LinkedList<Integer>();
			name = reSet.getString("name");
			surname = reSet.getString("surname");
			pesel = reSet.getString("pesel");
			grades = getGradesFromDb(pesel);
			
			Person2 pers = new Person2(name, surname, pesel, grades);
			dbList.add(pers);
		}
		return dbList;
	}

	public void addAllFromDbToLocal() throws SQLException {
		LinkedList<Person2> fromDb = getAllFromDb();
		
		for (Person2 p : fromDb) {
			PersonDatabase2.addToLocal(p);
		}
	}

	public void delete() throws SQLException {

		Connection con = connect.getConnection();

		System.out.println("podaj pesel osoby do usuniÄ™cia");
		int pes = input.nextInt();
		String deleteQuery = "DELETE FROM uczniowie WHERE pesel=?;";
		PreparedStatement prepStmt = con.prepareStatement(deleteQuery);
		prepStmt.setInt(1, pes);
		int result = prepStmt.executeUpdate();
		con.close();
	}

	public void addNewOrUpdate() throws SQLException {
		System.out.println("podaj imiÄ™ studenta do dodania");
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
		List<Integer> grades = pers.getOcenki();

		String createQuery = "INSERT into uczniowie(name,surname,pesel) VALUES (?,?,?);";
		PreparedStatement prepStat = con.prepareStatement(createQuery);
		prepStat.setString(1, name);
		prepStat.setString(2, surname);
		prepStat.setString(3, pesel);
		prepStat.executeUpdate();
		addGrades(pers, grades);
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
		addGrades(p, p.getOcenki());
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
			addGrades(newPerson, newPerson.getOcenki());
		} else {
			System.out.println("Nie ma takiej osoby - dodajÄ™ nowÄ…");
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
						System.out.println("Nie ma takiej osoby - dodajÄ™ nowÄ…");
						addToDb(personLocal);
						
						
					}
				} catch (NoSuchElementException e) {
					System.out.println("brak zmian");
					break;
				}
			}
		}
	}

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

	public void addGrades(Person2 pers, List<Integer> grades) throws SQLException {
		String sqlAddGrade = "Insert into grades(pesel,grade) VALUES (?,?);";
		String pesel = pers.getPesel();
		System.out.println("jestem jestem dodaje, oceny");
		grades = (LinkedList<Integer>) pers.getOcenki();
		Connection con = connect.getConnection();
		PreparedStatement prep = con.prepareStatement(sqlAddGrade);
		prep.setNString(1, pesel);
		for (int i=0;i<grades.size();i++) {
		prep.setInt(2, grades.get(i));
		prep.executeUpdate();
		}
		con.close();
	}
	public LinkedList<Integer> getGradesFromDb (String pesel) throws SQLException {
	Connection con = connect.getConnection();
	String getGradesSQL = "Select * from dzienniczek.grades WHERE pesel=?;";
	PreparedStatement prep = con.prepareStatement(getGradesSQL);
	prep.setString(1, pesel);
	ResultSet reSet = 	prep.executeQuery();

	LinkedList<Integer> grades = new LinkedList<>();
	
	while (reSet.next()) {
	int grade = reSet.getInt("grade");
	grades.add(grade);
	}
	return grades;

}
}
