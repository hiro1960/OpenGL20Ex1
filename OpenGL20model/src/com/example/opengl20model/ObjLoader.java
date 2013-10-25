package com.example.opengl20model;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.HashMap;

//OBJ���[�_�[
public class ObjLoader {
	//���[�h
    public static Figure load(String path) throws Exception {
        DataInputStream in=new DataInputStream(
            GLES.context.getAssets().open(path));
        
        //���_�o�b�t�@�ƃC���f�b�N�X�o�b�t�@
        ArrayList<float[]> positions=new ArrayList<float[]>();
        ArrayList<float[]> normals  =new ArrayList<float[]>();
        ArrayList<float[]> uvs      =new ArrayList<float[]>();
        ArrayList<float[]> vertexs  =new ArrayList<float[]>();
        ArrayList<short[]> indexs   =new ArrayList<short[]>();
        
        //���b�V���ƃ}�e���A��
        ArrayList<Mesh>          meshs=new ArrayList<Mesh>();
        HashMap<String,Material> materials=new HashMap<String,Material>();
        
        //�p�[�X
        Material material=null;
        boolean  meshFlag=false;
        String line=in.readLine();
        
        while (line!=null) {
            String[] word=line.split(" ",0);
            
            //MTL�t�@�C���̓ǂݍ���
            if (word[0].equals("mtllib")) {
                loadMtl(materials,word[1]);
            }
            //�}�e���A��
            else if (word[0].equals("usemtl")) {
                material=materials.get(word[1]);
            }
            //���_
            else if (word[0].equals("v")) {
                float[] position=new float[]{
                    Float.parseFloat(word[1]),
                    Float.parseFloat(word[2]),
                    Float.parseFloat(word[3])
                };
                positions.add(position);
            }
            //UV
            else if (word[0].equals("vt")) {
                float[] uv=new float[]{
                    Float.parseFloat(word[1]),
                    1.0f-Float.parseFloat(word[2])
                };
                uvs.add(uv);
            }
            //�@��
            else if (word[0].equals("vn")) {
                float[] normal=new float[]{
                    Float.parseFloat(word[1]),
                    Float.parseFloat(word[2]),
                    Float.parseFloat(word[3])
                };
                normals.add(normal);
            }
            //�C���f�b�N�X
            else if (word[0].equals("f")) {
                meshFlag=true;
                short[] index=new short[word.length-1];
                for (int i=0;i<index.length;i++) {
                    String[] w=word[i+1].split("/",0);
                    index[i]=(short)addVertex(vertexs,
                        positions.get(Integer.parseInt(w[0])-1),
                        uvs.get(Integer.parseInt(w[1])-1),
                        normals.get(Integer.parseInt(w[2])-1));
               }
                indexs.add(index);
            }
            //���b�V���̐���
            else if (meshFlag) {
                meshFlag=false;
                Mesh mesh=new Mesh();
                mesh.vertexBuffer=new VertexBuffer(vertexs);
                mesh.indexBuffer =new IndexBuffer(indexs);
                mesh.material    =material;
                meshs.add(mesh);
            }
            line=in.readLine();
        }
        in.close();
        
        //�t�B�M���A�̐���
        Figure figure=new Figure();
        figure.materials=materials;
        figure.meshs=meshs;
        return figure;
    }
    
    //���_�̒ǉ�
    private static int addVertex(ArrayList<float[]> vertexs,
        float[] position,float[] uv,float[] normal) {
        float[] vertex=new float[3+2+3];
        System.arraycopy(position,0,vertex,0,3);
        System.arraycopy(uv,0,vertex,3,2);
        System.arraycopy(normal,0,vertex,3+2,3);
        
        //�����̒��_
        for (int i=0;i<vertexs.size();i++) {
            float[] v=vertexs.get(i);
            int j=0;
            for (;j<8;j++) {
                if (v[j]!=vertex[j]) break;
            }
            if (j==8) return i;
        }
        
        //�V�K�̒��_
        vertexs.add(vertex);
        return vertexs.size()-1;
    }
    
    //MTL�̓ǂݍ���
    public static void loadMtl(HashMap<String,Material> materials,
        String path) throws Exception {
        DataInputStream in=new DataInputStream(
            GLES.context.getAssets().open(path));
        Material material=null;
        materials.clear();
        String line=in.readLine();
        while (line!=null) {
            String[] word=line.split(" ",0);
            //�}�e���A����
            if (word[0].equals("newmtl")) {
                material=new Material();
                materials.put(word[1],material);
            } 
            //����
            else if (word[0].equals("Ka")) {
                material.ambient=new float[]{
                    Float.parseFloat(word[1]),
                    Float.parseFloat(word[2]),
                    Float.parseFloat(word[3]),
                    1.0f};
            } 
            //�g�U��
            else if (word[0].equals("Kd")) {
                material.diffuse=new float[]{
                    Float.parseFloat(word[1]),
                    Float.parseFloat(word[2]),
                    Float.parseFloat(word[3]),
                    1.0f};
            } 
            //���ʌ�
            else if (word[0].equals("Ks")) {
                material.specular=new float[]{
                    Float.parseFloat(word[1]),
                    Float.parseFloat(word[2]),
                    Float.parseFloat(word[3]),
                    1.0f};
            } 
            //���ʔ��ˊp�x
            else if (word[0].equals("Ns")) {
                material.shininess=Float.parseFloat(word[1]);
            }            
            //�e�N�X�`��
            else if (word[0].equals("map_Kd")) {
                material.texture=
                    Texture.createTextureFromAsset(word[1]);
            }
            line=in.readLine();
        }
        in.close();
    }
}
