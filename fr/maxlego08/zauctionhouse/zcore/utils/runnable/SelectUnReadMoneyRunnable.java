/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.runnable;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.storage.IConnection;
/*    */ import fr.maxlego08.zauctionhouse.api.transaction.Transaction;
/*    */ import fr.maxlego08.zauctionhouse.dto.TransactionDTO;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.RequestHelper;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*    */ import java.util.List;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Collectors;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class SelectUnReadMoneyRunnable
/*    */   extends ZUtils
/*    */   implements Runnable
/*    */ {
/*    */   private final IConnection connection;
/*    */   private final Player player;
/*    */   private final Consumer<List<Transaction>> consumer;
/*    */   
/*    */   public SelectUnReadMoneyRunnable(IConnection paramIConnection, Player paramPlayer, Consumer<List<Transaction>> paramConsumer) {
/* 24 */     this.connection = paramIConnection;
/* 25 */     this.player = paramPlayer;
/* 26 */     this.consumer = paramConsumer;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 32 */     RequestHelper requestHelper = this.connection.getRequestHelper();
/* 33 */     List list = requestHelper.select("%prefix%transactions", TransactionDTO.class, paramSchema -> {
/*    */           paramSchema.where("seller", this.player.getUniqueId());
/*    */           
/*    */           paramSchema.where("is_read", Boolean.valueOf(false));
/*    */         });
/* 38 */     List<Transaction> list1 = (List)list.stream().map(fr.maxlego08.zauctionhouse.transaction.ZTransaction::new).collect(Collectors.toList());
/*    */     
/* 40 */     this.consumer.accept(list1);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\runnable\SelectUnReadMoneyRunnable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */