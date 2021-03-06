import chopper.Chopper
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

/**
 *
 * @author Konstantin Krivopustov
 */
class CreateMultiPageDoc extends DefaultTask {

    String docName
    String docLang
    String docTitle

    @InputDirectory
    File getSrcDir() {
        return new File("${project.buildDir}/$docName/$docLang/html-single")
    }

    @OutputDirectory
    File getDstDir() {
        return new File("${project.buildDir}/$docName/$docLang/html")
    }

    @TaskAction
    def createMultiPageDoc() {

        def chopper = new Chopper(
                "${srcDir}/${docName}.html",
                dstDir.absolutePath,
                "${project.rootDir}/tools/chopper",
                docLang == 'en' ? '' : docLang,
                ['docName': docName]
        )
        chopper.process()

        project.copy {
            from "${project.rootDir}/buildSrc/build/classes/java/main/chopper/server"
            into "${dstDir}/WEB-INF/classes/chopper/server"
        }

        project.configurations.chopper.files.each { dep ->
            project.copy {
                from dep.absolutePath
                into "${dstDir}/WEB-INF/lib"
            }
        }
    }
}
