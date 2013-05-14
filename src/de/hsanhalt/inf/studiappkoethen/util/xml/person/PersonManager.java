package de.hsanhalt.inf.studiappkoethen.util.xml.person;

import java.util.HashSet;
import java.util.Set;

public class PersonManager {
	
	private static PersonManager INSTANCE;
	
	
	private Set<Person> person;
	
	
	private PersonManager(){
		
		person = new HashSet<Person>();
		// TODO XML DateiEN laden und Personenobjekte erzeugen.
	}
	
	public void addPerson(Person newperson){
		this.person.add(newperson);
		// TODO Sortieren nach Kategorie....
	}
	
	/**
	 * Gibt eine Instanz dieser Klasse zurueck und sorgt dafuer, dass auch nur
	 * eine Instanz erstellt wird! Ganz ala Singleton.
	 * 
	 * @return eine Instanz!
	 */
	public static PersonManager getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new PersonManager();
		}
		return INSTANCE;
	}
	
	

}
