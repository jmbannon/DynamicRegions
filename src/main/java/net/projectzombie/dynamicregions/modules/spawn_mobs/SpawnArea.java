/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamicregions.modules.spawn_mobs;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import net.projectzombie.dynamicregions.modules.RegionModule;
import static net.projectzombie.dynamicregions.modules.spawn_mobs.CollisionOffsetTests.*;
import net.projectzombie.dynamicregions.spawning.MythicMobType;
import net.projectzombie.dynamicregions.utilities.Coordinate;
import net.projectzombie.dynamicregions.utilities.PlayerMethods;
import net.projectzombie.dynamicregions.world.DRWorld;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 *
 * @author jesse
 */
public abstract class SpawnArea extends RegionModule
{
    private final Coordinate spawnArea[];
    private final int spawnLimit;
    
    public SpawnArea(final String regionName,
                     final int frequency,
                     final int spawnCount,
                     final Coordinate[] spawnArea,
                     final MythicMobType[] mobTypes)
    {
        super(regionName, frequency);
        this.spawnArea = spawnArea;
        this.spawnLimit = spawnCount;
    }
    
    @Override
    public boolean executeModule(final World world,
                                 final Player player)
    {
        final Block playerBlock = player.getLocation().getBlock();
        final LinkedList<Coordinate> validSpawns = getValidSpawns(playerBlock);
        
        int spawnCount = Math.min(validSpawns.size(), this.spawnLimit);
        for (int i = 0; i < spawnCount; i++)
        {
            Coordinate toSpawn = validSpawns.removeFirst();
            MythicMobType.TEST_ZOMBIE.spawnMythicMob(playerBlock, toSpawn);
            DRWorld.getPlugin().getLogger().info("Spawning mob at " + toSpawn.toBlockOffset(playerBlock).toString());
        }
        return true;
    }
    
    public LinkedList<Coordinate> getValidSpawns(final Block playerBlock)
    {
        return this.getPlayerSafeSpawns(playerBlock, getCollisionSafeSpawns(playerBlock));
    }
    
    abstract public boolean isPlayerSafe(final Block playerBlock,
                                         final Player[] players,
                                         final Coordinate offset);
    
    
    /**
     * Checks rectangle from two given BlockOffset corners relative to playerLocation
     * for valid spawns.
     * 
     * @param playerBlock
     * @return LinkedList of valid spawns.
     */
    private LinkedList<Coordinate> getCollisionSafeSpawns(final Block playerBlock)
    {
        final LinkedList<Coordinate> validSpawns = new LinkedList<>();
        
        Block blockToTest;
        for (Coordinate coord : this.spawnArea)
        {
            blockToTest = coord.toBlockOffset(playerBlock);
            if (isCollisionSafe(blockToTest))
                validSpawns.add(coord);
        }
        return validSpawns;
    }
   
    
    private LinkedList<Coordinate> getPlayerSafeSpawns(final Block playerBlock,
                                                       final LinkedList<Coordinate> spawnArea)
    {
        final Player[] players = PlayerMethods.getOnlinePlayers();
        Iterator<Coordinate> iterator = spawnArea.iterator();
        while (iterator.hasNext())
            if (!isPlayerSafe(playerBlock, players, iterator.next()))
            {
                iterator.remove();
            }

        Collections.shuffle(spawnArea);
        return spawnArea;
    }
    
    private boolean isCollisionSafe(final Block spawnBlock)
    {
        return hasSolidGround(spawnBlock) && hasEmptySpace(spawnBlock);
    }
    
    private boolean hasSolidGround(final Block spawnBlock)
    {
        return !spawnBlock.getRelative(0, -2, 0).isEmpty();
    }
    
    private boolean hasEmptySpace(final Block spawnBlock)
    {
        return isEmpty(spawnBlock, COLLISION_RELATIVE_MAIN) &&
                  (isEmpty(spawnBlock, COLLITION_RELATIVE_TEST1)
                || isEmpty(spawnBlock, COLLITION_RELATIVE_TEST2)
                || isEmpty(spawnBlock, COLLITION_RELATIVE_TEST3)
                || isEmpty(spawnBlock, COLLITION_RELATIVE_TEST4));
    }
    
    private boolean isEmpty(final Block spawnBlock,
                           final Coordinate[] emptyOffsets)
    {
        for (Coordinate coord : emptyOffsets)
            if (!coord.toBlockOffset(spawnBlock).isEmpty())
                return false;
        return true;
    }
}
