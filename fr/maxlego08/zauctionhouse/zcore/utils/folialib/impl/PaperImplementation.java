/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.folialib.impl;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.FoliaLib;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.util.ImplementationTestsUtil;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.player.PlayerTeleportEvent;
/*    */ 
/*    */ 
/*    */ public class PaperImplementation
/*    */   extends SpigotImplementation
/*    */ {
/*    */   private Method teleportAsyncMethod;
/*    */   
/*    */   public PaperImplementation(FoliaLib paramFoliaLib) {
/* 18 */     super(paramFoliaLib);
/*    */ 
/*    */     
/* 21 */     if (ImplementationTestsUtil.isAsyncTeleportSupported()) {
/*    */       try {
/* 23 */         this.teleportAsyncMethod = Entity.class.getMethod("teleportAsync", new Class[] { Location.class, PlayerTeleportEvent.TeleportCause.class });
/* 24 */       } catch (NoSuchMethodException noSuchMethodException) {
/* 25 */         throw new RuntimeException("Failed to initialize PaperImplementation", noSuchMethodException);
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CompletableFuture<Boolean> teleportAsync(Entity paramEntity, Location paramLocation) {
/* 34 */     return teleportAsync(paramEntity, paramLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public CompletableFuture<Boolean> teleportAsync(Entity paramEntity, Location paramLocation, PlayerTeleportEvent.TeleportCause paramTeleportCause) {
/* 40 */     if (!ImplementationTestsUtil.isAsyncTeleportSupported()) return super.teleportAsync(paramEntity, paramLocation, paramTeleportCause);
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 45 */       return (CompletableFuture<Boolean>)this.teleportAsyncMethod.invoke(paramEntity, new Object[] { paramLocation, paramTeleportCause });
/* 46 */     } catch (Exception exception) {
/* 47 */       exception.printStackTrace();
/* 48 */       return super.teleportAsync(paramEntity, paramLocation, paramTeleportCause);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\folialib\impl\PaperImplementation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */