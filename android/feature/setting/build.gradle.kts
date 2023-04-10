plugins {
    id("com.jaino.feature")
    id("com.jaino.hilt")
}

android {
    namespace = "com.jaino.setting"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}
dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:navigation"))
    implementation(libs.timber)
    implementation(libs.kotlin.datetime)
    implementation(libs.androidx.fragment)
    implementation(libs.bundles.androidx)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.nav.feature)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
}