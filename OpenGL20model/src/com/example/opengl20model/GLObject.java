package com.example.opengl20model;

//GL�I�u�W�F�N�g(VertexBuffer/IndexBuffer/Material/Texture�̐e)
public abstract class GLObject {
	//�j�����ɌĂ΂��
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        dispose();
    }

    //�o�C���h
    public abstract void bind();

    //�A���o�C���h
    public abstract void unbind();

    //���
    public abstract void dispose();
}
