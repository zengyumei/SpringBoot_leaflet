package com.hbase;

import java.util.BitSet;
import java.io.File;
import java.math.BigInteger;

public class Rowkey {
    
    private static final String base32Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
    
    /**
     * Get Geohash number
     * 
     * @param level:tile level
     * @param number:number of row or column
     * @param floor:minnumber of this level
     * @param ceiling:maxnumber of this level
     * @return BitSet
     */
    public static BitSet getBits(int level, int number, double floor, double ceiling) {
        BitSet buffer = new BitSet(level);
        for (int i = 0; i < level; i++) {
                double mid = (floor + ceiling) / 2;
                if (number > mid) {
                        buffer.set(i);
                        floor = mid;
                } else {
                        ceiling = mid;
                }
        }
        return buffer;
    }
    
    /**
     * Get Rowkey of tile
     * 
     * @param level:tile level
     * @param col: tile column
     * @param row: tile row
     * @return String
     */
    public static String getRowkey(int level, int row, int col) {
        StringBuilder buffer = new StringBuilder();
        StringBuilder rowkey = new StringBuilder();
        String str = base32Encode(decimalToBinary(level));
        rowkey.append(str);
        // String str = String.format("%02d", level);
        if (row < (int) Math.pow(2, level) && col < (int) Math.pow(2, level)) {
                if (level == 0) {
                        return rowkey.toString();
                } else {
                        BitSet rowbits = getBits(level, row + 1, 0, (int) Math.pow(2, level));
                        BitSet colbits = getBits(level, col + 1, 0, (int) Math.pow(2, level));
                        for (int i = 0; i < level; i++) {
                                buffer.append(colbits.get(i) ? '1' : '0');
                                buffer.append(rowbits.get(i) ? '1' : '0');
                        }
                        rowkey.append(base32Encode(buffer.toString()));
                        //System.out.print(buffer.toString() + ",");
                }
        } else {
                rowkey.append('!');
                System.out.println("wrong row number or wrong col number!");
        }
        return rowkey.toString();
    }
    
    /**
     * Get Row key of tile
     * 
     * @param level:tile level
     * @param col: tile column
     * @param row: tile row
     * @return String
     */
    public static String getRowkey(String level, String row, String col) {
        return getRowkey(Integer.parseInt(level), Integer.parseInt(row), Integer.parseInt(col));
    }

    /**
     * Get row number from a path name
     * 
     * @param rowFileName:filename of a tile
     * @param level: tile level
     * @return String
     */
    public static String getRightRowNumber(File rowFileName, String level) {
        String row = rowFileName.getName().substring(0, rowFileName.getName().lastIndexOf("."));
        String realrow = String.valueOf(getRightRowNumber(Integer.parseInt(row), Integer.parseInt(level)));
        return realrow;
    }
    
    /**
     * Transform the row number
     * 
     * @param row: tile row
     * @param level: tile level
     * @return Integer
     */
    public static int getRightRowNumber(int row, int level) {
            int realrow = (int) Math.pow(2, level) - 1 - row;
            return realrow;
    }

    public static String decimalToBinary(String str) {
            int DecimalNum = Integer.valueOf(str).intValue();
            String BinaryString = Integer.toBinaryString(DecimalNum);
            return BinaryString;
    }

    public static String decimalToBinary(int num) {
            String BinaryString = Integer.toBinaryString(num);
            return BinaryString;
    }

    public static String binaryToDecimal(String binarySource) {
            BigInteger bi = new BigInteger(binarySource, 2); // 
            return bi.toString(); //
    }

    static public String base32Encode(String binary) {
            String modifiedBinary;
            if (binary.length() % 5 != 0) {
                    int numOfAppend = 5 - binary.length() % 5;
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < numOfAppend; i++) {
                            sb.append('0');
                    }
                    sb.append(binary);
                    modifiedBinary = sb.toString();
            } else {
                    modifiedBinary = binary;
            }

            StringBuilder buf = new StringBuilder();
            for (int i = 0; i < modifiedBinary.length(); i += 5) {
                    String tmp = modifiedBinary.substring(i, i + 5);
                    int base32Index = Integer.parseInt(new BigInteger(tmp, 2).toString());
                    buf.append(base32Chars.charAt(base32Index));
            }
            //System.out.println("origin: " + binary + ", base32: " + buf.toString());
            return buf.toString();
    }

}
