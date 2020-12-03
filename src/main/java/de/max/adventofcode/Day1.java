package de.max.adventofcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO Klasse kommentieren
 * 
 * @author rumforma
 * @version $Revision:$<br/>
 *          $Date:$<br/>
 *          $Author:$
 */
public class Day1
{
  public static void main(String[] args) throws IOException
  {
    // part1();
    // part2();
    part2BruteForce();
  }

  private static void part1() throws FileNotFoundException, IOException
  {
    File file = new File("src/main/resources/input-day1.txt");
    FileReader fr = new FileReader(file);
    BufferedReader br = new BufferedReader(fr);
    String line = br.readLine();
    List<Integer> list = new ArrayList<>();
    while (line != null)
    {
      Integer number = Integer.valueOf(line);
      list.add(number);
      line = br.readLine();
    }
    Collections.sort(list);
    for (Integer l : list)
      System.out.println(l);

    int startIndex = 0;
    int endIndex = list.size() - 1;
    Integer sum = list.get(startIndex) + list.get(endIndex);

    while (sum != 2020)
    {
      System.out.println(sum);
      if (sum > 2020)
      {
        endIndex--;
      }
      else if (sum < 2020)
      {
        startIndex++;
      }
      sum = list.get(startIndex) + list.get(endIndex);
    }
    System.out.println("Faktoren: " + list.get(startIndex) + " und " + list.get(endIndex));
    System.out.println("Produkt: " + list.get(startIndex) * list.get(endIndex));
  }

  private static void part2() throws FileNotFoundException, IOException
  {
    File file = new File("src/main/resources/input-day1.txt");
    FileReader fr = new FileReader(file);
    BufferedReader br = new BufferedReader(fr);
    String line = br.readLine();
    List<Integer> list = new ArrayList<>();
    while (line != null)
    {
      Integer number = Integer.valueOf(line);
      list.add(number);
      line = br.readLine();
    }
    Collections.sort(list);

    int startIndex = 0;
    int endIndex = list.size() - 1;
    int midIndex = startIndex + (endIndex - startIndex) / 2;

    Integer sum = list.get(startIndex) + list.get(midIndex) + list.get(endIndex);
    while (sum != 2020)
    {
      midIndex = startIndex + (endIndex - startIndex) / 2;
      boolean lastActionPlus = false;
      int index = 0;
      System.out.println(sum);
      while (true)
      {
        System.out.println("Indizes: " + startIndex + ", " + midIndex + ", " + endIndex);
        System.out.println("Faktoren: " + list.get(startIndex) + ", " + list.get(midIndex) + " und "
            + list.get(endIndex));
        if (endIndex == 119)
          System.exit(0);
        sum = list.get(startIndex) + list.get(midIndex) + list.get(endIndex);
        if (sum > 2020)
        {
          if (lastActionPlus && index != 0)
            break;

          midIndex--;
          if (midIndex <= startIndex)
            break;

          lastActionPlus = false;
        }
        else if (sum < 2020)
        {
          if (!lastActionPlus && index != 0)
            break;

          midIndex++;
          if (midIndex >= endIndex)
            break;

          lastActionPlus = true;
        }
        index++;
      }

      System.out.println("summe: " + sum);

      if (sum > 2020)
      {
        endIndex--;
      }
      else if (sum < 2020)
      {
        startIndex++;
      }

      sum = list.get(startIndex) + list.get(midIndex) + list.get(endIndex);
    }
    System.out.println("Faktoren: " + list.get(startIndex) + ", " + list.get(midIndex) + " und "
        + list.get(endIndex));
    System.out
        .println("Produkt: " + list.get(startIndex) * list.get(midIndex) * list.get(endIndex));
  }

  private static void part2BruteForce() throws IOException
  {
    File file = new File("src/main/resources/input-day1.txt");
    FileReader fr = new FileReader(file);
    BufferedReader br = new BufferedReader(fr);
    String line = br.readLine();
    List<Integer> list = new ArrayList<>();
    while (line != null)
    {
      Integer number = Integer.valueOf(line);
      list.add(number);
      line = br.readLine();
    }
    Collections.sort(list);

    Integer sum = list.get(0) + list.get(1) + list.get(2);
    for (int startIndex = 0; startIndex < list.size(); startIndex++)
    {
      for (int midIndex = startIndex + 1; midIndex < list.size(); midIndex++)
      {
        for (int endIndex = midIndex + 1; endIndex < list.size(); endIndex++)
        {
          sum = list.get(startIndex) + list.get(midIndex) + list.get(endIndex);
          if (sum == 2020)
          {
            System.out.println("Faktoren: " + list.get(startIndex) + ", " + list.get(midIndex)
                + " und " + list.get(endIndex));
            System.out.println("Indizes: " + startIndex + ", " + midIndex + ", " + endIndex);
            System.out.println(
                "Produkt: " + list.get(startIndex) * list.get(midIndex) * list.get(endIndex));
            break;
          }
        }
        if (sum == 2020)
          break;
      }
      if (sum == 2020)
        break;
    }
  }

}
