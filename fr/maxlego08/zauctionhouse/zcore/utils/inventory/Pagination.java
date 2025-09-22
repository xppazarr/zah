/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.inventory;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ public class Pagination<T>
/*    */ {
/*    */   public List<T> paginateReverse(List<T> paramList, int paramInt1, int paramInt2) {
/* 19 */     ArrayList<T> arrayList = new ArrayList();
/* 20 */     if (paramInt2 == 0)
/* 21 */       paramInt2 = 1; 
/* 22 */     int i = paramList.size() - 1 - (paramInt2 - 1) * paramInt1;
/* 23 */     int j = i - paramInt1;
/* 24 */     if (j < paramList.size() - paramInt1 && paramList.size() < paramInt1 * paramInt2)
/* 25 */       j = 0; 
/* 26 */     for (int k = i; k > j; k--)
/* 27 */       arrayList.add(paramList.get(k)); 
/* 28 */     return arrayList;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<T> paginate(List<T> paramList, int paramInt1, int paramInt2) {
/* 40 */     ArrayList<T> arrayList = new ArrayList();
/* 41 */     if (paramInt2 <= 0)
/* 42 */       paramInt2 = 1; 
/* 43 */     int i = (paramInt2 - 1) * paramInt1;
/* 44 */     int j = i + paramInt1;
/* 45 */     if (j > paramList.size())
/* 46 */       j = paramList.size(); 
/* 47 */     for (int k = i; k < j; k++)
/* 48 */       arrayList.add(paramList.get(k)); 
/* 49 */     return arrayList;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<T> paginateReverse(Map<?, T> paramMap, int paramInt1, int paramInt2) {
/* 60 */     return paginateReverse(new ArrayList<>(paramMap.values()), paramInt1, paramInt2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<T> paginate(Map<?, T> paramMap, int paramInt1, int paramInt2) {
/* 71 */     return paginate(new ArrayList<>(paramMap.values()), paramInt1, paramInt2);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\inventory\Pagination.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */