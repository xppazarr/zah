package fr.maxlego08.zauctionhouse.api;

import fr.maxlego08.zauctionhouse.api.category.CategoryManager;
import fr.maxlego08.zauctionhouse.api.inventory.InventoryManager;
import fr.maxlego08.zauctionhouse.api.inventory.VInventoryManager;
import fr.maxlego08.zauctionhouse.api.storage.CustomStorageProvider;
import fr.maxlego08.zauctionhouse.api.translation.TranslationManager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public interface AuctionPlugin extends Plugin {
  InventoryManager getInventories();
  
  VInventoryManager getInventoryManager();
  
  CategoryManager getCategoryManager();
  
  TranslationManager getTranslationManager();
  
  String translateItemStack(ItemStack paramItemStack);
  
  CustomStorageProvider getCustomStorageProvider();
  
  void setCustomStorageProvider(CustomStorageProvider paramCustomStorageProvider);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\AuctionPlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */