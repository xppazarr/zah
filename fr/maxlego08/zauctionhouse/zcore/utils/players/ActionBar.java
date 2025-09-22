/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.players;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.nms.NmsVersion;
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import net.md_5.bungee.api.ChatMessageType;
/*    */ import net.md_5.bungee.api.chat.BaseComponent;
/*    */ import net.md_5.bungee.api.chat.TextComponent;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class ActionBar
/*    */ {
/*    */   private static Class<?> craftPlayerClass;
/*    */   private static Class<?> packetClass;
/*    */   private static Method getHandleMethod;
/*    */   private static Field playerConnectionField;
/*    */   private static Constructor<?> constructorPacket;
/*    */   private static Constructor<?> constructorComponent;
/*    */   
/*    */   static {
/* 23 */     String str = Bukkit.getServer().getClass().getPackage().getName();
/* 24 */     str = str.substring(str.lastIndexOf(".") + 1);
/*    */     
/*    */     try {
/* 27 */       craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + str + ".entity.CraftPlayer");
/* 28 */       Class<?> clazz1 = Class.forName("net.minecraft.server." + str + ".PacketPlayOutChat");
/* 29 */       packetClass = Class.forName("net.minecraft.server." + str + ".Packet");
/* 30 */       Class<?> clazz2 = Class.forName("net.minecraft.server." + str + ".IChatBaseComponent");
/*    */       
/* 32 */       getHandleMethod = craftPlayerClass.getMethod("getHandle", new Class[0]);
/* 33 */       playerConnectionField = getHandleMethod.getReturnType().getField("playerConnection");
/*    */       
/* 35 */       Class<?> clazz3 = Class.forName("net.minecraft.server." + str + ".ChatComponentText");
/*    */       
/* 37 */       constructorComponent = clazz3.getConstructor(new Class[] { String.class });
/* 38 */       constructorPacket = clazz1.getConstructor(new Class[] { clazz2, byte.class });
/* 39 */     } catch (Exception exception) {}
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void sendActionBar(Player paramPlayer, String paramString) {
/* 45 */     if (!paramPlayer.isOnline()) {
/*    */       return;
/*    */     }
/*    */     
/* 49 */     if (NmsVersion.nmsVersion.getVersion() >= NmsVersion.V_1_10.getVersion()) {
/* 50 */       paramPlayer.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(TextComponent.fromLegacyText(paramString)));
/*    */       
/*    */       return;
/*    */     } 
/*    */     try {
/* 55 */       Object object1 = craftPlayerClass.cast(paramPlayer);
/* 56 */       Object object2 = constructorComponent.newInstance(new Object[] { paramString });
/* 57 */       Object object3 = constructorPacket.newInstance(new Object[] { object2, Byte.valueOf((byte)2) });
/* 58 */       Object object4 = getHandleMethod.invoke(object1, new Object[0]);
/* 59 */       object2 = playerConnectionField.get(object4);
/* 60 */       Method method = object2.getClass().getDeclaredMethod("sendPacket", new Class[] { packetClass });
/* 61 */       method.invoke(object2, new Object[] { object3 });
/* 62 */     } catch (Exception exception) {
/* 63 */       exception.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\players\ActionBar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */