package com.sun.ts.tests.jaxrs.ee.rs.core.application;

import io.quarkus.test.QuarkusUnitTest;
import java.util.function.Supplier;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.extension.RegisterExtension;

public class JAXRSClient0128  extends AbstractJAXRSClient0128 {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest().setFlatClassPath(true)
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.rest.fail-on-duplicate", "false")
            .overrideConfigKey("quarkus.rest.default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_core_application_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.core.application.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.core.application.ApplicationServlet.class,
                                    ApplicationHolderSingleton.class, com.sun.ts.tests.jaxrs.common.util.JaxrsUtil.class);
                }
            });

    public JAXRSClient0128() {
        setContextRoot("/jaxrs_ee_core_application_web/ApplicationTest");
    }

    private static final long serialVersionUID = 1L;

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        JAXRSClient0128 theTests = new JAXRSClient0128();
        theTests.run(args);
    }

}
