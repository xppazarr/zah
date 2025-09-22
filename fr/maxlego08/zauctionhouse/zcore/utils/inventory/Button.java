/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.inventory;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.material.MaterialData;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Button
/*    */   extends ZUtils
/*    */ {
/*    */   private final int slot;
/*    */   private final String name;
/*    */   private final MaterialData item;
/*    */   private final List<String> lore;
/*    */   
/*    */   public Button(int paramInt1, String paramString, Material paramMaterial, int paramInt2, List<String> paramList) {
/* 28 */     this.slot = paramInt1;
/* 29 */     this.name = paramString;
/* 30 */     this.item = new MaterialData(paramMaterial, (byte)paramInt2);
/* 31 */     this.lore = paramList;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Button(int paramInt, String paramString, Material paramMaterial) {
/* 41 */     this(paramInt, paramString, paramMaterial, 0, new String[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Button(int paramInt, String paramString, Material paramMaterial, String... paramVarArgs) {
/* 52 */     this(paramInt, paramString, paramMaterial, 0, Arrays.asList(paramVarArgs));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Button(int paramInt1, String paramString, Material paramMaterial, int paramInt2, String... paramVarArgs) {
/* 64 */     this(paramInt1, paramString, paramMaterial, paramInt2, Arrays.asList(paramVarArgs));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSlot() {
/* 71 */     return this.slot;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 78 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MaterialData getItem() {
/* 85 */     return this.item;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getLore() {
/* 92 */     return this.lore;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\inventory\Button.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */