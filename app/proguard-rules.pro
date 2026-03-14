# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#-keep class com.danucdev.thesimpsons.data.network.dto.*

# Keep generic signatures (CRITICAL for Retrofit)
# Keep generic type info (CRITICAL for Retrofit suspend functions)
-keepattributes Signature, InnerClasses, EnclosingMethod,Exceptions

# Keep annotations
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations

# DTOs
-keep class com.danucdev.thesimpsons.data.network.dto.** { *; }

# Retrofit service interfaces
-keep interface com.danucdev.thesimpsons.data.network.** { *; }

# Retrofit HTTP annotations
-keepclassmembers interface * {
    @retrofit2.http.* <methods>;
}

-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Gson
-keep class com.google.gson.** { *; }
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

-keep class retrofit2.KotlinExtensions { *; }

# Dagger / Inject
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }

# Kotlin metadata
-keep class kotlin.Metadata { *; }
-keep class kotlin.collections.** { *; }

-keepattributes SourceFile,LineNumberTable

 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

 # R8 full mode strips generic signatures from return types if not kept.
 -if interface * { @retrofit2.http.* public *** *(...); }
 -keep,allowoptimization,allowshrinking,allowobfuscation class <3>

 # With R8 full mode generic signatures are stripped for classes that are not kept.
 -keep,allowobfuscation,allowshrinking class retrofit2.Response

