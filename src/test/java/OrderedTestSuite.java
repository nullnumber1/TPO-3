import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@DisplayName("Ordered Test Suite for gitlab.com")
@SelectClasses({
        EditProfileNavigationTest.class,
        EditProfileSettingTest.class,
        GenerateSSHKeyTest.class,
        CreateRepositoryTest.class,
        CreateTagTest.class,
        CreateFileTest.class,
        NewBranchCreationTest.class,
//        CreateMergeRequest.class

})
class OrderedTestSuite {
}