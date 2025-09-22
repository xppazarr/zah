/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.commands;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public abstract class Arguments
/*     */   extends ZUtils
/*     */ {
/*     */   protected String[] args;
/*  15 */   protected int parentCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String argAsString(int paramInt) {
/*     */     try {
/*  24 */       return this.args[paramInt + this.parentCount];
/*  25 */     } catch (Exception exception) {
/*  26 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String argAsString(int paramInt, String paramString) {
/*     */     try {
/*  38 */       return this.args[paramInt + this.parentCount];
/*  39 */     } catch (Exception exception) {
/*  40 */       return paramString;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean argAsBoolean(int paramInt) {
/*  51 */     return Boolean.valueOf(argAsString(paramInt)).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean argAsBoolean(int paramInt, boolean paramBoolean) {
/*     */     try {
/*  62 */       return Boolean.valueOf(argAsString(paramInt)).booleanValue();
/*  63 */     } catch (Exception exception) {
/*  64 */       return paramBoolean;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int argAsInteger(int paramInt) {
/*  74 */     return Integer.valueOf(argAsString(paramInt)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int argAsInteger(int paramInt1, int paramInt2) {
/*     */     try {
/*  85 */       return Integer.valueOf(argAsString(paramInt1)).intValue();
/*  86 */     } catch (Exception exception) {
/*  87 */       return paramInt2;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long argAsLong(int paramInt) {
/*  97 */     return Long.valueOf(argAsString(paramInt)).longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long argAsLong(int paramInt, long paramLong) {
/*     */     try {
/* 108 */       return Long.valueOf(argAsString(paramInt)).longValue();
/* 109 */     } catch (Exception exception) {
/* 110 */       return paramLong;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double argAsDouble(int paramInt, double paramDouble) {
/*     */     try {
/* 122 */       return Double.valueOf(argAsString(paramInt).replace(",", ".")).doubleValue();
/* 123 */     } catch (Exception exception) {
/* 124 */       return paramDouble;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double argAsDouble(int paramInt) {
/* 134 */     return Double.valueOf(argAsString(paramInt).replace(",", ".")).doubleValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Player argAsPlayer(int paramInt) {
/* 143 */     return Bukkit.getPlayer(argAsString(paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Player argAsPlayer(int paramInt, Player paramPlayer) {
/*     */     try {
/* 154 */       return Bukkit.getPlayer(argAsString(paramInt));
/* 155 */     } catch (Exception exception) {
/* 156 */       return paramPlayer;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected OfflinePlayer argAsOfflinePlayer(int paramInt) {
/* 166 */     return Bukkit.getOfflinePlayer(argAsString(paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected OfflinePlayer argAsOfflinePlayer(int paramInt, OfflinePlayer paramOfflinePlayer) {
/*     */     try {
/* 177 */       return Bukkit.getOfflinePlayer(argAsString(paramInt));
/* 178 */     } catch (Exception exception) {
/* 179 */       return paramOfflinePlayer;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Location argAsLocation(int paramInt) {
/* 189 */     return changeStringLocationToLocationEye(argAsString(paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Location argAsLocation(int paramInt, Location paramLocation) {
/*     */     try {
/* 200 */       return changeStringLocationToLocationEye(argAsString(paramInt));
/* 201 */     } catch (Exception exception) {
/* 202 */       return paramLocation;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EntityType argAsEntityType(int paramInt) {
/* 212 */     return EntityType.valueOf(argAsString(paramInt).toUpperCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EntityType argAsEntityType(int paramInt, EntityType paramEntityType) {
/*     */     try {
/* 223 */       return EntityType.valueOf(argAsString(paramInt).toUpperCase());
/* 224 */     } catch (Exception exception) {
/* 225 */       return paramEntityType;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected World argAsWorld(int paramInt) {
/*     */     try {
/* 236 */       return Bukkit.getWorld(argAsString(paramInt));
/* 237 */     } catch (Exception exception) {
/* 238 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected World argAsWorld(int paramInt, World paramWorld) {
/*     */     try {
/* 249 */       return Bukkit.getWorld(argAsString(paramInt));
/* 250 */     } catch (Exception exception) {
/* 251 */       return paramWorld;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\commands\Arguments.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */