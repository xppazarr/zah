/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.generator;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.XAccessFlag;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.objects.ReflectedObject;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.ReflectiveProxyObject;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations.Constructor;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations.Field;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations.Final;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations.Ignore;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations.Private;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations.Protected;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations.Proxify;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations.Static;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.IOException;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.annotation.Repeatable;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Parameter;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardOpenOption;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.StringJoiner;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
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
/*     */ @Internal
/*     */ public final class XProxifier
/*     */ {
/*     */   private static final String MEMBER_SPACES = "    ";
/*  62 */   private final StringBuilder writer = new StringBuilder(1000);
/*  63 */   private final Set<String> imports = new HashSet<>(20);
/*     */   
/*     */   private final String proxifiedClassName;
/*     */   
/*     */   private final Class<?> clazz;
/*     */   
/*     */   private final boolean generateIntelliJAnnotations = true;
/*     */   private final boolean generateInaccessibleMembers = true;
/*     */   private final boolean copyAnnotations = true;
/*     */   private final boolean writeComments = true;
/*     */   private final boolean writeInfoAnnotationsAsComments = true;
/*     */   private boolean disableIDEFormatting;
/*     */   private Function<Class<?>, String> remapper;
/*     */   
/*     */   public XProxifier(Class<?> paramClass) {
/*  78 */     this.clazz = paramClass;
/*  79 */     this.proxifiedClassName = paramClass.getSimpleName() + "Proxified";
/*  80 */     proxify();
/*     */   }
/*     */   
/*     */   private static Class<?> unwrapArrayType(Class<?> paramClass) {
/*  84 */     for (; paramClass.isArray(); paramClass = paramClass.getComponentType());
/*  85 */     return paramClass;
/*     */   }
/*     */   
/*     */   private void imports(Class<?> paramClass) {
/*  89 */     paramClass = unwrapArrayType(paramClass);
/*  90 */     if (!paramClass.isPrimitive() && !paramClass.getPackage().getName().equals("java.lang"))
/*  91 */       this.imports.add(paramClass.getName().replace('$', '.')); 
/*     */   }
/*     */   
/*     */   private void writeComments(String... paramVarArgs) {
/*  95 */     boolean bool = (paramVarArgs.length > 1) ? true : false;
/*  96 */     if (!bool) {
/*  97 */       this.writer.append("// ").append(paramVarArgs[0]).append('\n');
/*     */     }
/*     */     
/* 100 */     this.writer.append("/**\n");
/*     */     
/* 102 */     for (String str : paramVarArgs) {
/* 103 */       this.writer.append(" * ");
/* 104 */       this.writer.append(str);
/* 105 */       this.writer.append('\n');
/*     */     } 
/*     */     
/* 108 */     this.writer.append(" */\n");
/*     */   }
/*     */   
/*     */   private void writeThrownExceptions(Class<?>[] paramArrayOfClass) {
/* 112 */     if (paramArrayOfClass == null || paramArrayOfClass.length == 0)
/* 113 */       return;  this.writer.append(" throws ");
/* 114 */     StringJoiner stringJoiner = new StringJoiner(", ");
/* 115 */     for (Class<?> clazz : paramArrayOfClass) {
/* 116 */       imports(clazz);
/* 117 */       stringJoiner.add(clazz.getSimpleName());
/*     */     } 
/* 119 */     this.writer.append(stringJoiner);
/*     */   }
/*     */   
/*     */   private void writeMember(ReflectedObject paramReflectedObject) {
/* 123 */     writeMember(paramReflectedObject, false); } private void writeMember(ReflectedObject paramReflectedObject, boolean paramBoolean) { Constructor constructor1; Class[] arrayOfClass;
/*     */     Constructor constructor2;
/*     */     Field field;
/*     */     Method method;
/* 127 */     this.writer.append(annotationsToString(true, true, (AnnotatedElement)paramReflectedObject));
/*     */     
/* 129 */     Set set = paramReflectedObject.accessFlags();
/* 130 */     if (set.contains(XAccessFlag.PRIVATE)) writeAnnotation(Private.class, new String[0]); 
/* 131 */     if (set.contains(XAccessFlag.PROTECTED)) writeAnnotation(Protected.class, new String[0]); 
/* 132 */     if (set.contains(XAccessFlag.STATIC)) writeAnnotation(Static.class, new String[0]); 
/* 133 */     if (set.contains(XAccessFlag.FINAL)) writeAnnotation(Final.class, new String[0]);
/*     */     
/* 135 */     switch (paramReflectedObject.type()) {
/*     */       case CONSTRUCTOR:
/* 137 */         writeAnnotation(Constructor.class, new String[0]);
/* 138 */         writeAnnotation("NotNull", new String[0]);
/*     */         
/* 140 */         constructor1 = (Constructor)paramReflectedObject.unreflect();
/* 141 */         str = Arrays.<Class<?>>stream(constructor1.getParameterTypes()).map(paramClass -> "_").collect(Collectors.joining(", "));
/* 142 */         writeAnnotation("Contract", new String[] { "value = \"" + str + " -> new\"", "pure = true" });
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case FIELD:
/* 149 */         writeAnnotation(Field.class, new String[0]);
/* 150 */         if (paramBoolean) {
/* 151 */           writeAnnotation("Contract", new String[] { "pure = true" }); break;
/*     */         } 
/* 153 */         writeAnnotation("Contract", new String[] { "mutates = \"this\"" });
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 158 */     StringJoiner stringJoiner = new StringJoiner(", ", "(", ")");
/* 159 */     String str = null;
/* 160 */     this.writer.append("    ");
/* 161 */     switch (paramReflectedObject.type()) {
/*     */       case CONSTRUCTOR:
/* 163 */         constructor2 = (Constructor)paramReflectedObject.unreflect();
/* 164 */         arrayOfClass = constructor2.getExceptionTypes();
/* 165 */         this.writer.append(this.proxifiedClassName).append(' ').append("construct");
/* 166 */         writeParameters(stringJoiner, constructor2.getParameters());
/*     */         break;
/*     */       case FIELD:
/* 169 */         field = (Field)paramReflectedObject.unreflect();
/* 170 */         imports(field.getType());
/*     */         
/* 172 */         if (paramBoolean) {
/* 173 */           this.writer.append(field.getType().getSimpleName());
/*     */         } else {
/* 175 */           this.writer.append("void");
/* 176 */           stringJoiner.add(field.getType().getSimpleName() + " value");
/*     */         } 
/*     */         
/* 179 */         this.writer.append(' ');
/* 180 */         this.writer.append(paramReflectedObject.name());
/*     */         break;
/*     */       case METHOD:
/* 183 */         method = (Method)paramReflectedObject.unreflect();
/* 184 */         arrayOfClass = method.getExceptionTypes();
/*     */         
/* 186 */         imports(method.getReturnType());
/*     */         
/* 188 */         this.writer.append(method.getReturnType().getSimpleName());
/* 189 */         this.writer.append(' ');
/* 190 */         this.writer.append(paramReflectedObject.name());
/* 191 */         writeParameters(stringJoiner, method.getParameters());
/*     */         break;
/*     */     } 
/*     */     
/* 195 */     this.writer.append(stringJoiner);
/* 196 */     writeThrownExceptions(arrayOfClass);
/* 197 */     this.writer.append(";\n\n"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object[] getArray(Object paramObject) {
/* 204 */     if (paramObject instanceof Object[]) return (Object[])paramObject; 
/* 205 */     int i = Array.getLength(paramObject);
/* 206 */     Object[] arrayOfObject = new Object[i];
/* 207 */     for (byte b = 0; b < i; b++) {
/* 208 */       arrayOfObject[b] = Array.get(paramObject, b);
/*     */     }
/* 210 */     return arrayOfObject;
/*     */   }
/*     */   
/*     */   private String constantToString(Object paramObject) {
/* 214 */     if (paramObject instanceof String) return '"' + paramObject.toString() + '"'; 
/* 215 */     if (paramObject instanceof Class) {
/* 216 */       Class<?> clazz = (Class)paramObject;
/* 217 */       imports(clazz);
/* 218 */       return clazz.getSimpleName() + ".class";
/*     */     } 
/* 220 */     if (paramObject instanceof Annotation) {
/* 221 */       Annotation annotation = (Annotation)paramObject;
/* 222 */       return annotationToString(annotation);
/*     */     } 
/* 224 */     if (paramObject.getClass().isEnum()) {
/* 225 */       imports(paramObject.getClass());
/* 226 */       return paramObject.getClass().getSimpleName() + '.' + ((Enum)paramObject).name();
/*     */     } 
/* 228 */     if (paramObject.getClass().isArray()) {
/*     */       StringJoiner stringJoiner;
/* 230 */       Object[] arrayOfObject = getArray(paramObject);
/*     */       
/* 232 */       if (arrayOfObject.length == 0) return "{}"; 
/* 233 */       if (arrayOfObject.length == 1) { stringJoiner = new StringJoiner(", "); }
/* 234 */       else { stringJoiner = new StringJoiner(", ", "{", "}"); }
/*     */       
/* 236 */       for (Object object : arrayOfObject) {
/* 237 */         stringJoiner.add(constantToString(object));
/*     */       }
/*     */       
/* 240 */       return stringJoiner.toString();
/*     */     } 
/*     */ 
/*     */     
/* 244 */     return paramObject.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String annotationsToString(boolean paramBoolean1, boolean paramBoolean2, AnnotatedElement paramAnnotatedElement) {
/* 252 */     StringJoiner stringJoiner = (new StringJoiner((paramBoolean2 ? (CharSequence)Character.valueOf('\n') : "") + (paramBoolean1 ? "    " : ""), paramBoolean1 ? "    " : "", paramBoolean2 ? "\n" : "")).setEmptyValue("");
/*     */     
/* 254 */     for (Annotation annotation : paramAnnotatedElement.getAnnotations()) {
/* 255 */       Annotation[] arrayOfAnnotation = unwrapRepeatElement(annotation);
/* 256 */       if (arrayOfAnnotation != null) {
/* 257 */         for (Annotation annotation1 : arrayOfAnnotation) {
/* 258 */           stringJoiner.add(annotationToString(annotation1));
/*     */         }
/*     */       } else {
/* 261 */         stringJoiner.add(annotationToString(annotation));
/*     */       } 
/*     */     } 
/*     */     
/* 265 */     return stringJoiner.toString();
/*     */   }
/*     */   
/*     */   private static Annotation[] unwrapRepeatElement(Annotation paramAnnotation) {
/*     */     try {
/* 270 */       Method method = paramAnnotation.annotationType().getDeclaredMethod("value", new Class[0]);
/* 271 */       if (method.getReturnType().isArray()) {
/*     */         
/* 273 */         Class<?> clazz = unwrapArrayType(method.getReturnType());
/* 274 */         if (clazz.isAnnotation()) {
/* 275 */           Repeatable repeatable = clazz.<Repeatable>getAnnotation(Repeatable.class);
/* 276 */           if (repeatable != null && repeatable.value() == paramAnnotation.annotationType()) {
/*     */             try {
/* 278 */               return (Annotation[])method.invoke(paramAnnotation, new Object[0]);
/* 279 */             } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException illegalAccessException) {
/* 280 */               throw new IllegalArgumentException(illegalAccessException);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/* 285 */     } catch (NoSuchMethodException noSuchMethodException) {}
/*     */     
/* 287 */     return null;
/*     */   }
/*     */   private String annotationToString(Annotation paramAnnotation) {
/*     */     String str;
/* 291 */     ArrayList<String> arrayList = new ArrayList();
/* 292 */     boolean bool = false;
/*     */     
/* 294 */     for (Method method : paramAnnotation.annotationType().getDeclaredMethods()) {
/*     */       try {
/* 296 */         method.setAccessible(true);
/* 297 */         String str1 = method.getName();
/* 298 */         Object object = method.invoke(paramAnnotation, new Object[0]);
/*     */         try {
/* 300 */           Object object1 = method.getDefaultValue();
/*     */ 
/*     */           
/* 303 */           if (object1 != null && (
/* 304 */             object1.getClass().isArray() ? 
/* 305 */             Arrays.equals(getArray(object1), getArray(object)) : 
/*     */             
/* 307 */             object.equals(object1))) {
/*     */             continue;
/*     */           }
/* 310 */         } catch (TypeNotPresentException typeNotPresentException) {}
/*     */ 
/*     */ 
/*     */         
/* 314 */         if (str1.equals("value")) bool = true; 
/* 315 */         arrayList.add(str1 + " = " + constantToString(object));
/* 316 */       } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException illegalAccessException) {
/* 317 */         throw new IllegalStateException("Failed to get annotation value " + method, illegalAccessException);
/*     */       } 
/*     */       continue;
/*     */     } 
/* 321 */     imports(paramAnnotation.annotationType());
/*     */     
/* 323 */     if (arrayList.isEmpty()) { str = ""; }
/* 324 */     else if (arrayList.size() == 1 && bool)
/* 325 */     { str = arrayList.get(0);
/* 326 */       int i = str.indexOf('=');
/* 327 */       str = '(' + str.substring(i + 2) + ')'; }
/*     */     else
/* 329 */     { str = '(' + String.join(", ", (Iterable)arrayList) + ')'; }
/*     */ 
/*     */     
/* 332 */     return '@' + paramAnnotation.annotationType().getSimpleName() + str;
/*     */   }
/*     */   
/*     */   private StringJoiner writeParameters(StringJoiner paramStringJoiner, Parameter[] paramArrayOfParameter) {
/* 336 */     for (Parameter parameter : paramArrayOfParameter) {
/* 337 */       String str1; imports(parameter.getType());
/*     */ 
/*     */       
/* 340 */       if (parameter.isVarArgs()) {
/* 341 */         str1 = parameter.getType().getSimpleName() + "... ";
/*     */       } else {
/* 343 */         str1 = parameter.getType().getSimpleName();
/*     */       } 
/*     */       
/* 346 */       String str2 = annotationsToString(false, false, parameter);
/* 347 */       paramStringJoiner.add(str2 + (str2.isEmpty() ? "" : " ") + str1 + ' ' + parameter.getName());
/*     */     } 
/* 349 */     return paramStringJoiner;
/*     */   }
/*     */   
/*     */   private void writeAnnotation(Class<?> paramClass, String... paramVarArgs) {
/* 353 */     writeAnnotation(true, paramClass, paramVarArgs);
/*     */   }
/*     */   
/*     */   private void writeAnnotation(boolean paramBoolean, Class<?> paramClass, String... paramVarArgs) {
/* 357 */     imports(paramClass);
/* 358 */     writeAnnotation(paramBoolean, paramClass.getSimpleName(), paramVarArgs);
/*     */   }
/*     */   
/*     */   private void writeAnnotation(String paramString, String... paramVarArgs) {
/* 362 */     writeAnnotation(true, paramString, paramVarArgs);
/*     */   }
/*     */   
/*     */   private void writeAnnotation(boolean paramBoolean, String paramString, String... paramVarArgs) {
/* 366 */     if (paramBoolean) this.writer.append("    "); 
/* 367 */     this.writer.append('@').append(paramString);
/* 368 */     if (paramVarArgs.length != 0) {
/* 369 */       StringJoiner stringJoiner = new StringJoiner(", ", "(", ")");
/* 370 */       for (String str : paramVarArgs) stringJoiner.add(str); 
/* 371 */       this.writer.append(stringJoiner);
/*     */     } 
/* 373 */     this.writer.append('\n');
/*     */   }
/*     */   
/*     */   private void proxify() {
/* 377 */     if (this.disableIDEFormatting)
/*     */     {
/*     */       
/* 380 */       this.writer.append("// ").append("@formatter:").append("OFF").append('\n');
/*     */     }
/*     */ 
/*     */     
/* 384 */     writeComments(new String[] { "This is a generated proxified class for " + this.clazz
/* 385 */           .getSimpleName() + ". However, you might", "want to review each member and correct its annotations when needed.", "<p>", "It's also recommended to use your IDE's code formatter to adjust", "imports and spaces according to your settings.", "In IntelliJ, this can be done by with Ctrl+Alt+L", "<p>", "Full Target Class Path:", this.clazz
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 393 */           .getName() });
/*     */ 
/*     */ 
/*     */     
/* 397 */     this.writer.append(annotationsToString(false, true, this.clazz));
/*     */     
/* 399 */     writeAnnotation(false, Proxify.class, new String[] { "target = " + this.clazz
/*     */ 
/*     */           
/* 402 */           .getSimpleName() + ".class" });
/*     */     
/* 404 */     if (!XAccessFlag.PUBLIC.isSet(this.clazz.getModifiers())) {
/* 405 */       writeAnnotation(false, Private.class, new String[0]);
/*     */     }
/* 407 */     if (XAccessFlag.FINAL.isSet(this.clazz.getModifiers())) {
/* 408 */       writeAnnotation(false, Final.class, new String[0]);
/* 409 */       writeAnnotation(false, "ApiStatus.NonExtendable", new String[0]);
/*     */     } 
/* 411 */     this.writer
/* 412 */       .append("public interface ")
/* 413 */       .append(this.proxifiedClassName)
/* 414 */       .append(" extends ")
/* 415 */       .append(ReflectiveProxyObject.class.getSimpleName())
/* 416 */       .append(" {\n");
/*     */     
/* 418 */     Field[] arrayOfField = this.clazz.getDeclaredFields();
/* 419 */     for (Field field : arrayOfField) {
/* 420 */       if (!field.isSynthetic()) {
/* 421 */         if (!XAccessFlag.FINAL.isSet(field.getModifiers())) {
/* 422 */           writeMember(ReflectedObject.of(field), false);
/*     */         }
/* 424 */         writeMember(ReflectedObject.of(field), true);
/*     */       } 
/* 426 */     }  if (arrayOfField.length != 0) this.writer.append('\n');
/*     */     
/* 428 */     Constructor[] arrayOfConstructor = (Constructor[])this.clazz.getDeclaredConstructors();
/* 429 */     for (Constructor constructor : arrayOfConstructor) {
/* 430 */       if (!constructor.isSynthetic())
/* 431 */         writeMember(ReflectedObject.of(constructor)); 
/*     */     } 
/* 433 */     if (arrayOfConstructor.length != 0) this.writer.append('\n');
/*     */     
/* 435 */     for (Method method : this.clazz.getDeclaredMethods()) {
/* 436 */       if (method.getDeclaringClass() != Object.class && 
/* 437 */         !method.isSynthetic() && 
/* 438 */         !method.isBridge()) {
/* 439 */         writeMember(ReflectedObject.of(method));
/*     */       }
/*     */     } 
/* 442 */     this.writer.append('\n');
/* 443 */     writeAnnotation(Ignore.class, new String[0]);
/* 444 */     writeAnnotation("NotNull", new String[0]);
/* 445 */     writeAnnotation("ApiStatus.OverrideOnly", new String[0]);
/* 446 */     writeAnnotation("Contract", new String[] { "value = \"_ -> new\"", "pure = true" });
/*     */ 
/*     */ 
/*     */     
/* 450 */     this.writer.append("    ").append(this.proxifiedClassName).append(" bindTo(@NotNull Object instance);\n");
/*     */     
/* 452 */     this.writer.append("}\n");
/* 453 */     finalizeString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void finalizeString() {
/* 460 */     StringBuilder stringBuilder = new StringBuilder(this.writer.length() + this.imports.size() * 100);
/* 461 */     stringBuilder.append("import org.jetbrains.annotations.*;\n");
/*     */ 
/*     */     
/* 464 */     ArrayList<String> arrayList = new ArrayList<>(this.imports);
/* 465 */     arrayList.sort(Comparator.naturalOrder());
/*     */     
/* 467 */     for (String str : arrayList) {
/* 468 */       stringBuilder.append("import ").append(str).append(";\n");
/*     */     }
/*     */     
/* 471 */     stringBuilder.append('\n');
/* 472 */     this.writer.insert(0, stringBuilder);
/* 473 */     imports(ReflectiveProxyObject.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString() {
/* 478 */     if (this.writer.length() == 0) proxify(); 
/* 479 */     return this.writer.toString();
/*     */   }
/*     */   
/*     */   public void writeTo(Path paramPath) {
/* 483 */     if (Files.isDirectory(paramPath, new java.nio.file.LinkOption[0])) {
/* 484 */       paramPath = paramPath.resolve(this.proxifiedClassName + ".java");
/*     */     }
/*     */     
/* 487 */     try { BufferedWriter bufferedWriter = Files.newBufferedWriter(paramPath, StandardCharsets.UTF_8, new OpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING });
/*     */       
/* 489 */       try { bufferedWriter.write(getString());
/* 490 */         if (bufferedWriter != null) bufferedWriter.close();  } catch (Throwable throwable) { if (bufferedWriter != null) try { bufferedWriter.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException iOException)
/* 491 */     { throw new IllegalStateException(iOException); }
/*     */   
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\proxy\generator\XProxifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */