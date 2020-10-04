/*
 * Copyright (C) 2020 Developer Guilliman
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.developerguilliman.cardEditor.data;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author DeveloperGuilliman
 */
public class CardData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String title;

	private String name;

	private String legend;

	private String rules;

	private String cost;

	public CardData() {
	}

	public CardData(String title, String name, String legend, String rules, String cost) {
		this.title = title.trim();
		this.name = name.trim();
		this.legend = legend.trim();
		this.rules = rules.trim();
		this.cost = cost.trim();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLegend() {
		return legend;
	}

	public void setLegend(String legend) {
		this.legend = legend;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final CardData other = (CardData) obj;
		if (!Objects.equals(this.title, other.title)) {
			return false;
		}
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		if (!Objects.equals(this.legend, other.legend)) {
			return false;
		}
		if (!Objects.equals(this.rules, other.rules)) {
			return false;
		}
		if (!Objects.equals(this.cost, other.cost)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "StratagemData{" + "title=" + title + ", name=" + name + ", legend=" + legend + ", rules="
				+ rules + ", cost=" + cost + '}';
	}

}
