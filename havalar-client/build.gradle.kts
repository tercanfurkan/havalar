plugins {
    id("com.github.node-gradle.node") version "2.2.4"
}

group = "io.havalar"
version = "0.0.1"

tasks["npmInstall"].let {
    it.inputs.files(
        "package.json",
        "package-lock.json",
        "webpack.config.js"
    )

    it.outputs.dir("node_modules")
}

tasks.create<com.moowork.gradle.node.task.NodeTask>("build") {
    dependsOn("npmInstall")

    inputs.dir("$projectDir/src")

    outputs.dir("$projectDir/build")

    // ./node_modules/webpack/bin/webpack.js --config webpack.config.js
    script = File("$projectDir/node_modules/react-scripts/bin/react-scripts.js")
    addArgs("build")
}

tasks.create<Copy>("copy") {
    dependsOn("build")

    inputs.dir("$projectDir/build")

    outputs.dir("${rootProject.projectDir}/web/src/main/resources/static")

    from("build")
    into("${rootProject.projectDir}/havalar-backend/src/main/resources/client")
    include("**/*")
}

tasks.create<Delete>("clean") {
    delete("node_modules", "build")
}