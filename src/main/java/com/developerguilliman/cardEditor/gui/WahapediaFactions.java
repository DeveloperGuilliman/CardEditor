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

import java.awt.Frame;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Callable;

import javax.swing.JOptionPane;

import com.developerguilliman.cardEditor.csv.WahapediaCsvBuilder;
import com.developerguilliman.cardEditor.csv.WahapediaCsvFactions;
import com.developerguilliman.cardEditor.data.CardCollectionData;
import com.developerguilliman.cardEditor.data.Faction;
import com.developerguilliman.cardEditor.input.WahapediaAbilities;
import com.developerguilliman.cardEditor.input.WahapediaMiscCardBuilder;
import com.developerguilliman.cardEditor.input.WahapediaPsychicPowers;
import com.developerguilliman.cardEditor.input.WahapediaStratagems;
import com.developerguilliman.cardEditor.input.WahapediaWarlordTraits;

/**
 *
 * @author Developer Guilliman <developerguilliman@gmail.com>
 */
public class WahapediaFactions extends javax.swing.JDialog {

	private static final long serialVersionUID = 6760128213570554529L;

	private LinkedHashMap<String, Faction> factions;

	/**
	 * Creates new form WahapediaFactions
	 * 
	 * @param parent
	 */
	public WahapediaFactions(MainWindow parent) {
		super(parent, true);
		initComponents();
		java.awt.EventQueue.invokeLater(() -> {
			updateFactions();
		});
	}

	public void updateFactions() {
		Callable<List<String>> callable = new Callable<List<String>>() {

			@Override
			public List<String> call() throws Exception {
				factions = new WahapediaCsvFactions().build(WahapediaCsvFactions.getInputStreamFromUrl()).getData();
				
				String[] factionNames = new String[factions.size()];
				int i = 0;
				for (Faction f : factions.values()) {
					factionNames[i++] = f.getFullName();
				}
				factionList.setListData(factionNames);
				return null;
			}
		};
		WaitingDialog.show((Frame) getParent(), "Loading factions...", callable);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		buttonsPanel = new javax.swing.JPanel();
		filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0),
				new java.awt.Dimension(32767, 0));
		jPanel1 = new javax.swing.JPanel();
		selectedCardsLabel = new javax.swing.JLabel();
		importButton = new javax.swing.JButton();
		cancelButton = new javax.swing.JButton();
		dataPanel = new javax.swing.JPanel();
		jPanel2 = new javax.swing.JPanel();
		selectAllButton = new javax.swing.JButton();
		selectNoneButton = new javax.swing.JButton();
		jPanel3 = new javax.swing.JPanel();
		stratagemCheckBox = new javax.swing.JCheckBox();
		psychicPowersCheckBox = new javax.swing.JCheckBox();
		warlordTraitsCheckBox = new javax.swing.JCheckBox();
		factionAbilitiesCheckBox = new javax.swing.JCheckBox();
		miscCheckBox = new javax.swing.JCheckBox();
		filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0),
				new java.awt.Dimension(0, 32767));
		jPanel4 = new javax.swing.JPanel();
		regroupLabel = new javax.swing.JLabel();
		regroupSpinner = new javax.swing.JSpinner();
		reorderCheckBox = new javax.swing.JCheckBox();
		deduplicateCheckBox = new javax.swing.JCheckBox();
		factionScrollPane = new javax.swing.JScrollPane();
		factionList = new javax.swing.JList<>();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setMinimumSize(new java.awt.Dimension(500, 480));
		setModal(true);

		buttonsPanel.setLayout(new javax.swing.BoxLayout(buttonsPanel, javax.swing.BoxLayout.LINE_AXIS));
		buttonsPanel.add(filler1);

		jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 2, 0, 2));
		jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));
		jPanel1.add(selectedCardsLabel);

		buttonsPanel.add(jPanel1);

		importButton.setText("Import selected");
		importButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				importButtonActionPerformed(evt);
			}
		});
		buttonsPanel.add(importButton);

		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelButtonActionPerformed(evt);
			}
		});
		buttonsPanel.add(cancelButton);

		getContentPane().add(buttonsPanel, java.awt.BorderLayout.SOUTH);

		dataPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
		dataPanel.setLayout(new java.awt.BorderLayout());

		jPanel2.setLayout(new java.awt.GridLayout(0, 2));

		selectAllButton.setText("All");
		selectAllButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				selectAllButtonActionPerformed(evt);
			}
		});
		jPanel2.add(selectAllButton);

		selectNoneButton.setText("None");
		selectNoneButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				selectNoneButtonActionPerformed(evt);
			}
		});
		jPanel2.add(selectNoneButton);

		dataPanel.add(jPanel2, java.awt.BorderLayout.NORTH);

		jPanel3.setLayout(new java.awt.GridLayout(0, 1));

		stratagemCheckBox.setText("Stratagems");
		jPanel3.add(stratagemCheckBox);

		psychicPowersCheckBox.setText("Psychic Powers");
		jPanel3.add(psychicPowersCheckBox);

		warlordTraitsCheckBox.setText("Warlord Traits");
		jPanel3.add(warlordTraitsCheckBox);

		factionAbilitiesCheckBox.setText("Faction Abilities");
		jPanel3.add(factionAbilitiesCheckBox);

		miscCheckBox.setText("Misc (slow)");
		jPanel3.add(miscCheckBox);
		jPanel3.add(filler2);

		dataPanel.add(jPanel3, java.awt.BorderLayout.CENTER);

		jPanel4.setLayout(new java.awt.GridLayout(0, 1));

		regroupLabel.setText("Regroup sections with less than:");
		jPanel4.add(regroupLabel);

		regroupSpinner.setModel(new javax.swing.SpinnerNumberModel(2, 0, null, 1));
		jPanel4.add(regroupSpinner);

		reorderCheckBox.setText("Reorder by name");
		jPanel4.add(reorderCheckBox);

		deduplicateCheckBox.setText("Remove duplicates");
		jPanel4.add(deduplicateCheckBox);

		dataPanel.add(jPanel4, java.awt.BorderLayout.SOUTH);

		getContentPane().add(dataPanel, java.awt.BorderLayout.EAST);

		factionScrollPane.setPreferredSize(new java.awt.Dimension(300, 352));
		factionScrollPane.setViewportView(factionList);

		getContentPane().add(factionScrollPane, java.awt.BorderLayout.CENTER);

		setBounds(0, 0, 706, 478);
	}// </editor-fold>//GEN-END:initComponents

	private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_importButtonActionPerformed

		LinkedHashMap<String, Faction> selectedFactions = getSelectedFactions();

		if (selectedFactions == null || selectedFactions.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Select at least one faction", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		boolean stratagems = stratagemCheckBox.isSelected();
		boolean psychicPowers = psychicPowersCheckBox.isSelected();
		boolean warlordTraits = warlordTraitsCheckBox.isSelected();
		boolean factionAbilities = factionAbilitiesCheckBox.isSelected();
		boolean misc = miscCheckBox.isSelected();

		if (!stratagems && !psychicPowers && !warlordTraits && !factionAbilities && !misc) {
			JOptionPane.showMessageDialog(this, "Select at least one card type", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		MainWindow mainWindow = (MainWindow) getParent();

		WaitingDialog.Handler callable = (label) -> {
			CardCollectionData newCards = new CardCollectionData();

			int regroup = (int) regroupSpinner.getModel().getValue() - 1;
			boolean reorderByName = reorderCheckBox.isSelected();
			boolean deduplicate = deduplicateCheckBox.isSelected();

			if (stratagems) {
				label.accept("Loading Stratagems...");
				newCards.addAll(new WahapediaStratagems(selectedFactions, regroup, reorderByName, deduplicate)
						.build(WahapediaStratagems.getInputStreamFromUrl()));
			}
			if (psychicPowers) {
				label.accept("Loading Psychic Powers...");
				newCards.addAll(new WahapediaPsychicPowers(selectedFactions, regroup, reorderByName, deduplicate)
						.build(WahapediaPsychicPowers.getInputStreamFromUrl()));
			}
			if (warlordTraits) {
				label.accept("Loading Warlord Traits...");
				newCards.addAll(new WahapediaWarlordTraits(selectedFactions, regroup, reorderByName, deduplicate)
						.build(WahapediaWarlordTraits.getInputStreamFromUrl()));
			}
			if (factionAbilities) {
				label.accept("Loading Abilities...");
				newCards.addAll(new WahapediaAbilities(selectedFactions, regroup, reorderByName, deduplicate)
						.build(WahapediaAbilities.getInputStreamFromUrl()));
			}
			if (misc) {
				label.accept("Loading Misc...");
				for (Faction faction : selectedFactions.values()) {
					newCards.addAll(new WahapediaMiscCardBuilder(regroup, reorderByName, deduplicate)
							.build(WahapediaCsvBuilder.getInputStreamFromUrl(faction.getLink().concat("/"))));
				}
			}
			dispose();
			java.awt.EventQueue.invokeLater(() -> {
				new CardImportDialog(mainWindow, newCards).setVisible(true);

			});
			return null;
		};
		WaitingDialog.show(mainWindow, "Loading...", callable);
	}// GEN-LAST:event_importButtonActionPerformed

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelButtonActionPerformed
		dispose();
	}// GEN-LAST:event_cancelButtonActionPerformed

	private void selectAllButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_selectAllButtonActionPerformed
		stratagemCheckBox.setSelected(true);
		psychicPowersCheckBox.setSelected(true);
		warlordTraitsCheckBox.setSelected(true);
		factionAbilitiesCheckBox.setSelected(true);
		miscCheckBox.setSelected(true);
	}// GEN-LAST:event_selectAllButtonActionPerformed

	private void selectNoneButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_selectNoneButtonActionPerformed
		stratagemCheckBox.setSelected(false);
		psychicPowersCheckBox.setSelected(false);
		warlordTraitsCheckBox.setSelected(false);
		factionAbilitiesCheckBox.setSelected(false);
		miscCheckBox.setSelected(false);
	}// GEN-LAST:event_selectNoneButtonActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel buttonsPanel;
	private javax.swing.JButton cancelButton;
	private javax.swing.JPanel dataPanel;
	private javax.swing.JCheckBox deduplicateCheckBox;
	private javax.swing.JCheckBox factionAbilitiesCheckBox;
	private javax.swing.JList<String> factionList;
	private javax.swing.JScrollPane factionScrollPane;
	private javax.swing.Box.Filler filler1;
	private javax.swing.Box.Filler filler2;
	private javax.swing.JButton importButton;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JCheckBox miscCheckBox;
	private javax.swing.JCheckBox psychicPowersCheckBox;
	private javax.swing.JLabel regroupLabel;
	private javax.swing.JSpinner regroupSpinner;
	private javax.swing.JCheckBox reorderCheckBox;
	private javax.swing.JButton selectAllButton;
	private javax.swing.JButton selectNoneButton;
	private javax.swing.JLabel selectedCardsLabel;
	private javax.swing.JCheckBox stratagemCheckBox;
	private javax.swing.JCheckBox warlordTraitsCheckBox;
	// End of variables declaration//GEN-END:variables

	private LinkedHashMap<String, Faction> getSelectedFactions() {
		LinkedHashMap<String, Faction> selected = new LinkedHashMap<>();
		int[] selectedIndices = factionList.getSelectedIndices();
		int i = 0;
		for (Faction faction : factions.values()) {
			for (int index : selectedIndices) {
				if (index == i) {
					selected.put(faction.getId(), faction);
					break;
				}
			}
			i++;
		}
		return selected;
	}

}
