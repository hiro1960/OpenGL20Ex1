package OpenGL20common;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.HashMap;


//OBJローダー
public class ObjLoader {
	//ロード
    public static Figure load(String path) throws Exception {
        DataInputStream in=new DataInputStream(
            GLES.context.getAssets().open(path));
        
        //頂点バッファとインデックスバッファ
        ArrayList<float[]> positions=new ArrayList<float[]>();
        ArrayList<float[]> normals  =new ArrayList<float[]>();
        ArrayList<float[]> uvs      =new ArrayList<float[]>();
        ArrayList<float[]> vertexs  =new ArrayList<float[]>();
        ArrayList<short[]> indexs   =new ArrayList<short[]>();
        
        //メッシュとマテリアル
        ArrayList<Mesh>          meshs=new ArrayList<Mesh>();
        HashMap<String,Material> materials=new HashMap<String,Material>();
        
        //パース
        Material material=null;
        boolean  meshFlag=false;
        String line=in.readLine();
        
        while (line!=null) {
            String[] word=line.split(" ",0);
            
            //MTLファイルの読み込み
            if (word[0].equals("mtllib")) {
                loadMtl(materials,word[1]);
            }
            //マテリアル
            else if (word[0].equals("usemtl")) {
                material=materials.get(word[1]);
            }
            //頂点
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
            //法線
            else if (word[0].equals("vn")) {
                float[] normal=new float[]{
                    Float.parseFloat(word[1]),
                    Float.parseFloat(word[2]),
                    Float.parseFloat(word[3])
                };
                normals.add(normal);
            }
            //インデックス
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
            //メッシュの生成
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
        
        //フィギュアの生成
        Figure figure=new Figure();
        figure.materials=materials;
        figure.meshs=meshs;
        return figure;
    }
    
    //頂点の追加
    private static int addVertex(ArrayList<float[]> vertexs,
        float[] position,float[] uv,float[] normal) {
        float[] vertex=new float[3+2+3];
        System.arraycopy(position,0,vertex,0,3);
        System.arraycopy(uv,0,vertex,3,2);
        System.arraycopy(normal,0,vertex,3+2,3);
        
        //既存の頂点
        for (int i=0;i<vertexs.size();i++) {
            float[] v=vertexs.get(i);
            int j=0;
            for (;j<8;j++) {
                if (v[j]!=vertex[j]) break;
            }
            if (j==8) return i;
        }
        
        //新規の頂点
        vertexs.add(vertex);
        return vertexs.size()-1;
    }
    
    //MTLの読み込み
    public static void loadMtl(HashMap<String,Material> materials,
        String path) throws Exception {
        DataInputStream in=new DataInputStream(
            GLES.context.getAssets().open(path));
        Material material=null;
        materials.clear();
        String line=in.readLine();
        while (line!=null) {
            String[] word=line.split(" ",0);
            //マテリアル名
            if (word[0].equals("newmtl")) {
                material=new Material();
                materials.put(word[1],material);
            } 
            //環境光
            else if (word[0].equals("Ka")) {
                material.ambient=new float[]{
                    Float.parseFloat(word[1]),
                    Float.parseFloat(word[2]),
                    Float.parseFloat(word[3]),
                    1.0f};
            } 
            //拡散光
            else if (word[0].equals("Kd")) {
                material.diffuse=new float[]{
                    Float.parseFloat(word[1]),
                    Float.parseFloat(word[2]),
                    Float.parseFloat(word[3]),
                    1.0f};
            } 
            //鏡面光
            else if (word[0].equals("Ks")) {
                material.specular=new float[]{
                    Float.parseFloat(word[1]),
                    Float.parseFloat(word[2]),
                    Float.parseFloat(word[3]),
                    1.0f};
            } 
            //鏡面反射角度
            else if (word[0].equals("Ns")) {
                material.shininess=Float.parseFloat(word[1]);
            }            
            //テクスチャ
            else if (word[0].equals("map_Kd")) {
                material.texture=
                    Texture.createTextureFromAsset(word[1]);
            }
            line=in.readLine();
        }
        in.close();
    }
}
