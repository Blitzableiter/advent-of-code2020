package de.max.adventofcode;

import java.time.LocalDateTime;
import java.util.List;

public class Day3
{

  public static void main(String[] args)
  {
    part1();
    // part2();
  }

  private static void part1()
  {
    long start = System.currentTimeMillis();
    System.out.println("Starting at: " + LocalDateTime.now());
    List<String> topology = Utils.getListFromFilename("input-day3.txt");

    char[][] topAsChar = new char[topology.get(0)
        .length()][topology.size()];
    // .........#..##..#..#........#..
    // #...#..#..#...##.....##.##.#...
    // ....#..............#....#....#.
    // #.#..#.....#...#.##..#.#.#.....
    // ........#..#.#..#.......#......
    // .#........#.#..###.#....#.#.#..
    // ........#........#.......#.....
    // ...##..#.#.#........##.........

    int countTrees11 = traverseBy(topology, 1, 1);
    int countTrees31 = traverseBy(topology, 3, 1);
    int countTrees51 = traverseBy(topology, 5, 1);
    int countTrees71 = traverseBy(topology, 7, 1);
    int countTrees12 = traverseBy(topology, 1, 2);

    System.out.println("Right 1, down 1. Trees: " + countTrees11);
    System.out.println("Right 3, down 1. Trees: " + countTrees31);
    System.out.println("Right 5, down 1. Trees: " + countTrees51);
    System.out.println("Right 7, down 1. Trees: " + countTrees71);
    System.out.println("Right 1, down 2. Trees: " + countTrees12);

    Long product = (long) countTrees11 * countTrees31 * countTrees51 * countTrees71 * countTrees12;

    System.out.println("Product: " + product);

    long end = System.currentTimeMillis();
    System.out.println("Finishing at: " + LocalDateTime.now());
    System.out.println("Runtime: " + (end - start) + "ms");

  }

  /**
   * @param topology
   * @return
   */
  private static int traverseBy(List<String> topology, int right, int down)
  {
    int countTrees = 0;
    int x = 0;
    int countDown = 0;

    // Wenn down > 1 wird nicht in der ersten Reihe begonnen
    if (down > 1)
      x = right;

    if (topology.get(0)
        .toCharArray()[0] == '#')
      countTrees++;

    for (String row : topology)
    {
      if (down > 1 && down > countDown)
      {
        countDown++;
        continue;
      }
      countDown = 1;

      char[] rowAsChar = row.toCharArray();

      if (x >= row.length())
        x -= row.length();

      if (rowAsChar[x] == '#')
        countTrees++;

      x += right;
    }
    return countTrees;
  }

  private static void part2()
  {
    List<String> list = Utils.getListFromFilename("input-day3.txt");

  }

}
