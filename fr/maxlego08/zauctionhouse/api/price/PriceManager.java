package fr.maxlego08.zauctionhouse.api.price;

import fr.maxlego08.zauctionhouse.api.AuctionItem;
import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
import fr.maxlego08.zauctionhouse.api.economy.EconomyLimit;
import fr.maxlego08.zauctionhouse.api.storage.Savable;
import java.util.Optional;
import org.bukkit.inventory.ItemStack;

public interface PriceManager extends Savable {
  long getMinPrice(ItemStack paramItemStack, AuctionEconomy paramAuctionEconomy);
  
  long getMinPrice(AuctionItem paramAuctionItem, AuctionEconomy paramAuctionEconomy);
  
  long getMaxPrice(AuctionItem paramAuctionItem, AuctionEconomy paramAuctionEconomy);
  
  Optional<PriceItem> getPrice(ItemStack paramItemStack);
  
  Optional<EconomyLimit> getEconomyLimit(AuctionEconomy paramAuctionEconomy);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\price\PriceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */