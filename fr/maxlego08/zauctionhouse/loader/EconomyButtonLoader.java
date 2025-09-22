/*    */ package fr.maxlego08.zauctionhouse.loader;
/*    */ 
/*    */ import fr.maxlego08.menu.api.button.Button;
/*    */ import fr.maxlego08.menu.api.button.DefaultButtonValue;
/*    */ import fr.maxlego08.menu.api.loader.ButtonLoader;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.buttons.ZEconomyButton;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class EconomyButtonLoader extends ButtonLoader {
/*    */   private final ZAuctionPlugin plugin;
/*    */   
/*    */   public EconomyButtonLoader(ZAuctionPlugin paramZAuctionPlugin) {
/* 15 */     super((Plugin)paramZAuctionPlugin, "zauctionhouse_economy");
/* 16 */     this.plugin = paramZAuctionPlugin;
/*    */   }
/*    */ 
/*    */   
/*    */   public Button load(YamlConfiguration paramYamlConfiguration, String paramString, DefaultButtonValue paramDefaultButtonValue) {
/* 21 */     String str = paramYamlConfiguration.getString(paramString + "economy", "empty");
/* 22 */     return (Button)new ZEconomyButton(this.plugin, str);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\loader\EconomyButtonLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */