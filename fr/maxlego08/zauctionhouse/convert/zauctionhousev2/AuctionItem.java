/*     */ package fr.maxlego08.zauctionhouse.convert.zauctionhousev2;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.AuctionType;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ItemStackUtils;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AuctionItem
/*     */   extends ZUtils
/*     */ {
/*     */   private final long id;
/*     */   private final String item;
/*     */   private transient ItemStack itemStack;
/*     */   private final long price;
/*     */   private String player;
/*     */   private UUID uuid;
/*     */   private long time;
/*     */   private String economy;
/*     */   private boolean isAlreadyBuying;
/*     */   private boolean canBuy;
/*     */   
/*     */   public AuctionItem(long paramLong1, ItemStack paramItemStack, long paramLong2, String paramString1, int paramInt, String paramString2, UUID paramUUID) {
/*  34 */     this.id = paramLong1;
/*  35 */     this.item = ItemStackUtils.serializeItemStack(paramItemStack);
/*  36 */     this.itemStack = paramItemStack;
/*  37 */     this.price = paramLong2;
/*  38 */     this.player = paramString1;
/*  39 */     this.time = System.currentTimeMillis() + paramInt * 1000L;
/*  40 */     this.economy = paramString2;
/*  41 */     this.uuid = paramUUID;
/*  42 */     this.isAlreadyBuying = false;
/*  43 */     this.canBuy = true;
/*  44 */     if (paramString2 == null) {
/*  45 */       paramString2 = "VAULT";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlreadyBuying(boolean paramBoolean) {
/*  53 */     this.isAlreadyBuying = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCanBuy(boolean paramBoolean) {
/*  61 */     this.canBuy = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBuy() {
/*  68 */     return this.canBuy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAlreadyBuying() {
/*  75 */     return this.isAlreadyBuying;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean match(Player paramPlayer) {
/*  83 */     return this.player.equalsIgnoreCase(paramPlayer.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchPlayer(String paramString) {
/*  91 */     return this.player.equalsIgnoreCase(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEconomy() {
/*  98 */     if (this.economy == null)
/*  99 */       this.economy = "VAULT"; 
/* 100 */     return this.economy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEconomy(String paramString) {
/* 108 */     this.economy = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 115 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getItem() {
/* 122 */     return this.item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItemStack() {
/* 129 */     createItemStack();
/* 130 */     return this.itemStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getPrice() {
/* 137 */     return this.price;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String getPlayer() {
/* 145 */     return this.player;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTime() {
/* 152 */     return this.time;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setPlayer(String paramString) {
/* 161 */     this.player = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTime(long paramLong) {
/* 169 */     this.time = System.currentTimeMillis() + paramLong * 1000L;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void createItemStack() {
/* 175 */     if (this.itemStack == null) {
/* 176 */       this.itemStack = ItemStackUtils.safeDeserializeItemStack(this.item);
/* 177 */       if (this.itemStack == null) {
/* 178 */         Logger.info("Created itemStack is null for " + this.id + " AuctionItem", Logger.LogType.ERROR);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String betterPrice() {
/* 187 */     if (AuctionConfiguration.betterPrice)
/* 188 */       return format(this.price, AuctionConfiguration.betterPriceEspace); 
/* 189 */     if (AuctionConfiguration.betterPriceReduction) {
/* 190 */       return getDisplayBalance(this.price);
/*     */     }
/* 192 */     return String.valueOf(this.price);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 202 */     return "AuctionItem [id=" + this.id + ", price=" + this.price + ", time=" + this.time + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UUID getUuid() {
/* 210 */     return this.uuid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUuid(UUID paramUUID) {
/* 218 */     this.uuid = paramUUID;
/*     */   }
/*     */ 
/*     */   
/*     */   public OfflinePlayer getAsPlayer() {
/* 223 */     return (this.uuid == null) ? Bukkit.getOfflinePlayer(this.player) : Bukkit.getOfflinePlayer(this.uuid);
/*     */   }
/*     */   
/*     */   public fr.maxlego08.zauctionhouse.api.AuctionItem getModernAuction(StorageType paramStorageType, boolean paramBoolean, ZAuctionPlugin paramZAuctionPlugin) {
/* 227 */     UUID uUID1 = UUID.randomUUID();
/* 228 */     ItemStack itemStack = getItemStack();
/* 229 */     AuctionType auctionType = AuctionType.DEFAULT;
/* 230 */     long l1 = getPrice();
/* 231 */     String str = getEconomy();
/* 232 */     UUID uUID2 = getUuid();
/* 233 */     long l2 = System.currentTimeMillis() + 1000L * AuctionConfiguration.expireTime;
/*     */     
/* 235 */     OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uUID2);
/*     */     
/* 237 */     return (fr.maxlego08.zauctionhouse.api.AuctionItem)new ZAuctionItem(uUID1, itemStack, auctionType, l1, str, uUID2, l2, paramStorageType, offlinePlayer
/* 238 */         .getName(), 0, paramZAuctionPlugin.getStorage().getServerName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public fr.maxlego08.zauctionhouse.api.AuctionItem getModernAuction(StorageType paramStorageType, ZAuctionPlugin paramZAuctionPlugin) {
/* 248 */     if (paramStorageType.equals(StorageType.STORAGE) || paramStorageType.equals(StorageType.EXPIRE)) {
/* 249 */       return getModernAuction(paramStorageType, true, paramZAuctionPlugin);
/*     */     }
/* 251 */     UUID uUID1 = UUID.randomUUID();
/* 252 */     ItemStack itemStack = getItemStack();
/* 253 */     AuctionType auctionType = AuctionType.DEFAULT;
/* 254 */     long l1 = getPrice();
/* 255 */     String str = getEconomy();
/* 256 */     UUID uUID2 = getUuid();
/* 257 */     UUID uUID3 = getAsPlayer().getUniqueId();
/* 258 */     long l2 = System.currentTimeMillis() + 1000L * AuctionConfiguration.expireTime;
/*     */     
/* 260 */     OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uUID2);
/*     */     
/* 262 */     return (fr.maxlego08.zauctionhouse.api.AuctionItem)new ZAuctionItem(uUID1, itemStack, auctionType, l1, str, uUID2, l2, uUID3, paramStorageType, offlinePlayer
/*     */         
/* 264 */         .getName(), 0, paramZAuctionPlugin.getStorage().getServerName());
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\convert\zauctionhousev2\AuctionItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */