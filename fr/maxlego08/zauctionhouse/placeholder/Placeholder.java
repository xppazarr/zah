/*    */ package fr.maxlego08.zauctionhouse.placeholder;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.regex.Pattern;
/*    */ import me.clip.placeholderapi.PlaceholderAPI;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Placeholder
/*    */ {
/* 13 */   public static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("[%]([^%]+)[%]");
/* 14 */   public static final Pattern BRACKET_PLACEHOLDER_PATTERN = Pattern.compile("[{]([^{}]+)[}]");
/*    */   
/*    */   static Placeholder getPlaceholder() {
/* 17 */     if (LP.placeholder == null) {  } else {  }  return LP.placeholder;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   String setPlaceholders(Player paramPlayer, String paramString);
/*    */ 
/*    */ 
/*    */   
/*    */   List<String> setPlaceholders(Player paramPlayer, List<String> paramList);
/*    */ 
/*    */ 
/*    */   
/*    */   static boolean containsPlaceholders(String text) {
/* 32 */     return (text != null && PLACEHOLDER_PATTERN.matcher(text).find());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static boolean containsBracketPlaceholders(String text) {
/* 43 */     return (text != null && BRACKET_PLACEHOLDER_PATTERN.matcher(text).find());
/*    */   }
/*    */   
/*    */   public static class Api
/*    */     implements Placeholder {
/*    */     public Api() {
/* 49 */       DistantPlaceholder distantPlaceholder = new DistantPlaceholder(LocalPlaceholder.getInstance());
/* 50 */       distantPlaceholder.register();
/*    */     }
/*    */ 
/*    */     
/*    */     public String setPlaceholders(Player param1Player, String param1String) {
/* 55 */       return PlaceholderAPI.setPlaceholders(param1Player, param1String);
/*    */     }
/*    */ 
/*    */     
/*    */     public List<String> setPlaceholders(Player param1Player, List<String> param1List) {
/* 60 */       return PlaceholderAPI.setPlaceholders(param1Player, param1List);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static class Local
/*    */     implements Placeholder
/*    */   {
/*    */     public String setPlaceholders(Player param1Player, String param1String) {
/* 69 */       return LocalPlaceholder.getInstance().setPlaceholders(param1Player, param1String);
/*    */     }
/*    */ 
/*    */     
/*    */     public List<String> setPlaceholders(Player param1Player, List<String> param1List) {
/* 74 */       return LocalPlaceholder.getInstance().setPlaceholders(param1Player, param1List);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class LP {
/*    */     public static Placeholder placeholder;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\placeholder\Placeholder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */