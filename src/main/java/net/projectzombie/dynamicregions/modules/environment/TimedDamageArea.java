/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamicregions.modules.environment;

import java.util.Random;
import net.projectzombie.dynamicregions.modules.Controller;
import net.projectzombie.dynamicregions.world.DRWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 *
 * @author jesse
 */
public abstract class TimedDamageArea extends DamageArea
{    
    private static final Random RAND = new Random();
    
    private final int durationMin;
    private final int durationMax;
    private final int damageFrequency;
    
    public TimedDamageArea(final String regionName,
                      final int executeFrequency,
                      final int durationMin,
                      final int durationMax,
                      final int damageFrequency,
                      final int damage,
                      final String damageMessage) 
    {
        super(regionName, executeFrequency, damageMessage, damage);
        this.durationMin = DRWorld.getTickAmount(durationMin);
        this.durationMax = DRWorld.getTickAmount(durationMax);
        this.damageFrequency = DRWorld.getTickAmount(damageFrequency);
    }
    
    @Override
    public boolean executeModule(final World world,
                                 final Player player)
    {
        if (!this.isRunning() && this.globallyInConditions(world))
        {
            this.setRunning(true);
            this.start(player);
            final int tickStart = 0;
            final int tickDuration = RAND.nextInt(durationMax - durationMin) + durationMin;
            
            this.scheduleDamageCheck(world, player, tickStart, tickDuration);
            return true;
        }
        else
            return false;
    }
    
    private void scheduleDamageCheck(final World world,
                                     final Player player,
                                     final int tickDelay,
                                     final int tickDuration)
    {
        final TimedDamageArea area = this;

        Bukkit.getLogger().info("scheduling damage check");
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Controller.getPlugin(), new Runnable()
        {
            @Override
            public void run()
            {
                if (area.canScheduleDamage(tickDelay, tickDuration, world))
                {
                    area.damagePlayer(world, player);
                    area.scheduleDamageCheck(world, player, tickDelay + damageFrequency, tickDuration);
                    Bukkit.getLogger().info("tick delay: " + tickDelay + " tickDuration: " + tickDuration);
                    
                } else
                {
                    area.end(player);
                    area.setRunning(false);
                }
            }
        }, tickDelay + damageFrequency);
    }
    
    private boolean canScheduleDamage(final int tickDelay,
                                      final int tickDuration,
                                      final World world)
    {
        return tickDelay <= tickDuration && this.globallyInConditions(world);
    }
    
    public boolean globallyInConditions(final World world) { return true; }
    
    public abstract void start(final Player player);
    public abstract void end(final Player player);
    public abstract boolean isRunning();
    public abstract void setRunning(final boolean isRunning);
    @Override public abstract double damageModifier(final Player player);
    @Override public abstract boolean isDamageable(final World world,
                                                   final Player player);
}
