package de.max.adventofcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO Klasse kommentieren
 * 
 * @author rumforma
 * @version $Revision:$<br/>
 *          $Date:$<br/>
 *          $Author:$
 */
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

}
