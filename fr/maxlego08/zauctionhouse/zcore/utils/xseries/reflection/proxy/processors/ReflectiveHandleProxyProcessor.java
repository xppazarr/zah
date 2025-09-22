/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.processors;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.ReflectiveHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.XAccessFlag;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.ConstructorMemberHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.FieldMemberHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.MethodMemberHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.classes.ClassHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.ReflectiveProxy;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations.Ignore;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Parameter;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
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
/*     */ @Internal
/*     */ public class ReflectiveHandleProxyProcessor
/*     */ {
/*     */   public static <T extends fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.ReflectiveProxyObject> T proxify(@NotNull Class<T> paramClass, @NotNull ClassHandle paramClassHandle, @NotNull ReflectiveHandle<?>... paramVarArgs) {
/*  46 */     Set<?> set = Collections.newSetFromMap(new IdentityHashMap<>());
/*  47 */     set.addAll(Arrays.asList((Object[])paramVarArgs));
/*     */     
/*  49 */     Method[] arrayOfMethod = paramClass.getMethods();
/*  50 */     IdentityHashMap<Object, Object> identityHashMap = new IdentityHashMap<>(arrayOfMethod.length);
/*  51 */     for (Method method : arrayOfMethod) {
/*  52 */       if (!ReflectiveAnnotationProcessor.isAnnotationInherited(paramClass, method, (Class)Ignore.class)) {
/*     */         
/*  54 */         String str = method.getName();
/*  55 */         Iterator<?> iterator = set.iterator();
/*  56 */         while (iterator.hasNext()) {
/*  57 */           ReflectiveHandle reflectiveHandle = (ReflectiveHandle)iterator.next();
/*  58 */           if (reflectiveHandle instanceof FieldMemberHandle) {
/*  59 */             FieldMemberHandle fieldMemberHandle = (FieldMemberHandle)reflectiveHandle;
/*  60 */             if (fieldMemberHandle.getPossibleNames().stream().anyMatch(paramString2 -> paramString2.equals(paramString1))) {
/*  61 */               iterator.remove();
/*  62 */               identityHashMap.put(method, new ReflectiveProxy.ProxifiedObject((MethodHandle)fieldMemberHandle
/*  63 */                     .unreflect(), null, fieldMemberHandle
/*  64 */                     .getAccessFlags().contains(XAccessFlag.STATIC), false, null, null)); break;
/*     */             }  continue;
/*     */           } 
/*  67 */           if (reflectiveHandle instanceof MethodMemberHandle) {
/*  68 */             MethodMemberHandle methodMemberHandle = (MethodMemberHandle)reflectiveHandle;
/*  69 */             if (methodMemberHandle.getPossibleNames().stream().anyMatch(paramString2 -> paramString2.equals(paramString1))) {
/*  70 */               iterator.remove();
/*  71 */               identityHashMap.put(method, new ReflectiveProxy.ProxifiedObject((MethodHandle)methodMemberHandle
/*  72 */                     .unreflect(), null, methodMemberHandle
/*  73 */                     .getAccessFlags().contains(XAccessFlag.STATIC), false, null, null)); break;
/*     */             }  continue;
/*     */           } 
/*  76 */           if (reflectiveHandle instanceof ConstructorMemberHandle && method
/*  77 */             .getReturnType() == paramClass && str
/*  78 */             .equals(paramClass.getName())) {
/*     */             Constructor constructor;
/*  80 */             ConstructorMemberHandle constructorMemberHandle = (ConstructorMemberHandle)reflectiveHandle;
/*  81 */             if ((constructorMemberHandle.getParameterTypes()).length != method.getParameterCount()) {
/*     */               continue;
/*     */             }
/*     */             try {
/*  85 */               constructor = constructorMemberHandle.reflectJvm();
/*  86 */             } catch (ReflectiveOperationException reflectiveOperationException) {
/*  87 */               throw new IllegalStateException("Failed to map " + method, reflectiveOperationException);
/*     */             } 
/*     */             
/*  90 */             byte b = 0;
/*  91 */             for (Parameter parameter : method.getParameters()) {
/*  92 */               if (constructor.getParameters()[b].getType() != parameter.getType()) {
/*  93 */                 b = -1;
/*     */                 break;
/*     */               } 
/*  96 */               b++;
/*     */             } 
/*  98 */             if (b == -1)
/*     */               continue; 
/* 100 */             iterator.remove();
/* 101 */             identityHashMap.put(method, new ReflectiveProxy.ProxifiedObject((MethodHandle)constructorMemberHandle
/* 102 */                   .unreflect(), null, false, true, null, null));
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 112 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\proxy\processors\ReflectiveHandleProxyProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */