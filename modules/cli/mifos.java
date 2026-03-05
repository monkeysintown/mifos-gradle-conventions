/// usr/bin/env jbang "$0" "$@" ; exit $?

//DEPS org.projectlombok:lombok:1.18.46
//DEPS org.slf4j:slf4j-simple:2.0.17
//DEPS io.pebbletemplates:pebble:4.1.1
//DEPS tools.jackson.dataformat:jackson-dataformat-yaml:3.1.3
//DEPS org.apache.maven.resolver:maven-resolver-api:2.0.18
//DEPS org.apache.maven.resolver:maven-resolver-spi:2.0.18
//DEPS org.apache.maven.resolver:maven-resolver-tools:2.0.18
//DEPS org.apache.maven.resolver:maven-resolver-impl:2.0.18
//DEPS org.apache.maven.resolver:maven-resolver-supplier-mvn3:2.0.18
//DEPS com.google.jimfs:jimfs:1.3.0

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession.SessionBuilder;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.resolution.VersionRangeRequest;
import org.eclipse.aether.util.graph.visitor.DependencyGraphDumper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.extern.slf4j.Slf4j;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
class MifosTemplate {
    private String templateId;
    private String groupId;
    private String description;
    private String author;
    private Map<String, MifosTemplateParameter> parameters;
    private String layout;
    private Map<String, MifosTemplateModule> modules;
}

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
class MifosTemplateModule {
    private String name;
    private Map<String, MifosTemplateParameter> parameters;
}

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
class MifosTemplateParameter {
    private String name;
    private String prompt;
    private String type;
    private String value;
}

@Slf4j
class mifos {
    public static final DependencyGraphDumper DUMPER_SOUT = new DependencyGraphDumper(System.out::println);

    public static void main(String... args) {
        log.info("Hello World");

        try (var system = newRepositorySystem("default");
                var session = newRepositorySystemSession(system, "default")
                        .build()) {
            Artifact artifact = new DefaultArtifact("org.apache.maven.resolver:maven-resolver-util:[0,)");

            var rangeRequest = new VersionRangeRequest();
            rangeRequest.setArtifact(artifact);
            rangeRequest.setRepositories(newRepositories(system, session));

            var rangeResult = system.resolveVersionRange(session, rangeRequest);

            var newestVersion = rangeResult.getHighestVersion();

            log.info(
                    "Newest version " + newestVersion + " from repository " + rangeResult.getRepository(newestVersion));
        }
    }

    static RepositorySystem newRepositorySystem(final String factory) {
        log.info("Using factory: " + factory);
        
        return SupplierRepositorySystemFactory.newRepositorySystem();
    }

    static SessionBuilder newRepositorySystemSession(RepositorySystem system, String fs) {
        log.info("Using FS: " + fs);

        boolean close;

        Path localRepository = Path.of("build/repository");

        var result = new SessionBuilderSupplier(system)
                .get()
                .withLocalRepositoryBaseDirectories(localRepository)
                .setRepositoryListener(new ConsoleRepositoryListener())
                .setTransferListener(new ConsoleTransferListener())
                .setConfigProperty("aether.generator.gpg.enabled", Boolean.TRUE.toString())
                .setConfigProperty(
                        "aether.generator.gpg.keyFilePath",
                        Paths.get("src/main/resources/alice.key")
                                .toAbsolutePath()
                                .toString());

            result.addOnSessionEndedHandler(() -> {
                try {
                    localRepository.getFileSystem().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            return result;
    }
}
