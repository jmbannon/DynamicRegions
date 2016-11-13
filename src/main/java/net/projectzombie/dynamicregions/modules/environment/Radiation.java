/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamicregions.modules.environment;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 *
 * @author jb
 */
public class Radiation extends ConstantDamageArea {

    private static final int    RAD_AREA_DAMAGE = 1;    
    private static final String RAD_AREA_DAMAGE_MESSAGE
            = ChatColor.GRAY + "You are in a "
            + ChatColor.GOLD + "radiated " 
            + ChatColor.GRAY + "area. Wear a " 
            + ChatColor.RED + "hazmat suit "
            + ChatColor.GRAY + "to withstand the toxic chemicals.";

    public Radiation(final String regionName,
                     final int frequencySeconds)
    {
        super(regionName, frequencySeconds, RAD_AREA_DAMAGE_MESSAGE, RAD_AREA_DAMAGE);
    }

    @Override
    public double damageModifier(final Player player)
    {
        return getArmorRatio(player,
                             Material.GOLD_HELMET,
                             Material.GOLD_CHESTPLATE,
                             Material.GOLD_LEGGINGS,
                             Material.GOLD_BOOTS);
    }

    /**
     * @param world
     * @param player
     * @return Always true since radiation is in the entire region.
     */
    @Override
    public boolean isDamageable(final World world,
                                final Player player)
    {
        return true;
    }
    
}
