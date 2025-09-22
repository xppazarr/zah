/*     */ package fr.maxlego08.zauctionhouse.message;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.api.messages.ITitleMessage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZTitleMessage
/*     */   implements ITitleMessage
/*     */ {
/*     */   private int end;
/*     */   private int start;
/*     */   private int time;
/*     */   private String title;
/*     */   private String subTitle;
/*     */   
/*     */   public ZTitleMessage(int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2) {
/*  22 */     this.end = paramInt1;
/*  23 */     this.start = paramInt2;
/*  24 */     this.time = paramInt3;
/*  25 */     this.title = paramString1;
/*  26 */     this.subTitle = paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnd() {
/*  33 */     return this.end;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStart() {
/*  40 */     return this.start;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTime() {
/*  47 */     return this.time;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTitle() {
/*  54 */     return this.title;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSubTitle() {
/*  61 */     return this.subTitle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnd(int paramInt) {
/*  69 */     this.end = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStart(int paramInt) {
/*  77 */     this.start = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTime(int paramInt) {
/*  85 */     this.time = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitle(String paramString) {
/*  93 */     this.title = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubTitle(String paramString) {
/* 101 */     this.subTitle = paramString;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\message\ZTitleMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */