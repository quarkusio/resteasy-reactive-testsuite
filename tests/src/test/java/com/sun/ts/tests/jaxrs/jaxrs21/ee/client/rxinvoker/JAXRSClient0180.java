package com.sun.ts.tests.jaxrs.jaxrs21.ee.client.rxinvoker;

import io.quarkus.test.QuarkusUnitTest;
import java.util.function.Supplier;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.extension.RegisterExtension;

public class JAXRSClient0180 extends AbstractJAXRSClient0180 {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest().setFlatClassPath(true)
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.rest.fail-on-duplicate", "false")
            .overrideConfigKey("quarkus.rest.default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_jaxrs21_ee_client_rxinvoker_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.jaxrs21.ee.client.rxinvoker.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.jaxrs21.ee.client.rxinvoker.Resource.class,
                                    com.sun.ts.tests.jaxrs.common.impl.TRACE.class);
                }
            });

    private static final long serialVersionUID = 21L;

    public JAXRSClient0180() {
        setContextRoot("/jaxrs_jaxrs21_ee_client_rxinvoker_web/resource");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0180().run(args);
    }
}
