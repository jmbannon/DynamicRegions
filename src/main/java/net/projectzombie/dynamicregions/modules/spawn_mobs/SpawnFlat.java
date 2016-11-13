/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamicregions.modules.spawn_mobs;

import net.projectzombie.dynamicregions.utilities.Coordinate;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 *
 * @author jbannon
 */
public class SpawnFlat extends SpawnArea
{
    static private final int 
      P_WIDTH   = 70,
      Z_WIDTH   = 20,
      Z_HEIGHT  = 5;
    
    public SpawnFlat(final String regionName,
                     final int frequency)
    {
        super(regionName, frequency, 12, getSpawnArea(), null);
    }
    
    
    @Override
    public boolean isPlayerSafe(final Block playerBlock,
                                final Player[] players,
                                final Coordinate offset)
    {
        final Block blockOffset = offset.toBlockOffset(playerBlock);
        final Location blockLocation = blockOffset.getLocation();
        Location playerLoc;
        
        for (Player player : players)
        {
            playerLoc = player.getLocation();
            if (Math.abs(playerLoc.getBlockY() - blockOffset.getY()) <= Z_HEIGHT
                    && playerLoc.distance(blockLocation) <= P_WIDTH)
                return false;
        }
        return true;
    }
    
    static private Coordinate[] getSpawnArea()
    {
        final Coordinate[] spawnArea;
        final Coordinate[][] rectanglesToAdd =
        {
          getHorizontalRectangle(true),
          getHorizontalRectangle(false),
          getVerticalRectangle(true),
          getVerticalRectangle(false)
        };

        int x = 0;
        for (Coordinate[] rect : rectanglesToAdd)
            x += rect.length;
        
        spawnArea = new Coordinate[x];
        x = 0;
        for (Coordinate[] rect : rectanglesToAdd)
            for (Coordinate coord : rect)
                spawnArea[x++] = coord;

        return spawnArea;
    }
    
    static private Coordinate[] getHorizontalRectangle(final boolean left)
    {
        final int m = left ? 1 : -1;
        final int widthSum = P_WIDTH + Z_WIDTH;
        
        return Coordinate.getRectangle(new Coordinate(m*widthSum, -2,       -widthSum),
                                       new Coordinate(m * P_WIDTH,  Z_HEIGHT, widthSum));
    }
    
    static private Coordinate[] getVerticalRectangle(final boolean top)
    {
        final int m = top ? 1 : -1;
        final int widthSum = P_WIDTH + Z_WIDTH;
        
        return Coordinate.getRectangle(new Coordinate(-P_WIDTH, -2,        m*P_WIDTH),
                                       new Coordinate( P_WIDTH, Z_HEIGHT, m*widthSum));

    }
    
}
