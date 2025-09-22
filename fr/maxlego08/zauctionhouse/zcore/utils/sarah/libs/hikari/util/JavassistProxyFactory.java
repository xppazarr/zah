/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.util;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.pool.ProxyCallableStatement;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.pool.ProxyConnection;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.pool.ProxyDatabaseMetaData;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.pool.ProxyPreparedStatement;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.pool.ProxyResultSet;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.pool.ProxyStatement;
/*     */ import java.lang.reflect.Array;
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ import javassist.ClassPath;
/*     */ import javassist.ClassPool;
/*     */ import javassist.CtClass;
/*     */ import javassist.CtMethod;
/*     */ import javassist.CtNewMethod;
/*     */ import javassist.LoaderClassPath;
/*     */ import javassist.Modifier;
/*     */ import javassist.NotFoundException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JavassistProxyFactory
/*     */ {
/*     */   private static ClassPool classPool;
/*  44 */   private static String genDirectory = "";
/*     */   
/*     */   public static void main(String... paramVarArgs) {
/*  47 */     classPool = new ClassPool();
/*  48 */     classPool.importPackage("java.sql");
/*  49 */     classPool.appendClassPath((ClassPath)new LoaderClassPath(JavassistProxyFactory.class.getClassLoader()));
/*     */     
/*  51 */     if (paramVarArgs.length > 0) {
/*  52 */       genDirectory = paramVarArgs[0];
/*     */     }
/*     */ 
/*     */     
/*  56 */     String str = "{ try { return delegate.method($$); } catch (SQLException e) { throw checkException(e); } }";
/*  57 */     generateProxyClass(Connection.class, ProxyConnection.class.getName(), str);
/*  58 */     generateProxyClass(Statement.class, ProxyStatement.class.getName(), str);
/*  59 */     generateProxyClass(ResultSet.class, ProxyResultSet.class.getName(), str);
/*  60 */     generateProxyClass(DatabaseMetaData.class, ProxyDatabaseMetaData.class.getName(), str);
/*     */ 
/*     */     
/*  63 */     str = "{ try { return ((cast) delegate).method($$); } catch (SQLException e) { throw checkException(e); } }";
/*  64 */     generateProxyClass(PreparedStatement.class, ProxyPreparedStatement.class.getName(), str);
/*  65 */     generateProxyClass(CallableStatement.class, ProxyCallableStatement.class.getName(), str);
/*     */     
/*  67 */     modifyProxyFactory();
/*     */   }
/*     */   
/*     */   private static void modifyProxyFactory() {
/*  71 */     System.out.println("Generating method bodies for com.zaxxer.hikari.proxy.ProxyFactory");
/*     */     
/*  73 */     String str = ProxyConnection.class.getPackage().getName();
/*  74 */     CtClass ctClass = classPool.getCtClass("fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.pool.ProxyFactory");
/*  75 */     for (CtMethod ctMethod : ctClass.getMethods()) {
/*  76 */       switch (ctMethod.getName()) {
/*     */         case "getProxyConnection":
/*  78 */           ctMethod.setBody("{return new " + str + ".HikariProxyConnection($$);}");
/*     */           break;
/*     */         case "getProxyStatement":
/*  81 */           ctMethod.setBody("{return new " + str + ".HikariProxyStatement($$);}");
/*     */           break;
/*     */         case "getProxyPreparedStatement":
/*  84 */           ctMethod.setBody("{return new " + str + ".HikariProxyPreparedStatement($$);}");
/*     */           break;
/*     */         case "getProxyCallableStatement":
/*  87 */           ctMethod.setBody("{return new " + str + ".HikariProxyCallableStatement($$);}");
/*     */           break;
/*     */         case "getProxyResultSet":
/*  90 */           ctMethod.setBody("{return new " + str + ".HikariProxyResultSet($$);}");
/*     */           break;
/*     */         case "getProxyDatabaseMetaData":
/*  93 */           ctMethod.setBody("{return new " + str + ".HikariProxyDatabaseMetaData($$);}");
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 101 */     ctClass.writeFile(genDirectory + "target/classes");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> void generateProxyClass(Class<T> paramClass, String paramString1, String paramString2) {
/* 109 */     String str = paramString1.replaceAll("(.+)\\.(\\w+)", "$1.Hikari$2");
/*     */     
/* 111 */     CtClass ctClass1 = classPool.getCtClass(paramString1);
/* 112 */     CtClass ctClass2 = classPool.makeClass(str, ctClass1);
/* 113 */     ctClass2.setModifiers(Modifier.setPublic(16));
/*     */     
/* 115 */     System.out.println("Generating " + str);
/*     */ 
/*     */     
/* 118 */     HashSet<String> hashSet1 = new HashSet();
/* 119 */     for (CtMethod ctMethod : ctClass1.getMethods()) {
/* 120 */       if ((ctMethod.getModifiers() & 0x10) == 16) {
/* 121 */         hashSet1.add(ctMethod.getName() + ctMethod.getSignature());
/*     */       }
/*     */     } 
/*     */     
/* 125 */     HashSet<String> hashSet2 = new HashSet();
/* 126 */     for (Class<?> clazz : getAllInterfaces(paramClass)) {
/* 127 */       CtClass ctClass = classPool.getCtClass(clazz.getName());
/* 128 */       ctClass2.addInterface(ctClass);
/* 129 */       for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
/* 130 */         String str1 = ctMethod.getName() + ctMethod.getSignature();
/*     */ 
/*     */         
/* 133 */         if (!hashSet1.contains(str1))
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 138 */           if (!hashSet2.contains(str1)) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 143 */             hashSet2.add(str1);
/*     */ 
/*     */             
/* 146 */             CtMethod ctMethod1 = CtNewMethod.copy(ctMethod, ctClass2, null);
/*     */             
/* 148 */             String str2 = paramString2;
/*     */ 
/*     */             
/* 151 */             CtMethod ctMethod2 = ctClass1.getMethod(ctMethod.getName(), ctMethod.getSignature());
/* 152 */             if ((ctMethod2.getModifiers() & 0x400) != 1024 && !isDefaultMethod(clazz, ctMethod)) {
/* 153 */               str2 = str2.replace("((cast) ", "");
/* 154 */               str2 = str2.replace("delegate", "super");
/* 155 */               str2 = str2.replace("super)", "super");
/*     */             } 
/*     */             
/* 158 */             str2 = str2.replace("cast", paramClass.getName());
/*     */ 
/*     */             
/* 161 */             if (isThrowsSqlException(ctMethod)) {
/* 162 */               str2 = str2.replace("method", ctMethod1.getName());
/*     */             } else {
/*     */               
/* 165 */               str2 = "{ return ((cast) delegate).method($$); }".replace("method", ctMethod1.getName()).replace("cast", paramClass.getName());
/*     */             } 
/*     */             
/* 168 */             if (ctMethod1.getReturnType() == CtClass.voidType) {
/* 169 */               str2 = str2.replace("return", "");
/*     */             }
/*     */             
/* 172 */             ctMethod1.setBody(str2);
/* 173 */             ctClass2.addMethod(ctMethod1);
/*     */           }  } 
/*     */       } 
/*     */     } 
/* 177 */     ctClass2.getClassFile().setMajorVersion(52);
/* 178 */     ctClass2.writeFile(genDirectory + "target/classes");
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isThrowsSqlException(CtMethod paramCtMethod) {
/*     */     try {
/* 184 */       for (CtClass ctClass : paramCtMethod.getExceptionTypes()) {
/* 185 */         if (ctClass.getSimpleName().equals("SQLException")) {
/* 186 */           return true;
/*     */         }
/*     */       }
/*     */     
/* 190 */     } catch (NotFoundException notFoundException) {}
/*     */ 
/*     */ 
/*     */     
/* 194 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isDefaultMethod(Class<?> paramClass, CtMethod paramCtMethod) {
/* 199 */     ArrayList<Class<?>> arrayList = new ArrayList();
/*     */     
/* 201 */     for (CtClass ctClass : paramCtMethod.getParameterTypes()) {
/* 202 */       arrayList.add(toJavaClass(ctClass));
/*     */     }
/*     */     
/* 205 */     return paramClass.getDeclaredMethod(paramCtMethod.getName(), (Class[])arrayList.<Class<?>[]>toArray((Class<?>[][])new Class[0])).toString().contains("default ");
/*     */   }
/*     */ 
/*     */   
/*     */   private static Set<Class<?>> getAllInterfaces(Class<?> paramClass) {
/* 210 */     LinkedHashSet<Class<?>> linkedHashSet = new LinkedHashSet();
/* 211 */     for (Class<?> clazz : paramClass.getInterfaces()) {
/* 212 */       if ((clazz.getInterfaces()).length > 0) {
/* 213 */         linkedHashSet.addAll(getAllInterfaces(clazz));
/*     */       }
/* 215 */       linkedHashSet.add(clazz);
/*     */     } 
/* 217 */     if (paramClass.getSuperclass() != null) {
/* 218 */       linkedHashSet.addAll(getAllInterfaces(paramClass.getSuperclass()));
/*     */     }
/*     */     
/* 221 */     if (paramClass.isInterface()) {
/* 222 */       linkedHashSet.add(paramClass);
/*     */     }
/*     */     
/* 225 */     return linkedHashSet;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Class<?> toJavaClass(CtClass paramCtClass) {
/* 230 */     if (paramCtClass.getName().endsWith("[]")) {
/* 231 */       return Array.newInstance(toJavaClass(paramCtClass.getName().replace("[]", "")), 0).getClass();
/*     */     }
/*     */     
/* 234 */     return toJavaClass(paramCtClass.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> toJavaClass(String paramString) {
/* 240 */     switch (paramString) {
/*     */       case "int":
/* 242 */         return int.class;
/*     */       case "long":
/* 244 */         return long.class;
/*     */       case "short":
/* 246 */         return short.class;
/*     */       case "byte":
/* 248 */         return byte.class;
/*     */       case "float":
/* 250 */         return float.class;
/*     */       case "double":
/* 252 */         return double.class;
/*     */       case "boolean":
/* 254 */         return boolean.class;
/*     */       case "char":
/* 256 */         return char.class;
/*     */       case "void":
/* 258 */         return void.class;
/*     */     } 
/* 260 */     return Class.forName(paramString);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\libs\hikar\\util\JavassistProxyFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */