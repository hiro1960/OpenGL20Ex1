package OpenGL20common;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;


import android.opengl.GLES20;

public class VertexBuffer extends GLObject {
	
	 private int vertexBufferId;//頂点バッファID
	 
	//コンストラクタ
	    public VertexBuffer(ArrayList<float[]> vertexs) {
	        float[] vertexArray=new float[8*vertexs.size()];
	        for (int i=0;i<vertexs.size();i++) {
	            float[] vertex=vertexs.get(i);
	            System.arraycopy(vertex,0,vertexArray,8*i,8);
	        }
	        int count=vertexArray.length/8;
	        int[] bufferIds=new int[1];
	        GLES20.glGenBuffers(1,bufferIds,0);
	        vertexBufferId=bufferIds[0];
	        FloatBuffer fb=ByteBuffer.allocateDirect(8*4*count).
	            order(ByteOrder.nativeOrder()).asFloatBuffer();
	        fb.put(vertexArray);
	        fb.position(0);
	        GLES20.glDisable(GLES20.GL_CULL_FACE);
	        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,vertexBufferId);
	        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER,fb.capacity()*4,
	            fb,GLES20.GL_STATIC_DRAW);
	        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,0);
	    }
	    
	    //バインド
	    @Override
	    public void bind() {
	        GLES20.glEnableVertexAttribArray(GLES.positionHandle);
	        GLES20.glEnableVertexAttribArray(GLES.normalHandle);  
	        GLES20.glEnableVertexAttribArray(GLES.uvHandle);      
	        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,vertexBufferId);
	        GLES20.glVertexAttribPointer(GLES.positionHandle,3,
	            GLES20.GL_FLOAT,false,8*4,0);
	        GLES20.glVertexAttribPointer(GLES.uvHandle,2,
	            GLES20.GL_FLOAT,false,8*4,3*4);
	        GLES20.glVertexAttribPointer(GLES.normalHandle,3,
	            GLES20.GL_FLOAT,false,8*4,5*4);
	    }

	    //アンバインド
	    @Override
	    public void unbind() {
	        GLES20.glDisableVertexAttribArray(GLES.positionHandle);
	        GLES20.glDisableVertexAttribArray(GLES.normalHandle);  
	        GLES20.glDisableVertexAttribArray(GLES.uvHandle);      
	        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,0);
	    }

	    //破棄
	    @Override
	    public void dispose() {
	        if (vertexBufferId!=0) {
	            GLES20.glDeleteBuffers(1,new int[]{vertexBufferId},0);
	            vertexBufferId=0;
	        }
	    }

}
