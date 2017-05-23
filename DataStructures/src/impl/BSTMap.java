package impl;
 
import impl.KeyValuePair;
import adt.Map;
 
public class BSTMap<K extends Comparable, V> implements Map<K, V> {
 
	private TreeNode<KeyValuePair<K,V>> root;
	private int size;
	
	public BSTMap() {
		root = null;
		size = 0;
	}
	
	@Override
	public void define(K key, V value) {
		TreeNode<KeyValuePair<K,V>> newNode = new TreeNode(value);
		newNode.setValue(new KeyValuePair<K,V>(key, value));
        
        if(root == null){
            root = newNode;
        }else {
            TreeNode<KeyValuePair<K,V>> cur = root;
            TreeNode<KeyValuePair<K,V>> parent = null;
            boolean isLeft = true;
            
            while(cur != null) {
                parent = cur;
                if(cur.getValue().getKey().compareTo(key) > 0) {
                    cur = cur.getLeft();
                    isLeft = true;
                } else if(cur.getValue().getKey().compareTo(key) < 0){
                    cur = cur.getRight();
                    isLeft = false;
                } else {
                	cur.getValue().setValue(value);
                	return;
                }
            }
               
            if(isLeft)
                parent.setLeft(newNode);
            else
                parent.setRight(newNode);
        }
        size++;
	}
 
	@Override
	public V getValue(K key) {
		TreeNode<KeyValuePair<K,V>> cur = root;
        while(cur != null) {
            if(cur.getValue().getKey().compareTo(key) == 0){
                return cur.getValue().getValue();
            }else if(cur.getValue().getKey().compareTo(key) > 0){
                cur = cur.getLeft();
            }else{
                cur = cur.getRight();
            }
        }
        return null;
	}
 
	@Override
	public V remove(K key) {
		size--;
		return removeHelper(root, key);
	}
	
	public V removeHelper(TreeNode<KeyValuePair<K, V>> node, K key){
		if (key.compareTo(node.getValue().getKey()) == 0) {
			TreeNode<KeyValuePair<K,V>> minNode = node.getRight();
			while (minNode.getLeft() != null) {
				minNode = minNode.getLeft();
			}
			K minkey = minNode.getValue().getKey();
			V minvalue=minNode.getValue().getValue();
			remove(minkey);
			node.setValue(new KeyValuePair(minkey, minvalue));
			size++;
			return node.getValue().getValue();
		}

		if (key.compareTo(node.getValue().getKey()) < 0) {
			if (node.getLeft() == null) {
				return null;
			} else if (key.compareTo(node.getLeft().getValue().getKey()) == 0) {
				if (node.getLeft().getLeft() == null && node.getLeft().getRight() == null) {
					node.setLeft(null);
					return node.getLeft().getValue().getValue();
				} else if (node.getLeft().getLeft() != null && node.getLeft().getRight() == null) {
					node.setLeft(node.getLeft().getLeft());
					return node.getLeft().getValue().getValue();
				} else if (node.getLeft().getLeft() == null && node.getLeft().getRight() != null) {
					node.setLeft(node.getLeft().getRight());
					return node.getLeft().getValue().getValue();
				} else {
					TreeNode<KeyValuePair<K,V>> minNode = node.getLeft().getRight();
					while (minNode.getLeft() != null) {
						minNode = minNode.getLeft();
					}
					K minkey = minNode.getValue().getKey();
					V minvalue=minNode.getValue().getValue();
					remove(minkey);
					node.getLeft().setValue(new KeyValuePair(minkey, minvalue));;
					size++;
					return node.getLeft().getValue().getValue();
				}
			}
			return removeHelper(node.getLeft(), key);
		} else {
			if (node.getRight() == null) {
				return null;
			} else if (key.compareTo(node.getRight().getValue().getKey()) == 0) {
				if (node.getRight().getLeft() == null && node.getRight().getRight() == null) {
					V temp=node.getRight().getValue().getValue();
					node.setRight(null);
					return temp;
				} else if (node.getRight().getLeft() != null && node.getRight().getRight() == null) {
					node.setRight(node.getRight().getLeft());
					return node.getRight().getValue().getValue();
				} else if (node.getRight().getLeft() == null && node.getRight().getRight() != null) {
					node.setRight(node.getRight().getRight());
					return node.getRight().getValue().getValue();
				} else {
					TreeNode<KeyValuePair<K,V>> minNode = node.getRight().getRight();
					while (minNode.getLeft() != null) {
						minNode = minNode.getLeft();
					}
					K minkey = minNode.getValue().getKey();
					V minvalue=minNode.getValue().getValue();
					remove(minkey);
					node.getRight().setValue(new KeyValuePair(minkey, minvalue));
					size++;
					return node.getRight().getValue().getValue();
					}
				}
			}
		return removeHelper(node.getRight(), key);
	}
 
	@Override
	public KeyValuePair<K, V> removeAny() throws Exception {
		if(size==0){
			throw new Exception("BSTMap is empty");
		}else{
			remove(root.getValue().getKey());
			return root.getValue();
		}
	}
 
	@Override
	public int getSize() {
		return size;
	}
 
	@Override
	public void clear() {
		root = null;
		size = 0;
	}
 
    @Override
    public String toString() {
    	return toStringHelper(root);
    }
    private String toStringHelper(TreeNode<KeyValuePair<K, V>> node){
        if(node == null){
            return "";
        }
        return toStringHelper(node.getLeft())+" "+
                "<"+node.getValue().getKey()+","+
                node.getValue().getValue()+">"+
                toStringHelper(node.getRight());
    }
}