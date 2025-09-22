/*    */ package fr.maxlego08.zauctionhouse.loader;
/*    */ 
/*    */ import fr.maxlego08.menu.api.button.Button;
/*    */ import fr.maxlego08.menu.api.button.DefaultButtonValue;
/*    */ import fr.maxlego08.menu.api.loader.ButtonLoader;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.buttons.ZRemoveConfirmButton;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class RemoveConfirmButtonLoader extends ButtonLoader {
/*    */   private final ZAuctionPlugin plugin;
/*    */   private final boolean isAdmin;
/*    */   
/*    */   public RemoveConfirmButtonLoader(ZAuctionPlugin paramZAuctionPlugin, boolean paramBoolean) {
/* 16 */     super((Plugin)paramZAuctionPlugin, "zauctionhouse_remove_confirm" + (paramBoolean ? "_admin" : ""));
/* 17 */     this.plugin = paramZAuctionPlugin;
/* 18 */     this.isAdmin = paramBoolean;
/*    */   }
/*    */ 
/*    */   
/*    */   public Button load(YamlConfiguration paramYamlConfiguration, String paramString, DefaultButtonValue paramDefaultButtonValue) {
/* 23 */     return (Button)new ZRemoveConfirmButton(this.plugin, this.isAdmin);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\loader\RemoveConfirmButtonLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */