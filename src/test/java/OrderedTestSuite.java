import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@DisplayName("Ordered Test Suite for gitlab.com")
@SelectClasses({
        SignInTest.class,
})
class OrderedTestSuite {
}