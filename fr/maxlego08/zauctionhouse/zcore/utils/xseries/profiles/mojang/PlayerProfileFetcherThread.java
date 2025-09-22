/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.mojang;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.ProfileLogger;
/*    */ import java.util.concurrent.ExecutorService;
/*    */ import java.util.concurrent.Executors;
/*    */ import java.util.concurrent.ThreadFactory;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class PlayerProfileFetcherThread
/*    */   implements ThreadFactory
/*    */ {
/* 39 */   public static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(2, new PlayerProfileFetcherThread());
/*    */   
/* 41 */   private static final AtomicInteger COUNT = new AtomicInteger();
/*    */ 
/*    */   
/*    */   public Thread newThread(@NotNull Runnable paramRunnable) {
/* 45 */     Thread thread = new Thread(paramRunnable);
/* 46 */     thread.setName("Profile Lookup Executor #" + COUNT.getAndIncrement());
/* 47 */     thread.setUncaughtExceptionHandler((paramThread, paramThrowable) -> ProfileLogger.LOGGER.error("Uncaught exception in thread {}", paramThread.getName(), paramThrowable));
/*    */     
/* 49 */     return thread;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\profiles\mojang\PlayerProfileFetcherThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */