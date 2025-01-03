# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class searchQuery to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file searchQuery.
#-renamesourcefileattribute SourceFile
-dontwarn org.slf4j.impl.StaticLoggerBinder
-keep class com.kal.brawlstatz3.data.model.brawler.* {*;}
-keep class com.kal.brawlstatz3.data.model.club.* {*;}
-keep class com.kal.brawlstatz3.data.model.event.* {*;}
-keep class com.kal.brawlstatz3.data.model.player.* {*;}