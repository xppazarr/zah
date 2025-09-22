/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.processors;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.ReflectiveHandle;
/*    */ import java.lang.reflect.Method;
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
/*    */ @Internal
/*    */ public class ProxyMethodInfo
/*    */ {
/*    */   public final ReflectiveHandle<?> handle;
/*    */   public final Method interfaceMethod;
/*    */   public final MappedType rType;
/*    */   public final MappedType[] pTypes;
/*    */   
/*    */   public ProxyMethodInfo(ReflectiveHandle<?> paramReflectiveHandle, Method paramMethod, MappedType paramMappedType, MappedType[] paramArrayOfMappedType) {
/* 38 */     this.handle = paramReflectiveHandle;
/* 39 */     this.interfaceMethod = paramMethod;
/* 40 */     this.rType = paramMappedType;
/* 41 */     this.pTypes = paramArrayOfMappedType;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 46 */     return getClass().getSimpleName() + '(' + this.interfaceMethod + ')';
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\proxy\processors\ProxyMethodInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */