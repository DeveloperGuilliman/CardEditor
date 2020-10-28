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
import com.developerguilliman.cardEditor.data.CardData;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 *
 * @author DeveloperGuilliman
 */
public class PdfOutput implements ICardOutput {

    private static final Color VERY_LIGHT_GRAY = new Color(0xe7, 0xe7, 0xe7);

    public static final int DEFAULT_CARDS_PER_X = 3;
    public static final int DEFAULT_CARDS_PER_Y = 3;

    public static final int DEFAULT_MARGIN_X = 3;
    public static final int DEFAULT_MARGIN_Y = 3;

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

    private final float LEADING_FACTOR = 1.125f;
    private final float LEADING_INTERTEXT_FACTOR = 0.5f;

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

    private final boolean titleBars;
    private final boolean cardBorders;

    private final CardHash cardHash;
    private final StringBuilder printedTextBuffer;

    public PdfOutput() {

        this.perX = DEFAULT_MARGIN_X;
        this.perY = DEFAULT_MARGIN_Y;

        this.marginPercentX = DEFAULT_MARGIN_X;
        this.marginPercentY = DEFAULT_MARGIN_Y;

        this.titleFont = new FontData(DEFAULT_TITLE_FONT_TYPE, DEFAULT_TITLE_FONT_SIZE);
        this.nameFont = new FontData(DEFAULT_NAME_FONT_TYPE, DEFAULT_NAME_FONT_SIZE);
        this.legendFont = new FontData(DEFAULT_LEGEND_FONT_TYPE, DEFAULT_LEGEND_FONT_SIZE);
        this.rulesFont = new FontData(DEFAULT_RULES_FONT_TYPE, DEFAULT_RULES_FONT_SIZE);
        this.costFont = new FontData(DEFAULT_COST_FONT_TYPE, DEFAULT_COST_FONT_SIZE);

        this.titleBars = true;
        this.cardBorders = true;

        this.cardHash = new CardHash();
        this.printedTextBuffer = new StringBuilder();
    }

    private PdfOutput(int perX, int perY, float marginPercentX, float marginPercentY, FontData titleFont, FontData nameFont, FontData legendFont, FontData rulesFont, FontData costFont, boolean titleBars, boolean cardBorders) {
        this.perX = perX;
        this.perY = perY;

        this.marginPercentX = marginPercentX;
        this.marginPercentY = marginPercentY;

        this.titleFont = titleFont;
        this.nameFont = nameFont;
        this.legendFont = legendFont;
        this.rulesFont = rulesFont;
        this.costFont = costFont;

        this.titleBars = titleBars;
        this.cardBorders = cardBorders;

        this.cardHash = new CardHash();
        this.printedTextBuffer = new StringBuilder();
    }

    @Override
    public void build(OutputStream out, List<List<CardData>> cards) throws IOException {

        try (PDDocument document = new PDDocument()) {
            buildDocument(document, cards);
            document.save(out);

        }

    }

    private void buildDocument(PDDocument document, List<List<CardData>> cards) throws IOException {

        for (List<CardData> cardPage : cards) {
            Iterator<CardData> cardIterator = cardPage.iterator();
            while (cardIterator.hasNext()) {

                PDPage page = new PDPage();
                document.addPage(page);

                final float pageWidth = page.getBBox().getWidth();
                final float pageHeight = page.getBBox().getHeight();

                final float printableWidth = pageWidth * (100 - marginPercentX) / 100;
                final float printableHeight = pageHeight * (100 - marginPercentY) / 100;

                final float marginX = (pageWidth - printableWidth) / 2;
                final float marginY = (pageHeight - printableHeight) / 2;

                final float cardWidth = printableWidth / perX;
                final float cardHeight = printableHeight / perY;

                try (PDPageContentStream cont = new PDPageContentStream(document, page)) {

                    buildPage(cont, cardIterator, marginX, marginY, cardWidth, cardHeight);
                }
            }
        }
    }

    private void buildPage(PDPageContentStream cont, Iterator<CardData> cardIterator,
            float marginX, float marginY, float width, float height) throws IOException {
        // Up to Down
        for (int y = perY - 1; y >= 0; y--) {
            // Left to Right
            for (int x = 0; x < perX; x++) {
                if (!cardIterator.hasNext()) {
                    return;
                }

                CardData card = cardIterator.next();
                float x0 = width * x + marginX;
                float y0 = height * y + marginY;
                printCard(cont, null, card, x0, y0, width, height);
            }
        }
    }

    private void printCard(PDPageContentStream cont, PDImageXObject backgroundImage, CardData card,
            float x, float y, float width, float height) throws IOException {

        String title = card.getTitle().trim();
        String name = card.getName().trim();
        String legend = card.getLegend().trim();
        String rules = card.getRules().trim();
        String cost = card.getCost().trim();

        printedTextBuffer.setLength(0);

        drawRectangleOutside(cont, x, y, width, height);

        if (backgroundImage != null) {
            cont.drawImage(backgroundImage, x, y, width, height);
        }
        if (cardBorders) {
            drawPoligonOutside(cont, x, y, width, height);
        }

        final float normalTextWidth = width * 0.9f;
        final float normalTextMarginX = (width - normalTextWidth) / 2;

        final float factionTextWidth = width * 0.8125f;
        final float factionTextMarginX = (width - factionTextWidth) / 2;

        float nextY = y + 0.98f * height;
        float minY = y + 0.03f * height + costFont.size;

        nextY -= printCenteredText(title, x + factionTextMarginX, nextY, factionTextWidth, titleFont, cont, printedTextBuffer);
        nextY -= 2;

        if (titleBars) {
            nextY -= 3;
            drawNameLines(cont, x, width, nextY + 1);
        }

        nextY -= printCenteredText(name, x + normalTextMarginX, nextY, normalTextWidth, nameFont, cont, printedTextBuffer);

        if (titleBars) {
            drawNameLines(cont, x, width, nextY - 2);
            nextY -= 4;
        }
        nextY -= 2;

        nextY -= printBreakingText(legend, x + normalTextMarginX, nextY, normalTextWidth, nextY - minY - legendFont.size, legendFont, cont, printedTextBuffer);

        nextY -= printBreakingText(rules, x + normalTextMarginX, nextY, normalTextWidth, nextY - minY - rulesFont.size, rulesFont, cont, printedTextBuffer);

        printCenteredText(cost, x + normalTextMarginX, minY, normalTextWidth, costFont, cont, printedTextBuffer);

        String printedText = printedTextBuffer.toString();
        //System.out.println(printedText);

        printRightText(cardHash.getStringsHash(printedText), x - (normalTextMarginX / 2), minY - (1.5f * hashFont.size), normalTextWidth, hashFont, cont);
        cont.setNonStrokingColor(Color.BLACK);

    }

    private void drawRectangleOutside(PDPageContentStream cont, float x, float y, float width, float height)
            throws IOException {
        cont.addRect(x, y, width, height);
        cont.setStrokingColor(VERY_LIGHT_GRAY);
        cont.stroke();
    }

    private void drawNameLines(PDPageContentStream cont, float x, float width, float nextY) throws IOException {
        float lineX0 = x + width * 0.1f;
        float lineX1 = x + width * 0.9f;
        cont.setStrokingColor(Color.BLACK);

        cont.moveTo(lineX0, nextY);
        cont.lineTo(lineX1, nextY);
        cont.stroke();
    }

    private void drawPoligonOutside(PDPageContentStream cont, float x, float y, float width, float height)
            throws IOException {

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

        cont.moveTo(x1, y0);
        cont.lineTo(x2, y0);
        cont.lineTo(x3, y1);
        cont.lineTo(x3, y2);
        cont.lineTo(x2, y3);
        cont.lineTo(x1, y3);
        cont.lineTo(x0, y2);
        cont.lineTo(x0, y1);
        cont.lineTo(x1, y0);

        cont.setStrokingColor(Color.BLACK);
        cont.stroke();

    }

    private float printCenteredText(String text, float x, float y, float maxWidth, FontData font,
            PDPageContentStream cont, StringBuilder printedTextBuffer) throws IOException {

        if (text.isEmpty()) {
            return 0;
        }
        y -= font.size;

        float leading = font.size * LEADING_FACTOR;

        cont.setLeading(leading);

        float size = getTextSize(font, text);

        if (size > maxWidth) {
            int center = findNearestCenterSpace(text);
            if (center > 0 && center < text.length()) {
                String pre = text.substring(0, center);
                String post = text.substring(center + 1);
                printCenteredText(pre, x, y, maxWidth, font, cont, getTextSize(font, pre), printedTextBuffer);
                printCenteredText(post, x, y - leading, maxWidth, font, cont, getTextSize(font, post), printedTextBuffer);
                return (2 * leading);
            }
        }

        printCenteredText(text, x, y, maxWidth, font, cont, size, printedTextBuffer);
        return leading;

    }

    private void printCenteredText(String text, float x, float y, float maxWidth, FontData font,
            PDPageContentStream cont, float size, StringBuilder printedTextBuffer) throws IOException {

        float xDisp = (maxWidth - size) / 2;
        cont.setFont(font.font, font.size);
        cont.setNonStrokingColor(font.color);
        cont.beginText();
        cont.newLineAtOffset(x + xDisp, y);
        cont.showText(text);
        printedTextBuffer.append(text);
        printedTextBuffer.append("\n");
        cont.endText();
    }

    private void printRightText(String text, float x, float y, float maxWidth, FontData font,
            PDPageContentStream cont) throws IOException {

        float xDisp = maxWidth - getTextSize(font, text);
        cont.setFont(font.font, font.size);
        cont.setNonStrokingColor(font.color);
        cont.beginText();
        cont.newLineAtOffset(x + xDisp, y);
        cont.showText(text);
        printedTextBuffer.append(text);
        printedTextBuffer.append("\n");
        cont.endText();
    }

    private float printBreakingText(String text, float x, float y, float maxWidth, float maxHeight, FontData font,
            PDPageContentStream cont, StringBuilder printedTextBuffer) throws IOException {

        if (text.isEmpty()) {
            return 0;
        }
        y -= font.size;

        float leading = font.size * LEADING_FACTOR;
        float yDiff = 0;
        cont.setFont(font.font, font.size);
        cont.setNonStrokingColor(font.color);
        cont.setLeading(leading);
        cont.beginText();
        cont.newLineAtOffset(x, y);

        int currentLineStart = 0;
        while (currentLineStart < text.length() && yDiff < maxHeight) {
            currentLineStart = printLine(text, currentLineStart, maxWidth, font, cont, printedTextBuffer);
            printedTextBuffer.append("\n");
            yDiff += leading;
        }
        cont.endText();
        return (yDiff + (leading * LEADING_INTERTEXT_FACTOR));
    }

    private int printLine(String text, int currentLineStart, float maxWidth, FontData font,
            PDPageContentStream cont, StringBuilder printedTextBuffer) throws IOException {

        int len = text.length();

        int currentLineEnd = text.indexOf(' ', currentLineStart + 1);
        if (currentLineEnd < 0) {
            String currentLine = text.substring(currentLineStart);
            cont.showText(currentLine);
            printedTextBuffer.append(currentLine);
            return len;
        }
        String currentLine = text.substring(currentLineStart, currentLineEnd);
        String nextLine;
        int nextLineEnd = 0;
        while (nextLineEnd < len) {

            nextLineEnd = text.indexOf(' ', currentLineEnd + 1);
            if (nextLineEnd < 0) {
                nextLineEnd = len;
            }
            nextLine = text.substring(currentLineStart, nextLineEnd);
            if (getTextSize(font, nextLine) > maxWidth) {
                break;
            }
            currentLine = nextLine;
            currentLineEnd = nextLineEnd;
        }
        cont.showText(currentLine);
        printedTextBuffer.append(currentLine);
        cont.newLine();
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

        private boolean titleBars;
        private boolean cardBorders;

        public Builder() {

            this.perX = DEFAULT_MARGIN_X;
            this.perY = DEFAULT_MARGIN_Y;

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

            this.titleBars = true;
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

        public Builder setTitleBars(boolean titleBars) {
            this.titleBars = titleBars;
            return this;
        }

        public Builder setCardBorders(boolean cardBorders) {
            this.cardBorders = cardBorders;
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

        public PdfOutput build() {
            return new PdfOutput(
                    perX, perY, marginPercentX, marginPercentY,
                    new FontData(titleFontType, titleFontSize, titleFontColor),
                    new FontData(nameFontType, nameFontSize, nameFontColor),
                    new FontData(legendFontType, legendFontSize, legendFontColor),
                    new FontData(rulesFontType, rulesFontSize, rulesFontColor),
                    new FontData(costFontType, costFontSize, costFontColor),
                    titleBars, cardBorders
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

        public FontData(PDFont font, float size) {
            this(font, size, Color.BLACK);
        }

    }

}
