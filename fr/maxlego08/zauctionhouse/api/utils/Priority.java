/*    */ package fr.maxlego08.zauctionhouse.api.utils;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Priority
/*    */ {
/*    */   private final int priority;
/*    */   private final int limit;
/*    */   private final String permission;
/*    */   private final String text;
/*    */   
/*    */   public Priority(int paramInt1, int paramInt2, String paramString1, String paramString2) {
/* 20 */     this.priority = paramInt1;
/* 21 */     this.limit = paramInt2;
/* 22 */     this.permission = paramString1;
/* 23 */     this.text = paramString2;
/*    */   }
/*    */   
/*    */   public Priority(Map<?, ?> paramMap) {
/* 27 */     this(((Number)paramMap.get("priority")).intValue(), ((Number)paramMap.get("limit")).intValue(), (String)paramMap.get("permission"), (String)paramMap.get("text"));
/*    */   }
/*    */   
/*    */   public String getText() {
/* 31 */     return this.text;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getPriority() {
/* 38 */     return this.priority;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLimit() {
/* 45 */     return this.limit;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermission() {
/* 52 */     return this.permission;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 62 */     return "Priority [priority=" + this.priority + ", limit=" + this.limit + ", permission=" + this.permission + "]";
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\ap\\utils\Priority.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */