plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    `maven-publish`
}

val libVersion = "0.4.0"

android {
    defaultConfig {
        minSdk = 21
        compileSdk = 35
        version = libVersion
        namespace = "com.github.skgmn.composetooltip"
        consumerProguardFiles("consumer-rules.pro")
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
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
    buildFeatures.compose = true
    packagingOptions.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.preview)
    implementation(libs.androidx.compose.material2)
    implementation(libs.androidx.compose.constraints)
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.quipper.compose-tooltip"
            artifactId = "composetooltip"
            version = libVersion

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
