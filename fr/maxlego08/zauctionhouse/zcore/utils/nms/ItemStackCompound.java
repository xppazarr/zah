/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.nms;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ItemStackUtils;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemStackCompound
/*     */ {
/*     */   public static ItemStackCompound itemStackCompound;
/*     */   private final EnumReflectionCompound reflection;
/*     */   
/*     */   static {
/*  15 */     NmsVersion nmsVersion = NmsVersion.nmsVersion;
/*  16 */     if (nmsVersion == NmsVersion.V_1_18_2)
/*  17 */     { itemStackCompound = new ItemStackCompound(EnumReflectionCompound.V1_18_2); }
/*  18 */     else if (nmsVersion.getVersion() >= 1200)
/*  19 */     { itemStackCompound = new ItemStackCompound(EnumReflectionCompound.V1_12); }
/*  20 */     else if (nmsVersion.getVersion() >= 1190)
/*  21 */     { itemStackCompound = new ItemStackCompound(EnumReflectionCompound.V1_19); }
/*  22 */     else if (nmsVersion.getVersion() >= 1170)
/*  23 */     { itemStackCompound = new ItemStackCompound(EnumReflectionCompound.V1_17); }
/*  24 */     else { itemStackCompound = new ItemStackCompound(EnumReflectionCompound.V1_8_8); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStackCompound(EnumReflectionCompound paramEnumReflectionCompound) {
/*  36 */     this.reflection = paramEnumReflectionCompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getCompound(ItemStack paramItemStack) {
/*  47 */     if (paramItemStack == null) return null; 
/*  48 */     Object object1 = ItemStackUtils.EnumReflectionItemStack.CRAFTITEMSTACK.getClassz().getMethod("asNMSCopy", new Class[] { ItemStack.class }).invoke(null, new Object[] { paramItemStack });
/*  49 */     Object object2 = object1.getClass().getMethod(this.reflection.getMethodGetTag(), new Class[0]).invoke(object1, new Object[0]);
/*  50 */     if (object2 != null) {
/*  51 */       return object2;
/*     */     }
/*  53 */     return ItemStackUtils.EnumReflectionItemStack.NBTTAGCOMPOUND.getClassz().newInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack applyCompound(ItemStack paramItemStack, Object paramObject) {
/*  66 */     Object object = ItemStackUtils.EnumReflectionItemStack.CRAFTITEMSTACK.getClassz().getMethod("asNMSCopy", new Class[] { ItemStack.class }).invoke(null, new Object[] { paramItemStack });
/*  67 */     object.getClass().getMethod(this.reflection.getMethodSetTag(), new Class[] { ItemStackUtils.EnumReflectionItemStack.NBTTAGCOMPOUND.getClassz() }).invoke(object, new Object[] { paramObject });
/*  68 */     return (ItemStack)ItemStackUtils.EnumReflectionItemStack.CRAFTITEMSTACK.getClassz().getMethod("asBukkitCopy", new Class[] { ItemStackUtils.EnumReflectionItemStack.ITEMSTACK.getClassz() }).invoke(null, new Object[] { object });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack setString(ItemStack paramItemStack, String paramString1, String paramString2) {
/*     */     try {
/*  81 */       Object object = getCompound(paramItemStack);
/*  82 */       if (object == null) return null; 
/*  83 */       object.getClass().getMethod(this.reflection.getMethodSetString(), new Class[] { String.class, String.class }).invoke(object, new Object[] { paramString1, paramString2 });
/*  84 */       return applyCompound(paramItemStack, object);
/*  85 */     } catch (Exception exception) {
/*  86 */       exception.printStackTrace();
/*     */       
/*  88 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(ItemStack paramItemStack, String paramString) {
/*     */     try {
/* 101 */       Object object = getCompound(paramItemStack);
/* 102 */       if (object == null) return null; 
/* 103 */       return (String)object.getClass().getMethod(this.reflection.getMethodGetString(), new Class[] { String.class }).invoke(object, new Object[] { paramString });
/* 104 */     } catch (Exception exception) {
/* 105 */       exception.printStackTrace();
/*     */       
/* 107 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDouble(ItemStack paramItemStack, String paramString) {
/*     */     try {
/* 121 */       Object object = getCompound(paramItemStack);
/* 122 */       if (object == null) return 0.0D; 
/* 123 */       return ((Double)object.getClass().getMethod(this.reflection.getMethodGetDouble(), new Class[] { String.class }).invoke(object, new Object[] { paramString })).doubleValue();
/* 124 */     } catch (Exception exception) {
/* 125 */       exception.printStackTrace();
/*     */       
/* 127 */       return 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLong(ItemStack paramItemStack, String paramString) {
/*     */     try {
/* 140 */       Object object = getCompound(paramItemStack);
/* 141 */       if (object == null) return 0L; 
/* 142 */       return ((Long)object.getClass().getMethod(this.reflection.getMethodGetLong(), new Class[] { String.class }).invoke(object, new Object[] { paramString })).longValue();
/* 143 */     } catch (Exception exception) {
/* 144 */       exception.printStackTrace();
/*     */       
/* 146 */       return 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInt(ItemStack paramItemStack, String paramString) {
/*     */     try {
/* 158 */       Object object = getCompound(paramItemStack);
/* 159 */       if (object == null) return 0; 
/* 160 */       return ((Integer)object.getClass().getMethod(this.reflection.getMethodGetInt(), new Class[] { String.class }).invoke(object, new Object[] { paramString })).intValue();
/* 161 */     } catch (Exception exception) {
/* 162 */       exception.printStackTrace();
/*     */       
/* 164 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getFloat(ItemStack paramItemStack, String paramString) {
/*     */     try {
/* 177 */       Object object = getCompound(paramItemStack);
/* 178 */       return ((Float)object.getClass().getMethod(this.reflection.getMethodGetFloat(), new Class[] { String.class }).invoke(object, new Object[] { paramString })).floatValue();
/* 179 */     } catch (Exception exception) {
/* 180 */       exception.printStackTrace();
/*     */       
/* 182 */       return 0.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBoolean(ItemStack paramItemStack, String paramString) {
/*     */     try {
/* 195 */       Object object = getCompound(paramItemStack);
/* 196 */       return ((Boolean)object.getClass().getMethod(this.reflection.getMethodGetBoolean(), new Class[] { String.class }).invoke(object, new Object[] { paramString })).booleanValue();
/* 197 */     } catch (Exception exception) {
/* 198 */       exception.printStackTrace();
/*     */       
/* 200 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack setInt(ItemStack paramItemStack, String paramString, int paramInt) {
/*     */     try {
/* 214 */       Object object = getCompound(paramItemStack);
/* 215 */       object.getClass().getMethod(this.reflection.getMethodSetInt(), new Class[] { String.class, int.class }).invoke(object, new Object[] { paramString, Integer.valueOf(paramInt) });
/* 216 */       return applyCompound(paramItemStack, object);
/* 217 */     } catch (Exception exception) {
/* 218 */       exception.printStackTrace();
/*     */       
/* 220 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack setLong(ItemStack paramItemStack, String paramString, long paramLong) {
/*     */     try {
/* 234 */       Object object = getCompound(paramItemStack);
/* 235 */       object.getClass().getMethod(this.reflection.getMethodSetLong(), new Class[] { String.class, long.class }).invoke(object, new Object[] { paramString, Long.valueOf(paramLong) });
/* 236 */       return applyCompound(paramItemStack, object);
/* 237 */     } catch (Exception exception) {
/* 238 */       exception.printStackTrace();
/*     */       
/* 240 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack setFloat(ItemStack paramItemStack, String paramString, float paramFloat) {
/*     */     try {
/* 254 */       Object object = getCompound(paramItemStack);
/* 255 */       object.getClass().getMethod(this.reflection.getMethodSetFloat(), new Class[] { String.class, float.class }).invoke(object, new Object[] { paramString, Float.valueOf(paramFloat) });
/* 256 */       return applyCompound(paramItemStack, object);
/* 257 */     } catch (Exception exception) {
/* 258 */       exception.printStackTrace();
/*     */       
/* 260 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack setBoolean(ItemStack paramItemStack, String paramString, boolean paramBoolean) {
/*     */     try {
/* 275 */       Object object = getCompound(paramItemStack);
/* 276 */       object.getClass().getMethod(this.reflection.getMethodSetBoolean(), new Class[] { String.class, boolean.class }).invoke(object, new Object[] { paramString, Boolean.valueOf(paramBoolean) });
/* 277 */       return applyCompound(paramItemStack, object);
/* 278 */     } catch (Exception exception) {
/* 279 */       exception.printStackTrace();
/*     */       
/* 281 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack setDouble(ItemStack paramItemStack, String paramString, double paramDouble) {
/*     */     try {
/* 295 */       Object object = getCompound(paramItemStack);
/* 296 */       object.getClass().getMethod(this.reflection.getMethodSetDouble(), new Class[] { String.class, double.class }).invoke(object, new Object[] { paramString, Double.valueOf(paramDouble) });
/* 297 */       return applyCompound(paramItemStack, object);
/* 298 */     } catch (Exception exception) {
/* 299 */       exception.printStackTrace();
/*     */       
/* 301 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isKey(ItemStack paramItemStack, String paramString) {
/*     */     try {
/* 314 */       Object object = getCompound(paramItemStack);
/* 315 */       if (object == null) return false; 
/* 316 */       return ((Boolean)object.getClass().getMethod(this.reflection.getMethodHasKey(), new Class[] { String.class }).invoke(object, new Object[] { paramString })).booleanValue();
/* 317 */     } catch (Exception exception) {
/*     */       
/* 319 */       return false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\nms\ItemStackCompound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */