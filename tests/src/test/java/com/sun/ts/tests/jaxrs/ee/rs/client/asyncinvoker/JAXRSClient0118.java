package com.sun.ts.tests.jaxrs.ee.rs.client.asyncinvoker;

import io.quarkus.test.QuarkusUnitTest;
import java.util.function.Supplier;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.extension.RegisterExtension;



public class JAXRSClient0118 extends AbstractJAXRSClient0118 {

    private static final long serialVersionUID = -696868584437674095L;

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest().setFlatClassPath(true)
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.rest.fail-on-duplicate", "false")
            .overrideConfigKey("quarkus.rest.default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_rs_client_asyncinvoker_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.client.asyncinvoker.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.client.asyncinvoker.Resource.class,
                                    com.sun.ts.tests.jaxrs.common.provider.StringBean.class,
                                    com.sun.ts.tests.jaxrs.common.impl.TRACE.class);
                }
            });

    public JAXRSClient0118() {
        setContextRoot("jaxrs_ee_rs_client_asyncinvoker_web/resource");
    }

    public static void main(String[] args) {
        new JAXRSClient0118().run(args);
    }

}
