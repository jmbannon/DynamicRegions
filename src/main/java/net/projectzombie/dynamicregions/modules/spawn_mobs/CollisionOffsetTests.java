/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamicregions.modules.spawn_mobs;

import net.projectzombie.dynamicregions.utilities.Coordinate;

/**
 *
 * @author jesse
 */
public class CollisionOffsetTests
{
    static protected final Coordinate
      // O O O
      // O O O
      // O O O at offset y = 0
      COLLISION_RELATIVE_MAIN[] = new Coordinate[]
      {
        new Coordinate(-1, 0, 1),  new Coordinate(0, 0, 1),  new Coordinate(1, 0, 1),
        new Coordinate(-1, 0, 0),  new Coordinate(0, 0, 0),  new Coordinate(1, 0, 0),
        new Coordinate(-1, 0, -1), new Coordinate(0, 0, -1), new Coordinate(1, 0, -1)
      },
      // O O X
      // O O X
      // X X X at offset y = -1
      COLLITION_RELATIVE_TEST1[] = new Coordinate[]
      {
        new Coordinate(-1, -1, 1), new Coordinate(0, -1, 1),
        new Coordinate(-1, -1, 0), new Coordinate(0, -1, 0)
      },
      // X O O
      // X O O
      // X X X at offset y = -1
      COLLITION_RELATIVE_TEST2[] = new Coordinate[]
      {
        new Coordinate(0, -1, 1), new Coordinate(1, -1, 1),
        new Coordinate(0, -1, 0), new Coordinate(1, -1, 0) 
      },
      // X X X
      // O O X
      // O O X at offset y = -1
      COLLITION_RELATIVE_TEST3[] = new Coordinate[]
      {
        new Coordinate(-1, -1, 0),  new Coordinate(0, -1, 0),
        new Coordinate(-1, -1, -1), new Coordinate(0, -1, -1)
      },
      // X X X
      // X O O
      // X O O at offset y = -1
      COLLITION_RELATIVE_TEST4[] = new Coordinate[]
      {
        new Coordinate(0, -1, 0),  new Coordinate(1, -1, 0),
        new Coordinate(0, -1, -1), new Coordinate(1, -1, -1)
      };
    
}
