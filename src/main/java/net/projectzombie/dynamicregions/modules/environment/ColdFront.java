/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamicregions.modules.environment;

import net.projectzombie.dynamicregions.utilities.Coordinate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 *
 * @author jesse
 */
public class ColdFront extends TimedDamageArea
{
    private static final String COLD_FRONT_START_TITLE    = " title {text:\"Cold Front\",color:\"aqua\",bold:true,italic:true}";
    private static final String COLD_FRONT_START_SUBTITLE = " subtitle {text:\"Use \",color:\"gold\",extra:[{text:\"fur armor \",color:\"red\",bold:true,italic:true},{text:\"and \",color:\"gold\"},{text:\"fire \",color:\"red\",bold:true,italic:true},{text:\"to stay warm.\",color:\"gold\"}]}";
    private static final String COLD_FRONT_END_TITLE      = " title {text:\"Cold Front \",color:\"aqua\",bold:true,extra:[{text:\"Ended\",color:\"gold\"}]}";
    
    private static final int DURATION_MIN     = 20; //120
    private static final int DURATION_MAX     = 30; //270
    private static final int DAMAGE_FREQUENCY = 5;  //30
    private static final int DAMAGE           = 2;    
    private static final String DAMAGE_MESSAGE
            = ChatColor.GRAY + "You are "
            + ChatColor.AQUA + "freezing" 
            + ChatColor.GRAY + ". Wear " 
            + ChatColor.RED + "fur armor "
            + ChatColor.GRAY + "or stand near a "
            + ChatColor.RED + "fire "
            + ChatColor.GRAY + "to stay warm.";
    
    static boolean IS_RUNNING = false;
    
    private final int yValue;
    
    public ColdFront(final String regionName, 
                     final int executeFrequencySeconds,
                     final int yValue)
    {
        super(regionName,
              executeFrequencySeconds,
              DURATION_MIN,
              DURATION_MAX,
              DAMAGE_FREQUENCY,
              DAMAGE,
              DAMAGE_MESSAGE);
        
        this.yValue = yValue;
    }
    
    @Override
    public boolean globallyInConditions(final World world)
    { 
        return world.hasStorm();
    }

    @Override
    public boolean isDamageable(final World world,
                                final Player player)
    {
        return player.getLocation().getBlockY() > yValue && !this.isNearFire(player);
    }

    @Override
    public double damageModifier(Player player) {
        return getArmorRatio(player,
                             Material.LEATHER_HELMET,
                             Material.LEATHER_CHESTPLATE,
                             Material.LEATHER_LEGGINGS,
                             Material.LEATHER_BOOTS);
    }

    @Override
    public boolean isRunning()
    {
        return IS_RUNNING;
    }

    @Override
    public void setRunning(boolean isRunning) {
        IS_RUNNING = isRunning;
    }
    
    private boolean isNearFire(final Player player)
    {
        final Coordinate[] toCheck = Coordinate.getRectangle(new Coordinate(-6, -9, -6), new Coordinate(6, 3, 6));
        final Block playerBlock = player.getLocation().getBlock();
        
        for (Coordinate offset : toCheck)
            if (this.isWarm(offset.toBlockOffset(playerBlock)))
                return true;
        
        return false;
    }
    
    private boolean isWarm(final Block block)
    {
        final Material type = block.getType();
        return type.equals(Material.FIRE)
                || type.equals(Material.LAVA)
                || type.equals(Material.BURNING_FURNACE);
    }

    @Override
    public void start(final Player player)
    {
        final String cmdBegin = "title " + player.getName();
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmdBegin + COLD_FRONT_START_SUBTITLE);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmdBegin + COLD_FRONT_START_TITLE);
    }

    @Override
    public void end(final Player player)
    {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() + COLD_FRONT_END_TITLE);
    }
}
