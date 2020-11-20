package in.pks.journal.java.datastructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * A simple implementation of SingleLinkedList.
 * Element of list extends Comparable so that sort feature can be provided.
 * @param <T>
 */
public class SingleLinkedList<T extends Comparable> {

    private Node<T> rootNode = null;
    private Node<T> currentNode = null;
    private int length = 0;
    private long modifiedCount = 0;


    private static class Node<T>{
        private T data;
        private Node<T> nextNode = null;

        Node(T t){
            this.data = t;
        }

        public T getData() {
            return data;
        }

        public Node<T> getNextNode() {
            return nextNode;
        }

        public void setNextNode(Node<T> nextNode) {
            this.nextNode = nextNode;
        }

        public boolean hasNext(){
            return Objects.nonNull(this.nextNode);
        }

        @Override
        public String toString() {
            return Objects.nonNull(data)?data.toString():"";
        }
    }


    /**
     * Adds T to the end of the list
     * @param data
     * @throws NullPointerException if data to be added is null.
     */
    public synchronized void add(T data){
        if(Objects.isNull(data)){
            throw new NullPointerException("Null can't be added.");
        }

        Node<T> node = new Node<T>(data);
        if(Objects.isNull(rootNode)){
            rootNode = node;
        } else{
            this.currentNode.setNextNode(node);
        }
        this.currentNode = node;
        this.length++;
        this.modifiedCount++;
    }

    /**
     * Removes first occurrence of T from list and returns the same. If <T> is not present, then returns null.
     * @param data
     * @return
     * @throws NullPointerException if data to be removed is Null.
     */
    public synchronized T remove(T data){

        if(Objects.isNull(data)){
            throw new NullPointerException("Null can't be removed.");
        }

        Node<T> _crawler = this.rootNode;
        Node<T> prevNode = null;
        T removedData = null;

        while(Objects.nonNull(_crawler)){
            if(Objects.nonNull(_crawler.getData()) && _crawler.getData().equals(data)){
                removedData = _crawler.getData();

                /**
                 * if previous-node is null (i.e.) current Node is root, then check
                 *      if root is only node in list then - set root null, current null
                 *      else if list has more nodes, then set root to root.next.
                 * if crawlerNode is last Node then set prevNode.nextNode = null;
                 *
                 */

                if(!_crawler.hasNext()){
                    this.currentNode = prevNode;
                }

                if(Objects.isNull(prevNode)){
                    this.rootNode = _crawler.getNextNode();
                } else {
                    prevNode.setNextNode(_crawler.getNextNode());
                }
                this.length--;
                this.modifiedCount++;
                break;
            }
            prevNode = _crawler;
            _crawler = _crawler.getNextNode();
        }

        return removedData;
    }

    public boolean contains(T data){
        Node<T> crawler = this.rootNode;
        boolean contains = false;

        while(Objects.nonNull(crawler)){
            contains = crawler.getData().equals(data);
            if(contains)
                break;
            crawler = crawler.getNextNode();
        }


        return contains;
    }

    private int size(){
        return this.length;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        int count=0;
        Node<T> _crawler = this.rootNode;
        while(Objects.nonNull(_crawler)){
            if(count++ != 0){
                sb.append(" --> ");
            }
            sb.append(_crawler.getData());
            _crawler = _crawler.getNextNode();

        }

        return sb.toString();
    }

    public static void main(String[] args) {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        System.out.println("Current list length: " + list.size());
        for (char i=49; i<=59; i++){
            list.add(Character.toString(i));
        }
        System.out.println("List:\n" + list.toString());

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(list.size()>0){
            try{
                System.out.print("\nEnter element: ");
                String element = br.readLine();
                if("exit".equalsIgnoreCase(element)){
                    break;
                }
                System.out.print("\nOption (1-Add 2-Remove 3-Seek): ");
                String optn = br.readLine();
                switch(optn){
                    case "1":
                        list.add(element);
                        break;
                    case "2":
                        list.remove(element);
                        break;
                    case "3":
                        System.out.println("Found: " + list.contains(element));
                        break;
                    default:
                        System.err.println("\nInvalid selection. Try again!!!\n");
                        continue;
                }
                System.out.println("List (size= " +list.size() + "): " + list.toString());
            } catch (IOException e){}
        }

        try{
            if(Objects.nonNull(br)){
                br.close();
            }
        } catch (IOException e){}

        System.out.println(list.toString());
    }
}
