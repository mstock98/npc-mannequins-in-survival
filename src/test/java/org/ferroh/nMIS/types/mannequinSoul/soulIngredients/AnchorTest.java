package org.ferroh.nMIS.types.mannequinSoul.soulIngredients;

import org.ferroh.nMIS.NMIS;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

public class AnchorTest {
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

    /*@Test
    public void testConstructor_throwWhenItemStackNull() {
        try {
            new Anchor(null); // Arrange & Act
        } catch (IllegalArgumentException e) { // Assert
            return;
        }
        Assertions.fail();
    }*/
}
