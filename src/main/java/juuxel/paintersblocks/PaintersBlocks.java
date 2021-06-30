package juuxel.paintersblocks;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public final class PaintersBlocks implements ModInitializer {
    public static final String ID = "painters_blocks";

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

    @Override
    public void onInitialize() {

    }
}
