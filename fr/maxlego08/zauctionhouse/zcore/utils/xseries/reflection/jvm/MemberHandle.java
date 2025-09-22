/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.ReflectiveHandle;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.XAccessFlag;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.classes.ClassHandle;
/*    */ import java.lang.invoke.MethodHandle;
/*    */ import java.lang.reflect.Member;
/*    */ import java.lang.reflect.Modifier;
/*    */ import java.util.EnumSet;
/*    */ import java.util.Set;
/*    */ import org.intellij.lang.annotations.Language;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MemberHandle
/*    */   implements ReflectiveHandle<MethodHandle>
/*    */ {
/* 44 */   protected Set<XAccessFlag> accessFlags = EnumSet.noneOf(XAccessFlag.class);
/*    */   
/*    */   protected MemberHandle(ClassHandle paramClassHandle) {
/* 47 */     this.clazz = paramClassHandle;
/*    */   }
/*    */   protected final ClassHandle clazz;
/*    */   @Internal
/*    */   public Set<XAccessFlag> getAccessFlags() {
/* 52 */     return this.accessFlags;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ClassHandle getClassHandle() {
/* 60 */     return this.clazz;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MemberHandle makeAccessible() {
/* 67 */     this.accessFlags.add(XAccessFlag.PRIVATE);
/* 68 */     return this;
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
/*    */   protected <T extends java.lang.reflect.AccessibleObject & Member> T handleAccessible(T paramT) {
/* 92 */     if (this.accessFlags.contains(XAccessFlag.PRIVATE) || Modifier.isPrivate(((Member)paramT).getDeclaringClass().getModifiers()))
/* 93 */       paramT.setAccessible(true); 
/* 94 */     return paramT;
/*    */   }
/*    */   
/*    */   public abstract MemberHandle signature(@Language("Java") String paramString);
/*    */   
/*    */   public abstract MethodHandle reflect();
/*    */   
/*    */   public abstract <T extends java.lang.reflect.AccessibleObject & Member> T reflectJvm();
/*    */   
/*    */   public abstract MemberHandle copy();
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\jvm\MemberHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */