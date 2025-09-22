/*    */ package fr.maxlego08.zauctionhouse.placeholder;
/*    */ 
/*    */ import me.clip.placeholderapi.expansion.PlaceholderExpansion;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DistantPlaceholder
/*    */   extends PlaceholderExpansion
/*    */ {
/*    */   private final LocalPlaceholder placeholder;
/*    */   
/*    */   public DistantPlaceholder(LocalPlaceholder paramLocalPlaceholder) {
/* 16 */     this.placeholder = paramLocalPlaceholder;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAuthor() {
/* 21 */     return this.placeholder.getPlugin().getDescription().getAuthors().get(0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIdentifier() {
/* 26 */     return this.placeholder.getPrefix();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getVersion() {
/* 31 */     return this.placeholder.getPlugin().getDescription().getVersion();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean persist() {
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public String onPlaceholderRequest(Player paramPlayer, String paramString) {
/* 41 */     return this.placeholder.onRequest(paramPlayer, paramString);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\placeholder\DistantPlaceholder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */