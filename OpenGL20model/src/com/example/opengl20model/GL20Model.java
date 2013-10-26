package com.example.opengl20model;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
//import android.view.Menu;

//3Dモデルの読み込み
public class GL20Model extends Activity {

	private GLSurfaceView glView;
	
	//アクティビティ生成時に呼ばれる
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//GLサーフェイスビュー
        glView=new GLSurfaceView(this);
        glView.setEGLContextClientVersion(2);
        glView.setRenderer(new GLRenderer(this));  // これによりglView::onDrawFrame()が定期的に呼び出される
		setContentView(glView);
	}

	//アクティビティレジューム時に呼ばれる
    @Override
    public void onResume() {
        super.onResume();
        glView.onResume();
    }
    
    //アクティビティポーズ時に呼ばれる
    @Override
    public void onPause() {
        super.onPause();
        glView.onPause();
    }

}
