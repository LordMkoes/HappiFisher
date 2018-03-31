package me.iloveani.vocan;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class FisherPlugin extends JavaPlugin implements Listener {

    private EatUtil eatUtil;

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        eatUtil = new EatUtil();
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    private void playerConsumeEvent(PlayerItemConsumeEvent playerItemConsumeEvent) {
            if (eatUtil.eatableItemsWithoutFish.contains(playerItemConsumeEvent.getItem())) {
                playerItemConsumeEvent.setCancelled(true);
                playerItemConsumeEvent.getPlayer().sendMessage("Niestety, ale na tym serwerze mozna jesc tylko ryby tak jak stary happiego!");
            if (playerItemConsumeEvent.getItem().getType() == Material.GOLDEN_APPLE) {
                playerItemConsumeEvent.getPlayer().setFoodLevel(playerItemConsumeEvent.getPlayer().getFoodLevel() - 2);
                playerItemConsumeEvent.getPlayer().sendMessage("Zjadles zlote jablko, ale nie uzupelnilo ono twojego glodu, bo na tym serwerze mozna jesc tylko ryby jak stary happiego!");
            }

            }
    }

    @EventHandler
    private void openChestInVillage(InventoryOpenEvent inventoryOpenEvent) {

        for (ItemStack itemstack : inventoryOpenEvent.getInventory().getContents()) {
            if (eatUtil.eatableItemsWithoutFish.contains(itemstack)) {
                itemstack.setAmount(-1);
                inventoryOpenEvent.getPlayer().sendMessage("Otworzyles inventory z jedzeniem ,tkorego nie mozna zdobyc! jego ilosc zostaje ustawiona na -1");
            }
        }

    }

    @EventHandler
    private void onBlockGrow(BlockGrowEvent blockGrowEvent) {
        blockGrowEvent.setCancelled(true);
    }

    @EventHandler
    private void onEntityDeath(EntityDeathEvent entityDeathEvent) {
        if (!(entityDeathEvent.getEntity().getType() == EntityType.PLAYER)) {
            entityDeathEvent.getDrops().clear();
        }
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent playerJoinEvent){
            playerJoinEvent.getPlayer().sendMessage("Pamietaj! Na naszym serwerze mozna zdobywac pozywienie tylko z lowienia jak stary happiego!");

            if (!playerJoinEvent.getPlayer().hasPlayedBefore()) {
                playerJoinEvent.getPlayer().getInventory().addItem(new ItemStack(Material.FISHING_ROD));
                playerJoinEvent.getPlayer().sendMessage("Dostales na start wedke, zebys nie musial juz jej krasc jak stary happiego");
            }
        }

}
