/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.base.XModule;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.base.XRegistry;
/*     */ import java.util.Collection;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiFunction;
/*     */ import org.bukkit.Registry;
/*     */ import org.bukkit.block.banner.PatternType;
/*     */ import org.jetbrains.annotations.NotNull;
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
/*     */ public final class XPatternType
/*     */   extends XModule<XPatternType, PatternType>
/*     */ {
/*     */   public static final XRegistry<XPatternType, PatternType> REGISTRY;
/*     */   
/*     */   static {
/*  35 */     REGISTRY = new XRegistry(PatternType.class, XPatternType.class, () -> Registry.BANNER_PATTERN, XPatternType::new, paramInt -> new XPatternType[paramInt]);
/*     */   }
/*     */ 
/*     */   
/*  39 */   public static final XPatternType BASE = std(new String[] { "base" });
/*     */   
/*  41 */   public static final XPatternType SQUARE_BOTTOM_LEFT = std(new String[] { "square_bottom_left" });
/*  42 */   public static final XPatternType SQUARE_BOTTOM_RIGHT = std(new String[] { "square_bottom_right" });
/*  43 */   public static final XPatternType SQUARE_TOP_LEFT = std(new String[] { "square_top_left" });
/*  44 */   public static final XPatternType SQUARE_TOP_RIGHT = std(new String[] { "square_top_right" });
/*     */   
/*  46 */   public static final XPatternType STRIPE_BOTTOM = std(new String[] { "stripe_bottom" });
/*  47 */   public static final XPatternType STRIPE_TOP = std(new String[] { "stripe_top" });
/*  48 */   public static final XPatternType STRIPE_LEFT = std(new String[] { "stripe_left" });
/*  49 */   public static final XPatternType STRIPE_RIGHT = std(new String[] { "stripe_right" });
/*  50 */   public static final XPatternType STRIPE_CENTER = std(new String[] { "stripe_center" });
/*  51 */   public static final XPatternType STRIPE_MIDDLE = std(new String[] { "stripe_middle" });
/*  52 */   public static final XPatternType STRIPE_DOWNRIGHT = std(new String[] { "stripe_downright" });
/*  53 */   public static final XPatternType STRIPE_DOWNLEFT = std(new String[] { "stripe_downleft" });
/*     */   
/*  55 */   public static final XPatternType SMALL_STRIPES = std(new String[] { "small_stripes", "STRIPE_SMALL" });
/*     */   
/*  57 */   public static final XPatternType CROSS = std(new String[] { "cross" });
/*  58 */   public static final XPatternType STRAIGHT_CROSS = std(new String[] { "straight_cross" });
/*     */   
/*  60 */   public static final XPatternType TRIANGLE_BOTTOM = std(new String[] { "triangle_bottom" });
/*  61 */   public static final XPatternType TRIANGLE_TOP = std(new String[] { "triangle_top" });
/*  62 */   public static final XPatternType TRIANGLES_BOTTOM = std(new String[] { "triangles_bottom" });
/*  63 */   public static final XPatternType TRIANGLES_TOP = std(new String[] { "triangles_top" });
/*     */   
/*  65 */   public static final XPatternType DIAGONAL_LEFT = std(new String[] { "diagonal_left" });
/*  66 */   public static final XPatternType DIAGONAL_UP_RIGHT = std(new String[] { "diagonal_up_right", "DIAGONAL_RIGHT_MIRROR" });
/*  67 */   public static final XPatternType DIAGONAL_UP_LEFT = std(new String[] { "diagonal_up_left", "DIAGONAL_LEFT_MIRROR" });
/*  68 */   public static final XPatternType DIAGONAL_RIGHT = std(new String[] { "diagonal_right" });
/*     */   
/*  70 */   public static final XPatternType CIRCLE = std(new String[] { "circle", "CIRCLE_MIDDLE" });
/*  71 */   public static final XPatternType RHOMBUS = std(new String[] { "rhombus", "RHOMBUS_MIDDLE" });
/*     */   
/*  73 */   public static final XPatternType HALF_VERTICAL = std(new String[] { "half_vertical" });
/*  74 */   public static final XPatternType HALF_HORIZONTAL = std(new String[] { "half_horizontal" });
/*  75 */   public static final XPatternType HALF_VERTICAL_RIGHT = std(new String[] { "half_vertical_right", "HALF_VERTICAL_MIRROR" });
/*  76 */   public static final XPatternType HALF_HORIZONTAL_BOTTOM = std(new String[] { "half_horizontal_bottom", "HALF_HORIZONTAL_MIRROR" });
/*     */   
/*  78 */   public static final XPatternType BORDER = std(new String[] { "border" });
/*  79 */   public static final XPatternType CURLY_BORDER = std(new String[] { "curly_border" });
/*  80 */   public static final XPatternType CREEPER = std(new String[] { "creeper" });
/*  81 */   public static final XPatternType GRADIENT = std(new String[] { "gradient" });
/*  82 */   public static final XPatternType GRADIENT_UP = std(new String[] { "gradient_up" });
/*  83 */   public static final XPatternType BRICKS = std(new String[] { "bricks" });
/*  84 */   public static final XPatternType SKULL = std(new String[] { "skull" });
/*  85 */   public static final XPatternType FLOWER = std(new String[] { "flower" });
/*  86 */   public static final XPatternType MOJANG = std(new String[] { "mojang" });
/*  87 */   public static final XPatternType GLOBE = std(new String[] { "globe" });
/*  88 */   public static final XPatternType PIGLIN = std(new String[] { "piglin" });
/*  89 */   public static final XPatternType FLOW = std(new String[] { "flow" });
/*  90 */   public static final XPatternType GUSTER = std(new String[] { "guster" });
/*     */ 
/*     */   
/*     */   private XPatternType(PatternType paramPatternType, String[] paramArrayOfString) {
/*  94 */     super(paramPatternType, paramArrayOfString);
/*     */   }
/*     */   
/*     */   public static XPatternType of(PatternType paramPatternType) {
/*  98 */     return (XPatternType)REGISTRY.getByBukkitForm(paramPatternType);
/*     */   }
/*     */   
/*     */   public static Optional<XPatternType> of(String paramString) {
/* 102 */     return REGISTRY.getByName(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Collection<XPatternType> getValues() {
/* 108 */     return REGISTRY.getValues();
/*     */   }
/*     */   
/*     */   private static XPatternType std(String... paramVarArgs) {
/* 112 */     return (XPatternType)REGISTRY.std(paramVarArgs);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\XPatternType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */