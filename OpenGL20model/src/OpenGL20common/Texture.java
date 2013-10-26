package OpenGL20common;

import java.io.IOException;
import java.io.InputStream;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

//テクスチャ
public class Texture extends GLObject {
	
	public int textureId;//テクスチャID
    public int width;    //幅
    public int height;   //高さ
    
  //バインド
    @Override
    public void bind() {
        if (textureId!=0) {
            GLES20.glUniform1i(GLES.useTexHandle,1);
            GLES20.glEnable(GLES20.GL_TEXTURE_2D);
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureId);
            GLES20.glUniform1i(GLES.texHandle,0);
        }
    }

    //アンバインド
    @Override
    public void unbind() {
        GLES20.glDisable(GLES20.GL_TEXTURE_2D);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);
        GLES20.glUniform1i(GLES.useTexHandle,0);
    }
    
    //解放
    @Override
    public void dispose() {
        if (textureId!=0) {
            GLES20.glDeleteTextures(0,new int[]{textureId},0);
            textureId=0;
        }
    }
    
    //テクスチャの生成
    public static Texture createInstance(Bitmap bmp) {
        Texture result=new Texture();
        int[] bufferIds=new int[1];
        GLES20.glGenTextures(1,bufferIds,0);
        result.textureId=bufferIds[0];
        result.width=bmp.getWidth();
        result.height=bmp.getHeight();
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,result.textureId);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,bmp,0);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_NEAREST);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);
        return result;
    }
    
    //テクスチャの生成
    public static Texture createTextureFromAsset(
        String assetFileName) throws IOException {
        InputStream in=GLES.context.getAssets().open(assetFileName);
        Bitmap bmp=BitmapFactory.decodeStream(in);
        Texture result=Texture.createInstance(bmp);
        bmp.recycle();
        in.close();
        return result;
    }

}
