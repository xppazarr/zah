/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.sign.packet;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.ProtocolLibrary;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.google.common.base.Objects;
/*     */ import org.bukkit.entity.Player;
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
/*     */ public abstract class AbstractPacket
/*     */ {
/*     */   protected PacketContainer handle;
/*     */   
/*     */   protected AbstractPacket(PacketContainer paramPacketContainer, PacketType paramPacketType) {
/*  39 */     if (paramPacketContainer == null) throw new IllegalArgumentException("Packet handle cannot be NULL."); 
/*  40 */     if (!Objects.equal(paramPacketContainer.getType(), paramPacketType)) {
/*  41 */       throw new IllegalArgumentException(paramPacketContainer.getHandle() + " is not a packet of type " + paramPacketType);
/*     */     }
/*  43 */     this.handle = paramPacketContainer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PacketContainer getHandle() {
/*  52 */     return this.handle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPacket(Player paramPlayer) {
/*     */     try {
/*  63 */       ProtocolLibrary.getProtocolManager().sendServerPacket(paramPlayer, getHandle());
/*  64 */     } catch (Exception exception) {
/*  65 */       throw new RuntimeException("Cannot send packet.", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcastPacket() {
/*  73 */     ProtocolLibrary.getProtocolManager().broadcastServerPacket(getHandle());
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
/*     */   @Deprecated
/*     */   public void recievePacket(Player paramPlayer) {
/*     */     try {
/*  87 */       ProtocolLibrary.getProtocolManager().receiveClientPacket(paramPlayer, getHandle());
/*  88 */     } catch (Exception exception) {
/*  89 */       throw new RuntimeException("Cannot recieve packet.", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void receivePacket(Player paramPlayer) {
/*     */     try {
/* 101 */       ProtocolLibrary.getProtocolManager().receiveClientPacket(paramPlayer, getHandle());
/* 102 */     } catch (Exception exception) {
/* 103 */       throw new RuntimeException("Cannot receive packet.", exception);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sign\packet\AbstractPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */