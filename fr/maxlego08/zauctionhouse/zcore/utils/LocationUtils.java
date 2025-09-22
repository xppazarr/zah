/*     */ package fr.maxlego08.zauctionhouse.zcore.utils;
/*     */ 
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Chunk;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class LocationUtils
/*     */   extends PapiUtils
/*     */ {
/*     */   protected Location changeStringLocationToLocation(String paramString) {
/*  17 */     return changeStringLocationToLocationEye(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Location changeStringLocationToLocationEye(String paramString) {
/*  27 */     String[] arrayOfString = paramString.split(",");
/*  28 */     World world = Bukkit.getServer().getWorld(arrayOfString[0]);
/*  29 */     float f1 = Float.parseFloat(arrayOfString[1]);
/*  30 */     float f2 = Float.parseFloat(arrayOfString[2]);
/*  31 */     float f3 = Float.parseFloat(arrayOfString[3]);
/*  32 */     if (arrayOfString.length == 6) {
/*  33 */       float f4 = Float.parseFloat(arrayOfString[4]);
/*  34 */       float f5 = Float.parseFloat(arrayOfString[5]);
/*  35 */       return new Location(world, f1, f2, f3, f4, f5);
/*     */     } 
/*  37 */     return new Location(world, f1, f2, f3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String changeLocationToString(Location paramLocation) {
/*  45 */     return paramLocation.getWorld().getName() + "," + paramLocation.getBlockX() + "," + paramLocation.getBlockY() + "," + paramLocation
/*  46 */       .getBlockZ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String changeLocationToStringEye(Location paramLocation) {
/*  55 */     return paramLocation.getWorld().getName() + "," + paramLocation.getBlockX() + "," + paramLocation.getBlockY() + "," + paramLocation
/*  56 */       .getBlockZ() + "," + paramLocation.getYaw() + "," + paramLocation.getPitch();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Chunk changeStringChuncToChunk(String paramString) {
/*  65 */     String[] arrayOfString = paramString.split(",");
/*  66 */     World world = Bukkit.getServer().getWorld(arrayOfString[0]);
/*  67 */     return world.getChunkAt(Integer.valueOf(arrayOfString[1]).intValue(), Integer.valueOf(arrayOfString[2]).intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String changeChunkToString(Chunk paramChunk) {
/*  75 */     return paramChunk.getWorld().getName() + "," + paramChunk.getX() + "," + paramChunk.getZ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String changeCuboidToString(Cuboid paramCuboid) {
/*  84 */     return paramCuboid.getWorld().getName() + "," + paramCuboid.getLowerX() + "," + paramCuboid.getLowerY() + "," + paramCuboid
/*  85 */       .getLowerZ() + ",;" + paramCuboid.getWorld().getName() + "," + paramCuboid.getUpperX() + "," + paramCuboid
/*  86 */       .getUpperY() + "," + paramCuboid.getUpperZ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Cuboid changeStringToCuboid(String paramString) {
/*  95 */     String[] arrayOfString1 = paramString.split(";");
/*  96 */     String[] arrayOfString2 = arrayOfString1[0].split(",");
/*  97 */     String[] arrayOfString3 = arrayOfString1[1].split(",");
/*     */     
/*  99 */     String str1 = arrayOfString2[0];
/* 100 */     double d1 = Double.valueOf(arrayOfString2[1]).doubleValue();
/* 101 */     double d2 = Double.valueOf(arrayOfString2[2]).doubleValue();
/* 102 */     double d3 = Double.valueOf(arrayOfString2[3]).doubleValue();
/*     */     
/* 104 */     String str2 = arrayOfString3[0];
/* 105 */     double d4 = Double.valueOf(arrayOfString3[1]).doubleValue();
/* 106 */     double d5 = Double.valueOf(arrayOfString3[2]).doubleValue();
/* 107 */     double d6 = Double.valueOf(arrayOfString3[3]).doubleValue();
/*     */     
/* 109 */     Location location1 = new Location(Bukkit.getWorld(str1), d1, d2, d3);
/*     */     
/* 111 */     Location location2 = new Location(Bukkit.getWorld(str2), d4, d5, d6);
/*     */     
/* 113 */     return new Cuboid(location1, location2);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\LocationUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */