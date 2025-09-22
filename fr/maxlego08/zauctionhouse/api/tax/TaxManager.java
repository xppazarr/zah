package fr.maxlego08.zauctionhouse.api.tax;

import fr.maxlego08.zauctionhouse.api.storage.Savable;
import java.util.Optional;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface TaxManager extends Savable {
  Optional<Tax> getTax(ItemStack paramItemStack);
  
  double getTax(Player paramPlayer, long paramLong, ItemStack paramItemStack, TaxType paramTaxType);
  
  double reverseTax(long paramLong, ItemStack paramItemStack);
  
  double getTaxPercent(ItemStack paramItemStack, TaxType paramTaxType);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\tax\TaxManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */