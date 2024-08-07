import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
	alias(opensavvyConventions.plugins.base)
	alias(opensavvyConventions.plugins.kotlin.library)
	alias(opensavvyConventions.plugins.aligned.kotest)
}

@OptIn(ExperimentalWasmDsl::class)
kotlin {
	jvm()
	js {
		browser()
		nodejs()
	}
	linuxX64()
	iosArm64()
	iosSimulatorArm64()
	iosX64()
	wasmJs {
		browser()
		nodejs()
	}

	sourceSets.commonTest.dependencies {
		implementation(libs.prepared.kotest)
	}
}

library {
	name.set("Material You color generator")
	description.set("Port of the Material You color generation algorithm for Kotlin")
	homeUrl.set("https://gitlab.com/opensavvy/ui/material-you-algorithm")

	license.set {
		name.set("Apache 2.0")
		url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
	}
}
