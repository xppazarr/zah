/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.database;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.SchemaBuilder;
/*     */ import java.util.function.Consumer;
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
/*     */ public abstract class Migration
/*     */ {
/*     */   private boolean alter = false;
/*     */   
/*     */   public abstract void up();
/*     */   
/*     */   protected void create(String paramString, Consumer<Schema> paramConsumer) {
/*  30 */     SchemaBuilder.create(this, paramString, paramConsumer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void create(String paramString, Class<?> paramClass) {
/*  40 */     SchemaBuilder.create(this, paramString, paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void index(String paramString1, String paramString2) {
/*  50 */     SchemaBuilder.createIndex(this, paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drop(String paramString) {
/*  59 */     SchemaBuilder.drop(this, paramString);
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
/*     */   protected void modify(String paramString, Consumer<Schema> paramConsumer) {
/*  71 */     SchemaBuilder.modify(this, paramString, paramConsumer);
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
/*     */   protected void createOrAlter(String paramString, Consumer<Schema> paramConsumer) {
/*  84 */     create(paramString, paramConsumer);
/*  85 */     this.alter = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createOrAlter(String paramString, Class<?> paramClass) {
/*  96 */     create(paramString, paramClass);
/*  97 */     this.alter = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAlter() {
/* 108 */     return this.alter;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\database\Migration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */