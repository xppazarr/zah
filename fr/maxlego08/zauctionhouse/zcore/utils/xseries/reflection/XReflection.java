/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.aggregate.AggregateReflectiveHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.aggregate.AggregateReflectiveSupplier;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.aggregate.VersionHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.asm.XReflectASM;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.classes.DynamicClassHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.classes.StaticClassHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.minecraft.MinecraftClassHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.minecraft.MinecraftMapping;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.minecraft.MinecraftPackage;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.ReflectiveProxy;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.ReflectiveProxyObject;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.stream.Collectors;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.jetbrains.annotations.ApiStatus.Experimental;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.Contract;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.jetbrains.annotations.TestOnly;
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
/*     */ public final class XReflection
/*     */ {
/*     */   @Nullable
/*     */   @Internal
/* 156 */   public static final String NMS_VERSION = findNMSVersionString();
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public static final String XSERIES_VERSION = "13.0.0";
/*     */ 
/*     */   
/*     */   @TestOnly
/*     */   public static final String DISABLE_MINECRAFT_CAPABILITIES_PROPERTY = "xseries.xreflection.disable.minecraft";
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public static final boolean SUPPORTS_ASM;
/*     */ 
/*     */   
/*     */   public static final int MAJOR_NUMBER;
/*     */ 
/*     */   
/*     */   public static final int MINOR_NUMBER;
/*     */   
/*     */   public static final int PATCH_NUMBER;
/*     */ 
/*     */   
/*     */   static {
/*     */     boolean bool;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 186 */       Class.forName("org.objectweb.asm.ClassWriter");
/* 187 */       Class.forName("org.objectweb.asm.MethodVisitor");
/* 188 */       Class.forName("org.objectweb.asm.FieldVisitor");
/* 189 */       Class.forName("org.objectweb.asm.Constants");
/* 190 */       Class.forName("org.objectweb.asm.Opcodes");
/* 191 */       bool = true;
/* 192 */     } catch (ClassNotFoundException classNotFoundException) {
/* 193 */       bool = false;
/*     */     } 
/* 195 */     SUPPORTS_ASM = bool;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 278 */     String str = isMinecraftDisabled();
/* 279 */     if (str != null) {
/* 280 */       System.out.println("[XSeries/XReflection] Testing with hardcoded server version: " + (
/* 281 */           str.isEmpty() ? "Disabled Minecraft Capabilities" : str));
/* 282 */       if (str.isEmpty() || str.equals("true")) {
/* 283 */         str = "1.21.4-R0.1-SNAPSHOT";
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 290 */     Matcher matcher = Pattern.compile("^(?<major>\\d+)\\.(?<minor>\\d+)(?:\\.(?<patch>\\d+))?").matcher((str != null) ? str : Bukkit.getBukkitVersion());
/* 291 */     if (matcher.find()) {
/*     */       
/*     */       try {
/* 294 */         String str1 = matcher.group("patch");
/* 295 */         MAJOR_NUMBER = Integer.parseInt(matcher.group("major"));
/* 296 */         MINOR_NUMBER = Integer.parseInt(matcher.group("minor"));
/* 297 */         PATCH_NUMBER = Integer.parseInt((str1 == null || str1.isEmpty()) ? "0" : str1);
/* 298 */       } catch (Throwable throwable) {
/* 299 */         throw new IllegalStateException("Failed to parse minor number: " + matcher + ' ' + getVersionInformation(), throwable);
/*     */       } 
/*     */     } else {
/* 302 */       throw new IllegalStateException("Cannot parse server version: \"" + Bukkit.getBukkitVersion() + '"');
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String isMinecraftDisabled() {
/*     */     try {
/* 308 */       return System.getProperty("xseries.xreflection.disable.minecraft");
/* 309 */     } catch (SecurityException securityException) {
/* 310 */       return null;
/*     */     } 
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
/*     */   @NotNull
/*     */   @Contract(pure = true)
/*     */   public static String getVersionInformation() {
/* 325 */     return "(NMS: " + ((NMS_VERSION == null) ? "Unknown NMS" : NMS_VERSION) + " | Parsed: " + MAJOR_NUMBER + '.' + MINOR_NUMBER + '.' + PATCH_NUMBER + " | Minecraft: " + 
/*     */       
/* 327 */       Bukkit.getVersion() + " | Bukkit: " + 
/* 328 */       Bukkit.getBukkitVersion() + ')';
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
/*     */   @Nullable
/*     */   @Contract(pure = true)
/*     */   public static Integer getLatestPatchNumberOf(int paramInt) {
/* 343 */     if (paramInt <= 0) throw new IllegalArgumentException("Minor version must be positive: " + paramInt);
/*     */ 
/*     */ 
/*     */     
/* 347 */     int[] arrayOfInt = { 1, 5, 2, 7, 2, 4, 10, 8, 4, 2, 2, 2, 2, 4, 2, 5, 1, 2, 4, 6, 4 };
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
/* 372 */     if (paramInt > arrayOfInt.length) return null; 
/* 373 */     return Integer.valueOf(arrayOfInt[paramInt - 1]);
/*     */   } @Nullable @Internal public static String findNMSVersionString() { String str = null; for (Package package_ : Package.getPackages()) { String str1 = package_.getName(); if (str1.startsWith("org.bukkit.craftbukkit.v")) {
/*     */         str = package_.getName().split("\\.")[3]; try {
/*     */           Class.forName("org.bukkit.craftbukkit." + str + ".entity.CraftPlayer"); break;
/*     */         } catch (ClassNotFoundException classNotFoundException) {
/*     */           str = null;
/*     */         } 
/*     */       }  }
/* 381 */      return str; } @Internal public static final String CRAFTBUKKIT_PACKAGE = (isMinecraftDisabled() != null) ? null : Bukkit.getServer().getClass().getPackage().getName(); @Internal
/* 382 */   public static final String NMS_PACKAGE = (String)v(17, (T)"net.minecraft").orElse("net.minecraft.server." + NMS_VERSION);
/*     */   
/*     */   @Internal
/*     */   @Experimental
/*     */   public static final Set<MinecraftMapping> SUPPORTED_MAPPINGS;
/*     */ 
/*     */   
/*     */   static {
/* 390 */     if (isMinecraftDisabled() != null) {
/* 391 */       SUPPORTED_MAPPINGS = Collections.unmodifiableSet(EnumSet.noneOf(MinecraftMapping.class));
/*     */     } else {
/* 393 */       SUPPORTED_MAPPINGS = Collections.unmodifiableSet(
/* 394 */           (Set<? extends MinecraftMapping>)supply(
/* 395 */             ofMinecraft()
/* 396 */             .inPackage(MinecraftPackage.NMS, "server.level")
/* 397 */             .map(MinecraftMapping.MOJANG, "ServerPlayer"), () -> EnumSet.of(MinecraftMapping.MOJANG))
/*     */           
/* 399 */           .or(
/* 400 */             (ReflectiveHandle)ofMinecraft()
/* 401 */             .inPackage(MinecraftPackage.NMS, "server.level")
/* 402 */             .map(MinecraftMapping.MOJANG, "EntityPlayer"), () -> EnumSet.of(MinecraftMapping.SPIGOT, MinecraftMapping.OBFUSCATED))
/*     */           
/* 404 */           .get());
/*     */     } 
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
/*     */   @NotNull
/*     */   @Contract(value = "_, _ -> new", pure = true)
/*     */   public static <T> VersionHandle<T> v(int paramInt, T paramT) {
/* 422 */     return new VersionHandle(paramInt, paramT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(value = "_, _, _ -> new", pure = true)
/*     */   public static <T> VersionHandle<T> v(int paramInt1, int paramInt2, T paramT) {
/* 431 */     return new VersionHandle(paramInt1, paramInt2, paramT);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   @Contract(value = "_, _ -> new", pure = true)
/*     */   public static <T> VersionHandle<T> v(int paramInt, Callable<T> paramCallable) {
/* 437 */     return new VersionHandle(paramInt, paramCallable);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   @Contract(value = "_, _, _ -> new", pure = true)
/*     */   public static <T> VersionHandle<T> v(int paramInt1, int paramInt2, Callable<T> paramCallable) {
/* 443 */     return new VersionHandle(paramInt1, paramInt2, paramCallable);
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
/*     */   @Contract(pure = true)
/*     */   public static boolean supports(int paramInt) {
/* 456 */     return (MINOR_NUMBER >= paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Contract(pure = true)
/*     */   public static boolean supports(int paramInt1, int paramInt2, int paramInt3) {
/* 464 */     if (paramInt1 != 1) throw new IllegalArgumentException("Invalid major number: " + paramInt1); 
/* 465 */     return supports(paramInt2, paramInt3);
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
/*     */   @Contract(pure = true)
/*     */   public static boolean supports(int paramInt1, int paramInt2) {
/* 480 */     return (MINOR_NUMBER == paramInt1) ? ((PATCH_NUMBER >= paramInt2)) : supports(paramInt1);
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
/*     */   @Deprecated
/*     */   @Contract(pure = true)
/*     */   public static boolean supportsPatch(int paramInt) {
/* 495 */     return (PATCH_NUMBER >= paramInt);
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
/*     */   @Deprecated
/*     */   @NotNull
/*     */   public static Class<?> getNMSClass(@Nullable String paramString1, @NotNull String paramString2) {
/* 512 */     if (paramString1 != null && supports(17)) paramString2 = paramString1 + '.' + paramString2;
/*     */     
/*     */     try {
/* 515 */       return Class.forName(NMS_PACKAGE + '.' + paramString2);
/* 516 */     } catch (ClassNotFoundException classNotFoundException) {
/* 517 */       throw new IllegalArgumentException(classNotFoundException);
/*     */     } 
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
/*     */   @Deprecated
/*     */   @NotNull
/*     */   public static Class<?> getNMSClass(@NotNull String paramString) {
/* 534 */     return getNMSClass(null, paramString);
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
/*     */   @Deprecated
/*     */   @NotNull
/*     */   public static Class<?> getCraftClass(@NotNull String paramString) {
/*     */     try {
/* 550 */       return Class.forName(CRAFTBUKKIT_PACKAGE + '.' + paramString);
/* 551 */     } catch (ClassNotFoundException classNotFoundException) {
/* 552 */       throw new IllegalArgumentException(classNotFoundException);
/*     */     } 
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
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(pure = true)
/*     */   public static Class<?> toArrayClass(@NotNull Class<?> paramClass) {
/* 572 */     Objects.requireNonNull(paramClass, "Class is null");
/*     */     try {
/* 574 */       return Class.forName("[L" + paramClass.getName() + ';');
/* 575 */     } catch (ClassNotFoundException classNotFoundException) {
/* 576 */       throw new IllegalArgumentException("Cannot find array class for class: " + paramClass, classNotFoundException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(value = "-> new", pure = true)
/*     */   public static MinecraftClassHandle ofMinecraft() {
/* 586 */     return new MinecraftClassHandle(new ReflectiveNamespace());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(value = "-> new", pure = true)
/*     */   public static DynamicClassHandle classHandle() {
/* 595 */     return new DynamicClassHandle(new ReflectiveNamespace());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(value = "_ -> new", pure = true)
/*     */   public static StaticClassHandle of(Class<?> paramClass) {
/* 604 */     return new StaticClassHandle(new ReflectiveNamespace(), paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(value = "-> new", pure = true)
/*     */   public static ReflectiveNamespace namespaced() {
/* 616 */     return new ReflectiveNamespace();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SafeVarargs
/*     */   @NotNull
/*     */   @Contract(value = "_ -> new", pure = true)
/*     */   public static <T, H extends ReflectiveHandle<T>> AggregateReflectiveHandle<T, H> any(H... paramVarArgs) {
/* 626 */     return new AggregateReflectiveHandle((Collection)Arrays.<H>stream(paramVarArgs).map(paramReflectiveHandle -> ()).collect(Collectors.toList()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SafeVarargs
/*     */   @NotNull
/*     */   @Contract(value = "_ -> new", pure = true)
/*     */   public static <T, H extends ReflectiveHandle<T>> AggregateReflectiveHandle<T, H> anyOf(Callable<H>... paramVarArgs) {
/* 636 */     return new AggregateReflectiveHandle(Arrays.asList(paramVarArgs));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Experimental
/*     */   @NotNull
/*     */   @Contract(value = "_, _ -> new", pure = true)
/*     */   public static <H extends ReflectiveHandle<?>, O> AggregateReflectiveSupplier<H, O> supply(H paramH, O paramO) {
/* 646 */     AggregateReflectiveSupplier<H, O> aggregateReflectiveSupplier = new AggregateReflectiveSupplier();
/* 647 */     aggregateReflectiveSupplier.or((ReflectiveHandle)paramH, paramO);
/* 648 */     return aggregateReflectiveSupplier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Experimental
/*     */   @NotNull
/*     */   @Contract(value = "_, _ -> new", pure = true)
/*     */   public static <H extends ReflectiveHandle<?>, O> AggregateReflectiveSupplier<H, O> supply(H paramH, Supplier<O> paramSupplier) {
/* 658 */     AggregateReflectiveSupplier<H, O> aggregateReflectiveSupplier = new AggregateReflectiveSupplier();
/* 659 */     aggregateReflectiveSupplier.or((ReflectiveHandle)paramH, paramSupplier);
/* 660 */     return aggregateReflectiveSupplier;
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
/*     */ 
/*     */   
/*     */   @Experimental
/*     */   @NotNull
/*     */   @Contract(value = "_ -> param1", mutates = "param1")
/*     */   public static <T extends Throwable> T relativizeSuppressedExceptions(@NotNull T paramT) {
/* 680 */     Objects.requireNonNull(paramT, "Cannot relativize null exception");
/* 681 */     StackTraceElement[] arrayOfStackTraceElement1 = new StackTraceElement[0];
/* 682 */     StackTraceElement[] arrayOfStackTraceElement2 = paramT.getStackTrace();
/*     */     
/* 684 */     for (Throwable throwable : paramT.getSuppressed()) {
/* 685 */       StackTraceElement[] arrayOfStackTraceElement = throwable.getStackTrace();
/* 686 */       ArrayList<StackTraceElement> arrayList = new ArrayList(10);
/*     */       
/* 688 */       for (byte b = 0; b < arrayOfStackTraceElement.length; b++) {
/* 689 */         if (arrayOfStackTraceElement2.length <= b) {
/* 690 */           arrayList = null;
/*     */           
/*     */           break;
/*     */         } 
/* 694 */         StackTraceElement stackTraceElement1 = arrayOfStackTraceElement2[b];
/* 695 */         StackTraceElement stackTraceElement2 = arrayOfStackTraceElement[b];
/* 696 */         if (stackTraceElement1.equals(stackTraceElement2)) {
/*     */           break;
/*     */         }
/* 699 */         arrayList.add(stackTraceElement2);
/*     */       } 
/*     */ 
/*     */       
/* 703 */       if (arrayList != null)
/*     */       {
/*     */         
/* 706 */         throwable.setStackTrace(arrayList.<StackTraceElement>toArray(arrayOfStackTraceElement1));
/*     */       }
/*     */     } 
/* 709 */     return paramT;
/*     */   }
/*     */ 
/*     */   
/*     */   @Contract(value = "_ -> fail", pure = true)
/*     */   private static <T extends Throwable> void throwException(Throwable paramThrowable) throws T {
/* 715 */     throw (T)paramThrowable;
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
/*     */   @Contract(value = "_ -> fail", pure = true)
/*     */   public static RuntimeException throwCheckedException(@NotNull Throwable paramThrowable) {
/* 748 */     Objects.requireNonNull(paramThrowable, "Cannot throw null exception");
/*     */ 
/*     */     
/* 751 */     throwException(paramThrowable);
/* 752 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Experimental
/*     */   @Contract(value = "_ -> new", mutates = "param1")
/*     */   public static <T> CompletableFuture<T> stacktrace(@NotNull CompletableFuture<T> paramCompletableFuture) {
/* 761 */     StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
/* 762 */     return paramCompletableFuture.whenComplete((paramObject, paramThrowable) -> {
/*     */           if (paramThrowable == null) {
/*     */             paramCompletableFuture.complete(paramObject);
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*     */           try {
/*     */             StackTraceElement[] arrayOfStackTraceElement1 = paramThrowable.getStackTrace();
/*     */             
/*     */             if (arrayOfStackTraceElement1.length >= 3) {
/*     */               ArrayList<?> arrayList = new ArrayList(Arrays.asList((Object[])arrayOfStackTraceElement1));
/*     */               
/*     */               Collections.reverse(arrayList);
/*     */               
/*     */               Iterator<?> iterator = arrayList.iterator();
/*     */               
/*     */               List<String> list1 = Arrays.asList(new String[] { "java.util.concurrent.CompletableFuture", "java.util.concurrent.ThreadPoolExecutor", "java.util.concurrent.ForkJoinTask", "java.util.concurrent.ForkJoinWorkerThread", "java.util.concurrent.ForkJoinPool" });
/*     */               
/*     */               List<String> list2 = Arrays.asList(new String[] { 
/*     */                     "postComplete", "encodeThrowable", "completeThrowable", "tryFire", "run", "runWorker", "scan", "exec", "doExec", "topLevelExec", 
/*     */                     "uniWhenComplete" });
/*     */               
/*     */               while (iterator.hasNext()) {
/*     */                 StackTraceElement stackTraceElement = (StackTraceElement)iterator.next();
/*     */                 
/*     */                 String str1 = stackTraceElement.getClassName();
/*     */                 
/*     */                 String str2 = stackTraceElement.getMethodName();
/*     */                 
/*     */                 if (str1.equals(Thread.class.getName())) {
/*     */                   continue;
/*     */                 }
/*     */                 
/*     */                 Objects.requireNonNull(str1);
/*     */                 
/*     */                 Objects.requireNonNull(str2);
/*     */                 
/*     */                 if (list1.stream().anyMatch(str1::startsWith) && list2.stream().anyMatch(str2::equals)) {
/*     */                   iterator.remove();
/*     */                 }
/*     */               } 
/*     */               
/*     */               Collections.reverse(arrayList);
/*     */               
/*     */               arrayOfStackTraceElement1 = arrayList.<StackTraceElement>toArray(new StackTraceElement[0]);
/*     */             } 
/*     */             
/*     */             StackTraceElement[] arrayOfStackTraceElement2 = (StackTraceElement[])Arrays.<StackTraceElement>stream(paramArrayOfStackTraceElement).skip(2L).toArray(());
/*     */             paramThrowable.setStackTrace(concatenate(arrayOfStackTraceElement1, arrayOfStackTraceElement2));
/* 812 */           } catch (Throwable throwable) {
/*     */             paramThrowable.addSuppressed(throwable);
/*     */           } finally {
/*     */             paramCompletableFuture.completeExceptionally(paramThrowable);
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @Contract(value = "_, _ -> new", pure = true)
/*     */   public static <T> T[] concatenate(T[] paramArrayOfT1, T[] paramArrayOfT2) {
/* 823 */     int i = paramArrayOfT1.length;
/* 824 */     int j = paramArrayOfT2.length;
/*     */ 
/*     */     
/* 827 */     Object[] arrayOfObject = (Object[])Array.newInstance(paramArrayOfT1.getClass().getComponentType(), i + j);
/* 828 */     System.arraycopy(paramArrayOfT1, 0, arrayOfObject, 0, i);
/* 829 */     System.arraycopy(paramArrayOfT2, 0, arrayOfObject, i, j);
/*     */     
/* 831 */     return (T[])arrayOfObject;
/*     */   }
/*     */   
/* 834 */   private static final Map<Class<?>, ReflectiveProxyObject> PROXIFIED_CLASSES = new IdentityHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Experimental
/*     */   @NotNull
/*     */   public static <T extends ReflectiveProxyObject> T proxify(@NotNull Class<T> paramClass) {
/*     */     ReflectiveProxyObject reflectiveProxyObject2;
/* 845 */     ReflectiveProxy.checkInterfaceClass(paramClass);
/*     */     
/* 847 */     ReflectiveProxyObject reflectiveProxyObject1 = PROXIFIED_CLASSES.get(paramClass);
/* 848 */     if (reflectiveProxyObject1 != null) {
/* 849 */       return (T)reflectiveProxyObject1;
/*     */     }
/*     */     
/* 852 */     if (SUPPORTS_ASM) {
/* 853 */       reflectiveProxyObject2 = XReflectASM.proxify(paramClass).create();
/*     */     } else {
/* 855 */       reflectiveProxyObject2 = ReflectiveProxy.proxify(paramClass).proxy();
/*     */     } 
/*     */     
/* 858 */     PROXIFIED_CLASSES.put(paramClass, reflectiveProxyObject2);
/* 859 */     return (T)reflectiveProxyObject2;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\XReflection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */