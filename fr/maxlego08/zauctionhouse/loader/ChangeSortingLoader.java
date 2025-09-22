/*    */ package fr.maxlego08.zauctionhouse.loader;
/*    */ 
/*    */ import fr.maxlego08.menu.api.button.Button;
/*    */ import fr.maxlego08.menu.api.button.DefaultButtonValue;
/*    */ import fr.maxlego08.menu.api.loader.ButtonLoader;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Sorting;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.DisplaySorting;
/*    */ import fr.maxlego08.zauctionhouse.buttons.ZChangeSortingButton;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.stream.Collectors;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class ChangeSortingLoader extends ButtonLoader {
/*    */   private final ZAuctionPlugin plugin;
/*    */   
/*    */   public ChangeSortingLoader(ZAuctionPlugin paramZAuctionPlugin) {
/* 20 */     super((Plugin)paramZAuctionPlugin, "ZAUCTIONHOUSE_CHANGE_SORTING");
/* 21 */     this.plugin = paramZAuctionPlugin;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Button load(YamlConfiguration paramYamlConfiguration, String paramString, DefaultButtonValue paramDefaultButtonValue) {
/* 27 */     String str1 = paramYamlConfiguration.getString(paramString + "enableText");
/* 28 */     String str2 = paramYamlConfiguration.getString(paramString + "disableText");
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 33 */     List list = (List)paramYamlConfiguration.getMapList(paramString + "sorting").stream().map(paramMap -> { Sorting sorting = Sorting.valueOf((String)paramMap.get("sorting")); String str = (String)paramMap.get("display"); return new DisplaySorting(sorting, str); }).collect(Collectors.toList());
/*    */     
/* 35 */     return (Button)new ZChangeSortingButton(this.plugin, str1, str2, list);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\loader\ChangeSortingLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */