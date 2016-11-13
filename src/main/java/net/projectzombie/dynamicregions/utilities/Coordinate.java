/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamicregions.utilities;

import java.util.Arrays;
import java.util.LinkedList;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 *
 * @author Jesse Bannon (jmbannon@uw.edu)
 * A simple class to store 3 dimensional coordinates.
 * 
 */
public class Coordinate
{
    private final int x, y, z;
    
    /**
     * Creates a new 3d coordinate.
     * @param x
     * @param y
     * @param z 
     */
    public Coordinate(final int x,
                      final int y,
                      final int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
    public int getZ() { return z; }
    
    /**
     * Converts the coordinate to the block at the given location in world.
     * @param world
     * @return Block at that coordinate in world.
     */
    public Block toBlock(final World world)
    {
        return world.getBlockAt(x, y, z);
    }
    
    /**
     * Converts the coordinate to the block relative to the given block.
     * @param block
     * @return Block relative to the given block.
     */
    public Block toBlockOffset(final Block block)
    {
        return block.getRelative(x, y, z);
    }
    
    /**
     * @return Coordinate in "<x,y,z>" form. 
     */
    @Override
    public String toString()
    {
        return "<" + this.x + "," + this.y + "," + this.z + ">";
    }
    
    /**
     * @param cornerOne
     * @param cornerTwo
     * @return Returns array of coordinates that are within the rectangle formed from the two
     * corner coordinates.
     */
    public static Coordinate[] getRectangle(final Coordinate cornerOne,
                                            final Coordinate cornerTwo)
    {
        final int
          xMin = Math.min(cornerOne.getX(), cornerTwo.getX()),
          yMin = Math.min(cornerOne.getY(), cornerTwo.getY()),
          zMin = Math.min(cornerOne.getZ(), cornerTwo.getZ()),
          xMax = Math.max(cornerOne.getX(), cornerTwo.getX()),
          yMax = Math.max(cornerOne.getY(), cornerTwo.getY()),
          zMax = Math.max(cornerOne.getZ(), cornerTwo.getZ());
        
        int idx = 0;
        
        Coordinate rectangle[] = new Coordinate[(xMax-xMin+1)*(yMax-yMin+1)*(zMax-zMin+1)];
        for (int x = xMin; x <= xMax; x++)
            for (int y = yMin; y <= yMax; y++)
                for (int z = zMin; z <= zMax; z++)
                    rectangle[idx++] = new Coordinate(x, y, z);

        return rectangle;
    }
    
    public static LinkedList<Coordinate> toLinkedList(final Coordinate[] rectangle)
    {
        final LinkedList<Coordinate> rect = new LinkedList<>();
        rect.addAll(Arrays.asList(rectangle));
        return rect;
    }
}