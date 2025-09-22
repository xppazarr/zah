/*    */ package fr.maxlego08.zauctionhouse.loader;
/*    */ 
/*    */ import fr.maxlego08.menu.api.button.Button;
/*    */ import fr.maxlego08.menu.api.button.DefaultButtonValue;
/*    */ import fr.maxlego08.menu.api.loader.ButtonLoader;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.buttons.ZCategoriesButton;
/*    */ import java.util.List;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class CategoryListLoader
/*    */   extends ButtonLoader {
/*    */   private final ZAuctionPlugin plugin;
/*    */   
/*    */   public CategoryListLoader(ZAuctionPlugin paramZAuctionPlugin) {
/* 17 */     super((Plugin)paramZAuctionPlugin, "ZAUCTIONHOUSE_CATEGORIES_LORE");
/* 18 */     this.plugin = paramZAuctionPlugin;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Button load(YamlConfiguration paramYamlConfiguration, String paramString, DefaultButtonValue paramDefaultButtonValue) {
/* 24 */     String str1 = paramYamlConfiguration.getString(paramString + "enableText");
/* 25 */     String str2 = paramYamlConfiguration.getString(paramString + "disableText");
/* 26 */     List list = paramYamlConfiguration.getStringList(paramString + "categories");
/*    */     
/* 28 */     return (Button)new ZCategoriesButton(this.plugin, str1, str2, list);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\loader\CategoryListLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */