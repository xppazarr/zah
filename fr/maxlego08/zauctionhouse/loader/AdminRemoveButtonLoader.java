/*    */ package fr.maxlego08.zauctionhouse.loader;
/*    */ 
/*    */ import fr.maxlego08.menu.api.button.Button;
/*    */ import fr.maxlego08.menu.api.button.DefaultButtonValue;
/*    */ import fr.maxlego08.menu.api.loader.ButtonLoader;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.buttons.ZAdminRemoveButton;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class AdminRemoveButtonLoader extends ButtonLoader {
/*    */   private final ZAuctionPlugin plugin;
/*    */   
/*    */   public AdminRemoveButtonLoader(ZAuctionPlugin paramZAuctionPlugin) {
/* 15 */     super((Plugin)paramZAuctionPlugin, "zauctionhouse_admin_remove");
/* 16 */     this.plugin = paramZAuctionPlugin;
/*    */   }
/*    */ 
/*    */   
/*    */   public Button load(YamlConfiguration paramYamlConfiguration, String paramString, DefaultButtonValue paramDefaultButtonValue) {
/* 21 */     boolean bool1 = paramYamlConfiguration.getBoolean(paramString + "isSilent", false);
/* 22 */     boolean bool2 = paramYamlConfiguration.getBoolean(paramString + "isForceRemove", false);
/* 23 */     return (Button)new ZAdminRemoveButton(this.plugin, bool1, bool2);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\loader\AdminRemoveButtonLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */