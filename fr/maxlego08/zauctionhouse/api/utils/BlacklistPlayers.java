/*    */ package fr.maxlego08.zauctionhouse.api.utils;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ 
/*    */ public class BlacklistPlayers
/*    */ {
/*  9 */   public static List<UUID> blacklistPlayers = new ArrayList<>();
/*    */   
/*    */   public void save(Persist paramPersist) {
/* 12 */     paramPersist.save(this);
/*    */   }
/*    */   
/*    */   public void load(Persist paramPersist) {
/* 16 */     paramPersist.loadOrSaveDefault(this, BlacklistPlayers.class);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\ap\\utils\BlacklistPlayers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */