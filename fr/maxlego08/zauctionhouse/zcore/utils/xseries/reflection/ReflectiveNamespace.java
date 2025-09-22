/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.classes.ClassHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.classes.DynamicClassHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.classes.StaticClassHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.minecraft.MinecraftClassHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.parser.ReflectionParser;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import org.intellij.lang.annotations.Language;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReflectiveNamespace
/*     */ {
/*  88 */   private final Map<String, Class<?>> imports = new HashMap<>();
/*  89 */   private final MethodHandles.Lookup lookup = MethodHandles.lookup();
/*  90 */   private final Set<ClassHandle> handles = Collections.newSetFromMap(new IdentityHashMap<>());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReflectiveNamespace imports(@NotNull Class<?>... paramVarArgs) {
/* 101 */     for (Class<?> clazz : paramVarArgs) {
/* 102 */       imports(clazz.getSimpleName(), clazz);
/*     */     }
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReflectiveNamespace imports(@NotNull String paramString, @NotNull Class<?> paramClass) {
/* 115 */     Objects.requireNonNull(paramString);
/* 116 */     Objects.requireNonNull(paramClass);
/* 117 */     this.imports.put(paramString, paramClass);
/* 118 */     return this;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   @Internal
/*     */   public Map<String, Class<?>> getImports() {
/* 124 */     for (ClassHandle classHandle : this.handles) {
/* 125 */       Class<?> clazz = (Class)classHandle.reflectOrNull();
/* 126 */       if (clazz == null)
/*     */         continue; 
/* 128 */       for (String str : classHandle.getPossibleNames()) {
/* 129 */         this.imports.put(str, clazz);
/*     */       }
/*     */     } 
/* 132 */     return this.imports;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void link(ClassHandle paramClassHandle) {
/* 137 */     if (paramClassHandle.getNamespace() != this) throw new IllegalArgumentException("Not the same namespace"); 
/* 138 */     this.handles.add(paramClassHandle);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   @Internal
/*     */   public MethodHandles.Lookup getLookup() {
/* 144 */     return this.lookup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StaticClassHandle of(Class<?> paramClass) {
/* 155 */     imports(new Class[] { paramClass });
/* 156 */     return new StaticClassHandle(this, paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DynamicClassHandle classHandle(@Language(value = "Java", suffix = "{}") String paramString) {
/* 167 */     DynamicClassHandle dynamicClassHandle = new DynamicClassHandle(this);
/* 168 */     return (new ReflectionParser(paramString)).imports(this).parseClass(dynamicClassHandle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DynamicClassHandle classHandle() {
/* 179 */     return new DynamicClassHandle(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MinecraftClassHandle ofMinecraft(@Language(value = "Java", suffix = "{}") String paramString) {
/* 189 */     MinecraftClassHandle minecraftClassHandle = new MinecraftClassHandle(this);
/* 190 */     return (MinecraftClassHandle)(new ReflectionParser(paramString)).imports(this).parseClass((DynamicClassHandle)minecraftClassHandle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MinecraftClassHandle ofMinecraft() {
/* 200 */     return new MinecraftClassHandle(this);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\ReflectiveNamespace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */