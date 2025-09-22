package fr.maxlego08.zauctionhouse.api;

import fr.maxlego08.zauctionhouse.api.category.Category;
import fr.maxlego08.zauctionhouse.api.category.CategoryManager;
import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
import fr.maxlego08.zauctionhouse.api.enums.AuctionType;
import fr.maxlego08.zauctionhouse.api.enums.Message;
import fr.maxlego08.zauctionhouse.api.enums.StorageType;
import fr.maxlego08.zauctionhouse.api.filter.Filter;
import fr.maxlego08.zauctionhouse.api.transaction.Transaction;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface AuctionItem {
  UUID getUniqueId();
  
  ItemStack getItemStack();
  
  AuctionType getType();
  
  long getPrice();
  
  void setPrice(long paramLong);
  
  AuctionEconomy getEconomy();
  
  String getEconomyName();
  
  OfflinePlayer getSeller();
  
  OfflinePlayer getBuyer();
  
  void setBuyer(UUID paramUUID);
  
  UUID getSellerUniqueId();
  
  UUID getBuyerUniqueId();
  
  long getExpireAt();
  
  void setExpireAt(long paramLong);
  
  String serializedItem();
  
  ItemStack createItem(Player paramPlayer, Message paramMessage);
  
  boolean canBuy();
  
  void setCanBuy(boolean paramBoolean);
  
  boolean isAlreadyBuying();
  
  void setAlreadyBuying(boolean paramBoolean);
  
  int getAmount();
  
  Transaction buildTransaction(long paramLong);
  
  List<ItemStack> getItemStacks();
  
  List<String> serializedItems();
  
  boolean hasFreeSpace(Player paramPlayer);
  
  boolean match(Optional<Filter> paramOptional, String paramString);
  
  boolean isExpired();
  
  StorageType getStorageType();
  
  void setStorageType(StorageType paramStorageType);
  
  String getSellerName();
  
  void giveIf(Player paramPlayer, Predicate<AuctionItem> paramPredicate);
  
  int getPriority();
  
  String priceFormat();
  
  String timeFormat();
  
  String statusFormat(UUID paramUUID);
  
  String getServer();
  
  List<Category> getCategories();
  
  void setCategories(CategoryManager paramCategoryManager);
  
  String getItemName();
  
  String getMaterialName();
  
  int getItemStackSize();
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\AuctionItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */