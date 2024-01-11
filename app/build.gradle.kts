plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "io.schiar.threerule"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.schiar.threerule"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {
    implementation("androidx.compose.foundation:foundation-layout-android:1.5.4")
    val composeVersion = "1.5.4"
    val wearComposeVersion = "1.2.1"

    // General compose dependencies
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.wear.compose:compose-material:$wearComposeVersion")
    implementation("androidx.wear.compose:compose-foundation:$wearComposeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("com.google.android.gms:play-services-wearable:18.1.0")
    implementation("androidx.percentlayout:percentlayout:1.0.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
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