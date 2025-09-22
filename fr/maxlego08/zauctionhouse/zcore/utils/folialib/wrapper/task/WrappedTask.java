package fr.maxlego08.zauctionhouse.zcore.utils.folialib.wrapper.task;

import org.bukkit.plugin.Plugin;

public interface WrappedTask {
  void cancel();
  
  boolean isCancelled();
  
  Plugin getOwningPlugin();
  
  boolean isAsync();
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\folialib\wrapper\task\WrappedTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */