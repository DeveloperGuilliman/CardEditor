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

	private String fullName;

	private String link;

	private String parentId;

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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String FullName) {
		this.fullName = FullName;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fullName, id, link, name, parentId);
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
		return Objects.equals(fullName, other.fullName) && Objects.equals(id, other.id)
				&& Objects.equals(link, other.link) && Objects.equals(name, other.name)
				&& Objects.equals(parentId, other.parentId);
	}

	@Override
	public String toString() {
		return "Faction [id=" + id + ", name=" + name + ", fullName=" + fullName + ", link=" + link + ", parentId="
				+ parentId + "]";
	}

}
