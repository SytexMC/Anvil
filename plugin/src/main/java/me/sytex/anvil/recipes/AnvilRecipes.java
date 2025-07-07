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

package me.sytex.anvil.recipes;

import static me.sytex.anvil.Anvil.DAMAGE_INGREDIENT;
import static me.sytex.anvil.Anvil.DAMAGE_MAP;
import static me.sytex.anvil.Anvil.REPAIR_INGREDIENT;
import static me.sytex.anvil.Anvil.REPAIR_MAP;

import java.util.Map;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class AnvilRecipes {
  public static void register(@NotNull Plugin plugin) {
    register(plugin, REPAIR_MAP, REPAIR_INGREDIENT, "repair_");
    register(plugin, DAMAGE_MAP, DAMAGE_INGREDIENT, "damage_");
  }

  private static void register(
      @NotNull Plugin plugin,
      @NotNull Map<Material, Material> recipeMap,
      @NotNull Material ingredient,
      @NotNull String prefix
  ) {
    for (Map.Entry<Material, Material> entry : recipeMap.entrySet()) {
      Material fromAnvil = entry.getKey();
      Material toAnvil = entry.getValue();

      if (fromAnvil == toAnvil) continue;

      String recipeId = prefix + fromAnvil.name().toLowerCase();
      NamespacedKey key = new NamespacedKey(plugin, recipeId);

      ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(toAnvil, 1));
      recipe.addIngredient(new RecipeChoice.MaterialChoice(fromAnvil));
      recipe.addIngredient(ingredient);

      try {
        plugin.getServer().addRecipe(recipe);
      } catch (IllegalArgumentException e) {
        plugin.getComponentLogger().warn(Component.text("Failed to register recipe: " + recipeId + " - " + e.getMessage()));
      }
    }
  }
}
