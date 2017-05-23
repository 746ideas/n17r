package impl;

import adt.Set;

public class BSTSet<T extends Comparable> implements Set<T> {

	private TreeNode<T> root;
	private int size;

	public BSTSet() {
		root = null;
		size = 0;
	}

	@Override
	public void add(T value) {
		TreeNode<T> temp = new TreeNode<T>(value);
		if (!contains(value)) {
			if (root == null) {
				root = temp;
			} else {
				addHelper(root, temp);
			}
			size++;
		}
	}

	private void addHelper(TreeNode<T> node, TreeNode<T> temp) {
		if (temp.getValue().compareTo(node.getValue()) < 0) {
			if (node.getLeft() == null) {
				node.setLeft(temp);
				return;
			}
			addHelper(node.getLeft(), temp);
		} else {
			if (node.getRight() == null) {
				node.setRight(temp);
				return;
			}
			addHelper(node.getRight(), temp);
		}
	}

	@Override
	public boolean contains(T value) {
		if (size == 0) {
			return false;
		}
		return containsHelper(root, value);
	}

	private boolean containsHelper(TreeNode<T> node, T temp) {
		if (temp.compareTo(node.getValue()) == 0) {
			return true;
		}

		if (temp.compareTo(node.getValue()) < 0) {
			if (node.getLeft() == null) {
				return false;
			}
			return containsHelper(node.getLeft(), temp);
		} else {
			if (node.getRight() == null) {
				return false;
			}
			return containsHelper(node.getRight(), temp);
		}
	}

	@Override
	public boolean remove(T value) {
		if(!contains(value)){
            return false;
		}
        TreeNode<T> cur = root;
        TreeNode<T> parent = null;
        int isLeft = -1;
        
        size--;
        
        while(cur.getValue().compareTo(value) != 0) {
            parent = cur;
            if(cur.getValue().compareTo(value) > 0) {
                cur = cur.getLeft();
                isLeft = 0;
            } else {
                cur = cur.getRight();
                isLeft = 1;
            }
        }
        
        if(cur.getLeft() == null && cur.getRight() == null) {
            if(isLeft == -1){
                root = null;
            }else if(isLeft == 0){
                parent.setLeft(null);
            }else{
                parent.setRight(null);
            }
        }else if(cur.getLeft() == null && cur.getRight() != null) {
            if(isLeft == -1){
                root = cur.getRight();
            }else if(isLeft == 0){
                parent.setLeft(cur.getRight());
            }else{
                parent.setRight(cur.getRight());
            }
        }
        else if(cur.getLeft() != null && cur.getRight() == null) {
            if(isLeft == -1){
                root = cur.getLeft();
            }else if(isLeft == 0){
                parent.setLeft(cur.getLeft());
            }else{
                parent.setRight(cur.getLeft());
            }
        }else{
            TreeNode<T> parentNew = cur;
            TreeNode<T> small = cur.getRight();
            boolean q = true;
            
            while(small.getLeft() != null) {
                q = false;
                parentNew = small;
                small = small.getLeft();
            }
            
            T newValue = small.getValue();
            
            if(q){
                parentNew.setRight(small.getRight());
            } else {
                if(small.getRight() != null){
                    parentNew.setLeft(small.getRight());
                }else{
                    parentNew.setLeft(null);
                }
            }
            cur.setValue(newValue);
        }

        return true;
	}

	@Override
	public T removeAny() throws Exception {
		if (size == 0) {
			throw new Exception("The set is empty.");
		}
		T result = root.getValue();
		remove(result);
		return result;
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

	public String toString() {
		return "(" + toStringHelper(root) + ")";
	}

	private String toStringHelper(TreeNode<T> node) {
		if (node == null) {
			return "";
		}
		return toStringHelper(node.getLeft()) + node.getValue() + " " + toStringHelper(node.getRight());

	}

}
