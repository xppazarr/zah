/*     */ package fr.maxlego08.zauctionhouse.zcore.utils;
/*     */ 
/*     */ public final class Base64
/*     */ {
/*     */   private static final int BASELENGTH = 128;
/*     */   private static final int LOOKUPLENGTH = 64;
/*     */   private static final int TWENTYFOURBITGROUP = 24;
/*     */   private static final int EIGHTBIT = 8;
/*     */   private static final int SIXTEENBIT = 16;
/*     */   private static final int FOURBYTE = 4;
/*     */   private static final int SIGN = -128;
/*     */   private static final char PAD = '=';
/*     */   private static final boolean fDebug = false;
/*  14 */   private static final byte[] base64Alphabet = new byte[128];
/*  15 */   private static final char[] lookUpBase64Alphabet = new char[64];
/*     */   
/*     */   static {
/*     */     byte b1;
/*  19 */     for (b1 = 0; b1 < ''; b1++) {
/*  20 */       base64Alphabet[b1] = -1;
/*     */     }
/*  22 */     for (b1 = 90; b1 >= 65; b1--) {
/*  23 */       base64Alphabet[b1] = (byte)(b1 - 65);
/*     */     }
/*  25 */     for (b1 = 122; b1 >= 97; b1--) {
/*  26 */       base64Alphabet[b1] = (byte)(b1 - 97 + 26);
/*     */     }
/*     */     
/*  29 */     for (b1 = 57; b1 >= 48; b1--) {
/*  30 */       base64Alphabet[b1] = (byte)(b1 - 48 + 52);
/*     */     }
/*     */     
/*  33 */     base64Alphabet[43] = 62;
/*  34 */     base64Alphabet[47] = 63;
/*     */     
/*  36 */     for (b1 = 0; b1 <= 25; b1++)
/*  37 */       lookUpBase64Alphabet[b1] = (char)(65 + b1); 
/*     */     byte b2;
/*  39 */     for (b1 = 26, b2 = 0; b1 <= 51; b1++, b2++) {
/*  40 */       lookUpBase64Alphabet[b1] = (char)(97 + b2);
/*     */     }
/*  42 */     for (b1 = 52, b2 = 0; b1 <= 61; b1++, b2++)
/*  43 */       lookUpBase64Alphabet[b1] = (char)(48 + b2); 
/*  44 */     lookUpBase64Alphabet[62] = '+';
/*  45 */     lookUpBase64Alphabet[63] = '/';
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean isWhiteSpace(char paramChar) {
/*  50 */     return (paramChar == ' ' || paramChar == '\r' || paramChar == '\n' || paramChar == '\t');
/*     */   }
/*     */   
/*     */   protected static boolean isPad(char paramChar) {
/*  54 */     return (paramChar == '=');
/*     */   }
/*     */   
/*     */   protected static boolean isData(char paramChar) {
/*  58 */     return (paramChar < '' && base64Alphabet[paramChar] != -1);
/*     */   }
/*     */   
/*     */   protected static boolean isBase64(char paramChar) {
/*  62 */     return (isWhiteSpace(paramChar) || isPad(paramChar) || isData(paramChar));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String encode(byte[] paramArrayOfbyte) {
/*  73 */     if (paramArrayOfbyte == null) {
/*  74 */       return null;
/*     */     }
/*  76 */     int i = paramArrayOfbyte.length * 8;
/*  77 */     if (i == 0) {
/*  78 */       return "";
/*     */     }
/*     */     
/*  81 */     int j = i % 24;
/*  82 */     int k = i / 24;
/*  83 */     int m = (j != 0) ? (k + 1) : k;
/*  84 */     char[] arrayOfChar = null;
/*     */     
/*  86 */     arrayOfChar = new char[m * 4];
/*     */     
/*  88 */     byte b1 = 0, b2 = 0, b3 = 0, b4 = 0, b5 = 0;
/*     */     
/*  90 */     byte b6 = 0;
/*  91 */     byte b7 = 0;
/*     */ 
/*     */     
/*     */     byte b8;
/*     */     
/*  96 */     for (b8 = 0; b8 < k; b8++) {
/*  97 */       b3 = paramArrayOfbyte[b7++];
/*  98 */       b4 = paramArrayOfbyte[b7++];
/*  99 */       b5 = paramArrayOfbyte[b7++];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 105 */       b2 = (byte)(b4 & 0xF);
/* 106 */       b1 = (byte)(b3 & 0x3);
/*     */       
/* 108 */       byte b9 = ((b3 & 0xFFFFFF80) == 0) ? (byte)(b3 >> 2) : (byte)(b3 >> 2 ^ 0xC0);
/*     */       
/* 110 */       byte b10 = ((b4 & 0xFFFFFF80) == 0) ? (byte)(b4 >> 4) : (byte)(b4 >> 4 ^ 0xF0);
/* 111 */       byte b11 = ((b5 & 0xFFFFFF80) == 0) ? (byte)(b5 >> 6) : (byte)(b5 >> 6 ^ 0xFC);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 119 */       arrayOfChar[b6++] = lookUpBase64Alphabet[b9];
/* 120 */       arrayOfChar[b6++] = lookUpBase64Alphabet[b10 | b1 << 4];
/* 121 */       arrayOfChar[b6++] = lookUpBase64Alphabet[b2 << 2 | b11];
/* 122 */       arrayOfChar[b6++] = lookUpBase64Alphabet[b5 & 0x3F];
/*     */     } 
/*     */ 
/*     */     
/* 126 */     if (j == 8) {
/* 127 */       b3 = paramArrayOfbyte[b7];
/* 128 */       b1 = (byte)(b3 & 0x3);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 133 */       b8 = ((b3 & 0xFFFFFF80) == 0) ? (byte)(b3 >> 2) : (byte)(b3 >> 2 ^ 0xC0);
/* 134 */       arrayOfChar[b6++] = lookUpBase64Alphabet[b8];
/* 135 */       arrayOfChar[b6++] = lookUpBase64Alphabet[b1 << 4];
/* 136 */       arrayOfChar[b6++] = '=';
/* 137 */       arrayOfChar[b6++] = '=';
/* 138 */     } else if (j == 16) {
/* 139 */       b3 = paramArrayOfbyte[b7];
/* 140 */       b4 = paramArrayOfbyte[b7 + 1];
/* 141 */       b2 = (byte)(b4 & 0xF);
/* 142 */       b1 = (byte)(b3 & 0x3);
/*     */       
/* 144 */       b8 = ((b3 & 0xFFFFFF80) == 0) ? (byte)(b3 >> 2) : (byte)(b3 >> 2 ^ 0xC0);
/* 145 */       byte b = ((b4 & 0xFFFFFF80) == 0) ? (byte)(b4 >> 4) : (byte)(b4 >> 4 ^ 0xF0);
/*     */       
/* 147 */       arrayOfChar[b6++] = lookUpBase64Alphabet[b8];
/* 148 */       arrayOfChar[b6++] = lookUpBase64Alphabet[b | b1 << 4];
/* 149 */       arrayOfChar[b6++] = lookUpBase64Alphabet[b2 << 2];
/* 150 */       arrayOfChar[b6++] = '=';
/*     */     } 
/*     */     
/* 153 */     return new String(arrayOfChar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] decode(String paramString) {
/* 164 */     if (paramString == null) {
/* 165 */       return null;
/*     */     }
/* 167 */     char[] arrayOfChar = paramString.toCharArray();
/*     */     
/* 169 */     int i = removeWhiteSpace(arrayOfChar);
/*     */     
/* 171 */     if (i % 4 != 0) {
/* 172 */       return null;
/*     */     }
/*     */     
/* 175 */     int j = i / 4;
/*     */     
/* 177 */     if (j == 0) {
/* 178 */       return new byte[0];
/*     */     }
/* 180 */     byte[] arrayOfByte = null;
/* 181 */     byte b1 = 0, b2 = 0, b3 = 0, b4 = 0;
/* 182 */     char c1 = Character.MIN_VALUE, c2 = Character.MIN_VALUE, c3 = Character.MIN_VALUE, c4 = Character.MIN_VALUE;
/*     */     
/* 184 */     byte b5 = 0;
/* 185 */     byte b6 = 0;
/* 186 */     byte b7 = 0;
/* 187 */     arrayOfByte = new byte[j * 3];
/*     */     
/* 189 */     for (; b5 < j - 1; b5++) {
/*     */       
/* 191 */       if (!isData(c1 = arrayOfChar[b7++]) || 
/* 192 */         !isData(c2 = arrayOfChar[b7++]) || 
/* 193 */         !isData(c3 = arrayOfChar[b7++]) || 
/* 194 */         !isData(c4 = arrayOfChar[b7++])) {
/* 195 */         return null;
/*     */       }
/* 197 */       b1 = base64Alphabet[c1];
/* 198 */       b2 = base64Alphabet[c2];
/* 199 */       b3 = base64Alphabet[c3];
/* 200 */       b4 = base64Alphabet[c4];
/*     */       
/* 202 */       arrayOfByte[b6++] = (byte)(b1 << 2 | b2 >> 4);
/* 203 */       arrayOfByte[b6++] = (byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF);
/* 204 */       arrayOfByte[b6++] = (byte)(b3 << 6 | b4);
/*     */     } 
/*     */     
/* 207 */     if (!isData(c1 = arrayOfChar[b7++]) || 
/* 208 */       !isData(c2 = arrayOfChar[b7++])) {
/* 209 */       return null;
/*     */     }
/*     */     
/* 212 */     b1 = base64Alphabet[c1];
/* 213 */     b2 = base64Alphabet[c2];
/*     */     
/* 215 */     c3 = arrayOfChar[b7++];
/* 216 */     c4 = arrayOfChar[b7++];
/* 217 */     if (!isData(c3) || 
/* 218 */       !isData(c4)) {
/* 219 */       if (isPad(c3) && isPad(c4)) {
/* 220 */         if ((b2 & 0xF) != 0)
/* 221 */           return null; 
/* 222 */         byte[] arrayOfByte1 = new byte[b5 * 3 + 1];
/* 223 */         System.arraycopy(arrayOfByte, 0, arrayOfByte1, 0, b5 * 3);
/* 224 */         arrayOfByte1[b6] = (byte)(b1 << 2 | b2 >> 4);
/* 225 */         return arrayOfByte1;
/* 226 */       }  if (!isPad(c3) && isPad(c4)) {
/* 227 */         b3 = base64Alphabet[c3];
/* 228 */         if ((b3 & 0x3) != 0)
/* 229 */           return null; 
/* 230 */         byte[] arrayOfByte1 = new byte[b5 * 3 + 2];
/* 231 */         System.arraycopy(arrayOfByte, 0, arrayOfByte1, 0, b5 * 3);
/* 232 */         arrayOfByte1[b6++] = (byte)(b1 << 2 | b2 >> 4);
/* 233 */         arrayOfByte1[b6] = (byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF);
/* 234 */         return arrayOfByte1;
/*     */       } 
/* 236 */       return null;
/*     */     } 
/*     */     
/* 239 */     b3 = base64Alphabet[c3];
/* 240 */     b4 = base64Alphabet[c4];
/* 241 */     arrayOfByte[b6++] = (byte)(b1 << 2 | b2 >> 4);
/* 242 */     arrayOfByte[b6++] = (byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF);
/* 243 */     arrayOfByte[b6++] = (byte)(b3 << 6 | b4);
/*     */ 
/*     */ 
/*     */     
/* 247 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static int removeWhiteSpace(char[] paramArrayOfchar) {
/* 257 */     if (paramArrayOfchar == null) {
/* 258 */       return 0;
/*     */     }
/*     */     
/* 261 */     byte b1 = 0;
/* 262 */     int i = paramArrayOfchar.length;
/* 263 */     for (byte b2 = 0; b2 < i; b2++) {
/* 264 */       if (!isWhiteSpace(paramArrayOfchar[b2]))
/* 265 */         paramArrayOfchar[b1++] = paramArrayOfchar[b2]; 
/*     */     } 
/* 267 */     return b1;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\Base64.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */