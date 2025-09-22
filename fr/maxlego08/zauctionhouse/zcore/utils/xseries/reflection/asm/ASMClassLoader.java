/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.asm;
/*     */ 
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.reflect.Method;
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
/*     */ final class ASMClassLoader
/*     */   extends ClassLoader
/*     */ {
/*     */   private static final String DEFINE_CLASS = "defineClass";
/*     */   
/*     */   protected Class<?> defineClass(String paramString, byte[] paramArrayOfbyte) {
/*  45 */     return defineClass(asmTypeToBinary(paramString), paramArrayOfbyte, 0, paramArrayOfbyte.length);
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
/*     */ 
/*     */   
/*     */   private static Class<?> defineClassLombockTransplanter(String paramString, byte[] paramArrayOfbyte, ClassLoader paramClassLoader) {
/*     */     try {
/*  61 */       Class<?> clazz1 = Class.forName("java.lang.invoke.MethodHandles");
/*  62 */       Class<?> clazz2 = Class.forName("java.lang.invoke.MethodHandle");
/*  63 */       Class<?> clazz3 = Class.forName("java.lang.invoke.MethodType");
/*  64 */       Class<?> clazz4 = Class.forName("java.lang.invoke.MethodHandles$Lookup");
/*  65 */       Method method1 = clazz1.getDeclaredMethod("lookup", new Class[0]);
/*  66 */       Method method2 = clazz3.getDeclaredMethod("methodType", new Class[] { Class.class, Class[].class });
/*  67 */       Method method3 = clazz4.getDeclaredMethod("findVirtual", new Class[] { Class.class, String.class, clazz3 });
/*  68 */       Method method4 = clazz2.getDeclaredMethod("invokeWithArguments", new Class[] { Object[].class });
/*     */       
/*  70 */       Object object1 = method1.invoke(null, new Object[0]);
/*  71 */       Object object2 = method2.invoke(null, new Object[] { Class.class, { String.class, byte[].class, int.class, int.class } });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  79 */       Object object3 = method3.invoke(object1, new Object[] { paramClassLoader.getClass(), "defineClass", object2 });
/*  80 */       return (Class)method4.invoke(object3, new Object[] { { paramClassLoader, paramString, paramArrayOfbyte, 
/*  81 */               Integer.valueOf(0), Integer.valueOf(paramArrayOfbyte.length) } });
/*  82 */     } catch (ClassNotFoundException|NoSuchMethodException|IllegalAccessException|java.lang.reflect.InvocationTargetException classNotFoundException) {
/*     */ 
/*     */       
/*  85 */       throw new IllegalStateException(classNotFoundException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> defineClassJLA(String paramString, byte[] paramArrayOfbyte) {
/*     */     try {
/*  93 */       Class<?> clazz = Class.forName("jdk.internal.reflect.ClassDefiner");
/*  94 */       Method method = clazz.getDeclaredMethod("defineClass", new Class[] { String.class, byte[].class, int.class, int.class, ClassLoader.class });
/*  95 */       return (Class)method.invoke(null, new Object[] { paramString, paramArrayOfbyte, Integer.valueOf(0), Integer.valueOf(paramArrayOfbyte.length), XReflectASM.class.getClassLoader() });
/*  96 */     } catch (ClassNotFoundException|NoSuchMethodException|IllegalAccessException|java.lang.reflect.InvocationTargetException classNotFoundException) {
/*     */       
/*  98 */       throw new IllegalStateException(classNotFoundException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String asmTypeToBinary(String paramString) {
/* 103 */     return paramString.replace('/', '.');
/*     */   }
/*     */   
/*     */   private static Class<?> methodHandleLoadClass(byte[] paramArrayOfbyte) {
/* 107 */     MethodHandles.Lookup lookup = MethodHandles.lookup();
/*     */     
/*     */     try {
/* 110 */       MethodHandle methodHandle = lookup.findVirtual(MethodHandles.Lookup.class, "defineClass", 
/* 111 */           MethodType.methodType(Class.class, byte[].class));
/*     */       
/* 113 */       return methodHandle.invoke(lookup, paramArrayOfbyte);
/* 114 */     } catch (Throwable throwable) {
/* 115 */       throw new IllegalStateException(throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> loadClass(String paramString, byte[] paramArrayOfbyte) {
/*     */     Class<?> clazz;
/*     */     try {
/* 124 */       ClassLoader classLoader = ClassLoader.getSystemClassLoader();
/* 125 */       Class<?> clazz1 = Class.forName("java.lang.ClassLoader");
/*     */       
/* 127 */       Method method = clazz1.getDeclaredMethod("defineClass", new Class[] { String.class, byte[].class, int.class, int.class });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 132 */       method.setAccessible(true);
/*     */       try {
/* 134 */         Object[] arrayOfObject = { paramString, paramArrayOfbyte, new Integer(0), new Integer(paramArrayOfbyte.length) };
/* 135 */         clazz = (Class)method.invoke(classLoader, arrayOfObject);
/*     */       } finally {
/* 137 */         method.setAccessible(false);
/*     */       } 
/* 139 */     } catch (Exception exception) {
/* 140 */       throw new IllegalStateException("Failed to load class " + paramString + " into the system class loader", exception);
/*     */     } 
/* 142 */     return clazz;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\asm\ASMClassLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */