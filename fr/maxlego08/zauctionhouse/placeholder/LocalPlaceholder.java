/*     */ package fr.maxlego08.zauctionhouse.placeholder;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.stream.Collectors;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocalPlaceholder
/*     */ {
/*     */   private static volatile LocalPlaceholder instance;
/*  19 */   private final Pattern pattern = Pattern.compile("[%]([^%]+)[%]");
/*  20 */   private final List<AutoPlaceholder> autoPlaceholders = new ArrayList<>();
/*     */   private ZAuctionPlugin plugin;
/*  22 */   private String prefix = "template";
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
/*     */   
/*     */   public static LocalPlaceholder getInstance() {
/*  35 */     if (instance == null) {
/*  36 */       synchronized (LocalPlaceholder.class) {
/*  37 */         if (instance == null) {
/*  38 */           instance = new LocalPlaceholder();
/*     */         }
/*     */       } 
/*     */     }
/*  42 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public String setPlaceholders(Player paramPlayer, String paramString) {
/*  47 */     if (paramString == null || !paramString.contains("%")) {
/*  48 */       return paramString;
/*     */     }
/*     */     
/*  51 */     String str = this.prefix + "_";
/*     */     
/*  53 */     Matcher matcher = this.pattern.matcher(paramString);
/*  54 */     while (matcher.find()) {
/*  55 */       String str1 = matcher.group(0);
/*  56 */       String str2 = matcher.group(1).replace(str, "");
/*  57 */       String str3 = onRequest(paramPlayer, str2);
/*  58 */       if (str3 != null) {
/*  59 */         paramString = paramString.replace(str1, str3);
/*     */       }
/*     */     } 
/*     */     
/*  63 */     return paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> setPlaceholders(Player paramPlayer, List<String> paramList) {
/*  72 */     return (paramList == null) ? null : (List<String>)paramList.stream().map(paramString -> paramString = setPlaceholders(paramPlayer, paramString)).collect(Collectors.toList());
/*     */   }
/*     */ 
/*     */   
/*     */   public String onRequest(Player paramPlayer, String paramString) {
/*  77 */     Optional<AutoPlaceholder> optional = this.autoPlaceholders.stream().filter(paramAutoPlaceholder -> paramAutoPlaceholder.startsWith(paramString)).findFirst();
/*  78 */     if (optional.isPresent()) {
/*     */       
/*  80 */       AutoPlaceholder autoPlaceholder = optional.get();
/*  81 */       String str = paramString.replace(autoPlaceholder.getStartWith(), "");
/*  82 */       return autoPlaceholder.accept(paramPlayer, str);
/*     */     } 
/*     */     
/*  85 */     return null;
/*     */   }
/*     */   
/*     */   public void register(String paramString, ReturnBiConsumer<Player, String, String> paramReturnBiConsumer) {
/*  89 */     this.autoPlaceholders.add(new AutoPlaceholder(paramString, paramReturnBiConsumer));
/*     */   }
/*     */   
/*     */   public void register(String paramString, ReturnConsumer<Player, String> paramReturnConsumer) {
/*  93 */     this.autoPlaceholders.add(new AutoPlaceholder(paramString, paramReturnConsumer));
/*     */   }
/*     */   
/*     */   public String getPrefix() {
/*  97 */     return this.prefix;
/*     */   }
/*     */   
/*     */   public void setPrefix(String paramString) {
/* 101 */     this.prefix = paramString;
/*     */   }
/*     */   
/*     */   public ZAuctionPlugin getPlugin() {
/* 105 */     return this.plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlugin(ZAuctionPlugin paramZAuctionPlugin) {
/* 114 */     this.plugin = paramZAuctionPlugin;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\placeholder\LocalPlaceholder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */