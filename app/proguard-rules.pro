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

#this method is to keep defined classes name same asitis
-keep class com.apps.cricnews.Model.** { *; }
-keep class com.apps.cricnews.Adapter.** { *; }
-keep class com.apps.cricnews.API.** { *; }
-keep class com.apps.cricnews.Components.** { *; }
-keep class com.apps.cricnews.Repo.** { *; }
-keep class com.apps.cricnews.Screens.** { *; }
-keep class com.apps.cricnews.ViewModels.** { *; }
-keep class com.apps.cricnews.Utils.** { *; }
-keep class com.google.android.exoplayer.** {*;}
# Keep all classes and their members in kotlinx.coroutines
-keep class kotlinx.coroutines.** { *; }
-keep class com.google.android.play.** { *; }

# Don't warn about any missing classes or methods in kotlinx.coroutines
-dontwarn kotlinx.coroutines.**
## Rules for Retrofit2
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions


# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes Signature
-keepattributes *Annotation*

# Retrofit with Gson
-keep class retrofit2.converter.gson.** { *; }
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**
-keep class com.facebook.shimmer.** { *; }
-keep class com.google.android.gms.** { *; }
-keep class com.firebaseui.** { *; }

# Picasso
-dontwarn com.squareup.okhttp.**


# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
-dontwarn com.google.android.gms.auth.api.credentials.Credential$Builder
-dontwarn com.google.android.gms.auth.api.credentials.Credential
-dontwarn com.google.android.gms.auth.api.credentials.CredentialRequest$Builder
-dontwarn com.google.android.gms.auth.api.credentials.CredentialRequest
-dontwarn com.google.android.gms.auth.api.credentials.CredentialRequestResponse
-dontwarn com.google.android.gms.auth.api.credentials.Credentials
-dontwarn com.google.android.gms.auth.api.credentials.CredentialsClient
-dontwarn com.google.android.gms.auth.api.credentials.CredentialsOptions$Builder
-dontwarn com.google.android.gms.auth.api.credentials.CredentialsOptions
-dontwarn com.google.android.gms.auth.api.credentials.HintRequest$Builder
-dontwarn com.google.android.gms.auth.api.credentials.HintRequest