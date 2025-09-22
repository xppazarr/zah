/*    */ package fr.maxlego08.zauctionhouse.buttons.multiple;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.InventoryType;
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class ZEBIButton
/*    */   extends MultipleButton
/*    */ {
/*    */   public ZEBIButton(Plugin paramPlugin) {
/* 12 */     super((ZAuctionPlugin)paramPlugin, Arrays.asList(new InventoryType[] { InventoryType.BUYING, InventoryType.EXPIRE, InventoryType.ITEMS }));
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\buttons\multiple\ZEBIButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */