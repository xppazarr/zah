/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.asm;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import org.objectweb.asm.Opcodes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ASMVersion
/*     */ {
/*     */   protected static final int LATEST_ASM_OPCODE_VERSION;
/*     */   protected static final int USED_ASM_OPCODE_VERSION;
/*  37 */   protected static final int CURRENT_JAVA_VERSION = getJavaVersion();
/*  38 */   protected static final int CURRENT_JAVA_FILE_FORMAT = javaVersionToClassFileFormat(CURRENT_JAVA_VERSION); protected static final int USED_JAVA_FILE_FORMAT;
/*     */   static {
/*  40 */     int i = 0;
/*  41 */     int j = 0;
/*     */     
/*     */     try {
/*  44 */       for (Field field : Opcodes.class.getDeclaredFields()) {
/*  45 */         String str = field.getName();
/*  46 */         if (!str.contains("EXPERIMENTAL")) {
/*     */           
/*  48 */           if (str.startsWith("ASM")) {
/*  49 */             i = Math.max(i, field.getInt(null));
/*     */           }
/*  51 */           if (str.startsWith("V") && str.length() <= 4)
/*  52 */             j = Math.max(j, field.getInt(null)); 
/*     */         } 
/*     */       } 
/*  55 */     } catch (IllegalAccessException illegalAccessException) {
/*  56 */       throw new IllegalStateException(illegalAccessException);
/*     */     } 
/*     */ 
/*     */     
/*  60 */     if (i == 0) i = 393216; 
/*  61 */     if (j == 0) j = 52;
/*     */     
/*  63 */     LATEST_ASM_OPCODE_VERSION = i;
/*  64 */     LATEST_SUPPORTED_JAVA_CLASS_FILE_FORMAT_VERSION = j;
/*     */     
/*  66 */     int k = i;
/*  67 */     int m = Math.min(CURRENT_JAVA_FILE_FORMAT, LATEST_SUPPORTED_JAVA_CLASS_FILE_FORMAT_VERSION);
/*     */     try {
/*  69 */       String str1 = System.getProperty("xseries.xreflection.asm.version");
/*  70 */       String str2 = System.getProperty("xseries.xreflection.asm.javaVersion");
/*     */       
/*  72 */       if (str1 != null) {
/*  73 */         k = Integer.parseInt(str1);
/*  74 */         System.out.println("[XSeries/XReflection] Using custom ASM version: " + k);
/*     */       } 
/*  76 */       if (str2 != null) {
/*  77 */         m = Integer.parseInt(str2);
/*  78 */         System.out.println("[XSeries/XReflection] Using custom ASM Java target version: " + m);
/*     */       } 
/*  80 */     } catch (SecurityException securityException) {}
/*     */ 
/*     */ 
/*     */     
/*  84 */     USED_ASM_OPCODE_VERSION = k;
/*  85 */     USED_JAVA_FILE_FORMAT = m;
/*     */   }
/*     */   protected static final int LATEST_SUPPORTED_JAVA_CLASS_FILE_FORMAT_VERSION;
/*     */   
/*     */   protected static int getASMOpcodeVersion(int paramInt) {
/*  90 */     return paramInt << 16 | 0x0;
/*     */   }
/*     */   
/*     */   protected static int getJavaVersion() {
/*  94 */     String str = System.getProperty("java.version");
/*  95 */     if (str.startsWith("1.")) {
/*  96 */       str = str.substring(2, 3);
/*     */     } else {
/*  98 */       int i = str.indexOf('.');
/*  99 */       if (i != -1) {
/* 100 */         str = str.substring(0, i);
/*     */       }
/*     */     } 
/* 103 */     return Integer.parseInt(str);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static int javaVersionToClassFileFormat(int paramInt) {
/* 108 */     return 0x0 | 44 + paramInt;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\asm\ASMVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */