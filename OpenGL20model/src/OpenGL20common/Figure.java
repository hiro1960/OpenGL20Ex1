package OpenGL20common;

import java.util.ArrayList;
import java.util.HashMap;


//�t�B�M���A
public class Figure {
	
	public HashMap<String,Material> materials;//�}�e���A���Q
    public ArrayList<Mesh>          meshs;    //���b�V���Q
    
    //�`��
    public void draw() {
        for (Mesh mesh:meshs) mesh.draw();
    }
}
