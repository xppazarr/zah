/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.folialib.impl;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.FoliaLib;
/*    */ import java.util.logging.Logger;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ 
/*    */ 
/*    */ public class UnsupportedImplementation
/*    */   extends LegacySpigotImplementation
/*    */ {
/*    */   public UnsupportedImplementation(FoliaLib paramFoliaLib) {
/* 12 */     super(paramFoliaLib);
/*    */     
/* 14 */     JavaPlugin javaPlugin = paramFoliaLib.getPlugin();
/* 15 */     Logger logger = javaPlugin.getLogger();
/*    */     
/* 17 */     logger.warning(
/* 18 */         String.format("\n---------------------------------------------------------------------\nFoliaLib does not support this server software! (%s)\nFoliaLib will attempt to use the legacy spigot implementation.\n---------------------------------------------------------------------\n", new Object[] {
/*    */ 
/*    */ 
/*    */ 
/*    */             
/* 23 */             javaPlugin.getServer().getVersion()
/*    */           }));
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\folialib\impl\UnsupportedImplementation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */