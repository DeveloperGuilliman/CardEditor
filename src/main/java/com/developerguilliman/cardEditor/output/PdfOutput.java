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
package com.developerguilliman.cardEditor.output;

import com.developerguilliman.cardEditor.CardHash;
import com.developerguilliman.cardEditor.data.CardCollectionData;
import com.developerguilliman.cardEditor.data.CardData;
import com.developerguilliman.cardEditor.data.SectionData;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author DeveloperGuilliman
 */
public class PdfOutput implements ICardOutput {

    private static final float LEADING_FACTOR = 1.125f;
    private static final float LEADING_INTERTEXT_FACTOR = 0.5f;
    private static final Color VERY_LIGHT_GRAY = new Color(0xe7, 0xe7, 0xe7);
    private static final float EXTRA_GRID = 5;

    public static final PDRectangle DEFAULT_PAGE_SIZE = PDRectangle.A4;

    public static final int DEFAULT_CARDS_PER_X = 3;
    public static final int DEFAULT_CARDS_PER_Y = 3;

    public static final float DEFAULT_MARGIN_X = 10;
    public static final float DEFAULT_MARGIN_Y = 11.2f;

    public static final float DEFAULT_TITLE_FONT_SIZE = 9;
    public static final float DEFAULT_NAME_FONT_SIZE = 11;
    public static final float DEFAULT_LEGEND_FONT_SIZE = 7;
    public static final float DEFAULT_RULES_FONT_SIZE = 7;
    public static final float DEFAULT_COST_FONT_SIZE = 11;

    public static final PDType1Font DEFAULT_TITLE_FONT_TYPE = PDType1Font.HELVETICA_BOLD;
    public static final PDType1Font DEFAULT_NAME_FONT_TYPE = PDType1Font.HELVETICA_BOLD;
    public static final PDType1Font DEFAULT_LEGEND_FONT_TYPE = PDType1Font.TIMES_ITALIC;
    public static final PDType1Font DEFAULT_RULES_FONT_TYPE = PDType1Font.TIMES_ROMAN;
    public static final PDType1Font DEFAULT_COST_FONT_TYPE = PDType1Font.HELVETICA_BOLD;

    public static final Color DEFAULT_TITLE_FONT_COLOR = Color.BLACK;
    public static final Color DEFAULT_NAME_FONT_COLOR = Color.BLACK;
    public static final Color DEFAULT_LEGEND_FONT_COLOR = Color.BLACK;
    public static final Color DEFAULT_RULES_FONT_COLOR = Color.BLACK;
    public static final Color DEFAULT_COST_FONT_COLOR = Color.BLACK;

    public static final Color DEFAULT_TITLE_BAR_COLOR = Color.BLACK;
    public static final Color DEFAULT_CARD_BORDER_COLOR = Color.BLACK;
    public static final Color DEFAULT_CARD_FILL_COLOR = Color.WHITE;
    public static final Color DEFAULT_FOREGROUND_GRID_COLOR = VERY_LIGHT_GRAY;
    public static final Color DEFAULT_BACKGROUND_GRID_COLOR = VERY_LIGHT_GRAY;

    public static final boolean DEFAULT_BACKGROUND_PAGES = false;

    private final PDRectangle pageSize;

    private final int perX;
    private final int perY;

    private final float marginPercentX;
    private final float marginPercentY;

    private final FontData titleFont;
    private final FontData nameFont;
    private final FontData legendFont;
    private final FontData rulesFont;
    private final FontData costFont;
    private final FontData hashFont = new FontData(PDType1Font.HELVETICA, 6, Color.GRAY);

    private final Color titleBarsColor;
    private final Color cardBordersColor;
    private final Color cardFillColor;
    private final Color foregroundGridColor;
    private final Color backgroundGridColor;
    private final boolean backgroundPages;

    private final CardHash cardHash;
    private final StringBuilder printedTextBuffer;

    public PdfOutput() {

        this.pageSize = DEFAULT_PAGE_SIZE;

        this.perX = DEFAULT_CARDS_PER_X;
        this.perY = DEFAULT_CARDS_PER_Y;

        this.marginPercentX = DEFAULT_MARGIN_X;
        this.marginPercentY = DEFAULT_MARGIN_Y;

        this.titleFont = new FontData(DEFAULT_TITLE_FONT_TYPE, DEFAULT_TITLE_FONT_SIZE, DEFAULT_TITLE_FONT_COLOR);
        this.nameFont = new FontData(DEFAULT_NAME_FONT_TYPE, DEFAULT_NAME_FONT_SIZE, DEFAULT_NAME_FONT_COLOR);
        this.legendFont = new FontData(DEFAULT_LEGEND_FONT_TYPE, DEFAULT_LEGEND_FONT_SIZE, DEFAULT_LEGEND_FONT_COLOR);
        this.rulesFont = new FontData(DEFAULT_RULES_FONT_TYPE, DEFAULT_RULES_FONT_SIZE, DEFAULT_RULES_FONT_COLOR);
        this.costFont = new FontData(DEFAULT_COST_FONT_TYPE, DEFAULT_COST_FONT_SIZE, DEFAULT_COST_FONT_COLOR);

        this.titleBarsColor = DEFAULT_TITLE_BAR_COLOR;
        this.cardBordersColor = DEFAULT_CARD_BORDER_COLOR;
        this.cardFillColor = DEFAULT_CARD_FILL_COLOR;
        this.foregroundGridColor = DEFAULT_FOREGROUND_GRID_COLOR;
        this.backgroundGridColor = DEFAULT_BACKGROUND_GRID_COLOR;

        this.backgroundPages = DEFAULT_BACKGROUND_PAGES;

        this.cardHash = new CardHash();
        this.printedTextBuffer = new StringBuilder();
    }

    private PdfOutput(PDRectangle pageSize, int perX, int perY, float marginPercentX, float marginPercentY,
            FontData titleFont, FontData nameFont, FontData legendFont, FontData rulesFont, FontData costFont,
            Color titleBarsColor, Color cardBordersColor, Color cardFillColor,
            Color foregroundGridColor, Color backgroundGridColor, boolean backgroundPages) {

        this.pageSize = pageSize;
        this.perX = perX;
        this.perY = perY;

        this.marginPercentX = marginPercentX;
        this.marginPercentY = marginPercentY;

        this.titleFont = titleFont;
        this.nameFont = nameFont;
        this.legendFont = legendFont;
        this.rulesFont = rulesFont;
        this.costFont = costFont;

        this.titleBarsColor = titleBarsColor;
        this.cardBordersColor = cardBordersColor;
        this.cardFillColor = cardFillColor;

        this.foregroundGridColor = foregroundGridColor;
        this.backgroundGridColor = backgroundGridColor;

        this.backgroundPages = backgroundPages;

        this.cardHash = new CardHash();
        this.printedTextBuffer = new StringBuilder();
    }

    @Override
    public void build(OutputStream out, CardCollectionData cards) throws IOException {

        try ( PDDocument document = new PDDocument()) {
            buildDocument(document, cards);
            document.save(out);

        }

    }

    private void buildDocument(PDDocument document, CardCollectionData cards) throws IOException {

        for (SectionData cardPage : cards) {
            Iterator<CardData> cardIterator = cardPage.iterator();
            while (cardIterator.hasNext()) {

                int printedCards = buildForegroundPage(document, cardIterator);
                if (backgroundPages) {
                    buildBackgroundPage(document, printedCards);
                }
            }
        }
    }

    private int buildForegroundPage(PDDocument document, Iterator<CardData> cardIterator) throws IOException {
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

            int printedCards = buildCardsContentPage(cs, cardIterator, marginX, marginY, cardWidth, cardHeight);
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

    private int buildCardsContentPage(PDPageContentStream cs, Iterator<CardData> cardIterator,
            float marginX, float marginY, float cardWidth, float cardHeight) throws IOException {

        int printedCards = 0;

        // Up to Down
        for (int numY = perY - 1; numY >= 0; numY--) {
            // Left to Right
            for (int numX = 0; numX < perX; numX++) {
                if (!cardIterator.hasNext()) {
                    return printedCards;
                }
                CardData card = cardIterator.next();
                float x = cardWidth * numX + marginX;
                float y = cardHeight * numY + marginY;
                printCardForeground(cs, card, x, y, cardWidth, cardHeight);
                printedCards++;
            }
        }
        return printedCards;
    }

    private void buildCardsBackgroundPage(PDPageContentStream cs, int printedCards,
            float marginX, float marginY, float width, float height) throws IOException {

        // Up to Down
        for (int numY = perY - 1; numY >= 0; numY--) {
            // Left to Right
            for (int numX = 0; numX < perX; numX++) {
                if (printedCards == 0) {
                    return;
                }
                float x = width * numX + marginX;
                float y = height * numY + marginY;
                printCardBackground(cs, x, y, width, height);
                printedCards--;
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

    private void printCardForeground(PDPageContentStream cs, CardData card, float x, float y,
            float width, float height) throws IOException {

        String title = card.getTitle().replace('\n', ' ').trim();
        String name = card.getName().replace('\n', ' ').trim();
        String legend = card.getLegend().trim();
        String rules = card.getRules().trim();
        String cost = card.getCost().replace('\n', ' ').trim();

        printedTextBuffer.setLength(0);

//        if (foregroundImage != null) {
//            cs.drawImage(foregroundImage, x, y, width, height);
//        }
        if (cardBordersColor != null || cardFillColor != null) {
            drawCardPoligon(cs, x, y, width, height, cardBordersColor, cardFillColor);
        }

        final float normalTextWidth = width * 0.9f;
        final float normalTextMarginX = (width - normalTextWidth) / 2;

        final float factionTextWidth = width * 0.8125f;
        final float factionTextMarginX = (width - factionTextWidth) / 2;

        float nextY = y + 0.98f * height;
        float minY = y + 0.03f * height + costFont.size;

        nextY -= printCenteredText(cs, title, x + factionTextMarginX, nextY, factionTextWidth, titleFont, printedTextBuffer);
        nextY -= 2;

        if (titleBarsColor != null) {
            nextY -= 3;
            drawNameLines(cs, x, width, nextY + 1, titleBarsColor);
        }

        nextY -= printCenteredText(cs, name, x + normalTextMarginX, nextY, normalTextWidth, nameFont, printedTextBuffer);

        if (titleBarsColor != null) {
            drawNameLines(cs, x, width, nextY - 2, titleBarsColor);
            nextY -= 4;
        }
        nextY -= 2;

        nextY -= printBreakingText(legend, x + normalTextMarginX, nextY, normalTextWidth, nextY - minY, legendFont, cs, printedTextBuffer);

        nextY -= printBreakingText(rules, x + normalTextMarginX, nextY, normalTextWidth, nextY - minY, rulesFont, cs, printedTextBuffer);

        nextY -= printCenteredText(cs, cost, x + normalTextMarginX, minY, normalTextWidth, costFont, printedTextBuffer);

        String printedText = printedTextBuffer.toString();
        //System.out.println(printedText);

        printRightText(cs, cardHash.getStringsHash(printedText), x - (normalTextMarginX / 2), minY - (1.5f * hashFont.size), normalTextWidth, hashFont, printedTextBuffer);

    }

    private static void drawNameLines(PDPageContentStream cs, float x, float width, float nextY, Color color) throws IOException {
        float lineX0 = x + width * 0.1f;
        float lineX1 = x + width * 0.9f;
        cs.setStrokingColor(color);

        cs.moveTo(lineX0, nextY);
        cs.lineTo(lineX1, nextY);
        cs.stroke();
    }

    private void printCardBackground(PDPageContentStream cs, float x, float y, float width, float height) throws IOException {

        if (cardBordersColor != null || cardFillColor != null) {
            drawCardPoligon(cs, x, y, width, height, cardBordersColor, cardFillColor);
        }
    }

    private static void drawCardPoligon(PDPageContentStream cs, float x, float y, float width, float height,
            Color outerColor, Color fillColor) throws IOException {

        width -= 2;
        height -= 2;
        float blankSpace = width * 0.125f;
        float x0 = x + 1;
        float x1 = x0 + blankSpace;
        float x2 = x0 + width - blankSpace;
        float x3 = x0 + width;
        float y0 = y + 1;
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

    private static float printCenteredText(PDPageContentStream cs, String text, float x, float y,
            float maxWidth, FontData font, StringBuilder printedTextBuffer) throws IOException {

        if (text.isEmpty()) {
            return 0;
        }
        y -= font.size;

        float leading = font.size * LEADING_FACTOR;

        cs.setLeading(leading);

        float size = getTextSize(font, text);

        if (size > maxWidth) {
            int center = findNearestCenterSpace(text);
            if (center > 0 && center < text.length()) {
                String pre = text.substring(0, center);
                String post = text.substring(center + 1);
                printCenteredText(pre, x, y, maxWidth, font, cs, getTextSize(font, pre), printedTextBuffer);
                printCenteredText(post, x, y - leading, maxWidth, font, cs, getTextSize(font, post), printedTextBuffer);
                return (2 * leading);
            }
        }

        printCenteredText(text, x, y, maxWidth, font, cs, size, printedTextBuffer);
        return leading;

    }

    private static void printCenteredText(String text, float x, float y, float maxWidth, FontData font,
            PDPageContentStream cs, float size, StringBuilder printedTextBuffer) throws IOException {

        float xDisp = (maxWidth - size) / 2;
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

        float xDisp = maxWidth - getTextSize(font, text);
        cs.setFont(font.font, font.size);
        cs.setNonStrokingColor(font.color);
        cs.beginText();
        cs.newLineAtOffset(x + xDisp, y);
        cs.showText(text);
        printedTextBuffer.append(text);
        printedTextBuffer.append('\n');
        cs.endText();
    }

    private static float printBreakingText(String text, float x, float y, float maxWidth, float maxHeight, FontData font,
            PDPageContentStream cs, StringBuilder printedTextBuffer) throws IOException {

        if (text.isEmpty()) {
            return 0;
        }
        y -= font.size;

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
            newlineIndexOf = (newlineIndexOf < 0) ? text.length() : newlineIndexOf;
            if (prevNewlineIndexOf == newlineIndexOf) {
                cs.newLine();
                printedTextBuffer.append('\n');
                yDiff += leading;
            } else {
                String line = text.substring(prevNewlineIndexOf, newlineIndexOf);
                int currentLineStart = 0;
                while (currentLineStart < line.length() && yDiff < maxHeight) {
                    currentLineStart = printLine(line, currentLineStart, maxWidth, font, cs, printedTextBuffer);
                    printedTextBuffer.append('\n');
                    yDiff += leading;
                }
            }
            prevNewlineIndexOf = newlineIndexOf + 1;
            newlineIndexOf = text.indexOf('\n', prevNewlineIndexOf);
        } while (newlineIndexOf >= 0);
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
            if (getTextSize(font, nextLine) > maxWidth) {
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

    private static float getTextSize(FontData font, String text) throws IOException {
        return font.size * font.font.getStringWidth(text) / 1000;
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
        private float costFontSize;

        private PDFont titleFontType;
        private PDFont nameFontType;
        private PDFont legendFontType;
        private PDFont rulesFontType;
        private PDFont costFontType;

        private Color titleFontColor;
        private Color nameFontColor;
        private Color legendFontColor;
        private Color rulesFontColor;
        private Color costFontColor;

        private Color titleBarsColor;
        private Color cardBordersColor;
        private Color cardFillColor;
        private Color foregroundGridColor;
        private Color backgroundGridColor;

        private boolean backgroundPages;

        public Builder() {

            this.pageSize = DEFAULT_PAGE_SIZE;
            this.perX = DEFAULT_CARDS_PER_X;
            this.perY = DEFAULT_CARDS_PER_Y;

            this.marginPercentX = DEFAULT_MARGIN_X;
            this.marginPercentY = DEFAULT_MARGIN_Y;

            this.titleFontSize = DEFAULT_TITLE_FONT_SIZE;
            this.nameFontSize = DEFAULT_NAME_FONT_SIZE;
            this.legendFontSize = DEFAULT_LEGEND_FONT_SIZE;
            this.rulesFontSize = DEFAULT_RULES_FONT_SIZE;
            this.costFontSize = DEFAULT_COST_FONT_SIZE;

            this.titleFontType = DEFAULT_TITLE_FONT_TYPE;
            this.nameFontType = DEFAULT_NAME_FONT_TYPE;
            this.legendFontType = DEFAULT_LEGEND_FONT_TYPE;
            this.rulesFontType = DEFAULT_RULES_FONT_TYPE;
            this.costFontType = DEFAULT_COST_FONT_TYPE;

            this.titleBarsColor = DEFAULT_TITLE_BAR_COLOR;
            this.cardBordersColor = DEFAULT_CARD_BORDER_COLOR;
            this.cardFillColor = DEFAULT_CARD_FILL_COLOR;
            this.foregroundGridColor = DEFAULT_FOREGROUND_GRID_COLOR;
            this.backgroundGridColor = DEFAULT_BACKGROUND_GRID_COLOR;

            this.backgroundPages = DEFAULT_BACKGROUND_PAGES;
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

        public Builder setTitleFontSize(
                float titleFontSize) {
            this.titleFontSize = titleFontSize;
            return this;
        }

        public Builder setNameFontSize(
                float nameFontSize) {
            this.nameFontSize = nameFontSize;
            return this;
        }

        public Builder setLegendFontSize(
                float legendFontSize) {
            this.legendFontSize = legendFontSize;
            return this;
        }

        public Builder setRulesFontSize(
                float rulesFontSize) {
            this.rulesFontSize = rulesFontSize;
            return this;
        }

        public Builder setCostFontSize(
                float costFontSize) {
            this.costFontSize = costFontSize;
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

        public Builder setCostFontType(PDFont costFont) {
            this.costFontType = costFont;
            return this;
        }

        public Builder setTitleBarsColor(Color titleBarsColor) {
            this.titleBarsColor = titleBarsColor;
            return this;
        }

        public Builder setCardBordersColor(Color cardBordersColor) {
            this.cardBordersColor = cardBordersColor;
            return this;
        }

        public Builder setCardFillColor(Color cardFillColor) {
            this.cardFillColor = cardFillColor;
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

        public Builder setCostFontColor(Color costFontColor) {
            this.costFontColor = costFontColor;
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

        public PdfOutput build() {
            return new PdfOutput(
                    pageSize, perX, perY, marginPercentX, marginPercentY,
                    new FontData(titleFontType, titleFontSize, titleFontColor),
                    new FontData(nameFontType, nameFontSize, nameFontColor),
                    new FontData(legendFontType, legendFontSize, legendFontColor),
                    new FontData(rulesFontType, rulesFontSize, rulesFontColor),
                    new FontData(costFontType, costFontSize, costFontColor),
                    titleBarsColor, cardBordersColor, cardFillColor,
                    foregroundGridColor, backgroundGridColor, backgroundPages
            );
        }

    }

    private static class FontData {

        private final PDFont font;
        private final float size;
        private final Color color;

        public FontData(PDFont font, float size, Color color) {
            this.font = font;
            this.size = size;
            this.color = color;
        }

    }

}
