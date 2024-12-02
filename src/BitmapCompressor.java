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
 *  @author Benjamin Chock
 */
public class BitmapCompressor {

    /**
     * Reads a sequence of bits from standard input, compresses them,
     * and writes the results to standard output.
     */
    public static void compress() {

        // TODO: complete compress()
        int numberZero = 0;
        int numberOne = 0;
        int bit;
        ArrayList<Integer> holder = new ArrayList<Integer>();
        bit = BinaryStdIn.readInt(1);
        //if first number isn't zero record it as no numbers
        if (bit != 0){
            holder.add(0);
        }
        //while their are numbers to be read in read them
        while (!BinaryStdIn.isEmpty()) {
            //if number is zero increase count of zeros in a row and end count of ones in a row
            if (bit == 0) {
                numberZero++;
                if (numberOne != 0) {
                    holder.add(numberOne);
                    numberOne = 0;
                }
            }
            //else increase number of ones and end count of zeros
            else {
                numberOne++;
                if (numberZero != 0) {
                    holder.add(numberZero);
                    numberZero = 0;
                }
            }
            bit = BinaryStdIn.readInt(1);
            }
        //write out leangth of all saved ints to avoid reading in the placer filler zeros
            BinaryStdOut.write(holder.size());
        //read in all the numbers as 8 bit and if the number is greater than 8 bits read it in as seperate 8 bit numbers
            for (int i = 0; i < holder.size(); i++) {
                while (holder.get(i) > 255) {
                    BinaryStdOut.write(255, 8);
                    BinaryStdOut.write(0, 1);
                    holder.set(i, holder.get(i)-255);
                }
                BinaryStdOut.write(holder.get(i), 8);
            }
            BinaryStdOut.close();

    }

    /**
     * Reads a sequence of bits from standard input, decodes it,
     * and writes the results to standard output.
     */
    public static void expand() {


        // TODO: complete expand()
        int numDidgets = 0;
        int size = BinaryStdIn.readInt();
        //for all the numbers print the number os zeros/ones they correspond two the order goes 0 the 1
        for (int i = 0; i < size; i++){
            numDidgets = BinaryStdIn.readInt(8);
            if (numDidgets == 0){

            }
            else if (i%2 == 0){
                for (int j = 0; j < numDidgets; j++){
                    BinaryStdOut.write(0,1);
                }
            }
            else{
                 for (int j = 0; j < numDidgets; j++){
                     BinaryStdOut.write(1,1);
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