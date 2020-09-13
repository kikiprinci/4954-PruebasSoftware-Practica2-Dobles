package mastermind.distributed;

import mastermind.distributed.dispatchers.TCPIP;
import mastermind.models.StateValue;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SessionProxyTest {

    @Mock
    private TCPIP tcpip;

    private SessionProxy sessionProxy;

    public SessionProxyTest() {
    }

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        this.sessionProxy = new SessionProxy(this.tcpip);
    }

    @Test
    public void givenSessionProxyWhenGetStateValueThenStartGame() {
        when(tcpip.receiveInt()).thenReturn(0);
        assertEquals(StateValue.INITIAL, this.sessionProxy.getValueState());
    }

    @Test
    public void givenSessionProxyWhenGetStateValueThenFinalGame() {
        when(tcpip.receiveInt()).thenReturn(3);
        assertEquals(StateValue.EXIT, this.sessionProxy.getValueState());
    }

    @Test
    public void givenSessionProxyWhenGetWidthThenHasValue() {
        when(tcpip.receiveInt()).thenReturn(1);
        int res = this.sessionProxy.getWidth();
        assertEquals(res, 1);
    }

}