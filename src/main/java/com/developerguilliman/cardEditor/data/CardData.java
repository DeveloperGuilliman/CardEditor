/*
 * Copyright (C) 2020 Developer Guilliman <developerguilliman@gmail.com>
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

/**
 *
 * @author Developer Guilliman <developerguilliman@gmail.com>
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

    private String costValue;

    private String costType;

    public CardData() {
        this("", "", "", "", "", "");
    }

    public CardData(String title, String name, String legend, String rules, String costValue, String costType) {
        this.title = title;
        this.name = name;
        this.legend = legend;
        this.rules = rules;
        this.costValue = costValue;
        this.costType = costType;
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

    public String getCostValue() {
        return costValue;
    }

    public void setCostValue(String costValue) {
        this.costValue = costValue;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public void trimStrings() {
        this.title = title.trim();
        this.name = name.trim();
        this.legend = legend.trim();
        this.rules = rules.trim();
        this.costValue = costValue.trim();
        this.costType = costType.trim();
    }

    @Override
    public String toString() {
        return "CardData{" + "title=" + title + ", name=" + name + ", legend=" + legend + ", rules=" + rules + ", costValue=" + costValue + ", costType=" + costType + '}';
    }

}
