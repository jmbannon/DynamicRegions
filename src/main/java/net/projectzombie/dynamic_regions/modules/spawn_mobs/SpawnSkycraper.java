/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.modules.spawn_mobs;

import net.projectzombie.dynamic_regions.utilities.Coordinate;
import net.projectzombie.dynamic_regions.utilities.PlayerTrail;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 *
 * @author jbannon
 */
public class SpawnSkycraper extends SpawnArea
{
    static private final int
      SPAWN_BASE   = 6,  // Bottom of spawn box relative to above the player.
      SPAWN_HEIGHT = 10, // Height of spawn box relative from SPAWN_BASE.
      SPAWN_WIDTH  = 20; // width of spawn box relative to the player.

    public SpawnSkycraper(final String regionName,
                          final int frequency)
    {
        super(regionName, frequency, 6, getSpawnArea(), null);
    }
    
    
    @Override
    public boolean isPlayerSafe(final Block playerBlock,
                                 final Player[] players,
                                 final Coordinate offset)
    { 
        Coordinate coord;
        for (Object coordObject : PlayerTrail.getTrail())
        {
            coord = (Coordinate)coordObject;
            if (inPlayerRange(playerBlock, coord.toBlock(playerBlock.getWorld())))
                return false;
        }
        
        for (Player player : players)
            if (inPlayerRange(playerBlock, player.getLocation().getBlock()))
                return false;
        
        return true;
    }
    
    static private Coordinate[] getSpawnArea()
    {
        final int verticalSum = SPAWN_BASE+SPAWN_HEIGHT;
        return Coordinate.getRectangle(new Coordinate(-SPAWN_WIDTH, SPAWN_BASE, -SPAWN_WIDTH),
                                       new Coordinate( SPAWN_WIDTH, verticalSum, SPAWN_WIDTH));
    }
    
    private boolean inPlayerRange(final Block playerBlock,
                                  final Block otherPlayerBlock)
    {
        
        return (Math.abs(otherPlayerBlock.getX() - playerBlock.getX()) <= SPAWN_WIDTH
                && Math.abs(otherPlayerBlock.getZ() - playerBlock.getZ()) <= SPAWN_WIDTH
                && (otherPlayerBlock.getY() - playerBlock.getY()) > SPAWN_BASE);
    }
}
