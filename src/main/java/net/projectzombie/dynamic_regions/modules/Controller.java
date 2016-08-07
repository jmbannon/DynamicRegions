/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.modules;

import com.sk89q.worldguard.protection.managers.RegionManager;
import net.projectzombie.dynamic_regions.modules.spawn_mobs.SpawnSkycraper;
import net.projectzombie.dynamic_regions.modules.spawn_mobs.SpawnFlat;
import java.util.ArrayList;
import java.util.logging.Level;
import net.projectzombie.dynamic_regions.modules.environment.ColdFront;
import net.projectzombie.dynamic_regions.modules.environment.FreezingWater;
import net.projectzombie.dynamic_regions.modules.environment.Radiation;
import net.projectzombie.dynamic_regions.utilities.PlayerTrail;
import net.projectzombie.dynamic_regions.world.DRWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author jesse
 */
public class Controller extends DRWorld
{
    private final ArrayList<RegionModule> DynamicRegions;
    
    
    public Controller(final Plugin plugin,
                      final String worldName)
    {
        super(plugin, worldName);
        this.scheduleTrail();
        
        DynamicRegions = new ArrayList<>();
        DynamicRegions.add(new SpawnFlat     ("city_base",        45));
        DynamicRegions.add(new SpawnSkycraper("city_skyscrapers", 20));
        DynamicRegions.add(new Radiation     ("minetech",         15));
        DynamicRegions.add(new FreezingWater ("__global__",       10));
        DynamicRegions.add(new ColdFront     ("__global__",       80, 1)); //900
        
        
        for (RegionModule region : DynamicRegions)
            scheduleExecution(region);
    }
    
    private void scheduleExecution(final RegionModule module)
    {
        final RegionManager WGRegionManager = this.getRegionManager();
        final World world = this.getWorld();
        
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Controller.getPlugin(), new Runnable()
        {
            @Override
            public void run()
            {
                Controller.getPlugin().getLogger().info("Executing module for " + module.getRegionName());
                module.executeForAllPlayers(world, WGRegionManager);
                scheduleExecution(module);
            }
        }, module.getFrequencyTicks());
    }
    
    private void scheduleTrail()
    {
        PlayerTrail.updateTrailList();
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Controller.getPlugin(), new Runnable()
        {
            @Override
            public void run()
            {
                PlayerTrail.updateTrailList();
            }
        }, PlayerTrail.getFrequencyTicks());
    }
    
    
    
}
