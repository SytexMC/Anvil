/*
 * This file is part of Anvil, licensed under GPL v3.
 *
 * Copyright (c) 2025 Sytex <sytex@duck.com>
 * Copyright (c) contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.sytex.anvil.listeners;

import java.util.Set;
import me.sytex.anvil.Anvil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PlayerInteractListener implements Listener {

  private static final Set<Material> ANVIL_TYPES = Set.of(
      Material.ANVIL, Material.CHIPPED_ANVIL, Material.DAMAGED_ANVIL
  );

  @EventHandler
  public void onPlayerInteract(@NotNull PlayerInteractEvent event) {
    if (event.getAction() != Action.RIGHT_CLICK_BLOCK || !event.getPlayer().isSneaking()) return;

    Block block = event.getClickedBlock();
    if (block == null) return;

    Material anvilMaterial = block.getType();

    if (!ANVIL_TYPES.contains(anvilMaterial)) return;

    Player player = event.getPlayer();
    ItemStack itemStack = player.getInventory().getItemInMainHand();
    Material itemMaterial = itemStack.getType();

    if (itemMaterial == Anvil.REPAIR_INGREDIENT) {
      apply(block, player, itemStack, Anvil.REPAIR_MAP.get(block.getType()));
      event.setCancelled(true);
    } else if (itemMaterial == Anvil.DAMAGE_INGREDIENT) {
      apply(block, player, itemStack, Anvil.DAMAGE_MAP.get(block.getType()));
      event.setCancelled(true);
    }
  }

  private void apply(
      @NotNull Block block,
      @NotNull Player player,
      @NotNull ItemStack item,
      @NotNull Material newMaterial
  ) {
    if (block.getType() == newMaterial) return;

    Directional directional = (Directional) block.getBlockData();
    var facing = directional.getFacing();

    block.setType(newMaterial);

    directional = (Directional) block.getBlockData();

    directional.setFacing(facing);
    block.setBlockData(directional);

    if (player.getGameMode() != GameMode.CREATIVE) {
      item.setAmount(item.getAmount() - 1);
    }
  }
}