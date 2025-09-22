/*     */ package fr.maxlego08.zauctionhouse.zmenu;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Priority;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.nms.NmsVersion;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.kyori.adventure.text.Component;
/*     */ import net.kyori.adventure.text.format.TextDecoration;
/*     */ import net.kyori.adventure.text.minimessage.MiniMessage;
/*     */ import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
/*     */ import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ public class MiniMessageHelper
/*     */   extends ZUtils
/*     */ {
/*  32 */   private final MiniMessage MINI_MESSAGE = MiniMessage.builder().tags(TagResolver.builder().resolver(StandardTags.defaults()).build()).build();
/*  33 */   private final Map<String, String> COLORS_MAPPINGS = new HashMap<>();
/*     */   private final ZAuctionPlugin plugin;
/*     */   
/*     */   public MiniMessageHelper(ZAuctionPlugin paramZAuctionPlugin) {
/*  37 */     this.plugin = paramZAuctionPlugin;
/*  38 */     this.COLORS_MAPPINGS.put("0", "black");
/*  39 */     this.COLORS_MAPPINGS.put("1", "dark_blue");
/*  40 */     this.COLORS_MAPPINGS.put("2", "dark_green");
/*  41 */     this.COLORS_MAPPINGS.put("3", "dark_aqua");
/*  42 */     this.COLORS_MAPPINGS.put("4", "dark_red");
/*  43 */     this.COLORS_MAPPINGS.put("5", "dark_purple");
/*  44 */     this.COLORS_MAPPINGS.put("6", "gold");
/*  45 */     this.COLORS_MAPPINGS.put("7", "gray");
/*  46 */     this.COLORS_MAPPINGS.put("8", "dark_gray");
/*  47 */     this.COLORS_MAPPINGS.put("9", "blue");
/*  48 */     this.COLORS_MAPPINGS.put("a", "green");
/*  49 */     this.COLORS_MAPPINGS.put("b", "aqua");
/*  50 */     this.COLORS_MAPPINGS.put("c", "red");
/*  51 */     this.COLORS_MAPPINGS.put("d", "light_purple");
/*  52 */     this.COLORS_MAPPINGS.put("e", "yellow");
/*  53 */     this.COLORS_MAPPINGS.put("f", "white");
/*  54 */     this.COLORS_MAPPINGS.put("k", "obfuscated");
/*  55 */     this.COLORS_MAPPINGS.put("l", "bold");
/*  56 */     this.COLORS_MAPPINGS.put("m", "strikethrough");
/*  57 */     this.COLORS_MAPPINGS.put("n", "underlined");
/*  58 */     this.COLORS_MAPPINGS.put("o", "italic");
/*  59 */     this.COLORS_MAPPINGS.put("r", "reset");
/*     */   }
/*     */ 
/*     */   
/*     */   public void createItem(ItemStack paramItemStack, Player paramPlayer, Message paramMessage, AuctionItem paramAuctionItem) {
/*  64 */     ItemMeta itemMeta = paramItemStack.getItemMeta();
/*  65 */     List<Component> list = itemMeta.hasLore() ? itemMeta.lore() : new ArrayList();
/*  66 */     if (list == null) list = new ArrayList();
/*     */     
/*  68 */     String str = itemMeta.hasCustomModelData() ? String.valueOf(itemMeta.getCustomModelData()) : paramItemStack.getType().name();
/*     */     
/*  70 */     for (String str1 : paramMessage.getMessages()) {
/*  71 */       str1 = str1.replace("%item_type%", str);
/*  72 */       str1 = str1.replace("%economy_name%", paramAuctionItem.getEconomyName());
/*  73 */       str1 = str1.replace("%time%", paramAuctionItem.timeFormat());
/*  74 */       str1 = str1.replace("%price%", paramAuctionItem.priceFormat());
/*  75 */       str1 = str1.replace("%status%", paramAuctionItem.statusFormat(paramPlayer.getUniqueId()));
/*  76 */       str1 = str1.replace("%seller%", paramAuctionItem.getSellerName());
/*  77 */       str1 = str1.replace("%priority%", String.valueOf(paramAuctionItem.getPriority()));
/*     */       
/*  79 */       Optional<Priority> optional = this.plugin.getAuctionManager().getPriority(paramAuctionItem.getPriority());
/*  80 */       str1 = str1.replace("%priority_text%", optional.isPresent() ? color(((Priority)optional.get()).getText()) : Message.PRIORITY.msg());
/*     */       
/*  82 */       if (paramAuctionItem.getBuyerUniqueId() != null) {
/*  83 */         OfflinePlayer offlinePlayer = paramAuctionItem.getBuyer();
/*  84 */         str1 = str1.replace("%buyer%", (offlinePlayer == null) ? paramAuctionItem.getBuyerUniqueId().toString() : Objects.<CharSequence>requireNonNull(offlinePlayer.getName()));
/*     */       } 
/*     */       
/*  87 */       list.add(this.MINI_MESSAGE.deserialize(colorMiniMessage(AuctionConfiguration.enablePapiInAuctionItemLore ? papi(str1, paramPlayer) : str1)).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));
/*     */     } 
/*     */     
/*  90 */     itemMeta.lore(list);
/*  91 */     paramItemStack.setItemMeta(itemMeta);
/*     */   }
/*     */ 
/*     */   
/*     */   private String colorMiniMessage(String paramString) {
/*  96 */     String str = paramString;
/*     */     
/*  98 */     if (NmsVersion.nmsVersion.isHexVersion()) {
/*  99 */       Pattern pattern = Pattern.compile("(?<!<)(?<!:)#[a-fA-F0-9]{6}");
/* 100 */       Matcher matcher = pattern.matcher(paramString);
/* 101 */       while (matcher.find()) {
/* 102 */         String str1 = paramString.substring(matcher.start(), matcher.end());
/* 103 */         str = str.replace(str1, "<" + str1 + ">");
/* 104 */         paramString = paramString.replace(str1, "");
/* 105 */         matcher = pattern.matcher(paramString);
/*     */       } 
/*     */     } 
/*     */     
/* 109 */     for (Map.Entry<String, String> entry : this.COLORS_MAPPINGS.entrySet()) {
/* 110 */       String str1 = (String)entry.getKey();
/* 111 */       String str2 = (String)entry.getValue();
/*     */       
/* 113 */       str = str.replace("&" + str1, "<" + str2 + ">");
/* 114 */       str = str.replace("§" + str1, "<" + str2 + ">");
/* 115 */       str = str.replace("&" + str1.toUpperCase(), "<" + str2 + ">");
/* 116 */       str = str.replace("§" + str1.toUpperCase(), "<" + str2 + ">");
/*     */     } 
/*     */     
/* 119 */     return str;
/*     */   }
/*     */   
/*     */   public ItemStack createItem(Material paramMaterial, String paramString, List<String> paramList) {
/* 123 */     ItemStack itemStack = new ItemStack(paramMaterial);
/* 124 */     ItemMeta itemMeta = itemStack.getItemMeta();
/*     */     
/* 126 */     if (paramString != null) {
/* 127 */       itemMeta.displayName(this.MINI_MESSAGE.deserialize(colorMiniMessage(paramString)).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));
/*     */     }
/* 129 */     if (!paramList.isEmpty()) {
/* 130 */       ArrayList<Component> arrayList = new ArrayList();
/* 131 */       for (String str : paramList) {
/* 132 */         arrayList.add(this.MINI_MESSAGE.deserialize(colorMiniMessage(str)).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));
/*     */       }
/* 134 */       itemMeta.lore(arrayList);
/*     */     } 
/*     */     
/* 137 */     itemStack.setItemMeta(itemMeta);
/*     */     
/* 139 */     return itemStack;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zmenu\MiniMessageHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */