/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Date;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Consumer;
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
/*     */ public class ConsumerConstructor
/*     */ {
/*     */   public static Consumer<Schema> createConsumerFromTemplate(Class<?> paramClass, Object paramObject) {
/*  38 */     Constructor[] arrayOfConstructor = (Constructor[])paramClass.getDeclaredConstructors();
/*  39 */     Constructor constructor = arrayOfConstructor[0];
/*  40 */     constructor.setAccessible(true);
/*     */     
/*  42 */     Field[] arrayOfField = paramClass.getDeclaredFields();
/*  43 */     if (arrayOfField.length != constructor.getParameterCount()) {
/*  44 */       throw new IllegalArgumentException("Fields count does not match constructor parameters count");
/*     */     }
/*     */ 
/*     */     
/*  48 */     return paramSchema -> {
/*     */         boolean bool = false;
/*     */         
/*     */         for (byte b = 0; b < paramArrayOfField.length; b++) {
/*     */           Field field = paramArrayOfField[b];
/*     */           
/*     */           field.setAccessible(true);
/*     */           
/*     */           Class<?> clazz = field.getType();
/*     */           
/*     */           String str1 = field.getName();
/*     */           String str2 = clazz.getTypeName().substring(clazz.getTypeName().lastIndexOf('.') + 1);
/*     */           Column column = null;
/*     */           if (field.isAnnotationPresent((Class)Column.class)) {
/*     */             column = field.<Column>getAnnotation(Column.class);
/*     */           }
/*     */           if (column != null && !column.type().isEmpty()) {
/*     */             str2 = column.type();
/*     */           }
/*     */           if (column != null && !column.value().isEmpty()) {
/*     */             str1 = column.value();
/*     */           }
/*     */           try {
/*     */             schemaFromType(paramSchema, str2, str1, (paramObject == null) ? null : field.get(paramObject));
/*  72 */           } catch (IllegalAccessException illegalAccessException) {
/*     */             throw new RuntimeException(illegalAccessException);
/*     */           } 
/*     */           if (column != null) {
/*     */             if (column.primary() && !bool) {
/*     */               bool = true;
/*     */               paramSchema.primary();
/*     */             } 
/*     */             if (column.autoIncrement()) {
/*     */               if (!clazz.getTypeName().equals("long")) {
/*     */                 throw new IllegalArgumentException("Auto increment is only available for long type");
/*     */               }
/*     */               paramSchema.autoIncrement(column.value());
/*     */             } 
/*     */             if (column.foreignKey()) {
/*     */               if (column.foreignKeyReference().isEmpty()) {
/*     */                 throw new IllegalArgumentException("Foreign key reference is empty");
/*     */               }
/*     */               paramSchema.foreignKey(column.foreignKeyReference());
/*     */             } 
/*     */             if (column.nullable()) {
/*     */               paramSchema.nullable();
/*     */             }
/*     */           } 
/*     */           if (b == 0 && !bool) {
/*     */             bool = true;
/*     */             paramSchema.primary();
/*     */           } 
/*     */         } 
/*     */       };
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
/*     */   private static void schemaFromType(Schema paramSchema, String paramString1, String paramString2, Object paramObject) {
/* 129 */     switch (paramString1.toLowerCase()) {
/*     */       case "string":
/* 131 */         if (paramObject == null) {
/* 132 */           paramSchema.string(paramString2, 255);
/*     */         } else {
/*     */           
/* 135 */           paramSchema.string(paramString2, paramObject.toString());
/*     */         }  return;
/*     */       case "longtext":
/* 138 */         paramSchema.longText(paramString2);
/*     */         return;
/*     */       case "integer":
/*     */       case "int":
/*     */       case "long":
/*     */       case "bigint":
/* 144 */         if (paramObject == null) {
/* 145 */           paramSchema.bigInt(paramString2);
/*     */         } else {
/*     */           
/* 148 */           paramSchema.bigInt(paramString2, Long.parseLong(paramObject.toString()));
/*     */         }  return;
/*     */       case "boolean":
/* 151 */         if (paramObject == null) {
/* 152 */           paramSchema.bool(paramString2);
/*     */         } else {
/*     */           
/* 155 */           paramSchema.bool(paramString2, ((Boolean)paramObject).booleanValue());
/*     */         }  return;
/*     */       case "double":
/*     */       case "float":
/*     */       case "bigdecimal":
/* 160 */         if (paramObject == null) {
/* 161 */           paramSchema.decimal(paramString2);
/*     */         } else {
/*     */           
/* 164 */           paramSchema.decimal(paramString2, (Double)paramObject);
/*     */         }  return;
/*     */       case "uuid":
/* 167 */         if (paramObject == null) {
/* 168 */           paramSchema.uuid(paramString2);
/*     */         } else {
/*     */           
/* 171 */           paramSchema.uuid(paramString2, (UUID)paramObject);
/*     */         }  return;
/*     */       case "date":
/* 174 */         paramSchema.date(paramString2, (Date)paramObject).nullable();
/*     */         return;
/*     */       case "timestamp":
/* 177 */         paramSchema.timestamp(paramString2).nullable();
/*     */         return;
/*     */     } 
/* 180 */     throw new IllegalArgumentException("Type " + paramString1 + " is not supported");
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\ConsumerConstructor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */