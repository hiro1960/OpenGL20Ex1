package OpenGL20common;

import java.util.ArrayList;


import android.opengl.Matrix;

//3D�I�u�W�F�N�g
public class Object3D {
	public Figure  figure;                     //�t�B�M���A
    public Vector3 position=new Vector3();     //�ʒu
    public Vector3 rotate  =new Vector3();     //��]
    public Vector3 scale   =new Vector3(1,1,1);//�g�k
    public ArrayList<Object3D> childs=new ArrayList<Object3D>();// �q��
    
    //�`��
    public void draw() {
        GLES.glPushMatrix();
        Matrix.translateM(GLES.mMatrix,0,position.x,position.y,position.z);
        Matrix.rotateM(GLES.mMatrix,0,rotate.z,0,0,1);
        Matrix.rotateM(GLES.mMatrix,0,rotate.y,0,1,0);
        Matrix.rotateM(GLES.mMatrix,0,rotate.x,1,0,0);
        Matrix.scaleM(GLES.mMatrix,0,scale.x,scale.y,scale.z);
        GLES.updateMatrix();
        figure.draw();
        for (int i=0;i<childs.size();i++) childs.get(i).draw();
        GLES.glPopMatrix();
    }
    
}
