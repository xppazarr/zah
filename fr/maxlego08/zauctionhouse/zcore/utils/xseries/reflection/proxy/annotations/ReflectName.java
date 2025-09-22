package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ReflectNames.class)
public @interface ReflectName {
  String[] value();
  
  String version() default "";
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\proxy\annotations\ReflectName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */