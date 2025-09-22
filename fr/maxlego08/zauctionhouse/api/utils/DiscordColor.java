/*    */ package fr.maxlego08.zauctionhouse.api.utils;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DiscordColor
/*    */ {
/*    */   private final int r;
/*    */   private final int g;
/*    */   private final int b;
/*    */   
/*    */   public DiscordColor(int paramInt1, int paramInt2, int paramInt3) {
/* 20 */     this.r = paramInt1;
/* 21 */     this.g = paramInt2;
/* 22 */     this.b = paramInt3;
/*    */   }
/*    */   
/*    */   public DiscordColor(YamlConfiguration paramYamlConfiguration, String paramString) {
/* 26 */     this(paramYamlConfiguration.getInt(paramString + "r"), paramYamlConfiguration.getInt(paramString + "g"), paramYamlConfiguration.getInt(paramString + "b"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getR() {
/* 33 */     return this.r;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getG() {
/* 40 */     return this.g;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getB() {
/* 47 */     return this.b;
/*    */   }
/*    */   
/*    */   public Color getColor() {
/* 51 */     return new Color(this.r, this.g, this.b);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\ap\\utils\DiscordColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */