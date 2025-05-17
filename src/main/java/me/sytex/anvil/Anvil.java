package me.sytex.anvil;

import static org.bukkit.Bukkit.getPluginManager;

import me.sytex.anvil.listeners.PlayerInteractListener;
import me.sytex.anvil.recipes.AnvilRecipes;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public final class Anvil extends JavaPlugin {

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
    getPluginManager().registerEvents(new PlayerInteractListener(), this);

    AnvilRecipes.register(this);
  }
}
