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
