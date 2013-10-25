package com.example.opengl20model;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.Matrix;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

//�����_���[
public class GLRenderer implements GLSurfaceView.Renderer {

	//�V�X�e��
    private float aspect;//�A�X�y�N�g��
    private int   angle; //��]�p�x
        
    //���f��
    private Object3D model=new Object3D();
    
    //�R���X�g���N�^
    public GLRenderer(Context context) {
        GLES.context=context;
    }


    /*
     * setRender(GLSurfaceView.Renderer)�����s���邱�Ƃɂ��A����I�ɌĂяo�����
     */
	@Override
	public void onDrawFrame(GL10 gl10) {
		//��ʂ̃N���A
        GLES20.glClearColor(1.0f,1.0f,1.0f,1.0f);  
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|
            GLES20.GL_DEPTH_BUFFER_BIT);
                
        //�ˉe�ϊ�
        Matrix.setIdentityM(GLES.pMatrix,0);
        GLES.gluPerspective(GLES.pMatrix,
            45.0f,  //Y�����̉�p
            aspect, //�A�X�y�N�g��
            0.01f,  //�j�A�N���b�v
            100.0f);//�t�@�[�N���b�v                
        
        //�����ʒu�̎w��
        GLES20.glUniform4f(GLES.lightPosHandle,5.0f,5.0f,5.0f,1.0f);
        
        //�r���[�ϊ�
        Matrix.setIdentityM(GLES.mMatrix,0);
        GLES.gluLookAt(GLES.mMatrix,
            0.0f,0.8f,5.0f, //�J�����̎��_
            0.0f,0.8f,0.0f, //�J�����̏œ_
            0.0f,1.0f,0.0f);//�J�����̏����
        
        //���f���ϊ�
        Matrix.rotateM(GLES.mMatrix,0,angle++,0,1,0);
        
        //���f���̕`��
        model.draw();    
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		//�r���[�|�[�g�ϊ�
        GLES20.glViewport(0,0,width,height);
        aspect=(float)width/(float)height;
	}

	//�T�[�t�F�C�X�������ɌĂ΂��
	@Override
	public void onSurfaceCreated(GL10 gl10, EGLConfig config) {
		//�v���O�����̐���
        GLES.makeProgram();
        
        //�f�v�X�o�b�t�@�̗L����
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        
        //�����̗L����
        GLES20.glUniform1i(GLES.useLightHandle,1);
        
        //�����F�̎w��
        GLES20.glUniform4f(GLES.lightAmbientHandle,0.2f,0.2f,0.2f,1.0f);
        GLES20.glUniform4f(GLES.lightDiffuseHandle,0.7f,0.7f,0.7f,1.0f);
        GLES20.glUniform4f(GLES.lightSpecularHandle,0.9f,0.9f,0.9f,1.0f);
        
        //���f���̓ǂݍ���
        try {
            model.figure=ObjLoader.load("droid.obj");
        } catch (Exception e) {
            android.util.Log.e("debug",e.toString());
            for (StackTraceElement ste:e.getStackTrace()) {
                android.util.Log.e("debug","    "+ste);
            }
        }    

	}

}
