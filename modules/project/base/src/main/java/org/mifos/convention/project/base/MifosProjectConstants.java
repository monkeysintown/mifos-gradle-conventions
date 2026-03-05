package org.mifos.convention.project.base;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public final class MifosProjectConstants {
    private MifosProjectConstants() {}

    public static final String MIFOS_EMPTY = "";

    public static final String MIFOS_PREFIX = "mifos.";

    public static final String MIFOS_DEVELOPERS_PROPERTY_PREFIX = MIFOS_PREFIX + "developers.";
    public static final String MIFOS_DEVELOPERS_PROPERTY_USERNAME_TEMPLATE = MIFOS_DEVELOPERS_PROPERTY_PREFIX + "%d.username";
    public static final String MIFOS_DEVELOPERS_PROPERTY_EMAIL_TEMPLATE = MIFOS_DEVELOPERS_PROPERTY_PREFIX + "%d.email";
    public static final String MIFOS_DEVELOPERS_PROPERTY_FIRSTNAME_TEMPLATE = MIFOS_DEVELOPERS_PROPERTY_PREFIX + "%d.firstname";
    public static final String MIFOS_DEVELOPERS_PROPERTY_LASTNAME_TEMPLATE = MIFOS_DEVELOPERS_PROPERTY_PREFIX + "%d.lastname";
    public static final String MIFOS_DEVELOPERS_PROPERTY_TIMEZONE_TEMPLATE = MIFOS_DEVELOPERS_PROPERTY_PREFIX + "%d.timezone";
    public static final String MIFOS_DEVELOPERS_PROPERTY_URL_TEMPLATE = MIFOS_DEVELOPERS_PROPERTY_PREFIX + "%d.url";
    public static final String MIFOS_DEVELOPERS_PROPERTY_ORGANISATION_TEMPLATE = MIFOS_DEVELOPERS_PROPERTY_PREFIX + "%d.organisation";
    public static final String MIFOS_DEVELOPERS_PROPERTY_ORGANISATION_URL_TEMPLATE = MIFOS_DEVELOPERS_PROPERTY_PREFIX + "%d.organisationUrl";
    public static final String MIFOS_DEVELOPERS_PROPERTY_ROLES_TEMPLATE = MIFOS_DEVELOPERS_PROPERTY_PREFIX + "%d.roles";
    public static final String MIFOS_DEVELOPERS_PROPERTY_GPG_PUBLIC_KEY_TEMPLATE = MIFOS_DEVELOPERS_PROPERTY_PREFIX + "%d.gpgPublicKey";

    public static final String MIFOS_PROJECTS_PROPERTY_PREFIX = MIFOS_PREFIX + "projects.";
    public static final String MIFOS_PROJECTS_PROPERTY_ID_TEMPLATE = MIFOS_PROJECTS_PROPERTY_PREFIX + "%d.id";
    public static final String MIFOS_PROJECTS_PROPERTY_GROUP_ID_TEMPLATE = MIFOS_PROJECTS_PROPERTY_PREFIX + "%d.group.id";
    public static final String MIFOS_PROJECTS_PROPERTY_CLASSIFIER_TEMPLATE = MIFOS_PROJECTS_PROPERTY_PREFIX + "%d.classifier";
    public static final String MIFOS_PROJECTS_PROPERTY_NAME_TEMPLATE = MIFOS_PROJECTS_PROPERTY_PREFIX + "%d.name";
    public static final String MIFOS_PROJECTS_PROPERTY_DESCRIPTION_TEMPLATE = MIFOS_PROJECTS_PROPERTY_PREFIX + "%d.description";
    public static final String MIFOS_PROJECTS_PROPERTY_BASE_PACKAGE_TEMPLATE = MIFOS_PROJECTS_PROPERTY_PREFIX + "%d.base.package";
    public static final String MIFOS_PROJECTS_PROPERTY_BASE_PATH_TEMPLATE = MIFOS_PROJECTS_PROPERTY_PREFIX + "%d.base.path";
    public static final String MIFOS_PROJECTS_PROPERTY_MODULES_PATH_TEMPLATE = MIFOS_PROJECTS_PROPERTY_PREFIX + "%d.modules.path";
    public static final String MIFOS_PROJECTS_PROPERTY_STORAGE_TEMPLATE = MIFOS_PROJECTS_PROPERTY_PREFIX + "%d.storage";
    public static final String MIFOS_PROJECTS_PROPERTY_LICENSE_NAME_TEMPLATE = MIFOS_PROJECTS_PROPERTY_PREFIX + "%d.license.name";
    public static final String MIFOS_PROJECTS_PROPERTY_LICENSE_URL_TEMPLATE = MIFOS_PROJECTS_PROPERTY_PREFIX + "%d.license.link";
    public static final String MIFOS_PROJECTS_PROPERTY_HOMEPAGE_TEMPLATE = MIFOS_PROJECTS_PROPERTY_PREFIX + "%d.homepage";
    public static final String MIFOS_PROJECTS_PROPERTY_BUG_TRACKER_TEMPLATE = MIFOS_PROJECTS_PROPERTY_PREFIX + "%d.bug.tracker";
    public static final String MIFOS_PROJECTS_PROPERTY_CONTACT_TEMPLATE = MIFOS_PROJECTS_PROPERTY_PREFIX + "%d.contact";
    public static final String MIFOS_PROJECTS_PROPERTY_INCEPTION_YEAR_TEMPLATE = MIFOS_PROJECTS_PROPERTY_PREFIX + "%d.inception.year";
    public static final String MIFOS_PROJECTS_PROPERTY_VENDOR_TEMPLATE = MIFOS_PROJECTS_PROPERTY_PREFIX + "%d.vendor";
    public static final String MIFOS_PROJECTS_PROPERTY_COPYRIGHT_TEMPLATE = MIFOS_PROJECTS_PROPERTY_PREFIX + "%d.copyright";

    public static final String MIFOS_PROJECTS_PROPERTY_SCM_URL = MIFOS_PROJECTS_PROPERTY_PREFIX + "scm.url";
    public static final String MIFOS_PROJECTS_PROPERTY_SCM_CONNECTION_MAIN = MIFOS_PROJECTS_PROPERTY_PREFIX + "scm.connection.main";
    public static final String MIFOS_PROJECTS_PROPERTY_SCM_CONNECTION_DEVELOPER = MIFOS_PROJECTS_PROPERTY_PREFIX + "scm.connection.developer";
    public static final String MIFOS_PROJECTS_PROPERTY_CONTRIBUTE = MIFOS_PROJECTS_PROPERTY_PREFIX + "contribute";
    public static final String MIFOS_PROJECTS_PROPERTY_DOCUMENTATION = MIFOS_PROJECTS_PROPERTY_PREFIX + "documentation";
    public static final String MIFOS_PROJECTS_PROPERTY_DONATION = MIFOS_PROJECTS_PROPERTY_PREFIX + "donation";
    public static final String MIFOS_PROJECTS_PROPERTY_FAQ = MIFOS_PROJECTS_PROPERTY_PREFIX + "faq";
    public static final String MIFOS_PROJECTS_PROPERTY_HELP = MIFOS_PROJECTS_PROPERTY_PREFIX + "help";
    public static final String MIFOS_PROJECTS_PROPERTY_TRANSLATE = MIFOS_PROJECTS_PROPERTY_PREFIX + "translate";

    public static final String MIFOS_PROJECTS_CURRENT_PROPERTY_ID = MIFOS_PROJECTS_PROPERTY_PREFIX + "id";
    public static final String MIFOS_PROJECTS_CURRENT_PROPERTY_GROUP_ID = MIFOS_PROJECTS_PROPERTY_PREFIX + "group.id";
    public static final String MIFOS_PROJECTS_CURRENT_PROPERTY_CLASSIFIER = MIFOS_PROJECTS_PROPERTY_PREFIX + "classifier";
    public static final String MIFOS_PROJECTS_CURRENT_PROPERTY_NAME = MIFOS_PROJECTS_PROPERTY_PREFIX + "name";
    public static final String MIFOS_PROJECTS_CURRENT_PROPERTY_DESCRIPTION = MIFOS_PROJECTS_PROPERTY_PREFIX + "description";
    public static final String MIFOS_PROJECTS_CURRENT_PROPERTY_BASE_PACKAGE = MIFOS_PROJECTS_PROPERTY_PREFIX + "base.package";
    public static final String MIFOS_PROJECTS_CURRENT_PROPERTY_BASE_PATH = MIFOS_PROJECTS_PROPERTY_PREFIX + "base.path";
    public static final String MIFOS_PROJECTS_CURRENT_PROPERTY_MODULES_PATH = MIFOS_PROJECTS_PROPERTY_PREFIX + "modules.path";
    public static final String MIFOS_PROJECTS_CURRENT_PROPERTY_STORAGE = MIFOS_PROJECTS_PROPERTY_PREFIX + "storage";
    public static final String MIFOS_PROJECTS_CURRENT_PROPERTY_LICENSE_NAME = MIFOS_PROJECTS_PROPERTY_PREFIX + "license.name";
    public static final String MIFOS_PROJECTS_CURRENT_PROPERTY_LICENSE_URL = MIFOS_PROJECTS_PROPERTY_PREFIX + "license.link";
    public static final String MIFOS_PROJECTS_CURRENT_PROPERTY_HOMEPAGE = MIFOS_PROJECTS_PROPERTY_PREFIX + "homepage";
    public static final String MIFOS_PROJECTS_CURRENT_PROPERTY_BUG_TRACKER = MIFOS_PROJECTS_PROPERTY_PREFIX + "bug.tracker";
    public static final String MIFOS_PROJECTS_CURRENT_PROPERTY_CONTACT = MIFOS_PROJECTS_PROPERTY_PREFIX + "contact";
    public static final String MIFOS_PROJECTS_CURRENT_PROPERTY_INCEPTION_YEAR = MIFOS_PROJECTS_PROPERTY_PREFIX + "inception.year";
    public static final String MIFOS_PROJECTS_CURRENT_PROPERTY_VENDOR = MIFOS_PROJECTS_PROPERTY_PREFIX + "vendor";
    public static final String MIFOS_PROJECTS_CURRENT_PROPERTY_COPYRIGHT = MIFOS_PROJECTS_PROPERTY_PREFIX + "copyright";
    public static final String MIFOS_PROJECTS_CURRENT_PROPERTY_SCM_URL = MIFOS_PROJECTS_PROPERTY_PREFIX + "scm.url";
    public static final String MIFOS_PROJECTS_CURRENT_PROPERTY_SCM_CONNECTION_MAIN = MIFOS_PROJECTS_PROPERTY_PREFIX + "scm.connection.main";
    public static final String MIFOS_PROJECTS_CURRENT_PROPERTY_SCM_CONNECTION_DEVELOPER = MIFOS_PROJECTS_PROPERTY_PREFIX + "scm.connection.developer";

    public static final String MIFOS_PROJECTS_CURRENT_PROPERTY_MODULE = MIFOS_PROJECTS_PROPERTY_PREFIX + "module";
    public static final String MIFOS_PROJECTS_CURRENT_PROPERTY_APPLICATION_MAIN = MIFOS_PROJECTS_PROPERTY_PREFIX + "application.main";

    public static final String MIFOS_PROJECT_DEFAULT_MODULE_FOLDER = "modules";
    public static final String MIFOS_PROJECT_DEFAULT_CLASSIFIER = "imperative";
    public static final String MIFOS_PROJECT_DEFAULT_ID = "mifos-unknown";
    public static final String MIFOS_PROJECT_DEFAULT_GROUP_ID = "org.mifos";
    public static final String MIFOS_PROJECT_DEFAULT_DESCRIPTION = "A Mifos Initiative Project";
    public static final String MIFOS_PROJECT_DEFAULT_BASE_PACKAGE = "org.mifos.uknown";
    public static final String MIFOS_PROJECT_DEFAULT_STORAGE = "imperative";
    public static final String MIFOS_PROJECT_DEFAULT_LICENSE_NAME = "MPL-2.0";
    public static final String MIFOS_PROJECT_DEFAULT_LICENSE_URL = "https://spdx.org/licenses/MPL-2.0.html";
    public static final String MIFOS_PROJECT_DEFAULT_HOMEPAGE = "https://mifos.org";
    public static final String MIFOS_PROJECT_DEFAULT_BUG_TRACKER = "https://mifosforge.jira.com";
    public static final String MIFOS_PROJECT_DEFAULT_CONTACT = "https://mifos.org/about-us/contact-us";
    public static final String MIFOS_PROJECT_DEFAULT_INCEPTION_YEAR = LocalDate.now().getYear() + "";
    public static final String MIFOS_PROJECT_DEFAULT_VENDOR = "Mifos Initiative";
    public static final String MIFOS_PROJECT_DEFAULT_COPYRIGHT = "Copyright (c) %s Mifos Initiative".formatted(MIFOS_PROJECT_DEFAULT_INCEPTION_YEAR);
    public static final String MIFOS_PROJECT_DEFAULT_SCM_URL = MIFOS_PROJECT_DEFAULT_HOMEPAGE;
    public static final String MIFOS_PROJECT_DEFAULT_SCM_CONNECTION_MAIN = "scm:git:git://github.com:openmf/unknown.git";
    public static final String MIFOS_PROJECT_DEFAULT_SCM_CONNECTION_DEVELOPER = MIFOS_PROJECT_DEFAULT_SCM_CONNECTION_MAIN;
    public static final String MIFOS_PROJECT_DEFAULT_CONTRIBUTE = MIFOS_PROJECT_DEFAULT_HOMEPAGE;
    public static final String MIFOS_PROJECT_DEFAULT_DOCUMENTATION = MIFOS_PROJECT_DEFAULT_HOMEPAGE;
    public static final String MIFOS_PROJECT_DEFAULT_DONATION = MIFOS_PROJECT_DEFAULT_HOMEPAGE;
    public static final String MIFOS_PROJECT_DEFAULT_FAQ = MIFOS_PROJECT_DEFAULT_HOMEPAGE;
    public static final String MIFOS_PROJECT_DEFAULT_HELP = MIFOS_PROJECT_DEFAULT_HOMEPAGE;
    public static final String MIFOS_PROJECT_DEFAULT_TRANSLATE = MIFOS_PROJECT_DEFAULT_HOMEPAGE;

    public static final int MIFOS_DEVELOPER_MAX_SIZE = 100;
    public static final int MIFOS_PROJECT_MAX_SIZE = 100;

    public static final String GRADLE_CONFIGURATION_IMPLEMENTATION = "implementation";
    public static final String GRADLE_CONFIGURATION_API = "api";
    public static final String GRADLE_CONFIGURATION_COMPILE_ONLY = "compileOnly";

    public static final List<String> MIFOS_PROJECT_BOOT_MODULES = List.of("core", "mapping", "databind");
    public static final List<String> MIFOS_PROJECT_BOOT_STARTER_MODULES = List.of("starter");
    public static final List<String> MIFOS_PROJECT_BOOT_PERSISTENCE_MODULES = List.of("relational", "jpa", "mongo", "redis", "cassandra", "couchbase");
    public static final List<String> MIFOS_PROJECT_BOOT_TRANSPORT_MODULES = List.of("rest", "grpc");
    public static final List<String> MIFOS_PROJECT_BOOT_USECASE_MODULES = List.of("core", "implementation", "mapping");
    public static final List<String> MIFOS_PROJECT_BOOT_SERVICE_MODULES = List.of("core", "implementation", "mapping");
    public static final List<String> MIFOS_PROJECT_BOOT_APPLICATION_MODULES = List.of("cli", "rest", "migration", "bff", "vaadin", "jsf");

    public static final Pattern MIFOS_PROJECT_MODULE_DOC_PATTERN = Pattern.compile("^.*/modules/(.*)/doc/build.gradle$", Pattern.CASE_INSENSITIVE);
    public static final Pattern MIFOS_PROJECT_BOOT_MODULE_PATTERN = Pattern.compile("^.*/modules/(.*)/(core|mapping|databind|starter)/build.gradle$", Pattern.CASE_INSENSITIVE);
    public static final Pattern MIFOS_PROJECT_BOOT_MODULE_SDK_PATTERN = Pattern.compile("^.*/modules/(.*)/sdk/(.*)/build.gradle$", Pattern.CASE_INSENSITIVE);
    public static final Pattern MIFOS_PROJECT_BOOT_MODULE_SUPPORT_PATTERN = Pattern.compile("^.*/modules/(.*)/support/(.*)/build.gradle$", Pattern.CASE_INSENSITIVE);
    public static final Pattern MIFOS_PROJECT_BOOT_MODULE_PERSISTENCE_PATTERN = Pattern.compile("^.*/modules/(.*)/persistence/(relational|jpa|mongo|cassandra|couchbase)/build.gradle$", Pattern.CASE_INSENSITIVE);
    public static final Pattern MIFOS_PROJECT_BOOT_MODULE_SERVICE_PATTERN = Pattern.compile("^.*/modules/(.*)/service/(.*)/(core|implementation|mapping)/build.gradle$", Pattern.CASE_INSENSITIVE);
    public static final Pattern MIFOS_PROJECT_BOOT_MODULE_USECASE_PATTERN = Pattern.compile("^.*/modules/(.*)/usecase/(.*)/(core|implementation|mapping)/build.gradle$", Pattern.CASE_INSENSITIVE);
    public static final Pattern MIFOS_PROJECT_BOOT_MODULE_TRANSPORT_PATTERN = Pattern.compile("^.*/modules/(.*)/transport/(rest|grpc|rsocket)/build.gradle$", Pattern.CASE_INSENSITIVE);
    public static final Pattern MIFOS_PROJECT_BOOT_MODULE_STARTER_PATTERN = Pattern.compile("^.*/modules/(.*)/starter/build.gradle$", Pattern.CASE_INSENSITIVE);
    public static final Pattern MIFOS_PROJECT_BOOT_MODULE_APPLICATION_PATTERN = Pattern.compile("^.*/modules/(.*)/application/(cli|rest|migration|bff|vaadin|jsf)/build.gradle$", Pattern.CASE_INSENSITIVE);
}
