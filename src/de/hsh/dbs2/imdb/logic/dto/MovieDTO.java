package de.hsh.dbs2.imdb.logic.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Data Transfer Object (DTO) fuer Objekte der Klasse Movie
 * Enthaelt alles noetige fuer die Kommunikation GUI <-> Geschaeftslogik.
 * Dazue gehoeren auch alle dem Movie zugeordneten Genres und Charaktere.
 * Enthaelt die ID; falls es sich um einen neuen Movie handelt, der noch nicht
 * in der Datenbank vorhanden ist, ist die ID null.
 * @author felix
 */
public class MovieDTO {

	private Long id = null;
	private String title = "";
	private String type = "C";
	private int year = 0;
	private Set<String> genres = new HashSet<String>();
	private List<CharacterDTO> characters = new ArrayList<CharacterDTO>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	public void addGenre(String genre) {
		genres.add(genre);
	}
	public void addCharacter(CharacterDTO cdto) {
		characters.add(cdto);
		
	}
	public Set<String> getGenres() {
		return genres;
	}
	public List<CharacterDTO> getCharacters() {
		return characters;
	}
	public void setCharacters(List<CharacterDTO> characters) {
		this.characters = characters;
	}
	public void setGenres(Set<String> genres2) {
		this.genres = genres2;
	}

	/**
	 * Returns a string representation of the object.
	 *
	 * @return a string representation of the object.
	 * @apiNote In general, the
	 * {@code toString} method returns a string that
	 * "textually represents" this object. The result should
	 * be a concise but informative representation that is easy for a
	 * person to read.
	 * It is recommended that all subclasses override this method.
	 * The string output is not necessarily stable over time or across
	 * JVM invocations.
	 * @implSpec The {@code toString} method for class {@code Object}
	 * returns a string consisting of the name of the class of which the
	 * object is an instance, the at-sign character `{@code @}', and
	 * the unsigned hexadecimal representation of the hash code of the
	 * object. In other words, this method returns a string equal to the
	 * value of:
	 * <blockquote>
	 * <pre>
	 * getClass().getName() + '@' + Integer.toHexString(hashCode())
	 * </pre></blockquote>
	 */
	@Override
	public String toString() {
		return "" + id + " " + title + " " + genres + " " + characters;
	}
}
