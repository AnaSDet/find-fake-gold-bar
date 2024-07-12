package fetch.sdetchallenge.runners;
import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource(value = "featuresForCucumber")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "fetch.sdetchallenge.steps")
@IncludeTags("Test")
public class UiRegressionRunner {
}
