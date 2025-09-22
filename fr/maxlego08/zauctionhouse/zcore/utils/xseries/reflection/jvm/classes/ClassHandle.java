/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.classes;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.ReflectiveHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.ReflectiveNamespace;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.constraint.ReflectiveConstraint;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.constraint.ReflectiveConstraintException;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.ConstructorMemberHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.EnumMemberHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.FieldMemberHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.MethodMemberHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.NamedReflectiveHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.objects.ReflectedObject;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.objects.ReflectedObjectHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.parser.ReflectionParser;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import org.intellij.lang.annotations.Language;
/*     */ import org.jetbrains.annotations.ApiStatus.Experimental;
/*     */ import org.jetbrains.annotations.Contract;
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
/*     */ public abstract class ClassHandle
/*     */   implements ReflectiveHandle<Class<?>>, NamedReflectiveHandle
/*     */ {
/*     */   protected final ReflectiveNamespace namespace;
/*  49 */   private final Map<Class<ReflectiveConstraint>, ReflectiveConstraint> constraints = new IdentityHashMap<>();
/*     */   
/*     */   protected ClassHandle(@NotNull ReflectiveNamespace paramReflectiveNamespace) {
/*  52 */     this.namespace = paramReflectiveNamespace;
/*  53 */     paramReflectiveNamespace.link(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Experimental
/*     */   @Contract(value = "_ -> this", mutates = "this")
/*     */   public ClassHandle constraint(@NotNull ReflectiveConstraint paramReflectiveConstraint) {
/*  63 */     this.constraints.put(paramReflectiveConstraint.getClass(), paramReflectiveConstraint);
/*  64 */     return this;
/*     */   }
/*     */   
/*     */   protected <T extends Class<?>> T checkConstraints(T paramT) {
/*  68 */     for (ReflectiveConstraint reflectiveConstraint : this.constraints.values()) {
/*  69 */       ReflectiveConstraint.Result result = reflectiveConstraint.appliesTo(this, paramT);
/*  70 */       if (result != ReflectiveConstraint.Result.MATCHED) {
/*  71 */         throw ReflectiveConstraintException.create(reflectiveConstraint, result, this, paramT);
/*     */       }
/*     */     } 
/*  74 */     return paramT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract("-> new")
/*     */   public final ClassHandle asArray() {
/*  84 */     return asArray(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract("_ -> new")
/*     */   public DynamicClassHandle inner(@Language(value = "Java", suffix = "{}") String paramString) {
/*  93 */     return inner(this.namespace.classHandle(paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract("_ -> param1")
/*     */   public <T extends DynamicClassHandle> T inner(@NotNull T paramT) {
/* 104 */     Objects.requireNonNull(paramT, "Inner handle is null");
/* 105 */     if (this == paramT) throw new IllegalArgumentException("Same instance: " + this); 
/* 106 */     ((DynamicClassHandle)paramT).parent = this;
/* 107 */     this.namespace.link(this);
/* 108 */     return paramT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDimensionCount() {
/* 118 */     byte b = -1;
/* 119 */     Class<?> clazz = (Class)reflectOrNull();
/* 120 */     if (clazz == null) return b;
/*     */     
/*     */     do {
/* 123 */       clazz = clazz.getComponentType();
/* 124 */       b++;
/* 125 */     } while (clazz != null);
/*     */     
/* 127 */     return b;
/*     */   }
/*     */   
/*     */   @Contract(pure = true)
/*     */   public ReflectiveNamespace getNamespace() {
/* 132 */     return this.namespace;
/*     */   }
/*     */   
/*     */   @Contract(value = "-> new", pure = true)
/*     */   public MethodMemberHandle method() {
/* 137 */     return new MethodMemberHandle(this);
/*     */   }
/*     */   
/*     */   @Contract(value = "_ -> new", pure = true)
/*     */   public MethodMemberHandle method(@Language(value = "Java", suffix = ";") String paramString) {
/* 142 */     return createParser(paramString).parseMethod(method());
/*     */   }
/*     */   
/*     */   @Contract(value = "-> new", pure = true)
/*     */   public EnumMemberHandle enums() {
/* 147 */     return new EnumMemberHandle(this);
/*     */   }
/*     */   
/*     */   @Contract(value = "-> new", pure = true)
/*     */   public FieldMemberHandle field() {
/* 152 */     return new FieldMemberHandle(this);
/*     */   }
/*     */   
/*     */   @Contract(value = "_ -> new", pure = true)
/*     */   public FieldMemberHandle field(@Language(value = "Java", suffix = ";") String paramString) {
/* 157 */     return createParser(paramString).parseField(field());
/*     */   }
/*     */   
/*     */   @Contract(value = "_ -> new", pure = true)
/*     */   public ConstructorMemberHandle constructor(@Language(value = "Java", suffix = ";") String paramString) {
/* 162 */     return createParser(paramString).parseConstructor(constructor());
/*     */   }
/*     */   
/*     */   @Contract(value = "-> new", pure = true)
/*     */   public ConstructorMemberHandle constructor() {
/* 167 */     return new ConstructorMemberHandle(this);
/*     */   }
/*     */   
/*     */   @Contract(value = "_ -> new", pure = true)
/*     */   public ConstructorMemberHandle constructor(Class<?>... paramVarArgs) {
/* 172 */     return constructor().parameters(paramVarArgs);
/*     */   }
/*     */   
/*     */   @Contract(value = "_ -> new", pure = true)
/*     */   public ConstructorMemberHandle constructor(ClassHandle... paramVarArgs) {
/* 177 */     return constructor().parameters(paramVarArgs);
/*     */   }
/*     */   
/*     */   @Contract(value = "_ -> new", pure = true)
/*     */   private ReflectionParser createParser(@Language("Java") String paramString) {
/* 182 */     return (new ReflectionParser(paramString)).imports(this.namespace);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ReflectiveHandle<ReflectedObject> jvm() {
/* 190 */     return (ReflectiveHandle<ReflectedObject>)new ReflectedObjectHandle(() -> ReflectedObject.of((Class)reflect()));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   @Contract("_ -> new")
/*     */   public abstract ClassHandle asArray(int paramInt);
/*     */   
/*     */   @Contract(pure = true)
/*     */   public abstract boolean isArray();
/*     */   
/*     */   public abstract ClassHandle copy();
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\jvm\classes\ClassHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */