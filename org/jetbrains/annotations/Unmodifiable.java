package org.jetbrains.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE_USE})
public @interface Unmodifiable {}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\org\jetbrains\annotations\Unmodifiable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */