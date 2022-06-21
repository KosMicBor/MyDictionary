object Config {
    const val compileSdk = 32
    const val applicationId = "kosmicbor.mydictionary"
    const val minSdk = 23
    const val targetSdk = 32
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object Releases {
    const val versionCode = 1
    const val versionName = "1.0"
}

object Modules {
    const val app = ":app"
}

object Versions {
    //Core
    const val core = "1.8.0"

    //Design
    const val appCompat = "1.4.2"
    const val material = "1.6.1"
    const val constraintLayout = "2.1.4"
    const val legacySupport = "1.0.0"
    const val recyclerView = "1.2.1"

    //Test
    const val junit = "4.13.2"
    const val extJunit = "1.1.3"
    const val espressoCore = "3.4.0"

    //viewBinding delegate
    const val viewBindingPropertyDelegate = "1.5.6"
    const val dataBindingRuntime = "7.2.1"

    //Retrofit
    const val retrofit = "2.9.0"
    const val converterGson = "2.9.0"
    const val retrofitAdapter = "3.0.0"

    //OkHttp
    const val okhttp = "4.9.3"
    const val loggingInterceptor = "4.9.3"

    //viewModel
    const val fragmentKtx = "1.4.1"
    const val lifecycleViewModelKtx = "2.4.1"
    const val lifecycleLivedataKtx = "2.4.1"
    const val lifecycleViewModelSavedState = "2.4.1"

    //Timber
    const val timber = "5.0.1"

    //Koin
    const val koinCore = "3.1.2"
    const val koinAndroid = "3.1.2"
    const val koinAndroidCompat = "3.1.2"

    //Coroutines
    const val coroutinesCore = "1.5.1"
    const val coroutinesAndroid = "1.5.2"

    //Room
    const val roomKtx = "2.4.2"
    const val roomRuntime = "2.4.2"
    const val annotationProcessorRoomCompiler = "2.4.2"
    const val kaptRoomCompiler = "2.4.2"

    //Glide
    const val glide = "4.13.0"
    const val annotationProcessorGlideCompiler = "4.13.0"
}

object Kotlin {
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val legacySupport = "androidx.legacy:legacy-support-v4:${Versions.legacySupport}"
}

object Design {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
}

object Test {
    const val junit = "junit:junit:${Versions.junit}"
    const val extJunit = "androidx.test.ext:junit:${Versions.extJunit}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}

object ViewBindingDelegate {
    const val viewBindingPropertyDelegate =
        "com.github.kirich1409:viewbindingpropertydelegate:${Versions.viewBindingPropertyDelegate}"
    const val dataBindingRuntime =
        "androidx.databinding:databinding-runtime:${Versions.dataBindingRuntime}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
    const val retrofitAdapter =
        "com.github.akarnokd:rxjava3-retrofit-adapter:${Versions.retrofitAdapter}"
}

object OkHttp {
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
}

object ViewModel {
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
    const val lifecycleViewModelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewModelKtx}"
    const val lifecycleLivedataKtx =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleLivedataKtx}"
    const val lifecycleViewModelSavedState =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycleViewModelSavedState}"
}

object Timber {
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
}

object Koin {
    const val koinCore = "io.insert-koin:koin-core:${Versions.koinCore}"
    const val koinAndroid = "io.insert-koin:koin-android:${Versions.koinAndroid}"
    const val koinAndroidCompat = "io.insert-koin:koin-android-compat:${Versions.koinAndroidCompat}"
}

object Coroutines {
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
}

object Room {
    const val roomKtx = "androidx.room:room-ktx:${Versions.roomKtx}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomRuntime}"
    const val annotationProcessorRoomCompiler =
        "androidx.room:room-compiler:${Versions.annotationProcessorRoomCompiler}"
    const val kaptRoomCompiler = "androidx.room:room-compiler:${Versions.kaptRoomCompiler}"
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val annotationProcessorGlideCompiler =
        "com.github.bumptech.glide:compiler:${Versions.annotationProcessorGlideCompiler}"
}