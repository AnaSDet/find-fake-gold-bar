package fetch.sdetchallenge.runners;

import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("featuresForCucumber")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME,value ="fetch.sdetchallenge.steps")
@ExcludeTags("Ignore")
@IncludeTags("Smoke")
public class UiSmokeRunner {

    //for the assignment i do not need it, but in general I would have it for a project
}
