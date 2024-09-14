-dontwarn org.slf4j.impl.StaticLoggerBinder

# Keep the generated serializer for @Serializable classes
-keepclassmembers class ** implements kotlinx.serialization.KSerializer {
    <init>(...);
}

# Keep all @Serializable classes
-keep class kotlinx.serialization.** { *; }
-keep @kotlinx.serialization.Serializable class * { *; }

# Keep serializable classes and their generated serializer companions
-keepnames class **Kt