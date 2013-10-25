package com.example.opengl20model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import android.opengl.GLES20;

public class IndexBuffer extends GLObject {
	
	private int indexBufferId;  //�C���f�b�N�X�o�b�t�@ID
    private int indexBufferSize;//�C���f�b�N�X�o�b�t�@�T�C�Y
    
  //�R���X�g���N�^
    public IndexBuffer(ArrayList<short[]> indexs) {
        short[] indexArray=new short[3*indexs.size()];
        for (int i=0;i<indexs.size();i++) {
            short[] index=indexs.get(i);
            System.arraycopy(index,0,indexArray,3*i,3);
        }
        int[] bufferIds=new int[1];
        GLES20.glGenBuffers(1,bufferIds,0);
        indexBufferId=bufferIds[0];
        bind();
        ShortBuffer sb=ByteBuffer.allocateDirect(indexArray.length*2).
            order(ByteOrder.nativeOrder()).asShortBuffer();
        sb.put(indexArray);
        sb.position(0);
        indexBufferSize=indexArray.length;
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER,
            sb.capacity()*2,sb,GLES20.GL_STATIC_DRAW);
        unbind();
    }

    //�`��
    public void draw() {
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER,indexBufferId);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES,indexBufferSize,GLES20.GL_UNSIGNED_SHORT,0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    //�o�C���h
    @Override
    public void bind() {
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER,indexBufferId);
    }

    //�A���o�C���h
    @Override
    public void unbind() {
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER,0);
    }
    
    //���
    @Override
    public void dispose() {
        if (indexBufferId!=0) {
            GLES20.glDeleteBuffers(1,new int[]{indexBufferId},0);
            indexBufferId=0;
        }
    }

}
