package fr.maxlego08.zauctionhouse.zcore.utils.commands;

import java.util.List;
import org.bukkit.command.CommandSender;

@FunctionalInterface
public interface CollectionBiConsumer {
  List<String> accept(CommandSender paramCommandSender, String[] paramArrayOfString);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\commands\CollectionBiConsumer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */