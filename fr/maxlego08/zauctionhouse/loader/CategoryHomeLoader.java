/*    */ package fr.maxlego08.zauctionhouse.loader;
/*    */ 
/*    */ import fr.maxlego08.menu.api.button.Button;
/*    */ import fr.maxlego08.menu.api.button.DefaultButtonValue;
/*    */ import fr.maxlego08.menu.api.loader.ButtonLoader;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.buttons.ZCategoriesHomeButton;
/*    */ import java.util.List;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class CategoryHomeLoader
/*    */   extends ButtonLoader {
/*    */   private final ZAuctionPlugin plugin;
/*    */   
/*    */   public CategoryHomeLoader(ZAuctionPlugin paramZAuctionPlugin) {
/* 17 */     super((Plugin)paramZAuctionPlugin, "ZAUCTIONHOUSE_CATEGORIES_HOME");
/* 18 */     this.plugin = paramZAuctionPlugin;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Button load(YamlConfiguration paramYamlConfiguration, String paramString, DefaultButtonValue paramDefaultButtonValue) {
/* 24 */     List list = paramYamlConfiguration.getStringList(paramString + "categories");
/* 25 */     return (Button)new ZCategoriesHomeButton(this.plugin, list);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\loader\CategoryHomeLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */