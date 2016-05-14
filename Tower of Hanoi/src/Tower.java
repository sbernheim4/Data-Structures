/**
 * This program asks the user for a number which represents the number of disks for the initial state of a three tower
 * tower of hanoi. The program then prints out instructions on what moves should be executed to end up with all the
 * disks on the right most tower in order of smallest to largest (diameter)
 **/

import java.util.Scanner;

public class Tower {

    public static void main(String[] args) {

        // scanner for user input from the console
        Scanner console = new Scanner(System.in);
        System.out.println("Enter the number of disks");

        int numOfDiscs = console.nextInt();

        // if the user enters a number less than 1, throw an error. Otherwise continue on with the program
        if (numOfDiscs < 1) {
            throw new IllegalArgumentException("You entered a number less than 1");
        } else {
            printInstructions('A', 'B', 'C', numOfDiscs);
        }
    }

    /**
     * This method is responsible for printing out instructions on what the user should move. It works by recursively
     * calling itself. The method takes three chars in addition to an int as input where each char represents one of the
     * three poles. The int represents the number of disks the user entered.
     * */
    public static void printInstructions(char poleOne, char poleTwo, char poleThree, int num) {

        // base case --> If there is only 1 disk, move it from the starting position to the end position or poleOne to
        // poleThree
        if (num == 1) {
            // if there is one disk left, move it from pole one to pole three
            System.out.println("Move disk from " + poleOne + " to " + poleThree);
        } else {
            // Otherwise call the method on itself but switching poleTwo and poleThree. This is because poleThree would
            // contain the disk with the largest diameter and so we can solve this problem treating poleThree as the middle
            // or temp pole while moving the disks to poleTwo as the goal because any disk can go on top of the largest
            // disk.
            printInstructions(poleOne, poleThree, poleTwo, num - 1);

            // Print statement telling the user what to move and where. poleOne represents the startPole and poleThree
            // the endPole which is why those two are used in the print statement.
            System.out.println("Move disk from " + poleOne + " to " + poleThree);

            // Make another recursive call this time switching poleOne and poleThree. This is because now all the
            // disks that were on poleTwo need to be moved to poleThree. poleTwo is no longer the temp pole but is the
            // start pole and the disks on poleTwo need to be moved to poleThree. poleOne thus gets treated as the
            // middle or temp pole.
            printInstructions(poleTwo, poleOne, poleThree, num - 1);
        }
    }
}