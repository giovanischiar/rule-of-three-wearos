plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.9.21-1.0.15"
}

android {
    namespace = "io.schiar.ruleofthree"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.schiar.ruleofthree"
        minSdk = 30
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
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.6"
    }
}

dependencies {
    implementation("androidx.compose.foundation:foundation-layout-android:1.5.4")
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.6")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    // General compose dependencies
    val composeVersion = "1.5.4"
    val wearComposeVersion = "1.2.1"
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.wear.compose:compose-foundation:$wearComposeVersion")

    // Preview
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.4")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.wear:wear-tooling-preview:1.0.0")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("androidx.wear.compose:compose-material3:1.0.0-alpha15")

    // Material
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.wear.compose:compose-material:$wearComposeVersion")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("com.google.android.gms:play-services-wearable:18.1.0")
    implementation("androidx.percentlayout:percentlayout:1.0.0")

    // Room for database persistence
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    //Test
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

task("generateIcons", type = Exec::class) {
    dependsOn("mergeDebugResources")
    inputs.file("src/main/res/values/iconpalette.xml")
    inputs.dir("../scripts/IconCreator/Icon")
    outputs.file("src/main/res/drawable/ic_launcher_background.xml")
    outputs.file("src/main/res/drawable-v24/ic_launcher_foreground.xml")
    project.exec {
        commandLine = listOf("bash", "../scripts/IconCreator/Library/icon-creator.sh", "android", "../..", "../Icon", "scaled")
    }
}