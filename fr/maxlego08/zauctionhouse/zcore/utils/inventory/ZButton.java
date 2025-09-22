/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.inventory;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.api.utils.ItemBuilder;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Consumer;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.event.inventory.ClickType;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZButton
/*     */ {
/*     */   private int slot;
/*     */   private final ItemStack displayItem;
/*     */   private Consumer<InventoryClickEvent> onClick;
/*     */   private Consumer<InventoryClickEvent> onMiddleClick;
/*     */   private Consumer<InventoryClickEvent> onLeftClick;
/*     */   private Consumer<InventoryClickEvent> onRightClick;
/*     */   
/*     */   public ZButton(ItemStack paramItemStack) {
/*  24 */     this.displayItem = paramItemStack;
/*     */   }
/*     */   
/*     */   public ZButton(Material paramMaterial, int paramInt, String paramString, String... paramVarArgs) {
/*  28 */     this((new ItemBuilder(paramMaterial, paramInt, 1, paramString, Arrays.asList(paramVarArgs), null, null)).build());
/*     */   }
/*     */   
/*     */   public ZButton(Material paramMaterial, String paramString, String... paramVarArgs) {
/*  32 */     this((new ItemBuilder(paramMaterial, paramString)).setLore(paramVarArgs).build());
/*     */   }
/*     */   
/*     */   public ZButton(Material paramMaterial) {
/*  36 */     this((new ItemBuilder(paramMaterial)).build());
/*     */   }
/*     */   
/*     */   public ZButton(Material paramMaterial, String paramString) {
/*  40 */     this((new ItemBuilder(paramMaterial, paramString)).build());
/*     */   }
/*     */   
/*     */   public ZButton setClick(Consumer<InventoryClickEvent> paramConsumer) {
/*  44 */     this.onClick = paramConsumer;
/*  45 */     return this;
/*     */   }
/*     */   
/*     */   public ZButton setMiddleClick(Consumer<InventoryClickEvent> paramConsumer) {
/*  49 */     this.onMiddleClick = paramConsumer;
/*  50 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZButton setLeftClick(Consumer<InventoryClickEvent> paramConsumer) {
/*  58 */     this.onLeftClick = paramConsumer;
/*  59 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZButton setRightClick(Consumer<InventoryClickEvent> paramConsumer) {
/*  67 */     this.onRightClick = paramConsumer;
/*  68 */     return this;
/*     */   }
/*     */   
/*     */   public ItemStack getDisplayItem() {
/*  72 */     return this.displayItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSlot() {
/*  79 */     return this.slot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSlot(int paramInt) {
/*  87 */     this.slot = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClick(InventoryClickEvent paramInventoryClickEvent) {
/*  97 */     if (this.onClick != null) {
/*  98 */       this.onClick.accept(paramInventoryClickEvent);
/*     */     }
/* 100 */     if ((paramInventoryClickEvent.getClick().equals(ClickType.MIDDLE) || paramInventoryClickEvent.getClick().equals(ClickType.DROP)) && this.onMiddleClick != null) {
/*     */       
/* 102 */       this.onMiddleClick.accept(paramInventoryClickEvent);
/* 103 */     } else if (paramInventoryClickEvent.getClick().equals(ClickType.RIGHT) && this.onRightClick != null) {
/* 104 */       this.onRightClick.accept(paramInventoryClickEvent);
/* 105 */     } else if (paramInventoryClickEvent.getClick().equals(ClickType.LEFT) && this.onLeftClick != null) {
/* 106 */       this.onLeftClick.accept(paramInventoryClickEvent);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\inventory\ZButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */