package mastermind.distributed;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import mastermind.distributed.dispatchers.FrameType;
import mastermind.distributed.dispatchers.TCPIP;
import mastermind.models.Session;
import mastermind.types.Color;
import mastermind.types.Error;

@RunWith(MockitoJUnitRunner.class)
public class PlayControllerProxyTest {

    @Mock
    private TCPIP tcpip;
    @Mock
    private Session session;

    private PlayControllerProxy playControllerProxy;

    public PlayControllerProxyTest() {
    }

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        this.playControllerProxy = new PlayControllerProxy(session, tcpip);
    }

    @Test
    public void givenPlayControllerProxyWhenGetColorsThenReturnColors() {
        when(tcpip.receiveInt()).thenReturn(1);
        when(tcpip.receiveColor()).thenReturn(Color.YELLOW);
        List<Color> res = this.playControllerProxy.getColors(1);
        verify(tcpip).send(1);
        verify(tcpip).send(FrameType.COLORS.name());
        verify(tcpip).receiveColor();

        assertEquals(res.size(), 1);
        assertEquals(res.get(0), Color.YELLOW);
    }

    @Test
    public void givenPlayControllerProxyWhenGetAttempsThenReturnAttemp() {
        when(tcpip.receiveInt()).thenReturn(1);
        int res = this.playControllerProxy.getAttempts();
        verify(tcpip).send(FrameType.ATTEMPTS.name());
        verify(tcpip).receiveInt();
        assertEquals(res, 1);
    }

    @Test
    public void givenPlayControllerProxyWhenGetBlacksThenReturnBlacks() {
        when(tcpip.receiveInt()).thenReturn(1);
        int res = this.playControllerProxy.getBlacks(1);
        verify(tcpip).send(FrameType.BLACKS.name());
        verify(tcpip).receiveInt();
        assertEquals(res, 1);
    }

    @Test
    public void givenPlayControllerProxyWhenGetWhitesThenReturnWhites() {
        when(tcpip.receiveInt()).thenReturn(1);
        int res = this.playControllerProxy.getWhites(1);
        verify(tcpip).send(FrameType.WHITES.name());
        verify(tcpip).receiveInt();
        assertEquals(res, 1);
    }

    @Test
    public void givenPlayControllerProxyWhenIsWinner() {
        when(tcpip.receiveBoolean()).thenReturn(true);
        boolean res = this.playControllerProxy.isWinner();
        verify(tcpip).send(FrameType.WINNER.name());
        verify(tcpip).receiveBoolean();
        assertEquals(res, true);
    }

    @Test
    public void givenPlayControllerProxyWhenIsLooser() {
        when(tcpip.receiveBoolean()).thenReturn(true);
        boolean res = this.playControllerProxy.isLooser();
        verify(tcpip).send(FrameType.LOOSER.name());
        verify(tcpip).receiveBoolean();
        assertEquals(res, true);
    }

    @Test
    public void givenPlayControllerProxyWhenUndoThenUndoAction() {
        this.playControllerProxy.undo();
        verify(tcpip).send(FrameType.UNDO.name());
    }

    @Test
    public void givenPlayControllerProxyWhenRedoThenRedoAction() {
        this.playControllerProxy.redo();
        verify(tcpip).send(FrameType.REDO.name());
    }

    @Test
    public void givenPlayControllerProxyWhenUndoableThenUndoableAction() {
        when(tcpip.receiveBoolean()).thenReturn(true);
        boolean res = this.playControllerProxy.undoable();
        verify(tcpip).send(FrameType.UNDOABLE.name());
        verify(tcpip).receiveBoolean();
        assertEquals(res, true);
    }

    @Test
    public void givenPlayControllerProxyWhenRedoableThenRedoableAction() {
        when(tcpip.receiveBoolean()).thenReturn(true);
        boolean res = this.playControllerProxy.redoable();
        verify(tcpip).send(FrameType.REDOABLE.name());
        verify(tcpip).receiveBoolean();
        assertEquals(res, true);
    }

    @Test
    public void givenPlayControllerProxyWhenaddProposedCombinationOKThenOK() {
        Error res = this.playControllerProxy.addProposedCombination(fillColorsOK());
        assertEquals(res, null);
        verify(tcpip).receiveError();
    }

    @Test
    public void givenPlayControllerProxyWhenaddProposedCombinationKOThenKO() {
        when(tcpip.receiveError()).thenReturn(Error.WRONG_CHARACTERS);
        Error res = this.playControllerProxy.addProposedCombination(fillColorsKO());
        assertEquals(res, Error.WRONG_CHARACTERS);
        verify(tcpip).receiveError();
    }

    @Test
    public void givenPlayControllerProxyWhenaddProposedCombinationDuplicatedThenDuplicated() {
        when(tcpip.receiveError()).thenReturn(Error.DUPLICATED);
        Error res = this.playControllerProxy.addProposedCombination(fillColorDuplicatedColor());
        assertEquals(res, Error.DUPLICATED);
        verify(tcpip).receiveError();
    }

    @Test
    public void givenPlayControllerProxyWhenaddProposedCombinationWrongLentghtThenWrongLength() {
        when(tcpip.receiveError()).thenReturn(Error.WRONG_LENGTH);
        Error res = this.playControllerProxy.addProposedCombination(fillColorsWrongLength());
        assertEquals(res, Error.WRONG_LENGTH);
        verify(tcpip).receiveError();
    }

    private List<Color> fillColorsOK() {
        List<Color> lst = new ArrayList<Color>();
        lst.add(Color.BLUE);
        lst.add(Color.GREEN);
        lst.add(Color.ORANGE);
        lst.add(Color.PURPLE);
        return lst;
    }

    private List<Color> fillColorsKO() {
        List<Color> lst = new ArrayList<Color>();
        lst.add(Color.BLUE);
        lst.add(null);
        lst.add(Color.ORANGE);
        lst.add(Color.PURPLE);
        return lst;
    }

    private List<Color> fillColorsWrongLength() {
        List<Color> lst = new ArrayList<Color>();
        lst.add(Color.BLUE);
        lst.add(Color.GREEN);
        lst.add(Color.ORANGE);
        lst.add(Color.PURPLE);
        lst.add(Color.RED);
        return lst;
    }

    private List<Color> fillColorDuplicatedColor() {
        List<Color> lst = new ArrayList<Color>();
        lst.add(Color.RED);
        lst.add(Color.ORANGE);
        lst.add(Color.PURPLE);
        lst.add(Color.RED);
        return lst;
    }
}