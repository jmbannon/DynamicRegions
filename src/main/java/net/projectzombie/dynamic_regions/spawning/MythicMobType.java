/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.spawning;

import net.projectzombie.dynamic_regions.utilities.Coordinate;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;

/**
 *
 * @author jbannon
 */
public enum MythicMobType
{
    TEST_ZOMBIE("D8_ZOMBIE");
    
    private final String mobID;
    
    MythicMobType(final String mobID)
    {
        this.mobID = mobID;
    }
    
    public String getID() { return this.mobID; }
    
    public void spawnMythicMob(final Block playerBlock,
                               final Coordinate offset)
    {
        final Block spawnBlock = offset.toBlockOffset(playerBlock);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), this.getSpawnMythicMobCommand(spawnBlock));
    }
    
    /**
     * Returns the command to spawn one MythicMob at the given block.
     * @param spawnBlock
     * @return 
     */
    private String getSpawnMythicMobCommand(final Block spawnBlock)
    {
        return "mm mobs spawn " + this.mobID + ":0 1 " + MythicMobType.toCoordinate(spawnBlock);
    }
    
    /**
     * Converts block to coordinate for SpawnMythicMobCommand.
     * @param spawnBlock
     * @return 
     */
    static private String toCoordinate(final Block spawnBlock)
    {
        return spawnBlock.getWorld().getName() 
                + "," + spawnBlock.getX() 
                + "," + spawnBlock.getY() 
                + "," + spawnBlock.getZ();
    }
}
