/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.minecraft;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.XReflection;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.classes.ClassHandle;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ public final class MinecraftConnection
/*     */ {
/*  45 */   public static final MinecraftClassHandle ServerPlayer = XReflection.ofMinecraft()
/*  46 */     .inPackage(MinecraftPackage.NMS, "server.level")
/*  47 */     .map(MinecraftMapping.MOJANG, "ServerPlayer")
/*  48 */     .map(MinecraftMapping.SPIGOT, "EntityPlayer");
/*  49 */   public static final MinecraftClassHandle CraftPlayer = XReflection.ofMinecraft()
/*  50 */     .inPackage(MinecraftPackage.CB, "entity")
/*  51 */     .named(new String[] { "CraftPlayer" });
/*  52 */   public static final MinecraftClassHandle ServerPlayerConnection = XReflection.ofMinecraft()
/*  53 */     .inPackage(MinecraftPackage.NMS, "server.network")
/*  54 */     .map(MinecraftMapping.MOJANG, "ServerPlayerConnection")
/*  55 */     .map(MinecraftMapping.SPIGOT, "PlayerConnection");
/*  56 */   public static final MinecraftClassHandle ServerGamePacketListenerImpl = XReflection.ofMinecraft()
/*  57 */     .inPackage(MinecraftPackage.NMS, "server.network")
/*  58 */     .map(MinecraftMapping.MOJANG, "ServerGamePacketListenerImpl")
/*  59 */     .map(MinecraftMapping.SPIGOT, "PlayerConnection");
/*  60 */   public static final MinecraftClassHandle Packet = XReflection.ofMinecraft()
/*  61 */     .inPackage(MinecraftPackage.NMS, "network.protocol")
/*  62 */     .map(MinecraftMapping.SPIGOT, "Packet");
/*     */   
/*  64 */   private static final MethodHandle PLAYER_CONNECTION = (MethodHandle)ServerPlayer
/*     */     
/*  66 */     .field().getter()
/*  67 */     .returns((ClassHandle)ServerGamePacketListenerImpl)
/*  68 */     .map(MinecraftMapping.MOJANG, "connection")
/*  69 */     .map(MinecraftMapping.OBFUSCATED, (String)XReflection.v(21, 2, "f").v(20, "c").v(17, "b").orElse("playerConnection"))
/*  70 */     .unreflect();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   private static final MethodHandle GET_HANDLE = (MethodHandle)CraftPlayer
/*  79 */     .method()
/*  80 */     .named(new String[] { "getHandle"
/*  81 */       }).returns((ClassHandle)ServerPlayer)
/*  82 */     .unreflect();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   private static final MethodHandle SEND_PACKET = (MethodHandle)ServerPlayerConnection
/*  88 */     .method()
/*  89 */     .returns(void.class)
/*  90 */     .parameters(new ClassHandle[] { (ClassHandle)Packet
/*  91 */       }).map(MinecraftMapping.MOJANG, "send")
/*  92 */     .map(MinecraftMapping.OBFUSCATED, (String)XReflection.v(20, 2, "b").v(18, "a").orElse("sendPacket"))
/*  93 */     .unreflect();
/*     */   
/*     */   @NotNull
/*     */   public static Object getHandle(@NotNull Player paramPlayer) {
/*  97 */     Objects.requireNonNull(paramPlayer, "Cannot get handle of null player");
/*     */     try {
/*  99 */       return GET_HANDLE.invoke(paramPlayer);
/* 100 */     } catch (Throwable throwable) {
/* 101 */       throw XReflection.throwCheckedException(throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Object getConnection(@NotNull Player paramPlayer) {
/* 110 */     Objects.requireNonNull(paramPlayer, "Cannot get connection of null player");
/*     */     try {
/* 112 */       Object object = GET_HANDLE.invoke(paramPlayer);
/* 113 */       return PLAYER_CONNECTION.invoke(object);
/* 114 */     } catch (Throwable throwable) {
/* 115 */       throw XReflection.throwCheckedException(throwable);
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
/*     */   @NotNull
/*     */   public static void sendPacket(@NotNull Player paramPlayer, @NotNull Object... paramVarArgs) {
/* 129 */     Objects.requireNonNull(paramPlayer, () -> "Can't send packet to null player: " + Arrays.toString(paramArrayOfObject));
/* 130 */     Objects.requireNonNull(paramVarArgs, () -> "Can't send null packets to player: " + paramPlayer);
/*     */     try {
/* 132 */       Object object1 = GET_HANDLE.invoke(paramPlayer);
/* 133 */       Object object2 = PLAYER_CONNECTION.invoke(object1);
/*     */ 
/*     */       
/* 136 */       if (object2 != null) {
/* 137 */         for (Object object : paramVarArgs) {
/* 138 */           Objects.requireNonNull(object, "Null packet detected between packets array");
/* 139 */           SEND_PACKET.invoke(object2, object);
/*     */         } 
/*     */       }
/* 142 */     } catch (Throwable throwable) {
/* 143 */       throw new IllegalStateException("Failed to send packet to " + paramPlayer + ": " + Arrays.toString(paramVarArgs), throwable);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\minecraft\MinecraftConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */