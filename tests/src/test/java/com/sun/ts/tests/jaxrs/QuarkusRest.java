package com.sun.ts.tests.jaxrs;

/**
 * Use this class to keep track of which tests we had to disable for Quarkus-REST
 */
public class QuarkusRest {

    // 3
    public static final String Nuts = "It makes no sense to be something like this even exists...";
    // 4
    public static final String Test_Doesnt_Make_Sense = "The test doesn't seem to be in accordance with the spec says";
    // 5
    public static final String Encoded = "@Encoded in paths not supported yet";
    // 2
    public static final String Underspecified = "Not supported in Quarkus-REST because the spec isn't clear about it";
    // 2
    public static final String Unsupported = "Won't be supported in Quarkus-REST";
    // 6
    public static final String Unsupported_Source = "Won't be supported in Quarkus-REST: Source";
    // 13
    public static final String Unsupported_DataSource = "Won't be supported in Quarkus-REST: DataSource";
    // 17
    public static final String Unsupported_Xml = "XML is not supported yet";
    // 5
    public static final String Unsupported_Streaming_Output = "StreamingOutput is not supported";
    // 2
    public static final String Unsupported_Application_Singletons = "Won't be supported in Quarkus-REST";
    // 9
    public static final String Unsupported_Servlet = "Servlet related features won't be supported in Quarkus-REST";
    // 8
    public static final String Unsupported_Managed_Bean = "@ManagedBean won't be supported in Quarkus-REST";
    // 6
    public static final String Unsupported_Injection_Of_Path_Param_Before_Resource_Locator_Is_Known = "Requires field injection of a path param in a sub-resource locator before the method that defines the path param is known";
    // 1
    public static final String Unsupported_Path_Segment_Parameter_With_Matrix_Params = "Requires PathParam to keep track of which path segment they belong to";
    // 1
    public static final String Not_Implemented_Yet_Get_Set_Entity_Stream = "Not Implemented Yet: get/setEntityStream";
    // 1
    public static final String No_Container = "Not applicable to Quarkus-REST as there is no underlying container";
    // 2
    public static final String BV_Integration_Issue = "Bean Validation Issue (affect quarkus resteasy as well)";
    // 2
    public static final String Caught_At_Build_Time = "In Quarkus, this sort of error is caught at build time";
    // 1
    public static final String Uri_Builder = "Issue with URI Builder that also occurs in RESTEasy";
    // 2
    public static final String Unsupported_Client_Server_Injection_Separation = "Quarkus-REST bug wrt injection we need to solve";
}
