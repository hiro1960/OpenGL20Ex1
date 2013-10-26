package com.example.opengl20model;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
//import android.view.Menu;

//3D���f���̓ǂݍ���
public class GL20Model extends Activity {

	private GLSurfaceView glView;
	
	//�A�N�e�B�r�e�B�������ɌĂ΂��
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//GL�T�[�t�F�C�X�r���[
        glView=new GLSurfaceView(this);
        glView.setEGLContextClientVersion(2);
        glView.setRenderer(new GLRenderer(this));  // ����ɂ��glView::onDrawFrame()������I�ɌĂяo�����
		setContentView(glView);
	}

	//�A�N�e�B�r�e�B���W���[�����ɌĂ΂��
    @Override
    public void onResume() {
        super.onResume();
        glView.onResume();
    }
    
    //�A�N�e�B�r�e�B�|�[�Y���ɌĂ΂��
    @Override
    public void onPause() {
        super.onPause();
        glView.onPause();
    }

}
