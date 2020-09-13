package mastermind.distributed;

import mastermind.distributed.dispatchers.FrameType;
import mastermind.distributed.dispatchers.TCPIP;
import mastermind.models.Session;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StartControllerProxyTest {

    @Mock
    private TCPIP tcpip;
    @Mock
    private Session session;

    private StartControllerProxy startControllerProxy;

    public StartControllerProxyTest() {
    }

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        this.startControllerProxy = new StartControllerProxy(session, tcpip);
    }

    @Test
    public void givenStartControllerProxyWhenStartThenStartGame() {
        this.startControllerProxy.start();
        verify(tcpip).send(FrameType.START.name());
    }
}