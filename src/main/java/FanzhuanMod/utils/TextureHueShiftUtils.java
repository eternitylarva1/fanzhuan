package FanzhuanMod.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class TextureHueShiftUtils {
    private static ShaderProgram shaderProgram;
    private static SpriteBatch batch;
    private static FrameBuffer frameBuffer;

    static {
        String vertexShader = Gdx.files.internal("localization/vertex.glsl").readString();
        String fragmentShader = Gdx.files.internal("localization/fragment.glsl").readString();
        shaderProgram = new ShaderProgram(vertexShader, fragmentShader);
        if (!shaderProgram.isCompiled()) {
            Gdx.app.error("Shader", shaderProgram.getLog());
        }
        batch = new SpriteBatch();
    }

    public static TextureAtlas.AtlasRegion shiftHue180(Texture inputTexture) {
        int width = inputTexture.getWidth();
        int height = inputTexture.getHeight();

        if (frameBuffer == null || frameBuffer.getWidth() != width || frameBuffer.getHeight() != height) {
            if (frameBuffer != null) {
                frameBuffer.dispose();
            }
            frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
        }

        frameBuffer.begin();

        Gdx.gl.glClearColor(0, 0, 0, 0); // 透明背景
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, width, height);

        batch.setShader(shaderProgram);
        batch.begin();
        shaderProgram.setUniformf("hueShift", 0.5f); // 180度偏移
        batch.draw(inputTexture, 0, 0, width, height, 0, 0, width, height, false, false); // 避免纹理翻转
        batch.end();

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        Gdx.gl.glPixelStorei(GL20.GL_PACK_ALIGNMENT, 1);
        Gdx.gl.glReadPixels(0, 0, width, height, GL20.GL_RGBA, GL20.GL_UNSIGNED_BYTE, pixmap.getPixels());

        frameBuffer.end();

        Texture outputTexture = new Texture(pixmap);
        pixmap.dispose();

        return new TextureAtlas.AtlasRegion(outputTexture, 0, 0, width, height);
    }

    public static void dispose() {
        shaderProgram.dispose();
        batch.dispose();
        if (frameBuffer != null) {
            frameBuffer.dispose();
        }
    }
}