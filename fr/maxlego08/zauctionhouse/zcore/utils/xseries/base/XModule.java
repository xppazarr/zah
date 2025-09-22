/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.base;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import org.jetbrains.annotations.ApiStatus.Experimental;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class XModule<XForm extends XModule<XForm, BukkitForm>, BukkitForm>
/*     */   implements XBase<XForm, BukkitForm>
/*     */ {
/*     */   private final BukkitForm bukkitForm;
/*     */   private final String[] names;
/*     */   
/*     */   @Internal
/*     */   protected XModule(BukkitForm paramBukkitForm, String[] paramArrayOfString) {
/*  42 */     this.bukkitForm = paramBukkitForm;
/*  43 */     this.names = paramArrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final String name() {
/*  54 */     return this.names[0];
/*     */   }
/*     */   
/*     */   @Experimental
/*     */   protected void setEnumName(XRegistry<XForm, BukkitForm> paramXRegistry, String paramString) {
/*  59 */     if (this.names[0] != null)
/*  60 */       throw new IllegalStateException("Enum name already set " + paramString + " -> " + Arrays.toString(this.names)); 
/*  61 */     this.names[0] = paramString;
/*     */     
/*  63 */     BukkitForm bukkitForm = paramXRegistry.getBukkit(this.names);
/*  64 */     if (this.bukkitForm != bukkitForm)
/*     */     {
/*  66 */       paramXRegistry.std((XForm)this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public String[] getNames() {
/*  73 */     return this.names;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public final BukkitForm get() {
/*  79 */     return this.bukkitForm;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String toString() {
/*  84 */     return (isSupported() ? "" : "!") + getClass().getSimpleName() + '(' + name() + ')';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int hashCode() {
/*  92 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final boolean equals(Object paramObject) {
/* 101 */     return super.equals(paramObject);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\base\XModule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */