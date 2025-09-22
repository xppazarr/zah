/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.asm;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.function.Predicate;
/*     */ import org.objectweb.asm.ClassReader;
/*     */ import org.objectweb.asm.ClassVisitor;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.objectweb.asm.tree.MethodNode;
/*     */ import org.objectweb.asm.tree.analysis.Analyzer;
/*     */ import org.objectweb.asm.tree.analysis.AnalyzerException;
/*     */ import org.objectweb.asm.tree.analysis.Interpreter;
/*     */ import org.objectweb.asm.tree.analysis.SimpleVerifier;
/*     */ import org.objectweb.asm.util.CheckClassAdapter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ASMAnalyzer
/*     */ {
/*     */   private static final MethodHandle CheckClassAdapter_printAnalyzerResult;
/*     */   
/*     */   static {
/*     */     MethodHandle methodHandle;
/*  56 */     MethodHandles.Lookup lookup = MethodHandles.lookup();
/*     */     try {
/*  58 */       Method method = CheckClassAdapter.class.getDeclaredMethod("printAnalyzerResult", new Class[] { MethodNode.class, Analyzer.class, PrintWriter.class });
/*  59 */       method.setAccessible(true);
/*  60 */       methodHandle = lookup.unreflect(method);
/*  61 */     } catch (NoSuchMethodException|IllegalAccessException noSuchMethodException) {
/*  62 */       methodHandle = null;
/*     */     } 
/*  64 */     CheckClassAdapter_printAnalyzerResult = methodHandle;
/*     */   }
/*     */   
/*     */   protected static Throwable findCause(Throwable paramThrowable, Predicate<Throwable> paramPredicate) {
/*  68 */     Set<?> set = Collections.newSetFromMap(new IdentityHashMap<>(5));
/*     */     
/*  70 */     Throwable throwable = paramThrowable;
/*     */     do {
/*  72 */       if (!set.add(throwable)) return null; 
/*  73 */       if (paramPredicate.test(throwable)) return throwable; 
/*  74 */       throwable = throwable.getCause();
/*  75 */     } while (throwable != null);
/*     */     
/*  77 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void verify(ClassReader paramClassReader, ClassLoader paramClassLoader, boolean paramBoolean, PrintWriter paramPrintWriter) {
/*  85 */     ClassNode classNode = new ClassNode();
/*  86 */     paramClassReader.accept((ClassVisitor)new CheckClassAdapter(ASMVersion.LATEST_ASM_OPCODE_VERSION, (ClassVisitor)classNode, false) {  }2);
/*     */ 
/*     */ 
/*     */     
/*  90 */     Type type = (classNode.superName == null) ? null : Type.getObjectType(classNode.superName);
/*  91 */     List list = classNode.methods;
/*     */     
/*  93 */     ArrayList<Type> arrayList = new ArrayList();
/*  94 */     for (String str : classNode.interfaces) {
/*  95 */       arrayList.add(Type.getObjectType(str));
/*     */     }
/*     */     
/*  98 */     for (MethodNode methodNode : list) {
/*     */       boolean bool;
/*     */       
/* 101 */       SimpleVerifier simpleVerifier = new SimpleVerifier(Type.getObjectType(classNode.name), type, arrayList, ((classNode.access & 0x200) != 0));
/*     */ 
/*     */ 
/*     */       
/* 105 */       Analyzer analyzer = new Analyzer((Interpreter)simpleVerifier);
/* 106 */       if (paramClassLoader != null) {
/* 107 */         simpleVerifier.setClassLoader(paramClassLoader);
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 112 */         analyzer.analyze(classNode.name, methodNode);
/* 113 */         bool = false;
/* 114 */       } catch (AnalyzerException analyzerException) {
/* 115 */         ClassNotFoundException classNotFoundException = (ClassNotFoundException)findCause((Throwable)analyzerException, paramThrowable -> paramThrowable instanceof ClassNotFoundException);
/* 116 */         if (classNotFoundException == null || classNotFoundException.getMessage() == null || !classNotFoundException.getMessage().contains("XSeriesGen")) {
/* 117 */           bool = true;
/* 118 */           analyzerException.printStackTrace(paramPrintWriter);
/*     */         } else {
/* 120 */           bool = false;
/*     */         } 
/*     */       } 
/*     */       
/* 124 */       if (paramBoolean || bool) {
/*     */         try {
/* 126 */           CheckClassAdapter_printAnalyzerResult.invokeExact(methodNode, analyzer, paramPrintWriter);
/* 127 */         } catch (Throwable throwable) {
/* 128 */           throw new IllegalStateException("Cannot write bytecode instructions: ", throwable);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 133 */     paramPrintWriter.flush();
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\asm\ASMAnalyzer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */