package com.example.opengl20model;

//3�v�f�x�N�g��
public class Vector3 {

	public float x=0.0f;//X���W
    public float y=0.0f;//Y���W
    public float z=0.0f;//Z���W
    
    //�R���X�g���N�^
    public Vector3() {
    }

    //�R���X�g���N�^
    public Vector3(float x,float y,float z) {
        set(x,y,z);
    }

    //�R���X�g���N�^
    public Vector3(Vector3 origin) {
        set(origin);
    }

    //�l�̎w��
    public void set(Vector3 origin) {
        x=origin.x;
        y=origin.y;
        z=origin.z;
    }

    //�l�̎w��
    public void set(float x,float y,float z) {
        this.x=x;
        this.y=y;
        this.z=z;
    }

    //���ς̌v�Z
    public float dot(Vector3 v) {
        return (x*v.x)+(y*v.y)+(z*v.z);
    }

    //���ς̌v�Z
    public float dot(float x,float y,float z) {
        return (this.x*x)+(this.y*y)+(this.z*z);
    }

    //�O�ς̌v�Z
    public Vector3 cross(Vector3 v,Vector3 result) {
        result.set((y*v.z)-(z*v.y),(z*v.x)-(x*v.z),(x*v.y)-(y*v.x));
        return result;
    }

    //�O�ς̌v�Z
    public void cross(float x,float y,float z) {
        set((this.y*z)-(this.z*y),(this.z*x)-(this.x*z),(this.x*y)-(this.y*x));
    }

    //�a�̌v�Z
    public void add(Vector3 v0,Vector3 v1) {
        x=v0.x+v1.x;
        y=v0.y+v1.y;
        z=v0.z+v1.z;
    }

    //���̌v�Z
    public void sub(Vector3 v0,Vector3 v1) {
        x=v0.x-v1.x;
        y=v0.y-v1.y;
        z=v0.z-v1.z;
    }
    
    //�x�N�g���̒����̎擾
    public float length() {
        return (float)Math.sqrt((double)((x*x)+(y*y)+(z*z)));
    }

    //�x�N�g���̒����̐��K��
    public void normalize() {
        final float len=length();
        x/=len;
        y/=len;
        z/=len;
    }

    //�l�̔�r
    @Override
    public boolean equals(Object o) {
        Vector3 v=(Vector3)o;
        return v.x==x && v.y==y && v.z==z;
    }
}
