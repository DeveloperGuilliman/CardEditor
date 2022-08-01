package com.developerguilliman.cardEditor.data;

import java.io.Serializable;
import java.util.Objects;

public class Faction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String link;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, link, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faction other = (Faction) obj;
		return Objects.equals(id, other.id) && Objects.equals(link, other.link) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Faction [id=" + id + ", name=" + name + ", link=" + link + "]";
	}

}
