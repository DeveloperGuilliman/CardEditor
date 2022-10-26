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
package com.developerguilliman.cardEditor.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;

/**
 *
 * @author Developer Guilliman <developerguilliman@gmail.com>
 */
public class JColorChooserButton extends JButton {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3466647951142008682L;
	
	private static final String[] HEX_COLOR_PREFIXES = {"#000000", "#00000", "#0000", "#000", "#00", "#0", "#"};
    private static final int LOW_LUMA_LIMIT = 120;

    private Color selectedColor;

    public JColorChooserButton() {
        this(Color.BLACK);
    }

    public JColorChooserButton(Color color) {
        super();
        super.setContentAreaFilled(false);
        changeSelectedColor(color);
    }

    public JColorChooserButton(Icon icon) {
        this(icon, Color.BLACK);
    }

    public JColorChooserButton(Icon icon, Color color) {
        super(icon);
        super.setContentAreaFilled(false);
        changeSelectedColor(color);
    }

    public JColorChooserButton(Action a) {
        this(a, Color.BLACK);
    }

    public JColorChooserButton(Action a, Color color) {
        super(a);
        super.setContentAreaFilled(false);
        changeSelectedColor(color);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(selectedColor);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
        super.paintComponent(g);
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color color) {
        changeSelectedColor(color);
    }

    protected final void changeSelectedColor(Color color) {
        this.selectedColor = color;
        setText(getSelectedColorString(color));
        setForeground(getFastLuma(color) < LOW_LUMA_LIMIT ? Color.WHITE : Color.BLACK);
    }

    public Color openSelectColorDialog(Component parent, String text) {
        Color color = JColorChooser.showDialog(parent, text, selectedColor);
        if (color != null) {
            changeSelectedColor(color);
        }
        return color;
    }

    private static String getSelectedColorString(Color color) {
        String hex = Integer.toHexString(color.getRGB() & 0xFFFFFF).toUpperCase();
        return HEX_COLOR_PREFIXES[hex.length()].concat(hex);
    }

    private static int getFastLuma(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        return (r + r + r + b + g + g + g + g) >> 3;
    }

}
