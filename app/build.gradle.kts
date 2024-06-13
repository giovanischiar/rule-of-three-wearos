plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
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
    // Activity
    val activityVersion = "1.8.2"
    //noinspection KtxExtensionAvailable
    implementation("androidx.activity:activity:$activityVersion")
    implementation("androidx.activity:activity-compose:$activityVersion")

    implementation("androidx.annotation:annotation:1.7.1") // Annotation

    // Lifecycle
    val lifecycleVersion = "2.7.0"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    //noinspection KtxExtensionAvailable
    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycleVersion")

    // Coroutines
    val coroutinesVersion = "1.7.3"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    // Compose
    val composeVersion = "1.6.2"
    implementation("androidx.compose.foundation:foundation-layout-android:$composeVersion")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.animation:animation-core:$composeVersion")
    implementation("androidx.compose.animation:animation:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.runtime:runtime:$composeVersion")
    implementation("androidx.compose.ui:ui-graphics:$composeVersion")
    implementation("androidx.compose.ui:ui-text:$composeVersion")
    implementation("androidx.compose.ui:ui-unit:$composeVersion")

    // Wear Compose
    val wearComposeVersion = "1.3.0"
    implementation("androidx.wear.compose:compose-foundation:$wearComposeVersion")

    // Preview
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")

    implementation("androidx.wear:wear-tooling-preview:1.0.0") // Wear Preview

    // Navigation
    val navigationVersion = "2.7.7"
    implementation("androidx.navigation:navigation-compose:$navigationVersion")
    implementation("androidx.navigation:navigation-common:$navigationVersion")
    //noinspection KtxExtensionAvailable
    implementation("androidx.navigation:navigation-runtime:$navigationVersion")

    implementation("androidx.compose.material3:material3:1.2.0") // Material
    implementation("androidx.wear.compose:compose-material:$wearComposeVersion") // Compose Material
    implementation("androidx.wear.compose:compose-material3:1.0.0-alpha18") // Wear Material 3
    implementation("com.google.android.gms:play-services-wearable:18.1.0") // Wearable Play Services

    // Room
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.room:room-common:$roomVersion")

    //noinspection KtxExtensionAvailable
    implementation("androidx.sqlite:sqlite:2.4.0") // SQLite
    testImplementation("junit:junit:4.13.2") // JUnit Test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion") // Coroutines Test
    androidTestImplementation("androidx.test:core:1.5.0") // Android Test
    androidTestImplementation("junit:junit:4.13.2") // JUnit Android Test
    androidTestImplementation("androidx.test:runner:1.5.2") // Android Test Runner

    // Hilt
    implementation("com.google.dagger:hilt-android:2.50")
    ksp("com.google.dagger:hilt-android-compiler:2.50")
    implementation("androidx.hilt:hilt-work:1.2.0")
    ksp("androidx.hilt:hilt-compiler:1.2.0")
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
}

task("generateIcons", type = Exec::class) {
    dependsOn("mergeDebugResources")
    inputs.file("src/main/res/values/iconpalette.xml")
    inputs.dir("../scripts/IconCreator/Icon")
    outputs.file("src/main/res/drawable/ic_launcher_background.xml")
    outputs.file("src/main/res/drawable-v24/ic_launcher_foreground.xml")
    project.exec {
        commandLine = listOf("bash", "../scripts/IconCreator/Library/icon-creator.sh", "android", "../..", "../Icon")
    }
}