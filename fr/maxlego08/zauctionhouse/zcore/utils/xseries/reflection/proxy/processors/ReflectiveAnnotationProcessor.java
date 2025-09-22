/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.processors;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.ReflectiveHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.XAccessFlag;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.XReflection;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.aggregate.VersionHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.ConstructorMemberHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.FieldMemberHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.MethodMemberHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.NameableReflectiveHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.classes.ClassHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.classes.DynamicClassHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.classes.PackageHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.classes.StaticClassHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.minecraft.MinecraftClassHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.ClassOverloadedMethods;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.OverloadedMethod;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.ReflectiveProxy;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.ReflectiveProxyObject;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations.Constructor;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations.Field;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations.Final;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations.Ignore;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations.MappedMinecraftName;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations.Private;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations.Protected;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations.Proxify;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations.ReflectMinecraftPackage;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations.ReflectName;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations.Static;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Function;
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
/*     */ @Internal
/*     */ public final class ReflectiveAnnotationProcessor
/*     */ {
/*     */   private final Class<? extends ReflectiveProxyObject> interfaceClass;
/*     */   private ClassOverloadedMethods<ProxyMethodInfo> mapped;
/*     */   private Class<?> targetClass;
/*     */   
/*     */   public ReflectiveAnnotationProcessor(Class<? extends ReflectiveProxyObject> paramClass) {
/*  58 */     ReflectiveProxy.checkInterfaceClass(paramClass);
/*  59 */     this.interfaceClass = paramClass;
/*     */   }
/*     */   
/*     */   private void error(String paramString) {
/*  63 */     error(paramString, null);
/*     */   }
/*     */   
/*     */   private void error(String paramString, Throwable paramThrowable) {
/*  67 */     throw new IllegalStateException(paramString + " (Proxified Interface: " + this.interfaceClass + ')', paramThrowable);
/*     */   }
/*     */   
/*     */   protected static boolean isAnnotationInherited(Class<?> paramClass, Method paramMethod, Class<? extends Annotation> paramClass1) {
/*     */     try {
/*  72 */       Method method = paramClass.getDeclaredMethod(paramMethod.getName(), paramMethod.getParameterTypes());
/*  73 */       if (method.isAnnotationPresent(paramClass1)) return true; 
/*  74 */     } catch (NoSuchMethodException noSuchMethodException) {}
/*     */ 
/*     */     
/*  77 */     for (Class<?> clazz : paramClass.getInterfaces()) {
/*  78 */       if (isAnnotationInherited(clazz, paramMethod, paramClass1)) return true;
/*     */     
/*     */     } 
/*  81 */     return false;
/*     */   }
/*     */   
/*     */   public void loadDependencies(Function<Class<?>, Boolean> paramFunction) {
/*  85 */     for (OverloadedMethod overloadedMethod : this.mapped.mappings().values()) {
/*  86 */       for (ProxyMethodInfo proxyMethodInfo : overloadedMethod.getOverloads()) {
/*  87 */         loadDependency(proxyMethodInfo.rType, paramFunction);
/*  88 */         for (MappedType mappedType : proxyMethodInfo.pTypes) {
/*  89 */           loadDependency(mappedType, paramFunction);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadDependency(MappedType paramMappedType, Function<Class<?>, Boolean> paramFunction) {
/*  97 */     if (ReflectiveProxyObject.class.isAssignableFrom(paramMappedType.synthetic) && paramMappedType.synthetic != this.interfaceClass && 
/*     */       
/*  99 */       !((Boolean)paramFunction.apply(paramMappedType.synthetic)).booleanValue()) {
/* 100 */       XReflection.proxify(paramMappedType.synthetic);
/*     */     }
/*     */   }
/*     */   
/*     */   public void process(Function<ProxyMethodInfo, String> paramFunction) {
/* 105 */     ClassHandle classHandle = processTargetClass();
/* 106 */     Method[] arrayOfMethod = this.interfaceClass.getMethods();
/* 107 */     OverloadedMethod.Builder builder = new OverloadedMethod.Builder(paramFunction);
/*     */     
/* 109 */     for (Method method : arrayOfMethod) {
/*     */       
/* 111 */       if (!isAnnotationInherited(this.interfaceClass, method, (Class)Ignore.class)) {
/*     */         MappedType mappedType; MethodMemberHandle methodMemberHandle;
/* 113 */         boolean bool1 = method.isAnnotationPresent((Class)Static.class);
/* 114 */         boolean bool2 = method.isAnnotationPresent((Class)Final.class);
/*     */         
/* 116 */         MappedType[] arrayOfMappedType = new MappedType[0];
/*     */ 
/*     */         
/* 119 */         if (method.isAnnotationPresent((Class)Constructor.class)) {
/* 120 */           Class<?> clazz = method.getReturnType();
/* 121 */           if (clazz != this.targetClass && clazz != this.interfaceClass && clazz != Object.class) {
/* 122 */             error("Method marked with @Constructor must return Object.class, " + this.targetClass + " or " + this.interfaceClass);
/*     */           }
/*     */           
/* 125 */           mappedType = unwrap(clazz);
/* 126 */           arrayOfMappedType = unwrap(method.getParameterTypes());
/*     */           
/* 128 */           ConstructorMemberHandle constructorMemberHandle = classHandle.constructor(MappedType.getRealTypes(arrayOfMappedType));
/* 129 */           if (bool1) error("Constructor cannot be static: " + method); 
/* 130 */           if (bool2) error("Constructor cannot be final: " + method); 
/* 131 */         } else if (method.isAnnotationPresent((Class)Field.class)) {
/* 132 */           FieldMemberHandle fieldMemberHandle2 = classHandle.field();
/* 133 */           if (method.getReturnType() == void.class) {
/* 134 */             fieldMemberHandle2.setter();
/* 135 */             if (method.getParameterCount() != 1) {
/* 136 */               error("Field setter method must have only one parameter: " + method);
/*     */             }
/*     */             
/* 139 */             MappedType mappedType1 = unwrap(method.getParameterTypes()[0]);
/* 140 */             mappedType = new MappedType(void.class, void.class);
/* 141 */             arrayOfMappedType = new MappedType[] { mappedType1 };
/* 142 */             fieldMemberHandle2.returns(mappedType1.real);
/*     */           } else {
/* 144 */             fieldMemberHandle2.getter();
/* 145 */             if (method.getParameterCount() != 0) {
/* 146 */               error("Field getter method must not have any parameters: " + method);
/*     */             }
/*     */             
/* 149 */             mappedType = unwrap(method.getReturnType());
/* 150 */             fieldMemberHandle2.returns(mappedType.real);
/*     */           } 
/*     */           
/* 153 */           if (bool1) fieldMemberHandle2.asStatic(); 
/* 154 */           if (bool2) fieldMemberHandle2.asFinal(); 
/* 155 */           FieldMemberHandle fieldMemberHandle1 = fieldMemberHandle2;
/*     */         } else {
/* 157 */           mappedType = unwrap(method.getReturnType());
/* 158 */           arrayOfMappedType = unwrap(method.getParameterTypes());
/*     */ 
/*     */ 
/*     */           
/* 162 */           MethodMemberHandle methodMemberHandle1 = classHandle.method().returns(mappedType.real).parameters(MappedType.getRealTypes(arrayOfMappedType));
/* 163 */           if (bool1) methodMemberHandle1 = methodMemberHandle1.asStatic(); 
/* 164 */           if (bool2) error("Declaring method as final has no effect: " + method); 
/* 165 */           methodMemberHandle = methodMemberHandle1;
/*     */         } 
/*     */         
/* 168 */         boolean bool = false;
/* 169 */         if (method.isAnnotationPresent((Class)Private.class)) {
/* 170 */           bool = true;
/* 171 */           methodMemberHandle.getAccessFlags().add(XAccessFlag.PRIVATE);
/*     */         } 
/* 173 */         if (method.isAnnotationPresent((Class)Protected.class)) {
/* 174 */           if (bool)
/* 175 */             error("Cannot have two visibility modifier private and protected for " + method); 
/* 176 */           methodMemberHandle.getAccessFlags().add(XAccessFlag.PRIVATE);
/*     */         } 
/*     */         
/* 179 */         if (methodMemberHandle instanceof NameableReflectiveHandle) {
/* 180 */           ((NameableReflectiveHandle)methodMemberHandle).named(new String[] { method.getName() });
/* 181 */           reflectNames((NameableReflectiveHandle)methodMemberHandle, method);
/*     */         } 
/*     */         
/* 184 */         ReflectiveHandle<?> reflectiveHandle = methodMemberHandle.cached();
/*     */         try {
/* 186 */           reflectiveHandle.reflect();
/* 187 */         } catch (ReflectiveOperationException reflectiveOperationException) {
/* 188 */           error("Failed to map " + method, reflectiveOperationException);
/*     */         } 
/*     */         
/* 191 */         System.out.println("Adding method of type " + method.getName() + ": " + mappedType + " - " + Arrays.toString((Object[])arrayOfMappedType));
/* 192 */         ProxyMethodInfo proxyMethodInfo = new ProxyMethodInfo(reflectiveHandle, method, mappedType, arrayOfMappedType);
/* 193 */         builder.add(proxyMethodInfo, method.getName());
/*     */       } 
/*     */     } 
/* 196 */     this.mapped = builder.build();
/*     */   } @NotNull
/*     */   public ClassHandle processTargetClass() {
/*     */     MinecraftClassHandle minecraftClassHandle;
/*     */     boolean bool;
/* 201 */     Proxify proxify = this.interfaceClass.<Proxify>getAnnotation(Proxify.class);
/* 202 */     ReflectMinecraftPackage reflectMinecraftPackage = this.interfaceClass.<ReflectMinecraftPackage>getAnnotation(ReflectMinecraftPackage.class);
/*     */     
/* 204 */     if (proxify == null && reflectMinecraftPackage == null) {
/* 205 */       error("Proxy interface is not annotated with @Class or @ReflectMinecraftPackage");
/*     */     }
/* 207 */     if (proxify != null && reflectMinecraftPackage != null) {
/* 208 */       error("Proxy interface cannot contain both @Class or @ReflectMinecraftPackage");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 213 */     if (proxify != null) {
/* 214 */       if (proxify.target() != void.class) {
/* 215 */         StaticClassHandle staticClassHandle = XReflection.of(proxify.target());
/* 216 */         bool = true;
/*     */       } else {
/* 218 */         DynamicClassHandle dynamicClassHandle2 = XReflection.classHandle();
/* 219 */         DynamicClassHandle dynamicClassHandle1 = dynamicClassHandle2;
/* 220 */         dynamicClassHandle2.inPackage(proxify.packageName());
/* 221 */         bool = proxify.ignoreCurrentName();
/*     */       } 
/*     */     } else {
/* 224 */       MinecraftClassHandle minecraftClassHandle1 = XReflection.ofMinecraft();
/* 225 */       minecraftClassHandle = minecraftClassHandle1;
/* 226 */       minecraftClassHandle1.inPackage((PackageHandle)reflectMinecraftPackage.type(), reflectMinecraftPackage.packageName());
/* 227 */       bool = reflectMinecraftPackage.ignoreCurrentName();
/*     */     } 
/*     */     
/* 230 */     if (minecraftClassHandle instanceof DynamicClassHandle) {
/* 231 */       DynamicClassHandle dynamicClassHandle = (DynamicClassHandle)minecraftClassHandle;
/* 232 */       if (!bool) dynamicClassHandle.named(new String[] { this.interfaceClass.getSimpleName() }); 
/* 233 */       reflectNames((NameableReflectiveHandle)dynamicClassHandle, this.interfaceClass);
/*     */     } 
/*     */     
/* 236 */     this.targetClass = (Class)minecraftClassHandle.unreflect();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 245 */     MappedType.LOOK_AHEAD.put(this.interfaceClass, this.targetClass);
/*     */     
/* 247 */     return (ClassHandle)minecraftClassHandle;
/*     */   }
/*     */   
/*     */   public Class<?> getTargetClass() {
/* 251 */     return this.targetClass;
/*     */   }
/*     */   
/*     */   public ClassOverloadedMethods<ProxyMethodInfo> getMapped() {
/* 255 */     return this.mapped;
/*     */   }
/*     */   
/*     */   private void reflectNames(NameableReflectiveHandle paramNameableReflectiveHandle, AnnotatedElement paramAnnotatedElement) {
/* 259 */     MappedMinecraftName[] arrayOfMappedMinecraftName = paramAnnotatedElement.<MappedMinecraftName>getDeclaredAnnotationsByType(MappedMinecraftName.class);
/* 260 */     ReflectName[] arrayOfReflectName = paramAnnotatedElement.<ReflectName>getDeclaredAnnotationsByType(ReflectName.class);
/*     */     
/* 262 */     for (MappedMinecraftName mappedMinecraftName : arrayOfMappedMinecraftName) {
/* 263 */       reflectNames0(paramNameableReflectiveHandle, mappedMinecraftName.names());
/*     */     }
/* 265 */     reflectNames0(paramNameableReflectiveHandle, arrayOfReflectName);
/*     */   }
/*     */   
/*     */   private void reflectNames0(NameableReflectiveHandle paramNameableReflectiveHandle, ReflectName[] paramArrayOfReflectName) {
/* 269 */     if (paramArrayOfReflectName.length == 0)
/* 270 */       return;  VersionHandle versionHandle = null;
/* 271 */     String[] arrayOfString = null;
/*     */     
/* 273 */     byte b = 0;
/* 274 */     for (ReflectName reflectName : paramArrayOfReflectName) {
/* 275 */       b++;
/* 276 */       if (arrayOfString != null) {
/* 277 */         error("Cannot contain more tha one @ReflectName if no version is specified");
/*     */       }
/* 279 */       if (!reflectName.version().isEmpty()) {
/* 280 */         if (b == paramArrayOfReflectName.length) {
/* 281 */           error("Last @ReflectName should not contain version");
/*     */         }
/* 283 */         int[] arrayOfInt = Arrays.<String>stream(reflectName.version().split("\\.")).mapToInt(Integer::parseInt).toArray();
/* 284 */         if (versionHandle == null) {
/* 285 */           if (arrayOfInt.length == 1) versionHandle = XReflection.v(arrayOfInt[0], reflectName.value()); 
/* 286 */           if (arrayOfInt.length == 2) versionHandle = XReflection.v(arrayOfInt[1], reflectName.value()); 
/* 287 */           if (arrayOfInt.length == 3) versionHandle = XReflection.v(arrayOfInt[1], arrayOfInt[2], reflectName.value()); 
/*     */         } else {
/* 289 */           if (arrayOfInt.length == 1) versionHandle.v(arrayOfInt[0], reflectName.value()); 
/* 290 */           if (arrayOfInt.length == 2) versionHandle.v(arrayOfInt[1], reflectName.value()); 
/* 291 */           if (arrayOfInt.length == 3) versionHandle.v(arrayOfInt[1], arrayOfInt[2], reflectName.value()); 
/*     */         } 
/* 293 */       } else if (versionHandle != null) {
/* 294 */         if (b != paramArrayOfReflectName.length) {
/* 295 */           error("One of @ReflectName doesn't contain a version.");
/*     */         } else {
/* 297 */           arrayOfString = (String[])versionHandle.orElse(reflectName.value());
/*     */         } 
/*     */       } else {
/* 300 */         arrayOfString = reflectName.value();
/*     */       } 
/*     */     } 
/*     */     
/* 304 */     paramNameableReflectiveHandle.named(arrayOfString);
/*     */   }
/*     */   
/*     */   private MappedType[] unwrap(Class<?>[] paramArrayOfClass) {
/* 308 */     MappedType[] arrayOfMappedType = new MappedType[paramArrayOfClass.length];
/*     */     
/* 310 */     for (byte b = 0; b < paramArrayOfClass.length; b++) {
/* 311 */       Class<?> clazz = paramArrayOfClass[b];
/* 312 */       arrayOfMappedType[b] = unwrap(clazz);
/*     */     } 
/*     */     
/* 315 */     return arrayOfMappedType;
/*     */   }
/*     */ 
/*     */   
/*     */   private MappedType unwrap(Class<?> paramClass) {
/* 320 */     if (paramClass == this.interfaceClass)
/*     */     {
/* 322 */       return new MappedType(this.interfaceClass, this.targetClass);
/*     */     }
/*     */     
/* 325 */     if (ReflectiveProxyObject.class.isAssignableFrom(paramClass)) {
/* 326 */       Class<?> clazz = MappedType.getMappedTypeOrCreate((Class)paramClass);
/* 327 */       return new MappedType(paramClass, clazz);
/*     */     } 
/*     */     
/* 330 */     return new MappedType(paramClass, paramClass);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\proxy\processors\ReflectiveAnnotationProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */