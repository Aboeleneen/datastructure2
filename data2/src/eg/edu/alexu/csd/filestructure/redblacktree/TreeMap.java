package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.management.RuntimeErrorException;

public class TreeMap<T extends Comparable<T>,v> implements ITreeMap<T,v> {
	
	
	private RedBlackTree<T,v> container ;
	private INode<T,v> NIL ;
	private int size ;
	public TreeMap() {
		container = new RedBlackTree<T,v>();
		NIL=container.getRoot();
		size=0;
	}

	@Override
	public Entry<T, v> ceilingEntry(T key) {
		// TODO Auto-generated method stub
		if(key==null) {
			throw new RuntimeErrorException(new Error());
		}
		if(containsKey(key)) {
			return new AbstractMap.SimpleEntry<>(this.getNode(key).getKey(), this.getNode(key).getValue());
		}else {
			put(key, null);
		}
		INode<T,v> target=this.inOrderSuccessor(container.getRoot(),this.getNode(key));
			remove(key);
		if(target==null) {
			return null ;
		}
		return new AbstractMap.SimpleEntry<>(target.getKey(), target.getValue());
	}

	@Override
	public T ceilingKey(T key) {
		// TODO Auto-generated method stub
		if(key==null) {
			throw new RuntimeErrorException(new Error());
		}
		Map.Entry<T, v> target = ceilingEntry(key);
		if(target==null) {
			return null;
		}
		return target.getKey();
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		container.clear();
		size=0;
	}

	@Override
	public boolean containsKey(T key) {
		// TODO Auto-generated method stub
		return container.contains(key);
	}

	@Override
	public boolean containsValue(v value) {
		// TODO Auto-generated method stub
		if(value == null) {
			throw new RuntimeErrorException(new Error());
		}
		return inOrder(value,container.getRoot());
	}

	
	@Override
	public Set<Entry<T, v>> entrySet() {
		// TODO Auto-generated method stub
		if(container.isEmpty()) {
			return null;
		}
		ArrayList<Entry<T,v>> arr   = new ArrayList<Entry<T,v>>();
		this.inOrderValue(arr,container.getRoot());
		return new LinkedHashSet<>(arr);
	}

	@Override
	public Entry<T, v> firstEntry() {
		// TODO Auto-generated method stub
		
		if(container.isEmpty()) {
			return null ;
		}
		INode<T,v> target = this.getMinimum(container.getRoot());
		return new AbstractMap.SimpleEntry<T, v>(target.getKey(), target.getValue());
	}

	@Override
	public T firstKey() {
		// TODO Auto-generated method stub
		if(container.isEmpty()) {
			return null ;
		}
		return firstEntry().getKey();
	}

	@Override
	public Entry<T, v> floorEntry(T key) {
		// TODO Auto-generated method stub
		if(key==null) {
			throw new RuntimeErrorException(new Error());
		}
		if(containsKey(key)) {
			return new AbstractMap.SimpleEntry<>(this.getNode(key).getKey(), this.getNode(key).getValue());
		}else {
			put(key, null);
		}
		INode<T,v> target=this.inOrderPredecessor(container.getRoot(),this.getNode(key));
			remove(key);
		if(target==null) {
			return null ;
		}
		return new AbstractMap.SimpleEntry<>(target.getKey(), target.getValue());
	}

	@Override
	public T floorKey(T key) {
		// TODO Auto-generated method stub
		if(key==null) {
			throw new RuntimeErrorException(new Error());
		}
		Map.Entry<T, v> target = floorEntry(key);
		if(target==null) {
			return null;
		}
		return target.getKey();
	}

	@Override
	public v get(T key) {
		// TODO Auto-generated method stub
		if(key==null) {
			throw new RuntimeErrorException(new Error());
		}
		INode<T,v> curNode = container.getRoot() ;
		while(curNode!=NIL) {
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
	public ArrayList<Entry<T, v>> headMap(T toKey) {
		// TODO Auto-generated method stub
		if(toKey == null) {
			throw new RuntimeErrorException(new Error());
		}
		ArrayList<Entry<T,v>> arr = new ArrayList<>();
		INode<T,v> target = this.getNode(toKey);
		if(target==null||target.getLeftChild().isNull()) {
			return null ;
		}
		this.inOrderValue(arr, target.getLeftChild());
		return arr ;
	}

	@Override
	public ArrayList<Entry<T, v>> headMap(T toKey, boolean inclusive) {
		// TODO Auto-generated method stub
		if(toKey == null) {
			throw new RuntimeErrorException(new Error());
		}
		if(!inclusive) {
			return headMap(toKey);
		}
		INode<T,v> target = this.getNode(toKey);
		if(target==null) {
			return null ;
		}
		ArrayList<Entry<T,v>> arr = new ArrayList<>();
		this.inOrderValue(arr, target);
		return arr ;
	}

	@Override
	public Set<T> keySet() {
		// TODO Auto-generated method stub
		if(container.isEmpty()) {
			return null ;
		}
		ArrayList<T> arr = new ArrayList<>();
		this.inOrderKey(arr, container.getRoot());
		return new LinkedHashSet<>(arr);
	}

	@Override
	public Entry<T, v> lastEntry() {
		// TODO Auto-generated method stub
		if(container.isEmpty()) {
			return null ;
		}
		INode<T,v> target = this.getMaximum(container.getRoot());
		return new AbstractMap.SimpleEntry<T, v>(target.getKey(), target.getValue());
	}

	@Override
	public T lastKey() {
		// TODO Auto-generated method stub
		if(container.isEmpty()) {
			return null ;
		}
		return lastEntry().getKey();
	}

	@Override
	public Entry<T, v> pollFirstEntry() {
		// TODO Auto-generated method stub
		if(container.isEmpty()) {
			return null ;
		}
		Map.Entry<T, v> entry = this.firstEntry();
		this.remove(entry.getKey());
		return entry;
	}

	@Override
	public Entry<T, v> pollLastEntry() {
		// TODO Auto-generated method stub
		Map.Entry<T, v> entry = this.lastEntry();
		if(entry!=null) {
			this.remove(entry.getKey());
		}
		return entry;
	}

	@Override
	public void put(T key, v value) {
		// TODO Auto-generated method stub
		if(!container.contains(key)) size++;
		container.insert(key, value);
		
	}

	@Override
	public void putAll(Map<T, v> map) {
		// TODO Auto-generated method stub
		if(map==null) {
			throw new RuntimeErrorException(new Error());
		}
		for (Map.Entry<T, v> entry : map.entrySet()) {
			 if(!container.contains(entry.getKey())) size++;
            container.insert(entry.getKey(), entry.getValue());
           
        }
	}

	@Override
	public boolean remove(T key) {
		// TODO Auto-generated method stub
		if(container.delete(key)) {
			size--;
			return true ;
		}
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public Collection<v> values() {
		// TODO Auto-generated method stub
		if(container.isEmpty()) {
			return null ;
		}
		ArrayList<v> arr = new ArrayList<v>();
		this.inOrder(arr, container.getRoot());
		return arr;
	}
	
	private void inOrder(Collection<v> arr , INode<T,v> curNode){
		if(curNode.isNull()) {
			return ;
		}
		inOrder(arr,curNode.getLeftChild());
		arr.add(curNode.getValue());
		inOrder(arr,curNode.getRightChild());
	}
	private void inOrderKey(Collection<T> arr , INode<T,v> curNode){
		if(!curNode.isNull()) {
			return ;
		}
		inOrderKey(arr,curNode.getLeftChild());
		arr.add(curNode.getKey());
		inOrderKey(arr,curNode.getRightChild());
	}
	private void inOrderValue(ArrayList<Entry<T, v>> arr, INode<T,v> curNode){
		if(!curNode.isNull()) {
			return ;
		}
		inOrderValue(arr,curNode.getLeftChild());
		arr.add( new AbstractMap.SimpleEntry<>(curNode.getKey(), curNode.getValue()));
		inOrderValue(arr,curNode.getRightChild());
	}
	private boolean inOrder(v value , INode<T,v> curNode) {
		if(curNode.isNull()) {
			return false;
		}
		
		if(inOrder(value,curNode.getLeftChild())) {
			return true;
		}
		if(curNode.getValue().equals(value)) {
			return true;
		}
		if(inOrder(value,curNode.getRightChild())) {
			return true;
		}
		return false;
	}
	private INode<T,v> getMaximum(INode<T,v> curNode){
		while(curNode.getRightChild()!=NIL) {
			curNode = curNode.getRightChild();
		}
		return curNode;
	}
	private INode<T,v> getMinimum(INode<T,v> curNode){
		while(curNode.getLeftChild()!=NIL) {
			curNode = curNode.getLeftChild();
		}
		return curNode;
	}
	
	private INode<T,v> getNode(T key){
		INode<T,v> curNode = container.getRoot() ;
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
	private INode<T,v> inOrderSuccessor(INode<T,v> root, INode<T,v> n) { 
		  
        // step 1 of the above algorithm  
        if (!n.getRightChild().isNull()) { 
            return getMinimum(n.getRightChild()); 
        } 
  
        
        INode<T,v> p = n.getParent(); 
        while (!p.isNull() && n == p.getRightChild()) { 
            n = p; 
            p = p.getParent(); 
        } 
        return p; 
    } 
	private INode<T,v> inOrderPredecessor(INode<T,v> root, INode<T,v> n) { 
		  
        // step 1 of the above algorithm  
        if (!n.getLeftChild().isNull()) { 
            return getMinimum(n.getLeftChild()); 
        } 
  
        
        INode<T,v> p = n.getParent(); 
        while (!p.isNull() && n == p.getLeftChild()) { 
            n = p; 
            p = p.getParent(); 
        } 
        return p; 
    }


}
