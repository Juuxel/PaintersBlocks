/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.paintersblocks.loot;

import alexiil.mc.lib.multipart.api.PartLootParams;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.provider.nbt.LootNbtProvider;
import net.minecraft.loot.provider.nbt.LootNbtProviderType;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.JsonSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class PartLootNbtProvider implements LootNbtProvider {
    private static final Set<LootContextParameter<?>> REQUIRED_PARAMETERS = ImmutableSet.of(PartLootParams.BROKEN_PART);

    @Nullable
    @Override
    public NbtElement getNbtTag(LootContext context) {
        PartLootParams.@Nullable BrokenPart part = context.get(PartLootParams.BROKEN_PART);

        if (part != null) {
            return part.getPart().toTag();
        }

        return null;
    }

    @Override
    public Set<LootContextParameter<?>> getRequiredParameters() {
        return REQUIRED_PARAMETERS;
    }

    @Override
    public LootNbtProviderType getType() {
        return PbLoot.PART;
    }

    static final class Serializer implements JsonSerializer<LootNbtProvider> {
        @Override
        public void toJson(JsonObject json, LootNbtProvider object, JsonSerializationContext context) {
        }

        @Override
        public LootNbtProvider fromJson(JsonObject json, JsonDeserializationContext context) {
            return new PartLootNbtProvider();
        }
    }
}
