package org.ferroh.nMIS.types.mannequinSoul;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.NMIS;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.SoulIngredient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.mockStatic;

public class MannequinSoulTest {
    private ServerMock _server;
    private NMIS _plugin;

    private MockedStatic<SoulIngredient> _soulIngredientStaticMock;

    private ItemStack _mockSoulIngredientItem;
    private ItemStack _mockNonSoulIngredientItem;

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
        _soulIngredientStaticMock = mockStatic(SoulIngredient.class);

        _mockSoulIngredientItem = new ItemStack(Material.SAND);
        _soulIngredientStaticMock.when(() -> SoulIngredient.itemStackIsIngredient(_mockSoulIngredientItem)).thenReturn(true);
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();

        if (_soulIngredientStaticMock != null) {
            _soulIngredientStaticMock.close();
        }
    }

    @Test
    public void testMannequinSoulMatrixConstructor_throwWhenMatrixContainsNonIngredientItem() {
        // Arrange


        // Act
        // Assert
    }
}
