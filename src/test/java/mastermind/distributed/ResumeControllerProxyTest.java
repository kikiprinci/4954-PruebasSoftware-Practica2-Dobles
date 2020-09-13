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
public class ResumeControllerProxyTest {

    @Mock
    private Session session;
    @Mock
    private TCPIP tcpip;

    private ResumeControllerProxy resumeControllerProxy;

    public ResumeControllerProxyTest() {
    }

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        this.resumeControllerProxy = new ResumeControllerProxy(this.session, this.tcpip);
    }

    @Test
    public void givenResumeControllerProxyWhenResumeTheInitialState() {
        this.resumeControllerProxy.resume(true);
        verify(tcpip).send(FrameType.NEW_GAME.name());
        verify(tcpip).send(true);

    }

}