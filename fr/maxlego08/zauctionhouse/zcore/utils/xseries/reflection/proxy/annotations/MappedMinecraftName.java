package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.annotations;

import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.minecraft.MinecraftMapping;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(MappedMinecraftNames.class)
public @interface MappedMinecraftName {
  MinecraftMapping mapping();
  
  ReflectName[] names();
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\proxy\annotations\MappedMinecraftName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */