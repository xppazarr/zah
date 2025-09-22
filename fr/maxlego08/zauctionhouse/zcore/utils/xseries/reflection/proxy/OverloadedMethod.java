/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ public abstract class OverloadedMethod<T>
/*     */ {
/*     */   public abstract T get(Supplier<String> paramSupplier);
/*     */   
/*     */   public abstract Collection<T> getOverloads();
/*     */   
/*     */   static String getParameterDescriptor(Class<?>[] paramArrayOfClass) {
/*  50 */     StringBuilder stringBuilder = new StringBuilder(paramArrayOfClass.length * 10);
/*     */     
/*  52 */     for (Class<?> clazz1 : paramArrayOfClass) {
/*  53 */       Class<?> clazz2 = clazz1;
/*  54 */       while (clazz2.isArray()) {
/*  55 */         stringBuilder.append('[');
/*  56 */         clazz2 = clazz2.getComponentType();
/*     */       } 
/*     */       
/*  59 */       if (clazz2.isPrimitive()) {
/*  60 */         stringBuilder.append(getDescriptor(clazz2));
/*  61 */       } else if (clazz2 == String.class) {
/*  62 */         stringBuilder.append('@');
/*     */       } else {
/*  64 */         stringBuilder.append(clazz2.getName());
/*     */       } 
/*     */     } 
/*     */     
/*  68 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   static char getDescriptor(Class<?> paramClass) {
/*  72 */     if (paramClass == int.class) return 'I'; 
/*  73 */     if (paramClass == void.class) return 'V'; 
/*  74 */     if (paramClass == boolean.class) return 'Z'; 
/*  75 */     if (paramClass == byte.class) return 'B'; 
/*  76 */     if (paramClass == char.class) return 'C'; 
/*  77 */     if (paramClass == short.class) return 'S'; 
/*  78 */     if (paramClass == double.class) return 'D'; 
/*  79 */     if (paramClass == float.class) return 'F'; 
/*  80 */     if (paramClass == long.class) return 'J'; 
/*  81 */     throw new AssertionError("Unknown primitive: " + paramClass);
/*     */   }
/*     */   
/*     */   public static final class Builder<T> {
/*  85 */     private final Map<String, Map<String, T>> descriptorMap = new HashMap<>(10); private final Function<T, String> descritporProcessor;
/*     */     
/*     */     public Builder(Function<T, String> param1Function) {
/*  88 */       this.descritporProcessor = param1Function;
/*     */     }
/*     */     public void add(T param1T, String param1String) {
/*  91 */       Map<String, T> map = this.descriptorMap.computeIfAbsent(param1String, param1String -> new HashMap<>(3));
/*  92 */       String str = this.descritporProcessor.apply(param1T);
/*  93 */       if (map.put(str, param1T) != null) {
/*  94 */         throw new IllegalArgumentException("Method named '" + param1String + "' with descriptor '" + str + "' was already added: " + map);
/*     */       }
/*     */     }
/*     */     
/*     */     public ClassOverloadedMethods<T> build() {
/*  99 */       HashMap<Object, Object> hashMap = new HashMap<>(this.descriptorMap.size());
/* 100 */       ClassOverloadedMethods<T> classOverloadedMethods = new ClassOverloadedMethods((Map)hashMap);
/* 101 */       build((Map)hashMap);
/* 102 */       return classOverloadedMethods;
/*     */     }
/*     */     
/*     */     public void build(Map<String, OverloadedMethod<T>> param1Map) {
/* 106 */       for (Map.Entry<String, Map<String, T>> entry : this.descriptorMap.entrySet()) {
/*     */         OverloadedMethod.Multi<T> multi;
/* 108 */         if (((Map)entry.getValue()).size() == 1) {
/* 109 */           OverloadedMethod.Single single = new OverloadedMethod.Single(((Map)entry.getValue()).values().iterator().next());
/*     */         } else {
/* 111 */           multi = new OverloadedMethod.Multi((Map<String, ?>)entry.getValue());
/*     */         } 
/*     */         
/* 114 */         param1Map.put((String)entry.getKey(), multi);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class Single<T> extends OverloadedMethod<T> { private final T object;
/*     */     
/*     */     public Single(T param1T) {
/* 122 */       this.object = param1T;
/*     */     }
/*     */     
/*     */     public T get(Supplier<String> param1Supplier) {
/* 126 */       return this.object;
/*     */     }
/*     */ 
/*     */     
/*     */     public Collection<T> getOverloads() {
/* 131 */       return Collections.singleton(this.object);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 136 */       return "Single";
/*     */     } }
/*     */   
/*     */   private static final class Multi<T> extends OverloadedMethod<T> {
/*     */     private final Map<String, T> descriptorMap;
/*     */     
/*     */     public Multi(Map<String, T> param1Map) {
/* 143 */       this.descriptorMap = param1Map;
/*     */     }
/*     */     
/*     */     public T get(Supplier<String> param1Supplier) {
/* 147 */       return this.descriptorMap.get(param1Supplier.get());
/*     */     }
/*     */ 
/*     */     
/*     */     public Collection<T> getOverloads() {
/* 152 */       return this.descriptorMap.values();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 157 */       return "Multi(" + this.descriptorMap.keySet() + ')';
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\proxy\OverloadedMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */