// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
}

subprojects {
    afterEvaluate {
        dependencies {
            "implementation"(libs.androidx.core.ktx)
            "implementation"(libs.androidx.appcompat)
            "implementation"(libs.material)
            "implementation"(libs.kotlinx.coroutines.android)
            "implementation"(libs.androidx.lifecycle.runtime.ktx)
            "implementation"(platform(libs.androidx.compose.bom))
            "implementation"(libs.androidx.compose.ui)
            "implementation"(libs.androidx.compose.ui.tooling.preview)
            "implementation"(libs.androidx.compose.foundation)
            "implementation"(libs.androidx.compose.material3)
            "implementation"(libs.androidx.navigation.compose)
            "implementation"(libs.androidx.lifecycle.viewmodel.compose)
            "implementation"(libs.androidx.activity.compose)
            "implementation"(libs.androidx.lifecycle.runtime.compose)
            "implementation"(libs.androidx.compose.icons.core)
            "implementation"(libs.androidx.compose.icons.extended)
            "implementation"(libs.androidx.compose.material.icons.extended)
            "implementation"(libs.androidx.startup)
            "implementation"(libs.navigation.animations)
            "debugImplementation"(libs.bundles.androidx.compose.ui.tooling)
        }
    }
}