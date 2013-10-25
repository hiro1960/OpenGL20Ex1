package com.example.opengl20model;

import java.util.ArrayList;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

//GLES
public class GLES {

	//���_�V�F�[�_�̃R�[�h
    private final static String vertexShaderCode=
        //����
        "uniform vec4 u_LightAmbient;"+ //�����̊����F
        "uniform vec4 u_LightDiffuse;"+ //�����̊g�U���F
        "uniform vec4 u_LightSpecular;"+//�����̋��ʌ��F
        "uniform vec4 u_LightPos;"+     //�����̈ʒu
        "uniform int u_UseLight;"+      //�����̗��p        
    	
        //�}�e���A��
        "uniform vec4 u_MaterialAmbient;"+   //�}�e���A���̊����F
        "uniform vec4 u_MaterialDiffuse;"+   //�}�e���A���̊g�U���F
        "uniform vec4 u_MaterialSpecular;"+  //�}�e���A���̋��ʌ��F
        "uniform float u_MaterialShininess;"+//�}�e���A���̋��ʔ��ˊp�x       
        
        //�s��
        "uniform mat4 u_MMatrix;"+     //���f���r���[�s��
        "uniform mat4 u_PMatrix;"+     //�ˉe�s��
        "uniform mat4 u_NormalMatrix;"+//���f���r���[�s��̋t�]�u�s��        
    	
        //���_
        "uniform vec4 u_Color;"+     //�F      
        "attribute vec4 a_Position;"+//�ʒu
        "attribute vec3 a_Normal;"+  //�@��
        "attribute vec2 a_UV;"+      //UV
        
        //�e�N�X�`��
        "uniform mat4 u_TexMatrix;"+//�e�N�X�`���s��
        
        //�o��
        "varying vec4 v_Color;"+//�F
        "varying vec2 v_UV;"+   //UV
        "void main(){"+
            "if (u_UseLight==1){"+
                //�����̌v�Z
                "vec4 ambient=u_LightAmbient*u_MaterialAmbient;"+
        
                //�g�U���̌v�Z
                "vec3 P=vec3(u_MMatrix*a_Position);"+              
                "vec3 L=normalize(vec3(u_LightPos)-P);"+           
                "vec3 N=normalize(mat3(u_NormalMatrix)*a_Normal);"+
                "vec4 diffuseP=vec4(max(dot(L,N),0.0));"+
                "vec4 diffuse=diffuseP*u_LightDiffuse*u_MaterialDiffuse;"+
        
                //���ʌ��̌v�Z
                "vec3 S=normalize(L+vec3(0.0,0.0,1.0));"+  
                "float specularP=pow(max(dot(N,S),0.0),u_MaterialShininess);"+
                "vec4 specular=specularP*u_LightSpecular*u_MaterialSpecular;"+
            
                //�F�̎w��
                "v_Color=ambient+diffuse+specular;"+
            "}else{"+
                //�F�̎w��
                "v_Color=u_Color;"+
            "}"+
            
            //�ʒu�̎w��
            "gl_Position=u_PMatrix*u_MMatrix*a_Position;"+
            
            //UV�̎w��
            "v_UV=vec2(u_TexMatrix*vec4(a_UV,0.0,1.0));"+
        "}";
    
    //�t���O�����g�V�F�[�_�̃R�[�h
    private final static String fragmentShaderCode= 
        "precision mediump float;"+
        
        //�e�N�X�`��
        "uniform sampler2D u_Tex;"+//�e�N�X�`��
        "uniform int u_UseTex;"+   //�e�N�X�`�����p
        
        //����
        "varying vec2 v_UV;" +  //UV
        "varying vec4 v_Color;"+//�F
        "void main(){"+
            "if (u_UseTex==1){"+
                "gl_FragColor=texture2D(u_Tex,v_UV)*v_Color;"+
            "}else {"+
                "gl_FragColor=v_Color;"+
            "}"+
        "}";    
    
    //�V�X�e��
    public static Context context;//�R���e�L�X�g
    private static int    program;//�v���O�����I�u�W�F�N�g
    private static ArrayList<float[]> mMatrixs=new ArrayList<float[]>();

    //�����̃n���h��
    public static int lightAmbientHandle; //�����̊����F�n���h��  
    public static int lightDiffuseHandle; //�����̊g�U���F�n���h��  
    public static int lightSpecularHandle;//�����̋��ʌ��n���h��
    public static int lightPosHandle;     //�����̈ʒu�n���h��
    public static int useLightHandle;     //�����̗��p�n���h��
    
    //�}�e���A���̃n���h��
    public static int materialAmbientHandle;  //�}�e���A���̊����F�n���h��  
    public static int materialDiffuseHandle;  //�}�e���A���̊g�U���F�n���h��  
    public static int materialSpecularHandle; //�}�e���A���̋��ʌ��n���h��
    public static int materialShininessHandle;//�}�e���A���̋��ʔ��ˊp�x�n���h��
    
    //�s��̃n���h��
    public static int mMatrixHandle;     //���f���r���[�s��n���h��
    public static int pMatrixHandle;     //�ˉe�s��n���h��
    public static int normalMatrixHandle;//���f���r���[�s��̋t�]�u�s��n���h��
    
    //���_�̃n���h��
    public static int colorHandle;   //�F�n���h��
    public static int positionHandle;//�ʒu�n���h��
    public static int normalHandle;  //�@���n���h��
    public static int uvHandle;      //UV�n���h��
    
    //�e�N�X�`���̃n���h��
    public static int texMatrixHandle;//�e�N�X�`���s��n���h��    
    public static int texHandle;      //�e�N�X�`���n���h��    
    public static int useTexHandle;   //�e�N�X�`���̗��p�n���h��    
    
    //�s��
    public static float[] mMatrix  =new float[16];//���f���r���[�s��
    public static float[] pMatrix  =new float[16];//�ˉe�s��
    public static float[] texMatrix=new float[16];//�e�N�X�`���s��
    
    //�v���O�����̐���
    public static void makeProgram() {
        //�V�F�[�_�[�I�u�W�F�N�g�̐���
        int vertexShader  =loadShader(GLES20.GL_VERTEX_SHADER,  vertexShaderCode);
        int fragmentShader=loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode);
        
        //�v���O�����I�u�W�F�N�g�̐���
        program=GLES20.glCreateProgram();
        GLES20.glAttachShader(program,vertexShader);
        GLES20.glAttachShader(program,fragmentShader);
        GLES20.glLinkProgram(program);
        
        //�����̃n���h���̎擾        
        lightAmbientHandle=GLES20.glGetUniformLocation(program,"u_LightAmbient");
        lightDiffuseHandle=GLES20.glGetUniformLocation(program,"u_LightDiffuse");
        lightSpecularHandle=GLES20.glGetUniformLocation(program,"u_LightSpecular");
        lightPosHandle=GLES20.glGetUniformLocation(program,"u_LightPos");
        useLightHandle=GLES20.glGetUniformLocation(program,"u_UseLight");
        
        //�}�e���A���̃n���h���̎擾
        materialAmbientHandle=GLES20.glGetUniformLocation(program,"u_MaterialAmbient");
        materialDiffuseHandle=GLES20.glGetUniformLocation(program,"u_MaterialDiffuse");
        materialSpecularHandle=GLES20.glGetUniformLocation(program,"u_MaterialSpecular");
        materialShininessHandle=GLES20.glGetUniformLocation(program,"u_MaterialShininess");
        
        //�s��̃n���h���̎擾
        mMatrixHandle=GLES20.glGetUniformLocation(program,"u_MMatrix");
        pMatrixHandle=GLES20.glGetUniformLocation(program,"u_PMatrix");
        normalMatrixHandle=GLES20.glGetUniformLocation(program,"u_NormalMatrix");
        
        //���_�̃n���h���̎擾
        colorHandle=GLES20.glGetUniformLocation(program,"u_Color");
        positionHandle=GLES20.glGetAttribLocation(program,"a_Position");
        normalHandle=GLES20.glGetAttribLocation(program,"a_Normal");
        uvHandle=GLES20.glGetAttribLocation(program,"a_UV");
        
        //�e�N�X�`���̃n���h���̎擾
        texMatrixHandle=GLES20.glGetUniformLocation(program,"u_TexMatrix");
        texHandle=GLES20.glGetUniformLocation(program,"u_Tex");
        useTexHandle=GLES20.glGetUniformLocation(program,"u_UseTex");

        //�v���O�����I�u�W�F�N�g�̗��p�J�n
        GLES20.glUseProgram(program);
        
        //�s��̐��K��
        Matrix.setIdentityM(mMatrix,0);
        Matrix.setIdentityM(pMatrix,0);
        Matrix.setIdentityM(texMatrix,0);
        
        //�����l
        GLES20.glUniform4fv(lightAmbientHandle,1,new float[]{0,0,0,1},0);
        GLES20.glUniform4fv(lightDiffuseHandle,1,new float[]{1,1,1,1},0);
        GLES20.glUniform4fv(lightSpecularHandle,1,new float[]{1,1,1,1},0);
        GLES20.glUniform4fv(lightPosHandle,1,new float[]{0,0,1,0},0);
        GLES20.glUniform1i(useLightHandle,0);
        GLES20.glUniform4fv(colorHandle,1,new float[]{1,1,1,1},0);
        GLES20.glUniformMatrix4fv(GLES.texMatrixHandle,1,false,texMatrix,0);
        GLES20.glUniform1f(useTexHandle,0);
    }    
    
    //�V�F�[�_�[�I�u�W�F�N�g�̐���
    private static int loadShader(int type,String shaderCode) {
        int shader=GLES20.glCreateShader(type); 
        GLES20.glShaderSource(shader,shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    //���f���r���[�ˉe�s��̍X�V
    public static void updateMatrix() {
        //�ˉe�s����V�F�[�_�Ɏw��
        GLES20.glUniformMatrix4fv(pMatrixHandle,1,false,pMatrix,0);
        
        //���f���r���[�s����V�F�[�_�Ɏw��
        GLES20.glUniformMatrix4fv(mMatrixHandle,1,false,mMatrix,0);
        
        //���f���r���[�s��̋t�]�u�s��̎w��
        float[] normalM=new float[16];
        normalM(normalM,mMatrix);
        GLES20.glUniformMatrix4fv(normalMatrixHandle,1,false,normalM,0);        
    }
    
    //�s��̋t�]�u�s��̌v�Z
    public static void normalM(float[] rm,float[] m) {
        float[] invertM=new float[16];
        Matrix.invertM(invertM,0,m,0);
        Matrix.transposeM(rm,0,invertM,0);
    }        
    
    //�����ϊ��̎w��
    public static void gluPerspective(float[] m,
        float angle,float aspect,float near,float far) {
        float top=near*(float)Math.tan(angle*(Math.PI/360.0));
        float bottom=-top;
        float left=bottom*aspect;
        float right=top*aspect;
        float[] frustumM=new float[16];
        float[] resultM=new float[16];
        Matrix.frustumM(frustumM,0,left,right,bottom,top,near,far);
        Matrix.multiplyMM(resultM,0,m,0,frustumM,0);
        System.arraycopy(resultM,0,m,0,16);
    }
    
    //�r���[�ϊ��̎w��
    public static void gluLookAt(float[] m,
        float eyeX,float eyeY,float eyeZ,
        float focusX,float focusY,float focusZ,
        float upX,float upY,float upZ) {
        float[] lookAtM=new float[16];
        float[] resultM=new float[16];
        Matrix.setLookAtM(lookAtM,0,
            eyeX,eyeY,eyeZ,focusX,focusY,focusZ,upX,upY,upZ);
        Matrix.multiplyMM(resultM,0,m,0,lookAtM,0);
        System.arraycopy(resultM,0,m,0,16);
    }    
    
    //�s��̃v�b�V��
    public static void glPushMatrix() {
        float[] m=new float[16];
        System.arraycopy(mMatrix,0,m,0,16);
        mMatrixs.add(m);
    }
    
    //�s��̃|�b�v
    public static void glPopMatrix() {
        if (mMatrixs.size()==0) return;
        float[] m=mMatrixs.remove(mMatrixs.size()-1);
        System.arraycopy(m,0,mMatrix,0,16);
    }   

}
