/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.sign.packet;
/*    */ 
/*    */ import com.comphenix.protocol.PacketType;
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.wrappers.BlockPosition;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WrapperPlayClientUpdateSign
/*    */   extends AbstractPacket
/*    */ {
/* 26 */   public static final PacketType TYPE = PacketType.Play.Client.UPDATE_SIGN;
/*    */   
/*    */   public WrapperPlayClientUpdateSign() {
/* 29 */     super(new PacketContainer(TYPE), TYPE);
/* 30 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayClientUpdateSign(PacketContainer paramPacketContainer) {
/* 34 */     super(paramPacketContainer, TYPE);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockPosition getLocation() {
/* 45 */     return (BlockPosition)this.handle.getBlockPositionModifier().read(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLocation(BlockPosition paramBlockPosition) {
/* 54 */     this.handle.getBlockPositionModifier().write(0, paramBlockPosition);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getLines() {
/* 63 */     return (String[])this.handle.getStringArrays().read(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLines(String[] paramArrayOfString) {
/* 72 */     if (paramArrayOfString == null)
/* 73 */       throw new IllegalArgumentException("value cannot be null!"); 
/* 74 */     if (paramArrayOfString.length != 4) {
/* 75 */       throw new IllegalArgumentException("value must have 4 elements!");
/*    */     }
/* 77 */     this.handle.getStringArrays().write(0, paramArrayOfString);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sign\packet\WrapperPlayClientUpdateSign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */