/******************************************************************************
 *  Compilation:  javac BitmapCompressor.java
 *  Execution:    java BitmapCompressor - < input.bin   (compress)
 *  Execution:    java BitmapCompressor + < input.bin   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   q32x48.bin
 *                q64x96.bin
 *                mystery.bin
 *
 *  Compress or expand binary input from standard input.
 *
 *  % java DumpBinary 0 < mystery.bin
 *  8000 bits
 *
 *  % java BitmapCompressor - < mystery.bin | java DumpBinary 0
 *  1240 bits
 ******************************************************************************/

import java.util.ArrayList;

/**
 *  The {@code BitmapCompressor} class provides static methods for compressing
 *  and expanding a binary bitmap input.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Zach Blick
 *  @author YOUR NAME HERE
 */
public class BitmapCompressor {

    /**
     * Reads a sequence of bits from standard input, compresses them,
     * and writes the results to standard output.
     */
    public static void compress() {

        // TODO: complete compress()
        String s = BinaryStdIn.readString();
        int n = s.length();
        int numberZero = 0;
        int numberOne = 0;
        ArrayList<Integer> holder = new ArrayList<Integer>();

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == 0) {
                numberZero++;
                if (numberOne != 0){
                    holder.add(-1);
                    holder.add(numberOne);
                    numberOne = 0;
                }

            } else {
                numberOne++;
                if (numberZero != 0){
                    holder.add(-2);
                    holder.add(numberZero);
                    numberZero = 0;
                }
            }
        }
        BinaryStdOut.write(holder.size());
        for (int i = 0; i < holder.size(); i++){
            if (holder.get(i) == -1){
                BinaryStdOut.write(holder.get(i), 1);
            }
            else if ( holder.get(i) == -2){

            }
            else{
                BinaryStdOut.write(holder.get(i));
            }

        }
        BinaryStdOut.close();
    }

    /**
     * Reads a sequence of bits from standard input, decodes it,
     * and writes the results to standard output.
     */
    public static void expand() {

        // TODO: complete expand()
        int numZeros = 0;
        int numOnes = 0;
        int curNum = 0;
        int size = BinaryStdIn.readInt();
        for (int i = 0; i < size; i++){
            curNum = BinaryStdIn.readInt(1);
            if (curNum == 1){
                numOnes = BinaryStdIn.readInt();
                for (int j = 0; j < numOnes; j++){
                    BinaryStdOut.write(1,1);
                }
            }
            else{
                numZeros = BinaryStdIn.readInt();
                for (int j = 0; j < numZeros; j++){
                    BinaryStdOut.write(0,1);
                }
            }

        }
        BinaryStdOut.close();
    }

    /**
     * When executed at the command-line, run {@code compress()} if the command-line
     * argument is "-" and {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}