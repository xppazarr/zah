/*     */ package fr.maxlego08.zauctionhouse.zcore.utils;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.MessageType;
/*     */ import fr.maxlego08.zauctionhouse.api.messages.IMessage;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.zcore.ZPlugin;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.nms.NmsVersion;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.players.ActionBar;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.List;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MessageUtils
/*     */   extends LocationUtils
/*     */ {
/*     */   public static String removeColorCodes(String paramString) {
/*  28 */     paramString = paramString.replaceAll("#[0-9a-fA-F]{6}", "");
/*  29 */     paramString = paramString.replaceAll("§[0-9a-fA-Fk-oK-OrR]", "");
/*  30 */     return paramString.replaceAll("&[0-9a-fA-Fk-oK-OrR]", "");
/*     */   }
/*     */   
/*     */   protected void messageWO(CommandSender paramCommandSender, Message paramMessage, Object... paramVarArgs) {
/*  34 */     paramCommandSender.sendMessage(color(getMessage(paramMessage, paramVarArgs)));
/*     */   }
/*     */   
/*     */   protected void messageWO(CommandSender paramCommandSender, String paramString, Object... paramVarArgs) {
/*  38 */     paramCommandSender.sendMessage(color(getMessage(paramString, paramVarArgs)));
/*     */   }
/*     */   
/*     */   protected void message(CommandSender paramCommandSender, String paramString, Object... paramVarArgs) {
/*  42 */     paramCommandSender.sendMessage(color(Message.PREFIX.msg() + getMessage(paramString, paramVarArgs)));
/*     */   }
/*     */   
/*     */   public void message(CommandSender paramCommandSender, Message paramMessage, Object... paramVarArgs) {
/*  46 */     message(paramCommandSender, paramMessage.getIMessage(), paramVarArgs);
/*     */   }
/*     */   
/*     */   protected void actionMessage(Player paramPlayer, IMessage paramIMessage, Object... paramVarArgs) {
/*  50 */     ActionBar.sendActionBar(paramPlayer, color(getMessage(paramIMessage, paramVarArgs)));
/*     */   }
/*     */   
/*     */   protected void sendTchatMessage(Player paramPlayer, IMessage paramIMessage, Object... paramVarArgs) {
/*  54 */     if (paramIMessage.getMessages().size() > 0) {
/*  55 */       paramIMessage.getMessages().forEach(paramString -> message((CommandSender)paramPlayer, color(papi(getMessage(paramString, paramArrayOfObject), paramPlayer))));
/*     */     } else {
/*  57 */       message((CommandSender)paramPlayer, color(papi(((paramIMessage.getType() == MessageType.WITHOUT_PREFIX) ? "" : Message.PREFIX.msg()) + getMessage(paramIMessage, paramVarArgs), paramPlayer)));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void message(CommandSender paramCommandSender, String paramString) {
/*  62 */     paramCommandSender.sendMessage(color(paramString));
/*     */   }
/*     */   
/*     */   protected String getMessage(IMessage paramIMessage, Object... paramVarArgs) {
/*  66 */     return getMessage(paramIMessage.getMessage(), paramVarArgs);
/*     */   }
/*     */ 
/*     */   
/*     */   public void message(CommandSender paramCommandSender, IMessage paramIMessage, Object... paramVarArgs) {
/*  71 */     if (paramCommandSender instanceof org.bukkit.command.ConsoleCommandSender) {
/*  72 */       if (paramIMessage.getMessages().size() > 0)
/*  73 */       { paramIMessage.getMessages().forEach(paramString -> message(paramCommandSender, getMessage(paramString, paramArrayOfObject))); }
/*  74 */       else { message(paramCommandSender, Message.PREFIX.msg() + getMessage(paramIMessage, paramVarArgs)); }
/*     */     
/*     */     } else {
/*  77 */       String str1, str2; int i, j, k; Player player = (Player)paramCommandSender;
/*     */       
/*  79 */       if (AuctionConfiguration.USE_ZMENU_INVENTORY) {
/*  80 */         ZAuctionPlugin zAuctionPlugin = (ZAuctionPlugin)ZPlugin.z();
/*  81 */         zAuctionPlugin.getzMenuHandler().message((CommandSender)player, paramIMessage, paramVarArgs);
/*     */         
/*     */         return;
/*     */       } 
/*  85 */       switch (paramIMessage.getType()) {
/*     */         case CENTER:
/*  87 */           if (paramIMessage.getMessages().size() > 0) {
/*  88 */             paramIMessage.getMessages().forEach(paramString -> paramCommandSender.sendMessage(getCenteredMessage(papi(getMessage(paramString, paramArrayOfObject), paramPlayer)))); break;
/*     */           } 
/*  90 */           paramCommandSender.sendMessage(getCenteredMessage(papi(getMessage(paramIMessage, paramVarArgs), player)));
/*     */           break;
/*     */ 
/*     */         
/*     */         case ACTION:
/*  95 */           actionMessage(player, paramIMessage, paramVarArgs);
/*     */           break;
/*     */         case TCHAT_AND_ACTION:
/*  98 */           actionMessage(player, paramIMessage, paramVarArgs);
/*  99 */           sendTchatMessage(player, paramIMessage, paramVarArgs);
/*     */           break;
/*     */         case TCHAT:
/*     */         case WITHOUT_PREFIX:
/* 103 */           sendTchatMessage(player, paramIMessage, paramVarArgs);
/*     */           break;
/*     */         case TITLE:
/* 106 */           str1 = paramIMessage.getTitle();
/* 107 */           str2 = paramIMessage.getSubTitle();
/* 108 */           i = paramIMessage.getStart();
/* 109 */           j = paramIMessage.getTime();
/* 110 */           k = paramIMessage.getEnd();
/* 111 */           title(player, papi(getMessage(str1, paramVarArgs), player), papi(getMessage(str2, paramVarArgs), player), i, j, k);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void broadcast(Message paramMessage, Object... paramVarArgs) {
/* 121 */     for (Player player : Bukkit.getOnlinePlayers()) {
/* 122 */       message((CommandSender)player, paramMessage, paramVarArgs);
/*     */     }
/* 124 */     message((CommandSender)Bukkit.getConsoleSender(), paramMessage, paramVarArgs);
/*     */   }
/*     */   
/*     */   protected void actionMessage(Player paramPlayer, Message paramMessage, Object... paramVarArgs) {
/* 128 */     ActionBar.sendActionBar(paramPlayer, papi(getMessage(paramMessage, paramVarArgs), paramPlayer));
/*     */   }
/*     */   
/*     */   protected String getMessage(Message paramMessage, Object... paramVarArgs) {
/* 132 */     return getMessage(paramMessage.getMessage(), paramVarArgs);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getMessage(String paramString, Object... paramVarArgs) {
/* 137 */     if (paramVarArgs.length % 2 != 0) {
/* 138 */       System.err.println("From : " + paramString);
/* 139 */       throw new IllegalArgumentException("Number of invalid arguments. Arguments must be in pairs.");
/*     */     } 
/*     */     
/* 142 */     for (byte b = 0; b < paramVarArgs.length; b += 2) {
/*     */       
/* 144 */       if (paramVarArgs[b] == null) {
/* 145 */         System.err.println("zAuctionHouse Error: Keys and replacement values must not be null. Please check your code. (index: " + b + ")");
/* 146 */         System.err.println("From : " + paramString);
/*     */ 
/*     */       
/*     */       }
/* 150 */       else if (paramVarArgs[b + 1] == null) {
/* 151 */         System.err.println("zAuctionHouse Error: Keys and replacement values must not be null. Please check your code. (index: " + (b + 1) + ")");
/* 152 */         System.err.println("From : " + paramString);
/*     */       }
/*     */       else {
/*     */         
/* 156 */         paramString = paramString.replace(paramVarArgs[b].toString(), paramVarArgs[b + 1].toString());
/*     */       } 
/* 158 */     }  return paramString;
/*     */   }
/*     */   
/*     */   protected final Class<?> getNMSClass(String paramString) {
/*     */     try {
/* 163 */       return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + paramString);
/* 164 */     } catch (ClassNotFoundException classNotFoundException) {
/* 165 */       classNotFoundException.printStackTrace();
/*     */       
/* 167 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void title(Player paramPlayer, String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3) {
/* 172 */     if (NmsVersion.nmsVersion.isNewMaterial()) {
/* 173 */       paramPlayer.sendTitle(paramString1, paramString2, paramInt1, paramInt2, paramInt3);
/*     */       
/*     */       return;
/*     */     } 
/*     */     try {
/* 178 */       Object object1 = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\": \"" + paramString1 + "\"}" });
/* 179 */       Constructor<?> constructor1 = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class });
/* 180 */       Object object2 = constructor1.newInstance(new Object[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null), object1, Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) });
/*     */       
/* 182 */       Object object3 = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\": \"" + paramString2 + "\"}" });
/* 183 */       Constructor<?> constructor2 = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class });
/* 184 */       Object object4 = constructor2.newInstance(new Object[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null), object3, Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) });
/*     */       
/* 186 */       sendPacket(paramPlayer, object2);
/* 187 */       sendPacket(paramPlayer, object4);
/* 188 */     } catch (Exception exception) {
/* 189 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void sendPacket(Player paramPlayer, Object paramObject) {
/*     */     try {
/* 199 */       Object object1 = paramPlayer.getClass().getMethod("getHandle", new Class[0]).invoke(paramPlayer, new Object[0]);
/* 200 */       Object object2 = object1.getClass().getField("playerConnection").get(object1);
/* 201 */       object2.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") }).invoke(object2, new Object[] { paramObject });
/* 202 */     } catch (Exception exception) {
/* 203 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getCenteredMessage(String paramString) {
/* 212 */     if (paramString == null || paramString.equals("")) return ""; 
/* 213 */     paramString = ChatColor.translateAlternateColorCodes('&', paramString);
/*     */     
/* 215 */     int i = 0;
/* 216 */     boolean bool1 = false;
/* 217 */     boolean bool2 = false;
/*     */     
/* 219 */     for (char c : paramString.toCharArray()) {
/* 220 */       if (c == '§') {
/* 221 */         bool1 = true;
/* 222 */       } else if (bool1) {
/* 223 */         bool1 = false;
/* 224 */         bool2 = (c == 'l' || c == 'L') ? true : false;
/*     */       } else {
/* 226 */         DefaultFontInfo defaultFontInfo = DefaultFontInfo.getDefaultFontInfo(c);
/* 227 */         i += bool2 ? defaultFontInfo.getBoldLength() : defaultFontInfo.getLength();
/* 228 */         i++;
/*     */       } 
/*     */     } 
/*     */     
/* 232 */     int j = i / 2;
/* 233 */     int k = 154 - j;
/* 234 */     int m = DefaultFontInfo.SPACE.getLength() + 1;
/* 235 */     int n = 0;
/* 236 */     StringBuilder stringBuilder = new StringBuilder();
/* 237 */     while (n < k) {
/* 238 */       stringBuilder.append(" ");
/* 239 */       n += m;
/*     */     } 
/* 241 */     return stringBuilder + paramString;
/*     */   }
/*     */   
/*     */   protected void broadcastCenterMessage(List<String> paramList) {
/* 245 */     paramList.stream().map(paramString -> paramString = getCenteredMessage(paramString)).forEach(paramString -> {
/*     */           for (Player player : Bukkit.getOnlinePlayers()) {
/*     */             messageWO((CommandSender)player, paramString, new Object[0]);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   protected void broadcastAction(String paramString) {
/* 253 */     for (Player player : Bukkit.getOnlinePlayers())
/* 254 */       ActionBar.sendActionBar(player, papi(paramString, player)); 
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\MessageUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */