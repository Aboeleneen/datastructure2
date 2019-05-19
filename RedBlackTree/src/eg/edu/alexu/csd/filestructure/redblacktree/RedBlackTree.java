package eg.edu.alexu.csd.filestructure.redblacktree;

import javax.management.RuntimeErrorException;

public class RedBlackTree<T extends Comparable<T>, v> implements IRedBlackTree<T, v> {

	private INode<T,v> root ;
	private INode<T,v> NIL ;
	
	public RedBlackTree() {
		NIL = new Node<T, v>(null,null);
		NIL.setColor(false);
	//	NIL.setParent(NIL);
	//	NIL.setLeftChild(NIL);
	//	NIL.setRightChild(NIL);
	    root=NIL;
	}
	
	@Override
	public Node<T, v> getRoot() {
		// TODO Auto-generated method stub
		return (Node<T, v>) root;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return root.isNull();
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		root=NIL;
	}

	@Override
	public v search(T key) {
		// TODO Auto-generated method stub
		if(key==null) {
			throw new RuntimeErrorException(new Error());
		}
		INode<T,v> curNode = root ;
		while(!curNode.isNull()) {
			if(key.compareTo(curNode.getKey())>0) {
				curNode = curNode.getRightChild();
			}else if(key.compareTo(curNode.getKey())< 0) {
				curNode = curNode.getLeftChild();
			}else {
				return curNode.getValue();
			}
		}
		return null;
		
	}

	@Override
	public boolean contains(T key) {
		if (key == null) throw new RuntimeErrorException(new Error());
        return (this.search(key) != null);
		
	}

	@Override
	public void insert(T key, v value) {
		// TODO Auto-generated method stub
		if(key == null || value == null) {
			throw new RuntimeErrorException(new Error());
		}
		INode<T,v> newNode = new Node<T, v>(key,value);
		INode<T,v> curNode = root ;
		INode<T,v> previousNode = NIL ;
		
		while(!curNode.isNull()) {
			previousNode = curNode;
			if(key.compareTo(curNode.getKey())>0) {
				curNode = curNode.getRightChild();
			}else if(key.compareTo(curNode.getKey())< 0) {
				curNode = curNode.getLeftChild();
			}else {
				curNode.setValue((v) value);
				return;
			}
		}
		newNode.setParent(previousNode);
		if(previousNode.isNull()) {
			this.root=newNode;
		}else if (newNode.getKey().compareTo(previousNode.getKey()) > 0) {
			previousNode.setRightChild(newNode);
		}else {
			previousNode.setLeftChild(newNode);
		}
		newNode.setLeftChild(this.NIL);
		newNode.setRightChild(this.NIL);
		newNode.setColor(true);
	    FIX(newNode);
	}

	@Override
	public boolean delete(T key) {
		// TODO Auto-generated method stub
		if(key==null) {
			throw new RuntimeErrorException(new Error());
		}
		if(! contains(key)) return false;
		INode<T,v> ZNode = getNode(key);
		INode<T,v> XNode ;
		INode<T,v> YNode = ZNode ;
		Boolean YColor = YNode.getColor();
		if(ZNode.getLeftChild().isNull()) {
			XNode=ZNode.getRightChild();
			this.TransPlant(ZNode, ZNode.getRightChild());
		}
		else if (ZNode.getRightChild().isNull()) {
			XNode = ZNode.getLeftChild();
			this.TransPlant(ZNode, ZNode.getLeftChild());
		}else {
			YNode =this.getMinimum(ZNode.getRightChild());
			YColor = YNode.getColor();
			XNode = YNode.getRightChild();
			if(YNode.getParent()==ZNode) {
				XNode.setParent(YNode);
			}else {
				this.TransPlant(YNode, YNode.getRightChild());
				YNode.setRightChild(ZNode.getRightChild());
				YNode.getRightChild().setParent(YNode);
			}
			this.TransPlant(ZNode, YNode);
			YNode.setLeftChild(ZNode.getLeftChild());
			YNode.getLeftChild().setParent(YNode);
			YNode.setColor(ZNode.getColor());
		}
		if(!YColor) {
			FIX_DELETE(XNode);
		}
		
		return true;
		
	}
	
	private INode<T,v> getNode(T key){
		INode<T,v> curNode = root ;
		while(!curNode.isNull()) {
			if(key.compareTo(curNode.getKey())>0) {
				curNode = curNode.getRightChild();
			}else if(key.compareTo(curNode.getKey())< 0) {
				curNode = curNode.getLeftChild();
			}else {
				return curNode;
			}
		}
		return null;
	}
	
	private void FIX(INode<T,v> curNode) {
		if	(curNode==root) {
			root.setColor(false);
		}
		INode<T,v> node = curNode;
		while( node.getParent().getColor() ) {
			if(node.getParent() ==  node.getParent().getParent().getLeftChild()) {
				// case one when uncle is red so we do  color flip
				if(node.getParent().getParent().getRightChild().getColor()) {
					node.getParent().setColor(false);
					node.getParent().getParent().getRightChild().setColor(false);
					node.getParent().getParent().setColor(true);
					node=node.getParent().getParent();
				}
				
				// case two when uncle is black so we need rotation
				else {
					if (node ==  node.getParent().getRightChild()){
						node = node.getParent();
						LeftRotation(node);
					}
					node.getParent().setColor(false);
					node.getParent().getParent().setColor(true);
					RightRotation(node.getParent().getParent());
				}
			}else {
				
					// case one when uncle is red so we do  color flip
					if(node.getParent().getParent().getLeftChild().getColor()) {
						node.getParent().setColor(false);
						node.getParent().getParent().getLeftChild().setColor(false);
						node.getParent().getParent().setColor(true);
						node=node.getParent().getParent();
					}
					
					// case two when uncle is black so we need rotation
					else {
						if (node == node.getParent().getLeftChild()){
							node = node.getParent();
							RightRotation(node);
						}
						node.getParent().setColor(false);
						node.getParent().getParent().setColor(true);
						LeftRotation(node.getParent().getParent());
					}
				
			}
				
		
	}
		root.setColor(false);
	}
	
	private void RightRotation(INode<T,v> Xnode) {
		/*if (Xnode.isNull() || Xnode.getLeftChild().isNull()) {
			return;
		}*/
		INode<T,v> Ynode = Xnode.getLeftChild();
		Xnode.setLeftChild(Ynode.getRightChild());
		if(!Ynode.getRightChild().isNull()) {
			Ynode.getRightChild().setParent(Xnode);
		}
		
		
		Ynode.setParent(Xnode.getParent());
		if(Xnode.getParent().isNull()) {
			root = Ynode;
		}
		else if(Xnode == Xnode.getParent().getLeftChild()) {
			Xnode.getParent().setLeftChild(Ynode);
		}else {
			Xnode.getParent().setRightChild(Ynode);
		}	
		Ynode.setRightChild(Xnode);
		Xnode.setParent(Ynode);
	}
	
	private void LeftRotation(INode<T,v> Xnode) {
		/*if (Xnode.isNull()|| Xnode.getRightChild().isNull()) {
			return;
		}*/
		INode<T,v> Ynode = Xnode.getRightChild();
		Xnode.setRightChild(Ynode.getLeftChild());
		if(!Ynode.getLeftChild().isNull()) {
			
			Ynode.getLeftChild().setParent(Xnode);
		}
        Ynode.setParent(Xnode.getParent());
		if(Xnode.getParent().isNull()) {
			root=Ynode;
		}else if (Xnode == Xnode.getParent().getLeftChild()) {
			Xnode.getParent().setLeftChild(Ynode);
		}else {
			Xnode.getParent().setRightChild(Ynode);
		}
		Ynode.setLeftChild(Xnode);
		Xnode.setParent(Ynode);
	}
	
	private void TransPlant(INode<T,v> first , INode<T,v> second) {
		if(first.getParent().isNull()) {
			this.root=second;
		}else if (first == first.getParent().getLeftChild()) {
			first.getParent().setLeftChild(second);
		}else {
			first.getParent().setRightChild(second);
		}
		second.setParent(first.getParent());
	}
	
	private INode<T,v> getMinimum(INode<T,v> node) {
		
		while(!node.getLeftChild().isNull()) {
			node = node.getLeftChild();
		}
		return node;
	}
	
	private void FIX_DELETE(INode<T,v> node) {
			while(node!=root&&!node.getColor()) {
				// part one when node is left child
				if(node == node.getParent().getLeftChild()) {
					INode<T,v> sibling = node.getParent().getRightChild();
					if(sibling.getColor()) { // red 
						sibling.setColor(false);
						node.getParent().setColor(true);
						LeftRotation(node.getParent());
						sibling = node.getParent().getRightChild();
					}
				   
					if(!sibling.getLeftChild().getColor()&&!sibling.getRightChild().getColor()) {
						sibling.setColor(true);
						node=node.getParent();
						
					}
					
					else {
						if (!sibling.getRightChild().getColor()) {
						sibling.getLeftChild().setColor(false);
						sibling.setColor(true);
						this.RightRotation(sibling);
						sibling=node.getParent().getRightChild(); 
						}
						
					
					sibling.setColor(node.getParent().getColor());
					node.getParent().setColor(false);
					sibling.getRightChild().setColor(false);
					LeftRotation(node.getParent());
					node = root;}
			    	
				}else {
					
					INode<T,v> sibling = node.getParent().getLeftChild();
					if(sibling.getColor()) { // red 
						sibling.setColor(false);
						node.getParent().setColor(true);
					    RightRotation(node.getParent());
						sibling = node.getParent().getLeftChild();
					}
				   
					if(!sibling.getLeftChild().getColor()&&!sibling.getRightChild().getColor()) {
						sibling.setColor(false);
						node=node.getParent();
					}
					
					else {
						if (!sibling.getLeftChild().getColor()) {
					
						sibling.getRightChild().setColor(false);
						sibling.setColor(true);
						LeftRotation(sibling);
						sibling=node.getParent().getLeftChild(); 
						}
						
						
						sibling.setColor(node.getParent().getColor());
						node.getParent().setColor(false);
						sibling.getLeftChild().setColor(false);
						RightRotation(node.getParent());
						node = root;
			    	
					}
				}
					
			}
			node.setColor(false);
	}

}
