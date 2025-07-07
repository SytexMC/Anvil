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