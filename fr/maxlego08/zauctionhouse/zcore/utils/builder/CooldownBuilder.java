/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.builder;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.storage.Savable;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.Persist;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CooldownBuilder
/*    */   implements Savable
/*    */ {
/* 13 */   public static Map<String, Map<UUID, Long>> cooldowns = new HashMap<>();
/*    */   
/*    */   public static Map<UUID, Long> getCooldownMap(String paramString) {
/* 16 */     return cooldowns.getOrDefault(paramString, null);
/*    */   }
/*    */   
/*    */   public static void clear() {
/* 20 */     cooldowns.clear();
/*    */   }
/*    */   
/*    */   public static void createCooldown(String paramString) {
/* 24 */     cooldowns.putIfAbsent(paramString, new HashMap<>());
/*    */   }
/*    */ 
/*    */   
/*    */   public static void removeCooldown(String paramString, UUID paramUUID) {
/* 29 */     createCooldown(paramString);
/*    */     
/* 31 */     getCooldownMap(paramString).remove(paramUUID);
/*    */   }
/*    */   
/*    */   public static void removeCooldown(String paramString, Player paramPlayer) {
/* 35 */     removeCooldown(paramString, paramPlayer.getUniqueId());
/*    */   }
/*    */ 
/*    */   
/*    */   public static void addCooldown(String paramString, UUID paramUUID, long paramLong) {
/* 40 */     createCooldown(paramString);
/*    */     
/* 42 */     long l = System.currentTimeMillis() + paramLong * 1000L;
/* 43 */     getCooldownMap(paramString).put(paramUUID, Long.valueOf(l));
/*    */   }
/*    */   
/*    */   public static void addCooldown(String paramString, Player paramPlayer, long paramLong) {
/* 47 */     addCooldown(paramString, paramPlayer.getUniqueId(), paramLong);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isCooldown(String paramString, UUID paramUUID) {
/* 52 */     createCooldown(paramString);
/* 53 */     Map map = cooldowns.get(paramString);
/*    */     
/* 55 */     return (map.containsKey(paramUUID) && System.currentTimeMillis() <= ((Long)map.get(paramUUID)).longValue());
/*    */   }
/*    */   
/*    */   public static boolean isCooldown(String paramString, Player paramPlayer) {
/* 59 */     return isCooldown(paramString, paramPlayer.getUniqueId());
/*    */   }
/*    */ 
/*    */   
/*    */   public static long getCooldown(String paramString, UUID paramUUID) {
/* 64 */     createCooldown(paramString);
/* 65 */     Map map = cooldowns.get(paramString);
/*    */     
/* 67 */     return ((Long)map.getOrDefault(paramUUID, Long.valueOf(0L))).longValue() - System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public static long getCooldownPlayer(String paramString, Player paramPlayer) {
/* 71 */     return getCooldown(paramString, paramPlayer.getUniqueId());
/*    */   }
/*    */   
/*    */   public static String getCooldownAsString(String paramString, UUID paramUUID) {
/* 75 */     return TimerBuilder.getStringTime(getCooldown(paramString, paramUUID) / 1000L);
/*    */   }
/*    */   
/* 78 */   private static transient CooldownBuilder i = new CooldownBuilder();
/*    */ 
/*    */   
/*    */   public void save(Persist paramPersist) {
/* 82 */     paramPersist.save(i, "cooldowns");
/*    */   }
/*    */ 
/*    */   
/*    */   public void load(Persist paramPersist) {
/* 87 */     paramPersist.loadOrSaveDefault(i, CooldownBuilder.class, "cooldowns");
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\builder\CooldownBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */