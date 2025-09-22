/*    */ package fr.maxlego08.zauctionhouse.command;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.command.Command;
/*    */ import fr.maxlego08.zauctionhouse.api.inventory.Inventory;
/*    */ import java.util.List;
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
/*    */ public class CommandObject
/*    */   implements Command
/*    */ {
/*    */   private final String command;
/*    */   private final List<String> aliases;
/*    */   private final Inventory inventory;
/*    */   private final String permission;
/*    */   private final String description;
/*    */   
/*    */   public CommandObject(String paramString1, List<String> paramList, Inventory paramInventory, String paramString2, String paramString3) {
/* 26 */     this.command = paramString1;
/* 27 */     this.aliases = paramList;
/* 28 */     this.inventory = paramInventory;
/* 29 */     this.permission = paramString2;
/* 30 */     this.description = paramString3;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getCommand() {
/* 35 */     return this.command;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getAliases() {
/* 40 */     return this.aliases;
/*    */   }
/*    */ 
/*    */   
/*    */   public Inventory getInventory() {
/* 45 */     return this.inventory;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPermission() {
/* 50 */     return this.permission;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 55 */     return this.description;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 65 */     return "CommandObject [command=" + this.command + ", aliases=" + this.aliases + ", inventory=" + this.inventory + ", permission=" + this.permission + ", description=" + this.description + ", getCommand()=" + 
/* 66 */       getCommand() + ", getAliases()=" + 
/* 67 */       getAliases() + ", getInventory()=" + getInventory() + ", getPermission()=" + 
/* 68 */       getPermission() + ", getDescription()=" + getDescription() + ", getClass()=" + getClass() + ", hashCode()=" + 
/* 69 */       hashCode() + ", toString()=" + super.toString() + "]";
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\CommandObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */