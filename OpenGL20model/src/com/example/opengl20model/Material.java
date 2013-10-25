package com.example.opengl20model;

import android.opengl.GLES20;

public class Material extends GLObject {
	
	public Texture texture;              //�e�N�X�`��
    public float[] ambient =new float[4];//����
    public float[] diffuse =new float[4];//�g�U��
    public float[] specular=new float[4];//���ʌ�
    public float   shininess;            //���ʔ��ˊp�x
    
  //�o�C���h
    @Override
    public void bind() {
        GLES20.glUniform4fv(GLES.materialAmbientHandle,1,ambient,0);
        GLES20.glUniform4fv(GLES.materialDiffuseHandle,1,diffuse,0);
        GLES20.glUniform4fv(GLES.materialSpecularHandle,1,specular,0);
        GLES20.glUniform1f(GLES.materialShininessHandle,shininess);
        if (texture!=null) texture.bind();
    }

    //�A���o�C���h
    @Override
    public void unbind() {
        if (texture!=null) texture.unbind();
    }

    //�j��
    @Override
    public void dispose() {
        if (texture!=null) {
            texture.dispose();
            texture=null;
        }
    }

}
