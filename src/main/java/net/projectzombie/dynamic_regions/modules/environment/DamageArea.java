/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.modules.environment;

import net.projectzombie.dynamic_regions.modules.RegionModule;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 *
 * @author jb
 */
public abstract class DamageArea extends RegionModule
{
    protected static final String DAMAGE_AREA_TAG = ChatColor.DARK_GREEN + "[" + ChatColor.DARK_GREEN + "Environment" + ChatColor.DARK_GREEN + "] ";

    private final String damageMessage;
    private final int damage;
    
    public DamageArea(final String regionName,
                        final int frequencySeconds,
                        final String damageMessage,
                        final int damage)
    {
        super(regionName, frequencySeconds);
        this.damageMessage = DAMAGE_AREA_TAG + damageMessage;
        this.damage = damage;
    }

    @Override public abstract boolean executeModule(World world, Player player);
    
    public abstract boolean isDamageable(final World world,
                                         final Player player);
    
    public abstract double damageModifier(final Player player);
    
    public double getArmorRatio(final Player player,
                                final Material helmet,
                                final Material chest,
                                final Material legs,
                                final Material boots)
    {
        final PlayerInventory inv = player.getInventory();
        int damageModifier = 6;
        
        final ItemStack 
          playerHelmet = inv.getHelmet(),
          playerChest  = inv.getChestplate(),
          playerLegs   = inv.getLeggings(),
          playerBoots  = inv.getBoots();
        
        if(playerHelmet != null && playerHelmet.getType().equals(helmet))
            damageModifier -= 1;
        if(playerChest != null && playerChest.getType().equals(chest))
            damageModifier -= 2;
        if(playerLegs != null && playerLegs.getType().equals(legs))
            damageModifier -= 2;
        if(playerBoots != null && playerBoots.getType().equals(boots))
            damageModifier -= 1;
        
        return (double)damageModifier/6.0;
    }
    
    public void damagePlayer(final World world,
                              final Player player)
    {
        final double damageModifier = this.damageModifier(player);
        if (this.isDamageable(world, player) && damageModifier > 0)
        {
            player.sendMessage(damageMessage);
            player.damage(damage * damageModifier);
        }
    }
}
