/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamicregions.utilities;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import net.projectzombie.dynamicregions.world.DRWorld;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 *
 * @author Jesse Bannon (jmbannon@uw.edu)
 * 
 * Stores player's locations every WAIT_DURATION (seconds) PLAYER_RECORD_COUNT times
 * to the synchronized LinkedList trail.
 */
public class PlayerTrail
{
    
    static final private List<Object> trail =  Collections.synchronizedList(new LinkedList<>());
    static final private int 
        PLAYER_RECORD_COUNT = 20, // Coordinates to store while player is online.
        WAIT_DURATION       = 30; // Duration inbetween coordinate saves.
    
        
    static public List<Object> getTrail()          { return trail; }
    static public int          getFrequencyTicks() { return DRWorld.getTickAmount(WAIT_DURATION); }
    
    /**
     * Stores every player's coordinate location to the LinkedList trail.
     */
    static public void updateTrailList()
    {
        final Player[] players = PlayerMethods.getOnlinePlayers();
        flushTrailList(players.length);
        
        Block playerBlock;
        for (Player player : players)
        {
            playerBlock = player.getLocation().getBlock();
            trail.add(new Coordinate(playerBlock.getX(), playerBlock.getY(), playerBlock.getZ()));
        }
    }
    
    /**
     * Assures the trail LinkedList does not exceed a certain capacity based on
     * player count. Pops first player coordinates first
     * @param playerCount 
     */
    static private void flushTrailList(final int playerCount)
    {
        final int trailLimit = playerCount * PLAYER_RECORD_COUNT;
        while (trail.size() > trailLimit)
            trail.remove(0);
    }
    
}
