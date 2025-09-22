/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.inventory;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.exceptions.InventoryOpenException;
/*    */ import fr.maxlego08.zauctionhouse.inventory.VInventory;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public abstract class PaginateInventory<T>
/*    */   extends VInventory {
/*    */   protected final String inventoryName;
/*    */   protected final int inventorySize;
/*    */   protected List<T> collections;
/* 18 */   protected int paginationSize = 45;
/* 19 */   protected int nextSlot = 50;
/* 20 */   protected int previousSlot = 48;
/* 21 */   protected int defaultSlot = 0;
/*    */   protected boolean isReverse = false;
/*    */   protected boolean disableDefaultClick = false;
/* 24 */   protected Material previousMaterial = Material.ARROW;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PaginateInventory(String paramString, int paramInt) {
/* 32 */     this.inventoryName = paramString;
/* 33 */     this.inventorySize = paramInt;
/*    */   }
/*    */   
/*    */   public PaginateInventory(String paramString, InventorySize paramInventorySize) {
/* 37 */     this.inventoryName = paramString;
/* 38 */     this.inventorySize = paramInventorySize.getSize();
/* 39 */     this.paginationSize = paramInventorySize.getPaginationSize();
/* 40 */     this.nextSlot = paramInventorySize.getNextSlot();
/* 41 */     this.previousSlot = paramInventorySize.getPreviousSlot();
/* 42 */     this.defaultSlot = paramInventorySize.getDefaultSlot();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InventoryResult openInventory(ZAuctionPlugin paramZAuctionPlugin, Player paramPlayer, int paramInt, Object... paramVarArgs) {
/* 49 */     if (this.defaultSlot > this.inventorySize || this.nextSlot > this.inventorySize || this.previousSlot > this.inventorySize || this.paginationSize > this.inventorySize)
/*    */     {
/* 51 */       throw new InventoryOpenException("Une erreur est survenue avec la gestion des slots !");
/*    */     }
/* 53 */     this.collections = preOpenInventory();
/*    */     
/* 55 */     if (this.collections == null) {
/* 56 */       throw new InventoryOpenException("Collection is null");
/*    */     }
/* 58 */     createInventory(this.inventoryName.replace("%mp%", String.valueOf(getMaxPage(this.collections))).replace("%p%", 
/* 59 */           String.valueOf(paramInt)), this.inventorySize);
/*    */     
/* 61 */     Pagination<T> pagination = new Pagination();
/* 62 */     AtomicInteger atomicInteger = new AtomicInteger(this.defaultSlot);
/*    */ 
/*    */     
/* 65 */     List<T> list = this.isReverse ? pagination.paginateReverse(this.collections, this.paginationSize, paramInt) : pagination.paginate(this.collections, this.paginationSize, paramInt);
/*    */     
/* 67 */     list.forEach(paramObject -> {
/*    */           ZButton zButton = addItem(paramAtomicInteger.getAndIncrement(), buildItem((T)paramObject));
/*    */           
/*    */           zButton.setClick(());
/*    */         });
/* 72 */     if (getPage() != 1)
/* 73 */       addItem(this.previousSlot, Material.ARROW, "§f« §7Page précédente")
/* 74 */         .setClick(paramInventoryClickEvent -> createInventory(this.plugin, paramPlayer, getId(), getPage() - 1, paramArrayOfObject)); 
/* 75 */     if (getPage() != getMaxPage(this.collections)) {
/* 76 */       addItem(this.nextSlot, Material.ARROW, "§f» §7Page suivante")
/* 77 */         .setClick(paramInventoryClickEvent -> createInventory(this.plugin, paramPlayer, getId(), getPage() + 1, paramArrayOfObject));
/*    */     }
/* 79 */     postOpenInventory();
/*    */     
/* 81 */     return InventoryResult.SUCCESS;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int slotChange(int paramInt) {
/* 89 */     return paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ZButton createButton(ZButton paramZButton) {
/* 97 */     return paramZButton;
/*    */   }
/*    */   
/*    */   public abstract ItemStack buildItem(T paramT);
/*    */   
/*    */   public abstract void onClick(T paramT, ZButton paramZButton);
/*    */   
/*    */   public abstract List<T> preOpenInventory();
/*    */   
/*    */   public abstract void postOpenInventory();
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\inventory\PaginateInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */