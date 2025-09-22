/*     */ package fr.maxlego08.zauctionhouse.inventory;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.button.Button;
/*     */ import fr.maxlego08.zauctionhouse.api.button.buttons.AuctionButton;
/*     */ import fr.maxlego08.zauctionhouse.api.button.buttons.PermissibleButton;
/*     */ import fr.maxlego08.zauctionhouse.api.button.buttons.PlaceholderButton;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.InventoryType;
/*     */ import fr.maxlego08.zauctionhouse.api.exceptions.AuctionInventoryException;
/*     */ import fr.maxlego08.zauctionhouse.api.inventory.Inventory;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InventoryObject
/*     */   extends ZUtils
/*     */   implements Inventory
/*     */ {
/*     */   private final String name;
/*     */   private final InventoryType type;
/*     */   private final int size;
/*     */   private final List<Button> buttons;
/*     */   private final String fileName;
/*     */   
/*     */   public InventoryObject(String paramString1, InventoryType paramInventoryType, int paramInt, List<Button> paramList, String paramString2) {
/*  36 */     this.name = color(paramString1);
/*  37 */     this.type = paramInventoryType;
/*  38 */     this.size = paramInt;
/*  39 */     this.buttons = paramList;
/*  40 */     this.fileName = paramString2;
/*     */     
/*  42 */     if (getButtons((Class)AuctionButton.class).size() > 1) {
/*  43 */       throw new AuctionInventoryException("Vous devez avoir qu'un seul bouton de type AUCTION_ITEM dans votre inventaire.");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  49 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  54 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName(String paramString1, String paramString2) {
/*  59 */     return this.name.replace(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Button> List<T> getButtons(Class<T> paramClass) {
/*  66 */     List<T> list = (List)getButtons().stream().filter(paramButton -> paramClass.isAssignableFrom(paramButton.getClass())).map(paramButton -> paramButton).collect(Collectors.toList());
/*  67 */     list.addAll((Collection)getButtons().stream().filter(paramButton -> paramButton instanceof PermissibleButton)
/*  68 */         .map(paramButton -> (PermissibleButton)paramButton).filter(paramPermissibleButton -> paramPermissibleButton.hasElseButton()).map(paramPermissibleButton -> paramPermissibleButton.getElseButton())
/*  69 */         .filter(paramButton -> paramClass.isAssignableFrom(paramButton.getClass())).map(paramButton -> paramButton).collect(Collectors.toList()));
/*  70 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Button> getButtons() {
/*  75 */     return this.buttons;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void open(Player paramPlayer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public List<PlaceholderButton> sortButtons(int paramInt) {
/*  85 */     ArrayList<PlaceholderButton> arrayList = new ArrayList();
/*  86 */     for (Button button : getButtons()) {
/*     */       
/*  88 */       int i = button.getSlot() - (paramInt - 1) * this.size;
/*  89 */       if ((i >= 0 && i < this.size) || button.isPermament()) {
/*  90 */         button.setTmpSlot(i);
/*  91 */         arrayList.add((PlaceholderButton)button.toButton(PlaceholderButton.class));
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxPage(List<AuctionItem> paramList) {
/* 101 */     if (getType().isPagination()) {
/*     */       
/* 103 */       if (paramList == null) {
/* 104 */         Logger.info("Impossible de trouver la liste des items !");
/* 105 */         return 1;
/*     */       } 
/*     */       
/* 108 */       List<AuctionButton> list = getButtons(AuctionButton.class);
/* 109 */       if (list.size() == 1) {
/* 110 */         AuctionButton auctionButton = list.get(0);
/* 111 */         int j = auctionButton.getSlots().size();
/* 112 */         return paramList.size() / j + ((paramList.size() == j) ? 0 : 1);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 117 */     int i = 0;
/* 118 */     for (Button button : getButtons())
/* 119 */       i = Math.max(i, button.getSlot()); 
/* 120 */     return i / this.size + 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryType getType() {
/* 125 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFileName() {
/* 130 */     return this.fileName;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxPage() {
/* 135 */     return getMaxPage((List<AuctionItem>)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAuctionPageSize() {
/* 140 */     List<AuctionButton> list = getButtons(AuctionButton.class);
/* 141 */     if (list.size() == 1) {
/* 142 */       AuctionButton auctionButton = list.get(0);
/* 143 */       return auctionButton.getSlots().size();
/*     */     } 
/*     */     
/* 146 */     return 0;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\inventory\InventoryObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */