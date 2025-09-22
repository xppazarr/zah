/*     */ package fr.maxlego08.zauctionhouse.scoreboard;
/*     */ 
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Predicate;
/*     */ import org.bukkit.Bukkit;
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
/*     */ public final class FastReflection
/*     */ {
/*     */   private static final String NM_PACKAGE = "net.minecraft";
/*     */   public static final String OBC_PACKAGE = "org.bukkit.craftbukkit";
/*     */   public static final String NMS_PACKAGE = "net.minecraft.server";
/*  46 */   public static final String VERSION = Bukkit.getServer().getClass().getPackage().getName().substring("org.bukkit.craftbukkit".length() + 1);
/*     */   
/*  48 */   private static final MethodType VOID_METHOD_TYPE = MethodType.methodType(void.class);
/*  49 */   private static final boolean NMS_REPACKAGED = optionalClass("net.minecraft.network.protocol.Packet").isPresent();
/*     */   
/*     */   private static volatile Object theUnsafe;
/*     */   
/*     */   private FastReflection() {
/*  54 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public static boolean isRepackaged() {
/*  58 */     return NMS_REPACKAGED;
/*     */   }
/*     */   
/*     */   public static String nmsClassName(String paramString1, String paramString2) {
/*  62 */     if (NMS_REPACKAGED) {
/*  63 */       String str = (paramString1 == null) ? "net.minecraft" : ("net.minecraft." + paramString1);
/*  64 */       return str + '.' + paramString2;
/*     */     } 
/*  66 */     return "net.minecraft.server." + VERSION + '.' + paramString2;
/*     */   }
/*     */   
/*     */   public static Class<?> nmsClass(String paramString1, String paramString2) {
/*  70 */     return Class.forName(nmsClassName(paramString1, paramString2));
/*     */   }
/*     */   
/*     */   public static Optional<Class<?>> nmsOptionalClass(String paramString1, String paramString2) {
/*  74 */     return optionalClass(nmsClassName(paramString1, paramString2));
/*     */   }
/*     */   
/*     */   public static String obcClassName(String paramString) {
/*  78 */     return "org.bukkit.craftbukkit." + VERSION + '.' + paramString;
/*     */   }
/*     */   
/*     */   public static Class<?> obcClass(String paramString) {
/*  82 */     return Class.forName(obcClassName(paramString));
/*     */   }
/*     */   
/*     */   public static Optional<Class<?>> obcOptionalClass(String paramString) {
/*  86 */     return optionalClass(obcClassName(paramString));
/*     */   }
/*     */   
/*     */   public static Optional<Class<?>> optionalClass(String paramString) {
/*     */     try {
/*  91 */       return Optional.of(Class.forName(paramString));
/*  92 */     } catch (ClassNotFoundException classNotFoundException) {
/*  93 */       return Optional.empty();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Object enumValueOf(Class<?> paramClass, String paramString) {
/*  98 */     return Enum.valueOf((Class)paramClass.asSubclass(Enum.class), paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object enumValueOf(Class<?> paramClass, String paramString, int paramInt) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: invokestatic enumValueOf : (Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Object;
/*     */     //   5: areturn
/*     */     //   6: astore_3
/*     */     //   7: aload_0
/*     */     //   8: invokevirtual getEnumConstants : ()[Ljava/lang/Object;
/*     */     //   11: astore #4
/*     */     //   13: aload #4
/*     */     //   15: arraylength
/*     */     //   16: iload_2
/*     */     //   17: if_icmple -> 25
/*     */     //   20: aload #4
/*     */     //   22: iload_2
/*     */     //   23: aaload
/*     */     //   24: areturn
/*     */     //   25: aload_3
/*     */     //   26: athrow
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #103	-> 0
/*     */     //   #104	-> 6
/*     */     //   #105	-> 7
/*     */     //   #106	-> 13
/*     */     //   #107	-> 20
/*     */     //   #109	-> 25
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   0	5	6	java/lang/IllegalArgumentException
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Class<?> innerClass(Class<?> paramClass, Predicate<Class<?>> paramPredicate) {
/* 114 */     for (Class<?> clazz : paramClass.getDeclaredClasses()) {
/* 115 */       if (paramPredicate.test(clazz)) {
/* 116 */         return clazz;
/*     */       }
/*     */     } 
/* 119 */     throw new ClassNotFoundException("No class in " + paramClass.getCanonicalName() + " matches the predicate.");
/*     */   }
/*     */   
/*     */   public static PacketConstructor findPacketConstructor(Class<?> paramClass, MethodHandles.Lookup paramLookup) {
/*     */     try {
/* 124 */       MethodHandle methodHandle = paramLookup.findConstructor(paramClass, VOID_METHOD_TYPE);
/* 125 */       Objects.requireNonNull(methodHandle); return methodHandle::invoke;
/* 126 */     } catch (NoSuchMethodException|IllegalAccessException noSuchMethodException) {
/*     */ 
/*     */ 
/*     */       
/* 130 */       if (theUnsafe == null) {
/* 131 */         synchronized (FastReflection.class) {
/* 132 */           if (theUnsafe == null) {
/* 133 */             Class<?> clazz = Class.forName("sun.misc.Unsafe");
/* 134 */             Field field = clazz.getDeclaredField("theUnsafe");
/* 135 */             field.setAccessible(true);
/* 136 */             theUnsafe = field.get(null);
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 141 */       MethodType methodType = MethodType.methodType(Object.class, Class.class);
/* 142 */       MethodHandle methodHandle = paramLookup.findVirtual(theUnsafe.getClass(), "allocateInstance", methodType);
/* 143 */       return () -> paramMethodHandle.invoke(theUnsafe, paramClass);
/*     */     } 
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   static interface PacketConstructor {
/*     */     Object invoke() throws Throwable;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\scoreboard\FastReflection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */