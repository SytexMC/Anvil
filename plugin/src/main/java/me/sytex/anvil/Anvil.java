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

package me.sytex.anvil;

import java.util.Map;
import me.sytex.anvil.listeners.PlayerInteractListener;
import me.sytex.anvil.recipes.AnvilRecipes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class Anvil extends JavaPlugin {

  public static final Material REPAIR_INGREDIENT = Material.IRON_INGOT;
  public static final Material DAMAGE_INGREDIENT = Material.OBSIDIAN;

  public static final Map<Material, Material> REPAIR_MAP = Map.of(
      Material.DAMAGED_ANVIL, Material.CHIPPED_ANVIL,
      Material.CHIPPED_ANVIL, Material.ANVIL,
      Material.ANVIL, Material.ANVIL
  );

  public static final Map<Material, Material> DAMAGE_MAP = Map.of(
      Material.ANVIL, Material.CHIPPED_ANVIL,
      Material.CHIPPED_ANVIL, Material.DAMAGED_ANVIL,
      Material.DAMAGED_ANVIL, Material.DAMAGED_ANVIL
  );

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);

    AnvilRecipes.register(this);
  }
}
