/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2017
 *
 * This project is licensed under GNU GENERAL PUBLIC LICENSE Version 3.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client.effect.compound;

import hellfirepvp.astralsorcery.client.effect.EntityComplexFX;
import hellfirepvp.astralsorcery.client.util.Blending;
import hellfirepvp.astralsorcery.common.util.data.Vector3;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import org.lwjgl.opengl.GL11;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: CompoundObjectEffect
 * Created by HellFirePvP
 * Date: 16.02.2017 / 16:34
 */
public abstract class CompoundObjectEffect extends EntityComplexFX {

    @Override
    public final void render(float pTicks) {
        GL11.glPushMatrix();
        Tessellator tes = Tessellator.getInstance();
        VertexBuffer vb = tes.getBuffer();
        getGroup().beginDrawing(vb);
        render(vb, pTicks);
        tes.draw();
        GL11.glPopMatrix();
    }

    public abstract ObjectGroup getGroup();

    public abstract void render(VertexBuffer vb, float pTicks);

    public enum ObjectGroup {

        SOLID_COLOR_SPHERE;

        public void beginDrawing(VertexBuffer vb) {
            switch (this) {
                case SOLID_COLOR_SPHERE:
                    vb.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR);
                    break;
            }
        }

        public void prepareGLContext() {
            switch (this) {
                case SOLID_COLOR_SPHERE:
                    GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                    GL11.glEnable(GL11.GL_BLEND);
                    Blending.DEFAULT.apply();
                    GL11.glDisable(GL11.GL_ALPHA_TEST);
                    GL11.glDisable(GL11.GL_CULL_FACE);

                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GL11.glEnable(GL11.GL_COLOR_MATERIAL);
                    break;
            }
        }

        public void revertGLContext() {
            switch (this) {
                case SOLID_COLOR_SPHERE:
                    GL11.glPopAttrib();
                    GL11.glDisable(GL11.GL_COLOR_MATERIAL);
                    break;
            }
        }

    }

}

