package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.classes;

import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

public interface PackageHandle {
  @Language("RegExp")
  @Internal
  public static final String JAVA_PACKAGE_PATTERN = "(\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*\\.)*\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*";
  
  @Language("RegExp")
  @Internal
  public static final String JAVA_IDENTIFIER_PATTERN = "\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*";
  
  @NotNull
  String packageId();
  
  @NotNull
  String getBasePackageName();
  
  @NotNull
  String getPackage(@NotNull String paramString);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\jvm\classes\PackageHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */