package io.github.joeljeremy.externalizedproperties.samples.git;

import io.github.joeljeremy.externalizedproperties.core.ExternalizedProperties;
import io.github.joeljeremy.externalizedproperties.core.resolvers.ResourceResolver.PropertiesReader;
import io.github.joeljeremy.externalizedproperties.git.GitRepository;
import io.github.joeljeremy.externalizedproperties.git.GitResolver;
import java.nio.file.Paths;

public class Application {
  public static void main(String[] args) {
    System.out.println("Clone Dir: " + System.getProperty("cloneDir"));

    ExternalizedProperties externalizedProperties = buildExternalizedProperties();

    ApplicationProperties applicationProperties =
        externalizedProperties.initialize(ApplicationProperties.class);

    String property = applicationProperties.property();
    System.out.println("property: " + property);
  }

  private static ExternalizedProperties buildExternalizedProperties() {
    return ExternalizedProperties.builder().defaults().resolvers(gitResolver()).build();
  }

  private static GitResolver gitResolver() {
    return GitResolver.builder()
        .gitRepository(gitRepository())
        .resourceFilePath("git/src/main/resources/app.properties")
        // May use other resource reader e.g. json,yaml if resource is in another format.
        .resourceReader(new PropertiesReader())
        .build();
  }

  private static GitRepository gitRepository() {
    return GitRepository.builder()
        .uri("https://github.com/joeljeremy/externalized-properties-samples.git")
        .branch("main")
        .cloneDirectory(Paths.get(System.getProperty("cloneDir")))
        // May provide git credentials.
        // .credentialsProvider(new UsernamePasswordCredentialsProvider("test", "test"))
        // May also provide SSH session factory in case of SSH.
        // .sshSessionFactory(...)
        .build();
  }
}
