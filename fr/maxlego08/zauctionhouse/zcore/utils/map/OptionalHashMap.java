/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.map;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Optional;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OptionalHashMap<K, V>
/*    */   extends HashMap<K, V>
/*    */   implements OptionalMap<K, V>
/*    */ {
/*    */   private static final long serialVersionUID = -1389669310403530512L;
/*    */   
/*    */   public Optional<V> getOptional(K paramK) {
/* 19 */     V v = getOrDefault(paramK, null);
/* 20 */     return (v == null) ? Optional.<V>empty() : Optional.<V>of(v);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPresent(K paramK) {
/* 29 */     return getOptional(paramK).isPresent();
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\map\OptionalHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */