package org.ferroh.nMIS;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

public class TemplateTest {
    private ServerMock _server;
    private NMIS _plugin;

    @BeforeEach
    public void setUp() {
        _server = MockBukkit.mock();
        setUpBeforePluginLoad();
        _plugin = MockBukkit.load(NMIS.class);
        setUpAfterPluginLoad();
    }

    private void setUpBeforePluginLoad() {

    }

    private void setUpAfterPluginLoad() {

    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }
}