package data_management;

import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.data_management.DataStorage;
import com.data_management.WebSocketClientImpl;

import java.net.URI;
import java.net.URISyntaxException;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class WebSocketClientImplTest {

    private WebSocketClientImpl client;
    private DataStorage dataStorage;

    @BeforeEach
    public void setUp() throws URISyntaxException {
        dataStorage = Mockito.mock(DataStorage.class);
        client = new WebSocketClientImpl(new URI("ws://localhost:8080"), dataStorage);
    }

    @Test
    public void testOnOpen() {
        ServerHandshake handshake = mock(ServerHandshake.class);
        client.onOpen(handshake);
        // No assertions needed for onOpen as it just prints a statement
    }

    @Test
    public void testOnMessage() {
        String message = "1,1627861234567,heart_rate,72.5";
        client.onMessage(message);
        verify(dataStorage, times(1)).addPatientData(1, 72.5, "heart_rate", 1627861234567L);
    }

    @Test
    public void testOnMessageWithInvalidData() {
        String message = "invalid_data";
        client.onMessage(message);
        verify(dataStorage, never()).addPatientData(anyInt(), anyDouble(), anyString(), anyLong());
    }

    @Test
    public void testOnClose() {
        client.onClose(1000, "Normal closure", true);
     
    }

    @Test
    public void testOnError() {
        Exception ex = new Exception("Test exception");
        client.onError(ex);
       
    }
}
