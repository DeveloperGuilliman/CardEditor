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
package com.developerguilliman.cardEditor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author DeveloperGuilliman
 */
public class CardHash {

    // Base32 alphabet with only vocal E, to minimize the chance to printing
    // a word meanwhile all characters have the same height in the pdf
    private static final char[] BASE32_ALPHABET = "BCDEFGHJKLMNPQRSTVWXYZ1234567890".toCharArray();

    private final MessageDigest messageDigest;

    public CardHash() {
        try {
            this.messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException ex) {
            throw new Error(ex);
        }
    }

    public String getStringsHash(String s) {
        byte[] digest;
        synchronized (messageDigest) {
            digest = messageDigest.digest(s.getBytes(StandardCharsets.UTF_8));
            messageDigest.reset();
        }
        return base32Encode(digest, 4).substring(0, 5);
    }

    public String getStringsHash(String... strings) {
        byte[] digest;
        synchronized (messageDigest) {
            for (String s : strings) {
                messageDigest.update(s.getBytes(StandardCharsets.UTF_8));
            }
            digest = messageDigest.digest();
            messageDigest.reset();
        }
        return base32Encode(digest, 4).substring(0, 5);
    }

    private static StringBuilder base32Encode(byte[] bytes, int len) {
        int i = 0;
        int index = 0;
        int currByte, nextByte;
        StringBuilder base32 = new StringBuilder((len + 7) * 8 / 5);

        while (i < len) {
            int ni = i + 1;
            currByte = (bytes[i] >= 0) ? bytes[i] : (bytes[i] + 256);
            int digit;
            if (index > 3) {
                if (ni < len) {
                    nextByte = (bytes[ni] >= 0) ? bytes[ni] : (bytes[ni] + 256);
                } else {
                    nextByte = 0;
                }

                digit = currByte & (0xFF >> index);
                index = (index + 5) & 0x7;
                digit <<= index;
                digit |= nextByte >> (8 - index);
                i = ni;
            } else {
                digit = (currByte >> (8 - (index + 5))) & 0x1F;
                index = (index + 5) & 0x7;
                i = (index == 0) ? ni : i;
            }
            base32.append(BASE32_ALPHABET[digit]);
        }
        return base32;
    }

}
