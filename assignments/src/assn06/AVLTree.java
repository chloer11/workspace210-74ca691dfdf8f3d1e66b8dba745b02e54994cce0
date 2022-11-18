package assn06;

public class AVLTree<T extends Comparable<T>> implements SelfBalancingBST<T> {
    // Fields
    private T _value;
    private AVLTree<T> _left;
    private AVLTree<T> _right;
    private int _height;
    private int _size;

    public AVLTree() {
        _value = null;
        _left = null;
        _right = null;
        _height = -1;
        _size = 0;
    }

    /**
     * Rotates the tree left and returns
     * AVLTree root for rotated result.
     */

    private AVLTree<T> rotateLeft() {
        // You should implement left rotation and then use this
        // method as needed when fixing imbalances.
        // TODO
        // return null;
        // need new parent
        AVLTree<T> newbie = this._right;
        this._right = newbie._left;
        newbie._left = this;
        newbie.updateSizeofHeight();
        this.updateSizeofHeight();

        return newbie;
    }

    /**
     * Rotates the tree right and returns
     * AVLTree root for rotated result.
     */

    private AVLTree<T> rotateRight() {
        // You should implement right rotation and then use this
        // method as needed when fixing imbalances.
        // TODO
        // need new parent
        // return null;
        AVLTree<T> newbie = this._left;
        this._left = newbie._right;
        newbie._right = this;
        newbie.updateSizeofHeight();
        this.updateSizeofHeight();

        return newbie;
    }
    public int getBalFactor() {
        if (this._value == null) {
            return 0;
        }
        return Math.abs(this._left.height() - this._right.height());
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int height() {
        return _height;
    }

    //recursive, but sacrifices time complexity
    @Override
    public int size() {
        return _size;
    }

    //recursive, but sacrifices time complexity
    @Override
    public SelfBalancingBST<T> insert(T element) {
        // TODO
        // return null;
        if (this._size == 0) {
            this._value = element;
            this._left = new AVLTree<>();
            this._right = new AVLTree<>();
        }
        else {
            if (element.compareTo(_value) <= 0) {
                _left = (AVLTree<T>) _left.insert(element);
            } else {
                _right = (AVLTree<T>) _right.insert(element);
            }
        }
        _size += 1;
        this.updateSizeofHeight();

        return this.balance();
    }

    @Override
    public SelfBalancingBST<T> remove(T element) {
        // TODO
        // return null;
        if (isEmpty()) {
            return this;
        }
        if (element.compareTo(this.getValue()) > 0) {
            _right = (AVLTree<T>) this._right.remove(element);
        } else if (element.compareTo(this.getValue()) < 0) {
            _left = (AVLTree<T>) this._left.remove(element);
        }
        if (element.compareTo(this.getValue()) == 0) {
            if (this._right.isEmpty() && this._left.isEmpty()) {
                _value = null;
            }
            if (this._right.isEmpty() && !this._left.isEmpty()) {
                _value = _left.getValue();
                _left = new AVLTree<>();
            } else if (this._left.isEmpty()) {
                _value = _left.getValue();
                _right = new AVLTree<>();
            } else {
                _value = _right.findMin();
                _right = (AVLTree<T>) _right.remove(_value);
            }
        }
        _left.updateSizeofHeight();
        _right.updateSizeofHeight();
        updateSizeofHeight();
        return balance();
    }

    @Override
    public T findMin() {
        // TODO
        // return null;
        if (this._value == null) {
            throw new RuntimeException();
        }
        T min = this._value;
        if (this.hasLeft()) {
            return this.getLeft().findMin();
        }
        return min;
    }

    @Override
    public T findMax() {
        // TODO
        // return null;
        if (this._value == null) {
            throw new RuntimeException();
        }
        T max = this._value;
        if (this.hasRight()) {
            return this.getRight().findMax();
        }
        return max;
    }

    @Override
    public boolean contains(T element) {
        // TODO
        // return false;
        if (getValue() != null) {
            if (getValue().compareTo(element) == 0) {
                return true;
            } else if (getValue().compareTo(element) > 0) {
                if (_left != null && _right._value != null) {
                    return _left.contains(element);
                }
            } else if (getValue().compareTo(element) < 0) {
                if (_right != null && _right._value != null) {
                    return _right.contains(element);
                }
            }
        }
        return false;
    }

    @Override
    public T getValue() {
        return _value;
    }

    @Override
    public SelfBalancingBST<T> getLeft() {
        if (isEmpty()) {
            return null;
        }
        return _left;
    }

    @Override
    public SelfBalancingBST<T> getRight() {
        if (isEmpty()) {
            return null;
        }
        return _right;
    }

    public boolean hasLeft() {
        return _left.size() != 0;
    }

    public boolean hasRight() {
        return _right.size() != 0;
    }

    public void updateSizeofHeight() {
        if (this._value == null) {
            _size = 0;
            _height = -1;
        } else {
            _height = 1 + Math.max(_left.height(), _right.height());
            _size = 1 + _left.size() + _right.size();
        }
    }
    public SelfBalancingBST<T> balance() {
        // heavy on which side? right or left?
        // case sensitivity
        if (this._value == null || this.getBalFactor() < 2) {
            return this;
        }
        else {
            AVLTree<T> temporary = this;
            if (_left.height() > _right.height()) {
                if (this._left._right.height() > this._left._left.height()) {
                    this._left = _left.rotateLeft();
                }
                temporary = this.rotateRight();
            }
            else if (_right.height() > _left.height()) {
                if (this._right._left.height() > this._right._right.height()) {
                    this._right = _right.rotateRight();
                }
                temporary = rotateLeft();
            }
            temporary.updateSizeofHeight();
            return temporary;
        }
    }
}