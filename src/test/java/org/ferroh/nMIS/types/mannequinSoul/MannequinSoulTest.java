package org.ferroh.nMIS.types.mannequinSoul;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.ferroh.nMIS.NMIS;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.SoulIngredient;
import org.ferroh.nMIS.types.mannequinSoul.soulIngredients.SoulStarter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.mockStatic;

public class MannequinSoulTest {
    /*private ServerMock _server;
    private NMIS _plugin;

    private MockedStatic<SoulIngredient> _soulIngredientStaticMock;

    private ItemStack _mockSoulStarterItem;
    private ItemStack _mockSkinItem;
    private ItemStack _mockHealthBuffItem;
    private ItemStack _mockAnchorItem;
    private ItemStack _mockNonSoulIngredientItem;

    private ItemStack[] _mockCraftingMatrix;

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

        _mockSoulStarterItem = new ItemStack(Material.SAND);
        _soulIngredientStaticMock.when(() -> SoulIngredient.itemStackIsIngredient(_mockSoulStarterItem)).thenReturn(true);

        _mockSkinItem = new ItemStack(Material.NETHERITE_SWORD);
        _soulIngredientStaticMock.when(() -> SoulIngredient.itemStackIsIngredient(_mockSkinItem)).thenReturn(true);

        _mockHealthBuffItem = new ItemStack(Material.MELON);
        _soulIngredientStaticMock.when(() -> SoulIngredient.itemStackIsIngredient(_mockHealthBuffItem)).thenReturn(true);

        _mockAnchorItem = new ItemStack(Material.STONE);
        _soulIngredientStaticMock.when(() -> SoulIngredient.itemStackIsIngredient(_mockAnchorItem)).thenReturn(true);
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

        try (MockedConstruction<SoulStarter> mocked = mockConstruction(SoulStarter.class)) {
            _mockCraftingMatrix = new ItemStack[]{_mockSoulStarterItem};

            try {
                new MannequinSoul(_mockCraftingMatrix);
            } catch (IllegalArgumentException e) {
                Assertions.fail();
            }
        }

        // Act
        // Assert
    }*/
}
