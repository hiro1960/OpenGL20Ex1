package OpenGL20common;

//3要素ベクトル
public class Vector3 {

	public float x=0.0f;//X座標
    public float y=0.0f;//Y座標
    public float z=0.0f;//Z座標
    
    //コンストラクタ
    public Vector3() {
    }

    //コンストラクタ
    public Vector3(float x,float y,float z) {
        set(x,y,z);
    }

    //コンストラクタ
    public Vector3(Vector3 origin) {
        set(origin);
    }

    //値の指定
    public void set(Vector3 origin) {
        x=origin.x;
        y=origin.y;
        z=origin.z;
    }

    //値の指定
    public void set(float x,float y,float z) {
        this.x=x;
        this.y=y;
        this.z=z;
    }

    //内積の計算
    public float dot(Vector3 v) {
        return (x*v.x)+(y*v.y)+(z*v.z);
    }

    //内積の計算
    public float dot(float x,float y,float z) {
        return (this.x*x)+(this.y*y)+(this.z*z);
    }

    //外積の計算
    public Vector3 cross(Vector3 v,Vector3 result) {
        result.set((y*v.z)-(z*v.y),(z*v.x)-(x*v.z),(x*v.y)-(y*v.x));
        return result;
    }

    //外積の計算
    public void cross(float x,float y,float z) {
        set((this.y*z)-(this.z*y),(this.z*x)-(this.x*z),(this.x*y)-(this.y*x));
    }

    //和の計算
    public void add(Vector3 v0,Vector3 v1) {
        x=v0.x+v1.x;
        y=v0.y+v1.y;
        z=v0.z+v1.z;
    }

    //差の計算
    public void sub(Vector3 v0,Vector3 v1) {
        x=v0.x-v1.x;
        y=v0.y-v1.y;
        z=v0.z-v1.z;
    }
    
    //ベクトルの長さの取得
    public float length() {
        return (float)Math.sqrt((double)((x*x)+(y*y)+(z*z)));
    }

    //ベクトルの長さの正規化
    public void normalize() {
        final float len=length();
        x/=len;
        y/=len;
        z/=len;
    }

    //値の比較
    @Override
    public boolean equals(Object o) {
        Vector3 v=(Vector3)o;
        return v.x==x && v.y==y && v.z==z;
    }
}
