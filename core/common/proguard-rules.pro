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
-keep class com.thejohnsondev.common.AppType { *; }
-keep class com.thejohnsondev.common.AppType$Companion { *; }
-keep class com.thejohnsondev.common.ConstantsKt { *; }
-keep class com.thejohnsondev.common.Platform { *; }
-keep class com.thejohnsondev.common.Platform_androidKt { *; }
-keep class com.thejohnsondev.common.base.BaseViewModel { *; }
-keep class com.thejohnsondev.common.di.CommonModule_androidKt { *; }
-keep class com.thejohnsondev.common.navigation.Routes$BiometricRoute { *; }
-keep class com.thejohnsondev.common.navigation.Routes$HomeRoute { *; }
-keep class com.thejohnsondev.common.navigation.Routes$LoginRoute { *; }
-keep class com.thejohnsondev.common.navigation.Routes$SelectVaultTypeRoute { *; }
-keep class com.thejohnsondev.common.navigation.Routes$SettingsRoute { *; }
-keep class com.thejohnsondev.common.navigation.Routes$SignUpRoute { *; }
-keep class com.thejohnsondev.common.navigation.Routes$ToolsRoute { *; }
-keep class com.thejohnsondev.common.navigation.Routes$VaultRoute { *; }
-keep class com.thejohnsondev.common.navigation.Routes$VaultRoute$Companion { *; }
-keep class com.thejohnsondev.common.navigation.Routes$WelcomeRoute { *; }
-keep class com.thejohnsondev.common.navigation.Routes { *; }
-keep class com.thejohnsondev.common.utils.BuildKonfigProvider { *; }
-keep class com.thejohnsondev.common.utils.DataUtilsKt { *; }
-keep class com.thejohnsondev.common.utils.DateTimeUtilsKt { *; }
-keep class com.thejohnsondev.common.utils.Logger { *; }

-keepclassmembers class * {
    @kotlinx.serialization.Serializable <fields>;
    @kotlinx.serialization.Serializable <methods>;
}
-keepclassmembers class * {
    kotlinx.serialization.KSerializer $$serializer;
    <init>(kotlinx.serialization.internal.SerializationConstructorMarker);
}
-keep class kotlinx.serialization.** { *; }
-keep class kotlinx.serialization.json.** { *; }

-repackageclasses 'corecommon'
-keepattributes *Annotation*
-printmapping proguard-mapping.txt
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepattributes AnnotationDefault
-keepattributes Exceptions
-allowaccessmodification
-dontwarn java.lang.invoke.StringConcatFactory
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-printconfiguration proguard-config.txt