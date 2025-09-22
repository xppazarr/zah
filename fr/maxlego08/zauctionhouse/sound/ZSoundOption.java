/*    */ package fr.maxlego08.zauctionhouse.sound;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.sound.SoundOption;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.XSound;
/*    */ import org.bukkit.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZSoundOption
/*    */   implements SoundOption
/*    */ {
/*    */   private final XSound sound;
/*    */   private final float pitch;
/*    */   private final float volume;
/*    */   
/*    */   public ZSoundOption(XSound paramXSound, float paramFloat1, float paramFloat2) {
/* 20 */     this.sound = paramXSound;
/* 21 */     this.pitch = paramFloat1;
/* 22 */     this.volume = paramFloat2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XSound getSound() {
/* 29 */     return this.sound;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getPitch() {
/* 36 */     return this.pitch;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getVolume() {
/* 43 */     return this.volume;
/*    */   }
/*    */ 
/*    */   
/*    */   public void play(Entity paramEntity) {
/* 48 */     if (this.sound != null)
/* 49 */       this.sound.play(paramEntity, this.volume, this.pitch); 
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\sound\ZSoundOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */