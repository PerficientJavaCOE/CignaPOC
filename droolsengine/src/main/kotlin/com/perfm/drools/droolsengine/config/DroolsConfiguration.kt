package com.perfm.drools.droolsengine.config

import org.kie.api.KieServices
import org.kie.api.runtime.KieContainer
import org.kie.internal.io.ResourceFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import java.io.File

@Configuration
@ComponentScan("com.perfm.drools.droolsengine.config")
class DroolsConfiguration {

    @Bean
    fun kieContainer(): KieContainer? {
        val kieServices = KieServices.Factory.get() // creates a instance
        val kieFileSystem = kieServices.newKieFileSystem() // to create memory file system provided by framework for drools
        val dir = File("droolsengine/src/main/resources")
        val directoryListing: Array<File> = dir.listFiles()
        if (directoryListing != null) {
            for(child in directoryListing)
                kieFileSystem.write(ResourceFactory.newClassPathResource(child.name))
        }
        val kieBuilder = kieServices.newKieBuilder(kieFileSystem)
        kieBuilder.buildAll() //builds all resource to kiebase
        val kieModule = kieBuilder.kieModule
        return kieServices.newKieContainer(kieModule.releaseId)
    }
}