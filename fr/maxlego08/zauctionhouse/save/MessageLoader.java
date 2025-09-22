/*     */ package fr.maxlego08.zauctionhouse.save;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.MessageType;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.Savable;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Persist;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.loader.ItemStackLoader;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.nms.NmsVersion;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.yaml.YamlUtils;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MessageLoader
/*     */   extends YamlUtils
/*     */   implements Savable
/*     */ {
/*  31 */   private final List<Message> loadedMessages = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageLoader(ZAuctionPlugin paramZAuctionPlugin) {
/*  39 */     super(paramZAuctionPlugin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(Persist paramPersist) {
/*  50 */     if (paramPersist != null)
/*     */       return; 
/*  52 */     File file = new File(this.plugin.getDataFolder(), "messages.yml");
/*  53 */     if (!file.exists()) {
/*     */       try {
/*  55 */         file.createNewFile();
/*  56 */       } catch (IOException iOException) {
/*  57 */         iOException.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  61 */     ItemStackLoader itemStackLoader = new ItemStackLoader();
/*  62 */     YamlConfiguration yamlConfiguration = getConfig(file);
/*     */     
/*  64 */     for (Message message : Message.values()) {
/*     */       
/*  66 */       if (message.isUse()) {
/*     */         
/*  68 */         String str = "messages." + message.name().toLowerCase().replace("_", ".");
/*     */         
/*  70 */         if (yamlConfiguration.getConfigurationSection(str) == null) {
/*     */           
/*  72 */           if (message.getType() != MessageType.TCHAT) {
/*  73 */             yamlConfiguration.set(str + ".type", message.getType().name());
/*     */           }
/*  75 */           if (message.getType().equals(MessageType.TCHAT) || message.getType().equals(MessageType.ACTION) || message.getType().equals(MessageType.CENTER)) {
/*     */             
/*  77 */             if (message.isMessage()) {
/*     */               
/*  79 */               List list = (NmsVersion.getCurrentVersion().isHexVersion() && message.getMessagesNewVersion() != null) ? colorReverse(message.getMessagesNewVersion()) : colorReverse(message.getMessages());
/*  80 */               yamlConfiguration.set(str + ".messages", list);
/*     */             } else {
/*     */               
/*  83 */               String str1 = colorReverse((NmsVersion.getCurrentVersion().isHexVersion() && message.getMessageNewVersion() != null) ? message.getMessageNewVersion() : message.getMessage());
/*  84 */               yamlConfiguration.set(str + ".message", str1);
/*     */             }
/*     */           
/*  87 */           } else if (message.getType().equals(MessageType.ITEMSTACK)) {
/*     */             
/*  89 */             itemStackLoader.save(message.getItemStack(), yamlConfiguration, str + ".itemstack.");
/*     */           }
/*  91 */           else if (message.getType().equals(MessageType.TITLE)) {
/*     */             
/*  93 */             yamlConfiguration.set(str + ".title", colorReverse(message.getTitle()));
/*  94 */             yamlConfiguration.set(str + ".subtitle", colorReverse(message.getSubTitle()));
/*  95 */             yamlConfiguration.set(str + ".fadeInTime", Integer.valueOf(message.getStart()));
/*  96 */             yamlConfiguration.set(str + ".showTime", Integer.valueOf(message.getTime()));
/*  97 */             yamlConfiguration.set(str + ".fadeOutTime", Integer.valueOf(message.getEnd()));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }  try {
/* 102 */       yamlConfiguration.save(file);
/* 103 */     } catch (IOException iOException) {
/* 104 */       iOException.printStackTrace();
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
/*     */   public void load(Persist paramPersist) {
/* 116 */     File file = new File(this.plugin.getDataFolder(), "messages.yml");
/* 117 */     if (!file.exists()) {
/* 118 */       save((Persist)null);
/* 119 */       load(paramPersist);
/*     */       
/*     */       return;
/*     */     } 
/* 123 */     YamlConfiguration yamlConfiguration = getConfig(file);
/*     */     
/* 125 */     if (!yamlConfiguration.contains("messages")) {
/* 126 */       save((Persist)null);
/*     */     }
/*     */     
/* 129 */     this.loadedMessages.clear();
/*     */     
/* 131 */     for (String str : yamlConfiguration.getConfigurationSection("messages.").getKeys(false)) {
/* 132 */       loadMessage(yamlConfiguration, "messages." + str);
/*     */     }
/*     */     
/* 135 */     boolean bool = false;
/* 136 */     for (Message message : Message.values()) {
/*     */       
/* 138 */       if (!this.loadedMessages.contains(message) && message.isUse()) {
/* 139 */         bool = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 145 */     if (bool) {
/* 146 */       Logger.info("Save the message file, add new settings");
/* 147 */       save((Persist)null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadMessage(YamlConfiguration paramYamlConfiguration, String paramString) {
/*     */     try {
/*     */       String str2, str3;
/*     */       int i, j, k;
/*     */       HashMap<Object, Object> hashMap;
/* 160 */       MessageType messageType = MessageType.valueOf(paramYamlConfiguration.getString(paramString + ".type", "TCHAT").toUpperCase());
/* 161 */       String str1 = paramString.substring("messages.".length());
/* 162 */       Message message = Message.valueOf(str1.toUpperCase().replace(".", "_"));
/* 163 */       message.setType(messageType);
/*     */       
/* 165 */       this.loadedMessages.add(message);
/*     */       
/* 167 */       switch (messageType) {
/*     */         case TCHAT_AND_ACTION:
/*     */         case WITHOUT_PREFIX:
/*     */         case ACTION:
/* 171 */           str2 = paramYamlConfiguration.getString(paramString + ".message");
/* 172 */           message.setMessage(str2);
/*     */           break;
/*     */         
/*     */         case ITEMSTACK:
/*     */           try {
/* 177 */             ItemStackLoader itemStackLoader = new ItemStackLoader();
/* 178 */             ItemStack itemStack = (ItemStack)itemStackLoader.load(paramYamlConfiguration, paramString + ".itemstack.", new Object[0]);
/*     */             
/* 180 */             if (AuctionConfiguration.USE_ZMENU_INVENTORY) {
/* 181 */               itemStack = this.plugin.getzMenuHandler().loadItemStack(paramYamlConfiguration, paramString + ".itemstack.", itemStack);
/*     */             }
/*     */             
/* 184 */             message.setItemStack(itemStack);
/* 185 */           } catch (Exception exception) {
/* 186 */             exception.printStackTrace();
/*     */           } 
/*     */           break;
/*     */         
/*     */         case CENTER:
/*     */         case TCHAT:
/* 192 */           if (paramYamlConfiguration.contains(paramString + ".messages")) {
/* 193 */             List list = paramYamlConfiguration.getStringList(paramString + ".messages");
/* 194 */             message.setMessages(list);
/* 195 */             message.setMessage(null); break;
/*     */           } 
/* 197 */           str2 = paramYamlConfiguration.getString(paramString + ".message");
/* 198 */           message.setMessage(str2);
/* 199 */           message.setMessages(new ArrayList());
/*     */           break;
/*     */ 
/*     */         
/*     */         case TITLE:
/* 204 */           str2 = paramYamlConfiguration.getString(paramString + ".title");
/* 205 */           str3 = paramYamlConfiguration.getString(paramString + ".subtitle");
/* 206 */           i = paramYamlConfiguration.getInt(paramString + ".fadeInTime");
/* 207 */           j = paramYamlConfiguration.getInt(paramString + ".showTime");
/* 208 */           k = paramYamlConfiguration.getInt(paramString + ".fadeOutTime");
/* 209 */           hashMap = new HashMap<>();
/* 210 */           hashMap.put("title", str2);
/* 211 */           hashMap.put("subtitle", str3);
/* 212 */           hashMap.put("start", Integer.valueOf(i));
/* 213 */           hashMap.put("time", Integer.valueOf(j));
/* 214 */           hashMap.put("end", Integer.valueOf(k));
/* 215 */           hashMap.put("isUse", Boolean.valueOf(true));
/* 216 */           message.setTitles(hashMap);
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 223 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 226 */     if (paramYamlConfiguration.isConfigurationSection(paramString + "."))
/* 227 */       for (String str : paramYamlConfiguration.getConfigurationSection(paramString + ".").getKeys(false))
/* 228 */         loadMessage(paramYamlConfiguration, paramString + "." + str);  
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\save\MessageLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */