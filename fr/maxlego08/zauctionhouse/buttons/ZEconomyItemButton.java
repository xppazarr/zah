/*    */ package fr.maxlego08.zauctionhouse.buttons;
/*    */ 
/*    */ import fr.maxlego08.menu.api.button.Button;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.PlayerCache;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.nms.NmsVersion;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import java.util.stream.Collectors;
/*    */ import net.md_5.bungee.api.ChatColor;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class ZEconomyItemButton
/*    */   extends Button {
/*    */   public ZEconomyItemButton(Plugin paramPlugin) {
/* 23 */     this.plugin = (ZAuctionPlugin)paramPlugin;
/*    */   }
/*    */   
/*    */   private final ZAuctionPlugin plugin;
/*    */   
/*    */   public ItemStack getCustomItemStack(Player paramPlayer) {
/* 29 */     ItemStack itemStack = paramPlayer.getItemInHand().clone();
/* 30 */     ItemMeta itemMeta = itemStack.getItemMeta();
/* 31 */     List list = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList();
/* 32 */     if (list == null) list = new ArrayList(); 
/* 33 */     list.addAll((Collection)getItemStack().getLore().stream().map(this::color).collect(Collectors.toList()));
/* 34 */     itemMeta.setLore(list);
/* 35 */     PlayerCache playerCache = this.plugin.getAuctionManager().getCache((OfflinePlayer)paramPlayer);
/* 36 */     itemStack.setAmount(playerCache.getSellAmount());
/* 37 */     itemStack.setItemMeta(itemMeta);
/* 38 */     return itemStack;
/*    */   }
/*    */   
/*    */   protected String color(String paramString) {
/* 42 */     if (paramString == null) return null; 
/* 43 */     if (NmsVersion.nmsVersion.isHexVersion()) {
/* 44 */       Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
/* 45 */       Matcher matcher = pattern.matcher(paramString);
/* 46 */       while (matcher.find()) {
/* 47 */         String str = paramString.substring(matcher.start(), matcher.end());
/* 48 */         paramString = paramString.replace(str, String.valueOf(ChatColor.of(str)));
/* 49 */         matcher = pattern.matcher(paramString);
/*    */       } 
/*    */     } 
/* 52 */     return ChatColor.translateAlternateColorCodes('&', paramString);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\buttons\ZEconomyItemButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */