package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm;

import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.minecraft.MinecraftMapping;
import org.intellij.lang.annotations.Pattern;

public interface NameableReflectiveHandle extends NamedReflectiveHandle {
  NameableReflectiveHandle map(MinecraftMapping paramMinecraftMapping, @Pattern("\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*") String paramString);
  
  NameableReflectiveHandle named(@Pattern("\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*") String... paramVarArgs);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\jvm\NameableReflectiveHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */