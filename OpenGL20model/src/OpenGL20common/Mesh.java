package OpenGL20common;

//���b�V��
public class Mesh {
	public VertexBuffer vertexBuffer;//���_�o�b�t�@
    public IndexBuffer  indexBuffer; //�C���f�b�N�X�o�b�t�@
    public Material     material;    //�}�e���A��
    
    //�`��
    public void draw() {
        material.bind();
        vertexBuffer.bind();
        indexBuffer.draw();
        vertexBuffer.unbind();
        material.unbind();
    }
}
