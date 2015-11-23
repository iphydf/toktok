-optimizations *
-optimizationpasses 3
-allowaccessmodification

-dontwarn com.google.common.**
-dontwarn com.squareup.picasso.OkHttpDownloader
-dontwarn io.realm.**
-dontwarn org.threeten.bp.chrono.JapaneseEra
-dontwarn org.xmlpull.v1.**
-dontwarn scala.xml.**
-dontwarn scalaz.**
-dontwarn scodec.bits.*

-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class * extends android.support.design.widget.CoordinatorLayout$Behavior { <init>(...); }
-keep class * extends android.test.ActivityInstrumentationTestCase2 { public *; }

-keepattributes EnclosingMethod
-keepattributes InnerClasses
-keepattributes Signature

# Constructed from JNI.
-keep public class im.tox.** extends im.tox.tox4j.exceptions.ToxException {
  public *;
}

-keep public class im.tox.** extends java.lang.Enum {
  public *;
}
