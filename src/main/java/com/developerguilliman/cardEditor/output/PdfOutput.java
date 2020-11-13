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
package com.developerguilliman.cardEditor.output;

import com.developerguilliman.cardEditor.CardHash;
import com.developerguilliman.cardEditor.Utils;
import com.developerguilliman.cardEditor.data.CardCollectionData;
import com.developerguilliman.cardEditor.data.CardData;
import com.developerguilliman.cardEditor.data.SectionData;
import com.developerguilliman.cardEditor.warning.IWarningHandler;
import com.developerguilliman.cardEditor.warning.WarningConsoleOut;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Iterator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

/**
 *
 * @author Developer Guilliman <developerguilliman@gmail.com>
 */
public class PdfOutput implements ICardOutput {

    private static final String TAB_SPACES = "    ";
    private static final float LEADING_FACTOR = 1.125f;
    private static final float LEADING_INTERTEXT_FACTOR = 0.25f;
    private static final float MIN_Y_FONT_FACTOR = 1.25f;
    private static final Color VERY_LIGHT_GRAY = new Color(0xe7, 0xe7, 0xe7);
    private static final float EXTRA_GRID = 5;

    public static final PDRectangle DEFAULT_PAGE_SIZE = PDRectangle.A4;

    public static final int DEFAULT_CARDS_PER_X = 3;
    public static final int DEFAULT_CARDS_PER_Y = 3;

    public static final float DEFAULT_MARGIN_X = 10;
    public static final float DEFAULT_MARGIN_Y = 11.15f;

    public static final float DEFAULT_TITLE_FONT_SIZE = 9;
    public static final float DEFAULT_NAME_FONT_SIZE = 11;
    public static final float DEFAULT_LEGEND_FONT_SIZE = 7;
    public static final float DEFAULT_RULES_FONT_SIZE = 7;
    public static final float DEFAULT_COST_TYPE_FONT_SIZE = 8;

    public static final PDType1Font DEFAULT_TITLE_FONT_TYPE = PDType1Font.HELVETICA_BOLD;
    public static final PDType1Font DEFAULT_NAME_FONT_TYPE = PDType1Font.HELVETICA_BOLD;
    public static final PDType1Font DEFAULT_LEGEND_FONT_TYPE = PDType1Font.TIMES_ITALIC;
    public static final PDType1Font DEFAULT_RULES_FONT_TYPE = PDType1Font.TIMES_ROMAN;
    public static final PDType1Font DEFAULT_COST_VALUE_FONT_TYPE = PDType1Font.HELVETICA_BOLD;
    public static final PDType1Font DEFAULT_COST_TYPE_FONT_TYPE = PDType1Font.HELVETICA_BOLD;

    public static final Color DEFAULT_TITLE_FONT_COLOR = Color.BLACK;
    public static final Color DEFAULT_NAME_FONT_COLOR = Color.BLACK;
    public static final Color DEFAULT_LEGEND_FONT_COLOR = Color.BLACK;
    public static final Color DEFAULT_RULES_FONT_COLOR = Color.BLACK;
    public static final Color DEFAULT_COST_TYPE_FONT_COLOR = Color.BLACK;

    public static final Color DEFAULT_COST_TYPE_FILL_COLOR = null;
    public static final Color DEFAULT_FOREGROUND_GRID_COLOR = VERY_LIGHT_GRAY;
    public static final Color DEFAULT_BACKGROUND_GRID_COLOR = null;

    public static final boolean DEFAULT_BACKGROUND_PAGES = false;
    public static final boolean DEFAULT_FILL_UNUSED_CARD_SLOTS = false;
    public static final boolean DEFAULT_FILL_UNUSED_CARD_SLOTS_COST = false;
    public static final boolean DEFAULT_FILL_UNUSED_CARD_SLOTS_TITLES = false;

    private final PDRectangle pageSize;

    private final int perX;
    private final int perY;

    private final float marginPercentX;
    private final float marginPercentY;

    private final FontData titleFont;
    private final FontData nameFont;
    private final FontData legendFont;
    private final FontData rulesFont;
    private final FontData costValueFont;
    private final FontData costTypeFont;
    private final FontData hashFont = new FontData(PDType1Font.HELVETICA, 6, new Color(160, 160, 160));

    private final Color cardBackgroundColor;
    private final Color titleBarsColor;
    private final Color upperBarColor;
    private final Color lowerBarColor;
    private final Color cardBordersColor;
    private final Color costBordersColor;
    private final Color cardFillColor;
    private final Color costValueFillColor;
    private final Color costTypeFillColor;
    private final Color foregroundGridColor;
    private final Color backgroundGridColor;
    private final boolean backgroundPages;
    private final boolean fillUnusedCardSlots;
    private final boolean fillUnusedCardSlotsTitles;
    private final boolean fillUnusedCardSlotsBorders;

    private final CardHash cardHash;
    private final StringBuilder printedTextBuffer;

    public PdfOutput(DefaultPreset preset) {

        this.pageSize = DEFAULT_PAGE_SIZE;

        this.perX = DEFAULT_CARDS_PER_X;
        this.perY = DEFAULT_CARDS_PER_Y;

        this.marginPercentX = DEFAULT_MARGIN_X;
        this.marginPercentY = DEFAULT_MARGIN_Y;

        this.titleFont = new FontData(DEFAULT_TITLE_FONT_TYPE, DEFAULT_TITLE_FONT_SIZE, DEFAULT_TITLE_FONT_COLOR);
        this.nameFont = new FontData(DEFAULT_NAME_FONT_TYPE, DEFAULT_NAME_FONT_SIZE, DEFAULT_NAME_FONT_COLOR);
        this.legendFont = new FontData(DEFAULT_LEGEND_FONT_TYPE, DEFAULT_LEGEND_FONT_SIZE, DEFAULT_LEGEND_FONT_COLOR);
        this.rulesFont = new FontData(DEFAULT_RULES_FONT_TYPE, DEFAULT_RULES_FONT_SIZE, DEFAULT_RULES_FONT_COLOR);
        this.costValueFont = new FontData(DEFAULT_COST_VALUE_FONT_TYPE, preset.getCostValueFontSize(), preset.getCostValueFontColor());
        this.costTypeFont = new FontData(DEFAULT_COST_TYPE_FONT_TYPE, DEFAULT_COST_TYPE_FONT_SIZE, DEFAULT_COST_TYPE_FONT_COLOR);

        this.cardBackgroundColor = preset.getCardBackgroundColor();
        this.titleBarsColor = preset.getTitleBarsColor();
        this.upperBarColor = preset.getUpperBarColor();
        this.lowerBarColor = preset.getLowerBarColor();

        this.cardBordersColor = preset.getCardBorderColor();
        this.costBordersColor = preset.getCostBordersColor();
        this.cardFillColor = preset.getCardFillColor();
        this.costValueFillColor = preset.getCostValueFillColor();
        this.costTypeFillColor = DEFAULT_COST_TYPE_FILL_COLOR;
        this.foregroundGridColor = DEFAULT_FOREGROUND_GRID_COLOR;
        this.backgroundGridColor = DEFAULT_BACKGROUND_GRID_COLOR;

        this.backgroundPages = DEFAULT_BACKGROUND_PAGES;
        this.fillUnusedCardSlots = DEFAULT_FILL_UNUSED_CARD_SLOTS;
        this.fillUnusedCardSlotsBorders = DEFAULT_FILL_UNUSED_CARD_SLOTS_COST;
        this.fillUnusedCardSlotsTitles = DEFAULT_FILL_UNUSED_CARD_SLOTS_TITLES;

        this.cardHash = new CardHash();
        this.printedTextBuffer = new StringBuilder();
    }

    private PdfOutput(PDRectangle pageSize, int perX, int perY, float marginPercentX, float marginPercentY,
            FontData titleFont, FontData nameFont, FontData legendFont, FontData rulesFont, FontData costValueFont, FontData costTypeFont,
            Color cardBackgroundColor, Color titleBarsColor, Color upperBarColor, Color lowerBarColor,
            Color cardBordersColor, Color costBordersColor,
            Color cardFillColor, Color costValueFillColor, Color costTypeFillColor,
            Color foregroundGridColor, Color backgroundGridColor,
            boolean backgroundPages, boolean fillUnusedCardSlots, boolean fillUnusedCardSlotsBorders, boolean fillUnusedCardSlotsTitles) {

        this.pageSize = pageSize;
        this.perX = perX;
        this.perY = perY;

        this.marginPercentX = marginPercentX;
        this.marginPercentY = marginPercentY;

        this.titleFont = titleFont;
        this.nameFont = nameFont;
        this.legendFont = legendFont;
        this.rulesFont = rulesFont;
        this.costValueFont = costValueFont;
        this.costTypeFont = costTypeFont;

        this.cardBackgroundColor = cardBackgroundColor;
        this.titleBarsColor = titleBarsColor;
        this.upperBarColor = upperBarColor;
        this.lowerBarColor = lowerBarColor;
        this.cardBordersColor = cardBordersColor;
        this.costBordersColor = costBordersColor;
        this.cardFillColor = cardFillColor;
        this.costValueFillColor = costValueFillColor;
        this.costTypeFillColor = costTypeFillColor;

        this.foregroundGridColor = foregroundGridColor;
        this.backgroundGridColor = backgroundGridColor;

        this.backgroundPages = backgroundPages;
        this.fillUnusedCardSlots = fillUnusedCardSlots;
        this.fillUnusedCardSlotsBorders = fillUnusedCardSlotsBorders;
        this.fillUnusedCardSlotsTitles = fillUnusedCardSlotsTitles;

        this.cardHash = new CardHash();
        this.printedTextBuffer = new StringBuilder();
    }

    @Override
    public void build(OutputStream out, CardCollectionData cards, IWarningHandler warningHandler) throws IOException {

        try ( PDDocument document = new PDDocument()) {
            buildDocument(document, cards, warningHandler);
            document.save(out);

        }

    }

    public void build(OutputStream out, CardCollectionData cards) throws IOException {
        build(out, cards, new WarningConsoleOut());
    }

    private void buildDocument(PDDocument document, CardCollectionData cards, IWarningHandler warningHandler) throws IOException {

        Calendar now = Calendar.getInstance();
        PDDocumentInformation info = new PDDocumentInformation();
        info.setCreator("Card Editor 0.2.5");
        info.setProducer("https://github.com/DeveloperGuilliman/CardEditor");
        info.setCreationDate(now);
        info.setModificationDate(now);
        document.setDocumentInformation(info);

        document.getDocumentCatalog().setDocumentOutline(new PDDocumentOutline());

        for (SectionData sections : cards) {
            boolean first = true;
            Iterator<CardData> cardIterator = sections.iterator();
            while (cardIterator.hasNext()) {
                int printedCards = buildForegroundPage(document, sections, cardIterator, first, warningHandler);
                first = false;
                if (backgroundPages) {
                    buildBackgroundPage(document, printedCards);
                }
            }
        }

    }

    private int buildForegroundPage(PDDocument document, SectionData section, Iterator<CardData> cardIterator, boolean first, IWarningHandler warningHandler) throws IOException {
        PDPage page = new PDPage(pageSize);
        document.addPage(page);
        if (first) {
            String sectionTitle = section.getName();
            PDOutlineItem outlineItem = new PDOutlineItem();
            outlineItem.setDestination(page);
            outlineItem.setTitle(sectionTitle.isEmpty() ? "Various cards" : sectionTitle);
            document.getDocumentCatalog().getDocumentOutline().addLast(outlineItem);
        }
        try ( PDPageContentStream cs = new PDPageContentStream(document, page)) {

            final float pageWidth = page.getBBox().getWidth();
            final float pageHeight = page.getBBox().getHeight();

            final float printableWidth = pageWidth * (100 - marginPercentX) / 100;
            final float printableHeight = pageHeight * (100 - marginPercentY) / 100;

            final float marginX = (pageWidth - printableWidth) / 2;
            final float marginY = (pageHeight - printableHeight) / 2;

            final float cardWidth = printableWidth / perX;
            final float cardHeight = printableHeight / perY;

            int printedCards = buildCardsContentPage(cs, cardIterator, marginX, marginY, cardWidth, cardHeight, document.getPages().getCount(), warningHandler);
            buildPageGrid(cs, marginX, marginY, printableWidth, printableHeight, cardWidth, cardHeight, foregroundGridColor);
            return printedCards;

        }
    }

    private void buildBackgroundPage(PDDocument document, int printedCards) throws IOException {
        PDPage page = new PDPage(pageSize);
        document.addPage(page);
        try ( PDPageContentStream cs = new PDPageContentStream(document, page)) {
            final float pageWidth = page.getBBox().getWidth();
            final float pageHeight = page.getBBox().getHeight();

            final float printableWidth = pageWidth * (100 - marginPercentX) / 100;
            final float printableHeight = pageHeight * (100 - marginPercentY) / 100;

            final float marginX = (pageWidth - printableWidth) / 2;
            final float marginY = (pageHeight - printableHeight) / 2;

            final float cardWidth = printableWidth / perX;
            final float cardHeight = printableHeight / perY;

            buildCardsBackgroundPage(cs, printedCards, marginX, marginY, cardWidth, cardHeight);
            buildPageGrid(cs, marginX, marginY, printableWidth, printableHeight, cardWidth, cardHeight, backgroundGridColor);
        }
    }

    private int buildCardsContentPage(PDPageContentStream cs, Iterator<CardData> cardIterator, float marginX, float marginY, float cardWidth, float cardHeight, int pageIndex, IWarningHandler warningHandler) throws IOException {

        int printedCards = 0;

        // Up to Down
        for (int numY = perY - 1; numY >= 0; numY--) {
            // Left to Right
            for (int numX = 0; numX < perX; numX++) {
                float x = cardWidth * numX + marginX;
                float y = cardHeight * numY + marginY;
                if (!cardIterator.hasNext()) {
                    if (fillUnusedCardSlots) {
                        printCardEmptyForeground(cs, x, y, cardWidth, cardHeight, printedCards, pageIndex, warningHandler);
                    } else {
                        return printedCards;
                    }
                } else {
                    CardData card = cardIterator.next();
                    printedCards++;
                    String hash = printCardForeground(cs, card, x, y, cardWidth, cardHeight, printedCards, pageIndex, warningHandler);
                    printCardHash(cs, hash, x, y, cardWidth);
                }
            }
        }
        return printedCards;
    }

    private void buildCardsBackgroundPage(PDPageContentStream cs, int printedCards,
            float marginX, float marginY, float cardWidth, float cardHeight) throws IOException {

        // Up to Down
        for (int numY = perY - 1; numY >= 0; numY--) {
            // Left to Right
            for (int numX = 0; numX < perX; numX++) {
                float x = cardWidth * numX + marginX;
                float y = cardHeight * numY + marginY;
                if (fillUnusedCardSlots || printedCards > 0) {
                    printCardBackground(cs, x, y, cardWidth, cardHeight);
                    printedCards--;
                } else {
                    return;
                }
            }
        }
    }

    private void buildPageGrid(PDPageContentStream cs, float marginX, float marginY, float printableWidth,
            float printableHeight, float width, float height, Color gridColor) throws IOException {

        if (gridColor == null) {
            return;
        }
        cs.setStrokingColor(gridColor);

        // Up to Down
        for (int y = perY; y >= 0; y--) {
            cs.moveTo(marginX - EXTRA_GRID, height * y + marginY);
            cs.lineTo(marginX + printableWidth + EXTRA_GRID, height * y + marginY);
            cs.stroke();
        }
        // Left to Right
        for (int x = 0; x <= perX; x++) {
            cs.moveTo(width * x + marginX, marginY - EXTRA_GRID);
            cs.lineTo(width * x + marginX, marginY + printableHeight + EXTRA_GRID);
            cs.stroke();
        }
    }

    private void printCardEmptyForeground(PDPageContentStream cs, float x, float y, float width, float height, int cardIndex, int pageIndex, IWarningHandler warningHandler) throws IOException {

        if (cardBackgroundColor != null) {
            cs.setNonStrokingColor(cardBackgroundColor);
            cs.addRect(x, y, width, height);
            cs.fill();
        }

        boolean marginForCardBorders = (cardBordersColor != null || cardFillColor != null);
        float bordersMargin = Math.min(width, height);
        bordersMargin *= marginForCardBorders ? 0.04f : 0.02f;
        width -= 2 * bordersMargin;
        height -= 2 * bordersMargin;
        x += bordersMargin;
        y += bordersMargin;

        if (marginForCardBorders) {
            float blankSpace = Math.min(width, height) * 0.1f;
            drawCardPoligon(cs, x, y, width, height, blankSpace, cardBordersColor, cardFillColor);
        }
        if (fillUnusedCardSlotsBorders) {
            float minY;
            float costValueBottomY;
            float costTypeBottomY;
            float poligon6Width;
            float poligon8Side;
            float costValueX;
            float costTypeX;
            if (costBordersColor == null) {
                float maxFont = Math.max(costValueFont.size, costTypeFont.size) * MIN_Y_FONT_FACTOR;
                minY = y + maxFont;
                costValueBottomY = costTypeBottomY = minY + 2 - maxFont;
                costValueBottomY += costValueFont.getHeight() + ((maxFont - costValueFont.size) / 2);
                costTypeBottomY += costTypeFont.getHeight() + ((maxFont - costTypeFont.size) / 2);
            } else {
                float poligon6Height = Math.max(costValueFont.size, costTypeFont.size * 1.5f);
                poligon6Width = width * 0.5f;
                float poligon6Min = Math.min(poligon6Width, poligon6Height);

                float poligon6BlankSpace = poligon6Min * 0.1f;
                float poligon8ExtraSide = poligon6Min * 0.25f;
                poligon8Side = poligon6Height + 2 * poligon8ExtraSide;
                minY = y + 4;
                costValueBottomY = costTypeBottomY = minY;
                float costZoneMarginX = (width - poligon6Width - poligon8Side) / 2;

                if (poligon6Width + poligon8Side > width) {
                    warningHandler.warn("Cost borders too wide in card " + cardIndex + ", page:" + pageIndex + ".");
                }
                costValueX = costTypeX = x + costZoneMarginX;

                minY += poligon8Side;
                costTypeX += poligon8Side;
                costTypeBottomY += poligon8ExtraSide;

                drawCostPoligon(cs, costTypeX, costTypeBottomY, poligon6Width, poligon6Height, poligon6BlankSpace, costBordersColor, costTypeFillColor);
                drawCardPoligon(cs, costValueX, costValueBottomY, poligon8Side, poligon8Side, poligon8ExtraSide, costBordersColor, costValueFillColor);

                costValueBottomY += poligon8ExtraSide + costValueFont.getHeight() + ((poligon6Height - costValueFont.size) / 2) + 1;
                costTypeBottomY += costTypeFont.getHeight() + ((poligon6Height - costTypeFont.size) / 2) + 1;
            }
            if (lowerBarColor != null) {
                drawNameLines(cs, x, width, costValueBottomY, lowerBarColor);
            }
        }

        if (fillUnusedCardSlotsTitles) {

            float nextY = y + height;

            nextY -= titleFont.size * LEADING_FACTOR;
            nextY -= 4;

            if (upperBarColor != null) {
                nextY -= 2;
                drawNameLines(cs, x, width, nextY, upperBarColor);
            }

            if (titleBarsColor != null) {
                nextY -= 2;
                drawNameLines(cs, x, width, nextY, titleBarsColor);
            }

            nextY -= nameFont.size * LEADING_FACTOR;

            if (titleBarsColor != null) {
                drawNameLines(cs, x, width, nextY - 6, titleBarsColor);
            }
        }

    }

    private String printCardForeground(PDPageContentStream cs, CardData card, float x, float y, float width, float height, int cardIndex, int pageIndex, IWarningHandler warningHandler) throws IOException {

        String title = card.getTitle().replace("\t", TAB_SPACES).trim();
        String name = card.getName().replace("\t", TAB_SPACES).trim();
        String legend = Utils.rightTrim(card.getLegend().replace("\t", TAB_SPACES));
        String rules = Utils.rightTrim(card.getRules().replace("\t", TAB_SPACES));
        String costValue = card.getCostValue().replace("\t", TAB_SPACES).trim();
        String costType = card.getCostType().replace("\t", TAB_SPACES).trim();

        printedTextBuffer.setLength(0);

//        if (foregroundImage != null) {
//            cs.drawImage(foregroundImage, x, y, width, height);
//        }
        if (cardBackgroundColor != null) {
            cs.setNonStrokingColor(cardBackgroundColor);
            cs.addRect(x, y, width, height);
            cs.fill();
        }

        final float titleTextWidth;
        final float normalTextWidth;
        boolean marginForCardBorders = (cardBordersColor != null || cardFillColor != null);
        float bordersMargin = Math.min(width, height);
        bordersMargin *= marginForCardBorders ? 0.04f : 0.025f;
        width -= 2 * bordersMargin;
        height -= 2 * bordersMargin;
        x += bordersMargin;
        y += bordersMargin;

        if (cardBordersColor != null || cardFillColor != null) {
            float blankSpace = Math.min(width, height) * 0.1f;
            drawCardPoligon(cs, x, y, width, height, blankSpace, cardBordersColor, cardFillColor);
            titleTextWidth = width * 0.85f;
            normalTextWidth = width * 0.9f;
        } else {
            titleTextWidth = width * 0.9f;
            normalTextWidth = width * 0.9f;
            bordersMargin = 0;
        }

        final float normalTextMarginX = (width - normalTextWidth) / 2;

        final float titleTextMarginX = (width - titleTextWidth) / 2;

        float minY;
        float costValueBottomY = 0;
        float costTypeBottomY = 0;
        float poligon6Width = 0;
        float poligon8Side = 0;
        float costValueX = 0;
        float costTypeX = 0;

        if (costValue.isEmpty() && costType.isEmpty()) {
            minY = y + bordersMargin;
        } else if (costBordersColor == null) {
            float maxFont = Math.max(costValueFont.size, costTypeFont.size) * MIN_Y_FONT_FACTOR;
            minY = y + maxFont;
            poligon8Side = costValueFont.getTextSize(costValue.concat(" "));
            poligon6Width = costTypeFont.getTextSize(" ".concat(costType));
            float sumWidth = poligon8Side + poligon6Width;
            costValueX = x + ((width - sumWidth) / 2);
            costTypeX = costValueX + poligon8Side;
            costValueBottomY = costTypeBottomY = minY + 2 - maxFont;
            costValueBottomY += costValueFont.getHeight() + ((maxFont - costValueFont.size) / 2);
            costTypeBottomY += costTypeFont.getHeight() + ((maxFont - costTypeFont.size) / 2);
            if (lowerBarColor != null) {
                minY += 2;
                drawNameLines(cs, x + titleTextMarginX, titleTextWidth, minY, lowerBarColor);
            }
        } else {
            float poligon6Height = Math.max(costValueFont.size, costTypeFont.size * 1.5f);
            poligon6Width = width * 0.5f;
            poligon6Width = (!costType.isEmpty()) ? Math.max(poligon6Width, 1.01f * costTypeFont.getTextSize(costType)) : poligon6Width;
            float poligon6Min = Math.min(poligon6Width, poligon6Height);

            float poligon6BlankSpace = poligon6Min * 0.1f;
            float poligon8ExtraSide = poligon6Min * 0.25f;
            poligon8Side = poligon6Height + 2 * poligon8ExtraSide;
            minY = y + 4;
            costValueBottomY = costTypeBottomY = minY;
            float costZoneMarginX = (width - poligon6Width - poligon8Side) / 2;

            if (poligon6Width + poligon8Side > width) {
                warningHandler.warn("Cost borders too wide in card " + cardIndex + ", page:" + pageIndex + ".");
            }
            costValueX = costTypeX = x + costZoneMarginX;

            if (!costValue.isEmpty()) {
                minY += poligon8Side;
                if (!costType.isEmpty()) {
                    costTypeX += poligon8Side;
                    costTypeBottomY += poligon8ExtraSide;

                    drawCostPoligon(cs, costTypeX, costTypeBottomY, poligon6Width, poligon6Height, poligon6BlankSpace, costBordersColor, costTypeFillColor);
                    drawCardPoligon(cs, costValueX, costValueBottomY, poligon8Side, poligon8Side, poligon8ExtraSide, costBordersColor, costValueFillColor);

                } else {
                    drawCardPoligon(cs, costTypeX, costValueBottomY, poligon8Side, poligon8Side, poligon8ExtraSide, costBordersColor, costValueFillColor);
                }
                if (lowerBarColor != null) {
                    minY += 2;
                    drawNameLines(cs, x + titleTextMarginX, titleTextWidth, minY, lowerBarColor);
                }
            } else if (!costType.isEmpty()) {
                minY += poligon6Height + poligon8ExtraSide;
                poligon6Width += poligon8Side;
                costTypeBottomY += poligon8ExtraSide;
                drawCardPoligon(cs, costValueX, costTypeBottomY, poligon6Width, poligon6Height, poligon6BlankSpace, costBordersColor, costTypeFillColor);
            }
            costValueBottomY += poligon8ExtraSide + costValueFont.getHeight() + ((poligon6Height - costValueFont.size) / 2) + 1;
            costTypeBottomY += costTypeFont.getHeight() + ((poligon6Height - costTypeFont.size) / 2) + 1;
            if (lowerBarColor != null) {
                minY += 2;
                drawNameLines(cs, x + titleTextMarginX, titleTextWidth, minY, lowerBarColor);
            }
        }

        float nextY = y + height;

        // DEBUG: Uncomment the following lines to print max card text available area
        //cs.setStrokingColor(Color.RED);
        //cs.drawLine(x, minY, x + width, minY);
        nextY -= printBreakableCenteredText(cs, title, x + titleTextMarginX, nextY, titleTextWidth, titleFont, printedTextBuffer, cardIndex, pageIndex, warningHandler);
        nextY -= 4;

        if (upperBarColor != null) {
            drawNameLines(cs, x + titleTextMarginX, titleTextWidth, nextY, upperBarColor);
            nextY -= 2;
        }

        if (!name.isEmpty()) {

            if (titleBarsColor != null) {
                nextY -= 2;
                drawNameLines(cs, x + normalTextMarginX, normalTextWidth, nextY, titleBarsColor);
            }

            nextY -= printBreakableCenteredText(cs, name, x + normalTextMarginX, nextY, normalTextWidth, nameFont, printedTextBuffer, cardIndex, pageIndex, warningHandler);

            if (titleBarsColor != null) {
                drawNameLines(cs, x + normalTextMarginX, normalTextWidth, nextY - 6, titleBarsColor);
                nextY -= 4;
            }
        }
        nextY -= 6;

        nextY -= printBreakingText(legend, x + normalTextMarginX, nextY, normalTextWidth, nextY - minY, legendFont, cs, printedTextBuffer, cardIndex, pageIndex, warningHandler);

        nextY -= printBreakingText(rules, x + normalTextMarginX, nextY, normalTextWidth, nextY - minY, rulesFont, cs, printedTextBuffer, cardIndex, pageIndex, warningHandler);

        if (!costValue.isEmpty()) {
            printCenteredText(cs, costValue, costValueX, costValueBottomY, poligon8Side, costValueFont, printedTextBuffer, cardIndex, pageIndex, warningHandler);
        }
        if (!costType.isEmpty()) {
            printCenteredText(cs, costType, costTypeX, costTypeBottomY, poligon6Width, costTypeFont, printedTextBuffer, cardIndex, pageIndex, warningHandler);
        }

        return cardHash.getStringsHash(printedTextBuffer.toString());

    }

    private void printCardHash(PDPageContentStream cs, String hash, float x, float y, float width) throws IOException {
        float margin = hashFont.size * 0.5f;
        printRightText(cs, hash, x + margin, y + margin, width - 2 * margin, hashFont, printedTextBuffer);
    }

    private static void drawNameLines(PDPageContentStream cs, float x, float width, float y, Color color) throws IOException {

        cs.setStrokingColor(color);
        cs.moveTo(x, y);
        cs.lineTo(x + width, y);
        cs.stroke();
    }

    private void printCardBackground(PDPageContentStream cs, float x, float y, float width, float height) throws IOException {

        if (cardBackgroundColor != null) {
            cs.setNonStrokingColor(cardBackgroundColor);
            cs.addRect(x, y, width, height);
            cs.fill();
        }

        if (cardBordersColor != null || cardFillColor != null) {
            float margin = Math.min(width, height) * 0.04f;
            width -= 2 * margin;
            height -= 2 * margin;
            x += margin;
            y += margin;
            float blankSpace = Math.min(width, height) * 0.1f;
            drawCardPoligon(cs, x, y, width, height, blankSpace, cardBordersColor, cardFillColor);
        }
    }

    private static void drawCardPoligon(PDPageContentStream cs, float x, float y, float width, float height, float blankSpace,
            Color outerColor, Color fillColor) throws IOException {

        float x0 = x;
        float x1 = x0 + blankSpace;
        float x2 = x0 + width - blankSpace;
        float x3 = x0 + width;
        float y0 = y;
        float y1 = y0 + blankSpace;
        float y2 = y0 + height - blankSpace;
        float y3 = y0 + height;

        cs.moveTo(x1, y0);
        cs.lineTo(x2, y0);
        cs.lineTo(x3, y1);
        cs.lineTo(x3, y2);
        cs.lineTo(x2, y3);
        cs.lineTo(x1, y3);
        cs.lineTo(x0, y2);
        cs.lineTo(x0, y1);
        cs.closePath();

        if (outerColor != null) {
            cs.setStrokingColor(outerColor);
            if (fillColor != null) {
                cs.setNonStrokingColor(fillColor);
                cs.fillAndStroke();
            } else {
                cs.stroke();
            }
        } else if (fillColor != null) {
            cs.setNonStrokingColor(fillColor);
            cs.fill();
        }

    }

    private static void drawCostPoligon(PDPageContentStream cs, float x, float y, float width, float height, float blankSpace,
            Color outerColor, Color fillColor) throws IOException {

        float x0 = x;
        float x2 = x0 + width - blankSpace;
        float x3 = x0 + width;
        float y0 = y;
        float y1 = y0 + blankSpace;
        float y2 = y0 + height - blankSpace;
        float y3 = y0 + height;

        cs.moveTo(x0, y0);
        cs.lineTo(x2, y0);
        cs.lineTo(x3, y1);
        cs.lineTo(x3, y2);
        cs.lineTo(x2, y3);
        cs.lineTo(x0, y3);

        if (outerColor != null) {
            cs.setStrokingColor(outerColor);
            if (fillColor != null) {
                cs.setNonStrokingColor(fillColor);
                cs.fillAndStroke();
            } else {
                cs.stroke();
            }
        } else if (fillColor != null) {
            cs.setNonStrokingColor(fillColor);
            cs.fill();
        }
    }

    private static float printBreakableCenteredText(PDPageContentStream cs, String text, float x, float y,
            float maxWidth, FontData font, StringBuilder printedTextBuffer,
            int cardIndex, int pageIndex, IWarningHandler warningHandler) throws IOException {

        if (text.isEmpty()) {
            return 0;
        }
        y -= font.getHeight();

        float leading = font.size * LEADING_FACTOR;

        cs.setLeading(leading);

        float size = font.getTextSize(text);

        if (size > maxWidth) {
            int center = findNearestCenterSpace(text);
            if (center > 0 && center < text.length()) {
                String pre = text.substring(0, center);
                String post = text.substring(center + 1);
                printCenteredText(cs, pre, x, y, maxWidth, font, font.getTextSize(pre), printedTextBuffer, cardIndex, pageIndex, warningHandler);
                printCenteredText(cs, post, x, y - leading, maxWidth, font, font.getTextSize(post), printedTextBuffer, cardIndex, pageIndex, warningHandler);
                return (2 * leading);
            }
        }

        printCenteredText(cs, text, x, y, maxWidth, font, size, printedTextBuffer, cardIndex, pageIndex, warningHandler);
        return leading;
    }

    private static float printCenteredText(PDPageContentStream cs, String text, float x, float y,
            float maxWidth, FontData font, StringBuilder printedTextBuffer,
            int cardIndex, int pageIndex, IWarningHandler warningHandler) throws IOException {

        if (text.isEmpty()) {
            return 0;
        }
        y -= font.getHeight();

        float leading = font.size * LEADING_FACTOR;

        cs.setLeading(leading);

        float size = font.getTextSize(text);
        printCenteredText(cs, text, x, y, maxWidth, font, size, printedTextBuffer, cardIndex, pageIndex, warningHandler);
        return leading;
    }

    private static void printCenteredText(PDPageContentStream cs, String text, float x, float y, float maxWidth, FontData font,
            float size, StringBuilder printedTextBuffer, int cardIndex, int pageIndex, IWarningHandler warningHandler) throws IOException {

        float xDisp = (maxWidth - size) / 2;
        if (xDisp < 0) {
            warningHandler.warn("Out of horizontal space in card " + cardIndex + ", page:" + pageIndex + ".");
        }
        cs.setFont(font.font, font.size);
        cs.setNonStrokingColor(font.color);
        cs.beginText();
        cs.newLineAtOffset(x + xDisp, y);
        cs.showText(text);
        printedTextBuffer.append(text);
        printedTextBuffer.append('\n');
        cs.endText();
    }

    private static void printRightText(PDPageContentStream cs, String text, float x, float y,
            float maxWidth, FontData font, StringBuilder printedTextBuffer) throws IOException {

        float xDisp = maxWidth - font.getTextSize(text);
        cs.setFont(font.font, font.size);
        cs.setNonStrokingColor(font.color);
        cs.beginText();
        cs.newLineAtOffset(x + xDisp, y);
        cs.showText(text);
        printedTextBuffer.append(text);
        printedTextBuffer.append('\n');
        cs.endText();
    }

    private static float printBreakingText(String text, float x, float y, float maxWidth, float maxHeight, FontData font, PDPageContentStream cs, StringBuilder printedTextBuffer, int cardIndex, int pageIndex, IWarningHandler warningHandler) throws IOException {

        if (text.isEmpty()) {
            return 0;
        }
        int textLen = text.length();
        int startWroteChars = printedTextBuffer.length();
        y -= font.getHeight();

        float leading = font.size * LEADING_FACTOR;
        float yDiff = leading;
        cs.setFont(font.font, font.size);
        cs.setNonStrokingColor(font.color);
        cs.setLeading(leading);
        cs.beginText();
        cs.newLineAtOffset(x, y);

        int prevNewlineIndexOf = 0;
        int newlineIndexOf = text.indexOf('\n');
        do {
            newlineIndexOf = (newlineIndexOf < 0) ? textLen : newlineIndexOf;
            if (prevNewlineIndexOf == newlineIndexOf) {
                cs.newLine();
                printedTextBuffer.append('\n');
                yDiff += leading;
            } else {
                String line = text.substring(prevNewlineIndexOf, newlineIndexOf);
                int lineLen = line.length();
                int currentLineStart = 0;
                while (currentLineStart < lineLen && yDiff < maxHeight) {
                    currentLineStart = printLine(line, currentLineStart, maxWidth, font, cs, printedTextBuffer);
                    printedTextBuffer.append('\n');
                    yDiff += leading;
                }
            }
            prevNewlineIndexOf = newlineIndexOf + 1;
            newlineIndexOf = text.indexOf('\n', prevNewlineIndexOf);
        } while (prevNewlineIndexOf <= text.length());
        if (yDiff > maxHeight) {
            warningHandler.warn("Out of vertical space in card " + cardIndex + ", page:" + pageIndex + ". Wrote only " + (printedTextBuffer.length() - startWroteChars) + " of " + textLen + " characters.");
        }
        cs.endText();
        return (yDiff + (leading * LEADING_INTERTEXT_FACTOR));
    }

    private static int printLine(String text, int currentLineStart, float maxWidth, FontData font,
            PDPageContentStream cs, StringBuilder printedTextBuffer) throws IOException {

        int len = text.length();

        int currentLineEnd = text.indexOf(' ', currentLineStart + 1);
        if (currentLineEnd < 0) {
            String currentLine = text.substring(currentLineStart);
            cs.showText(currentLine);
            cs.newLine();
            printedTextBuffer.append(currentLine);
            return len;
        }
        String currentLine = text.substring(currentLineStart, currentLineEnd);
        String nextLine;
        int nextLineEnd = 0;
        while (nextLineEnd < len) {

            nextLineEnd = text.indexOf(' ', currentLineEnd + 1);
            nextLineEnd = (nextLineEnd < 0) ? len : nextLineEnd;

            nextLine = text.substring(currentLineStart, nextLineEnd);
            if (font.getTextSize(nextLine) > maxWidth) {
                break;
            }
            currentLine = nextLine;
            currentLineEnd = nextLineEnd;
        }
        cs.showText(currentLine);
        cs.newLine();
        printedTextBuffer.append(currentLine);
        return currentLineEnd + 1;
    }

    private static int findNearestCenterSpace(String text) {
        int middle = text.length() / 2;
        int left = text.lastIndexOf(' ', middle);
        left = left < 0 ? 0 : left;

        int right = text.indexOf(' ', middle);
        right = right < 0 ? text.length() : right;

        int diffLeft = middle - left;
        int diffRight = right - middle;

        return (diffLeft < diffRight) ? left : right;
    }

    public static class Builder {

        private PDRectangle pageSize;
        private int perX;
        private int perY;

        private float marginPercentX;
        private float marginPercentY;

        private float titleFontSize;
        private float nameFontSize;
        private float legendFontSize;
        private float rulesFontSize;
        private float costValueFontSize;
        private float costTypeFontSize;

        private PDFont titleFontType;
        private PDFont nameFontType;
        private PDFont legendFontType;
        private PDFont rulesFontType;
        private PDFont costValueFontType;
        private PDFont costTypeFontType;

        private Color titleFontColor;
        private Color nameFontColor;
        private Color legendFontColor;
        private Color rulesFontColor;
        private Color costValueFontColor;
        private Color costTypeFontColor;

        private Color cardBackgroundColor;
        private Color titleBarsColor;
        private Color upperBarColor;
        private Color lowerBarColor;
        private Color cardBordersColor;
        private Color costBordersColor;
        private Color cardFillColor;
        private Color costValueFillColor;
        private Color costTypeFillColor;
        private Color foregroundGridColor;
        private Color backgroundGridColor;

        private boolean backgroundPages;
        private boolean fillUnusedCardSlots;
        private boolean fillUnusedCardSlotsBorders;
        private boolean fillUnusedCardSlotsTitles;

        public Builder(DefaultPreset preset) {
            this.reset(preset);
        }

        public void reset(DefaultPreset preset) {
            this.pageSize = DEFAULT_PAGE_SIZE;
            this.perX = DEFAULT_CARDS_PER_X;
            this.perY = DEFAULT_CARDS_PER_Y;

            this.marginPercentX = DEFAULT_MARGIN_X;
            this.marginPercentY = DEFAULT_MARGIN_Y;

            this.titleFontSize = DEFAULT_TITLE_FONT_SIZE;
            this.nameFontSize = DEFAULT_NAME_FONT_SIZE;
            this.legendFontSize = DEFAULT_LEGEND_FONT_SIZE;
            this.rulesFontSize = DEFAULT_RULES_FONT_SIZE;
            this.costValueFontSize = preset.getCostValueFontSize();
            this.costTypeFontSize = DEFAULT_COST_TYPE_FONT_SIZE;

            this.titleFontType = DEFAULT_TITLE_FONT_TYPE;
            this.nameFontType = DEFAULT_NAME_FONT_TYPE;
            this.legendFontType = DEFAULT_LEGEND_FONT_TYPE;
            this.rulesFontType = DEFAULT_RULES_FONT_TYPE;
            this.costValueFontType = DEFAULT_COST_VALUE_FONT_TYPE;
            this.costTypeFontType = DEFAULT_COST_TYPE_FONT_TYPE;

            this.titleFontColor = DEFAULT_TITLE_FONT_COLOR;
            this.nameFontColor = DEFAULT_NAME_FONT_COLOR;
            this.legendFontColor = DEFAULT_LEGEND_FONT_COLOR;
            this.rulesFontColor = DEFAULT_RULES_FONT_COLOR;
            this.costValueFontColor = preset.getCostValueFontColor();
            this.costTypeFontColor = DEFAULT_COST_TYPE_FONT_COLOR;

            this.cardBackgroundColor = preset.getCardBackgroundColor();
            this.titleBarsColor = preset.getTitleBarsColor();
            this.upperBarColor = preset.getUpperBarColor();
            this.lowerBarColor = preset.getLowerBarColor();

            this.cardBordersColor = preset.getCardBorderColor();
            this.cardFillColor = preset.getCardFillColor();
            this.costBordersColor = preset.getCostBordersColor();
            this.costValueFillColor = preset.getCostValueFillColor();
            this.costTypeFillColor = DEFAULT_COST_TYPE_FILL_COLOR;
            this.foregroundGridColor = DEFAULT_FOREGROUND_GRID_COLOR;
            this.backgroundGridColor = DEFAULT_BACKGROUND_GRID_COLOR;

            this.backgroundPages = DEFAULT_BACKGROUND_PAGES;
            this.fillUnusedCardSlots = DEFAULT_FILL_UNUSED_CARD_SLOTS;
            this.fillUnusedCardSlotsBorders = DEFAULT_FILL_UNUSED_CARD_SLOTS_COST;
            this.fillUnusedCardSlotsTitles = DEFAULT_FILL_UNUSED_CARD_SLOTS_TITLES;
        }

        public Builder setPageSize(PDRectangle pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder setPerX(int perX) {
            this.perX = perX;
            return this;
        }

        public Builder setPerY(int perY) {
            this.perY = perY;
            return this;
        }

        public Builder setMarginPercentX(float marginX) {
            this.marginPercentX = marginX;
            return this;
        }

        public Builder setMarginPercentY(float marginY) {
            this.marginPercentY = marginY;
            return this;
        }

        public Builder setTitleFontSize(float titleFontSize) {
            this.titleFontSize = titleFontSize;
            return this;
        }

        public Builder setNameFontSize(float nameFontSize) {
            this.nameFontSize = nameFontSize;
            return this;
        }

        public Builder setLegendFontSize(float legendFontSize) {
            this.legendFontSize = legendFontSize;
            return this;
        }

        public Builder setRulesFontSize(float rulesFontSize) {
            this.rulesFontSize = rulesFontSize;
            return this;
        }

        public Builder setCostValueFontSize(float costValueFontSize) {
            this.costValueFontSize = costValueFontSize;
            return this;
        }

        public Builder setCostTypeFontSize(float costTypeFontSize) {
            this.costTypeFontSize = costTypeFontSize;
            return this;
        }

        public Builder setTitleFontType(PDFont titleFont) {
            this.titleFontType = titleFont;
            return this;
        }

        public Builder setNameFontType(PDFont nameFont) {
            this.nameFontType = nameFont;
            return this;
        }

        public Builder setLegendFontType(PDFont legendFont) {
            this.legendFontType = legendFont;
            return this;
        }

        public Builder setRulesFontType(PDFont rulesFont) {
            this.rulesFontType = rulesFont;
            return this;
        }

        public Builder setCostValueFontType(PDFont costFont) {
            this.costValueFontType = costFont;
            return this;
        }

        public Builder setCostTypeFontType(PDFont costTypeFontType) {
            this.costTypeFontType = costTypeFontType;
            return this;
        }

        public Builder setCardBackgroundColor(Color cardBackgroundColor) {
            this.cardBackgroundColor = cardBackgroundColor;
            return this;
        }

        public Builder setTitleBarsColor(Color titleBarsColor) {
            this.titleBarsColor = titleBarsColor;
            return this;
        }

        public Builder setUpperBarColor(Color upperBarColor) {
            this.upperBarColor = upperBarColor;
            return this;
        }

        public Builder setLowerBarColor(Color lowerBarColor) {
            this.lowerBarColor = lowerBarColor;
            return this;
        }

        public Builder setCardBordersColor(Color cardBordersColor) {
            this.cardBordersColor = cardBordersColor;
            return this;
        }

        public Builder setCostBordersColor(Color costBordersColor) {
            this.costBordersColor = costBordersColor;
            return this;
        }

        public Builder setCardFillColor(Color cardFillColor) {
            this.cardFillColor = cardFillColor;
            return this;
        }

        public Builder setCostValueFillColor(Color costValueFillColor) {
            this.costValueFillColor = costValueFillColor;
            return this;
        }

        public Builder setCostTypeFillColor(Color costTypeFillColor) {
            this.costTypeFillColor = costTypeFillColor;
            return this;
        }

        public Builder setTitleFontColor(Color titleFontColor) {
            this.titleFontColor = titleFontColor;
            return this;
        }

        public Builder setNameFontColor(Color nameFontColor) {
            this.nameFontColor = nameFontColor;
            return this;
        }

        public Builder setLegendFontColor(Color legendFontColor) {
            this.legendFontColor = legendFontColor;
            return this;
        }

        public Builder setRulesFontColor(Color rulesFontColor) {
            this.rulesFontColor = rulesFontColor;
            return this;
        }

        public Builder setCostValueFontColor(Color costValueFontColor) {
            this.costValueFontColor = costValueFontColor;
            return this;
        }

        public Builder setCostTypeFontColor(Color costTypeFontColor) {
            this.costTypeFontColor = costTypeFontColor;
            return this;
        }

        public Builder setForegroundGridColor(Color foregroundGridColor) {
            this.foregroundGridColor = foregroundGridColor;
            return this;
        }

        public Builder setBackgroundGridColor(Color backgroundGridColor) {
            this.backgroundGridColor = backgroundGridColor;
            return this;
        }

        public Builder setBackgroundPages(boolean backgroundPages) {
            this.backgroundPages = backgroundPages;
            return this;
        }

        public Builder setFillUnusedCardSlots(boolean fillUnusedCardSlots) {
            this.fillUnusedCardSlots = fillUnusedCardSlots;
            return this;
        }

        public Builder setFillUnusedCardSlotsBorders(boolean fillUnusedCardSlotsBorders) {
            this.fillUnusedCardSlotsBorders = fillUnusedCardSlotsBorders;
            return this;
        }

        public Builder setFillUnusedCardSlotsTitles(boolean fillUnusedCardSlotsTitles) {
            this.fillUnusedCardSlotsTitles = fillUnusedCardSlotsTitles;
            return this;
        }

        public PDRectangle getPageSize() {
            return pageSize;
        }

        public int getPerX() {
            return perX;
        }

        public int getPerY() {
            return perY;
        }

        public float getMarginPercentX() {
            return marginPercentX;
        }

        public float getMarginPercentY() {
            return marginPercentY;
        }

        public float getTitleFontSize() {
            return titleFontSize;
        }

        public float getNameFontSize() {
            return nameFontSize;
        }

        public float getLegendFontSize() {
            return legendFontSize;
        }

        public float getRulesFontSize() {
            return rulesFontSize;
        }

        public float getCostValueFontSize() {
            return costValueFontSize;
        }

        public float getCostTypeFontSize() {
            return costTypeFontSize;
        }

        public PDFont getTitleFontType() {
            return titleFontType;
        }

        public PDFont getNameFontType() {
            return nameFontType;
        }

        public PDFont getLegendFontType() {
            return legendFontType;
        }

        public PDFont getRulesFontType() {
            return rulesFontType;
        }

        public PDFont getCostValueFontType() {
            return costValueFontType;
        }

        public PDFont getCostTypeFontType() {
            return costTypeFontType;
        }

        public Color getTitleFontColor() {
            return titleFontColor;
        }

        public Color getNameFontColor() {
            return nameFontColor;
        }

        public Color getLegendFontColor() {
            return legendFontColor;
        }

        public Color getRulesFontColor() {
            return rulesFontColor;
        }

        public Color getCostValueFontColor() {
            return costValueFontColor;
        }

        public Color getCostTypeFontColor() {
            return costTypeFontColor;
        }

        public Color getCardBackgroundColor() {
            return cardBackgroundColor;
        }

        public Color getTitleBarsColor() {
            return titleBarsColor;
        }

        public Color getUpperBarColor() {
            return upperBarColor;
        }

        public Color getLowerBarColor() {
            return lowerBarColor;
        }

        public Color getCardBordersColor() {
            return cardBordersColor;
        }

        public Color getCostBordersColor() {
            return costBordersColor;
        }

        public Color getCardFillColor() {
            return cardFillColor;
        }

        public Color getCostValueFillColor() {
            return costValueFillColor;
        }

        public Color getCostTypeFillColor() {
            return costTypeFillColor;
        }

        public Color getForegroundGridColor() {
            return foregroundGridColor;
        }

        public Color getBackgroundGridColor() {
            return backgroundGridColor;
        }

        public boolean isBackgroundPages() {
            return backgroundPages;
        }

        public boolean isFillUnusedCardSlots() {
            return fillUnusedCardSlots;
        }

        public boolean isFillUnusedCardSlotsBorders() {
            return fillUnusedCardSlotsBorders;
        }

        public boolean isFillUnusedCardSlotsTitles() {
            return fillUnusedCardSlotsTitles;
        }

        public PdfOutput build() {
            return new PdfOutput(
                    pageSize, perX, perY, marginPercentX, marginPercentY,
                    new FontData(titleFontType, titleFontSize, titleFontColor),
                    new FontData(nameFontType, nameFontSize, nameFontColor),
                    new FontData(legendFontType, legendFontSize, legendFontColor),
                    new FontData(rulesFontType, rulesFontSize, rulesFontColor),
                    new FontData(costValueFontType, costValueFontSize, costValueFontColor),
                    new FontData(costTypeFontType, costTypeFontSize, costTypeFontColor),
                    cardBackgroundColor, titleBarsColor, upperBarColor, lowerBarColor,
                    cardBordersColor, costBordersColor,
                    cardFillColor, costValueFillColor, costTypeFillColor,
                    foregroundGridColor, backgroundGridColor,
                    backgroundPages, fillUnusedCardSlots, fillUnusedCardSlotsBorders, fillUnusedCardSlotsTitles
            );
        }

    }

    static class FontData {

        final PDFont font;
        final float size;
        final Color color;
        private float height;

        public FontData(PDFont font, float size, Color color) {
            this.font = font;
            this.size = size;
            this.color = color;
        }

        float getTextSize(String text) throws IOException {
            return size * font.getStringWidth(text) / 1000;
        }

        float getHeight() throws IOException {
            if (height == 0) {
                height = font.getBoundingBox().getHeight() * size / 1000;
            }
            return height;
        }

    }

    public enum DefaultPreset {
        BW_8 {
            @Override
            Color getCardBackgroundColor() {
                return null;
            }

            @Override
            Color getTitleBarsColor() {
                return Color.BLACK;
            }

            @Override
            Color getUpperBarColor() {
                return null;
            }

            @Override
            Color getLowerBarColor() {
                return null;
            }

            @Override
            Color getCardBorderColor() {
                return Color.BLACK;
            }

            @Override
            Color getCostBordersColor() {
                return Color.BLACK;
            }

            @Override
            float getCostValueFontSize() {
                return 11;
            }

            @Override
            Color getCostValueFontColor() {
                return Color.BLACK;
            }

            @Override
            Color getCostValueFillColor() {
                return null;
            }

            @Override
            Color getCardFillColor() {
                return null;
            }

        }, COLOR_8 {
            @Override
            Color getCardBackgroundColor() {
                return new Color(0xEFF5EF);
            }

            @Override
            Color getTitleBarsColor() {
                return Color.BLACK;
            }

            @Override
            Color getUpperBarColor() {
                return null;
            }

            @Override
            Color getLowerBarColor() {
                return null;
            }

            @Override
            Color getCardBorderColor() {
                return Color.BLACK;
            }

            @Override
            Color getCostBordersColor() {
                return Color.BLACK;
            }

            @Override
            float getCostValueFontSize() {
                return 11;
            }

            @Override
            Color getCostValueFontColor() {
                return Color.WHITE;
            }

            @Override
            Color getCostValueFillColor() {
                return new Color(0xCC0000);
            }

            @Override
            Color getCardFillColor() {
                return new Color(0xEAF4EE);
            }

        },
        BW_9 {
            @Override
            Color getCardBackgroundColor() {
                return null;
            }

            @Override
            Color getTitleBarsColor() {
                return null;
            }

            @Override
            Color getUpperBarColor() {
                return Color.BLACK;
            }

            @Override
            Color getLowerBarColor() {
                return Color.BLACK;
            }

            @Override
            Color getCardBorderColor() {
                return null;
            }

            @Override
            Color getCostBordersColor() {
                return null;
            }

            @Override
            float getCostValueFontSize() {
                return 8;
            }

            @Override
            Color getCostValueFontColor() {
                return Color.BLACK;
            }

            @Override
            Color getCostValueFillColor() {
                return null;
            }

            @Override
            Color getCardFillColor() {
                return null;
            }

        };

        abstract Color getCardBackgroundColor();

        abstract float getCostValueFontSize();

        abstract Color getCostValueFontColor();

        abstract Color getTitleBarsColor();

        abstract Color getUpperBarColor();

        abstract Color getLowerBarColor();

        abstract Color getCardBorderColor();

        abstract Color getCostBordersColor();

        abstract Color getCostValueFillColor();

        abstract Color getCardFillColor();
    }

}
