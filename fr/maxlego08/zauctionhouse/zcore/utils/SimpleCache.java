/*    */ package fr.maxlego08.zauctionhouse.zcore.utils;
/*    */ 
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimpleCache<K, V>
/*    */ {
/*  9 */   private final ConcurrentHashMap<K, V> cache = new ConcurrentHashMap<>();
/*    */ 
/*    */   
/*    */   public V get(K paramK, Loader<V> paramLoader) {
/* 13 */     return this.cache.computeIfAbsent(paramK, paramObject -> paramLoader.load());
/*    */   }
/*    */   
/*    */   public static interface Loader<V> {
/*    */     V load();
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\SimpleCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */