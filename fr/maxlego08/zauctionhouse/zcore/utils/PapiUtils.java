/*    */ package fr.maxlego08.zauctionhouse.zcore.utils;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.placeholder.Placeholder;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.nms.NmsVersion;
/*    */ import java.util.List;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import java.util.stream.Collectors;
/*    */ import net.md_5.bungee.api.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ public class PapiUtils
/*    */ {
/*    */   protected String color(String paramString) {
/* 17 */     if (paramString == null) return null; 
/* 18 */     if (NmsVersion.nmsVersion.isHexVersion()) {
/* 19 */       Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
/* 20 */       Matcher matcher = pattern.matcher(paramString);
/* 21 */       while (matcher.find()) {
/* 22 */         String str = paramString.substring(matcher.start(), matcher.end());
/* 23 */         paramString = paramString.replace(str, String.valueOf(ChatColor.of(str)));
/* 24 */         matcher = pattern.matcher(paramString);
/*    */       } 
/*    */     } 
/* 27 */     return ChatColor.translateAlternateColorCodes('&', paramString);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String colorReverse(String paramString) {
/* 32 */     if (paramString == null) return null;
/*    */     
/* 34 */     Pattern pattern = Pattern.compile("§x[a-fA-F0-9-§]{12}");
/* 35 */     Matcher matcher = pattern.matcher(paramString);
/* 36 */     while (matcher.find()) {
/* 37 */       String str1 = paramString.substring(matcher.start(), matcher.end());
/* 38 */       String str2 = str1.replace("§x", "#");
/* 39 */       str2 = str2.replace("§", "");
/* 40 */       paramString = paramString.replace(str1, str2);
/* 41 */       matcher = pattern.matcher(paramString);
/*    */     } 
/*    */     
/* 44 */     return paramString.replace("§", "&");
/*    */   }
/*    */   
/*    */   protected List<String> color(List<String> paramList) {
/* 48 */     return (List<String>)paramList.stream().map(this::color).collect(Collectors.toList());
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemStack papi(ItemStack paramItemStack, Player paramPlayer) {
/* 53 */     if (paramItemStack == null) {
/* 54 */       return paramItemStack;
/*    */     }
/*    */     
/* 57 */     ItemMeta itemMeta = paramItemStack.getItemMeta();
/*    */     
/* 59 */     if (itemMeta.hasDisplayName()) {
/* 60 */       itemMeta.setDisplayName(color(Placeholder.getPlaceholder().setPlaceholders(paramPlayer, itemMeta.getDisplayName())));
/*    */     }
/*    */     
/* 63 */     if (itemMeta.hasLore()) {
/* 64 */       itemMeta.setLore(color(Placeholder.getPlaceholder().setPlaceholders(paramPlayer, itemMeta.getLore())));
/*    */     }
/*    */     
/* 67 */     paramItemStack.setItemMeta(itemMeta);
/* 68 */     return paramItemStack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String papi(String paramString, Player paramPlayer) {
/* 78 */     return Placeholder.getPlaceholder().setPlaceholders(paramPlayer, paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> papi(List<String> paramList, Player paramPlayer) {
/* 89 */     return Placeholder.getPlaceholder().setPlaceholders(paramPlayer, paramList);
/*    */   }
/*    */   
/*    */   public static boolean isPlaceholder(String paramString) {
/* 93 */     return (Placeholder.containsPlaceholders(paramString) || Placeholder.containsBracketPlaceholders(paramString));
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\PapiUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */