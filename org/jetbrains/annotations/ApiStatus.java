/*    */ package org.jetbrains.annotations;
/*    */ 
/*    */ import java.lang.annotation.Documented;
/*    */ import java.lang.annotation.ElementType;
/*    */ import java.lang.annotation.Retention;
/*    */ import java.lang.annotation.RetentionPolicy;
/*    */ import java.lang.annotation.Target;
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
/*    */ public final class ApiStatus
/*    */ {
/*    */   private ApiStatus() {
/* 31 */     throw new AssertionError("ApiStatus should not be instantiated");
/*    */   }
/*    */   
/*    */   @Documented
/*    */   @Retention(RetentionPolicy.CLASS)
/*    */   @Target({ElementType.TYPE, ElementType.METHOD})
/*    */   public static @interface OverrideOnly {}
/*    */   
/*    */   @Documented
/*    */   @Retention(RetentionPolicy.CLASS)
/*    */   @Target({ElementType.TYPE, ElementType.METHOD})
/*    */   public static @interface NonExtendable {}
/*    */   
/*    */   @Documented
/*    */   @Retention(RetentionPolicy.CLASS)
/*    */   @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.PACKAGE})
/*    */   public static @interface AvailableSince {
/*    */     String value();
/*    */   }
/*    */   
/*    */   @Documented
/*    */   @Retention(RetentionPolicy.CLASS)
/*    */   @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.PACKAGE})
/*    */   public static @interface ScheduledForRemoval {
/*    */     String inVersion() default "";
/*    */   }
/*    */   
/*    */   @Documented
/*    */   @Retention(RetentionPolicy.CLASS)
/*    */   @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.PACKAGE})
/*    */   public static @interface Obsolete {
/*    */     String since() default "";
/*    */   }
/*    */   
/*    */   @Documented
/*    */   @Retention(RetentionPolicy.CLASS)
/*    */   @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.PACKAGE})
/*    */   public static @interface Internal {}
/*    */   
/*    */   @Documented
/*    */   @Retention(RetentionPolicy.CLASS)
/*    */   @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.PACKAGE})
/*    */   public static @interface Experimental {}
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\org\jetbrains\annotations\ApiStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */