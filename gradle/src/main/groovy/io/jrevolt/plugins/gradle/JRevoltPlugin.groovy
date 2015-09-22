package io.jrevolt.plugins.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 */
class JRevoltPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.tasks.getByName("jar").doFirst({ Jar jar ->
            jar.manifest.attributes.put(
                    "Maven-Dependencies",
                    project.configurations.runtime.resolvedConfiguration.resolvedArtifacts.collect {
                        "$it.moduleVersion.id.group:$it.moduleVersion.id.name:$it.moduleVersion.id.version" +
                                (it.type == "jar" && it.classifier == null ? "" : ":$it.type") +
                                (it.classifier ? ":$it.classifier" : "")
                    }.join(",")
            )
        })
    }
}
