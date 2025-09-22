/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.XAccessFlag;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.objects.ReflectedObject;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.processors.MappedType;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.processors.ProxyMethodInfo;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.processors.ReflectiveAnnotationProcessor;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.Arrays;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
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
/*     */ public final class ReflectiveProxy<T extends ReflectiveProxyObject>
/*     */   implements InvocationHandler
/*     */ {
/*  65 */   private static final Map<Class<?>, ReflectiveProxy<?>> PROXIFIED_CLASS_LOADER0 = new IdentityHashMap<>();
/*  66 */   private static final ClassLoader CLASS_LOADER = ReflectiveProxy.class.getClassLoader();
/*     */   
/*     */   private final Class<?> targetClass;
/*     */   
/*     */   private final Class<T> proxyClass;
/*     */   
/*     */   private T proxy;
/*     */   private final Object instance;
/*     */   private final Map<Method, ProxifiedObject> handles;
/*     */   private final ClassOverloadedMethods<ProxifiedObject> nameMapped;
/*     */   
/*     */   public static <T extends ReflectiveProxyObject> ReflectiveProxy<T> proxify(Class<T> paramClass) {
/*  78 */     ReflectiveProxy<T> reflectiveProxy1 = (ReflectiveProxy)PROXIFIED_CLASS_LOADER0.get(paramClass);
/*  79 */     if (reflectiveProxy1 != null) return reflectiveProxy1;
/*     */     
/*  81 */     ReflectiveAnnotationProcessor reflectiveAnnotationProcessor = new ReflectiveAnnotationProcessor(paramClass);
/*  82 */     reflectiveAnnotationProcessor.process(ReflectiveProxy::descriptorProcessor);
/*     */     
/*  84 */     Set set = reflectiveAnnotationProcessor.getMapped().mappings().entrySet();
/*  85 */     IdentityHashMap<Object, Object> identityHashMap = new IdentityHashMap<>(set.size());
/*  86 */     OverloadedMethod.Builder<ProxifiedObject> builder = new OverloadedMethod.Builder(ReflectiveProxy::descriptorProcessor);
/*     */ 
/*     */ 
/*     */     
/*  90 */     ReflectiveProxy<T> reflectiveProxy2 = new ReflectiveProxy<>(reflectiveAnnotationProcessor.getTargetClass(), paramClass, null, (Map)identityHashMap, builder.build());
/*     */ 
/*     */     
/*  93 */     PROXIFIED_CLASS_LOADER0.put(paramClass, reflectiveProxy2);
/*     */     
/*  95 */     for (Map.Entry entry : set) {
/*  96 */       for (ProxyMethodInfo proxyMethodInfo : ((OverloadedMethod)entry.getValue()).getOverloads()) {
/*  97 */         ReflectedObject reflectedObject = (ReflectedObject)proxyMethodInfo.handle.jvm().unreflect();
/*     */         
/*  99 */         MethodHandle methodHandle = (MethodHandle)proxyMethodInfo.handle.unreflect();
/*     */         
/* 101 */         methodHandle = createDynamicProxy(null, methodHandle);
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
/* 113 */         ProxifiedObject proxifiedObject = new ProxifiedObject(methodHandle, proxyMethodInfo, reflectedObject.accessFlags().contains(XAccessFlag.STATIC), (reflectedObject.type() == ReflectedObject.Type.CONSTRUCTOR), proxyMethodInfo.rType.isDifferent() ? proxify(proxyMethodInfo.rType.synthetic) : null, Arrays.<MappedType>stream(proxyMethodInfo.pTypes).anyMatch(MappedType::isDifferent) ? (ReflectiveProxy<?>[])Arrays.<MappedType>stream(proxyMethodInfo.pTypes).map(paramMappedType -> paramMappedType.isDifferent() ? proxify(paramMappedType.synthetic) : null).toArray(paramInt -> new ReflectiveProxy[paramInt]) : null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 119 */         identityHashMap.put(proxyMethodInfo.interfaceMethod, proxifiedObject);
/* 120 */         builder.add(proxifiedObject, (String)entry.getKey());
/*     */       } 
/*     */     } 
/*     */     
/* 124 */     builder.build(reflectiveProxy2.nameMapped.mappings());
/* 125 */     reflectiveProxy2.proxy = reflectiveProxy2.createProxy();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     for (Method method : reflectiveProxy2.proxy.getClass().getDeclaredMethods()) {
/* 132 */       ProxifiedObject proxifiedObject = reflectiveProxy2.nameMapped.get(method.getName(), () -> descriptorProcessor(paramMethod), true);
/* 133 */       if (proxifiedObject != null) identityHashMap.put(method, proxifiedObject); 
/*     */     } 
/* 135 */     for (Class<?> clazz : reflectiveProxy2.proxy.getClass().getInterfaces()) {
/* 136 */       for (Method method : clazz.getDeclaredMethods()) {
/* 137 */         ProxifiedObject proxifiedObject = reflectiveProxy2.nameMapped.get(method.getName(), () -> descriptorProcessor(paramMethod), true);
/* 138 */         if (proxifiedObject != null) identityHashMap.put(method, proxifiedObject);
/*     */       
/*     */       } 
/*     */     } 
/* 142 */     return reflectiveProxy2;
/*     */   }
/*     */   
/*     */   private static MethodHandle createDynamicProxy(@Nullable Object paramObject, MethodHandle paramMethodHandle) {
/* 146 */     int i = paramMethodHandle.type().parameterCount();
/* 147 */     byte b = (paramObject != null) ? 1 : 0;
/*     */ 
/*     */     
/* 150 */     if (paramObject != null) paramMethodHandle = paramMethodHandle.bindTo(paramObject);
/*     */     
/* 152 */     if (i == b) {
/* 153 */       return paramMethodHandle.asType(MethodType.methodType(Object.class));
/*     */     }
/* 155 */     return paramMethodHandle
/* 156 */       .asSpreader(Object[].class, i - b)
/* 157 */       .asType(MethodType.methodType(Object.class, Object[].class));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String descriptorProcessor(ProxifiedObject paramProxifiedObject) {
/* 163 */     return OverloadedMethod.getParameterDescriptor(MappedType.getRealTypes(paramProxifiedObject.proxyMethodInfo.pTypes));
/*     */   }
/*     */   
/*     */   private static String descriptorProcessor(ProxyMethodInfo paramProxyMethodInfo) {
/* 167 */     return OverloadedMethod.getParameterDescriptor(MappedType.getRealTypes(paramProxyMethodInfo.pTypes));
/*     */   }
/*     */   
/*     */   private static String descriptorProcessor(Method paramMethod) {
/* 171 */     return OverloadedMethod.getParameterDescriptor(paramMethod.getParameterTypes());
/*     */   }
/*     */   
/*     */   public static final class ProxifiedObject
/*     */   {
/*     */     private final MethodHandle handle;
/*     */     private final ProxyMethodInfo proxyMethodInfo;
/*     */     private final boolean isStatic;
/*     */     private final boolean isConstructor;
/*     */     private final ReflectiveProxy<?> rType;
/*     */     private final ReflectiveProxy<?>[] pTypes;
/*     */     
/*     */     public ProxifiedObject(MethodHandle param1MethodHandle, ProxyMethodInfo param1ProxyMethodInfo, boolean param1Boolean1, boolean param1Boolean2, ReflectiveProxy<?> param1ReflectiveProxy, ReflectiveProxy<?>[] param1ArrayOfReflectiveProxy) {
/* 184 */       this.handle = param1MethodHandle;
/* 185 */       this.proxyMethodInfo = param1ProxyMethodInfo;
/* 186 */       this.isStatic = param1Boolean1;
/* 187 */       this.isConstructor = param1Boolean2;
/* 188 */       this.rType = param1ReflectiveProxy;
/* 189 */       this.pTypes = param1ArrayOfReflectiveProxy;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 194 */       return getClass().getSimpleName() + '(' + this.proxyMethodInfo.interfaceMethod + ')';
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ReflectiveProxy(Class<?> paramClass, Class<T> paramClass1, Object paramObject, Map<Method, ProxifiedObject> paramMap, ClassOverloadedMethods<ProxifiedObject> paramClassOverloadedMethods) {
/* 201 */     this.targetClass = paramClass;
/* 202 */     this.proxyClass = paramClass1;
/* 203 */     this.instance = paramObject;
/* 204 */     this.handles = paramMap;
/* 205 */     this.nameMapped = paramClassOverloadedMethods;
/*     */   }
/*     */   
/*     */   public static void checkInterfaceClass(Class<?> paramClass) {
/* 209 */     Objects.requireNonNull(paramClass, "Interface class is null");
/*     */     
/* 211 */     if (!paramClass.isInterface()) {
/* 212 */       throw new IllegalArgumentException("Cannot proxify non-interface class: " + paramClass);
/*     */     }
/* 214 */     if (!ReflectiveProxyObject.class.isAssignableFrom(paramClass)) {
/* 215 */       throw new IllegalArgumentException("The provided interface class must extend ReflectiveProxyObject interface");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public T createProxy() {
/* 223 */     return (T)Proxy.newProxyInstance(CLASS_LOADER, new Class[] { this.proxyClass }, this);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public T proxy() {
/* 228 */     return this.proxy;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Object instance() {
/* 233 */     return this.instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public T bindTo(@NotNull Object paramObject) {
/* 243 */     if (this.instance != null) {
/* 244 */       throw new IllegalStateException("This proxy object already has an instance bound to it: " + this);
/*     */     }
/* 246 */     Objects.requireNonNull(paramObject, "Instance cannot be null");
/* 247 */     if (!this.targetClass.isAssignableFrom(paramObject.getClass())) {
/* 248 */       throw new IllegalArgumentException("The given instance doesn't match the target class: " + paramObject + " -> " + this);
/*     */     }
/*     */     
/* 251 */     IdentityHashMap<Object, Object> identityHashMap1 = new IdentityHashMap<>(this.handles.size());
/* 252 */     IdentityHashMap<Object, Object> identityHashMap2 = new IdentityHashMap<>(this.nameMapped.mappings().size());
/* 253 */     OverloadedMethod.Builder<ProxifiedObject> builder = new OverloadedMethod.Builder(ReflectiveProxy::descriptorProcessor);
/*     */     
/* 255 */     for (Map.Entry entry : this.nameMapped.mappings().entrySet()) {
/* 256 */       for (ProxifiedObject proxifiedObject1 : ((OverloadedMethod)entry.getValue()).getOverloads()) {
/*     */         
/* 258 */         ProxifiedObject proxifiedObject2 = proxifiedObject1;
/* 259 */         ProxifiedObject proxifiedObject3 = (ProxifiedObject)identityHashMap2.get(proxifiedObject2);
/* 260 */         if (proxifiedObject3 == null) {
/*     */           MethodHandle methodHandle;
/* 262 */           if (proxifiedObject2.isStatic || proxifiedObject2.isConstructor) {
/* 263 */             builder.add(proxifiedObject2, (String)entry.getKey());
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/*     */           try {
/* 269 */             methodHandle = (MethodHandle)proxifiedObject2.proxyMethodInfo.handle.unreflect();
/*     */ 
/*     */             
/* 272 */             if (methodHandle.type().parameterCount() == 0) {
/* 273 */               throw new IllegalStateException("Non-static, non-constructor with 0 arguments found: " + methodHandle);
/*     */             }
/* 275 */             methodHandle = createDynamicProxy(paramObject, methodHandle);
/*     */           }
/* 277 */           catch (Exception exception) {
/* 278 */             throw new IllegalStateException("Failed to bind " + paramObject + " to " + (String)entry.getKey() + " -> " + proxifiedObject2.handle + " (static=" + proxifiedObject2.isStatic + ", constructor=" + proxifiedObject2.isConstructor + ')', exception);
/*     */           } 
/*     */           
/* 281 */           proxifiedObject3 = new ProxifiedObject(methodHandle, proxifiedObject2.proxyMethodInfo, proxifiedObject2.isStatic, proxifiedObject2.isConstructor, proxifiedObject2.rType, proxifiedObject2.pTypes);
/* 282 */           identityHashMap2.put(proxifiedObject2, proxifiedObject3);
/*     */         } 
/* 284 */         builder.add(proxifiedObject3, (String)entry.getKey());
/*     */       } 
/*     */     } 
/* 287 */     for (Map.Entry<Method, ProxifiedObject> entry : this.handles.entrySet()) {
/* 288 */       ProxifiedObject proxifiedObject1 = (ProxifiedObject)entry.getValue();
/* 289 */       if (proxifiedObject1.isStatic || proxifiedObject1.isConstructor) {
/* 290 */         identityHashMap1.put(entry.getKey(), proxifiedObject1); continue;
/*     */       } 
/* 292 */       ProxifiedObject proxifiedObject2 = (ProxifiedObject)identityHashMap2.get(proxifiedObject1);
/* 293 */       if (proxifiedObject2 == null) {
/* 294 */         throw new IllegalStateException("Cannot find bound method for " + entry.getKey() + " (" + proxifiedObject1 + "::" + proxifiedObject1.hashCode() + ") in " + builder
/* 295 */             .build() + " - " + identityHashMap2.entrySet().stream()
/* 296 */             .map(paramEntry -> (new StringBuilder()).append(paramEntry.getKey()).append("::").append(paramEntry.hashCode()).toString()).collect(Collectors.toList()));
/*     */       }
/* 298 */       identityHashMap1.put(entry.getKey(), proxifiedObject2);
/*     */     } 
/*     */ 
/*     */     
/* 302 */     ReflectiveProxy<T> reflectiveProxy = new ReflectiveProxy(this.targetClass, this.proxyClass, paramObject, (Map)identityHashMap1, builder.build());
/* 303 */     return reflectiveProxy.createProxy();
/*     */   }
/*     */   
/*     */   private static String getMethodList(Class<?> paramClass, boolean paramBoolean) {
/* 307 */     return ((List)Arrays.<Method>stream(paramBoolean ? paramClass.getDeclaredMethods() : paramClass.getMethods())
/* 308 */       .map(paramMethod -> paramMethod.getName() + "::" + System.identityHashCode(paramMethod))
/* 309 */       .collect(Collectors.toList()))
/* 310 */       .toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object invoke(Object paramObject, Method paramMethod, @Nullable Object[] paramArrayOfObject) {
/* 316 */     int i = paramMethod.getParameterCount();
/* 317 */     Object object = paramMethod.getName();
/*     */     
/* 319 */     if (i == 0) {
/* 320 */       switch (object) {
/*     */         case "instance":
/* 322 */           return this.instance;
/*     */         case "toString":
/* 324 */           return (this.instance == null) ? this.proxyClass.toString() : this.instance.toString();
/*     */         case "hashCode":
/* 326 */           return Integer.valueOf((this.instance == null) ? this.proxyClass.hashCode() : this.instance.hashCode());
/*     */         case "notify":
/* 328 */           if (this.instance == null) { this.proxyClass.notify(); }
/* 329 */           else { this.instance.notify(); }
/* 330 */            return null;
/*     */         case "notifyAll":
/* 332 */           if (this.instance == null) { this.proxyClass.notifyAll(); }
/* 333 */           else { this.instance.notifyAll(); }
/* 334 */            return null;
/*     */         case "wait":
/* 336 */           if (this.instance == null) { this.proxyClass.wait(); }
/* 337 */           else { this.instance.wait(); }
/* 338 */            return null;
/*     */         case "getTargetClass":
/* 340 */           return this.targetClass;
/*     */       } 
/* 342 */     } else if (i == 1) {
/* 343 */       switch (object) {
/*     */         case "bindTo":
/* 345 */           return bindTo(paramArrayOfObject[0]);
/*     */         case "isInstance":
/* 347 */           return Boolean.valueOf(this.targetClass.isInstance(paramArrayOfObject[0]));
/*     */         case "equals":
/* 349 */           return Boolean.valueOf((this.instance == null) ? ((this.proxyClass == paramArrayOfObject[0])) : this.instance.equals(paramArrayOfObject[0]));
/*     */         case "wait":
/* 351 */           if (this.instance == null) { this.proxyClass.wait(((Long)paramArrayOfObject[0]).longValue()); }
/* 352 */           else { this.instance.wait(((Long)paramArrayOfObject[0]).longValue()); }
/* 353 */            return null;
/*     */       } 
/* 355 */     } else if (i == 2 && 
/* 356 */       object.equals("wait")) {
/* 357 */       if (this.instance == null) { this.proxyClass.wait(((Long)paramArrayOfObject[0]).longValue(), ((Integer)paramArrayOfObject[1]).intValue()); }
/* 358 */       else { this.instance.wait(((Long)paramArrayOfObject[0]).longValue(), ((Integer)paramArrayOfObject[1]).intValue()); }
/* 359 */        return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 364 */     ProxifiedObject proxifiedObject = this.handles.get(paramMethod);
/*     */     
/* 366 */     if (proxifiedObject == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 372 */       proxifiedObject = this.nameMapped.get(paramMethod.getName(), () -> descriptorProcessor(paramMethod));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 377 */       this.handles.put(paramMethod, proxifiedObject);
/*     */     } 
/*     */     
/* 380 */     if (!proxifiedObject.isStatic && !proxifiedObject.isConstructor && this.instance == null) {
/* 381 */       throw new IllegalStateException("Cannot invoke non-static non-constructor member handle with when no instance is set");
/*     */     }
/* 383 */     if (proxifiedObject.isConstructor && this.instance != null) {
/* 384 */       throw new IllegalStateException("Cannot invoke constructor twice");
/*     */     }
/*     */ 
/*     */     
/* 388 */     if (proxifiedObject.pTypes != null && paramArrayOfObject != null) {
/* 389 */       for (byte b = 0; b < paramArrayOfObject.length; b++) {
/* 390 */         Object object1 = paramArrayOfObject[b];
/* 391 */         if (object1 instanceof ReflectiveProxyObject) {
/* 392 */           paramArrayOfObject[b] = ((ReflectiveProxyObject)object1).instance();
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 403 */       if (paramArrayOfObject == null) {
/* 404 */         Object object1 = proxifiedObject.handle.invokeExact();
/*     */       } else {
/*     */         
/* 407 */         object = proxifiedObject.handle.invoke(paramArrayOfObject);
/*     */       } 
/* 409 */     } catch (Throwable throwable) {
/* 410 */       throw new IllegalStateException("Failed to execute " + paramMethod + " -> " + proxifiedObject
/* 411 */           .handle + " with args " + (
/* 412 */           (paramArrayOfObject == null) ? "null" : Arrays.stream(paramArrayOfObject).map(paramObject -> (paramObject == null) ? "null" : (paramObject + " (" + paramObject.getClass().getSimpleName() + ')'))), throwable);
/*     */     } 
/*     */     
/* 415 */     if (proxifiedObject.rType != null)
/*     */     {
/* 417 */       object = proxifiedObject.rType.bindTo(object);
/*     */     }
/*     */     
/* 420 */     return object;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 425 */     return "ReflectiveProxy(proxyClass=" + this.proxyClass + ", proxy=" + this.proxy + ", instance=" + this.instance + ", nameMapped=" + this.nameMapped + ')';
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\proxy\ReflectiveProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */