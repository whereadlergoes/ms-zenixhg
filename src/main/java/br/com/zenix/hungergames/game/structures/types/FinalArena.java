package br.com.zenix.hungergames.game.structures.types;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.zenix.core.spigot.options.ServerOptions;
import br.com.zenix.hungergames.game.structures.Structure;
import br.com.zenix.hungergames.manager.Manager;
import br.com.zenix.hungergames.player.gamer.Gamer;

/**
 * Copyright (C) Guilherme Fane, all rights reserved unauthorized copying of
 * this file, via any medium is strictly prohibited proprietary and confidential
 */

public class FinalArena extends Structure {

	public FinalArena(Manager manager, Location location) {
		super(manager, Bukkit.getWorld("arena").getSpawnLocation());
	}

	public void cleanPlayer() {
		int time = 0;

		ServerOptions.PVP.setActive(false);
		ServerOptions.GLOBAL_PVP.setActive(false);

		for (Player player : Bukkit.getServer().getOnlinePlayers()) {

			new BukkitRunnable() {
				public void run() {
					player.teleport(new Location(Bukkit.getWorld("arena"), 0, 7, 0));
				}
			}.runTaskLater(getManager().getPlugin(), time);

			time++;
		}

		ServerOptions.PVP.setActive(true);
		ServerOptions.GLOBAL_PVP.setActive(true);

		for (Gamer gamer : getManager().getGamerManager().getGamers().values()) {
			Player player = gamer.getPlayer();
			if (gamer.isAlive() && player.isOnline()) {
				player.closeInventory();
				for (Item i : player.getWorld().getEntitiesByClass(Item.class)) {
					i.remove();
				}
			}
		}
	}

}
