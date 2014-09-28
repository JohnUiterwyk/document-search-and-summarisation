package inforet.util;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by johnuiterwyk on 28/09/2014.
 */
public class ConsoleInput {

    private Scanner scanner = new Scanner(System.in);

    public String getString()
    {
        return scanner.nextLine();
    }

    /**
     * getIntRange returns a integer between the min and max provided
     * @param min is the minimum option
     * @param max is the maximum option
     * @return an integer between min and max inclusive
     */
    public int getIntRange(int min, int max)
    {
        return getIntRange(min, max, null);
    }
    public int getIntRange(int min, int max, String prompt)
    {
        int result = 0;
        boolean valid = false;
        do {
            if(prompt == null)
            {
                this.printInputRange(min, max);
            }else
            {
                System.out.println(prompt);
            }
            try
            {
                result = scanner.nextInt();
                if(result >= min && result <= max)
                {
                    valid = true;
                }else
                {
                    this.printInputError();
                }
            }catch (InputMismatchException ex)
            {
                this.printInputError();
            }
            this.clearBuffer();
        }while(!valid);
        return result;
    }
    /**
     * printInputRange prints a prompt for a number between the min and max
     * @param min
     * @param max
     */
    public void printInputRange(int min, int max)
    {
        System.out.println("Please enter a number from "+Integer.toString(min)+ " to "+Integer.toString(max)+":");
    }
    public void printInputError()
    {
        System.out.println("The provided input was not valid.");
    }
    /**
     * pause is a convenience function for "Press enter to continue" functionality
     */
    public void pause()
    {
        pause(null);
    }
    public void pause(String prompt)
    {
        if(prompt == null)
        {
            System.out.println("Press Enter to continue");
        }else
        {
            System.out.println(prompt);
        }
        scanner.nextLine();
    }
    /**
     * clear the buffer
     */
    public void clearBuffer()
    {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }
}
