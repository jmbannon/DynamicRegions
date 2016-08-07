/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.main;

import net.projectzombie.dynamic_regions.modules.Controller;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author jesse
 */
public class Main extends JavaPlugin
{
    private Controller PZController;
    
    @Override
    public void onEnable()
    {
        PZController = new Controller(this, "PZRebornNew");
        this.getLogger().info("DynamicRegions enabled!");
        
        /*
        TODO:
        
        SksycraperSpawns
        Multithreading (p/player)
        HARDCORE: Machine Learning spawn offsets
        
        */
    }

    @Override
    public void onDisable()
    {
        this.getLogger().info("DynamicRegions disabled!");
    }
}
