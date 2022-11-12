package de.hsh.dbs2.imdb.logic;

import de.hsh.dbs2.imdb.activerecords.Person;
import de.hsh.dbs2.imdb.factories.PersonFactory;

import java.util.ArrayList;
import java.util.List;

public class PersonManager {

	/**
	 * Liefert eine Liste aller Personen, deren Name den Suchstring enthaelt.
	 * @param text Suchstring
	 * @return Liste mit passenden Personennamen, die in der Datenbank eingetragen sind.
	 * @throws Exception
	 */
	public List<String> getPersonList(String text) throws Exception {
		// TODO DONE
		List<Person> personList = PersonFactory.getPersonsByName(text);

		List<String> result = new ArrayList<>();
		for (Person person : personList) {
			result.add(person.getName());
		}

		return result;
	}

}
