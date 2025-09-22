/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.currencies.providers;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.currencies.CurrencyProvider;
/*     */ import java.math.BigDecimal;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class ItemProvider
/*     */   implements CurrencyProvider
/*     */ {
/*     */   protected final Plugin plugin;
/*     */   private final ItemStack itemStack;
/*     */   
/*     */   public ItemProvider(Plugin paramPlugin, ItemStack paramItemStack) {
/*  18 */     this.plugin = paramPlugin;
/*  19 */     this.itemStack = paramItemStack;
/*     */   }
/*     */ 
/*     */   
/*     */   public void deposit(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/*  24 */     if (paramOfflinePlayer.isOnline()) {
/*  25 */       Player player = paramOfflinePlayer.getPlayer();
/*  26 */       giveItem(player, paramBigDecimal.intValue(), this.itemStack);
/*     */     } else {
/*  28 */       this.plugin.getLogger().severe("Deposit items to " + paramOfflinePlayer.getName() + " but is offline");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void withdraw(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/*  34 */     if (paramOfflinePlayer.isOnline()) {
/*  35 */       Player player = paramOfflinePlayer.getPlayer();
/*  36 */       removeItems(player, this.itemStack, paramBigDecimal.intValue());
/*     */     } else {
/*  38 */       this.plugin.getLogger().severe("Withdraw items from " + paramOfflinePlayer.getName() + " but is offline");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BigDecimal getBalance(OfflinePlayer paramOfflinePlayer) {
/*  44 */     if (paramOfflinePlayer.isOnline()) {
/*  45 */       Player player = paramOfflinePlayer.getPlayer();
/*  46 */       return BigDecimal.valueOf(getAmount(player, this.itemStack));
/*  47 */     }  return BigDecimal.ZERO;
/*     */   }
/*     */   
/*     */   protected int getAmount(Player paramPlayer, ItemStack paramItemStack) {
/*  51 */     int i = 0;
/*  52 */     for (byte b = 0; b != 36; b++) {
/*  53 */       ItemStack itemStack = paramPlayer.getInventory().getItem(b);
/*  54 */       if (itemStack != null && itemStack.isSimilar(paramItemStack))
/*  55 */         i += itemStack.getAmount(); 
/*     */     } 
/*  57 */     return i;
/*     */   }
/*     */   
/*     */   protected void removeItems(Player paramPlayer, ItemStack paramItemStack, long paramLong) {
/*  61 */     PlayerInventory playerInventory = paramPlayer.getInventory();
/*     */     
/*  63 */     int i = (int)paramLong;
/*  64 */     byte b = 0;
/*     */ 
/*     */     
/*  67 */     for (ItemStack itemStack : playerInventory.getContents()) {
/*     */       
/*  69 */       if (itemStack != null && itemStack.isSimilar(paramItemStack) && i > 0) {
/*     */         
/*  71 */         int j = itemStack.getAmount() - i;
/*  72 */         i -= itemStack.getAmount();
/*     */         
/*  74 */         if (j <= 0)
/*  75 */         { if (b == 40) {
/*  76 */             playerInventory.setItemInOffHand(null);
/*     */           } else {
/*  78 */             playerInventory.removeItem(new ItemStack[] { itemStack });
/*     */           }  }
/*  80 */         else { itemStack.setAmount(j); }
/*     */       
/*  82 */       }  b++;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void giveItem(Player paramPlayer, long paramLong, ItemStack paramItemStack) {
/*  87 */     paramItemStack = paramItemStack.clone();
/*  88 */     if (paramLong > 64L) {
/*  89 */       paramLong -= 64L;
/*  90 */       paramItemStack.setAmount(64);
/*  91 */       give(paramPlayer, paramItemStack);
/*  92 */       giveItem(paramPlayer, paramLong, paramItemStack);
/*     */     } else {
/*  94 */       paramItemStack.setAmount((int)paramLong);
/*  95 */       give(paramPlayer, paramItemStack);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ItemStack getItemStack(Player paramPlayer) {
/* 100 */     return this.itemStack.clone();
/*     */   }
/*     */   
/*     */   private void give(Player paramPlayer, ItemStack paramItemStack) {
/* 104 */     if (hasInventoryFull(paramPlayer)) { paramPlayer.getWorld().dropItem(paramPlayer.getLocation(), paramItemStack); }
/* 105 */     else { paramPlayer.getInventory().addItem(new ItemStack[] { paramItemStack }); }
/*     */   
/*     */   }
/*     */   private boolean hasInventoryFull(Player paramPlayer) {
/* 109 */     byte b1 = 0;
/* 110 */     PlayerInventory playerInventory = paramPlayer.getInventory();
/* 111 */     for (byte b2 = 0; b2 != 36; b2++) {
/* 112 */       ItemStack itemStack = playerInventory.getContents()[b2];
/* 113 */       if (itemStack == null) b1++; 
/*     */     } 
/* 115 */     return (b1 == 0);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\currencies\providers\ItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */