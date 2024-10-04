plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.netnews"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.netnews"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures{
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.8.2")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata:2.8.2")

    // Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")

    // Picasso
    implementation("com.squareup.picasso:picasso:2.71828")

    // Paging Library
    implementation("androidx.paging:paging-runtime:3.3.0")
    // RxJava3 support
    implementation("androidx.paging:paging-rxjava3:3.3.0")
    //Retrofit
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0");
    //Gson converter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //Logging
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")

    // Carousel view
    implementation("org.imaginativeworld.whynotimagecarousel:whynotimagecarousel:2.1.0")
    implementation("me.relex:circleindicator:2.1.6")


    //Hilt
    implementation ("com.google.dagger:hilt-android:2.50")
    annotationProcessor ("com.google.dagger:hilt-compiler:2.50")

    //Circle ImageView
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))

    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")

    implementation("com.google.firebase:firebase-database")

    //FireStore
    implementation("com.google.firebase:firebase-firestore")
    //Authentication
    implementation("com.google.firebase:firebase-auth")
    //Storage
    implementation("com.google.firebase:firebase-storage")

    //Realtime Database
    implementation("com.google.firebase:firebase-database")

    //Splash animation
    implementation ("com.airbnb.android:lottie:6.4.1")

    //Room Database
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")


    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}