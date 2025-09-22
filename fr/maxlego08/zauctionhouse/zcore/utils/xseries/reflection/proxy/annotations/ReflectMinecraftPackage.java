package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations;

import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.minecraft.MinecraftPackage;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReflectMinecraftPackage {
  MinecraftPackage type();
  
  String packageName() default "";
  
  boolean ignoreCurrentName() default false;
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\proxy\annotations\ReflectMinecraftPackage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */