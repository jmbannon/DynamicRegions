/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.world;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

/**
 * Parent class for modules to store their world and respective WorldGuard
 * region manager.
 *
 * @author Jesse Bannon (jmbannon@uw.edu)
 */
public class DRWorld
{
    private static Plugin PLUGIN;
    private static Plugin WG_PLUGIN;
    private static final int TICKS_PER_SECOND = 20;
    
    private final World world;
    private final RegionManager WGRegionManager;
    
    public DRWorld(final Plugin plugin,
                   final String worldName)
    {
        PLUGIN = plugin;
        WG_PLUGIN = getWorldGuard();
        
        this.world = Bukkit.getWorld(worldName);
        this.WGRegionManager = WGBukkit.getRegionManager(world);
    }
    
    public World         getWorld()         { return world; }
    public RegionManager getRegionManager() { return WGRegionManager; }
    
    static public Plugin getPlugin()      { return PLUGIN; }
    static public Plugin getWGPlugin()    { return WG_PLUGIN; }
    static public int    getTicksPerSec() { return TICKS_PER_SECOND; }
    
    /**
     * Converts seconds to ticks for task (process) scheduling.
     * @param seconds
     * @return 
     */
    static public int getTickAmount(final int seconds) 
    { 
        return seconds * TICKS_PER_SECOND; 
    }
    
    /**
     * Returns instance of the WorldGuard plugin if it exists.
     * @return 
     */
    static private WorldGuardPlugin getWorldGuard()
    {
        Plugin WGPlugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (WGPlugin == null || !(WGPlugin instanceof WorldGuardPlugin))
            return null;
        
        return (WorldGuardPlugin) WGPlugin;
    }
}
