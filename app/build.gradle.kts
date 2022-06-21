import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

val appProperties = Properties()
appProperties.load(FileInputStream(rootProject.file("app.properties")))

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Releases.versionCode
        versionName = Releases.versionName

        testInstrumentationRunner = Config.testInstrumentationRunner

        buildConfigField("String", "API_KEY", appProperties.getProperty("apiKey"))

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas"
                )
            }
        }
    }

    buildFeatures {
        viewBinding = true
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
}

dependencies {

    implementation(Kotlin.core)
    implementation(Design.appCompat)
    implementation(Design.material)
    implementation(Design.constraintLayout)
    implementation(Kotlin.legacySupport)
    implementation(Design.recyclerView)

    //Test
    testImplementation(Test.junit)
    androidTestImplementation(Test.extJunit)
    androidTestImplementation(Test.espressoCore)

    //viewBinding delegate
    implementation(ViewBindingDelegate.viewBindingPropertyDelegate)
    implementation(ViewBindingDelegate.dataBindingRuntime)

    //Retrofit
    implementation(Retrofit.retrofit)
    implementation(Retrofit.converterGson)
    implementation(Retrofit.retrofitAdapter)

    //OkHttp
    implementation(OkHttp.okhttp)
    implementation(OkHttp.loggingInterceptor)

    //viewModel
    implementation(ViewModel.fragmentKtx)
    implementation(ViewModel.lifecycleViewModelKtx)
    implementation(ViewModel.lifecycleLivedataKtx)
    implementation(ViewModel.lifecycleViewModelSavedState)

    //Timber
    implementation(Timber.timber)

    //Koin
    implementation(Koin.koinCore)
    implementation(Koin.koinAndroid)
    implementation(Koin.koinAndroidCompat)

    //Coroutines
    implementation(Coroutines.coroutinesCore)
    implementation(Coroutines.coroutinesAndroid)

    //Room
    implementation(Room.roomKtx)
    implementation(Room.roomRuntime)
    annotationProcessor(Room.annotationProcessorRoomCompiler)
    kapt(Room.kaptRoomCompiler)
    //Без этого не компилируется
    kapt("com.google.dagger:dagger-compiler:2.42")

    //Glide
    implementation(Glide.glide)
    kapt(Glide.annotationProcessorGlideCompiler)

    //Modules
    implementation(project(":utils"))
    implementation(project(":core"))
}