package com.sun.ts.tests.jaxrs;


/**
 * Use this class to keep track of which tests we had to disable for Quarkus-REST (as opposed to RESTEasy)
 */
public class QuarkusRest {

    // nothing hangs anymore YAY!
//    public static final String Hangs = "Quarkus-REST hangs";
    public static final String Nuts = "It makes no sense to be something like this even exists...";
    public static final String Encoded = "@Encoded not supported yet";
    public static final String Underspecified = "Not supported in Quarkus-REST because the spec isn't clear about it";
    public static final String Unsupported = "Won't be supported in Quarkus-REST";
    public static final String Unsupported_Xml = "XML is not supported yet";
    public static final String RESTEasyFailed = "Did not pass for RESTEasy";
    public static final String Unsupported_Streaming_Output = "StreamingOutput is not supported";

}
