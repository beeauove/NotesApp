//@file:Suppress("UNUSED_EXPRESSION")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //id("kotlin-android")
    // id("kotlin-android-extensions")
    // id("kotlin-kapt")
    id("com.google.devtools.ksp")
//    kotlin("android.extensions)"
}
android {
    namespace = "com.example.notesapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.notesapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.majorVersion
    }
}

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.20-1.0.14")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.20")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
//    implementation("com.android.car.ui:car-ui-lib:2.5.1")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //dependencies
    //material design
    //implementation("com.google.android.material:material:1.10.0.5")
    // circle image view
    //implementation("de.rhododendron:circumnavigate:3.1.0")
    //scalable unit text size
    implementation("com.intuit.ssp:ssp-android:1.0.6")
    //scalable unit size
    implementation("com.intuit.sdp:sdp-android:1.0.6")
    //room database
    implementation("androidx.room:room-runtime:2.6.0")
    ksp("androidx.room:room-compiler:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
    //implementation("com.maker amen:recompounded:2.3.0")
    //crop image library
    //implementation("com.softhearted.modded:android-image-cropper:2.8.0")
    //easy permission
//    implementation("pub.devrel:easypermissions:3.0.0")
    //coroutines core
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("io.appwrite:sdk-for-android:4.0.1")
}


