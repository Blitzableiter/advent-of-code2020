package de.max.adventofcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils
{
  public static List<String> getListFromFilename(String fileName)
  {
    File file = new File("src/main/resources/" + fileName);
    try (

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);)
    {
      String line = br.readLine();
      List<String> list = new ArrayList<>();
      while (line != null)
      {
        list.add(line);
        line = br.readLine();
      }
      return list;
    }
    catch (IOException e)
    {
      e.printStackTrace();
      System.exit(0);
    }
    return null;

  }

  public static List<List<Character>> get2dCharListFromFilename(String fileName)
  {
    File file = new File("src/main/resources/" + fileName);
    try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr);)
    {
      String line = br.readLine();
      List<List<Character>> list = new ArrayList<>();
      while (line != null)
      {
        List<Character> row = new ArrayList<>();
        for (char seat : line.toCharArray())
          row.add(Character.valueOf(seat));

        list.add(row);
        line = br.readLine();
      }
      return list;
    }
    catch (IOException e)
    {
      e.printStackTrace();
      System.exit(0);
    }
    return null;
  }

  public static List<List<List<Character>>> get3dCharListFromFilename(String fileName)
  {
    File file = new File("src/main/resources/" + fileName);
    try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr);)
    {
      String line = br.readLine();
      List<List<List<Character>>> grid = new ArrayList<>();
      List<List<Character>> list = new ArrayList<>();
      while (line != null)
      {
        List<Character> row = new ArrayList<>();
        for (char field : line.toCharArray())
          row.add(Character.valueOf(field));

        list.add(row);
        line = br.readLine();
      }
      grid.add(list);
      return grid;
    }
    catch (IOException e)
    {
      e.printStackTrace();
      System.exit(0);
    }
    return null;
  }
}
