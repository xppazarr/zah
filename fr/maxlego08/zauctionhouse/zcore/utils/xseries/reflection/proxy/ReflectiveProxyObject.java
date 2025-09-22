package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy;

import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations.Ignore;
import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.ApiStatus.OverrideOnly;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Experimental
public interface ReflectiveProxyObject {
  @Ignore
  @NotNull
  @NonExtendable
  @Contract(pure = true)
  Object instance();
  
  @Ignore
  @NotNull
  @NonExtendable
  @Contract(pure = true)
  Class<?> getTargetClass();
  
  @Ignore
  @NotNull
  @NonExtendable
  @Contract(pure = true)
  boolean isInstance(@Nullable Object paramObject);
  
  @Ignore
  @NotNull
  @OverrideOnly
  @Contract(value = "_ -> new", pure = true)
  ReflectiveProxyObject bindTo(@NotNull Object paramObject);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\proxy\ReflectiveProxyObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */