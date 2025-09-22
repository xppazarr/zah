package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection;

import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.NamedReflectiveHandle;
import org.jetbrains.annotations.ApiStatus.Experimental;

@Experimental
public interface ReflectiveMapping {
  boolean shouldBeChecked();
  
  String category();
  
  String name();
  
  String process(NamedReflectiveHandle paramNamedReflectiveHandle, String paramString);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\ReflectiveMapping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */