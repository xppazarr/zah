/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.folialib.enums;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.util.ImplementationTestsUtil;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum ImplementationType
/*    */ {
/* 22 */   FOLIA("FoliaImplementation", (Supplier<Boolean>[])new Supplier[0], new String[] { "io.papermc.paper.threadedregions.RegionizedServer"
/*    */ 
/*    */ 
/*    */     
/*    */     }),
/* 27 */   PAPER("PaperImplementation", (Supplier<Boolean>[])new Supplier[] { ImplementationTestsUtil::isTaskConsumersSupported }, new String[] { "com.destroystokyo.paper.PaperConfig", "io.papermc.paper.configuration.Configuration"
/*    */ 
/*    */ 
/*    */     
/*    */     }),
/* 32 */   LEGACY_PAPER("LegacyPaperImplementation", (Supplier<Boolean>[])new Supplier[0], new String[] { "com.destroystokyo.paper.PaperConfig", "io.papermc.paper.configuration.Configuration"
/*    */ 
/*    */ 
/*    */     
/*    */     }),
/* 37 */   SPIGOT("SpigotImplementation", (Supplier<Boolean>[])new Supplier[] { ImplementationTestsUtil::isTaskConsumersSupported }, new String[] { "org.spigotmc.SpigotConfig"
/*    */ 
/*    */ 
/*    */     
/*    */     }),
/* 42 */   LEGACY_SPIGOT("LegacySpigotImplementation", (Supplier<Boolean>[])new Supplier[0], new String[] { "org.spigotmc.SpigotConfig"
/*    */ 
/*    */ 
/*    */     
/*    */     }),
/* 47 */   UNKNOWN("UnsupportedImplementation", (Supplier<Boolean>[])new Supplier[0], new String[0]);
/*    */   
/*    */   private final String implementationClassName;
/*    */   
/*    */   private final Supplier<Boolean>[] tests;
/*    */   
/*    */   private final String[] classNames;
/*    */ 
/*    */   
/*    */   ImplementationType(String paramString1, Supplier<Boolean>[] paramArrayOfSupplier, String... paramVarArgs) {
/* 57 */     this.implementationClassName = paramString1;
/* 58 */     this.tests = paramArrayOfSupplier;
/* 59 */     this.classNames = paramVarArgs;
/*    */   }
/*    */   
/*    */   public String getImplementationClassName() {
/* 63 */     return this.implementationClassName;
/*    */   }
/*    */   
/*    */   public Supplier<Boolean>[] getTests() {
/* 67 */     return this.tests;
/*    */   }
/*    */   
/*    */   public String[] getClassNames() {
/* 71 */     return this.classNames;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean selfCheck() {
/* 76 */     for (Supplier<Boolean> supplier : getTests()) {
/* 77 */       if (!((Boolean)supplier.get()).booleanValue()) return false;
/*    */     
/*    */     } 
/*    */     
/* 81 */     String[] arrayOfString = getClassNames();
/*    */ 
/*    */     
/* 84 */     for (String str : arrayOfString) {
/*    */       
/*    */       try {
/* 87 */         Class.forName(str);
/*    */ 
/*    */         
/* 90 */         return true;
/* 91 */       } catch (ClassNotFoundException classNotFoundException) {}
/*    */     } 
/*    */     
/* 94 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\folialib\enums\ImplementationType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */