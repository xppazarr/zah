/*    */ package fr.maxlego08.zauctionhouse.loader;
/*    */ 
/*    */ import fr.maxlego08.menu.api.button.Button;
/*    */ import fr.maxlego08.menu.api.button.DefaultButtonValue;
/*    */ import fr.maxlego08.menu.api.loader.ButtonLoader;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.InventoryType;
/*    */ import fr.maxlego08.zauctionhouse.buttons.ZShowButton;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class ShowButtonLoader extends ButtonLoader {
/*    */   private final ZAuctionPlugin plugin;
/*    */   private final InventoryType inventoryType;
/*    */   
/*    */   public ShowButtonLoader(ZAuctionPlugin paramZAuctionPlugin, InventoryType paramInventoryType, String paramString) {
/* 17 */     super((Plugin)paramZAuctionPlugin, "zauctionhouse_show_" + paramString);
/* 18 */     this.plugin = paramZAuctionPlugin;
/* 19 */     this.inventoryType = paramInventoryType;
/*    */   }
/*    */ 
/*    */   
/*    */   public Button load(YamlConfiguration paramYamlConfiguration, String paramString, DefaultButtonValue paramDefaultButtonValue) {
/* 24 */     return (Button)new ZShowButton(this.plugin, this.inventoryType);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\loader\ShowButtonLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */