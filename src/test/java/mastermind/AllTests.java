package mastermind;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({
    // mastermind.distributed.LogicProxyTest.class,
    mastermind.distributed.SessionProxyTest.class,
    mastermind.distributed.ResumeControllerProxyTest.class,
    mastermind.distributed.StartControllerProxyTest.class,
    mastermind.distributed.PlayControllerProxyTest.class
})

public class AllTests {
}