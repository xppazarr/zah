/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.folialib.util;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.player.PlayerTeleportEvent;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitScheduler;
/*    */ import org.bukkit.scheduler.BukkitTask;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImplementationTestsUtil
/*    */ {
/*    */   private static final boolean IS_CANCELLED_SUPPORTED;
/*    */   private static final boolean IS_TASK_CONSUMERS_SUPPORTED;
/*    */   private static final boolean IS_ASYNC_TELEPORT_SUPPORTED;
/*    */   
/*    */   static {
/* 20 */     boolean bool1 = false;
/*    */     try {
/* 22 */       Class<BukkitTask> clazz = BukkitTask.class;
/*    */       
/* 24 */       clazz.getDeclaredMethod("isCancelled", new Class[0]);
/* 25 */       bool1 = true;
/* 26 */     } catch (NoSuchMethodException noSuchMethodException) {}
/*    */ 
/*    */ 
/*    */     
/* 30 */     IS_CANCELLED_SUPPORTED = bool1;
/*    */     
/* 32 */     boolean bool2 = false;
/*    */     try {
/* 34 */       Class<BukkitScheduler> clazz = BukkitScheduler.class;
/*    */       
/* 36 */       clazz.getDeclaredMethod("runTask", new Class[] { Plugin.class, Consumer.class });
/* 37 */       bool2 = true;
/* 38 */     } catch (NoSuchMethodException noSuchMethodException) {}
/*    */ 
/*    */ 
/*    */     
/* 42 */     IS_TASK_CONSUMERS_SUPPORTED = bool2;
/*    */ 
/*    */     
/* 45 */     boolean bool3 = false;
/*    */     try {
/* 47 */       Class<Entity> clazz = Entity.class;
/*    */       
/* 49 */       clazz.getDeclaredMethod("teleportAsync", new Class[] { Location.class, PlayerTeleportEvent.TeleportCause.class });
/* 50 */       bool3 = true;
/* 51 */     } catch (NoSuchMethodException noSuchMethodException) {}
/*    */ 
/*    */ 
/*    */     
/* 55 */     IS_ASYNC_TELEPORT_SUPPORTED = bool3;
/*    */   }
/*    */   
/*    */   public static boolean isCancelledSupported() {
/* 59 */     return IS_CANCELLED_SUPPORTED;
/*    */   }
/*    */   
/*    */   public static boolean isTaskConsumersSupported() {
/* 63 */     return IS_TASK_CONSUMERS_SUPPORTED;
/*    */   }
/*    */   
/*    */   public static boolean isAsyncTeleportSupported() {
/* 67 */     return IS_ASYNC_TELEPORT_SUPPORTED;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\foliali\\util\ImplementationTestsUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */