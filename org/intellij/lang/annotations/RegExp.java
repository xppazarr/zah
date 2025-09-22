package org.intellij.lang.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NonNls;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE})
@Language("RegExp")
public @interface RegExp {
  @NonNls
  String prefix() default "";
  
  @NonNls
  String suffix() default "";
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\org\intellij\lang\annotations\RegExp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */