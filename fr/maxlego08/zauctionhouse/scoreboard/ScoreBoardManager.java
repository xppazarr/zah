/*     */ package fr.maxlego08.zauctionhouse.scoreboard;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.interfaces.CollectionConsumer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.TimerTask;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ public class ScoreBoardManager
/*     */   extends ZUtils
/*     */ {
/*     */   private final Plugin plugin;
/*  16 */   private final Map<Player, FastBoard> boards = new HashMap<>();
/*     */   
/*     */   private final long schedulerMillisecond;
/*     */   private boolean isRunning = false;
/*     */   private CollectionConsumer<Player> lines;
/*     */   
/*     */   public ScoreBoardManager(Plugin paramPlugin, long paramLong) {
/*  23 */     this.schedulerMillisecond = paramLong;
/*  24 */     this.plugin = paramPlugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void schedule() {
/*  32 */     if (this.isRunning) {
/*     */       return;
/*     */     }
/*     */     
/*  36 */     this.isRunning = true;
/*     */     
/*  38 */     scheduleFix(this.plugin, this.schedulerMillisecond, (paramTimerTask, paramBoolean) -> {
/*     */           if (!paramBoolean.booleanValue()) {
/*     */             return;
/*     */           }
/*     */           if (!this.isRunning) {
/*     */             paramTimerTask.cancel();
/*     */             return;
/*     */           } 
/*     */           if (this.lines == null) {
/*     */             paramTimerTask.cancel();
/*     */             return;
/*     */           } 
/*     */           for (FastBoard fastBoard : this.boards.values()) {
/*     */             if (fastBoard.isDeleted() || !fastBoard.getPlayer().isOnline()) {
/*     */               this.boards.remove(fastBoard.getPlayer());
/*     */             }
/*     */           } 
/*     */           this.boards.forEach(());
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastBoard createBoard(Player paramPlayer, String paramString) {
/*  77 */     if (hasBoard(paramPlayer)) {
/*  78 */       return getBoard(paramPlayer);
/*     */     }
/*     */     
/*  81 */     FastBoard fastBoard = new FastBoard(paramPlayer);
/*  82 */     fastBoard.updateTitle(paramString);
/*     */     
/*  84 */     if (this.lines != null) {
/*  85 */       fastBoard.updateLines(this.lines.accept(paramPlayer));
/*     */     }
/*     */     
/*  88 */     this.boards.put(paramPlayer, fastBoard);
/*     */     
/*  90 */     return fastBoard;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean delete(Player paramPlayer) {
/* 102 */     if (!hasBoard(paramPlayer)) {
/* 103 */       return false;
/*     */     }
/*     */     
/* 106 */     FastBoard fastBoard = getBoard(paramPlayer);
/* 107 */     if (!fastBoard.isDeleted()) {
/* 108 */       fastBoard.delete();
/* 109 */       return true;
/*     */     } 
/*     */     
/* 112 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean updateTitle(Player paramPlayer, String paramString) {
/* 124 */     if (!hasBoard(paramPlayer)) {
/* 125 */       return false;
/*     */     }
/*     */     
/* 128 */     FastBoard fastBoard = getBoard(paramPlayer);
/* 129 */     if (!fastBoard.isDeleted()) {
/* 130 */       fastBoard.updateTitle(paramString);
/* 131 */       return true;
/*     */     } 
/* 133 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean updateLine(Player paramPlayer, int paramInt, String paramString) {
/* 145 */     if (!hasBoard(paramPlayer)) {
/* 146 */       return false;
/*     */     }
/*     */     
/* 149 */     FastBoard fastBoard = getBoard(paramPlayer);
/* 150 */     if (!fastBoard.isDeleted()) {
/* 151 */       fastBoard.updateLine(paramInt, paramString);
/* 152 */       return true;
/*     */     } 
/* 154 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasBoard(Player paramPlayer) {
/* 164 */     return this.boards.containsKey(paramPlayer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastBoard getBoard(Player paramPlayer) {
/* 174 */     return this.boards.getOrDefault(paramPlayer, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<Player, FastBoard> getBoards() {
/* 181 */     return this.boards;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSchedulerMillisecond() {
/* 188 */     return this.schedulerMillisecond;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRunning() {
/* 195 */     return this.isRunning;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CollectionConsumer<Player> getLines() {
/* 202 */     return this.lines;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRunning(boolean paramBoolean) {
/* 210 */     this.isRunning = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLines(CollectionConsumer<Player> paramCollectionConsumer) {
/* 218 */     this.lines = paramCollectionConsumer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLinesAndSchedule(CollectionConsumer<Player> paramCollectionConsumer) {
/* 226 */     this.lines = paramCollectionConsumer;
/* 227 */     schedule();
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\scoreboard\ScoreBoardManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */