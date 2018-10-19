import java.io.Serializable;
import java.util.List;

public class Person2 implements Serializable {

	private String name;
	private String surname;
	private String pesel;
	private List<Integer> ocenki;

	public Person2(String name, String surname, String pesel, List ocenki) {

		this.setName(name);
		this.setSurname(surname);
		this.setPesel(pesel);
		this.setOcenki(ocenki);

	}
	public Person2(String name,String surname,String pesel) {
		this.setName(name);
		this.setSurname(surname);
		this.setPesel(pesel);
	}

	public Person2() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pesel == null) ? 0 : pesel.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person2 other = (Person2) obj;
		if (ocenki != other.ocenki)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pesel == null) {
			if (other.pesel != null)
				return false;
		} else if (!pesel.equals(other.pesel))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "Student [imi�=" + name + ", nazwisko=" + surname + ", pesel=" + pesel + " , oceny = " + ocenki + "]";
	}

	public List<Integer> getOcenki() {
		return ocenki;
	}
	
	public void setOcenki(List<Integer> ocenki) {
		this.ocenki = ocenki;
	}
}