/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.utilities;

import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author jbannon
 */
public class PlayerMethods
{
    /**
     * @return Returns array of online players.
     */
    public static Player[] getOnlinePlayers()
    {
        final Collection<? extends Player> currentlyOnline = Bukkit.getServer().getOnlinePlayers();
        final Player players[] = new Player[currentlyOnline.size()];
        
        int idx = 0;
        for (Player player : currentlyOnline)
            players[idx++] = player;
        
        return players;

    }
}
