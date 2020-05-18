import java.util.Vector;

public class MyList {
    private class Node {
        public Node left;
        public Node right;
        public Page page;

        public Node() {
            this.left = this.right = null;
            this.page = null;
        }

        public Node(Page page){
            this.left = null;
            this.right = null;
            this.page = page;
        }

        public void clearAll() {
            left = right = null;
            page = null;
        }
    }

    private Node root;

    public MyList(Vector memory, int size) {
        for (int i = 0;i < size; ++i) {
            Page page = (Page) memory.elementAt(i);
            if (page.physical == -1)
                continue;
            pushBack(page);
        }
    }

    public Page getByVirtualId(int id) {
        Node start = root;
        boolean ok = false;
        while (start != null && (start != root || !ok)) {
            if (start.page.id == id)
                return start.page;
            start = start.right;
            ok = true;
        }
        return null;
    }

    public void printList() {
        Node start = root;
        boolean ok = false;
        while (start != null && (start != root || !ok)) {
            System.out.print(start.page.id + " ");
            start = start.right;
            ok = true;
        }
        System.out.println();
        start = root;
        ok = false;
        while (start != null && (start != root || !ok)) {
            System.out.print(start.page.physical + " ");
            start = start.right;
            ok = true;
        }
        System.out.println();
        start = root;
        ok = false;
        while (start != null && (start != root || !ok)) {
            System.out.print(start.page.lastTouchTime + " ");
            start = start.right;
            ok = true;
        }
        System.out.println();
    }

    public Page top() {
        return root.page;
    }

    public void popFront() {
        Node prev = root.left;
        Node next = root.right;
        if (root.left == root)
        {
            root.clearAll();
            root = null;
            return;
        }
        prev.right = next;
        next.left = prev;
        root = next;
    }

    public void pushBack(Page page) {
        Node newNode = new Node(page);
        if (root == null) {
            root = newNode;
            root.right = root.left = root;
        }
        Node prev = root.left;
        prev.right = newNode;
        newNode.left = prev;
        root.left = newNode;
        newNode.right = root;
    }

    public void moveToEnd(int virtualPageId) {
        Node current = root;
        boolean repeatedRoot = false;
        boolean findPage = false;
        while (current != null && (current != root || !repeatedRoot)) {
            if (current.page.id == virtualPageId) {
                findPage = true;
                break;
            }
            current = current.right;
            repeatedRoot = true;
        }
        assert !findPage;

        if (current != root) {
            Node prevCurrent = current.left;
            Node nextCurrent = current.right;
            prevCurrent.right = nextCurrent;
            nextCurrent.left = prevCurrent;

            pushBack(current.page);
            current.clearAll();
            current = null;
        }
        else
            root = root.right;
    }

}
