package xs.weapon;

import com.google.common.base.Preconditions;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xs.Main;
import xs.util.Mobs;

import java.util.*;


public class FreezeSword implements Listener {

    // 種族(race) 職業(pro) 職業等級(proLevel) 等級(level) 力量(STR) 韌性(TEN) 敏捷(DEX) 智慧(WIS) 技巧(SKI) 幸運(LUK)

    public ItemStack create(int race, int pro, int proLevel, int level, int material, int str, int ski, int luk) {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();
        tag.setBoolean("FreezeSword", true);
        nmsItem.setTag(tag);
        item = CraftItemStack.asBukkitCopy(nmsItem);
        ItemMeta meta = item.getItemMeta();
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 7, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
        meta.setDisplayName("§b凍結之劍");
        item.setItemMeta(meta);
        return item;
    }

    public static void spawnParticle(Location location, Particle particle, int amount) {
        Preconditions.checkArgument(amount > 0, "amount > 0");
        location.getWorld().spawnParticle(particle, location, amount);
    }

    Map<String, Long> list = new HashMap<>();
    //5sec = 5*20tick
    long coldDownTime = 5 * 20L;

    @EventHandler
    public void RightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        //is right click?
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        //is click by main hand and tool right?
        if (event.getHand() == EquipmentSlot.OFF_HAND || !player.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_SWORD)) {
            return;
        }
        if (list.containsKey(player.getName()) && !player.hasPermission("NoobTool.Admin.SkipCD.FreezeSword")) {
            //50 = 1000(millis)/20(ticks)
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "Tool CD Still Have " +
                    ((coldDownTime * 50 - (System.currentTimeMillis() - list.get(player.getName())) + 999) / 1000) + " Seconds Left!"));
            return;
        }


        if (CraftItemStack.asNMSCopy((player.getInventory().getItemInMainHand())).getTag().getBoolean("FreezeSword")) {
            spawnParticle(player.getLocation(), Particle.valueOf("END_ROD"), 10);
            Location loc = player.getLocation();
            Collection<Entity> near = loc.getWorld().getNearbyEntities(loc, 5, 3, 5);
            near.removeIf(i -> !(i instanceof Mob) || Mobs.isPassiveMob(i) || Mobs.isNeutralMob(i));
//            near.removeIf(i -> !(i instanceof Mob) && !(i instanceof Player));

            int count = near.size();
            if (count == 0) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "You Wasn't Freeze Any Entitys !"));
            } else {
//                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GOLD + "You Freezed " + ChatColor.RED + (count - 1) + " Entity(s) !"));
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GOLD + "You Freezed " + ChatColor.RED + (count) + " Entity(s) !"));
            }

            for (Entity nearEntity : near) {
                if (nearEntity.getName().equals(player.getName())) {
                    continue;
                }
                if (nearEntity instanceof Player) {
                    ((Player) nearEntity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 5));
                    ((Player) nearEntity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 60, 5));
                } else {
                    ((Mob) nearEntity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 5));
                    ((Mob) nearEntity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 60, 5));
                }
            }

            //save used time
            list.put(player.getName(), System.currentTimeMillis());
            //when coldDownTime pass
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), () -> list.remove(player.getName()), coldDownTime);
        }
    }
}