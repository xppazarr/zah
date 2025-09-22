package fr.maxlego08.zauctionhouse.zcore.utils.sarah;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
  String value();
  
  boolean primary() default false;
  
  boolean autoIncrement() default false;
  
  boolean foreignKey() default false;
  
  String foreignKeyReference() default "";
  
  String type() default "";
  
  boolean nullable() default false;
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\Column.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */