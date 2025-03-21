package com.sun.ts.tests.jaxrs.ee.rs.formparam;

import io.quarkus.test.QuarkusUnitTest;
import java.util.function.Supplier;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.extension.RegisterExtension;

public class JAXRSClient0146 extends AbstractJAXRSClient0146 {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest().setFlatClassPath(true)
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.rest.fail-on-duplicate", "false")
            .overrideConfigKey("quarkus.rest.default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_formparam_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.formparam.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.WebApplicationExceptionMapper.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithValueOf.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityThrowingExceptionGivenByName.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithFromString.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityThrowingWebApplicationException.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithConstructor.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.formparam.FormParamTest.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityPrototype.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.RuntimeExceptionMapper.class);
                }
            });

    public JAXRSClient0146() {
        setContextRoot("/jaxrs_ee_formparam_web/FormParamTest");
    }

    private static final long serialVersionUID = 1L;

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        JAXRSClient0146 theTests = new JAXRSClient0146();
        theTests.run(args);
    }

}
