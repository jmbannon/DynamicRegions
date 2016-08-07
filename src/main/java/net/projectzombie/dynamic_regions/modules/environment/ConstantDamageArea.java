/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.modules.environment;

import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 *
 * @author jb
 */
public abstract class ConstantDamageArea extends DamageArea
{

    public ConstantDamageArea(final String regionName,
                              final int frequencySeconds,
                              final String damageMessage,
                              final int damage) 
    {
        super(regionName, frequencySeconds, damageMessage, damage);
    }


    @Override public boolean executeModule(final World world,
                                           final Player player)
    {
        if (isDamageable(world, player))
            super.damagePlayer(world, player);
        return true;
    }
    
    @Override public abstract double damageModifier(final Player player);
    @Override public abstract boolean isDamageable(final World world,
                                                   final Player player);
}
