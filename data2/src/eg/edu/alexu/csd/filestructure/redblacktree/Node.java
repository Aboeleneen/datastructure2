package eg.edu.alexu.csd.filestructure.redblacktree;

public class Node<T1 extends Comparable<T1>, T2> implements INode<T1,T2>{

	
	  private  T1 key ;
	  private  T2 value;
	  private INode<T1,T2> left , right , parent ;
	  private boolean isRed ;
	  		
	  public Node(T1 key , T2 value) {
		 this.key=key;
		 this.value=value;
		 left=right=parent=null;
		 isRed=true;
	  }
	@Override
	public void setParent(INode<T1, T2> parent) {
		// TODO Auto-generated method stub
		this.parent =  parent;
	}

	@Override
	public INode<T1, T2> getParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	@Override
	public void setLeftChild(INode<T1, T2> leftChild) {
		// TODO Auto-generated method stub
		left = leftChild;
	}

	@Override
	public INode<T1, T2> getLeftChild() {
		// TODO Auto-generated method stub
		return left;
	}

	@Override
	public void setRightChild(INode<T1, T2> rightChild) {
		// TODO Auto-generated method stub
		right = rightChild;
	}

	@Override
	public INode<T1, T2> getRightChild() {
		// TODO Auto-generated method stub
		return right;
	}

	@Override
	public T1 getKey() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	public void setKey(T1 key) {
		// TODO Auto-generated method stub
		this.key=key;
	}

	@Override
	public T2 getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public void setValue(T2 value) {
		// TODO Auto-generated method stub
		this.value=value;
	}

	@Override
	public boolean getColor() {
		// TODO Auto-generated method stub
		return isRed;
	}

	@Override
	public void setColor(boolean color) {
		// TODO Auto-generated method stub
		isRed = color;
	}

	@Override
	public boolean isNull() {
		// TODO Auto-generated method stub
		return key==null;
	}
 
  
}
