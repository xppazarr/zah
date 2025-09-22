/*    */ package fr.maxlego08.zauctionhouse.loader;
/*    */ 
/*    */ import fr.maxlego08.menu.api.button.Button;
/*    */ import fr.maxlego08.menu.api.button.DefaultButtonValue;
/*    */ import fr.maxlego08.menu.api.loader.ButtonLoader;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.buttons.ZClaimButton;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class ClaimLoader
/*    */   extends ButtonLoader {
/*    */   public ClaimLoader(ZAuctionPlugin paramZAuctionPlugin) {
/* 14 */     super((Plugin)paramZAuctionPlugin, "zauctionhouse_claim");
/* 15 */     this.plugin = paramZAuctionPlugin;
/*    */   }
/*    */   private final ZAuctionPlugin plugin;
/*    */   
/*    */   public Button load(YamlConfiguration paramYamlConfiguration, String paramString, DefaultButtonValue paramDefaultButtonValue) {
/* 20 */     boolean bool = paramYamlConfiguration.getBoolean(paramString + "refresh-placeholder", false);
/* 21 */     return (Button)new ZClaimButton(this.plugin, bool);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\loader\ClaimLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */