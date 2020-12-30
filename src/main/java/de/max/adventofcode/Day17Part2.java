package de.max.adventofcode;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Day17Part2
{

  public static List<List<Character>> input;

  public static void main(String[] args)
  {
    long start = System.currentTimeMillis();
    System.out.println("Starting at: " + LocalDateTime.now());

    input = Utils.get2dCharListFromFilename("input-day17.txt");

    part2();

    long end = System.currentTimeMillis();
    System.out.println("Finishing at: " + LocalDateTime.now());
    System.out.println("Runtime: " + (end - start) + "ms");
  }

  private static void part2()
  {
    System.out.println(input);

    // cubes in grid are active. If a cube is not in the grid it's not active
    Set<Cube> gridOfActives = new HashSet<>();

    for (int y = 0; y < input.size(); y++)
    {
      for (int x = 0; x < input.get(y)
          .size(); x++)
      {
        Character state = input.get(y)
            .get(x);
        if (state.equals('#'))
        {
          Cube newCube = new Cube(x, y, 0, 0);
          gridOfActives.add(newCube);
        }
      }
    }
    for (int i = 1; i <= 6; i++)
    {
      cycleGrid(gridOfActives);
    }
    System.out.println(gridOfActives);
    System.out.println("size: " + gridOfActives.size());
  }

  /**
   * @param gridAfter
   */
  private static void cycleGrid(Set<Cube> gridAfter)
  {
    Set<Cube> gridBefore = new HashSet<>(gridAfter);
    System.out.println("before: " + gridBefore);

    // cycle all active cubes
    for (Cube cube : gridBefore)
    {
      Integer activeNeighbours = getActiveNeighboursForCube(gridBefore, cube);
      // remove cube from grid if it has not exactly 2 or 3 active neighbours
      if (activeNeighbours != 2 && activeNeighbours != 3)
        gridAfter.remove(cube);
    }

    // get all inactive cubes adjacent to active cubes
    Set<Cube> inactiveNeighbours = getInactiveAdjacentCubes(gridBefore);
    // cycle all inactive cubes
    for (Cube inactiveCube : inactiveNeighbours)
    {
      Integer activeNeighbours = getActiveNeighboursForCube(gridBefore, inactiveCube);
      // if an inactive cube has 3 active neighbours, it becomes active itself
      if (activeNeighbours == 3)
        gridAfter.add(inactiveCube);
    }

    System.out.println("after : " + gridAfter);
  }

  /**
   * Returns a set of all cubes that are inactive in grid but are next to an active cube.
   * 
   * @param grid
   * @return
   */
  private static Set<Cube> getInactiveAdjacentCubes(Set<Cube> grid)
  {
    Set<Cube> inactiveNeighbours = new HashSet<>();
    for (Cube cube : grid)
    {
      for (int x = cube.x - 1; x <= cube.x + 1; x++)
      {
        for (int y = cube.y - 1; y <= cube.y + 1; y++)
        {
          for (int z = cube.z - 1; z <= cube.z + 1; z++)
          {
            for (int w = cube.w - 1; w <= cube.w + 1; w++)
            {
              // loop over all neighbouring cubes

              // do not analyze center cube
              if (x == cube.x && y == cube.y && z == cube.z && w == cube.w)
                continue;

              // if a neighbouring cube is not in the grid (ie is not active) add it to list of
              // inactive cubes
              Cube newCube = new Cube(x, y, z, w);
              if (!grid.contains(newCube))
                inactiveNeighbours.add(newCube);
            }
          }
        }
      }
    }

    return inactiveNeighbours;
  }

  public static Integer getActiveNeighboursForCube(Set<Cube> grid, Cube cube)
  {
    Integer activeNeighbours = 0;
    for (int x = cube.x - 1; x <= cube.x + 1; x++)
    {
      for (int y = cube.y - 1; y <= cube.y + 1; y++)
      {
        for (int z = cube.z - 1; z <= cube.z + 1; z++)
        {
          for (int w = cube.w - 1; w <= cube.w + 1; w++)
          {
            // do not analyze center cube
            if (x == cube.x && y == cube.y && z == cube.z && w == cube.w)
              continue;

            if (grid.contains(new Cube(x, y, z, w)))
              activeNeighbours++;
          }
        }
      }
    }
    return activeNeighbours;
  }

  private static class Cube
  {
    public Integer x;
    public Integer y;
    public Integer z;
    public Integer w;

    public Cube(Integer x, Integer y, Integer z, Integer w)
    {
      this.x = x;
      this.y = y;
      this.z = z;
      this.w = w;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
      return Objects.hash(w, x, y, z);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      Cube other = (Cube) obj;
      return Objects.equals(w, other.w) && Objects.equals(x, other.x) && Objects.equals(y, other.y)
          && Objects.equals(z, other.z);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
      return "Cube [x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + "]";
    }

  }

}
