/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.modules.environment;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jb
 */
public class FreezingWater extends ConstantDamageArea 
{
    static private final int FREEZING_WATER_DAMAGE = 1;
    static private final String FREEZING_WATER_DAMAGE_MSG =
            ChatColor.GRAY + "Water temperatures are " +
            ChatColor.AQUA + "frigid" +
            ChatColor.GRAY + ". You need " +
            ChatColor.DARK_GREEN + "wetsuit armor " +
            ChatColor.GRAY + "to stay warm and a " +
            ChatColor.DARK_GREEN + "scuba mask " +
            ChatColor.GRAY + "to breathe underwater.";
    
    public FreezingWater(final String regionName,
                         final int frequencySeconds)
    {
        super(regionName, frequencySeconds, FREEZING_WATER_DAMAGE_MSG, FREEZING_WATER_DAMAGE);
    }
    
    @Override
    public double damageModifier(Player player)
    {
        return getArmorRatio(player,
                             Material.CHAINMAIL_HELMET,
                             Material.CHAINMAIL_CHESTPLATE,
                             Material.CHAINMAIL_LEGGINGS,
                             Material.CHAINMAIL_BOOTS);
    }

    @Override
    public boolean isDamageable(final World world,
                                final Player player)
    {
        final Block playerBlock = player.getEyeLocation().getBlock();
        final ItemStack helmet = player.getInventory().getHelmet();
        
        if (isWater(playerBlock) || isWater(playerBlock.getRelative(BlockFace.DOWN)))
        {
            if (helmet != null && helmet.getType().equals(Material.CHAINMAIL_HELMET))
                player.setRemainingAir(player.getMaximumAir());
            
            return true;
        }
        else
            return false;
    }
    
    private boolean isWater(final Block block)
    {
        final Material type = block.getType();
        return type.equals(Material.WATER) || type.equals(Material.STATIONARY_WATER);
    }
    
}
