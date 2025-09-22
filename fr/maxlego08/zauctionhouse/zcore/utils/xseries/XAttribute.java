/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.base.XModule;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.base.XRegistry;
/*     */ import java.util.Collection;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.function.BiFunction;
/*     */ import org.bukkit.NamespacedKey;
/*     */ import org.bukkit.Registry;
/*     */ import org.bukkit.attribute.Attribute;
/*     */ import org.bukkit.attribute.AttributeModifier;
/*     */ import org.bukkit.inventory.EquipmentSlot;
/*     */ import org.bukkit.inventory.EquipmentSlotGroup;
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
/*     */ public final class XAttribute
/*     */   extends XModule<XAttribute, Attribute>
/*     */ {
/*     */   public static final XRegistry<XAttribute, Attribute> REGISTRY;
/*     */   
/*     */   static {
/*     */     boolean bool;
/*  42 */     REGISTRY = new XRegistry(Attribute.class, XAttribute.class, () -> Registry.ATTRIBUTE, XAttribute::new, paramInt -> new XAttribute[paramInt]);
/*     */   }
/*     */ 
/*     */   
/*  46 */   public static final XAttribute MAX_HEALTH = std(new String[] { "max_health", "generic.max_health" });
/*  47 */   public static final XAttribute FOLLOW_RANGE = std(new String[] { "follow_range", "generic.follow_range" });
/*  48 */   public static final XAttribute KNOCKBACK_RESISTANCE = std(new String[] { "knockback_resistance", "generic.knockback_resistance" });
/*  49 */   public static final XAttribute MOVEMENT_SPEED = std(new String[] { "movement_speed", "generic.movement_speed" });
/*  50 */   public static final XAttribute FLYING_SPEED = std(new String[] { "flying_speed", "generic.flying_speed" });
/*  51 */   public static final XAttribute ATTACK_DAMAGE = std(new String[] { "attack_damage", "generic.attack_damage" });
/*  52 */   public static final XAttribute ATTACK_KNOCKBACK = std(new String[] { "attack_knockback", "generic.attack_knockback" });
/*  53 */   public static final XAttribute ATTACK_SPEED = std(new String[] { "attack_speed", "generic.attack_speed" });
/*  54 */   public static final XAttribute ARMOR = std(new String[] { "armor", "generic.armor" });
/*  55 */   public static final XAttribute ARMOR_TOUGHNESS = std(new String[] { "armor_toughness", "generic.armor_toughness" });
/*  56 */   public static final XAttribute FALL_DAMAGE_MULTIPLIER = std(new String[] { "fall_damage_multiplier", "generic.fall_damage_multiplier" });
/*  57 */   public static final XAttribute LUCK = std(new String[] { "luck", "generic.luck" });
/*  58 */   public static final XAttribute MAX_ABSORPTION = std(new String[] { "max_absorption", "generic.max_absorption" });
/*  59 */   public static final XAttribute SAFE_FALL_DISTANCE = std(new String[] { "safe_fall_distance", "generic.safe_fall_distance" });
/*  60 */   public static final XAttribute SCALE = std(new String[] { "scale", "generic.scale" });
/*  61 */   public static final XAttribute STEP_HEIGHT = std(new String[] { "step_height", "generic.step_height" });
/*  62 */   public static final XAttribute GRAVITY = std(new String[] { "gravity", "generic.gravity" });
/*  63 */   public static final XAttribute JUMP_STRENGTH = std(new String[] { "jump_strength", "generic.jump_strength", "horse.jump_strength" });
/*  64 */   public static final XAttribute BURNING_TIME = std(new String[] { "burning_time", "generic.burning_time" });
/*  65 */   public static final XAttribute EXPLOSION_KNOCKBACK_RESISTANCE = std(new String[] { "explosion_knockback_resistance", "generic.explosion_knockback_resistance" });
/*  66 */   public static final XAttribute MOVEMENT_EFFICIENCY = std(new String[] { "movement_efficiency", "generic.movement_efficiency" });
/*  67 */   public static final XAttribute OXYGEN_BONUS = std(new String[] { "oxygen_bonus", "generic.oxygen_bonus" });
/*  68 */   public static final XAttribute WATER_MOVEMENT_EFFICIENCY = std(new String[] { "water_movement_efficiency", "generic.water_movement_efficiency" });
/*  69 */   public static final XAttribute TEMPT_RANGE = std(new String[] { "tempt_range", "generic.tempt_range" });
/*  70 */   public static final XAttribute BLOCK_INTERACTION_RANGE = std(new String[] { "block_interaction_range", "player.block_interaction_range" });
/*  71 */   public static final XAttribute ENTITY_INTERACTION_RANGE = std(new String[] { "entity_interaction_range", "player.entity_interaction_range" });
/*  72 */   public static final XAttribute BLOCK_BREAK_SPEED = std(new String[] { "block_break_speed", "player.block_break_speed" });
/*  73 */   public static final XAttribute MINING_EFFICIENCY = std(new String[] { "mining_efficiency", "player.mining_efficiency" });
/*  74 */   public static final XAttribute SNEAKING_SPEED = std(new String[] { "sneaking_speed", "player.sneaking_speed" });
/*  75 */   public static final XAttribute SUBMERGED_MINING_SPEED = std(new String[] { "submerged_mining_speed", "player.submerged_mining_speed" });
/*  76 */   public static final XAttribute SWEEPING_DAMAGE_RATIO = std(new String[] { "sweeping_damage_ratio", "player.sweeping_damage_ratio" });
/*  77 */   public static final XAttribute SPAWN_REINFORCEMENTS = std(new String[] { "spawn_reinforcements", "zombie.spawn_reinforcements" });
/*     */ 
/*     */   
/*     */   private static final boolean SUPPORTS_MODERN_MODIFIERS;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  86 */       AttributeModifier.class.getConstructor(new Class[] { NamespacedKey.class, double.class, AttributeModifier.Operation.class, EquipmentSlotGroup.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  92 */       bool = true;
/*  93 */     } catch (NoSuchMethodException|NoClassDefFoundError noSuchMethodException) {
/*  94 */       bool = false;
/*     */     } 
/*     */     
/*  97 */     SUPPORTS_MODERN_MODIFIERS = bool;
/*     */   }
/*     */   
/*     */   private XAttribute(Attribute paramAttribute, String[] paramArrayOfString) {
/* 101 */     super(paramAttribute, paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AttributeModifier createModifier(@NotNull String paramString, double paramDouble, @NotNull AttributeModifier.Operation paramOperation, @Nullable EquipmentSlot paramEquipmentSlot) {
/* 110 */     Objects.requireNonNull(paramString, "Key is null");
/* 111 */     Objects.requireNonNull(paramOperation, "Operation is null");
/*     */     
/* 113 */     if (SUPPORTS_MODERN_MODIFIERS) {
/* 114 */       NamespacedKey namespacedKey = Objects.<NamespacedKey>requireNonNull(NamespacedKey.fromString(paramString), () -> "Invalid namespace: " + paramString);
/*     */       
/* 116 */       return new AttributeModifier(namespacedKey, paramDouble, paramOperation, (paramEquipmentSlot == null) ? EquipmentSlotGroup.ANY : paramEquipmentSlot.getGroup());
/*     */     } 
/*     */     
/* 119 */     return new AttributeModifier(UUID.randomUUID(), paramString, paramDouble, paramOperation, paramEquipmentSlot);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAttribute of(Attribute paramAttribute) {
/* 125 */     return (XAttribute)REGISTRY.getByBukkitForm(paramAttribute);
/*     */   }
/*     */   
/*     */   public static Optional<XAttribute> of(String paramString) {
/* 129 */     return REGISTRY.getByName(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static XAttribute[] values() {
/* 137 */     return (XAttribute[])REGISTRY.values();
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Collection<XAttribute> getValues() {
/* 143 */     return REGISTRY.getValues();
/*     */   }
/*     */   
/*     */   private static XAttribute std(String... paramVarArgs) {
/* 147 */     return (XAttribute)REGISTRY.std(paramVarArgs);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\XAttribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */