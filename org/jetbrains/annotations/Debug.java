/*    */ package org.jetbrains.annotations;
/*    */ 
/*    */ import java.lang.annotation.ElementType;
/*    */ import java.lang.annotation.Retention;
/*    */ import java.lang.annotation.RetentionPolicy;
/*    */ import java.lang.annotation.Target;
/*    */ import org.intellij.lang.annotations.Language;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Debug
/*    */ {
/*    */   private Debug() {
/* 34 */     throw new AssertionError("Debug should not be instantiated");
/*    */   }
/*    */   
/*    */   @Target({ElementType.TYPE})
/*    */   @Retention(RetentionPolicy.CLASS)
/*    */   public static @interface Renderer {
/*    */     @Language(value = "JAVA", prefix = "class Renderer{String $text(){return ", suffix = ";}}")
/*    */     @NonNls
/*    */     String text() default "";
/*    */     
/*    */     @Language(value = "JAVA", prefix = "class Renderer{Object[] $childrenArray(){return ", suffix = ";}}")
/*    */     @NonNls
/*    */     String childrenArray() default "";
/*    */     
/*    */     @Language(value = "JAVA", prefix = "class Renderer{boolean $hasChildren(){return ", suffix = ";}}")
/*    */     @NonNls
/*    */     String hasChildren() default "";
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\org\jetbrains\annotations\Debug.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */