package org.intellij.lang.annotations;

import java.lang.annotation.Documented;

@Documented
@Pattern("(?:[^%]|%%|(?:%(?:\\d+\\$)?(?:[-#+ 0,(<]*)?(?:\\d+)?(?:\\.\\d+)?(?:[tT])?(?:[a-zA-Z%])))*")
public @interface PrintFormat {}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\org\intellij\lang\annotations\PrintFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */